/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cnm_t_sollecito database table.
 * 
 */
@Entity
@Table(name="cnm_t_sollecito")
@NamedQuery(name="CnmTSollecito.findAll", query="SELECT c FROM CnmTSollecito c")
public class CnmTSollecito extends CnmRTCommons {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_sollecito")
	private Integer idSollecito;

	@Column(name="cod_messaggio_epay")
	private String codMessaggioEpay;

	@Temporal(TemporalType.DATE)
	@Column(name="data_scadenza_rata")
	private Date dataScadenzaRata;

	@Column(name="importo_sollecito")
	private BigDecimal importoSollecito;

	private BigDecimal maggiorazione;

	@Column(name="numero_protocollo")
	private String numeroProtocollo;

	@Column(name="flag_documento_pregresso")
	private boolean flagDocumentoPregresso;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_fine_validita")
	private Date dataFineValidita;
	
	//bi-directional many-to-one association to CnmRAllegatoSollecito
	@OneToMany(mappedBy="cnmTSollecito")
	private List<CnmRAllegatoSollecito> cnmRAllegatoSollecitos;

	//bi-directional many-to-one association to CnmDStatoSollecito
	@ManyToOne
	@JoinColumn(name="id_stato_sollecito")
	private CnmDStatoSollecito cnmDStatoSollecito;
	
	//bi-directional many-to-one association to CnmDTipoSollecito
	@ManyToOne
	@JoinColumn(name="id_tipo_sollecito")
	private CnmDTipoSollecito cnmDTipoSollecito;

	//bi-directional many-to-one association to CnmROrdinanzaVerbSog
	@ManyToOne
	@JoinColumn(name="id_ordinanza_verb_sog")
	private CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog;
	
	@Column(name = "reversale_d_ordine")
	private String reversaleDOrdine;

	@Column(name = "pagatore")
	private String pagatore;

	@Column(name = "note")
	private String note;

	//bi-directional many-to-many association to CnmTNotifica
	@ManyToMany
	@JoinTable(
		name="cnm_r_notifica_sollecito"
		, joinColumns={
			@JoinColumn(name="id_sollecito")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_notifica")
			}
		)
	private List<CnmTNotifica> cnmTNotificas;
	
	//bi-directional many-to-one association to CnmRSollecitoSoggRata
	@OneToMany(mappedBy="cnmTSollecito")
	private List<CnmRSollecitoSoggRata> cnmRSollecitoSoggRatas;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_update")
	private CnmTUser cnmTUser1;

	//bi-directional many-to-one association to CnmTUser
	@ManyToOne
	@JoinColumn(name="id_user_insert")
	private CnmTUser cnmTUser2;

	public CnmTSollecito() {
	}

	public Integer getIdSollecito() {
		return this.idSollecito;
	}

	public void setIdSollecito(Integer idSollecito) {
		this.idSollecito = idSollecito;
	}


	public String getCodMessaggioEpay() {
		return this.codMessaggioEpay;
	}

	public void setCodMessaggioEpay(String codMessaggioEpay) {
		this.codMessaggioEpay = codMessaggioEpay;
	}

	public Date getDataScadenzaRata() {
		return this.dataScadenzaRata;
	}

	public void setDataScadenzaRata(Date dataScadenzaRata) {
		this.dataScadenzaRata = dataScadenzaRata;
	}

	public BigDecimal getImportoSollecito() {
		return this.importoSollecito;
	}

	public void setImportoSollecito(BigDecimal importoSollecito) {
		this.importoSollecito = importoSollecito;
	}

	public BigDecimal getMaggiorazione() {
		return this.maggiorazione;
	}

	public void setMaggiorazione(BigDecimal maggiorazione) {
		this.maggiorazione = maggiorazione;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public List<CnmRAllegatoSollecito> getCnmRAllegatoSollecitos() {
		return this.cnmRAllegatoSollecitos;
	}

	public void setCnmRAllegatoSollecitos(List<CnmRAllegatoSollecito> cnmRAllegatoSollecitos) {
		this.cnmRAllegatoSollecitos = cnmRAllegatoSollecitos;
	}

	public CnmRAllegatoSollecito addCnmRAllegatoSollecito(CnmRAllegatoSollecito cnmRAllegatoSollecito) {
		getCnmRAllegatoSollecitos().add(cnmRAllegatoSollecito);
		cnmRAllegatoSollecito.setCnmTSollecito(this);

		return cnmRAllegatoSollecito;
	}

	public CnmRAllegatoSollecito removeCnmRAllegatoSollecito(CnmRAllegatoSollecito cnmRAllegatoSollecito) {
		getCnmRAllegatoSollecitos().remove(cnmRAllegatoSollecito);
		cnmRAllegatoSollecito.setCnmTSollecito(null);

		return cnmRAllegatoSollecito;
	}

	public CnmDStatoSollecito getCnmDStatoSollecito() {
		return this.cnmDStatoSollecito;
	}

	public void setCnmDStatoSollecito(CnmDStatoSollecito cnmDStatoSollecito) {
		this.cnmDStatoSollecito = cnmDStatoSollecito;
	}

	public CnmROrdinanzaVerbSog getCnmROrdinanzaVerbSog() {
		return this.cnmROrdinanzaVerbSog;
	}

	public void setCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		this.cnmROrdinanzaVerbSog = cnmROrdinanzaVerbSog;
	}

	public List<CnmTNotifica> getCnmTNotificas() {
		return this.cnmTNotificas;
	}

	public void setCnmTNotificas(List<CnmTNotifica> cnmTNotificas) {
		this.cnmTNotificas = cnmTNotificas;
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

	public boolean isFlagDocumentoPregresso() {
		return flagDocumentoPregresso;
	}

	public void setFlagDocumentoPregresso(boolean flagDocumentoPregresso) {
		this.flagDocumentoPregresso = flagDocumentoPregresso;
	}

	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public List<CnmRSollecitoSoggRata> getCnmRSollecitoSoggRatas() {
		return cnmRSollecitoSoggRatas;
	}

	public void setCnmRSollecitoSoggRatas(List<CnmRSollecitoSoggRata> cnmRSollecitoSoggRatas) {
		this.cnmRSollecitoSoggRatas = cnmRSollecitoSoggRatas;
	}

	public CnmDTipoSollecito getCnmDTipoSollecito() {
		return cnmDTipoSollecito;
	}

	public void setCnmDTipoSollecito(CnmDTipoSollecito cnmDTipoSollecito) {
		this.cnmDTipoSollecito = cnmDTipoSollecito;
	}

	public String getReversaleDOrdine() {
		return reversaleDOrdine;
	}

	public void setReversaleDOrdine(String reversaleDOrdine) {
		this.reversaleDOrdine = reversaleDOrdine;
	}

	public String getPagatore() {
		return pagatore;
	}

	public void setPagatore(String pagatore) {
		this.pagatore = pagatore;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
