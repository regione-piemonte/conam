/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.business.service.util.UtilsTraceCsiLogAuditService;
import it.csi.conam.conambl.business.service.verbale.UtilsVerbale;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.RequestSpostaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseSpostaDocumento;
import it.csi.conam.conambl.integration.beans.Soggetto;
import it.csi.conam.conambl.integration.doqui.DoquiConstants;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.conam.conambl.integration.doqui.bean.KeyDocumentoActa;
import it.csi.conam.conambl.integration.doqui.bean.SoggettoActa;
import it.csi.conam.conambl.integration.doqui.bean.UtenteActa;
import it.csi.conam.conambl.integration.doqui.entity.CnmDTipoDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmDTipoFornitore;
import it.csi.conam.conambl.integration.doqui.entity.CnmRRichiestaDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmRRichiestaDocumentoPK;
import it.csi.conam.conambl.integration.doqui.entity.CnmTDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmTFruitore;
import it.csi.conam.conambl.integration.doqui.entity.CnmTRichiesta;
import it.csi.conam.conambl.integration.doqui.entity.CnmTSpostamentoActa;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.exception.SpostaDocumentoException;
import it.csi.conam.conambl.integration.doqui.exception.TroppiAllegatiPerSpostamentoException;
import it.csi.conam.conambl.integration.doqui.helper.ManageRicercaDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.helper.ManageSpostaDocumentoHelper;
import it.csi.conam.conambl.integration.doqui.repositories.CnmTSpostamentoActaRepository;
import it.csi.conam.conambl.integration.doqui.service.ActaManagementService;
import it.csi.conam.conambl.integration.doqui.utils.XmlSerializer;
import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmDTipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.entity.CsiLogAudit.TraceOperation;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;

@Service
public class ManageSpostaDocumentoHelperImpl extends CommonManageDocumentoHelperImpl implements ManageSpostaDocumentoHelper {
	// 20200626_LC

	// 20200709_LC gestiti in costanti
	//	private static final String TIPO_DOCUMENTO_ACTA = "CONAM_ACTA";
	//	private static final String TIPO_DOCUMENTO_CONAM_1 = "CONAM_A1";
	//	private static final String TIPO_DOCUMENTO_CONAM_2 = "CONAM_A2";

	private static Logger log = Logger.getLogger(ManageSpostaDocumentoHelperImpl.class);

	@Autowired
	private ActaManagementService	actaManagementService;

	// 20200703_LC
	@Autowired
	private CnmTUserRepository cnmTUserRepository;	


	// 20200708_LC
	@Autowired
	private ManageRicercaDocumentoHelper manageRicercaDocumentoHelper;

	// 20200708_LC
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;

	@Autowired
	private UtilsDoqui utilsDoqui;

	@Autowired
	private UtilsVerbale utilsVerbale;

	@Autowired 
	private CnmTSpostamentoActaRepository cnmTSpostamentoActaRepository;

	@Autowired
	private UtilsDate utilsDate;

	@Autowired
	private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;

	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;

	@Autowired
	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;
	
	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseSpostaDocumento spostaDocumento(RequestSpostaDocumento request) throws SpostaDocumentoException  {

		String method = "spostaDocumento";
		log.debug(method + ". BEGIN");
		ResponseSpostaDocumento response =  new ResponseSpostaDocumento();	
		List<String> idToTraceList = new ArrayList<String>();
		String rootActa = request.getRootActa();
		String parolaChiaveFolderTemp = request.getParolaChiaveFolderTemp();
		Integer idVerbale = request.getIdVerbale();

		// 20200730_LC
		String soggettoActaFruitore = request.getSoggettoActa();


		boolean containsError = false;
		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". folder            = " + request.getFolder());
			log.debug(method + ". numeroProtocollo   = " + request.getNumeroProtocollo());
			log.debug(method + ". tipo Documento   = " + request.getTipoDocumento());

		}
		CnmTRichiesta cnmTRichiestaN = null;

		try
		{
			// validazioni 	
			if(request == null) throw new SpostaDocumentoException("Request non valorizzata");


			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new SpostaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getTipoDocumento())) throw new SpostaDocumentoException("Tipo documento non presente");
			if(StringUtils.isBlank(request.getFolder())) throw new SpostaDocumentoException("Folder partenza non presente");
			if(StringUtils.isBlank(request.getNumeroProtocollo())) throw new SpostaDocumentoException("Folder partenza non presente");






			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();
			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumento(request.getTipoDocumento());
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);

			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_SPOSTAMENTO_PROTOCOLLAZIONE);


			// DOCUMENTO qui crea solo il documento master (quello protocollato)

			// 20200711_LC 
			CnmTUser cnmTUser = null;
			if (SecurityUtils.getAuthentication()==null) {
				cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
			} else {
				UserDetails user = SecurityUtils.getUser();
				cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
			}

			CnmTDocumento cnmTDocumento = new CnmTDocumento();
			//			docTDocumento.setIdDocumento(getDocTDocumentoDaoIncrementer().nextLongValue());
			cnmTDocumento.setFolder(request.getFolder());	
			cnmTDocumento.setCnmDTipoDocumento(cnmDTipoDocumento);	// 20200630_LC
			cnmTDocumento.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
			cnmTDocumento.setCnmTUser2(cnmTUser);		// 20200702_LC 

			if(request.getMetadati() != null)
			{
				//				cnmTDocumento.setCodiceFiscale(request.getMetadati().getCodiceFiscale());
				cnmTDocumento.setIdentificativoEntitaFruitore(request.getMetadati().getIdEntitaFruitore());	// 20200720_LC in caso di multitipo contiene già "... N allegati ..."
				//				cnmTDocumento.setTarga(request.getMetadati().getTarga());
			}

			// 20201006_LC save anticipato
			cnmTDocumento = getCnmTDocumentoRepository().save(cnmTDocumento);
			// 20201007 PP - non setto la parola chiave poiche' sara' impostata con quella restituita da acta
			// cnmTDocumento.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumento.getIdDocumento())));	// 20200713_LC
			CnmTDocumento cnmTDocumentoN =getCnmTDocumentoRepository().save(cnmTDocumento);	
			cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);


			//RICHIESTA DOCUMENTO

			// 20200610_LC
			CnmRRichiestaDocumentoPK cnmRRichiestaDocumentoPK = new CnmRRichiestaDocumentoPK();
			cnmRRichiestaDocumentoPK.setIdDocumento(cnmTDocumento.getIdDocumento());
			cnmRRichiestaDocumentoPK.setIdRichiesta(cnmTRichiesta.getIdRichiesta());

			CnmRRichiestaDocumento cnmRRichiestaDocumentoN = new CnmRRichiestaDocumento();
			cnmRRichiestaDocumentoN.setId(cnmRRichiestaDocumentoPK);
			cnmRRichiestaDocumentoN.setCnmTDocumento(cnmTDocumento);
			cnmRRichiestaDocumentoN.setCnmTRichiesta(cnmTRichiesta);

			//POJO UTENTE
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());

			if(StringUtils.isNotEmpty(rootActa))
				utenteActa.setRootActa(rootActa);
			else
				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());

			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());

			//POJO DOCUMENTO
			DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
			documentoElettronicoActa.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
			documentoElettronicoActa.setFolder(request.getFolder());
			documentoElettronicoActa.setTipoStrutturaRoot(cnmDTipoDocumento.getCnmDStrutturaActaRoot().getIdStrutturaActa());		// 20200630_LC
			documentoElettronicoActa.setTipoStrutturaFolder(cnmDTipoDocumento.getCnmDStrutturaActaFolder().getIdStrutturaActa());	// 20200630_LC


			// 20200730_LC
			if(StringUtils.isNotEmpty(soggettoActaFruitore))
				documentoElettronicoActa.setFruitore(soggettoActaFruitore);
			else
				documentoElettronicoActa.setFruitore(cnmTFruitore.getDescrFruitore());

			SoggettoActa soggettoActa = new SoggettoActa();
			if (Soggetto.TOPOLOGIA_SOGGETTO_MITTENTE.equals(request.getSoggetto().getTipologia()))
			{
				soggettoActa.setMittente(true);
				documentoElettronicoActa.setAutore(request.getSoggetto().getCognome());
				//TODO Da verificare la denominazione
				log.debug(method + ". cnmDTipoDocumento.getDescFormadocEntrata(): " + cnmDTipoDocumento.getDescFormadocEntrata());
				utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocEntrata());
			}
			else
			{
				soggettoActa.setMittente(false);
				documentoElettronicoActa.setAutore(""); //REQUISITO CHIDERE A PAOLO
				utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocUscita());

			}
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
			soggettoActa.setCognome(request.getSoggetto().getCognome());
			soggettoActa.setNome(request.getSoggetto().getNome());
			if (request.getSoggetto().getDenominazione() != null)
				soggettoActa.setDenominazione(request.getSoggetto().getDenominazione());

			documentoElettronicoActa.setSoggettoActa(soggettoActa);


			if(log.isDebugEnabled())
			{
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
			}

			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);

			// 2023/02/25 PP - Creo la CnmTSpostamento per gestirla via batch
			// id_verbale, numero_protocollo, operazione (COPIA), stato (STATO_RICHIESTA_RICEVUTA), insertDate (now()), classificazioneId, info)
			CnmTSpostamentoActa cnmTSpostamentoActa = cnmTSpostamentoActaRepository.findByIdVerbaleAndNumeroProtocolloAndStato(idVerbale, request.getNumeroProtocollo(), CnmTSpostamentoActa.STATO_IN_CORSO);
			if(cnmTSpostamentoActa == null) {
				cnmTSpostamentoActa= new CnmTSpostamentoActa(
						idVerbale, 
						cnmTDocumentoN.getIdDocumento(),
						request.getNumeroProtocollo(), 
						request.getFolder(),
						CnmTSpostamentoActa.OPERAZIONE_SPOSTA, 
						CnmTSpostamentoActa.STATO_RICHIESTA_RICEVUTA, 
						null, // id della prenotazione
						utilsDate.asTimeStamp(LocalDateTime.now()), 
						"Richiesta presa in carico da conam",
						request.getParolaChiaveFolderTemp());
				cnmTSpostamentoActaRepository.save(cnmTSpostamentoActa);
			}

			getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);
			
			
