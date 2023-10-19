/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the cnm_d_causale database table.
 * 
 */
@Entity
@Table(name="cnm_d_causale")
@NamedQuery(name="CnmDCausale.findAll", query="SELECT c FROM CnmDCausale c")
public class CnmDCausale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_causale")
	private long idCausale;

	@Column(name="desc_causale")
	private String descCausale;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	public CnmDCausale() {
	}

	public long getIdCausale() {
		return this.idCausale;
	}

	public void setIdCausale(long idCausale) {
		this.idCausale = idCausale;
	}

	public String getDescCausale() {
		return this.descCausale;
	}

	public void setDescCausale(String descCausale) {
		this.descCausale = descCausale;
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
