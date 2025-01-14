package it.csi.conam.conambl.integration.auriga.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.mime.Attachment;

import it.csi.conam.conambl.integration.auriga.bean.ConamConcreteDocument;
import it.csi.conam.conambl.integration.auriga.bean.ConamDocument;
import it.csi.conam.conambl.integration.auriga.bean.DocumentFile;
import it.csi.conam.conambl.integration.auriga.exception.DocumentaleException;
import it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.TrovaDocFolder;
import it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.TrovaDocFolder.FiltriPrincipali;
import it.csi.conam.conambl.integration.auriga.model.getDetermina.RequestGetDetermina;
import it.csi.conam.conambl.integration.auriga.model.getFileInUD.EstremiXIdentificazioneFileUDType;
import it.csi.conam.conambl.integration.auriga.model.getFileInUD.EstremiXidentificazioneVerDocType;
import it.csi.conam.conambl.integration.auriga.service.getAllFilesInFolder.CercaDocumentiByRequest;
import it.csi.conam.conambl.integration.auriga.service.getAllFilesInFolder.NewGetAllFilesInFolderWS;
import it.csi.conam.conambl.integration.auriga.service.getDetermina.GetDeterminaWS;
import it.csi.conam.conambl.integration.auriga.service.getFileInUd.GetFileInUDWS;
import it.csi.conam.conambl.vo.verbale.DocumentoStiloVO;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;

@Service
public class AurigaServiceImpl implements AurigaService {

	private static Logger logger = Logger.getLogger(AurigaService.class);

	@Autowired
	private NewGetAllFilesInFolderWS aurigaGetAllFilesInFolderNew;

	@Autowired
	private GetFileInUDWS aurigaGetFileInUD;
	

	@Autowired
	private GetDeterminaWS getDeterminaWS;

	@Autowired
	private AurigaUtils aurigaUtils;

