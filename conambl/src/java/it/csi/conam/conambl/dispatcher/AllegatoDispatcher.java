/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.conam.conambl.common.security.AuthorizationRoles;
import it.csi.conam.conambl.response.RicercaDocumentiStiloResponse;
import it.csi.conam.conambl.response.RicercaProtocolloSuActaResponse;
import it.csi.conam.conambl.vo.CampiRicercaFormVO;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.ConfigAllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.DettaglioAllegatoFieldVO;

/**
 * @author riccardo.bova
 * @date 23 mar 2018
 */
public interface AllegatoDispatcher {

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ConfigAllegatoVO> getConfigAllegati();

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentoScaricatoVO> downloadAllegatoById(Integer id);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<SelectVO> getDecodificaSelectAllegato(Long decodificaSelect);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<SelectVO> getDecodificaSelectSoggettiAllegato(Integer idverbale);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<SelectVO> getDecodificaSelectSoggettiAllegatoCompleto(Integer idverbale);
	
	// 20200903_LC gestione pregresso (nuova resposne)
	//20220321_SB modifica per gestione della paginazione nella ricerca
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	RicercaProtocolloSuActaResponse ricercaProtocolloSuACTA(
		String numProtocollo, Integer idVerbale, Boolean flagPregresso, Integer pageRequest, Integer maxLineRequest);

	// 20200717_LC
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumento(String objectIdDocumento);

	// 20200825_LC
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumentoFisico(String objectIdDocumentoFisico);

	// 20200827_LC
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentoScaricatoVO> getDocFisiciByIdAllegato(Integer id);
	
	// 20200827_LC
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<DocumentoScaricatoVO> getDocFisiciByObjectIdDocumento(String objectIdDocumento);
	

	//E19_2022 - OBI45
	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	RicercaDocumentiStiloResponse ricercaDocumentiSuStilo(
			CampiRicercaFormVO input, Integer pageRequest, Integer maxLineRequest);


	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	List<ConfigAllegatoVO> getConfigAllegatiRicerca(Long idRicerca);

	@PreAuthorize(value = AuthorizationRoles.UTENTE)
	DettaglioAllegatoFieldVO getDettaglioFieldsByIdAllegato(Long idAllegato);


}
