/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale.allegato;
/**
 *  @author riccardo.bova
 *  @date 21 nov 2018
 */

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import it.csi.conam.conambl.vo.ParentVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPagamentoVO;

public class ModificaAllegatoFieldVO extends ParentVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	List<AllegatoFieldVO> allegatoFields;
	List<SoggettoPagamentoVO> soggetti;
	Long idAllegato;
	
	
	ModificaAllegatoFieldVO() {
		super();
	}

	public ModificaAllegatoFieldVO(List<AllegatoFieldVO> allegatoFields, Long idAllegato,
			List<SoggettoPagamentoVO> soggetti) {
		super();
		this.allegatoFields = allegatoFields;
		this.idAllegato = idAllegato;
		this.soggetti = soggetti;
	}




	public List<AllegatoFieldVO> getAllegatoFields() {
		return allegatoFields;
	}




	public void setAllegatoFields(List<AllegatoFieldVO> allegatoFields) {
		this.allegatoFields = allegatoFields;
	}




	public Long getIdAllegato() {
		return idAllegato;
	}




	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	}




	public List<SoggettoPagamentoVO> getSoggetti() {
		return soggetti;
	}




	public void setSoggetti(List<SoggettoPagamentoVO> soggetti) {
		this.soggetti = soggetti;
	}




	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
