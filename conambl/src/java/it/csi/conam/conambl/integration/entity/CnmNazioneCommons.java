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
 * The Absract class for the cnm_d_nazione and cnm_s_nazione database table.
 * 
 */
@MappedSuperclass
public class CnmNazioneCommons implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="cod_belfiore_nazione")
	protected String codBelfioreNazione;

	@Column(name="cod_istat_nazione")
	protected String codIstatNazione;

	@Column(name="denom_nazione")
	protected String denomNazione;

	@Column(name="dt_id_stato")
	protected BigDecimal dtIdStato;

	@Column(name="dt_id_stato_next")
	protected BigDecimal dtIdStatoNext;

	@Column(name="dt_id_stato_prev")
	protected BigDecimal dtIdStatoPrev;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	protected Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	protected Date inizioValidita;

	public CnmNazioneCommons() {
	}

	public String getCodBelfioreNazione() {
		return this.codBelfioreNazione;
	}

	public void setCodBelfioreNazione(String codBelfioreNazione) {
		this.codBelfioreNazione = codBelfioreNazione;
	}

	public String getCodIstatNazione() {
		return this.codIstatNazione;
	}

	public void setCodIstatNazione(String codIstatNazione) {
		this.codIstatNazione = codIstatNazione;
	}

	public String getDenomNazione() {
		return this.denomNazione;
	}

	public void setDenomNazione(String denomNazione) {
		this.denomNazione = denomNazione;
	}

	public BigDecimal getDtIdStato() {
		return this.dtIdStato;
	}

	public void setDtIdStato(BigDecimal dtIdStato) {
		this.dtIdStato = dtIdStato;
	}

	public BigDecimal getDtIdStatoNext() {
		return this.dtIdStatoNext;
	}

	public void setDtIdStatoNext(BigDecimal dtIdStatoNext) {
		this.dtIdStatoNext = dtIdStatoNext;
	}

	public BigDecimal getDtIdStatoPrev() {
		return this.dtIdStatoPrev;
	}

	public void setDtIdStatoPrev(BigDecimal dtIdStatoPrev) {
		this.dtIdStatoPrev = dtIdStatoPrev;
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