	@Override
	public List<ConamDocument> cercaDocumentiByRequest(CercaDocumentiByRequest request) throws DocumentaleException {
		logger.info("ENTRO NUOVO METODO");
		if (request != null) {
			List<ConamDocument> documentEng;
			TrovaDocFolder tdf = new TrovaDocFolder();
			FiltriPrincipali fp = new FiltriPrincipali();
			fp.setTipoOggettiDaCercare("D");
			/*
			CercaInFolder cif = new CercaInFolder();
			cif.setIncludiSubFolder("0");
			cif.setIdFolder(new BigInteger(folder.getIdFolder()));
			
			fp.setCercaInFolder(cif);
			 */
			tdf.setFiltriPrincipali(fp);
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(2));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(33));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(4));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(8));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(10));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(12));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(14));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(201));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(31));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(32));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(20));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(91));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(92));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(202));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(18));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(264));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(42));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(309));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(310));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(311));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(262));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(316));
			tdf.getLimitaEstrazioneAlCampo().add(BigInteger.valueOf(309));
			TrovaDocFolder.Ordinamento.PerCampo perCampo = new TrovaDocFolder.Ordinamento.PerCampo();
			perCampo.setValue(BigInteger.valueOf(201));
			// tdf.getOrdinamento().getPerCampo().add(perCampo);

			try {
				documentEng = aurigaGetAllFilesInFolderNew.getAllFilesInFolder(tdf, request.getCodiceFiscale());
			} catch (Exception e) {
				logger.error("Errore nel recupero dei file della cartella: " + e.getMessage(), e);
				throw new DocumentaleException("Errore nel recupero dei file della cartella");
			}
			return documentEng;
		} else {
			List<ConamDocument> documentEng = new ArrayList<>();
			return documentEng;
		}

	}
	@Override
	public ConamConcreteDocument getDocumentById(String idDocument, String operatoreCF) throws DocumentaleException {
		return getDocumentByIdAndVersion(idDocument, null, null, operatoreCF, false);
	}

	@Override
	public ConamConcreteDocument getDocumentByIdAndVersion(String idDocument, String version, String idDoc, String operatoreCF, boolean pdfVersion)
			throws DocumentaleException {
		/*
		 * Auriga gestisce l'unità documentale; l'unità documentale ha sempre un
		 * documento principale e può avere N allegati. Ciascun documento pertanto è
		 * individuato dall'identificativo dell'unità documentale (idUd) e dal numero
		 * dell'allegato (k) Per convenzione consideriamo idDocumento = idUd-k, idUd-0
		 * è il documento principale idUd-n è l'n-esimo allegato
		 */
		if (idDocument.contains("-")) {
			BigInteger idUd = new BigInteger(idDocument.split("-")[0]);
			BigInteger nAllegato = new BigInteger(idDocument.split("-")[1]);

			EstremiXidentificazioneVerDocType edoc = new EstremiXidentificazioneVerDocType();

			if(pdfVersion) {
				edoc.setGetVersPdf(BigInteger.valueOf(1L));		
			}

			it.csi.conam.conambl.integration.auriga.model.getFileInUD.EstremiXIdentificazioneUDType ud = new it.csi.conam.conambl.integration.auriga.model.getFileInUD.EstremiXIdentificazioneUDType();
			ud.setIdUD(idUd);

			EstremiXIdentificazioneFileUDType fud = new EstremiXIdentificazioneFileUDType();
			if (StringUtils.isNotBlank(version)) {
				fud.setNroVersione(new BigInteger(version));
			}
			if (nAllegato.compareTo(BigInteger.ZERO) > 0) {
				fud.setNroAllegato(nAllegato);
			} else {
				if (StringUtils.isBlank(idDoc))
					fud.setFlagPrimario("1");
			}
			if (StringUtils.isNotBlank(idDoc)) {
				fud.setIdDoc(new BigInteger(idDoc));
			}
			edoc.setEstremiXIdentificazioneUD(ud);
			edoc.setEstremixIdentificazioneFileUD(fud);

			Map<String, Attachment> files;
			try {

				files = aurigaGetFileInUD.getFileInUD(edoc, operatoreCF);
				if (files == null) {
					throw new DocumentaleException("Nessun file trovato per il documento: " + idDocument);
				}
			} catch (Exception e) {
				logger.error("[getFileInUD] Errore nel recupero del documento: " + e.getMessage(), e);
				throw new DocumentaleException("Errore nel recupero del documento");
			}
			Entry<String, Attachment> entry = files.entrySet().iterator().next();
			String filename = entry.getKey();
			Attachment attachment = entry.getValue();

			ConamConcreteDocument doc = new ConamConcreteDocument();
			doc.setIdDocument(idDocument);
			DocumentFile file = new DocumentFile();
			file.setContentType(attachment.getContentType());
			file.setFilename(filename);
			file.setSize(attachment.getSize());

			try {
				InputStream initialStream = attachment.getInputStream();
				byte[] content = new byte[initialStream.available()];
				initialStream.read(content);
				file.setContent(content);
			} catch (IOException e) {
				logger.error("[getInputStream] Errore nel recupero del documento: " + e.getMessage(), e);
				throw new DocumentaleException("Errore nel recupero del documento");
			}
			doc.setFile(file);
			return doc;

		} else {
			logger.error("Errore nel recupero del documento - Id non valido: " + idDocument);
			throw new DocumentaleException("Errore nel recupero del documento - Id non valido");
		}
	}
	@Override
	public List<DocumentoStiloVO> searchDetermina(RequestGetDetermina getDetermina) throws DocumentaleException {
		logger.info("Call searchDetermina");
		if (getDetermina != null) {
			List<DocumentoStiloVO> documentEng;			

			try {
				getDetermina.getIDDetermina().setTipoNumerazione(aurigaUtils.getSiglaNumerazione());
					
				documentEng = getDeterminaWS.searchDetermina(getDetermina);
				
			} catch (Exception e) {
				logger.error("Errore nel recupero dei file della cartella: " + e.getMessage());
				throw new DocumentaleException("Errore nel recupero dei file della cartella");
			}
			return documentEng;
		} else {
			List<DocumentoStiloVO> documentEng = new ArrayList<>();
			return documentEng;
		}
	}

}
