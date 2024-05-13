/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;



/**
 * The persistent class for the cnm_r_sogg_rata database table.
 * 
 */
@Entity
@Table(name="cnm_r_sogg_rata")
@NamedQuery(name="CnmRSoggRata.findAll", query="SELECT c FROM CnmRSoggRata c")
public class CnmRSoggRata extends CnmRTCommons {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private CnmRSoggRataPK id;

	//bi-directional many-to-one association to CnmDStatoRata
	@ManyToOne
	@JoinColumn(name="id_stato_rata")
	private CnmDStatoRata cnmDStatoRata;

	//bi-directional many-to-one association to CnmROrdinanzaVerbSog
	@ManyToOne
	@JoinColumn(name="id_ordinanza_verb_sog", insertable = false, updatable = false)
	private CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog;

	//bi-directional many-to-one association to CnmTRata
	@ManyToOne
	@JoinColumn(name="id_rata", insertable = false, updatable = false)
	private CnmTRata cnmTRata;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser2;

	public CnmRSoggRata() {
	}

	public CnmRSoggRataPK getId() {
		return this.id;
	}

	public void setId(CnmRSoggRataPK id) {
		this.setId(id);
	}

	public CnmDStatoRata getCnmDStatoRata() {
		return this.cnmDStatoRata;
	}

	public void setCnmDStatoRata(CnmDStatoRata cnmDStatoRata) {
		this.cnmDStatoRata = cnmDStatoRata;
	}

	public CnmROrdinanzaVerbSog getCnmROrdinanzaVerbSog() {
		return this.cnmROrdinanzaVerbSog;
	}

	public void setCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		this.cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSog;
	}

	public CnmTRata getCnmTRata() {
		return this.cnmTRata;
	}

	public void setCnmTRata(CnmTRata cnmTRata) {
		this.cnmTRata = cnmTRata;
	}

	public CnmTUser getCnmTUser1() {
		return this.cnmTUser1;
	}

	public void setCnmTUser1(CnmTUser cnmTUser1) {
		this.cnmTUser1 = cnmTUser1;
	}

	public CnmTUser getCnmTUser2() {
		return this.cnmTUser2;
	}

	public void setCnmTUser2(CnmTUser cnmTUser2) {
		this.cnmTUser2 = cnmTUser2;
	}

}
