/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the cnm_s_nazione database table.
 * 
 */
@Entity
@Table(name="cnm_s_nazione")
@NamedQuery(name="CnmSNazione.findAll", query="SELECT c FROM CnmSNazione c")
public class CnmSNazione extends CnmNazioneCommons implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_s_nazione")
	private long idSNazione;

	//bi-directional many-to-one association to CnmDNazione
	@ManyToOne
	@JoinColumn(name="id_nazione")
	private CnmDNazione cnmDNazione;

	public CnmSNazione() {
		super();
	}

	public long getIdSNazione() {
		return this.idSNazione;
	}

	public void setIdSNazione(long idSNazione) {
		this.idSNazione = idSNazione;
	}

	public CnmDNazione getCnmDNazione() {
		return this.cnmDNazione;
	}

	public void setCnmDNazione(CnmDNazione cnmDNazione) {
		this.cnmDNazione = cnmDNazione;
	}

}
