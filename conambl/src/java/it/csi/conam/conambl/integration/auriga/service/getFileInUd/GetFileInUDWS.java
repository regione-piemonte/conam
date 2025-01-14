package it.csi.conam.conambl.integration.auriga.service.getFileInUd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.mime.Attachment;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import it.csi.conam.conambl.integration.auriga.model.BaseOutputWS;
import it.csi.conam.conambl.integration.auriga.model.getFileInUD.EstremiXidentificazioneVerDocType;
import it.csi.conam.conambl.integration.auriga.model.getFileInUD.OutputFileUDToExtract;
import it.csi.conam.conambl.integration.auriga.model.getFileInUD.service.Service;
import it.csi.conam.conambl.integration.auriga.model.getFileInUD.service.ServiceResponse;
import it.csi.conam.conambl.integration.auriga.service.AurigaUtils;
import it.csi.conam.conambl.integration.auriga.service.MyWebServiceGatewaySupport;
import it.csi.conam.conambl.integration.auriga.service.getAllFilesInFolder.NewGetAllFilesInFolderWS;
import org.apache.log4j.Logger;

@Component
public class GetFileInUDWS extends MyWebServiceGatewaySupport {

	private static Logger logger = Logger.getLogger(GetFileInUDWS.class);

	@Autowired
	private AurigaUtils aurigaUtils;

	private static final String CONTEXT_PATH = "it.csi.conam.conambl.integration.auriga.model.getFileInUD";
	private static final String GET_FILE_IN_UD_ACTION = "WSExtractOne/serviceOperation";
	private static final String GET_FILE_IN_UD_URI = "WSExtractOne";


	@PostConstruct
	public void init() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(CONTEXT_PATH);
		setMarshaller(marshaller);
		setUnmarshaller(marshaller);
	}
	
	public Service buildRequest(String xml, String codiceFiscaleOperatore) throws Exception {
		Service request = new Service();
		request.setCodApplicazione(aurigaUtils.getCodApplicazione());
		request.setIstanzaApplicazione(aurigaUtils.getIstanzaApplicazione());
		//request.setUserName(AurigaUtils.username);
		if (StringUtils.isNotBlank(codiceFiscaleOperatore)) {
			request.setUserName(aurigaUtils.getUsername().concat(aurigaUtils.getUserSeparator()).concat(codiceFiscaleOperatore));
		} else {
			request.setUserName(aurigaUtils.getUsername());
		}
		request.setPassword(aurigaUtils.getPassword());
		request.setXml(xml);
		request.setHash(AurigaUtils.calcolaHash(xml));
		logger.info("getFileInUD");
		logger.info("xml: {"+xml+"}");
		logger.info("hash: {"+AurigaUtils.calcolaHash(xml)+"}");
		return request;
	}


	public Map<String, Attachment> getFileInUD(EstremiXidentificazioneVerDocType ud,String codiceFiscaleOperatore) throws Exception {

		String xml = AurigaUtils.getXmlStringByObject(ud);
		
		if(xml != null && logger.isDebugEnabled()) {
			logger.info("[GetFileInUDWS] - XML: " + xml);
		}
		
		Service request = buildRequest(xml,codiceFiscaleOperatore);

		Map<String, Attachment> output = getFileInUDResponse(aurigaUtils.getBasepath() + GET_FILE_IN_UD_ACTION, request);
		
		return output;
	}


	private Map<String, Attachment> getFileInUDResponse(String action, Service request) throws Exception   {

		Map<String, Attachment> output = (Map<String, Attachment>) getMyWebServiceTemplate().sendAndReceive(
				aurigaUtils.getBasepath() + GET_FILE_IN_UD_URI,
				getWebServiceMessageCallback(action, request), 
				getWebServiceMessageExtractor());
		return output;

	}

	private WebServiceMessageExtractor<Map<String, Attachment>> getWebServiceMessageExtractor() {
		return new WebServiceMessageExtractor<Map<String, Attachment>>() {
			public Map<String, Attachment> extractData(WebServiceMessage message) throws IOException, TransformerException {

				org.springframework.oxm.Unmarshaller unmarshaller = getUnmarshaller();
				ServiceResponse serviceResponse = (ServiceResponse) MarshallingUtils.unmarshal(unmarshaller, message);
				String baseOutputWSXml = AurigaUtils.decodeBase64(serviceResponse.getServiceReturn());

				BaseOutputWS baseOutput = AurigaUtils.getObjectFromXMlString(baseOutputWSXml, BaseOutputWS.class);
				Map<String, Attachment> files = null;
				if (baseOutput.getWSResult().equals("1")) {
					String outputFilesXml = AurigaUtils.getXmlStringFromAttachment(message);
					OutputFileUDToExtract outputFile = AurigaUtils.getObjectFromXMlString(outputFilesXml, OutputFileUDToExtract.class);
					List<String> fileNames = new ArrayList<>();
					fileNames.add(outputFile.getNomeFile());
					files = AurigaUtils.getFilesFromAttachment(fileNames, message);
				} else {
					if(logger.isDebugEnabled()) {
						logger.error("[GetFileInUDWS] - Error: " + baseOutputWSXml);
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

	
	
	


}
