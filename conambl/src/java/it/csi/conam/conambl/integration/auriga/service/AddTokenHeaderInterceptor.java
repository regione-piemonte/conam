package it.csi.conam.conambl.integration.auriga.service;

import javax.wsdl.extensions.soap.SOAPHeader;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;

public class AddTokenHeaderInterceptor implements ClientInterceptor {

	private String token;
	public AddTokenHeaderInterceptor(String token) {
		this.token = token;
	}
	@Override
	public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		// TODO Auto-generated method stub
		
	     TransportContext context = TransportContextHolder.getTransportContext();
	     HttpUrlConnection myURLConnection = (HttpUrlConnection) context.getConnection();
	     
	     
//	     myURLConnection.setRequestProperty ("Authorization", basicAuth);
//	     myURLConnection.setRequestMethod("POST");
//         PostMethod postMethod = connection.getPostMethod();
//         postMethod.addRequestHeader( "Authorization", "Bearer " + token );
//	     HttpComponentsConnection connection =(HttpComponentsConnection) context.getConnection();
//	     WebServiceConnection  connection =(WebServiceConnection) context.getConnection();
//	     connection.
//	     connection.addRequestHeader("name", "suman");
	     System.out.println("test");
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		// TODO Auto-generated method stub
		return true;
	}

}
