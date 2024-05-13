/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;


/**
 * The persistent class for the cnm_t_record_n3 database table.
 * 
 */
@Entity
@Table(name="cnm_t_record_n3")
@NamedQuery(name="CnmTRecordN3.findAll", query="SELECT c FROM CnmTRecordN3 c")
public class CnmTRecordN3 extends CnmTRecordCommons {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_record_n3")
	private Integer idRecordN3;

	public CnmTRecordN3() {
		super();
	}

	public Integer getIdRecordN3() {
		return this.idRecordN3;
	}

	public void setIdRecordN3(Integer idRecordN3) {
		this.idRecordN3 = idRecordN3;
	}

}
