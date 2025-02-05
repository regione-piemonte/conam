package it.csi.conam.conambl.integration.auriga.service.getDetermina;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.soap.MimeHeaders;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.mime.Attachment;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import it.csi.conam.conambl.integration.auriga.model.BaseOutputWS;
import it.csi.conam.conambl.integration.auriga.model.getDetermina.RequestGetDetermina;
import it.csi.conam.conambl.integration.auriga.model.getDetermina.ResponseGetDetermina;
import it.csi.conam.conambl.integration.auriga.model.getDetermina.ResponseGetDetermina.Determina;
import it.csi.conam.conambl.integration.auriga.model.getDetermina.ResponseGetDetermina.Determina.AttachmentFiles.File;
import it.csi.conam.conambl.integration.auriga.model.getDetermina.service.Service;
import it.csi.conam.conambl.integration.auriga.model.getDetermina.service.ServiceResponse;
import it.csi.conam.conambl.integration.auriga.service.AurigaUtils;
import it.csi.conam.conambl.integration.auriga.service.MyWebServiceGatewaySupport;
import it.csi.conam.conambl.vo.verbale.DocumentoStiloVO;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;
import it.csi.wso2.apiman.oauth2.helper.extra.cxf.CxfImpl;

@Component
public class GetDeterminaWS extends MyWebServiceGatewaySupport {

	private static Logger logger = Logger.getLogger(GetDeterminaWS.class);

	@Autowired
	private AurigaUtils aurigaUtils;

  private static final String CONTEXT_PATH = "it.csi.conam.conambl.integration.auriga.model.getDetermina";
  private static final String GET_DETERMINA_ACTION = "WSGetDetermina/serviceOperation";
  //TODO GET_DETERMINA_ACTION = WSGetDetermina/serviceOperation
  private static final String GET_DETERMINA_URI = "WSGetDetermina";
  //TODO GET_DETERMINA_URI = WSGetDetermina
  

