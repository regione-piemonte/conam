/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the cnm_d_tipo_ricerca database table.
 * 
 */
@Entity
@Table(name="cnm_d_tipo_ricerca")
@NamedQuery(name="CnmDTipoRicerca.findAll", query="SELECT c FROM CnmDTipoRicerca c")
public class CnmDTipoRicerca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tipo_ricerca")
	private long idTipoRicerca;

	@Column(name="desc_tipo_ricerca")
	private String descTipoRicerca;
	
	//bi-directional many-to-one association to CnmCField
	@OneToMany(mappedBy="cnmDTipoRicerca")
	private List<CnmCFieldRicerca> cnmCFieldsRicerca;

	
	public CnmDTipoRicerca() {
	}

	public long getIdTipoRicerca() {
		return this.idTipoRicerca;
	}

	public void setIdTipoRicerca(long idTipoRicerca) {
		this.idTipoRicerca = idTipoRicerca;
	}

	public String getDescTipoRicerca() {
		return this.descTipoRicerca;
	}

	public void setDescTipoRicerca(String descTipoRicerca) {
		this.descTipoRicerca = descTipoRicerca;
	}	

	public List<CnmCFieldRicerca> getCnmCFieldsRicerca() {
		return this.cnmCFieldsRicerca;
	}

	public void setCnmCFields(List<CnmCFieldRicerca> cnmCFieldsRicerca) {
		this.cnmCFieldsRicerca = cnmCFieldsRicerca;
	}

	public CnmCFieldRicerca addCnmCFieldRicerca(CnmCFieldRicerca cnmCFieldRicerca) {
		getCnmCFieldsRicerca().add(cnmCFieldRicerca);
		cnmCFieldRicerca.setCnmDTipoRicerca(this);

		return cnmCFieldRicerca;
	}

	public CnmCFieldRicerca removeCnmCFieldRicerca(CnmCFieldRicerca cnmCFieldRicerca) {
		getCnmCFieldsRicerca().remove(cnmCFieldRicerca);
		cnmCFieldRicerca.setCnmDTipoRicerca(null);

		return cnmCFieldRicerca;
	}
}
