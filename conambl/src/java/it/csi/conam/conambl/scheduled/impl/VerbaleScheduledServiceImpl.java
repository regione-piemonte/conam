/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;

import it.csi.conam.conambl.business.facade.DoquiServiceFacade;
import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.business.service.util.UtilsTraceCsiLogAuditService;
import it.csi.conam.conambl.business.service.verbale.VerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.beans.RequestSpostaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseAggiungiAllegato;
import it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseSpostaDocumento;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.conam.conambl.integration.doqui.bean.SoggettoActa;
import it.csi.conam.conambl.integration.doqui.bean.UtenteActa;
import it.csi.conam.conambl.integration.doqui.entity.CnmDTipoFornitore;
import it.csi.conam.conambl.integration.doqui.entity.CnmTDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmTFruitore;
import it.csi.conam.conambl.integration.doqui.entity.CnmTSpostamentoActa;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;
import it.csi.conam.conambl.integration.doqui.exception.RicercaDocumentoException;
import it.csi.conam.conambl.integration.doqui.helper.ManageRicercaDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.helper.ManageSpostaDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.repositories.CnmDTipoFornitoreRepository;
import it.csi.conam.conambl.integration.doqui.repositories.CnmTDocumentoRepository;
import it.csi.conam.conambl.integration.doqui.repositories.CnmTSpostamentoActaRepository;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.entity.CnmDStatoAllegato;
import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbalePK;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.entity.CsiLogAudit.TraceOperation;
import it.csi.conam.conambl.integration.repositories.CnmDStatoAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.scheduled.AllegatoScheduledService;
import it.csi.conam.conambl.scheduled.VerbaleScheduledService;
import it.csi.conam.conambl.util.UtilsTipoAllegato;

/**
 * @author riccardo.bova
 * @date 14 mar 2019
 */
@Service
public class VerbaleScheduledServiceImpl implements VerbaleScheduledService {

	protected static Logger logger = Logger.getLogger(VerbaleScheduledServiceImpl.class);

	private static final int DAY_BEFORE = 2;
	
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

	@Autowired
	private CommonAllegatoService commonAllegatoService;

	@Autowired
	private CnmTDocumentoRepository cnmTDocumentoRepository;

	@Autowired 
	private CnmTSpostamentoActaRepository cnmTSpostamentoActaRepository;

	@Autowired
	private ManageRicercaDocumentoHelper manageRicercaDocumentoHelper;

	@Autowired
	private ManageSpostaDocumentoHelper manageSpostaDocumentoHelper;

	@Autowired
	private CnmDTipoFornitoreRepository 		cnmDTipoFornitoreRepository;

	
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
		

		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.add(Calendar.DAY_OF_WEEK, -DAY_BEFORE);
	    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
	    
		
		List<CnmTVerbale> cnmTVerbales = cnmTVerbaleRepository.findCnmTVerbaleAndIdStatoAllegato(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA, timestamp, new PageRequest(0, 1));
		List<CnmTAllegato> allegati = cnmTAllegatoRepository.findAllegatiComparsaByStato(timestamp);
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
		

		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.add(Calendar.DAY_OF_WEEK, -DAY_BEFORE);
	    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
	    
		List<CnmTVerbale> cnmTVerbales = cnmTVerbaleRepository.findCnmTVerbaleAndIdStatoAllegato(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA, timestamp, new PageRequest(0, 1));
		if(cnmTVerbales.isEmpty()) {
			cnmTVerbales = cnmTVerbaleRepository.findCnmTVerbalePending();
		}
		List<CnmTAllegato> allegati = cnmTAllegatoRepository.findAllegatiComparsaByStato(timestamp);
		if (cnmTVerbales.isEmpty() && (allegati == null || allegati.isEmpty())) {
			logger.info("Nessun Allegato o verbale trovato da spostare in ACTA");
			return;
		}

		logger.info("Send allegati To Acta START");
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoAllegato avvioSpostamentoActa = cnmDStatoAllegatoRepository.findOne(Constants.STATO_AVVIA_SPOSTAMENTO_ACTA);
		String operationToTrace = null;


		//2021901 PP - CR_107
		manageComparsa(allegati, avvioSpostamentoActa);
		

