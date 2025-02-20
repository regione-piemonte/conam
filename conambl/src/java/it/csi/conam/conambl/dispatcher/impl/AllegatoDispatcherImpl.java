/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.dispatcher.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.dispatcher.AllegatoDispatcher;
import it.csi.conam.conambl.response.RicercaDocumentiStiloResponse;
import it.csi.conam.conambl.response.RicercaProtocolloSuActaResponse;
import it.csi.conam.conambl.vo.CampiRicercaFormVO;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.ConfigAllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.DettaglioAllegatoFieldVO;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Component
public class AllegatoDispatcherImpl implements AllegatoDispatcher {

	@Autowired
	private CommonAllegatoService commonAllegatoService;

	@Override
	public List<ConfigAllegatoVO> getConfigAllegati() {
		return commonAllegatoService.getConfigAllegati();
	}

	@Override
	public List<DocumentoScaricatoVO> downloadAllegatoById(Integer id) {
		return commonAllegatoService.downloadAllegatoById(id);
	}

	@Override
	public List<SelectVO> getDecodificaSelectAllegato(Long decodificaSelect) {
		return commonAllegatoService.getDecodificaSelectAllegato(decodificaSelect);
	}
	
	@Override
	public List<SelectVO> getDecodificaSelectSoggettiAllegato(Integer idverbale) {
		return commonAllegatoService.getDecodificaSelectSoggettiAllegato(idverbale);
	}

	@Override
	public List<SelectVO> getDecodificaSelectSoggettiAllegatoCompleto(Integer idverbale) {
		return commonAllegatoService.getDecodificaSelectSoggettiAllegatoCompleto(idverbale);
	}
	
	// 20200903_LC gestione pregresso
	//20220321_SB modifica per gestione della paginazione nella ricerca
	@Override
	public RicercaProtocolloSuActaResponse ricercaProtocolloSuACTA(
			String numProtocollo, Integer idVerbale, Boolean flagPregresso, Integer pageRequest, Integer maxLineRequest
		) {
			return commonAllegatoService.ricercaProtocolloSuACTA(numProtocollo, idVerbale, flagPregresso, pageRequest, maxLineRequest);
	}

	// 20200711_LC
	@Override
	public List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumento(String objectIdDocumento) {
		return commonAllegatoService.downloadAllegatoByObjectIdDocumento(objectIdDocumento);
	}

	// 20200711_LC
	@Override
	public List<DocumentoScaricatoVO> downloadAllegatoByObjectIdDocumentoFisico(String objectIdDocumentoFisico) {
		return commonAllegatoService.downloadAllegatoByObjectIdDocumentoFisico(objectIdDocumentoFisico);
	}
	
	
	// 20200827_LC
	@Override
	public List<DocumentoScaricatoVO> getDocFisiciByIdAllegato(Integer id) {
		return commonAllegatoService.getDocFisiciByIdAllegato(id);
	}
	
	// 20200827_LC	
	@Override
	public List<DocumentoScaricatoVO> getDocFisiciByObjectIdDocumento(String objectIdDocumento) {
		return commonAllegatoService.getDocFisiciByObjectIdDocumento(objectIdDocumento);
	}

	//E19_2022 - OBI45
	@Override
	public RicercaDocumentiStiloResponse ricercaDocumentiSuStilo(
			CampiRicercaFormVO input, Integer pageRequest, Integer maxLineRequest
			) {
		return commonAllegatoService.ricercaDocumentiSuStilo(input, pageRequest, maxLineRequest);
	}
	
	@Override
	public List<ConfigAllegatoVO> getConfigAllegatiRicerca(Long idRicerca) {
		return commonAllegatoService.getConfigAllegatiRicerca(idRicerca);
	}

	@Override
	public DettaglioAllegatoFieldVO getDettaglioFieldsByIdAllegato(Long idAllegato) {
		return commonAllegatoService.getDettaglioFieldsByIdAllegato(idAllegato);
	}

	
}

