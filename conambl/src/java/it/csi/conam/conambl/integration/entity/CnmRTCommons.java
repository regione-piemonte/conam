/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The Abstract class for the cnm_r_sogg_rata, cnm_t_sollecito, cnm_r_ordinanza_verb_sogg database table.
 * 
 */
@MappedSuperclass
public class CnmRTCommons implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="cod_avviso")
	private String codAvviso;

	@Column(name="cod_esito_lista_carico")
	private String codEsitoListaCarico;

	@Column(name="cod_iuv")
	private String codIuv;

	@Column(name="cod_posizione_debitoria")
	private String codPosizioneDebitoria;

	@Column(name="data_ora_insert")
	private Timestamp dataOraInsert;

	@Column(name="data_ora_update")
	private Timestamp dataOraUpdate;

	@Temporal(TemporalType.DATE)
	@Column(name="data_pagamento")
	private Date dataPagamento;
	
	@Column(name="importo_pagato")
	private BigDecimal importoPagato;

	public CnmRTCommons() {
	}

	public String getCodAvviso() {
		return this.codAvviso;
	}

	public void setCodAvviso(String codAvviso) {
		this.codAvviso = codAvviso;
	}

	public String getCodEsitoListaCarico() {
		return this.codEsitoListaCarico;
	}

	public void setCodEsitoListaCarico(String codEsitoListaCarico) {
		this.codEsitoListaCarico = codEsitoListaCarico;
	}

	public String getCodIuv() {
		return this.codIuv;
	}

	public void setCodIuv(String codIuv) {
		this.codIuv = codIuv;
	}

	public String getCodPosizioneDebitoria() {
		return this.codPosizioneDebitoria;
	}

	public void setCodPosizioneDebitoria(String codPosizioneDebitoria) {
		this.codPosizioneDebitoria = codPosizioneDebitoria;
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

	public Date getDataPagamento() {
		return this.dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getImportoPagato() {
		return this.importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

}