		if (!cnmTVerbales.isEmpty()) {

			CnmTVerbale cnmTVerbale = cnmTVerbales.get(0);
			
			// faccio un verbale alla volta
			List<CnmTAllegato> cnmTAllegatos = cnmRAllegatoVerbaleRepository.findCnmTAllegatosByCnmTVerbale(cnmTVerbale);
			// cerco relata di notifica

			List<CnmTAllegato> allegatiRelata = cnmTAllegatoRepository.findAllegatiRelataAvviaSpostamento(cnmTVerbale.getIdVerbale());
			cnmTAllegatos.addAll(allegatiRelata);
			
			// check master
			CnmTAllegato cnmTAllegatoMaster = Iterables.tryFind(cnmTAllegatos, UtilsTipoAllegato.findCnmTAllegatoInCnmTAllegatosByTipoAllegato(TipoAllegato.RAPPORTO_TRASMISSIONE)).orNull();

			if (cnmTAllegatoMaster == null) {
				logger.info("nessun allegato master trovato!");
				return;

			}

			// 20210805 PP - elimino questo controllo poiche' il master è ancora da protocollare e il numero di protocollo non è stato ancora riportato sul doc
//			if (cnmTAllegatoMaster.getCnmDStatoAllegato().getIdStatoAllegato() != Constants.STATO_ALLEGATO_PROTOCOLLATO || cnmTVerbale.getNumeroProtocollo() == null) {
//				logger.info("Allegato master con id " + cnmTAllegatoMaster.getIdAllegato() + " non protocollato!");
//				return;
//			}

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
			
			// 20210805 PP - proseguo con la protocollazione del master del verbale
			CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
			ResponseProtocollaDocumento responseProtocollaEsistente = commonAllegatoService.avviaProtocollazioneDocumentoEsistente(cnmTAllegatoMaster, cnmTUser, null, false);
			
			// aggiorna num potocollo su tutti gli allegati e relativi cnmDocumento
			String numProtocollo = responseProtocollaEsistente.getProtocollo();
			for (CnmTAllegato cnmTAllegato : cnmTAllegatos) {
				if(cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato()!=Constants.STATO_ALLEGATO_NON_PROTOCOLLARE
						&& cnmTAllegato.getNumeroProtocollo() == null) {
					cnmTAllegato.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
					cnmTAllegato.setNumeroProtocollo(numProtocollo);
					cnmTAllegato.setDataOraProtocollo(now);
					cnmTAllegato.setCnmTUser1(cnmTUser);
					cnmTAllegato.setDataOraUpdate(now);
					allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegato);	// fa il semplice save
					CnmTDocumento cnmTDocumento = cnmTDocumentoRepository.findOne(Integer.parseInt(cnmTAllegato.getIdActa()));
					if (cnmTDocumento != null) {
						cnmTDocumento.setProtocolloFornitore(numProtocollo);
						cnmTDocumento.setCnmTUser1(cnmTUser);
						cnmTDocumento.setDataOraUpdate(now);
						cnmTDocumentoRepository.save(cnmTDocumento);
					}
				}
			}				
		}
	}


	private void manageComparsa(List<CnmTAllegato> allegati, CnmDStatoAllegato avvioSpostamentoActa) {

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		String operationToTrace = null;
		
		if (!(allegati == null || allegati.isEmpty())) {

			// aggiorno in spostamento
			allegati = allegatoScheduledService.updateCnmDStatoAllegatoInCoda(allegati);
			
			for (CnmTAllegato cnmTAllegato : allegati) {
				if (cnmTAllegato.getCnmDStatoAllegato().getIdStatoAllegato() == Constants.STATO_ALLEGATO_IN_CODA) {
					CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findByIdActa(cnmTAllegato.getIdActaMaster());
					CnmTAllegato cnmTAllegatoMaster = cnmTAllegatoRepository.findByIdActa(cnmTAllegato.getIdActaMaster());
					
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

					// 20210901 PP - CR_107
					// PROTOCOLLAZIONE ALLEGATO MASTER GIA ESISTENTE
					// se tutti i cnmTAllegatoList hanno idActa != null, idActaMaster!=null, e idActaMaster == idACta di cnmTAllegatoMasterIstanza
					// si fa la protocollazione
					if (protocollazioneDaAvviare(allegati, cnmTAllegatoMaster)) {				
						CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
						ResponseProtocollaDocumento responseProtocollaEsistente = commonAllegatoService.avviaProtocollazioneDocumentoEsistente(cnmTAllegatoMaster, cnmTUser, null, true);
						
						// aggiorna num potocollo su tutti gli allegati e relativi cnmDocumento
						String numProtocollo = responseProtocollaEsistente.getProtocollo();
						for (CnmTAllegato cnmTAllegatoUpdate : allegati) {
							if(cnmTAllegatoUpdate.getIdActaMaster().equalsIgnoreCase(cnmTAllegatoMaster.getIdActa())){
								cnmTAllegatoUpdate.setCnmDStatoAllegato(cnmDStatoAllegatoRepository.findOne(Constants.STATO_ALLEGATO_PROTOCOLLATO));
								cnmTAllegatoUpdate.setNumeroProtocollo(numProtocollo);
								cnmTAllegatoUpdate.setDataOraProtocollo(now);
								cnmTAllegatoUpdate.setCnmTUser1(cnmTUser);
								cnmTAllegatoUpdate.setDataOraUpdate(now);
								allegatoScheduledService.updateCnmDStatoAllegato(cnmTAllegatoUpdate);	// fa il semplice save
								CnmTDocumento cnmTDocumento = cnmTDocumentoRepository.findOne(Integer.parseInt(cnmTAllegatoUpdate.getIdActa()));
								if (cnmTDocumento != null) {
									cnmTDocumento.setProtocolloFornitore(numProtocollo);
									cnmTDocumento.setCnmTUser1(cnmTUser);
									cnmTDocumento.setDataOraUpdate(now);
									cnmTDocumentoRepository.save(cnmTDocumento);
								}
							}
						}				
					}
				}
			}
		}
	}
	

	private boolean protocollazioneDaAvviare(List<CnmTAllegato> allegati, CnmTAllegato master) {
		
		// se almeno uno degli allegati all'istanza ha idActa=null, idActaMaster=null, e idActa=!idActaDelMster, allora non parte la protocollazione
		
		if (allegati != null && !allegati.isEmpty()) {
			
			for(CnmTAllegato all : allegati) {
				if(!(all.getIdActaMaster() != null  && all.getIdActaMaster().length() != 0 && 
						all.getIdActa() != null && all.getIdActa().length() != 0 && 
						all.getIdActaMaster().equalsIgnoreCase(master.getIdActa()))) {
					return false;
				}
			}
			
		} else {
			return false;
		}

		return true;
	
	}	
	
	
	@SuppressWarnings("unused")
	private boolean masterProtocollabile(List<CnmRAllegatoVerbale> cnmRAllegatoVerbales, CnmRAllegatoVerbale cnmRAllegatoVerbaleMaster) {
		
		if(cnmRAllegatoVerbaleMaster!= null) {
			for(CnmRAllegatoVerbale cnmRAllegatoVerbale : cnmRAllegatoVerbales) {
				if(cnmRAllegatoVerbale.getCnmTAllegato().getIdActaMaster() != null && cnmRAllegatoVerbale.getCnmTAllegato().getIdActaMaster().equalsIgnoreCase(cnmRAllegatoVerbaleMaster.getCnmTAllegato().getIdActa())
						&& (cnmRAllegatoVerbale.getCnmTAllegato().getIdActa() == null || cnmRAllegatoVerbale.getCnmTAllegato().getIdActa().length() == 0)) {
					return false;
				}
			}
		}else {
			return false;
		}
		
		return true;
	}

	

	@Override
	public void manageSpostamento() {
		CnmTFruitore cnmTFruitore = null;
		CnmDTipoFornitore cnmDTipoFornitore = null;
		UtenteActa utenteActa = null;
		
		List<CnmTSpostamentoActa> cnmSpostamentoActaList = cnmTSpostamentoActaRepository.findByStatoIn(Arrays.asList(CnmTSpostamentoActa.STATO_ERRORE, CnmTSpostamentoActa.STATO_RICHIESTA_RICEVUTA));
		
		if (cnmSpostamentoActaList!=null && cnmSpostamentoActaList.size()>0) {

			try {
				cnmTFruitore = manageSpostaDocumentoHelper.getFruitore();
			} catch (FruitoreException e) {
				logger.error("Errore nel reperimento del cnmTFruitore", e);
				return;
			}

			cnmDTipoFornitore = cnmDTipoFornitoreRepository.findOne(DoquiConstants.FORNITORE_ACTA);
			
			utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
		}
		CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		for(CnmTSpostamentoActa cnmSpostamentoActa :cnmSpostamentoActaList ) {
			
			logger.info("Eseguo manageSpostamento per cnmSpostamentoActa:" + cnmSpostamentoActa);

			// setta stato intermedio
			cnmSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_INVIO_RICHIESTA);
			cnmTSpostamentoActaRepository.save(cnmSpostamentoActa);
			
			boolean operazioneCopia = cnmSpostamentoActa.getOperazione().equals(CnmTSpostamentoActa.OPERAZIONE_COPIA);
			
			try {				

				CnmTVerbale verbale = cnmTVerbaleRepository.findByIdVerbale(cnmSpostamentoActa.getIdVerbale());
				CnmTDocumento cnmTDocumentoMaster = cnmTDocumentoRepository.findOne(cnmSpostamentoActa.getIdDocumentoMaster());
				String root = utilsDoqui.getRootActa(verbale);
				utenteActa.setRootActa(root!=null?root:cnmTDocumentoMaster.getCnmDTipoDocumento().getRoot());
				
				utenteActa.setIdvitalrecordcodetype(cnmTDocumentoMaster.getCnmDTipoDocumento().getIdVitalRecordCodeType());
				utenteActa.setIdStatoDiEfficacia(cnmTDocumentoMaster.getCnmDTipoDocumento().getIdStatoEfficacia());
				
				DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
				documentoElettronicoActa.setIdDocumento(cnmTDocumentoMaster.getIdentificativoArchiviazione());
				documentoElettronicoActa.setFolder(cnmSpostamentoActa.getFolder());
				documentoElettronicoActa.setTipoStrutturaRoot(cnmTDocumentoMaster.getCnmDTipoDocumento().getCnmDStrutturaActaRoot().getIdStrutturaActa());		// 20200630_LC
				documentoElettronicoActa.setTipoStrutturaFolder(cnmTDocumentoMaster.getCnmDTipoDocumento().getCnmDStrutturaActaFolder().getIdStrutturaActa());	
				
				String soggettoActaFruitore = utilsDoqui.getSoggettoActa(verbale);
				
				// 20200730_LC
				if(StringUtils.isNotEmpty(soggettoActaFruitore))
					documentoElettronicoActa.setFruitore(soggettoActaFruitore);
				else
					documentoElettronicoActa.setFruitore(cnmTFruitore.getDescrFruitore());
				
				SoggettoActa soggettoActa = new SoggettoActa();
				
				soggettoActa.setMittente(true);
				documentoElettronicoActa.setAutore("CONAM");
				//TODO Da verificare la denominazione
				utenteActa.setDescFormaDocumentaria(cnmTDocumentoMaster.getCnmDTipoDocumento().getDescFormadocEntrata());
				
				utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
				soggettoActa.setCognome("CONAM");
				soggettoActa.setNome("CONAM");
				documentoElettronicoActa.setSoggettoActa(soggettoActa);

				CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findByIdVerbale(cnmSpostamentoActa.getIdVerbale());
				
				ResponseSpostaDocumento response = new ResponseSpostaDocumento();
				if(operazioneCopia) {
					manageSpostaDocumentoHelper.manageCopia(response, documentoElettronicoActa, cnmSpostamentoActa, utenteActa, cnmSpostamentoActa.getParolaChiaveTemp(), cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1);
				}else {
					manageSpostaDocumentoHelper.manageSposta(response, documentoElettronicoActa, cnmSpostamentoActa, utenteActa, cnmSpostamentoActa.getParolaChiaveTemp(), cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1);
				}
				manageSpostaDocumentoHelper.checkUpdateStatusVerbale(cnmSpostamentoActa, cnmTUser);
				
			} catch (Exception e) {
				logger.error("Errore in elaborazione manageSpostamento:" + cnmSpostamentoActa, e);
				cnmSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_ERRORE);
				cnmSpostamentoActa.setInfo(e.getMessage());
				cnmTSpostamentoActaRepository.save(cnmSpostamentoActa);
			}
		}
		
	}
	
	@Override
	public void checkSpostamento() {
		
		CnmTUser cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		
		List<CnmTSpostamentoActa> cnmSpostamentoActaList = cnmTSpostamentoActaRepository.findByStato(CnmTSpostamentoActa.STATO_IN_CORSO);

		for(CnmTSpostamentoActa cnmSpostamentoActa :cnmSpostamentoActaList ) {
			
			// setta stato intermedio
			cnmSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_AGGIORNAMENTO_DATI);
			cnmTSpostamentoActaRepository.save(cnmSpostamentoActa);

			logger.info("Eseguo check per cnmSpostamentoActa:" + cnmSpostamentoActa);

			boolean operazioneCopia = cnmSpostamentoActa.getOperazione().equals(CnmTSpostamentoActa.OPERAZIONE_COPIA);
			
			// master dle protocollo spostato (gia creato su db)
			CnmTDocumento cnmTDocumentoMaster = cnmTDocumentoRepository.findOne(cnmSpostamentoActa.getIdDocumentoMaster());
			
			try {
				
				// torna lo stato dell'operazione (descStato)
				String moveResult = manageRicercaDocumentoHelper.recuperaInfoMoveDocument(cnmSpostamentoActa.getPrenotazioneId());
				
				if(moveResult != null && moveResult.equals("OK")) {
					
					CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findByIdVerbale(cnmSpostamentoActa.getIdVerbale());
					
					ResponseSpostaDocumento response = new ResponseSpostaDocumento();
					response.setObjectIdDocumentoToTraceList(new ArrayList<String>());
					manageSpostaDocumentoHelper.updateInfoPostSpostaCopia(cnmSpostamentoActa, response, operazioneCopia);
					manageSpostaDocumentoHelper.tracciaSuCsiLogAudit(operazioneCopia, cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1, response.getObjectIdDocumentoToTraceList());
					logger.info("Operazione terminata; chiamata a servizi acaris per aggiornamento info su DB");	
					cnmSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_EVASO);
					cnmSpostamentoActa.setDataOraEnd(utilsDate.asTimeStamp(LocalDateTime.now()));
					cnmSpostamentoActa.setInfo("Operazione " + cnmSpostamentoActa.getOperazione().toLowerCase() + " effettuata e dati aggiornati");
					cnmTSpostamentoActaRepository.save(cnmSpostamentoActa);
					
					manageSpostaDocumentoHelper.checkUpdateStatusVerbale(cnmSpostamentoActa, cnmTUser);
					
				} else {					
					logger.info("Operazione non ancora terminata");					
					cnmSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_IN_CORSO);
					cnmSpostamentoActa.setInfo("Richiesta " + cnmSpostamentoActa.getOperazione().toLowerCase() + " inviata");
					cnmTSpostamentoActaRepository.save(cnmSpostamentoActa);

				}
			} catch (RicercaDocumentoException e1) {
				logger.warn("Problema in ricerca protocollo per cnmSpostamentoActa:" + cnmSpostamentoActa, e1);
				cnmSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_IN_CORSO);
				cnmSpostamentoActa.setInfo(e1.getMessage());
				cnmTSpostamentoActaRepository.save(cnmSpostamentoActa);
			} catch (Exception e) {
				logger.error("Errore in elaborazione cnmSpostamentoActa:" + cnmSpostamentoActa, e);
				cnmSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_IN_CORSO);
				cnmSpostamentoActa.setInfo("Errore in elaborazione cnmSpostamentoActa");
				cnmTSpostamentoActaRepository.save(cnmSpostamentoActa);
			}
		}
		
	}


	private RequestSpostaDocumento buildRequestForUpdateInfo(CnmTSpostamentoActa cnmSpostamentoActa) {
		RequestSpostaDocumento req = new RequestSpostaDocumento();
		req.setIdVerbale(cnmSpostamentoActa.getIdVerbale());
		req.setNumeroProtocollo(cnmSpostamentoActa.getNumeroProtocollo());
		req.setCodiceFruitore(DoquiConstants.CODICE_FRUITORE);
		req.setFolder(cnmSpostamentoActa.getFolder());
		return req;
	}
	
	
	
	
}
