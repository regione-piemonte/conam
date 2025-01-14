package it.csi.conam.conambl.integration.auriga.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.mime.Attachment;
import org.springframework.ws.soap.SoapMessage;

import it.csi.conam.conambl.integration.entity.CnmCProprieta;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.repositories.CnmCProprietaRepository;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;


@Component
public class AurigaUtils {

	private static Logger logger = Logger.getLogger(AurigaUtils.class);

	@Autowired
	private CnmCProprietaRepository cnmCProprietaRepository;

	private static final String STILO_COD_APP = "STILO_COD_APP"; // codApplicazione = "SUA"
	private static final String STILO_ISTANZA_APP  = "STILO_ISTANZA_APP" ;// istanzaApplicazione = "";
	private static final String STILO_USERNAME = "STILO_USERNAME";// username = "FO-SUA";
	private static final String STILO_PASSWORD = "STILO_PASSWORD";// password = "Sportello2021!";
	private static final String STILO_BASE_PATH = "STILO_BASE_PATH";// basepath = "https://digidoc-coll.eng.it/AurigaBusinessSVIL/soap/";
	private static final String STILO_SCHEMA = "STILO_SCHEMA";// schema = "";
	private static final String STILO_USER_SEPARATOR = "STILO_USER_SEPARATOR";// userSeparator = "#:#";
	private static final String STILO_CONAM_SIGN = "STILO_CONAM_SIGN";// userSeparator = "#:#";
	private static final String STILO_API_MANAGER_URL = "STILO_API_MANAGER_URL";// 
	private static final String STILO_IS_WS = "STILO_IS_WS"; // se la connessione è tramite apiMAnager

	private String codApplicazione;
	private String istanzaApplicazione;
	private String username;
	private String password;
	private String basepath;
	private String schema;
	private String userSeparator;
	private String siglaNumerazione;
	private Boolean isWs;
	private String apiManagerUrl;
	private String apiManagerOauthUrl;
	private String apiManagerUser;// 		utilsCnmCProprietaService.getProprieta(PropKey.APIMANAGER_CONSUMERKEY);
	private String apiManagerPassword;// =	utilsCnmCProprietaService.getProprieta(PropKey.APIMANAGER_CONSUMERSECRET);
		
	public static boolean codeTest = false;
	public static boolean mockLivelli = true;

	public OauthHelper getApimanagrAuth() {
		if(apiManagerOauthUrl == null) {
			apiManagerOauthUrl = cnmCProprietaRepository.findByNome(PropKey.APIMANAGER_OAUTHURL.toString()).getValore();
		}
		OauthHelper apiManagerOauth = new OauthHelper(apiManagerOauthUrl,
				this.getApiManagerUser(),
				this.getApiManagerPassword());
		
		return apiManagerOauth;
	}
	

	public String getApiManagerUser() {
		if(apiManagerUser == null) {
			apiManagerUser = cnmCProprietaRepository.findByNome(PropKey.APIMANAGER_CONSUMERKEY.toString()).getValore();
		}
		return apiManagerUser;
	}

	public String getApiManagerPassword() {
		if(apiManagerPassword == null) {
			apiManagerPassword = cnmCProprietaRepository.findByNome(PropKey.APIMANAGER_CONSUMERSECRET.toString()).getValore();
		}
		return apiManagerPassword;
	}
	
	
	public boolean isWs() {
		if(this.isWs == null){
			CnmCProprieta cnmCProprieta = cnmCProprietaRepository.findByNome(STILO_IS_WS);
			isWs = Boolean.parseBoolean(cnmCProprieta.getValore());
		}
		return isWs;
	}

	public String getApiManagerUrl() {
		if(this.apiManagerUrl == null){
			CnmCProprieta cnmCProprieta = cnmCProprietaRepository.findByNome(STILO_API_MANAGER_URL);
			apiManagerUrl = cnmCProprieta.getValore();
		}
		return apiManagerUrl;
	}
	
	public String getSiglaNumerazione() {
		if(this.siglaNumerazione == null){
			CnmCProprieta cnmCProprieta = cnmCProprietaRepository.findByNome(STILO_CONAM_SIGN);
			siglaNumerazione = cnmCProprieta.getValore();
		}
		return siglaNumerazione;
	}
	public String getCodApplicazione() {
		if(this.codApplicazione == null){
			CnmCProprieta cnmCProprieta = cnmCProprietaRepository.findByNome(STILO_COD_APP);
			codApplicazione = cnmCProprieta.getValore();
		}
		return codApplicazione;
	}
	
	public String getIstanzaApplicazione() {
		if(this.istanzaApplicazione == null){
			CnmCProprieta cnmCProprieta = cnmCProprietaRepository.findByNome(STILO_ISTANZA_APP);
			istanzaApplicazione = cnmCProprieta.getValore();
		}
		return istanzaApplicazione;
	}

	public String getUsername() {
		if(this.username == null){
			CnmCProprieta cnmCProprieta = cnmCProprietaRepository.findByNome(STILO_USERNAME);
			username = cnmCProprieta.getValore();
		}
		return username;
	}
	
