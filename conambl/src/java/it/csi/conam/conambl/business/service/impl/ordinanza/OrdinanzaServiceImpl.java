/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.ordinanza.OrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.business.service.verbale.StoricizzazioneVerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.TipoProtocolloAllegato;
import it.csi.conam.conambl.common.exception.BusinessException;
import it.csi.conam.conambl.integration.entity.CnmDCausale;
import it.csi.conam.conambl.integration.entity.CnmDStatoPianoRate;
import it.csi.conam.conambl.integration.entity.CnmDStatoRata;
import it.csi.conam.conambl.integration.entity.CnmDStatoSollecito;
import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmDTipoOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoOrdinanzaPK;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaFiglio;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaFiglioPK;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRSoggRata;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmSStatoOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTAcconto;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTAllegatoField;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTPianoRate;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.entity.OrdinanzaEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDCausaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoOrdVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoPianoRateRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoRataRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoSollecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmDTipoOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoOrdVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRAllegatoOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaFiglioRepository;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRSoggRataRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmSStatoOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoFieldRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import it.csi.conam.conambl.integration.repositories.CnmTSollecitoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.request.ordinanza.SalvaOrdinanzaAnnullamentoRequest;
import it.csi.conam.conambl.request.ordinanza.SalvaOrdinanzaRequest;
import it.csi.conam.conambl.request.ordinanza.SoggettoOrdinanzaRequest;
import it.csi.conam.conambl.response.DatiSentenzaResponse;
import it.csi.conam.conambl.response.ImportoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UploadUtils;
import it.csi.conam.conambl.util.UtilsFieldAllegato;
import it.csi.conam.conambl.util.UtilsTipoAllegato;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.OrdinanzaVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoFieldVO;

@Service
public class OrdinanzaServiceImpl implements OrdinanzaService {

	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;
	@Autowired
	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;

	@Autowired
	private CnmDStatoOrdinanzaRepository cnmDStatoOrdinanzaRepository;
	@Autowired
	private CnmDTipoOrdinanzaRepository cnmDTipoOrdinanzaRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmDStatoOrdVerbSogRepository cnmDStatoOrdVerbSogRepository;

	@Autowired
	private OrdinanzaEntityMapper ordinanzaEntityMapper;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmRAllegatoOrdinanzaRepository cnmRAllegatoOrdinanzaRepository;
	@Autowired
	private UtilsOrdinanza utilsOrdinanza;
	@Autowired
	private CnmTAllegatoFieldRepository cnmTAllegatoFieldRepository;
	@Autowired
	private CnmRAllegatoOrdVerbSogRepository cnmRAllegatoOrdVerbSogRepository;
	@Autowired
	private CnmSStatoOrdinanzaRepository cnmSStatoOrdinanzaRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private StoricizzazioneVerbaleService storicizzazioneVerbaleService;
	@Autowired
	private NotificaService notificaService;
	@Autowired
	private UtilsDoqui utilsDoqui;
	
	@Autowired
	private CnmRSoggRataRepository cnmRSoggRataRepository;
	@Autowired
	private CnmTSollecitoRepository cnmTSollecitoRepository;
	@Autowired
	private CnmDStatoSollecitoRepository cnmDStatoSollecitoRepository;
	@Autowired
	private CnmDStatoRataRepository cnmDStatoRataRepository;
	@Autowired
	private CnmDStatoPianoRateRepository cnmDStatoPianoRateRepository;
	/*@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;*/

	@Autowired
	private CnmROrdinanzaFiglioRepository cnmROrdinanzaFiglioRepository;

	@Autowired
	private CnmDCausaleRepository cnmDCausaleRepository;

	//	Issue 3 - Sonarqube
	@Override
	@Transactional
	public void testIfOrdinanzaPagata(Integer idOrdinanza, UserDetails userDetails) {
		testIfOrdinanzaPagata(cnmTOrdinanzaRepository.findOne(idOrdinanza), userDetails);
	}

