/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled.impl;

import com.google.common.collect.Iterables;
import it.csi.conam.conambl.business.facade.DoquiServiceFacade;
import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.business.service.util.UtilsTraceCsiLogAuditService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.entity.CsiLogAudit.TraceOperation;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.scheduled.AllegatoScheduledService;
import it.csi.conam.conambl.scheduled.VerbaleScheduledService;
import it.csi.conam.conambl.util.UtilsTipoAllegato;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 mar 2019
 */
@Service
public class VerbaleScheduledServiceImpl implements VerbaleScheduledService {

	protected static Logger logger = Logger.getLogger(VerbaleScheduledServiceImpl.class);

	@Autowired
	private CnmRAllegatoVerbaleRepository cnmRAllegatoVerbaleRepository;
	@Autowired
	private CnmDStatoAllegatoRepository cnmDStatoAllegatoRepository;
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	@Autowired
	private StadocServiceFacade stadocServiceFacade;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private AllegatoScheduledService allegatoScheduledService;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;

	@Autowired
	private DoquiServiceFacade doquiServiceFacade;

	@Autowired
	private UtilsCnmCProprietaService utilsCnmCProprietaService;

	// 20200622_LC
	@Autowired
	private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;
	
	// 20200711_LC
	@Autowired
	private CnmTUserRepository cnmTUserRepository;

	private boolean isDoquiDirect() {
		 return Boolean.valueOf(utilsCnmCProprietaService.getProprieta(PropKey.IS_DOQUI_DIRECT));
	}
	
	@Override
	public void sendAllegatiInCodaToActa() {
		if(isDoquiDirect()) {
			sendAllegatiInCodaToActa_Doqui();
		}else {
			sendAllegatiInCodaToActa_Stadoc();
		}
	}

	public void sendAllegatiInCodaToActa_Stadoc() {
		List<CnmTVerbale> cnmTVerbales = cnmTVerbaleRepository.findCnmTVerbaleAndIdStatoAllegato(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA, new PageRequest(0, 1));
		List<CnmTAllegato> allegati = cnmTAllegatoRepository.findAllegatiComparsaByStato();
		if (cnmTVerbales.isEmpty() && (allegati == null || allegati.isEmpty())) {
			logger.info("Nessun Allegato o verbale trovato da spostare in ACTA");
			return;
		}

		logger.info("Send allegati To Acta START");
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoAllegato avvioSpostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);

		if (!(allegati == null || allegati.isEmpty())) {

			// aggiorno in spostamento
			allegati = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(allegati);

			for (CnmTAllegato cnmTAllegato : allegati) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());

					logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome file: "  + cnmTAllegato.getNomeFile());
					ResponseAggiungiAllegato responseAggiungiAllegato = null;					
					try {
						responseAggiungiAllegato = stadocServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegato.getIdActaMaster(),
								utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
								cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), StadocServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA, null);
					} catch (Exception e) {
						logger.error("Non riesco ad aggiungere l'allegato al master", e);
						cnmTAllegato.setCnmDStatoAllegato(avvioSpostamentoActa);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
					}
					if (responseAggiungiAllegato != null) {
						logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						String idIndex = cnmTAllegato.getIdIndex();
						cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_MULTI_NON_PROTOCOLLARE));
						cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
						cnmTAllegato.setIdIndex(null);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);

						try {
							stadocServiceFacade.eliminaDocumentoIndex(idIndex);
						} catch (Exception e) {
							logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
						}

					}

					CnmRAllegatoVerbale findByCnmTAllegato = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(cnmTAllegato);
					if (findByCnmTAllegato == null || findByCnmTAllegato.getId() == null) {
						logger.info("Mi prendo il master x idActa :: " + cnmTAllegato.getIdActa());
						CnmTVerbale master = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());
						logger.info("id verbale Master :: " + master.getIdVerbale());

						CnmRAllegatoVerbale cnmRAllegatoVerbale = new CnmRAllegatoVerbale();
						CnmRAllegatoVerbalePK cnmRAllegatoVerbalePK = new CnmRAllegatoVerbalePK();
						cnmRAllegatoVerbalePK.setIdAllegato(cnmTAllegato.getIdAllegato());
						cnmRAllegatoVerbalePK.setIdVerbale(master.getIdVerbale());
						cnmRAllegatoVerbale.setCnmTUser(master.getCnmTUser2());
						cnmRAllegatoVerbale.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
						cnmRAllegatoVerbale.setId(cnmRAllegatoVerbalePK);
						cnmRAllegatoVerbaleRepository.save(cnmRAllegatoVerbale);
					}
				}
								
			}

		}

		if (!cnmTVerbales.isEmpty()) {
			CnmTVerbale cnmTVerbale = cnmTVerbales.get(0);

			// faccio un verbale alla volta
			List<CnmTAllegato> cnmTAllegatos = cnmRAllegatoVerbaleRepository.findCnmTAllegatosByCnmTVerbale(cnmTVerbale);
			// check master
			CnmTAllegato cnmTAllegatoMaster = Iterables.tryFind(cnmTAllegatos, UtilsTipoAllegato.findCnmTAllegatoInCnmTAllegatosByTipoAllegato(TipoAllegato.RAPPORTO_TRASMISSIONE)).orNull();

			if (cnmTAllegatoMaster == null) {
				logger.info("nessun allegato master trovato!");
				return;

			}

			if (cnmTAllegatoMaster.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_PROTOCOLLATO || cnmTVerbale.getNumeroProtocollo() == null) {
				logger.info("Allegato master con id " + cnmTAllegatoMaster.getIdAllegato() + " non protocollato!");
				return;
			}

			// aggiorno in spostamento
			cnmTAllegatos = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(cnmTAllegatos);

			for (CnmTAllegato cnmTAllegato : cnmTAllegatos) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome allegato: " + cnmTAllegato.getNomeFile());
					ResponseAggiungiAllegato responseAggiungiAllegato = null;
					try {
						responseAggiungiAllegato = stadocServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegatoMaster.getIdActa(),
								utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
								cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), StadocServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_INGRESSO, null);
					} catch (Exception e) {
						logger.error("Non riesco ad aggiungere l'allegato al master", e);
						cnmTAllegato.setCnmDStatoAllegato(avvioSpostamentoActa);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
					}
					if (responseAggiungiAllegato != null) {
						logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						String idIndex = cnmTAllegato.getIdIndex();
						cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
						cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
						cnmTAllegato.setIdIndex(null);
						cnmTAllegato.setIdActaMaster(cnmTAllegatoMaster.getIdActa());
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);

						try {
							stadocServiceFacade.eliminaDocumentoIndex(idIndex);
						} catch (Exception e) {
							logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
						}

					}
				}
			}
		}
	}
	

	public void sendAllegatiInCodaToActa_Doqui() {
		List<CnmTVerbale> cnmTVerbales = cnmTVerbaleRepository.findCnmTVerbaleAndIdStatoAllegato(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA, new PageRequest(0, 1));
		List<CnmTAllegato> allegati = cnmTAllegatoRepository.findAllegatiComparsaByStato();
		if (cnmTVerbales.isEmpty() && (allegati == null || allegati.isEmpty())) {
			logger.info("Nessun Allegato o verbale trovato da spostare in ACTA");
			return;
		}

		logger.info("Send allegati To Acta START");
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoAllegato avvioSpostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
		String operationToTrace = null;

		if (!(allegati == null || allegati.isEmpty())) {

			// aggiorno in spostamento
			allegati = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(allegati);

			for (CnmTAllegato cnmTAllegato : allegati) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());

					logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome file: "  + cnmTAllegato.getNomeFile());
					ResponseAggiungiAllegato responseAggiungiAllegato = null;					
					try {
						responseAggiungiAllegato = doquiServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegato.getIdActaMaster(),
								utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
								cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_USCITA, null, new Date(cnmTAllegato.getDataOraInsert().getTime()));
					} catch (Exception e) {
						logger.error("Non riesco ad aggiungere l'allegato al master", e);
						cnmTAllegato.setCnmDStatoAllegato(avvioSpostamentoActa);
						// 20200711_LC
						CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
						cnmTAllegato.setCnmTUser1(cnmTUser);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
					}
					if (responseAggiungiAllegato != null) {
						logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						String idIndex = cnmTAllegato.getIdIndex();
						cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_MULTI_NON_PROTOCOLLARE));
						cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
						cnmTAllegato.setIdIndex(null);
						// 20200711_LC
						CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
						cnmTAllegato.setCnmTUser1(cnmTUser);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);

						try {
							doquiServiceFacade.eliminaDocumentoIndex(idIndex);
						} catch (Exception e) {
							logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
						}
						
					
						// 20200622_LC traccia job spostamento
						String className=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().lastIndexOf(".")+1);
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(),className+"."+ Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						// 20201120_LC	traccia inserimento allegato su Acta
						operationToTrace = cnmTAllegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
						utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseAggiungiAllegato.getObjectIdDocumento(), className+"."+ Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

					}

					CnmRAllegatoVerbale findByCnmTAllegato = cnmRAllegatoVerbaleRepository.findByCnmTAllegato(cnmTAllegato);
					if (findByCnmTAllegato == null || findByCnmTAllegato.getId() == null) {
						logger.info("Mi prendo il master x idActa :: " + cnmTAllegato.getIdActa());
						CnmTVerbale master = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());
						logger.info("id verbale Master :: " + master.getIdVerbale());

						CnmRAllegatoVerbale cnmRAllegatoVerbale = new CnmRAllegatoVerbale();
						CnmRAllegatoVerbalePK cnmRAllegatoVerbalePK = new CnmRAllegatoVerbalePK();
						cnmRAllegatoVerbalePK.setIdAllegato(cnmTAllegato.getIdAllegato());
						cnmRAllegatoVerbalePK.setIdVerbale(master.getIdVerbale());
						cnmRAllegatoVerbale.setCnmTUser(master.getCnmTUser2());
						cnmRAllegatoVerbale.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
						cnmRAllegatoVerbale.setId(cnmRAllegatoVerbalePK);
						cnmRAllegatoVerbaleRepository.save(cnmRAllegatoVerbale);
						
						
					}
					
					// 20210312-PP-SCENARIO-6 - test protocollazione master esistente, decommentare solo se va implementata
					// 20210312 PP - controllo se il master e' tra quelli per cui e' prevista la protocollazione dopo lo spostamento
					// e se tutti gli allegati al master sono gia' stati spostati
					// 20210423_LC per l'istanza di rateizzazone, spostato in ordinanzascheduledsserviceimpl
					
					
