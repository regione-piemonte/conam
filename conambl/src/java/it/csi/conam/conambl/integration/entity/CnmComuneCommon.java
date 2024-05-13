/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The abstract class for the cnm_s_comune and cnm_d_comune database table.
 * 
 */
@MappedSuperclass
public abstract class CnmComuneCommon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="cod_belfiore_comune")
	private String codBelfioreComune;

	@Column(name="cod_istat_comune")
	private String codIstatComune;

	@Column(name="denom_comune")
	private String denomComune;

	@Column(name="dt_id_comune")
	private BigDecimal dtIdComune;

	@Column(name="dt_id_comune_next")
	private BigDecimal dtIdComuneNext;

	@Column(name="dt_id_comune_prev")
	private BigDecimal dtIdComunePrev;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	public CnmComuneCommon() {
	}

	public String getCodBelfioreComune() {
		return this.codBelfioreComune;
	}

	public void setCodBelfioreComune(String codBelfioreComune) {
		this.codBelfioreComune = codBelfioreComune;
	}

	public String getCodIstatComune() {
		return this.codIstatComune;
	}

	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	public String getDenomComune() {
		return this.denomComune;
	}

	public void setDenomComune(String denomComune) {
		this.denomComune = denomComune;
	}

	public BigDecimal getDtIdComune() {
		return this.dtIdComune;
	}

	public void setDtIdComune(BigDecimal dtIdComune) {
		this.dtIdComune = dtIdComune;
	}

	public BigDecimal getDtIdComuneNext() {
		return this.dtIdComuneNext;
	}

	public void setDtIdComuneNext(BigDecimal dtIdComuneNext) {
		this.dtIdComuneNext = dtIdComuneNext;
	}

	public BigDecimal getDtIdComunePrev() {
		return this.dtIdComunePrev;
	}

	public void setDtIdComunePrev(BigDecimal dtIdComunePrev) {
		this.dtIdComunePrev = dtIdComunePrev;
	}

	public Date getFineValidita() {
		return this.fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public Date getInizioValidita() {
		return this.inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}

}