	@Override
	public void testIfOrdinanzaPagata(CnmTOrdinanza cnmTOrdinanza, UserDetails userDetails) {
		Double importoPagato = 0D;
		for (CnmTAcconto acconto: cnmTOrdinanza.getCnmTAccontos())
			importoPagato = importoPagato + acconto.getImporto().doubleValue();
		
		// 20210707_LC Jira 150: importo totale da pagare compreso di spese notifica
		OrdinanzaVO ordinanzaVO = ordinanzaEntityMapper.mapEntityToFullVO(cnmTOrdinanza);
		if (importoPagato >= ordinanzaVO.getImportoTotaleDaPagare().doubleValue())
			cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_PAGATA));
		cnmTOrdinanzaRepository.save(cnmTOrdinanza);
	}

	//ID_STATO_ORDINANZA_PAGATA
	// gestire eventuale condizione di sicurezza
	@Override
	public OrdinanzaVO dettaglioOrdinanzaById(Integer idOrdinanza, UserDetails userDetails) {
		CnmTOrdinanza cnmTOrdinanza = utilsOrdinanza.validateAndGetCnmTOrdinanza(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza==null");

		return ordinanzaEntityMapper.mapEntityToFullVO(cnmTOrdinanza);
	}

	@Override
	public OrdinanzaVO dettaglioOrdinanzaByIdOrdinanzaSoggetti(List<Integer> idOrdinanzaSoggetti, UserDetails utente) {
		if (idOrdinanzaSoggetti == null)
			throw new IllegalArgumentException("idOrdinanzaSoggetti==null");
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = (List<CnmROrdinanzaVerbSog>) cnmROrdinanzaVerbSogRepository.findAll(idOrdinanzaSoggetti);
		if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.isEmpty())
			throw new IllegalArgumentException("cnmROrdinanzaVerbSoggetti==null");

		CnmTOrdinanza cnmTOrdinanza = cnmROrdinanzaVerbSogList.get(0).getCnmTOrdinanza();
		Collection<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogListFilterd = Collections2.filter(cnmROrdinanzaVerbSogList, new Predicate<CnmROrdinanzaVerbSog>() {
			@Override
			public boolean apply(CnmROrdinanzaVerbSog input) {
				return !input.getCnmTOrdinanza().getIdOrdinanza().equals(cnmTOrdinanza.getIdOrdinanza());
			}
		});
		
		if (cnmROrdinanzaVerbSogListFilterd.size() != 0)
			throw new SecurityException("idOrdinanzaSoggetti non appartengono alla stessa ordinanza");
		return ordinanzaEntityMapper.mapEntityToFullVO(cnmTOrdinanza);
	}

	@Override
	@Transactional
	public Integer salvaOrdinanza(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		Long idUser = userDetails.getIdUser();
		SalvaOrdinanzaRequest request = commonAllegatoService.getRequest(data, file, SalvaOrdinanzaRequest.class);
		byte[] byteFile = request.getFile();
		String fileName = request.getFilename();
		Long idTipoAllegato = request.getIdTipoAllegato();
		List<AllegatoFieldVO> configAllegato = request.getAllegatoField();
		MinOrdinanzaVO ordinanza = request.getOrdinanza();
		List<SoggettoOrdinanzaRequest> soggetti = request.getSoggetti();
		NotificaVO notifica = request.getNotifica();
		
		//  TEST VALUE - TO REMOVE
//		List<SelectVO> causales = getCausaleSelect();
//		ordinanza.setCausale(causales.get(0));
//		ordinanza.setAnnoAccertamento(2023L);
//		ordinanza.setNumeroAccertamento("AAAAA1");
//		
//		notifica.setCausale(causales.get(1));
//		notifica.setAnnoAccertamento(2023L);
//		notifica.setNumeroAccertamento("AAAAA2");
		
		// controlli di sicurezza
		if (soggetti == null)
			throw new IllegalArgumentException("soggetti=null");
		if (ordinanza == null)
			throw new IllegalArgumentException("ordinanza=null");

		UploadUtils.checkDimensioneAllegato(byteFile);

		// 20201026 PP- controllo se e' stato caricato un file firmato , con firma non valida senza firma
		utilsDoqui.checkFileSign(byteFile, fileName);
		
		List<Integer> idVerbaleSoggettoList = new ArrayList<>();
		for (SoggettoOrdinanzaRequest s : soggetti)
			idVerbaleSoggettoList.add(s.getIdVerbaleSoggetto());

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettoList == null || cnmRVerbaleSoggettoList.isEmpty())
			throw new IllegalArgumentException("soggetti non trovati");

		/*---------2021-12-15 - LUCIO ROSADINI - CONTROLLO CODICE FISCALE---------*/
		if (idTipoAllegato.equals(TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId())) {
			utilsOrdinanza.isCFValid(cnmRVerbaleSoggettoList);
		}

		CnmTVerbale cnmTVerbale = cnmRVerbaleSoggettoList.get(0).getCnmTVerbale();

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
		/*if (cnmROrdinanzaVerbSogList != null && !cnmROrdinanzaVerbSogList.isEmpty())
			throw new SecurityException("ordinanza gia esistente per i soggetti passati in input");*/

		if (ordinanza.getNumDeterminazione() == null)
			throw new IllegalArgumentException("numero determinazione non valorizzato");
		
		
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findByNumDeterminazione(ordinanza.getNumDeterminazione().toUpperCase());
		if (cnmTOrdinanza != null)
			throw new BusinessException(ErrorCode.INSERIMENTO_ORDINANZA_NUMERO_DETERMINAZIONE_ESISTENTE);
		// fine controlli

		CnmDTipoOrdinanza cnmDTipoOrdinanza;
		Long idTipoOrdinanza;
		Long idTipoOrdinanzaSoggettoDefault;

		// non sarà mai ordinanza annullamento (api apposita)
		if (idTipoAllegato.equals(TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId())) {
			
			// 20230214 PP - nuovi campi per modifica integrazione PPAY
			if (ordinanza.getCausale() == null)
				throw new IllegalArgumentException("causale ordinanza non valorizzata");

			if (ordinanza.getNumeroAccertamento() == null)
				throw new IllegalArgumentException("numero accertamento ordinanza non valorizzato");

			if (ordinanza.getAnnoAccertamento() == null)
				throw new IllegalArgumentException("anno accertamento ordinanza non valorizzato");
			
			
			
			if(notifica!= null) {
				
				// TODO - da eliminare
//				System.out.println("diff importo=" + notifica.getImportoSpeseNotifica().compareTo(new BigDecimal(0L)));
				
				// 20230519 PP - CR abb 167 (issue 5)
				// controllo per chrome, dove si può inserire un importo negativo
				if(notifica.getImportoSpeseNotifica().compareTo(new BigDecimal(0L))<0) {
					throw new BusinessException(ErrorCode.INSERIMENTO_ORDINANZA_NOTIFICA_DATI_MANCANTI_IMP_NEG);
				}
				
				// resi opzionali i dati Importo spese notifica - Causale - Numero accertamento - Anno accertamento. Se solo uno di questi è valorizzato, sono necessari anche gli altri dati
				if ((notifica.getCausale() != null || notifica.getNumeroAccertamento() != null || notifica.getAnnoAccertamento() != null || (notifica.getImportoSpeseNotifica() != null && notifica.getImportoSpeseNotifica().compareTo(new BigDecimal(0L))>0))
						&&(notifica.getCausale() == null || notifica.getNumeroAccertamento() == null || notifica.getAnnoAccertamento() == null || (notifica.getImportoSpeseNotifica() == null || notifica.getImportoSpeseNotifica().compareTo(new BigDecimal(0L))==0))) {
					throw new BusinessException(ErrorCode.INSERIMENTO_ORDINANZA_NOTIFICA_DATI_MANCANTI);
				}
//				
//				if (notifica.getCausale() == null)
//					throw new IllegalArgumentException("causale notifica non valorizzata");
//
//				if (notifica.getNumeroAccertamento() == null)
//					throw new IllegalArgumentException("numero accertamento notifica non valorizzato");
//
//				if (notifica.getAnnoAccertamento() == null)
//					throw new IllegalArgumentException("anno accertamento notifica non valorizzato");
			}			
			
			idTipoOrdinanza = Constants.ID_TIPO_ORDINANZA_INGIUNZIONE;
			idTipoOrdinanzaSoggettoDefault = Constants.ID_STATO_ORDINANZA_SOGGETTO_INGIUNZIONE;
		} else if (idTipoAllegato.equals(TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId())) {
			idTipoOrdinanza = Constants.ID_TIPO_ORDINANZA_ARCHIVIATO;
			idTipoOrdinanzaSoggettoDefault = Constants.ID_STATO_ORDINANZA_SOGGETTO_ARCHIVIATO;
		} else
			throw new IllegalArgumentException("stato ordinanza non valorizzato ");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);
		Timestamp now = new Timestamp(new Date().getTime());

		// salvo ordinanza
		cnmTOrdinanza = ordinanzaEntityMapper.mapVOtoEntity(ordinanza);
		cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_IN_ATTESA_DI_NOTIFICA));
		cnmDTipoOrdinanza = cnmDTipoOrdinanzaRepository.findOne(idTipoOrdinanza);
		cnmTOrdinanza.setCnmDTipoOrdinanza(cnmDTipoOrdinanza);
		cnmTOrdinanza.setCnmTUser2(cnmTUser);
		cnmTOrdinanza.setDataOraInsert(now);
		cnmTOrdinanza = cnmTOrdinanzaRepository.save(cnmTOrdinanza);

		// salvo allegato
		CnmTAllegato cnmTAllegato = commonAllegatoService.salvaAllegato(
			byteFile,
			fileName,
			idTipoAllegato,
			configAllegato,
			cnmTUser,
			TipoProtocolloAllegato.DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO,
			null,
			null,
			false,
			true,
			null,
			null,
			0,
			null,
			null,
			null
		);

		// salvo relazione allegato ordinanza
		CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza = new CnmRAllegatoOrdinanza();
		CnmRAllegatoOrdinanzaPK cnmRAllegatoOrdinanzaPK = new CnmRAllegatoOrdinanzaPK();
		cnmRAllegatoOrdinanzaPK.setIdAllegato(cnmTAllegato.getIdAllegato());
		cnmRAllegatoOrdinanzaPK.setIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());
		cnmRAllegatoOrdinanza.setCnmTUser(cnmTUser);
		cnmRAllegatoOrdinanza.setDataOraInsert(now);
		cnmRAllegatoOrdinanza.setId(cnmRAllegatoOrdinanzaPK);
		cnmRAllegatoOrdinanzaRepository.save(cnmRAllegatoOrdinanza);

		// salvo la posizione dei soggetti
		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
			SoggettoOrdinanzaRequest soggettoOrdinanzaRequest = Iterables.tryFind(soggetti, new Predicate<SoggettoOrdinanzaRequest>() {
				@Override
				public boolean apply(SoggettoOrdinanzaRequest input) {
					return input.getIdVerbaleSoggetto().equals(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());
				}
			}).orNull();
			Long stato = soggettoOrdinanzaRequest != null && soggettoOrdinanzaRequest.getIdTipoOrdinanza() != null ? soggettoOrdinanzaRequest.getIdTipoOrdinanza() : idTipoOrdinanzaSoggettoDefault;
			CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = new CnmROrdinanzaVerbSog();
			cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSogRepository.findOne(stato));
			cnmROrdinanzaVerbSog.setCnmRVerbaleSoggetto(cnmRVerbaleSoggetto);
			cnmROrdinanzaVerbSog.setCnmTOrdinanza(cnmTOrdinanza);
			cnmROrdinanzaVerbSog.setCnmTUser2(cnmTUser);
			cnmROrdinanzaVerbSog.setDataOraInsert(now);
			cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);
		}

		// se tutti i soggetti hanno ordinanza aggiorna stato verbale
		//ob-181 modifica aggiornamento stato verbale se almeno un ordinanza per soggetto
		cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		//cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
		int numROrdinanzaVerbSog = 0;
		for(CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
			if(cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggetto(cnmRVerbaleSoggetto).size() <= 0) {
				break;
			}
			numROrdinanzaVerbSog++;
		}
		if (cnmRVerbaleSoggettoList.size() == numROrdinanzaVerbSog) {
			storicizzazioneVerbaleService.storicizzaStatoVerbale(cnmTVerbale, cnmTUser);
			CnmDStatoVerbale cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ORDINANZA);
			cnmTVerbale.setCnmDStatoVerbale(cnmDStatoVerbale);
			cnmTVerbaleRepository.save(cnmTVerbale);
		}		

		//Save della notifca se presente	-	20210415 se != null
		if(notifica != null) {
			notifica.setIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());
			notificaService.salvaNotifica(notifica, userDetails);
		}
		

		return cnmTOrdinanza.getIdOrdinanza();
	}


	@Override
	public DatiSentenzaResponse getDatiSentenzaByIdOrdinanzaVerbaleSoggetto(Integer idOrdinanzaVerbaleSoggetto) {
		if (idOrdinanzaVerbaleSoggetto == null)
			throw new IllegalArgumentException("id ordinanza soggetto = null");

		DatiSentenzaResponse datiSentenzaResponse = new DatiSentenzaResponse();
		datiSentenzaResponse.setImportoSpeseProcessuali(BigDecimal.ZERO);
		datiSentenzaResponse.setImportoTitoloSanzione(BigDecimal.ZERO);

		CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findOne(idOrdinanzaVerbaleSoggetto);
		if (cnmROrdinanzaVerbSog == null)
			throw new IllegalArgumentException("cnmROrdinanzaVerbSog = null");

		List<CnmRAllegatoOrdVerbSog> cnmRAllegatoOrdVerbSogList = cnmRAllegatoOrdVerbSogRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSog);
		if (cnmRAllegatoOrdVerbSogList.isEmpty())
			return datiSentenzaResponse;

		CnmRAllegatoOrdVerbSog cnmRAllegatoOrdVerbSog = Iterables
				.tryFind(cnmRAllegatoOrdVerbSogList, UtilsTipoAllegato.findCnmRAllegatoOrdVerbSogInCnmRAllegatoOrdVerbSogsByTipoAllegato(TipoAllegato.DISPOSIZIONE_DEL_GIUDICE)).orNull();
		if (cnmRAllegatoOrdVerbSog == null)
			return datiSentenzaResponse;

		List<CnmTAllegatoField> field = cnmTAllegatoFieldRepository.findByCnmTAllegato(cnmRAllegatoOrdVerbSog.getCnmTAllegato());

		CnmTAllegatoField importoSanzioneSentenza = Iterables.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_IMPORTO_SANZIONE_SENTENZA))
				.orNull();

		CnmTAllegatoField importoSpeseProcessuali = Iterables
				.tryFind(field, UtilsFieldAllegato.findCnmTAllegatoFieldInCnmTAllegatoFieldsByTipoAllegato(Constants.ID_FIELD_IMPORTO_SPESE_PROCESSUALI_SENTENZA)).orNull();

		datiSentenzaResponse.setImportoSpeseProcessuali(importoSpeseProcessuali != null ? importoSpeseProcessuali.getValoreNumber() : BigDecimal.ZERO);
		datiSentenzaResponse.setImportoTitoloSanzione(importoSanzioneSentenza != null ? importoSanzioneSentenza.getValoreNumber() : BigDecimal.ZERO);

		return datiSentenzaResponse;
	}

	@Override
	public ImportoResponse getImportoOrdinanzaByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog dto) {
		ImportoResponse importoPianoResponse = new ImportoResponse();

		importoPianoResponse.setImportoDaPagare(dto.getCnmTOrdinanza().getImportoOrdinanza());
		importoPianoResponse.setImportoPagato(dto.getImportoPagato() != null ? dto.getImportoPagato() : BigDecimal.ZERO);

		return importoPianoResponse;
	}

	@Override
	public void saveSStatoOrdinanza(CnmTOrdinanza cnmTOrdinanza, CnmTUser cnmTUser) {
		CnmSStatoOrdinanza cnmSStatoOrdinanza = new CnmSStatoOrdinanza();
		cnmSStatoOrdinanza.setCnmTOrdinanza(cnmTOrdinanza);
		cnmSStatoOrdinanza.setCnmDStatoOrdinanza(cnmTOrdinanza.getCnmDStatoOrdinanza());
		cnmSStatoOrdinanza.setCnmTUser(cnmTUser);
		cnmSStatoOrdinanza.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmSStatoOrdinanzaRepository.save(cnmSStatoOrdinanza);
	}

	
	
	// 20210304_LC lotto2scenario7
	@Override
	@Transactional
	public Integer salvaOrdinanzaAnnullamento(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		Long idUser = userDetails.getIdUser();
		SalvaOrdinanzaAnnullamentoRequest request = commonAllegatoService.getRequest(data, file, SalvaOrdinanzaAnnullamentoRequest.class);
		byte[] byteFile = request.getFile();
		String fileName = request.getFilename();
		Long idTipoAllegato = request.getIdTipoAllegato();
		List<AllegatoFieldVO> configAllegato = request.getAllegatoField();
		MinOrdinanzaVO ordinanza = request.getOrdinanza();
		List<SoggettoOrdinanzaRequest> soggetti = request.getSoggetti();
		NotificaVO notifica = request.getNotifica();
		

		// ------------------------------
		// controlli di sicurezza
		if (soggetti == null)
			throw new IllegalArgumentException("soggetti=null");
		if (ordinanza == null)
			throw new IllegalArgumentException("ordinanza=null");

		UploadUtils.checkDimensioneAllegato(byteFile);

		// 20201026 PP- controllo se e' stato caricato un file firmato , con firma non valida senza firma
		utilsDoqui.checkFileSign(byteFile, fileName);
		
		List<Integer> idVerbaleSoggettoList = new ArrayList<>();
		for (SoggettoOrdinanzaRequest s : soggetti)
			idVerbaleSoggettoList.add(s.getIdVerbaleSoggetto());

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettoList == null || cnmRVerbaleSoggettoList.isEmpty())
			throw new IllegalArgumentException("soggetti non trovati");

		/*---------2021-12-15 - LUCIO ROSADINI - CONTROLLO CODICE FISCALE---------*/
		if (idTipoAllegato.equals(TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId())) {
			utilsOrdinanza.isCFValid(cnmRVerbaleSoggettoList);
		}

		@SuppressWarnings("unused")
		CnmTVerbale cnmTVerbale = cnmRVerbaleSoggettoList.get(0).getCnmTVerbale();

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
		// controllo invertito rispetto al caso standard, questi soggetti devono già avere un'ordinanza
		if (cnmROrdinanzaVerbSogList == null || cnmROrdinanzaVerbSogList.isEmpty())
			throw new SecurityException("nessuna ordinanza trovata per i soggetti in input");

		if (ordinanza.getNumDeterminazione() == null)
			throw new IllegalArgumentException("numero determinazione non valorizzato");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findByNumDeterminazione(ordinanza.getNumDeterminazione().toUpperCase());
		if (cnmTOrdinanza != null)
			throw new BusinessException(ErrorCode.INSERIMENTO_ORDINANZA_NUMERO_DETERMINAZIONE_ESISTENTE);
		// fine controlli
		// ------------------------------

		
		
		
		
		CnmDTipoOrdinanza cnmDTipoOrdinanza;
		Long idTipoOrdinanza;
		Long idTipoOrdinanzaSoggettoDefault;

		// creazione nuova ordinanzaAnnullamento
		if (idTipoAllegato.equals(TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId())) {
			idTipoOrdinanza = Constants.ID_TIPO_ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE;
			idTipoOrdinanzaSoggettoDefault = Constants.ID_STATO_ORDINANZA_SOGGETTO_ARCHIVIATO;
		} else if (idTipoAllegato.equals(TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId())) {
			idTipoOrdinanza = Constants.ID_TIPO_ORDINANZA_ANNULLAMENTO_INGIUNZIONE;
			idTipoOrdinanzaSoggettoDefault = Constants.ID_STATO_ORDINANZA_SOGGETTO_INGIUNZIONE;
		} else
			throw new IllegalArgumentException("stato ordinanza non valorizzato ");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(idUser);
		Timestamp now = new Timestamp(new Date().getTime());

		


		// salva ordinanzaAnnullamento
		cnmTOrdinanza = ordinanzaEntityMapper.mapVOtoEntity(ordinanza);
		cnmTOrdinanza.setCnmDStatoOrdinanza(cnmDStatoOrdinanzaRepository.findOne(Constants.ID_STATO_ORDINANZA_IN_ATTESA_DI_NOTIFICA));
		cnmDTipoOrdinanza = cnmDTipoOrdinanzaRepository.findOne(idTipoOrdinanza);
		cnmTOrdinanza.setCnmDTipoOrdinanza(cnmDTipoOrdinanza);
		cnmTOrdinanza.setCnmTUser2(cnmTUser);
		cnmTOrdinanza.setDataOraInsert(now);
		cnmTOrdinanza = cnmTOrdinanzaRepository.save(cnmTOrdinanza);

		
		// salva cnm_r_ordinanza_figlio (l'ordinanza di annullamento è il figlio)
		CnmROrdinanzaFiglio cnmROrdinanzaFiglio = new CnmROrdinanzaFiglio();
		CnmROrdinanzaFiglioPK cnmROrdinanzaFiglioPK = new CnmROrdinanzaFiglioPK();
		cnmROrdinanzaFiglioPK.setIdOrdinanza(Integer.valueOf(request.getIdOrdinanzaDaAnnullare()));
		cnmROrdinanzaFiglioPK.setIdOrdinanzaFiglio(cnmTOrdinanza.getIdOrdinanza());
		cnmROrdinanzaFiglio.setId(cnmROrdinanzaFiglioPK);
		cnmROrdinanzaFiglioRepository.save(cnmROrdinanzaFiglio);
		
		
		
		// salva allegato ordinanzaAnnullamento
		CnmTAllegato cnmTAllegato = commonAllegatoService.salvaAllegato(byteFile, fileName, idTipoAllegato, configAllegato, cnmTUser, TipoProtocolloAllegato.DA_PROTOCOLLARE_IN_ISTANTE_SUCCESSIVO,
				null, null, false, true, null, null, 0, null, null, null);

		
		
		// salva relazione allegato-ordinanza per la nuova ordinanzaAnnullamento
		CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza = new CnmRAllegatoOrdinanza();
		CnmRAllegatoOrdinanzaPK cnmRAllegatoOrdinanzaPK = new CnmRAllegatoOrdinanzaPK();
		cnmRAllegatoOrdinanzaPK.setIdAllegato(cnmTAllegato.getIdAllegato());
		cnmRAllegatoOrdinanzaPK.setIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());
		cnmRAllegatoOrdinanza.setCnmTUser(cnmTUser);
		cnmRAllegatoOrdinanza.setDataOraInsert(now);
		cnmRAllegatoOrdinanza.setId(cnmRAllegatoOrdinanzaPK);
		cnmRAllegatoOrdinanzaRepository.save(cnmRAllegatoOrdinanza);

		
		
		// salva relazione ordinanza-verbale-soggetto per la nuova ordinanzaAnnullamento
		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
			SoggettoOrdinanzaRequest soggettoOrdinanzaRequest = Iterables.tryFind(soggetti, new Predicate<SoggettoOrdinanzaRequest>() {
				@Override
				public boolean apply(SoggettoOrdinanzaRequest input) {
					return input.getIdVerbaleSoggetto().equals(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());
				}
			}).orNull();
			Long stato = soggettoOrdinanzaRequest != null && soggettoOrdinanzaRequest.getIdTipoOrdinanza() != null ? soggettoOrdinanzaRequest.getIdTipoOrdinanza() : idTipoOrdinanzaSoggettoDefault;
			CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog = new CnmROrdinanzaVerbSog();
			cnmROrdinanzaVerbSog.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSogRepository.findOne(stato));
			cnmROrdinanzaVerbSog.setCnmRVerbaleSoggetto(cnmRVerbaleSoggetto);
			cnmROrdinanzaVerbSog.setCnmTOrdinanza(cnmTOrdinanza);
			cnmROrdinanzaVerbSog.setCnmTUser2(cnmTUser);
			cnmROrdinanzaVerbSog.setDataOraInsert(now);
			cnmROrdinanzaVerbSogRepository.save(cnmROrdinanzaVerbSog);
		}

		

		
		// operazioni sull'ordinanza annullata
		CnmTOrdinanza cnmTOrdinanzaDaAnnullare = cnmTOrdinanzaRepository.findOne(Integer.valueOf(request.getIdOrdinanzaDaAnnullare()));
		if (cnmTOrdinanzaDaAnnullare == null)
			throw new SecurityException("ordinanza da annullare non trovata");
		
		CnmDStatoSollecito cnmDStatoSollecitoEstinto = cnmDStatoSollecitoRepository.findOne(Constants.ID_STATO_SOLLECITO_ESTINTO);
		CnmDStatoRata cnmDStatoRataEstinto = cnmDStatoRataRepository.findOne(Constants.ID_STATO_RATA_ESTINTO);
		CnmDStatoPianoRate statoPianoRateEstinto = cnmDStatoPianoRateRepository.findOne(Constants.ID_STATO_PIANO_ESTINTO);
		boolean pagamentiDaEstinguere = false;
		
		for (CnmROrdinanzaVerbSog  cnmROrdinanzaVerbSogToUpdate : cnmROrdinanzaVerbSogList) {
			if (cnmROrdinanzaVerbSogToUpdate.getCnmTOrdinanza().equals(cnmTOrdinanzaDaAnnullare)) {				

				// aggiornamenti a ESTINTO solo se posizione passa da INGIUNZIONE a ARCHIVIATO
				if (cnmROrdinanzaVerbSogToUpdate.getCnmDStatoOrdVerbSog().getIdStatoOrdVerbSog() == Constants.ID_STATO_ORDINANZA_SOGGETTO_INGIUNZIONE
						&& idTipoOrdinanzaSoggettoDefault == Constants.ID_STATO_ORDINANZA_SOGGETTO_ARCHIVIATO) {
					pagamentiDaEstinguere = true;
				}
							
				// aggiorna stato ordinanza annullata
				cnmROrdinanzaVerbSogToUpdate.setCnmDStatoOrdVerbSog(cnmDStatoOrdVerbSogRepository.findOne(idTipoOrdinanzaSoggettoDefault));
				
				if (pagamentiDaEstinguere) {
					
					// aggiorna stato sollecito a ESTINTO
					List<CnmTSollecito> cnmTSollecitos = cnmTSollecitoRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSogToUpdate);
					for (CnmTSollecito soll : cnmTSollecitos) {
						soll.setCnmDStatoSollecito(cnmDStatoSollecitoEstinto);
					}
					
					// aggiorna rate a ESTINTO
					List<CnmRSoggRata> cnmRSoggRatas = cnmRSoggRataRepository.findByCnmROrdinanzaVerbSog(cnmROrdinanzaVerbSogToUpdate);
					for (CnmRSoggRata rata : cnmRSoggRatas) {
						rata.setCnmDStatoRata(cnmDStatoRataEstinto);					
					}
					
					// aggiorna piano a ESTINTO
					if (cnmRSoggRatas != null && !cnmRSoggRatas.isEmpty()) {
						CnmTPianoRate cnmTPianoRate = cnmRSoggRatas.get(0).getCnmTRata().getCnmTPianoRate();
						if (cnmTPianoRate != null)
							cnmTPianoRate.setCnmDStatoPianoRate(statoPianoRateEstinto);	
					}
				
				}
		
			}
					
		}		
		
		
		

		// le ordinanze di annullamento esistono solo per soggetti con già unìordinanza, per cui questa parte non serve / è ininfluente		
		// se tutti i soggetti hanno ordinanza aggiorna stato verbale