//			if(log.isDebugEnabled()){
//				log.debug(method + ". inserito documento           = " + cnmTDocumentoN);
//				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
//				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);
//
//			}
//
//
//			KeyDocumentoActa spostaResponse;
//			try {
//				spostaResponse = actaManagementService.spostaDocumento(documentoElettronicoActa, request.getNumeroProtocollo(), utenteActa, parolaChiaveFolderTemp, false);
//			} catch (TroppiAllegatiPerSpostamentoException e) {
//
//				log.debug(method + ". fascicolo con troppi allegati, richiesta per moveDocument asincrona: " + e.getMessage());
//
//				// eccezione Acraris SER-167, si ri-invoca il servizio con offlineRequest: true e si produce un elemento da far consumare al batch
//				spostaResponse = actaManagementService.spostaDocumento(documentoElettronicoActa, request.getNumeroProtocollo(), utenteActa, parolaChiaveFolderTemp, true);
//
//				// 2023/02/25 PP - Verifico se per il protocollo e il verbale è già in corso uno spostamento, altrimenti inserisco lo spostamento in tabella
//				// id_verbale, numero_protocollo, operazione (SPOSTA), stato (IN_CORSO), insertDate (now()), classificazioneId, info)
//				CnmTSpostamentoActa cnmTSpostamentoActa = cnmTSpostamentoActaRepository.findByIdVerbaleAndNumeroProtocolloAndStato(idVerbale, request.getNumeroProtocollo(), CnmTSpostamentoActa.STATO_IN_CORSO);
//				if(cnmTSpostamentoActa == null) {
//					cnmTSpostamentoActa= new CnmTSpostamentoActa(
//							idVerbale, 
//							cnmTDocumentoN.getIdDocumento(),
//							request.getNumeroProtocollo(), 
//							request.getFolder(),
//							CnmTSpostamentoActa.OPERAZIONE_SPOSTA, 
//							CnmTSpostamentoActa.STATO_IN_CORSO, 
//							spostaResponse.getObjectIdClassificazione(), // id della prenotazione
//							utilsDate.asTimeStamp(LocalDateTime.now()), 
//							"Richiesta sposta inviata");
//					cnmTSpostamentoActaRepository.save(cnmTSpostamentoActa);
//				}
//
//				// 2023/02/25 PP - Logica spostata su batch, poiche' le richieste di spostamento non sono subito finalizzate da ACTA
//
//				// response
//				response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));
//				response.setProtocollo(request.getNumeroProtocollo());
//				response.setIndiceClassificazione(null);
//				response.setIdFolder(spostaResponse.getIdFolderCreated()); 
//				return response;
//
//			}
//			
//			// continua se l'operazione è andata a buon fine in modo sincrono - crea il cnmTDocumento solo per il documento di riferimento (oltre al master)
//
//			String idClassificazioneNew = spostaResponse.getObjectIdClassificazione();
//			log.debug(method + ". operazione sincrona OK: idClassificazioneNew =\n " + idClassificazioneNew);
//
//			getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);
//
//			// aggiornamento informazioni documenti spostati
//			if (spostaResponse.getObjectIdClassificazione() != null) {
//				updateInfoPostSpostaCopia(request, cnmTUser, cnmTDocumento, response, false);
//
//				// 20201124_LC
//				response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));
//				response.setProtocollo(request.getNumeroProtocollo());
//				response.setIdFolder(spostaResponse.getIdFolderCreated());
//			}
			
			response.setObjectIdDocumentoToTraceList(Arrays.asList(""));
			return response;

		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". FruitoreException: " + e);
			throw new SpostaDocumentoException(e.getMessage());
