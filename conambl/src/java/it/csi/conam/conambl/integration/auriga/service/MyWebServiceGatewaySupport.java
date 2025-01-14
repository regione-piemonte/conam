package it.csi.conam.conambl.integration.auriga.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.transport.WebServiceMessageSender;
//import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;


@Component
public class MyWebServiceGatewaySupport extends WebServiceGatewaySupport {

	private static Logger log = Logger.getLogger(MyWebServiceGatewaySupport.class);

	public WebServiceTemplate getMyWebServiceTemplate() {
		
		WebServiceTemplate mytemplate = super.getWebServiceTemplate();
//		ClientInterceptor[] interceptors = new ClientInterceptor[1];
//		interceptors[0] = new AddTokenHeaderInterceptor(token);
//		mytemplate.setInterceptors(interceptors);
		
		boolean timeoutSet = false;
		
		for(WebServiceMessageSender item : mytemplate.getMessageSenders()) {
			try
            {	
                // TODO - da configurare su DB
                int readTimeoutMsec = 10000;//readTimeoutMillis;
                int connTimeoutMsec = 10000;//connectTimeoutMillis;
                
//                if(item instanceof HttpComponentsMessageSender) {
//                	HttpComponentsMessageSender httpSender = (HttpComponentsMessageSender) item;
//                    httpSender.setReadTimeout(readTimeoutMsec);
//                    httpSender.setConnectionTimeout(connTimeoutMsec);
//                }else { 
                	HttpUrlConnectionMessageSender httpSender = (HttpUrlConnectionMessageSender) item;
                	//httpSender.setReadTimeout(readTimeoutMsec);
                    //httpSender.setConnectionTimeout(connTimeoutMsec);
//                } 

                timeoutSet =  true;
            }
            catch (ClassCastException|NumberFormatException cex){
            	cex.printStackTrace();
            }
		}
		
		if(!timeoutSet) {
			log.warn("Cannot set WS timeout on WebServiceTemplate");
		}
		
		return mytemplate;
		
	}
	
}
