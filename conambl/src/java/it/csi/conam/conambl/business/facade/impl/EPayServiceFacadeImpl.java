/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.facade.impl;

import it.csi.conam.conambl.business.facade.EPayServiceFacade;
import it.csi.conam.conambl.business.service.util.UtilsCnmCProprietaService;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.common.config.Config;
import it.csi.conam.conambl.common.exception.RemoteWebServiceException;
import it.csi.conam.conambl.integration.entity.CnmCProprieta.PropKey;
import it.csi.conam.conambl.integration.epay.to.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;

/**
 * @author riccardo.bova
 * @date 29 gen 2019
 */
@Service
public class EPayServiceFacadeImpl implements EPayServiceFacade, InitializingBean {

	@Autowired
	private Config config;

	@Autowired
	private UtilsCnmCProprietaService utilsCnmCProprietaService;
	
	private static final Logger logger = Logger.getLogger(EPayServiceFacadeImpl.class);

	private Enti2EPaywsoServiceSOAPStub binding;

	private static final String PIEMONTE_PAY_ESITO_OK = "000";

	@Override
	public void afterPropertiesSet() throws Exception {
		Enti2EPaywsoService_ServiceLocator locator = new Enti2EPaywsoService_ServiceLocator();
		boolean isSecured = Boolean.valueOf(utilsCnmCProprietaService.getProprieta(PropKey.EPAY_WSSECURED));
		if(isSecured) {
			locator.setEnti2EPaywsoServiceSOAPEndpointAddress(utilsCnmCProprietaService.getProprieta(PropKey.EPAY_ENDPOINT_SECURED));
		}else {
			locator.setEnti2EPaywsoServiceSOAPEndpointAddress(utilsCnmCProprietaService.getProprieta(PropKey.EPAY_ENDPOINT));
		}
//		locator.setEnti2EPaywsoServiceSOAPEndpointAddress(config.getEpayServiceEndpointUrl());
		binding = (Enti2EPaywsoServiceSOAPStub) locator.getEnti2EPaywsoServiceSOAP();
	}

	@Override
	public void inserisciListaDiCarico(InserisciListaDiCaricoRequest inserisciListaDiCaricoRequest) {
		ResponseType response;
		try {

			boolean isSecured = Boolean.valueOf(utilsCnmCProprietaService.getProprieta(PropKey.EPAY_WSSECURED));
			if(isSecured) {				
				String wsUser= utilsCnmCProprietaService.getProprieta(PropKey.EPAY_WSUSER);
				String wsPWD= utilsCnmCProprietaService.getProprieta(PropKey.EPAY_WSPASS);
				response = binding.inserisciListaDiCarico(inserisciListaDiCaricoRequest, wsUser, wsPWD);
			} else {
				response = binding.inserisciListaDiCarico(inserisciListaDiCaricoRequest);				
			}
		} catch (RemoteException e) {
			throw new RemoteWebServiceException(ErrorCode.PIEMONTE_PAY_LISTA_CARICO_NON_DISPONIBILE);
		}

		if (response == null) {
			logger.error("Piemonte pay ha risposto con null");
			throw new RemoteWebServiceException(ErrorCode.PIEMONTE_PAY_LISTA_CARICO_CON_ERRORI);
		}

		ResultType result = response.getResult();
		String codice = result.getCodice();

		if (!codice.equals(PIEMONTE_PAY_ESITO_OK)) {
			logger.error("Piemonte pay ha risposto con il seguente codice di errore: " + codice + " e messaggio: " + result.getMessaggio());
			throw new RemoteWebServiceException(ErrorCode.PIEMONTE_PAY_LISTA_CARICO_CON_ERRORI);
		}
	}

}