//		} catch (IntegrationException e) 
//		{
//			containsError = true;
//			log.error(method + ". IntegrationException: " + e);
//			throw new SpostaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		}
		finally
		{
			log.info(method + ". END");
		}
	}













	// 20200728_LC
	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)	
	public ResponseSpostaDocumento copiaDocumento(RequestSpostaDocumento request) throws SpostaDocumentoException  {

		String method = "copiaDocumento";
		log.debug(method + ". BEGIN");
		ResponseSpostaDocumento response =  new ResponseSpostaDocumento();	
		List<String> idToTraceList = new ArrayList<String>();
		String rootActa = request.getRootActa();
		String parolaChiaveFolderTemp = request.getParolaChiaveFolderTemp();
		Integer idVerbale = request.getIdVerbale();

		// 20200730_LC
		String soggettoActaFruitore = request.getSoggettoActa();

		boolean containsError = false;
		if(log.isDebugEnabled())
		{	
			log.debug(method + ". codice fruitore  = " + request.getCodiceFruitore());
			log.debug(method + ". folder            = " + request.getFolder());
			log.debug(method + ". numeroProtocollo   = " + request.getNumeroProtocollo());
			log.debug(method + ". tipo Documento   = " + request.getTipoDocumento());

		}
		CnmTRichiesta cnmTRichiestaN = null;

		try
		{
			// validazioni 	
			if(request == null) throw new SpostaDocumentoException("Request non valorizzata");


			if(StringUtils.isBlank(request.getCodiceFruitore())) throw new SpostaDocumentoException("Codice fruitore non presente");
			if(StringUtils.isBlank(request.getTipoDocumento())) throw new SpostaDocumentoException("Tipo documento non presente");
			if(StringUtils.isBlank(request.getFolder())) throw new SpostaDocumentoException("Folder partenza non presente");
			if(StringUtils.isBlank(request.getNumeroProtocollo())) throw new SpostaDocumentoException("Folder partenza non presente");






			// recupero fruitore
			CnmTFruitore cnmTFruitore = getFruitore();
			// tipo documento
			CnmDTipoDocumento cnmDTipoDocumento = getTipoDocumento(request.getTipoDocumento());
			//tipo fornitore
			CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);

			// RICHIESTA
			CnmTRichiesta cnmTRichiesta = createCnmTRichiesta(cnmTFruitore.getIdFruitore(), DoquiConstants.SERVIZIO_COPIA_PROTOCOLLAZIONE);


			// DOCUMENTO qui crea solo il documento master (quello protocollato)

			// 20200711_LC 
			CnmTUser cnmTUser = null;
			if (SecurityUtils.getAuthentication()==null) {
				cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
			} else {
				UserDetails user = SecurityUtils.getUser();
				cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
			}

			CnmTDocumento cnmTDocumento = new CnmTDocumento();
			//			docTDocumento.setIdDocumento(getDocTDocumentoDaoIncrementer().nextLongValue());
			cnmTDocumento.setFolder(request.getFolder());	
			cnmTDocumento.setCnmDTipoDocumento(cnmDTipoDocumento);	// 20200630_LC
			cnmTDocumento.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
			cnmTDocumento.setCnmTUser2(cnmTUser);		// 20200702_LC 

			if(request.getMetadati() != null)
			{
				//				cnmTDocumento.setCodiceFiscale(request.getMetadati().getCodiceFiscale());
				cnmTDocumento.setIdentificativoEntitaFruitore(request.getMetadati().getIdEntitaFruitore());	// 20200720_LC in caso di multitipo contiene già "... N allegati ..."
				//				cnmTDocumento.setTarga(request.getMetadati().getTarga());
			}



			// 20201006_LC save anticipato
			cnmTDocumento = getCnmTDocumentoRepository().save(cnmTDocumento);
			// 20201007 PP - non setto la parola chiave poiche' sara' impostata con quella restituita da acta
			// cnmTDocumento.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumento.getIdDocumento())));	// 20200713_LC
			CnmTDocumento cnmTDocumentoN =getCnmTDocumentoRepository().save(cnmTDocumento);	
			cnmTRichiestaN = getStatoRichiestaService().insertRichiesta(cnmTRichiesta);


			//RICHIESTA DOCUMENTO

			// 20200610_LC
			CnmRRichiestaDocumentoPK cnmRRichiestaDocumentoPK = new CnmRRichiestaDocumentoPK();
			cnmRRichiestaDocumentoPK.setIdDocumento(cnmTDocumento.getIdDocumento());
			cnmRRichiestaDocumentoPK.setIdRichiesta(cnmTRichiesta.getIdRichiesta());

			CnmRRichiestaDocumento cnmRRichiestaDocumentoN = new CnmRRichiestaDocumento();
			cnmRRichiestaDocumentoN.setId(cnmRRichiestaDocumentoPK);
			cnmRRichiestaDocumentoN.setCnmTDocumento(cnmTDocumento);
			cnmRRichiestaDocumentoN.setCnmTRichiesta(cnmTRichiesta);

			//POJO UTENTE
			UtenteActa utenteActa = new UtenteActa();
			utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
			utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
			utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
			utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
			utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
			utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());

			if(StringUtils.isNotEmpty(rootActa))
				utenteActa.setRootActa(rootActa);
			else
				utenteActa.setRootActa(cnmDTipoDocumento.getRoot());

			utenteActa.setIdvitalrecordcodetype(cnmDTipoDocumento.getIdVitalRecordCodeType());
			utenteActa.setIdStatoDiEfficacia(cnmDTipoDocumento.getIdStatoEfficacia());

			//POJO DOCUMENTO
			DocumentoElettronicoActa documentoElettronicoActa = new DocumentoElettronicoActa();
			documentoElettronicoActa.setIdDocumento(cnmTDocumento.getIdentificativoArchiviazione());
			documentoElettronicoActa.setFolder(request.getFolder());
			documentoElettronicoActa.setTipoStrutturaRoot(cnmDTipoDocumento.getCnmDStrutturaActaRoot().getIdStrutturaActa());		// 20200630_LC
			documentoElettronicoActa.setTipoStrutturaFolder(cnmDTipoDocumento.getCnmDStrutturaActaFolder().getIdStrutturaActa());	// 20200630_LC

			// 20200730_LC
			if(StringUtils.isNotEmpty(soggettoActaFruitore))
				documentoElettronicoActa.setFruitore(soggettoActaFruitore);
			else
				documentoElettronicoActa.setFruitore(cnmTFruitore.getDescrFruitore());

			SoggettoActa soggettoActa = new SoggettoActa();
			if (Soggetto.TOPOLOGIA_SOGGETTO_MITTENTE.equals(request.getSoggetto().getTipologia()))
			{
				soggettoActa.setMittente(true);
				documentoElettronicoActa.setAutore(request.getSoggetto().getCognome());
				//TODO Da verificare la denominazione
				log.debug(method + ". cnmDTipoDocumento.getDescFormadocEntrata(): " + cnmDTipoDocumento.getDescFormadocEntrata());
				utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocEntrata());
			}
			else
			{
				soggettoActa.setMittente(false);
				documentoElettronicoActa.setAutore(""); //REQUISITO CHIDERE A PAOLO
				utenteActa.setDescFormaDocumentaria(cnmDTipoDocumento.getDescFormadocUscita());

			}
			utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());
			soggettoActa.setCognome(request.getSoggetto().getCognome());
			soggettoActa.setNome(request.getSoggetto().getNome());
			if (request.getSoggetto().getDenominazione() != null)
				soggettoActa.setDenominazione(request.getSoggetto().getDenominazione());

			documentoElettronicoActa.setSoggettoActa(soggettoActa);


			if(log.isDebugEnabled())
			{
				log.debug(method + ". UtenteActa =\n " + XmlSerializer.objectToXml(utenteActa));
			}

			CnmRRichiestaDocumento cnmRRichiestaDocumento = getCnmRRichiestaDocumentoRepository().save(cnmRRichiestaDocumentoN);

			if(log.isDebugEnabled()){
				log.debug(method + ". inserito documento           = " + cnmTDocumentoN);
				log.debug(method + ". inserita richiesta           = " + cnmTRichiestaN);
				log.debug(method + ". inserita richiesta documento = " + cnmRRichiestaDocumento);

			}

			
			
			// 2023/02/25 PP - Creo la CnmTSpostamento per gestirla via batch
			// id_verbale, numero_protocollo, operazione (COPIA), stato (STATO_RICHIESTA_RICEVUTA), insertDate (now()), classificazioneId, info)
			CnmTSpostamentoActa cnmTSpostamentoActa = cnmTSpostamentoActaRepository.findByIdVerbaleAndNumeroProtocolloAndStato(idVerbale, request.getNumeroProtocollo(), CnmTSpostamentoActa.STATO_IN_CORSO);
			if(cnmTSpostamentoActa == null) {
				cnmTSpostamentoActa= new CnmTSpostamentoActa(
						idVerbale, 
						cnmTDocumentoN.getIdDocumento(),
						request.getNumeroProtocollo(), 
						request.getFolder(),
						CnmTSpostamentoActa.OPERAZIONE_COPIA, 
						CnmTSpostamentoActa.STATO_RICHIESTA_RICEVUTA, 
						null, // id della prenotazione
						utilsDate.asTimeStamp(LocalDateTime.now()), 
						"Richiesta presa in carico da conam",
						request.getParolaChiaveFolderTemp());
				cnmTSpostamentoActaRepository.save(cnmTSpostamentoActa);
			}

			getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);

			// response
			response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));
			response.setProtocollo(request.getNumeroProtocollo());
			response.setIndiceClassificazione(null);
			response.setIdFolder(null); 
			return response;

			// (XXX)L pe ril moemtno il flusso di copia è come quello di sposta (eccezione acaris E167 in caso di troppi allegati -> richiesta asincrona)
			