  public String apiManagerToken = "";

  
  @PostConstruct
  public void init() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath(CONTEXT_PATH);
    setMarshaller(marshaller);
    setUnmarshaller(marshaller);
  }

  public Service buildRequest(String xml) throws Exception {//TODO il codifiscale va tolto
    Service request = new Service();
    request.setCodApplicazione(aurigaUtils.getCodApplicazione());
    request.setIstanzaApplicazione(aurigaUtils.getIstanzaApplicazione());
    
    request.setUserName(aurigaUtils.getUsername()); 
    request.setPassword(aurigaUtils.getPassword());
    
    request.setXml(xml);
    request.setHash(AurigaUtils.calcolaHash(xml));
    //TODO i log vanno cambiati
    logger.info("GetDeterminaWS");
    logger.info("xml: {"+xml+"}");
    logger.info("hash: {"+AurigaUtils.calcolaHash(xml)+"}");
    return request;
  }


  public List<DocumentoStiloVO> searchDetermina(RequestGetDetermina getDetermina) throws Exception {

    String xml = AurigaUtils.getXmlStringByObject(getDetermina);

    if (xml != null && logger.isDebugEnabled()) {
      logger.info("[GetDeterminaWS] - XML: " + xml);
    }

    Service request = buildRequest(xml);
    logger.info("ENTRO METODO LISTA - 1");
    List<DocumentoStiloVO> output = searchDetermina(aurigaUtils.getBasepath() + GET_DETERMINA_ACTION, request);
    logger.info("ENTRO METODO LISTA - 2");
    
    for(DocumentoStiloVO doc : output) {
    	doc.setAnno(getDetermina.getIDDetermina().getAnno());
    	doc.setNumero(getDetermina.getIDDetermina().getNumero().toString());
    }
    return output;
  }


  private List<DocumentoStiloVO> searchDetermina(String action, Service request) throws Exception {
    logger.info("ENTRO METODO LISTA - 0");
    refreshApiManagerToken();
    String url = aurigaUtils.getBasepath() + GET_DETERMINA_URI;
    List<DocumentoStiloVO> output = null;
    if(!aurigaUtils.isWs()) {
    	output = getMyWebServiceTemplate().sendAndReceive(
    			url,
            getWebServiceMessageCallback(action, request, apiManagerToken),
            getWebServiceMessageExtractor());
    }else {
    	url = aurigaUtils.getApiManagerUrl();
    	output = getMyWebServiceTemplate().sendAndReceive(
    			url,
            getWebServiceMessageCallback(null, request, apiManagerToken),
            getWebServiceMessageExtractor());
    	
    }
    return output;

  }

	private void refreshApiManagerToken() {
		
		String apiManagerUser = aurigaUtils.getApiManagerUser();
		String apiManagerPassword = aurigaUtils.getApiManagerPassword();
		OauthHelper apiManagerOauth = aurigaUtils.getApimanagrAuth();
		TokenRetryManager trm = new TokenRetryManager();

		trm.setOauthHelper(apiManagerOauth);
		WsProvider wsp = new CxfImpl();
		
		trm.setWsProvider(wsp);
		String encoded = new String(Base64.getEncoder().encode((apiManagerUser+":"+apiManagerPassword).getBytes()));
		
		trm.putHeader(TokenRetryManager.X_AUTH, "Basic " + encoded);
		logger.info("Token: " + trm.getOauthHelper().getNewToken());
		
		apiManagerToken = trm.getOauthHelper().getToken();
	
}

	private WebServiceMessageExtractor<List<DocumentoStiloVO>> getWebServiceMessageExtractor() {
		return new WebServiceMessageExtractor<List<DocumentoStiloVO>>() {
			public List<DocumentoStiloVO> extractData(WebServiceMessage message)
					throws IOException, TransformerException {
				logger.info("ENTRO METODO LISTA");
				org.springframework.oxm.Unmarshaller unmarshaller = getUnmarshaller();
				ServiceResponse serviceResponse = (ServiceResponse) MarshallingUtils.unmarshal(unmarshaller, message);
				String baseOutputWSXml = AurigaUtils.decodeBase64(serviceResponse.getServiceReturn());
				logger.info("Auriga respose : " + baseOutputWSXml);
				BaseOutputWS baseOutput = AurigaUtils.getObjectFromXMlString(baseOutputWSXml, BaseOutputWS.class);
				List<DocumentoStiloVO> files = new ArrayList<>();

				if (baseOutput.getWSResult().equals("1")) {
					Iterator<Attachment> attachments = null;
					if (message instanceof SaajSoapMessage) {
						SaajSoapMessage sm = (SaajSoapMessage) message;
						attachments = sm.getAttachments();
					}
					if (attachments != null && attachments.hasNext()) {
						Attachment dd = attachments.next();
						String listaDetermina = AurigaUtils.getXmlStringFromAttachment(message);
						logger.debug("[GetDeterminaWS] xml output: " + listaDetermina);
						ResponseGetDetermina resp = AurigaUtils.getObjectFromXMlString(listaDetermina,
								ResponseGetDetermina.class);
						if (resp != null) {
							Determina determina = resp.getDetermina();
							for (File fileDetermina : determina.getAttachmentFiles().getFile()) {
								DocumentoStiloVO docEng = new DocumentoStiloVO();
								docEng.setFilename(fileDetermina.getDisplayFilename());
								docEng.setOggetto(determina.getOggettoPubbl());
								docEng.setData(determina.getIDProposta().getTsRegistrazione().toString());
								try {
									Attachment att = attachments.next();
									docEng.setFile(IOUtils.toByteArray(att.getInputStream()));
								} catch (Throwable t) {
									logger.warn("[GetDeterminaWS] file byte problem", t);
								}
								logger.info("Carico oggetto: " + docEng);
								files.add(docEng);
							}
						}
					}
				} else {
					if (logger.isDebugEnabled()) {
						logger.error("[GetDeterminaWS] - Error: " + baseOutputWSXml);
						logger.error(baseOutput.getWSError().getErrorMessage());
					}
				}
				return files;
			}
		};
	}
	
  private WebServiceMessageCallback getWebServiceMessageCallback(String action,
                                                                 Service request, String token) {
    return new WebServiceMessageCallback() {

      public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
    	 
	        SoapMessage soapMessage = (SoapMessage) message;
	        soapMessage.setSoapAction(action==null?"":action);
    	  
		  boolean ok = true;
		  if(ok) {
	        SaajSoapMessage sssjSoapMessage = (SaajSoapMessage) message;
	        MimeHeaders mimeHeader = sssjSoapMessage.getSaajMessage().getMimeHeaders();
	        mimeHeader.setHeader("Authorization", "Bearer " +token);
		  
    	  }
        
        org.springframework.oxm.Marshaller marshaller = getMarshaller();
        MarshallingUtils.marshal(marshaller, request, message);

      }
    };
  }

}