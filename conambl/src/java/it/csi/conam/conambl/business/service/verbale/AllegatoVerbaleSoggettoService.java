/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.verbale;

import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;

import java.util.List;

public interface AllegatoVerbaleSoggettoService {

	List<CnmTAllegato> salvaVerbaleAudizione(List<Integer> idVerbaleSoggettoList, byte[] response, UserDetails user);

	List<CnmTAllegato> salvaConvocazioneAudizione(List<Integer> idVerbaleSoggettoList, byte[] response, UserDetails user);

	Boolean isAllegatoVerbaleSoggettoCreato(CnmRVerbaleSoggetto cnmRVerbaleSoggetto, TipoAllegato tipo);

	Integer getIdVerbaleAudizione(CnmRVerbaleSoggetto cnmRVerbaleSoggetto, TipoAllegato tipo);

	// 20200824_LC	-byte[]
	List<DocumentoScaricatoVO> downloadVerbaleAudizione(List<Integer> idVerbaleSoggettoList);

	// 20200824_LC	-byte[]
	List<DocumentoScaricatoVO> downloadConvocazioneAudizione(List<Integer> idVerbaleSoggettoList);

	DatiTemplateVO nomeVerbaleAudizione(List<Integer> idVerbaleSoggettoList);

	DatiTemplateVO nomeConvocazioneAudizione(List<Integer> idVerbaleSoggettoList);

}