//			KeyDocumentoActa copiaResponse;
//			try {
//				try {
//					copiaResponse = actaManagementService.copiaDocumento(documentoElettronicoActa, request.getNumeroProtocollo(), utenteActa, parolaChiaveFolderTemp, false);
//				} catch (TroppiAllegatiPerSpostamentoException e) {
//	
//					log.debug(method + ". fascicolo con troppi allegati, richiesta per aggiungiClassificazione asincrona: " + e.getMessage());
//	
//					// eccezione Acraris SER-167, si ri-invoca il servizio con offlineRequest: true e si produce un elemento da far consumare al batch
//					copiaResponse = actaManagementService.copiaDocumento(documentoElettronicoActa, request.getNumeroProtocollo(), utenteActa, parolaChiaveFolderTemp, true);
//	
//					// 2023/02/25 PP - Verifico se per il protocollo e il verbale è già in corso uno spostamento, altrimenti inserisco lo spostamento in tabella
//					// id_verbale, numero_protocollo, operazione (COPIA), stato (IN_CORSO), insertDate (now()), classificazioneId, info)
//					CnmTSpostamentoActa cnmTSpostamentoActa = cnmTSpostamentoActaRepository.findByIdVerbaleAndNumeroProtocolloAndStato(idVerbale, request.getNumeroProtocollo(), CnmTSpostamentoActa.STATO_IN_CORSO);
//					if(cnmTSpostamentoActa == null) {
//						cnmTSpostamentoActa= new CnmTSpostamentoActa(
//								idVerbale, 
//								cnmTDocumentoN.getIdDocumento(),
//								request.getNumeroProtocollo(), 
//								request.getFolder(),
//								CnmTSpostamentoActa.OPERAZIONE_COPIA, 
//								CnmTSpostamentoActa.STATO_IN_CORSO, 
//								copiaResponse.getObjectIdClassificazione(), // id della prenotazione
//								utilsDate.asTimeStamp(LocalDateTime.now()), 
//								"Richiesta presa in carico da conam");
//						cnmTSpostamentoActaRepository.save(cnmTSpostamentoActa);
//					}
//	
//					// 2023/02/25 PP - Logica spostata su batch, poiche' le richieste di spostamento non sono subito finalizzate da ACTA
//	
//					// response
//					response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));
//					response.setProtocollo(request.getNumeroProtocollo());
//					response.setIndiceClassificazione(null);
//					response.setIdFolder(copiaResponse.getIdFolderCreated()); 
//					return response;
//				}
//			} catch (IntegrationException e) 
//			{
//
//				// 2023/02/25 PP - Verifico se per il protocollo e il verbale è già in corso uno spostamento, altrimenti inserisco lo spostamento in tabella
//				// id_verbale, numero_protocollo, operazione (COPIA), stato (STATO_ERRORE), insertDate (now()), classificazioneId, info)
//				CnmTSpostamentoActa cnmTSpostamentoActa = cnmTSpostamentoActaRepository.findByIdVerbaleAndNumeroProtocolloAndStato(idVerbale, request.getNumeroProtocollo(), CnmTSpostamentoActa.STATO_ERRORE);
//				if(cnmTSpostamentoActa == null) {
//					cnmTSpostamentoActa= new CnmTSpostamentoActa(
//							idVerbale, 
//							cnmTDocumentoN.getIdDocumento(),
//							request.getNumeroProtocollo(), 
//							request.getFolder(),
//							CnmTSpostamentoActa.OPERAZIONE_COPIA, 
//							CnmTSpostamentoActa.STATO_ERRORE, 
//							null, // id della prenotazione
//							utilsDate.asTimeStamp(LocalDateTime.now()), 
//							"Richiesta copia inviata");
//					cnmTSpostamentoActaRepository.save(cnmTSpostamentoActa);
//				}
//
//				// 2023/02/25 PP - Logica spostata su batch, poiche' le richieste di spostamento non sono subito finalizzate da ACTA
//
//				// response
//				response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));
//				response.setProtocollo(request.getNumeroProtocollo());
//				response.setIndiceClassificazione(null);
//				response.setIdFolder(null); 
//				return response;
//			}
//			// continua se l'operazione è andata a buon fine in modo sincrono - crea il cnmTDocumento solo per il documento di riferimento (oltre al master)
//
//			String idClassificazioneNew = copiaResponse.getObjectIdClassificazione();
//			log.debug(method + ". operazione sincrona OK: idClassificazioneNew =\n " + XmlSerializer.objectToXml(idClassificazioneNew));
//
//			getStatoRichiestaService().changeStatoRichiesta(cnmTRichiesta.getIdRichiesta(), DoquiConstants.STATO_RICHIESTA_EVASA);
//
//			// aggiornamento informazioni documenti spostati
//			if (copiaResponse.getObjectIdClassificazione() != null) {
//				updateInfoPostSpostaCopia(request, cnmTUser, cnmTDocumento, response, true);
//
//				// 20201124_LC
//				response.setIdDocumento(String.valueOf(cnmTDocumento.getIdDocumento()));
//				response.setProtocollo(request.getNumeroProtocollo());
//				response.setIdFolder(copiaResponse.getIdFolderCreated());
//			}
//			
//			return response;

		}
		catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". FruitoreException: " + e);
			throw new SpostaDocumentoException(e.getMessage());
