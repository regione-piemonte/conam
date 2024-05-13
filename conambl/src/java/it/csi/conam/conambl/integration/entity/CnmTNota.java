/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the cnm_t_nota database table.
 * 
 */
@Entity
@Table(name = "cnm_t_nota")
@NamedQuery(name="CnmTNota.findAll", query="SELECT c FROM CnmTNota c")
public class CnmTNota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_nota")
	private Long idNota;

	@Column(name = "oggetto")
	private String oggetto;

	@Column(name = "desc_nota")
	private String desc;
	
	@Column(name = "date_nota")
	private Date date;

	//bi-directional many-to-one association to CnmDAmbito
	@ManyToOne
	@JoinColumn(name="id_ambito_note")
	private CnmDAmbitoNote cnmDAmbitoNote;

	@Column(name = "id_verbale")
	private Integer idVerbale;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_insert")
	private CnmTUser cnmTUser2;
	
	@Column(name = "data_ora_insert")
	private Timestamp dataOraInsert;

	// bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name = "id_user_update")
	private CnmTUser cnmTUser1;
	
	@Column(name = "data_ora_update")
	private Timestamp dataOraUpdate;



	public CnmTNota() {
	}

	public Long getIdNota() {
		return this.idNota;
	}

	public void setIdNota(Long idNota) {
		this.idNota = idNota;
	}

	public Timestamp getDataOraInsert() {
		return this.dataOraInsert;
	}

	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}

	public Timestamp getDataOraUpdate() {
		return this.dataOraUpdate;
	}

	public void setDataOraUpdate(Timestamp dataOraUpdate) {
		this.dataOraUpdate = dataOraUpdate;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public CnmDAmbitoNote getCnmDAmbitoNote() {
		return cnmDAmbitoNote;
	}

	public void setCnmDAmbitoNote(CnmDAmbitoNote cnmDAmbitoNote) {
		this.cnmDAmbitoNote = cnmDAmbitoNote;
	}

	public Integer getIdVerbale() {
		return idVerbale;
	}

	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}

	public CnmTUser getCnmTUser2() {
		return cnmTUser2;
	}

	public void setCnmTUser2(CnmTUser cnmTUser2) {
		this.cnmTUser2 = cnmTUser2;
	}

	public CnmTUser getCnmTUser1() {
		return cnmTUser1;
	}

	public void setCnmTUser1(CnmTUser cnmTUser1) {
		this.cnmTUser1 = cnmTUser1;
	}

}