//		cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
//		cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
//		if (cnmRVerbaleSoggettoList.size() == cnmROrdinanzaVerbSogList.size()) {
//			storicizzazioneVerbaleService.storicizzaStatoVerbale(cnmTVerbale, cnmTUser);
//			CnmDStatoVerbale cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ORDINANZA);
//			cnmTVerbale.setCnmDStatoVerbale(cnmDStatoVerbale);
//			cnmTVerbaleRepository.save(cnmTVerbale);
//		}				
		
		
		// salva notifca se presente	-	20210415 se != null
		if(notifica != null) {
			notifica.setIdOrdinanza(cnmTOrdinanza.getIdOrdinanza());
			notificaService.salvaNotifica(notifica, userDetails);
		}
		

		return cnmTOrdinanza.getIdOrdinanza();
	}

	@Override
	public List<SelectVO> getCausaleSelect() {
		List<SelectVO> causale = new ArrayList<SelectVO>();

		Iterable<CnmDCausale> cnmDCausaleRepositoryList = cnmDCausaleRepository.findAll();
		for (Iterator<CnmDCausale> it = cnmDCausaleRepositoryList.iterator(); it.hasNext();) {
			
			CnmDCausale tmp = it.next();
			if(tmp.getFineValidita()==null || 
					(tmp.getFineValidita()!= null && tmp.getFineValidita().after(new Date()))) {
				SelectVO s = new SelectVO();
				s.setDenominazione(tmp.getDescCausale());
				s.setId(tmp.getIdCausale());
				causale.add(s);
			}
		}
		return causale;
	}
	
}