//		} catch (IntegrationException e) 
//		{
//			containsError = true;
//			log.error(method + ". IntegrationException: " + e);
//			throw new SpostaDocumentoException(e.getMessage());
		}
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		}
		finally
		{
			log.info(method + ". END");
		}
	}
	
	public ResponseSpostaDocumento manageSposta (ResponseSpostaDocumento response, DocumentoElettronicoActa documentoElettronicoActa, CnmTSpostamentoActa cnmTSpostamentoActa, UtenteActa utenteActa, String parolaChiaveFolderTemp, boolean isPregresso) throws Exception
	{

		String method = "manageSposta";
		KeyDocumentoActa spostaResponse;
		try {
			spostaResponse = actaManagementService.spostaDocumento(documentoElettronicoActa, cnmTSpostamentoActa.getNumeroProtocollo(), utenteActa, parolaChiaveFolderTemp, false);
		} catch (TroppiAllegatiPerSpostamentoException e) {

			log.debug(method + ". fascicolo con troppi allegati, richiesta per moveDocument asincrona: " + e.getMessage());

			// eccezione Acraris SER-167, si ri-invoca il servizio con offlineRequest: true e si produce un elemento da far consumare al batch
			spostaResponse = actaManagementService.spostaDocumento(documentoElettronicoActa, cnmTSpostamentoActa.getNumeroProtocollo(), utenteActa, parolaChiaveFolderTemp, true);

			cnmTSpostamentoActa.setPrenotazioneId(spostaResponse.getObjectIdClassificazione());
			cnmTSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_IN_CORSO); 
			cnmTSpostamentoActa.setInfo("Richiesta sposta inviata ad ACTA");

			cnmTSpostamentoActaRepository.save(cnmTSpostamentoActa);
			

			// 2023/02/25 PP - Logica spostata su batch, poiche' le richieste di spostamento non sono subito finalizzate da ACTA

			// response
			response.setIdDocumento(String.valueOf(cnmTSpostamentoActa.getIdDocumentoMaster()));
			response.setProtocollo(cnmTSpostamentoActa.getNumeroProtocollo());
			response.setIndiceClassificazione(null);
			response.setIdFolder(spostaResponse.getIdFolderCreated()); 
			return response;

		}
		
		// continua se l'operazione è andata a buon fine in modo sincrono - crea il cnmTDocumento solo per il documento di riferimento (oltre al master)

		String idClassificazioneNew = spostaResponse.getObjectIdClassificazione();
		log.debug(method + ". operazione sincrona OK: idClassificazioneNew =\n " + idClassificazioneNew);

		// aggiornamento informazioni documenti spostati
		if (spostaResponse.getObjectIdClassificazione() != null) {
			response.setObjectIdDocumentoToTraceList(new ArrayList<String>());
			updateInfoPostSpostaCopia(cnmTSpostamentoActa, response, false);
			tracciaSuCsiLogAudit(false, isPregresso, response.getObjectIdDocumentoToTraceList());
			// 20201124_LC
			response.setIdDocumento(String.valueOf(cnmTSpostamentoActa.getIdDocumentoMaster()));
			response.setProtocollo(cnmTSpostamentoActa.getNumeroProtocollo());
			response.setIdFolder(spostaResponse.getIdFolderCreated());
		}
		

		cnmTSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_EVASO);
		cnmTSpostamentoActa.setDataOraEnd(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmTSpostamentoActa.setInfo("Operazione " + cnmTSpostamentoActa.getOperazione().toLowerCase() + " effettuata e dati aggiornati");

		cnmTSpostamentoActaRepository.save(cnmTSpostamentoActa);
		
		return response;
	}
	
	public ResponseSpostaDocumento manageCopia (ResponseSpostaDocumento response, DocumentoElettronicoActa documentoElettronicoActa, CnmTSpostamentoActa cnmTSpostamentoActa, UtenteActa utenteActa, String parolaChiaveFolderTemp, boolean isPregresso) throws Exception
	{

		// (XXX)L pe ril moemtno il flusso di copia è come quello di sposta (eccezione acaris E167 in caso di troppi allegati -> richiesta asincrona)

		String method = "manageCopia";
		KeyDocumentoActa copiaResponse;
		try {
			try {
				copiaResponse = actaManagementService.copiaDocumento(documentoElettronicoActa, cnmTSpostamentoActa.getNumeroProtocollo(), utenteActa, parolaChiaveFolderTemp, false);
			} catch (TroppiAllegatiPerSpostamentoException e) {

				log.debug(method + ". fascicolo con troppi allegati, richiesta per aggiungiClassificazione asincrona: " + e.getMessage());

				// eccezione Acraris SER-167, si ri-invoca il servizio con offlineRequest: true e si produce un elemento da far consumare al batch
				copiaResponse = actaManagementService.copiaDocumento(documentoElettronicoActa, cnmTSpostamentoActa.getNumeroProtocollo(), utenteActa, parolaChiaveFolderTemp, true);
					
				cnmTSpostamentoActa.setPrenotazioneId(copiaResponse.getObjectIdClassificazione());
				cnmTSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_IN_CORSO); 
				cnmTSpostamentoActa.setInfo("Richiesta copia inviata ad ACTA");
							
				cnmTSpostamentoActaRepository.save(cnmTSpostamentoActa);				

				// 2023/02/25 PP - Logica spostata su batch, poiche' le richieste di spostamento non sono subito finalizzate da ACTA

				// response
				response.setIdDocumento(String.valueOf(cnmTSpostamentoActa.getIdDocumentoMaster()));
				response.setProtocollo(cnmTSpostamentoActa.getNumeroProtocollo());
				response.setIndiceClassificazione(null);
				response.setIdFolder(copiaResponse.getIdFolderCreated()); 
				return response;
			}
		} catch (IntegrationException e) 
		{

			cnmTSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_ERRORE); 
			cnmTSpostamentoActa.setInfo("Errore restituito da ACTA ["+e.getMessage()+"]");

			cnmTSpostamentoActaRepository.save(cnmTSpostamentoActa);

			// 2023/02/25 PP - Logica spostata su batch, poiche' le richieste di spostamento non sono subito finalizzate da ACTA

			// response
			response.setIdDocumento(String.valueOf(cnmTSpostamentoActa.getIdDocumentoMaster()));
			response.setProtocollo(cnmTSpostamentoActa.getNumeroProtocollo());
			response.setIndiceClassificazione(null);
			response.setIdFolder(null); 
			return response;
		}
		// continua se l'operazione è andata a buon fine in modo sincrono - crea il cnmTDocumento solo per il documento di riferimento (oltre al master)

		String idClassificazioneNew = copiaResponse.getObjectIdClassificazione();
		log.debug(method + ". operazione sincrona OK: idClassificazioneNew =\n " + XmlSerializer.objectToXml(idClassificazioneNew));

		// aggiornamento informazioni documenti spostati
		if (copiaResponse.getObjectIdClassificazione() != null) {
			response.setObjectIdDocumentoToTraceList(new ArrayList<String>());
			updateInfoPostSpostaCopia(cnmTSpostamentoActa, response, true);
			tracciaSuCsiLogAudit(true, isPregresso, response.getObjectIdDocumentoToTraceList());
			// 20201124_LC
			response.setIdDocumento(String.valueOf(cnmTSpostamentoActa.getIdDocumentoMaster()));
			response.setProtocollo(cnmTSpostamentoActa.getNumeroProtocollo());
			response.setIdFolder(copiaResponse.getIdFolderCreated());
		}

		cnmTSpostamentoActa.setStato(CnmTSpostamentoActa.STATO_EVASO);
		cnmTSpostamentoActa.setDataOraEnd(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmTSpostamentoActa.setInfo("Operazione " + cnmTSpostamentoActa.getOperazione().toLowerCase() + " effettuata e dati aggiornati");

		cnmTSpostamentoActaRepository.save(cnmTSpostamentoActa);
		
		return response;
	}

	@SuppressWarnings("unused")
	@Transactional(propagation=Propagation.REQUIRED)
	public ResponseSpostaDocumento salvaAllegatoGiaPresenteNelFascicoloActa(DocumentoProtocollatoVO doc, CnmDTipoAllegato cnmDTipoAllegato, CnmTVerbale cnmTVerbale, CnmTUser cnmTUser, List<CnmTAllegato> allegatiGiaSalvati) throws SpostaDocumentoException {
		String method = "salvaAllegatoGiaPresenteNelFascicoloActa";
		log.debug(method + ". BEGIN");

		ResponseSpostaDocumento response =  new ResponseSpostaDocumento();	
		boolean containsError = false;
		try {
			//			String idEntitaFruitoreStart=cnmTVerbale.getIdVerbale() +  " - ";
			if(allegatiGiaSalvati == null || allegatiGiaSalvati.isEmpty()) throw new SpostaDocumentoException("allegatiGiaSalvati non valorizzata");
			List<Integer> idActaList = new ArrayList<Integer>();
			for(CnmTAllegato allegatoSalvato:allegatiGiaSalvati) {
				if(StringUtils.isNotBlank(allegatoSalvato.getIdActa()))
					idActaList.add(Integer.parseInt(allegatoSalvato.getIdActa()));
			}
			//			List<CnmTDocumento> cnmTDocumentoList = getCnmTDocumentoRepository().findByObjectiddocumentoAndIdentificativoEntitaFruitoreStartsWith(doc.getObjectIdDocumento(), idEntitaFruitoreStart);
			List<CnmTDocumento> cnmTDocumentoList = null;
			if (idActaList != null && !idActaList.isEmpty() && idActaList.size() > 0)
				cnmTDocumentoList = getCnmTDocumentoRepository().findByObjectiddocumentoAndIdDocumentoIn(doc.getObjectIdDocumento(), idActaList);

			CnmTDocumento cnmTDocumento = null;
			if(cnmTDocumentoList!=null && !cnmTDocumentoList.isEmpty()) {
				log.debug(method + ". insert gia' presente in cnmTDocumento per verbale=" + cnmTVerbale.getIdVerbale() + " e objectId=" + doc.getObjectIdDocumento());
				cnmTDocumento = cnmTDocumentoList.get(0); //prendo il 1 perche' in teoria ce ne dovrebbe essere solo 1
				if(cnmTDocumentoList.size()>1) {
					log.warn(method + ". trovati " + cnmTDocumentoList.size() + " record sulla cnmTDocumento per verbale=" + cnmTVerbale.getIdVerbale() + " e objectId=" + doc.getObjectIdDocumento()); 
				}
				cnmTDocumento.setIdentificativoEntitaFruitore(cnmTDocumento.getIdentificativoEntitaFruitore().concat("; ").concat(cnmDTipoAllegato.getDescTipoAllegato()));
				cnmTDocumento.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));   
				cnmTDocumento.setCnmTUser1(cnmTUser); 
			}else {
				cnmTDocumento = new CnmTDocumento();
				cnmTDocumento.setFolder(utilsDoqui.createOrGetfolder(cnmTVerbale));    
				cnmTDocumento.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmTDocumento.setCnmTUser2(cnmTUser);        
				cnmTDocumento.setIdentificativoEntitaFruitore(utilsDoqui.createIdEntitaFruitore(cnmTVerbale, cnmDTipoAllegato));    
				cnmTDocumento.setIdentificativoFornitore(doc.getDocumentoUUID());
				//				cnmTDocumento.setProtocolloFornitore(doc.getNumProtocolloMaster()); //prendo questo perche' sicuramente e' un allegato, il master sarebbe gia' presente in cnmTDocumento quindi sarei entrata nella if sopra
				//				cnmTDocumento.setClassificazioneId(doc.getClassificazioneId());
				cnmTDocumento.setRegistrazioneId(doc.getRegistrazioneId());
				cnmTDocumento.setObjectidclassificazione(doc.getClassificazioneId());
				cnmTDocumento.setObjectiddocumento(doc.getObjectIdDocumento());
				cnmTDocumento.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));  
				cnmTDocumento.setIdentificativoArchiviazione(doc.getParolaChiave());	

				// 20210524_LC in mancanza di byte[], true se è un p7m, false altrimenti
				boolean isDocSigned = false;
				if (doc != null && doc.getFilename() != null && doc.getFilename().toUpperCase().endsWith(".P7M"))
					isDocSigned = true;

				String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
				long idTipoAll = cnmDTipoAllegato.getIdTipoAllegato();
				if (idTipoAll == TipoAllegato.CONTRODEDUZIONE.getId() || idTipoAll == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId() 
						|| idTipoAll == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId() || idTipoAll == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()
						|| idTipoAll == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || idTipoAll == TipoAllegato.COMPARSA_ALLEGATO.getId() || idTipoAll == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
					tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;
				} else if (idTipoAll == TipoAllegato.LETTERA_ORDINANZA.getId() || idTipoAll == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
						|| idTipoAll == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || idTipoAll == TipoAllegato.LETTERA_SOLLECITO.getId()
						|| idTipoAll == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
					tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;			
				}

				cnmTDocumento.setCnmDTipoDocumento(getTipoDocumento(tipoDoc));
			}
			CnmTDocumento cnmTDocumentoN = getCnmTDocumentoRepository().save(cnmTDocumento); 
			response.setIdDocumento(String.valueOf(cnmTDocumentoN.getIdDocumento()));
			response.setProtocollo(cnmTDocumentoN.getProtocolloFornitore());
			//			response.setIndiceClassificazione(cnmTDocumentoN.getClassificazioneId());			

			// se la parola chiave non e' mai stata aggiornata faccio l'aggiornamento e salvo anche sul DB
			/* 20201006_ET CONAM-100 visto che il doc e' gia stato protocollato non vado a modificare la parola chiave
			if(StringUtils.isBlank(doc.getParolaChiave())) {

				// recupero fruitore
				CnmTFruitore cnmTFruitore = getFruitore();

				//tipo fornitore
				CnmDTipoFornitore cnmDTipoFornitore = getCnmDTipoFornitoreRepository().findOne(DoquiConstants.FORNITORE_ACTA);
				//POJO UTENTE
				UtenteActa utenteActa = new UtenteActa();
				utenteActa.setCodiceFiscale(cnmTFruitore.getCfActa());
				utenteActa.setIdAoo(new Integer(cnmTFruitore.getIdAooActa()));
				utenteActa.setIdNodo(new Integer(cnmTFruitore.getIdNodoActa()));
				utenteActa.setIdStruttura(new Integer(cnmTFruitore.getIdStrutturaActa()));
				utenteActa.setApplicationKeyActa(cnmDTipoFornitore.getApplicationKey());
				utenteActa.setRepositoryName(cnmDTipoFornitore.getRepository());
				utenteActa.setDescEnte(cnmDTipoFornitore.getCodEnte());

				actaManagementService.aggiornaPropertiesActaPostSpostamento(doc.getObjectIdDocumento(), componiParolaChiave(String.valueOf(cnmTDocumentoN.getIdDocumento())), "", utenteActa);	// 20200713_LC

				cnmTDocumentoN.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumentoN.getIdDocumento())));
				cnmTDocumentoN.setDataOraUpdate(utilsDate.asTimeStamp(LocalDateTime.now()));
				cnmTDocumento.setCnmTUser1(cnmTUser);
				getCnmTDocumentoRepository().save(cnmTDocumentoN);
			}*/
			return response;

		} catch (FruitoreException e)
		{
			containsError = true;
			log.error(method + ". " + e.getMessage());
			log.error(method + ". FruitoreException: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		} 
		catch (Exception e) 
		{
			containsError = true;
			log.error(method + ". Exception: " + e);
			throw new SpostaDocumentoException(e.getMessage());
		}
		finally
		{
			log.info(method + ". END");
		}
	}

	@Override
	public void updateInfoPostSpostaCopia(CnmTSpostamentoActa cnmTSpostamentoActa, ResponseSpostaDocumento response, boolean operazioneCopia) throws Exception {
		String method = "updateInfoPostSpostaCopia";
		log.debug(method + ". get info from Acaris and update CNmTDocumento / CnmTAllegato");


		// 20230301
		// nuova logica centralizzata per operazioni post sposta/copia (sincrono o asincrono)
		// la ricerca torna solo i documenti acta puntati dagli allegati in conam
		// per ognuno di questi, si aggiornano le informazioni sulle tabelle CnmTAllegato e CnmTDocumento
		// sulla CnmTDocumento si crea solo il record per il master acta (sempre, nel metodo sposta/copia) e per gli allegati acta se puntati da alleagti conam
		// flusso copia è come quello di sposta, ma viene aggiornata la parola chiave sul sb di conam uguale a quella del documento acta (già la ha, essendo già in conam)
		// è possibile che ci siano N allegati che puntano allo stesso documento acta (stesso objectIdSpostamento) -> lista cnmTAllegatoStessoObjectIdList generata per ogni documento acta che si processa sotto
		
		CnmTDocumento cnmTDocumentoMaster = getCnmTDocumentoRepository().findOne(cnmTSpostamentoActa.getIdDocumentoMaster());
		
		// 20200711_LC 
		CnmTUser cnmTUser = null;
		if (SecurityUtils.getAuthentication()==null) {
			cnmTUser = cnmTUserRepository.findByCodiceFiscaleAndFineValidita(DoquiConstants.USER_SCHEDULED_TASK);
		} else {
			UserDetails user = SecurityUtils.getUser();
			cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		}
		
		
		// recupero allegati del verbale per il protocollo spostato (objcetIdSpostamentoActa != null)
		// recupero e aggiornamento informazioni solo per questi documenti (e per il amster, che puo essere tra questi o no)
		List<CnmTAllegato> cnmTAllegatoProtocollatoDaAggiornareCompleteList = cnmTAllegatoRepository.findByNumeroProtocolloAndObjectidSpostamentoActaIsNotNull(cnmTSpostamentoActa.getNumeroProtocollo());

		//20201022_ET aggiungo filtro per aggiornare solo gli allegati legati al verbale in lavorazione
		// 20210420_LC se idVerbale è NULL, allora è un documento temporaneo (senza verbale) e non aggiorna la lista
		List<CnmTAllegato> cnmTAllegatoProtocollatoDaAggiornareList = new ArrayList<CnmTAllegato>();
		if (cnmTSpostamentoActa.getIdVerbale() != null) cnmTAllegatoProtocollatoDaAggiornareList = utilsVerbale.filtraAllegatiPerVerbale(cnmTSpostamentoActa.getIdVerbale(), cnmTAllegatoProtocollatoDaAggiornareCompleteList);
		else cnmTAllegatoProtocollatoDaAggiornareList = cnmTAllegatoProtocollatoDaAggiornareCompleteList;

		// con spring 3 non supportate le lambda expressions
		//		List<String> objectIdDocumentoList = cnmTAllegatoProtocollatoDaAggiornareList.stream().map(a -> a.getObjectidSpostamentoActa()).collect(Collectors.toList());
		List<String> objectIdDocumentoList = new ArrayList<>();
		for (CnmTAllegato a : cnmTAllegatoProtocollatoDaAggiornareList) objectIdDocumentoList.add(a.getObjectidSpostamentoActa());

		// torna solo documenti di interesse (master + allegati se tra quelli in CnmTAllegato con objectIdSpostamento != null), non serve quindi il controllo sotto
		List<DocumentoProtocollatoVO> listaNuoviDocumenti = manageRicercaDocumentoHelper.ricercaDocumentoProtocollatoPostSpostaCopia(cnmTSpostamentoActa.getNumeroProtocollo(), DoquiConstants.CODICE_FRUITORE, objectIdDocumentoList);

		if(listaNuoviDocumenti!=null && !listaNuoviDocumenti.isEmpty()) {

			for (DocumentoProtocollatoVO documentoNuovo : listaNuoviDocumenti) {

				String parolaChiave = null;
				
				// 20230302 - lista di tutti gli allegati che condividono l'objectId del documento acta che si sta processando
				List<CnmTAllegato> cnmTAllegatoStessoObjectIdList = new ArrayList<CnmTAllegato>();
				for (CnmTAllegato a : cnmTAllegatoProtocollatoDaAggiornareList) 
					// check se diverso da null: potrebbe esser stato reso null dalle iterazioni precedenti
					if (a.getObjectidSpostamentoActa() != null && a.getObjectidSpostamentoActa().equals(documentoNuovo.getObjectIdDocumento())) 
						cnmTAllegatoStessoObjectIdList.add(a);

				log.info("Aggiornamento allegati con objectIdSpostamento: " + documentoNuovo.getObjectIdDocumento());
				
				// 20201124_LC salva l'objectIdDOcumento per tracciarlo al livello superiore
				if (response != null && response.getObjectIdDocumentoToTraceList() != null) response.getObjectIdDocumentoToTraceList().add(documentoNuovo.getObjectIdDocumento());

				if (StringUtils.isBlank(documentoNuovo.getRegistrazioneId())){	// solo allegati (non hanno registrazioneId), se è quello in input

					// 20200722_LC crea nuovi documenti solo se sono tra quelli già associati al verbale come allegati (anche se poi su acta ce li sposta tutti)
					if (!cnmTAllegatoStessoObjectIdList.isEmpty()) {

						if(log.isDebugEnabled()) log.debug(method + ". Aggiornamento allegato al master (su Acta)"); 

						CnmTDocumento cnmTDocumentoAllegato = new CnmTDocumento();
						cnmTDocumentoAllegato.setFolder(cnmTSpostamentoActa.getFolder());	
						//cnmTDocumentoAllegato.setCnmDTipoDocumento(cnmDTipoDocumento);	

						// tipo documento
						// 20210524_LC in mancanza di byte[], true se è un p7m, false altrimenti
						boolean isDocSigned = false;
						//						if (request.getDocumento() != null && request.getDocumento().getNomeFile() != null && request.getDocumento().getNomeFile().toUpperCase().endsWith(".P7M"))
						//							isDocSigned = true;

						String tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_ACTA_SIGNED:DoquiConstants.TIPO_DOCUMENTO_ACTA;
						long tipoDocumento = cnmTAllegatoStessoObjectIdList.get(0).getCnmDTipoAllegato().getIdTipoAllegato();
						if (tipoDocumento == TipoAllegato.CONTRODEDUZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_INGIUNZIONE_PAGAMENTO.getId() 
								|| tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.ORDINANZA_ANNULLAMENTO_INGIUNZIONE.getId()
								|| tipoDocumento == TipoAllegato.ORDINANZA_ARCHIVIAZIONE.getId() || tipoDocumento == TipoAllegato.COMPARSA_ALLEGATO.getId() || tipoDocumento == TipoAllegato.ISTANZA_ALLEGATO.getId()) {
							tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_1_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_1;

						} else if (tipoDocumento == TipoAllegato.LETTERA_ORDINANZA.getId() || tipoDocumento == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()
								|| tipoDocumento == TipoAllegato.LETTERA_RATEIZZAZIONE.getId() || tipoDocumento == TipoAllegato.LETTERA_SOLLECITO.getId()
								|| tipoDocumento == TipoAllegato.LETTERA_SOLLECITO_RATE.getId()) {
							tipoDoc = isDocSigned?DoquiConstants.TIPO_DOCUMENTO_CONAM_2_SIGNED:DoquiConstants.TIPO_DOCUMENTO_CONAM_2;
						}
						CnmDTipoDocumento cnmDTipoDocumentoAllegato = getTipoDocumento(tipoDoc);
						cnmTDocumentoAllegato.setCnmDTipoDocumento(cnmDTipoDocumentoAllegato);


						cnmTDocumentoAllegato.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));	// 20200630_LC
						cnmTDocumentoAllegato.setCnmTUser2(cnmTUser);		// 20200702_LC


						// 20230302 qui nell'else deve entrarci quando, nella lista di allegati, ce ne sono N che condividono lo stesso objectIdSpostamento
						String entitaFruitore = null;
						String prefix = cnmTSpostamentoActa.getIdVerbale() != null ? cnmTSpostamentoActa.getIdVerbale().toString() : "TEMP"; // in caso di documento temporaneo (senza verbale)
						if (cnmTAllegatoStessoObjectIdList.size()==1) {
							entitaFruitore = prefix + " - " + cnmTAllegatoStessoObjectIdList.get(0).getCnmDTipoAllegato().getDescTipoAllegato();	// 20200708_LC
						} else {
							//entitaFruitore = request.getIdVerbale() + " - Documento contenente " + String.valueOf(cnmTAllegatoProtocollatoDaAggiornareList.size()) + " allegati";			
							entitaFruitore = prefix +  " - ";
							for (CnmTAllegato all:cnmTAllegatoStessoObjectIdList) {
								entitaFruitore =  entitaFruitore + all.getCnmDTipoAllegato().getDescTipoAllegato() + "; ";
							}
							entitaFruitore = entitaFruitore.substring(0, entitaFruitore.length()-2);
						}

						cnmTDocumentoAllegato.setIdentificativoEntitaFruitore(entitaFruitore);	// è dievrso da quellod el master

						cnmTDocumentoAllegato = getCnmTDocumentoRepository().save(cnmTDocumentoAllegato);
						// 20201007 PP - Se il doc su acta ha la parola chiave, allora la salvo sul db -	JIRA 100
						if(!StringUtils.isEmpty(documentoNuovo.getParolaChiave())) {
							cnmTDocumentoAllegato.setIdentificativoArchiviazione(documentoNuovo.getParolaChiave());
							// cnmTDocumentoAllegato.setIdentificativoArchiviazione(componiParolaChiave(String.valueOf(cnmTDocumentoAllegato.getIdDocumento())));	// 20200713_LC
						}


						CnmTDocumento cnmTDocumentoAllegatoN =getCnmTDocumentoRepository().save(cnmTDocumentoAllegato);	

						if(log.isDebugEnabled()){
							log.debug(method + ". inserito documento           = " + cnmTDocumentoAllegatoN);
						}


						//  20200804_LC non aggiorna parola chiave ed oggetto, ma deve impostare la parola chiave del nuovo doc uguale a quella del vecchio
						if (operazioneCopia) parolaChiave = manageRicercaDocumentoHelper.recuperaParolaChiave(documentoNuovo.getObjectIdDocumento());

						changeRiferimentiFornitoreDocumentoPostSpostamento(cnmTDocumentoAllegatoN.getIdDocumento(), documentoNuovo.getDocumentoUUID(), null,
								null, null,  documentoNuovo.getClassificazioneId(), documentoNuovo.getObjectIdDocumento(), parolaChiave);



						// aggiorna allegati corrispondenti al nuovo documento

						// 20230302 - aggiorna solo il cnmTAllegato relativo al documento acta che sta processando
						for (CnmTAllegato cnmTAllegatoDaAggiornare : cnmTAllegatoStessoObjectIdList) {
							cnmTAllegatoDaAggiornare.setIdActa(String.valueOf((cnmTDocumentoAllegato.getIdDocumento())));
							cnmTAllegatoDaAggiornare.setIdActaMaster(String.valueOf((cnmTDocumentoMaster.getIdDocumento())));
							cnmTAllegatoDaAggiornare.setObjectidSpostamentoActa(null); 	// 20200720_LC lo rimette null per evitare problemi nel retrieve
							cnmTAllegatoRepository.save(cnmTAllegatoDaAggiornare);	
						}

						// 20200721_LC aggiorna properties su Acta - parola chiave + oggetto
						// 20201007 PP - Commentato per non fare update della parolaChiave	-	JIRA 100
						// actaManagementService.aggiornaPropertiesActaPostSpostamento(documentoNuovo.getObjectIdDocumento(), componiParolaChiave(String.valueOf(cnmTDocumentoAllegatoN.getIdDocumento())), entitaFruitore, utenteActa);	// 20200713_LC

					}


				} else {	// il master (già protocollato) va solo aggiornato con idRegistrazione, classificazione_id e protocollo_fornitore
					
					if(log.isDebugEnabled()) log.debug(method + ". Aggiornamento allegato Master (su Acta)"); 
					
					if (response != null) response.setIndiceClassificazione(documentoNuovo.getClassificazione());
					

					// se doc temporaneo (Scritti difensivi), setta idEntitaFruitore: TEMP ...
					if (cnmTSpostamentoActa.getIdVerbale() == null) {
						cnmTDocumentoMaster.setIdentificativoEntitaFruitore("TEMP - Scritti difensivi");
						cnmTDocumentoMaster = getCnmTDocumentoRepository().save(cnmTDocumentoMaster);							
					}



					if (!cnmTAllegatoStessoObjectIdList.isEmpty()) {

						
						// 20230302 qui nell'else deve entrarci quando, nella lista di allegati, ce ne sono N che condividono lo stesso objectIdSpostamento
						// 20200804_LC gestione caso rapporto di trsmissione != vecchio master
						String entitaFruitore = null;				

						String prefix = cnmTSpostamentoActa.getIdVerbale() != null ? cnmTSpostamentoActa.getIdVerbale().toString() : "TEMP"; // in caso di documento temporaneo (senza verbale)
						if (cnmTAllegatoStessoObjectIdList.size()==1) {
							entitaFruitore = prefix + " - " + cnmTAllegatoStessoObjectIdList.get(0).getCnmDTipoAllegato().getDescTipoAllegato();	// 20200708_LC
						} else {
							//entitaFruitore = request.getIdVerbale() + " - Documento contenente " + String.valueOf(cnmTAllegatoProtocollatoDaAggiornareList.size()) + " allegati";										
							entitaFruitore = prefix +  " - ";
							for (CnmTAllegato all:cnmTAllegatoStessoObjectIdList) {
								entitaFruitore =  entitaFruitore + all.getCnmDTipoAllegato().getDescTipoAllegato() + "; ";
							}
							entitaFruitore = entitaFruitore.substring(0, entitaFruitore.length()-2);
						}

						cnmTDocumentoMaster.setIdentificativoEntitaFruitore(entitaFruitore);
						cnmTDocumentoMaster = getCnmTDocumentoRepository().save(cnmTDocumentoMaster);					



						//  20200804_LC non aggiorna parola chiave ed oggetto, ma deve impostare la parola chiave del nuovo doc uguale a quella del vecchio
						if (operazioneCopia) parolaChiave = manageRicercaDocumentoHelper.recuperaParolaChiave(documentoNuovo.getObjectIdDocumento());
						
						// 20210702_LC Jira 149 il nuovo CnmTDocumento per il master viene fatto in ogni caso (anche per documenti temporanei senza idVerbale); i suoi attributi vanno quindi aggiornati anche se cnmTAllegatoProtocollatoDaAggiornareList is empty
						changeRiferimentiFornitoreDocumentoPostSpostamento(cnmTDocumentoMaster.getIdDocumento(), documentoNuovo.getDocumentoUUID(), cnmTSpostamentoActa.getNumeroProtocollo(),
								documentoNuovo.getClassificazioneId(), documentoNuovo.getRegistrazioneId(), documentoNuovo.getClassificazioneId(), documentoNuovo.getObjectIdDocumento(), parolaChiave);

						// aggiorna allegati corrispondenti al nuovo documento
						
						// 20230302 - aggiorna solo il cnmTAllegato relativo al documento acta che sta processando (questo è il master, non ha idActaMaster)
						for (CnmTAllegato cnmTAllegatoDaAggiornare : cnmTAllegatoStessoObjectIdList) {
							cnmTAllegatoDaAggiornare.setIdActa(String.valueOf((cnmTDocumentoMaster.getIdDocumento())));
							//								cnmTAllegatoDaAggiornare.setIdActaMaster(String.valueOf((cnmTDocumentoMaster.getIdDocumento())));
							cnmTAllegatoDaAggiornare.setObjectidSpostamentoActa(null); 	// 20200720_LC lo rimette null per evitare problemi nel retrieve
							cnmTAllegatoRepository.save(cnmTAllegatoDaAggiornare);	
						}


						// 20200721_LC aggiorna properties su Acta - parola chiave + oggetto
						// 20201007 PP - Commentato per non fare update della parolaChiave	-	JIRA 100
						// actaManagementService.aggiornaPropertiesActaPostSpostamento(documentoNuovo.getObjectIdDocumento(), componiParolaChiave(String.valueOf(cnmTDocumentoN.getIdDocumento())), entitaFruitore, utenteActa);	// 20200713_LC


					}


				}


			}
		}			
	}
	
	@Override
	@Transactional
	public void checkUpdateStatusVerbale (CnmTSpostamentoActa cnmTSpostamentoActa, CnmTUser cnmTUser) {
	
		CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findByIdVerbale(cnmTSpostamentoActa.getIdVerbale());
		CnmDStatoVerbale cnmDStatoVerbaleNext = getStatoVerbaleNext(cnmTSpostamentoActa, cnmTUser, cnmTVerbale);
		
		try {
			if (cnmDStatoVerbaleNext!= null) {
				Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
				cnmTVerbale.setCnmDStatoVerbale(cnmDStatoVerbaleNext);
				cnmTVerbale.setDataOraProtocollo(now);
				cnmTVerbale.setDataOraUpdate(now);
				cnmTVerbale.setCnmTUser1(cnmTUser);
				cnmTVerbaleRepository.save(cnmTVerbale);					
			}
		}catch(Throwable t) {
			log.error("Errore in update stato verbale:" + cnmTSpostamentoActa, t);						
		}

	}

	private CnmDStatoVerbale getStatoVerbaleNext(CnmTSpostamentoActa cnmSpostamentoActaVerbale, CnmTUser cnmTUser, CnmTVerbale verbale) {
		
		log.info("updateVerbale - verifica per update stato finale verbale: " + verbale.getNumVerbale() + ", stato: " + verbale.getCnmDStatoVerbale().getDescStatoVerbale());

		List<CnmTSpostamentoActa> cnmSpostamentoActaList = cnmTSpostamentoActaRepository.findByIdVerbale(cnmSpostamentoActaVerbale.getIdVerbale());
		// solo per pregressi

		CnmDStatoVerbale cnmDStatoVerbaleNext = null;
		
		if(verbale.getCnmDStatoPregresso().getIdStatoPregresso() > 1) {
			boolean evasa = true;
			
			for(CnmTSpostamentoActa cnmSpostamentoActa : cnmSpostamentoActaList) {
				if(!cnmSpostamentoActa.getStato().equalsIgnoreCase(CnmTSpostamentoActa.STATO_EVASO)) {
					evasa = false;
					break;
				}
			}
			if(evasa) {
				if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ACQUISIZIONE) {
					cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO);
				} else if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ACQUISIZIONE_CON_PAGAMENTO) {
					cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
				} else if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ACQUISIZIONE_CON_SCRITTI_DIFENSIVI) {
					cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
				} else if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_IN_ARCHIVIATO_IN_AUTOTUTELA) {
					cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA);
				} else if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_PROTOCOLLAZIONE_IN_ATTESA_VERIFICA_PAGAMENTO) {
					cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_IN_ATTESA_VERIFICA_PAGAMENTO);
				} else if(verbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_PROTOCOLLAZIONE_PER_MANCANZA_CF) {
					cnmDStatoVerbaleNext = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_PER_MANCANZA_CF_SOGGETTO);
				}
			}
		}
		return cnmDStatoVerbaleNext;
		
	}
	
	public void tracciaSuCsiLogAudit(boolean isCopia, boolean isPregresso, List<String> objectIdDocumentoToTraceList) {

		if (objectIdDocumentoToTraceList != null) {

			for (String idToTrace : objectIdDocumentoToTraceList) {

				if(isCopia) {
					if(isPregresso)
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO_CI.getOperation(),Constants.OGGETTO_ACTA,"objectIdDocumento="+idToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "copiaDocumentoSuNuovaStruttura");
					else
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_CI.getOperation(),Constants.OGGETTO_ACTA,"objectIdDocumento="+idToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "copiaDocumentoSuNuovaStruttura");
				} else {
					if(isPregresso)
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_PREGRESSO_TI.getOperation(),Constants.OGGETTO_ACTA,"objectIdDocumento="+idToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "spostaDocumentoSuNuovaStruttura");
					else
						utilsTraceCsiLogAuditService.traceCsiLogAudit(TraceOperation.INSERIMENTO_ALLEGATO_TI.getOperation(),Constants.OGGETTO_ACTA,"objectIdDocumento="+idToTrace, Thread.currentThread().getStackTrace()[1].getMethodName(), "spostaDocumentoSuNuovaStruttura");
				}
				
			}
			
		}
	

	}
}
