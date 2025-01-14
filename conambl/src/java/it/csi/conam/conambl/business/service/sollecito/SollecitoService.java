/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.sollecito;

import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.response.ImportoResponse;
import it.csi.conam.conambl.response.RiconciliaSollecitoResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.notifica.NotificaVO;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;

/**
 * @author riccardo.bova
 * @date 11 feb 2019
 */
public interface SollecitoService {

	List<SollecitoVO> getSollecitiByIdOrdinanzaSoggetto(Integer idOrdinanzaVerbaleSoggetto);

	Integer salvaSollecito(SollecitoVO sollecitoVO, NotificaVO notificaVO, UserDetails userDetails);

	void eliminaSollecito(Integer idSollecito);

	SollecitoVO getSollecitoById(Integer id);

	//RiconciliaSollecitoResponse riconcilaSollecito(SollecitoVO sollecito, UserDetails userDetails);

	ImportoResponse getImportiSollecitoByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog dto);

	Integer salvaSollecitoRate(SollecitoVO sollecitoVO, NotificaVO notificaVO, Integer idPianoRateizzazione, UserDetails userDetails);
	
	List<SollecitoVO> getSollecitiByIdPianoRateizzazione(Integer idPianoRateizzazione);
	
	//E14 aggiunta 20240722 Genco Pasqualini
	RiconciliaSollecitoResponse riconciliaSollecito (List<InputPart> data, List<InputPart> file, UserDetails userDetails);
	
	//E14 aggiunta 20240722 Genco Pasqualini
	RiconciliaSollecitoResponse riconciliaSollecito (SollecitoVO sollecito, UserDetails userDetails);
	
}