//					List<CnmRAllegatoVerbale> cnmRAllegatoVerbales  = cnmRAllegatoVerbaleRepository.findByCnmTVerbale(cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster()));
//					//					
//					if(masterProtocollabile(cnmRAllegatoVerbales)) {
//						
//						cnmRAllegatoVerbales  = cnmRAllegatoVerbaleRepository.findByCnmTVerbale(cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster()));
//						CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
//						commonAllegatoService.avviaProtocollazioneDocumentoEsistente(cnmRAllegatoVerbales, cnmTUser);
//						
//					}
//					
				}
			}
		}

		if (!cnmTVerbales.isEmpty()) {
			CnmTVerbale cnmTVerbale = cnmTVerbales.get(0);

			// faccio un verbale alla volta
			List<CnmTAllegato> cnmTAllegatos = cnmRAllegatoVerbaleRepository.findCnmTAllegatosByCnmTVerbale(cnmTVerbale);
			// check master
			CnmTAllegato cnmTAllegatoMaster = Iterables.tryFind(cnmTAllegatos, UtilsTipoAllegato.findCnmTAllegatoInCnmTAllegatosByTipoAllegato(TipoAllegato.RAPPORTO_TRASMISSIONE)).orNull();

			if (cnmTAllegatoMaster == null) {
				logger.info("nessun allegato master trovato!");
				return;

			}

			if (cnmTAllegatoMaster.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_PROTOCOLLATO || cnmTVerbale.getNumeroProtocollo() == null) {
				logger.info("Allegato master con id " + cnmTAllegatoMaster.getIdAllegato() + " non protocollato!");
				return;
			}

			// aggiorno in spostamento
			cnmTAllegatos = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(cnmTAllegatos);

			for (CnmTAllegato cnmTAllegato : cnmTAllegatos) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					logger.info("Sposto allegato su acta con id" + cnmTAllegato.getIdAllegato() + " Nome allegato: " + cnmTAllegato.getNomeFile());
					ResponseAggiungiAllegato responseAggiungiAllegato = null;
					try {
						responseAggiungiAllegato = doquiServiceFacade.aggiungiAllegato(null, cnmTAllegato.getNomeFile(), cnmTAllegato.getIdIndex(), cnmTAllegatoMaster.getIdActa(),
								utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmTAllegato.getCnmDTipoAllegato()), "", utilsDoqui.getSoggettoActa(cnmTVerbale), utilsDoqui.getRootActa(cnmTVerbale),
								cnmTAllegato.getCnmDTipoAllegato().getIdTipoAllegato(), DoquiServiceFacade.TIPOLOGIA_DOC_ACTA_ALLEGATI_AL_MASTER_INGRESSO, null, new Date(cnmTAllegato.getDataOraInsert().getTime()));
					} catch (Exception e) {
						logger.error("Non riesco ad aggiungere l'allegato al master", e);
						cnmTAllegato.setCnmDStatoAllegato(avvioSpostamentoActa);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);
					}
					if (responseAggiungiAllegato != null) {
						logger.info("Spostato allegato con id" + cnmTAllegato.getIdAllegato() + "e di tipo:" + cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						String idIndex = cnmTAllegato.getIdIndex();
						cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
						cnmTAllegato.setIdActa(responseAggiungiAllegato.getIdDocumento());
						cnmTAllegato.setIdIndex(null);
						cnmTAllegato.setIdActaMaster(cnmTAllegatoMaster.getIdActa());
						// 20200711_LC
						CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
						cnmTAllegato.setCnmTUser1(cnmTUser);
						cnmTAllegato.setDataOraUpdate(now);
						allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);

						try {
							doquiServiceFacade.eliminaDocumentoIndex(idIndex);
						} catch (Exception e) {
							logger.error("Non riesco ad eliminare l'allegato con idIndex :: " + idIndex);
						}
						
											
						// 20200622_LC traccia job spostamento
						String className=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().lastIndexOf(".")+1);
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.SPOSTAMENTO_ALLEGATO_DA_INDEX.getOperation(),cnmTAllegato.getClass().getAnnotation(Table.class).name(),"id_allegato="+cnmTAllegato.getIdAllegato(), className+"."+Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());

						// 20201120_LC	traccia inserimento allegato su Acta
						operationToTrace = cnmTAllegato.isFlagDocumentoPregresso() ? TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO.getOperation() : TraceOperation.INSERIMENTO_ALLEGATO.getOperation();
						utilsTraceCsiLogAuditService.traceCsiLogAudit(operationToTrace,Constants.OGGETTO_ACTA,"objectIdDocumento="+responseAggiungiAllegato.getObjectIdDocumento(), className+"."+Thread.currentThread().getStackTrace()[1].getMethodName(), cnmTAllegato.getCnmDTipoAllegato().getDescTipoAllegato());
	
					}
	
				}
				
			}
		}
	}

	
	
	
	
