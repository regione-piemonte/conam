/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;


/**
 * The persistent class for the cnm_s_comune database table.
 * 
 */
@Entity
@Table(name="cnm_s_comune")
@NamedQuery(name="CnmSComune.findAll", query="SELECT c FROM CnmSComune c")
public class CnmSComune extends CnmComuneCommon {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_s_comune")
	private long idSComune;

	//bi-directional many-to-one association to CnmDComune
	@ManyToOne
	@JoinColumn(name="id_comune")
	private CnmDComune cnmDComune;

	//bi-directional many-to-one association to CnmDProvincia
	@ManyToOne
	@JoinColumn(name="id_provincia")
	private CnmDProvincia cnmDProvincia;

	public CnmSComune() {
	}

	public long getIdSComune() {
		return this.idSComune;
	}

	public void setIdSComune(long idSComune) {
		this.idSComune = idSComune;
	}

	public CnmDComune getCnmDComune() {
		return this.cnmDComune;
	}

	public void setCnmDComune(CnmDComune cnmDComune) {
		this.cnmDComune = cnmDComune;
	}

	public CnmDProvincia getCnmDProvincia() {
		return this.cnmDProvincia;
	}

	public void setCnmDProvincia(CnmDProvincia cnmDProvincia) {
		this.cnmDProvincia = cnmDProvincia;
	}

}
