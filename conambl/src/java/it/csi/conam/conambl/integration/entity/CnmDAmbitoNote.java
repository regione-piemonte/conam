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
 * The persistent class for the cnm_d_ambito_note database table.
 * 
 */
@Entity
@Table(name="cnm_d_ambito_note")
@NamedQuery(name="CnmDAmbitoNote.findAll", query="SELECT c FROM CnmDAmbitoNote c")
public class CnmDAmbitoNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ambito_note")
	private Long idAmbitoNote;

	@Column(name="desc_ambito_note")
	private String descAmbitoNote;

	@Temporal(TemporalType.DATE)
	@Column(name="fine_validita")
	private Date fineValidita;

	@Temporal(TemporalType.DATE)
	@Column(name="inizio_validita")
	private Date inizioValidita;

	public CnmDAmbitoNote() {
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

	public Long getIdAmbitoNote() {
		return idAmbitoNote;
	}

	public void setIdAmbitoNote(Long idAmbitoNote) {
		this.idAmbitoNote = idAmbitoNote;
	}

	public String getDescAmbitoNote() {
		return descAmbitoNote;
	}

	public void setDescAmbitoNote(String descAmbitoNote) {
		this.descAmbitoNote = descAmbitoNote;
	}


}