//	@SuppressWarnings("unused")
//	private boolean masterProtocollabile(List<CnmRAllegatoVerbale> cnmRAllegatoVerbales) {
//		
//		//TODO 20210312 - per i test proviamo con la COMPARSA, ma dovra' essere implemtnato solo per ISTANZA_RATEIZZAZIONE
////		CnmRAllegatoVerbale cnmRAllegatoVerbaleMaster = Iterables.tryFind(cnmRAllegatoVerbales, UtilsTipoAllegato.findAllegatoInCnmRAllegatoVerbaleByTipoAllegato(TipoAllegato.ISTANZA_RATEIZZAZIONE))
//		CnmRAllegatoVerbale cnmRAllegatoVerbaleMaster = Iterables.tryFind(cnmRAllegatoVerbales, UtilsTipoAllegato.findAllegatoInCnmRAllegatoVerbaleByTipoAllegato(TipoAllegato.COMPARSA))
//				.orNull();
//		
//		if(cnmRAllegatoVerbaleMaster!= null) {
//			for(CnmRAllegatoVerbale cnmRAllegatoVerbale : cnmRAllegatoVerbales) {
//				if(cnmRAllegatoVerbale.getCnmTAllegato().getIdActaMaster() != null && cnmRAllegatoVerbale.getCnmTAllegato().getIdActaMaster().equalsIgnoreCase(cnmRAllegatoVerbaleMaster.getCnmTAllegato().getIdActa())
//						&& (cnmRAllegatoVerbale.getCnmTAllegato().getIdActa() == null || cnmRAllegatoVerbale.getCnmTAllegato().getIdActa().length() == 0)) {
//					return false;
//				}
//			}
//		}else {
//			return false;
//		}
//		
//		return true;
//	}
	
	
	
	
}
