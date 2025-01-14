package it.csi.conam.conambl.integration.auriga.service.getAllFilesInFolder;


import it.csi.conam.conambl.integration.auriga.bean.ConamDocument;
import it.csi.conam.conambl.integration.auriga.bean.TipoRegistrazione;
import it.csi.conam.conambl.integration.auriga.model.BaseOutputWS;
import it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.Lista;
import it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.TrovaDocFolder;
import it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.service.Service;
import it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.service.ServiceResponse;
import it.csi.conam.conambl.integration.auriga.service.AurigaUtils;
import it.csi.conam.conambl.integration.auriga.service.MyWebServiceGatewaySupport;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import javax.annotation.PostConstruct;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

@Component
public class NewGetAllFilesInFolderWS extends MyWebServiceGatewaySupport {

	private static Logger logger = Logger.getLogger(NewGetAllFilesInFolderWS.class);

	@Autowired
	private AurigaUtils aurigaUtils;

  private static final String CONTEXT_PATH = "it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder";
  //TODO cambio il valore mettendo it.csi.conam.conambl.integration.auriga.model.getDetermina
  private static final String GET_ALL_FILES_IN_FOLDER_ACTION = "WSTrovaDocFolder/serviceOperation";
  //TODO GET_DETERMINA_ACTION = WSGetDetermina/serviceOperation
  private static final String GET_ALL_FILES_IN_FOLDER_URI = "WSTrovaDocFolder";
  //TODO GET_DETERMINA_URI = WSGetDetermina

  
  @PostConstruct
  public void init() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath(CONTEXT_PATH);
    setMarshaller(marshaller);
    setUnmarshaller(marshaller);
  }

  public Service buildRequest(String xml, String codiceFiscaleOperatore) throws Exception {//TODO il codifiscale va tolto
    Service request = new Service();
    request.setCodApplicazione(aurigaUtils.getCodApplicazione());
    request.setIstanzaApplicazione(aurigaUtils.getIstanzaApplicazione());
    //request.setUserName(AurigaUtils.username);
    if (StringUtils.isNotBlank(codiceFiscaleOperatore)) {
      request.setUserName(aurigaUtils.getUsername().concat(aurigaUtils.getUserSeparator()).concat(codiceFiscaleOperatore));
    } else {
      request.setUserName(aurigaUtils.getUsername()); //TODO quello sopra si tolgiel e rimane solo questo
    }
    request.setPassword(aurigaUtils.getPassword());
    request.setXml(xml);
    request.setHash(AurigaUtils.calcolaHash(xml));
    //TODO i log vanno cambiati
    logger.info("getAllFilesInUD");
    logger.info("xml: {"+xml+"}");
    logger.info("hash: {"+AurigaUtils.calcolaHash(xml)+"}");
    return request;
  }


  public List<ConamDocument> getAllFilesInFolder(TrovaDocFolder folder, String codiceFiscaleOperatore) throws Exception {

    String xml = AurigaUtils.getXmlStringByObject(folder);

    if (xml != null && logger.isDebugEnabled()) {
      logger.info("[GetAllFilesInFolderWS] - XML: " + xml);
    }

    Service request = buildRequest(xml, codiceFiscaleOperatore);
    logger.info("ENTRO METODO LISTA - 1");
    List<ConamDocument> output = getAllFilesInFolderResponse(aurigaUtils.getBasepath() + GET_ALL_FILES_IN_FOLDER_ACTION, request);
    logger.info("ENTRO METODO LISTA - 2");
    return output;
  }


  private List<ConamDocument> getAllFilesInFolderResponse(String action, Service request) throws Exception {
    logger.info("ENTRO METODO LISTA - 0");
    List<ConamDocument> output = getMyWebServiceTemplate().sendAndReceive(
      aurigaUtils.getBasepath() + GET_ALL_FILES_IN_FOLDER_URI,
            getWebServiceMessageCallback(action, request),
            getWebServiceMessageExtractor());
    return output;

  }

  private WebServiceMessageExtractor<List<ConamDocument>> getWebServiceMessageExtractor() {
    return new WebServiceMessageExtractor<List<ConamDocument>>() {
      public List<ConamDocument> extractData(WebServiceMessage message) throws IOException, TransformerException {
        logger.info("ENTRO METODO LISTA");
        org.springframework.oxm.Unmarshaller unmarshaller = getUnmarshaller();
        ServiceResponse serviceResponse = (ServiceResponse) MarshallingUtils.unmarshal(unmarshaller, message);
        String baseOutputWSXml = AurigaUtils.decodeBase64(serviceResponse.getServiceReturn());

        BaseOutputWS baseOutput = AurigaUtils.getObjectFromXMlString(baseOutputWSXml, BaseOutputWS.class);
        List<ConamDocument> files = new ArrayList<>();

        if (baseOutput.getWSResult().equals("1")) {
          String listaXml = AurigaUtils.getXmlStringFromAttachment(message);
          logger.debug("[GetAllFilesInFolderWS] xml output: " + listaXml);
          Lista lista = AurigaUtils.getObjectFromXMlString(listaXml, Lista.class);
          if (lista != null) {
            List<Lista.Riga> elencoRiga = lista.getRiga();
            for (Lista.Riga riga : elencoRiga) {
              ConamDocument docEng = new ConamDocument();
              for (Lista.Riga.Colonna colonna : riga.getColonna()) {
                if (colonna.getNro().equals(BigInteger.valueOf(2))) {   //ID UID
                  docEng.setIdUD(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(33))) {   //ID DOC Documento Primarii
                  docEng.setIdDoc(colonna.getContent());
                }

                if (colonna.getNro().equals(BigInteger.valueOf(4)) || colonna.getNro().equals(BigInteger.valueOf(8)) || colonna.getNro().equals(BigInteger.valueOf(10)) || colonna.getNro().equals(BigInteger.valueOf(12)) || colonna.getNro().equals(BigInteger.valueOf(14))) {   //Estremi Registrazione
                  settaParametriRegistrazione(colonna, docEng);
                }

                if (colonna.getNro().equals(BigInteger.valueOf(201))) { //Data Registrazione
                  docEng.setDataRegistrazione(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(31))) { //Id tipologia del documento
                  docEng.setTipologiaDocumento(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(32))) { //Decodifica Tipologia Documento
                  //docEng.setVerso(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(20))) { //VErso
                  docEng.setVerso(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(202))) { //Data Arrivo/invio PEC
                  docEng.setDataArrivo(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(18))) { //Oggetto
                  docEng.setOggetto(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(264))) { //Canale mezzo Trasmissione
                  docEng.setCanaleTrasmissione(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(308))) { //Presente altra pratica sua
                  //docEng.setNativoSUA(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(311))) { //INFO FILE
                  //docEng.(colonna.getContent());
                }
                /*
                if (colonna.getNro().equals(BigInteger.valueOf(309))) { //FILE ALLEGATI
                  creaAllegatiEngramma(colonna.getContent(), docEng);
                }
                 */
                if (colonna.getNro().equals(BigInteger.valueOf(310))) { //INFO FILE
                  //docEng.(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(262))) { //INFO FILE
                  docEng.setNativoSUA(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(91))) { //MITTENTE
                  docEng.setMittente(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(92))) { //DESTINATARIO
                  docEng.setDestinatario(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(42))) { //DESTINATARIO
                  docEng.setAssegnatario(colonna.getContent());
                }
                if (colonna.getNro().equals(BigInteger.valueOf(316))) { //FLAG (richiesta associazione)
                  if ("0".equals(colonna.getContent())) {
                    docEng.setRichiestaAssociazione(false);
                  } else if ("1".equals(colonna.getContent())) {
                    docEng.setRichiestaAssociazione(true);
                  }
                }
                if (colonna.getNro().equals(BigInteger.valueOf(309))) {
                  String[] dati = colonna.getContent().split(";");
                  if (dati.length >= 17) {
                    docEng.setAlgoritmoImpronta(dati[13]);
                    docEng.setDecodificaImpronta(dati[14]);
                    docEng.setImpronta(dati[15]);             //IMPRONTA FILE
                    docEng.setCodiciFiscaliFirmatari(dati[16]);   //CF FIRMATARI
                  }
                }
              }
              logger.info("Carico oggetto: " + docEng);
              files.add(docEng);
            }
          }
        } else {
          if (logger.isDebugEnabled()) {
            logger.error("[GetAllFilesInFolderWS] - Error: " + baseOutputWSXml);
            logger.error(baseOutput.getWSError().getErrorMessage());
          }
        }
        return files;
      }
    };
  }

  private WebServiceMessageCallback getWebServiceMessageCallback(String action,
                                                                 Service request) {
    return new WebServiceMessageCallback() {

      public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
        SoapMessage soapMessage = (SoapMessage) message;
        soapMessage.setSoapAction(action);

        org.springframework.oxm.Marshaller marshaller = getMarshaller();
        MarshallingUtils.marshal(marshaller, request, message);
      }
    };
  }

  private void settaParametriRegistrazione(Lista.Riga.Colonna colonna, ConamDocument docEng) {

//
//<Colonna Nro="10">Rep. TE 14/09/2023.0000007.U</Colonna>
//<Colonna Nro="14">BOL 2023.0000505.U</Colonna>
//<Colonna Nro="8">Prot. 14/09/2023.0001673.E</Colonna>
    String pattern = "(.*)([\\d+]{2}\\/[\\d+]{2}\\/[\\d+]{4}).([\\d+]+).([a-zA-Z]{1})";

    Pattern r = Pattern.compile(pattern);

    if (StringUtils.isNotBlank(colonna.getContent())) {

      Matcher m = r.matcher(colonna.getContent());

      if (colonna.getNro().equals(BigInteger.valueOf(4))) {
        if (m.find() && m.groupCount() == 4) {

          docEng.setNumeroRegistrazione(m.group(3));
          docEng.setDataRegistrazione(m.group(2));
          String[] siglaArr = m.group(1).trim().split(" ");
          String sigla = siglaArr.length > 1 ? siglaArr[1].trim().toUpperCase() : siglaArr[0].trim().toUpperCase();
          docEng.setSiglaRegistrazione(sigla);
        }
      } else if (colonna.getNro().equals(BigInteger.valueOf(8))) {
        docEng.setTipoRegistrazione(TipoRegistrazione.PROTOCOLLO.getValue());
        docEng.setSiglaRegistrazione(TipoRegistrazione.PROTOCOLLO.getValue());
      } else if (colonna.getNro().equals(BigInteger.valueOf(10))) {
        docEng.setTipoRegistrazione(TipoRegistrazione.REPERTORIO.getValue());
      } else if (colonna.getNro().equals(BigInteger.valueOf(14)) && StringUtils.isBlank(docEng.getTipoRegistrazione())) {
        docEng.setTipoRegistrazione(TipoRegistrazione.ALTRA_NUMERAZIONE.getValue());
      }
    }
  }
  /*

  private void creaAllegatiEngramma(String elencoAllegati, ConamDocument docEng) {
    if (!StringUtils.isBlank(elencoAllegati)) {
      List<AllegatoEngramma> elencoAllegato = new ArrayList<>();
      //String[] fileAllegati = allegati.split("#");
      String[] fileAllegati = elencoAllegati.split("#");
      for (String allegato : fileAllegati) {
        AllegatoEngramma allegatoObj = new AllegatoEngramma();
        String[] campiAllegato = allegato.split(";");

        allegatoObj.setNumAllegato(campiAllegato[0]);
        allegatoObj.setIdDoc(campiAllegato[1]);
        allegatoObj.setFlagPrivacy(campiAllegato[2]);
        allegatoObj.setNomeFile(campiAllegato[3]);
        allegatoObj.setMimetype(campiAllegato[4]);
        allegatoObj.setFlagFirmato(campiAllegato[5]);
        allegatoObj.setNumeroUltimaVersione(campiAllegato[6]);
        allegatoObj.setIdTipoDocumento(campiAllegato[7]);
        allegatoObj.setDecodificaTipoDocumento(campiAllegato[8]);
        allegatoObj.setFlagTimbrato(campiAllegato[9]);

        try {
          allegatoObj.setDescrizioneAllegato(campiAllegato[10]);
        } catch (Exception e) {
          logger.error("[creaAllegatiEngramma] - Campo descrizioneAllegato non presente: {}", e.getMessage());
          allegatoObj.setDescrizioneAllegato(Strings.EMPTY);
        }
        try {
          allegatoObj.setFileTimbrabile(campiAllegato[11]);
        } catch (Exception e) {
          logger.error("[creaAllegatiEngramma] - Campo fileTimbrabile non presente: {}", e.getMessage());
          allegatoObj.setFileTimbrabile(Strings.EMPTY);
        }
        try {
          allegatoObj.setFileSize(campiAllegato[12]);
        } catch (Exception e) {
          logger.error("[creaAllegatiEngramma] - Campo fileSize non presente: {}", e.getMessage());
          allegatoObj.setImpronta(Strings.EMPTY);
        }
        try {
          allegatoObj.setAlgoritmoImpronta(campiAllegato[13]);
        } catch (Exception e) {
          logger.error("[creaAllegatiEngramma] - Campo algoritmoImpronta non presente: {}", e.getMessage());
          allegatoObj.setAlgoritmoImpronta(Strings.EMPTY);
        }
        try {
          allegatoObj.setEncodingImpronta(campiAllegato[14]);
        } catch (Exception e) {
          logger.error("[creaAllegatiEngramma] - Campo encodingImpronta non presente: {}", e.getMessage());
          allegatoObj.setEncodingImpronta(Strings.EMPTY);
        }
        try {
          allegatoObj.setImpronta(campiAllegato[15]);
        } catch (Exception e) {
          logger.error("[creaAllegatiEngramma] - Campo encodingImpronta non presente: {}", e.getMessage());
          allegatoObj.setEncodingImpronta(Strings.EMPTY);
        }

        elencoAllegato.add(allegatoObj);
      }
      docEng.setAllegati(elencoAllegato);
    }
  }
   */
}