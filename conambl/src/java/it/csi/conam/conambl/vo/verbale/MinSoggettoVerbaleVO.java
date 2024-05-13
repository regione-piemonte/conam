/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class MinSoggettoVerbaleVO extends MinVerbaleVOCommons {

	private static final long serialVersionUID = -884217214905682332L;

	private Long idVerbale;
	private Long idSoggetto;
	private String note;
	private Boolean recidivo;

	private String descNormaViolata;

	public Long getIdVerbale() {
		return idVerbale;
	}

	public void setIdVerbale(Long idVerbale) {
		this.idVerbale = idVerbale;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getRecidivo() {
		return recidivo;
	}

	public void setRecidivo(Boolean recidivo) {
		this.recidivo = recidivo;
	}

	public String getDescNormaViolata() {
		return descNormaViolata;
	}

	public void setDescNormaViolata(String descNormaViolata) {
		this.descNormaViolata = descNormaViolata;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
