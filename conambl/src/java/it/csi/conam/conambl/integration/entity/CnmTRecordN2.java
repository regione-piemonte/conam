/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;


/**
 * The persistent class for the cnm_t_record_n2 database table.
 * 
 */
@Entity
@Table(name="cnm_t_record_n2")
@NamedQuery(name="CnmTRecordN2.findAll", query="SELECT c FROM CnmTRecordN2 c")
public class CnmTRecordN2 extends CnmTRecordCommons {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_record_n2")
	private Integer idRecordN2;

	@Column(name="presenza_obbligato")
	private String presenzaObbligato;

	public CnmTRecordN2() {
		super();
	}

	public Integer getIdRecordN2() {
		return this.idRecordN2;
	}

	public void setIdRecordN2(Integer idRecordN2) {
		this.idRecordN2 = idRecordN2;
	}
	
	public String getPresenzaObbligato() {
		return this.presenzaObbligato;
	}

	public void setPresenzaObbligato(String presenzaObbligato) {
		this.presenzaObbligato = presenzaObbligato;
	}

}