	public String getPassword() {
		if(this.password == null){
			CnmCProprieta cnmCProprieta = cnmCProprietaRepository.findByNome(STILO_PASSWORD);
			password = cnmCProprieta.getValore();
		}
		return password;
	}
	
	public String getBasepath() {
		if(this.basepath == null){
			CnmCProprieta cnmCProprieta = cnmCProprietaRepository.findByNome(STILO_BASE_PATH);
			basepath = cnmCProprieta.getValore();
		}
		return basepath;
	}
	
	public String getSchema() {
		if(this.schema == null){
			CnmCProprieta cnmCProprieta = cnmCProprietaRepository.findByNome(STILO_SCHEMA);
			schema = cnmCProprieta.getValore();
		}
		return schema;
	}

	public String getUserSeparator() {
		if(this.userSeparator == null){
			CnmCProprieta cnmCProprieta = cnmCProprietaRepository.findByNome(STILO_USER_SEPARATOR);
			userSeparator = cnmCProprieta.getValore();
		}
		return userSeparator;
	}



	public static String calcolaHash(String strIn) throws Exception {
		
		String hashOut = null;

		Base64 encoder = new Base64();
		
		// creo il digester
		java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA");
		md.update( strIn.getBytes() );

		// calcolo lo sha-1
		byte[] digest = md.digest();
		// lo codifico base64
		byte[] hashByte = encoder.encode(digest);
		
		hashOut = new String(hashByte, StandardCharsets.UTF_8);
		
		return hashOut;
	}

	public static String decodeBase64(String strIn) {
		
		String decodeOut = null;

		Base64 encoder = new Base64();
		byte[] hashByte = encoder.decode(strIn.getBytes());
		
		decodeOut = new String(hashByte, StandardCharsets.UTF_8);
		
		return decodeOut;
	}

	public static String wrapXML(String xml) {
		return "<![CDATA[" + xml + "]]>";
	}
	
	public static <T> String getXmlStringByObject(T obj)  throws TransformerException {
		try {
			JAXBContext jaxbContextIn = JAXBContext.newInstance(obj.getClass());
			Marshaller jaxbMarshaller = jaxbContextIn.createMarshaller();
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(obj, sw);
			return sw.toString();
		} catch (JAXBException e) {
			logger.info("ERRORE E' "+e);
			throw new TransformerException(e.getMessage());
		}
	}
	
	public static <T> T getObjectFromXMlString(String response, Class<T> type) throws TransformerException {
		try {
			logger.debug("[getObjectFromXMlString] type: {"+type.getClass().getName()+"} - response: {"+response+"}");



			response = sanityzeXml(response);
			StringReader reader = new StringReader(response);
			JAXBContext jaxbContext = JAXBContext.newInstance(type);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return type.cast(unmarshaller.unmarshal(reader));
		} catch (JAXBException e) {
			throw new TransformerException(e.getMessage());
		}
	}
	
	public static String getXmlStringFromAttachment(WebServiceMessage message) throws IOException {
		String outputUdResponse = null;
		
		Iterator<Attachment> attachmentIterator = ((SoapMessage) message).getAttachments();
		if (attachmentIterator != null) {
			if (attachmentIterator.hasNext()) {
				Attachment next = attachmentIterator.next();
				InputStream inputStream = next.getInputStream();
				outputUdResponse = new BufferedReader(
						new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
								.collect(Collectors.joining("\n"));
				outputUdResponse = StringEscapeUtils.unescapeXml(outputUdResponse);

			}
		}
		return outputUdResponse;
	}
	
	public static String sanityzeXml(String xml) {

		xml = xml.replaceAll("<WarningMessage>.*</WarningMessage>","");

		return xml.replaceAll("\\u00A0", " ");
	}

	public static Map<String, Attachment> getFilesFromAttachment(List<String> filenames, WebServiceMessage message) {
		Map<String, Attachment> files = new HashMap<>();
		Iterator<Attachment> attachmentIterator = ((SoapMessage) message).getAttachments();
		if (attachmentIterator != null) {
			if (attachmentIterator.hasNext()) {
				attachmentIterator.next(); //skip metadata attachment
			}
			int i = 0;
			while (attachmentIterator.hasNext()) {
				Attachment attachment = (Attachment) attachmentIterator.next();
				files.put(filenames.get(i), attachment);
				i++;
            }
		}
		return files;
	}

	public static Map<String, Attachment> getFilesAttachmentZip(List<String> filenames, WebServiceMessage message) {
		Map<String, Attachment> files = new HashMap<>();
		Iterator<Attachment> attachmentIterator = ((SoapMessage) message).getAttachments();
		if (attachmentIterator != null) {
			int i = 0;
			while (attachmentIterator.hasNext()) {
				Attachment attachment = (Attachment) attachmentIterator.next();
				files.put(filenames.get(i), attachment);
				i++;
			}
		}
		return files;
	}

	
}
