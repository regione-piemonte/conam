/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.spi.validation.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.conam.conambl.business.service.epay.EpayService;
import it.csi.conam.conambl.integration.epay.from.EsitoInserimentoListaDiCaricoRequest;
import it.csi.conam.conambl.integration.epay.from.EsitoInserimentoType;
import it.csi.conam.conambl.integration.epay.from.EsitoInserimentoType.ElencoPosizioniDebitorieInserite;
import it.csi.conam.conambl.integration.epay.from.PosizioneDebitoriaType;
import it.csi.conam.conambl.util.SpringSupportedResource;

/**
 * @author riccardo.bova
 * @date 09 gen 2019
 */
@Path("test")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class TestResource extends SpringSupportedResource {

	@Autowired
	private EpayService epayService;

	protected static Logger logger = Logger.getLogger(TestResource.class);
	@GET
	@Path("/notificaBollettiniRateizzazione")
	public Response notificaBollettiniRateizzazione() {
		
		EsitoInserimentoListaDiCaricoRequest parameters = new EsitoInserimentoListaDiCaricoRequest();
		EsitoInserimentoType esito = new EsitoInserimentoType();
		ElencoPosizioniDebitorieInserite elenco = new ElencoPosizioniDebitorieInserite();
		PosizioneDebitoriaType posizione = new PosizioneDebitoriaType();
		posizione.setCodiceAvviso("322200210521067041");
		posizione.setCodiceEsito("000");
		posizione.setDescrizioneEsito("OK");
		posizione.setIdPosizioneDebitoria("020820231307BAIGPP79H16L219K03PIANORAT"); // recuperato dalla tabella cnm_r_sogg_rata
		posizione.setIUV("00031421423453425341");
		elenco.getPosizioneDebitoriaInserita().add(posizione);
		posizione = new PosizioneDebitoriaType();
		posizione.setCodiceAvviso("322200210521067042");
		posizione.setCodiceEsito("000");
		posizione.setDescrizioneEsito("OK");
		posizione.setIdPosizioneDebitoria("020820231307BAIGPP79H16L219K02PIANORAT");
		posizione.setIUV("00031421423453425342");
		elenco.getPosizioneDebitoriaInserita().add(posizione);
		posizione = new PosizioneDebitoriaType();
		posizione.setCodiceAvviso("322200210521067043");
		posizione.setCodiceEsito("000");
		posizione.setDescrizioneEsito("OK");
		posizione.setIdPosizioneDebitoria("020820231307BAIGPP79H16L219K03PIANORAT");
		posizione.setIUV("00031421423453425343");
		elenco.getPosizioneDebitoriaInserita().add(posizione);
		esito.setElencoPosizioniDebitorieInserite(elenco );
		parameters.setEsitoInserimento(esito );
		logger.info("simulazionechiamata epay. Resource esitoInserimentoListaDiCarico");
		epayService.esitoInserimentoListaDiCarico(parameters);
		
		return Response.ok().entity("OK").build();
	}
}
