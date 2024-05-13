/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the cnm_d_nazione database table.
 * 
 */
@Entity
@Table(name="cnm_d_nazione")
@NamedQuery(name="CnmDNazione.findAll", query="SELECT c FROM CnmDNazione c")
public class CnmDNazione extends CnmNazioneCommons implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_nazione")
	private long idNazione;

	//bi-directional many-to-one association to CnmDRegione
	@OneToMany(mappedBy="cnmDNazione")
	private List<CnmDRegione> cnmDRegiones;

	//bi-directional many-to-one association to CnmSNazione
	@OneToMany(mappedBy="cnmDNazione")
	private List<CnmSNazione> cnmSNaziones;

	//bi-directional many-to-one association to CnmSRegione
	@OneToMany(mappedBy="cnmDNazione")
	private List<CnmSRegione> cnmSRegiones;

	//bi-directional many-to-one association to CnmTPersona
	@OneToMany(mappedBy="cnmDNazione")
	private List<CnmTPersona> cnmTPersonas;

	//bi-directional many-to-one association to CnmTSocieta
	@OneToMany(mappedBy="cnmDNazione")
	private List<CnmTSocieta> cnmTSocietas;
	
	//bi-directional many-to-one association to CnmTResidenza
	@OneToMany(mappedBy="cnmDNazione")
	private List<CnmTResidenza> cnmTResidenzas;

	public CnmDNazione() {
		super();
	}

	public long getIdNazione() {
		return this.idNazione;
	}

	public void setIdNazione(long idNazione) {
		this.idNazione = idNazione;
	}

	public List<CnmDRegione> getCnmDRegiones() {
		return this.cnmDRegiones;
	}

	public void setCnmDRegiones(List<CnmDRegione> cnmDRegiones) {
		this.cnmDRegiones = cnmDRegiones;
	}

	public CnmDRegione addCnmDRegione(CnmDRegione cnmDRegione) {
		getCnmDRegiones().add(cnmDRegione);
		cnmDRegione.setCnmDNazione(this);

		return cnmDRegione;
	}

	public CnmDRegione removeCnmDRegione(CnmDRegione cnmDRegione) {
		getCnmDRegiones().remove(cnmDRegione);
		cnmDRegione.setCnmDNazione(null);

		return cnmDRegione;
	}

	public List<CnmSNazione> getCnmSNaziones() {
		return this.cnmSNaziones;
	}

	public void setCnmSNaziones(List<CnmSNazione> cnmSNaziones) {
		this.cnmSNaziones = cnmSNaziones;
	}

	public CnmSNazione addCnmSNazione(CnmSNazione cnmSNazione) {
		getCnmSNaziones().add(cnmSNazione);
		cnmSNazione.setCnmDNazione(this);

		return cnmSNazione;
	}

	public CnmSNazione removeCnmSNazione(CnmSNazione cnmSNazione) {
		getCnmSNaziones().remove(cnmSNazione);
		cnmSNazione.setCnmDNazione(null);

		return cnmSNazione;
	}

	public List<CnmSRegione> getCnmSRegiones() {
		return this.cnmSRegiones;
	}

	public void setCnmSRegiones(List<CnmSRegione> cnmSRegiones) {
		this.cnmSRegiones = cnmSRegiones;
	}

	public CnmSRegione addCnmSRegione(CnmSRegione cnmSRegione) {
		getCnmSRegiones().add(cnmSRegione);
		cnmSRegione.setCnmDNazione(this);

		return cnmSRegione;
	}

	public CnmSRegione removeCnmSRegione(CnmSRegione cnmSRegione) {
		getCnmSRegiones().remove(cnmSRegione);
		cnmSRegione.setCnmDNazione(null);

		return cnmSRegione;
	}

	public List<CnmTPersona> getCnmTPersonas() {
		return this.cnmTPersonas;
	}

	public void setCnmTPersonas(List<CnmTPersona> cnmTPersonas) {
		this.cnmTPersonas = cnmTPersonas;
	}

	public CnmTPersona addCnmTPersona(CnmTPersona cnmTPersona) {
		getCnmTPersonas().add(cnmTPersona);
		cnmTPersona.setCnmDNazione(this);

		return cnmTPersona;
	}

	public CnmTPersona removeCnmTPersona(CnmTPersona cnmTPersona) {
		getCnmTPersonas().remove(cnmTPersona);
		cnmTPersona.setCnmDNazione(null);

		return cnmTPersona;
	}

	public List<CnmTSocieta> getCnmTSocietas() {
		return this.cnmTSocietas;
	}

	public void setCnmTSocietas(List<CnmTSocieta> cnmTSocietas) {
		this.cnmTSocietas = cnmTSocietas;
	}

	public CnmTSocieta addCnmTSocieta(CnmTSocieta cnmTSocieta) {
		getCnmTSocietas().add(cnmTSocieta);
		cnmTSocieta.setCnmDNazione(this);

		return cnmTSocieta;
	}

	public CnmTSocieta removeCnmTSocieta(CnmTSocieta cnmTSocieta) {
		getCnmTSocietas().remove(cnmTSocieta);
		cnmTSocieta.setCnmDNazione(null);

		return cnmTSocieta;
	}
	
	public List<CnmTResidenza> getCnmTResidenzas() {
		return this.cnmTResidenzas;
	}

	public void setCnmTResidenzas(List<CnmTResidenza> cnmTResidenzas) {
		this.cnmTResidenzas = cnmTResidenzas;
	}

	public CnmTResidenza addCnmTResidenza(CnmTResidenza cnmTResidenza) {
		getCnmTResidenzas().add(cnmTResidenza);
		cnmTResidenza.setCnmDNazione(this);

		return cnmTResidenza;
	}

	public CnmTResidenza removeCnmTResidenza(CnmTResidenza cnmTResidenza) {
		getCnmTResidenzas().remove(cnmTResidenza);
		cnmTResidenza.setCnmDNazione(null);

		return cnmTResidenza;
	}

}
