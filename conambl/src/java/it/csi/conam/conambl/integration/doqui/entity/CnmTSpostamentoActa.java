/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="cnm_t_spostamento_acta")
@NamedQuery(name="CnmTSpostamentoActa.findAll", query="SELECT c FROM CnmTSpostamentoActa c")
public class CnmTSpostamentoActa implements Serializable{
	
	// id_verbale, numero_protocollo, operazione, stato, insertDate, prenotazioneId, info (Richiesta partita da id_allegato [idAllegato])
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// stato che indica che la richiesta di spostamento e' stata ricevuta da conam
	public static final String STATO_RICHIESTA_RICEVUTA = "RICHIESTA_RICEVUTA";
	
	// stato che indica che la richiesta di spostamento e' in fase di invio verso ACTA
	public static final String STATO_INVIO_RICHIESTA = "INVIO_RICHIESTA";
	
	// stato iniziale 
	public static final String STATO_IN_CORSO = "IN_CORSO";
	
	// durante l'esecuzione del batch pe rsingolo elemento
	public static final String STATO_AGGIORNAMENTO_DATI = "AGGIORNAMENTO_DATI";
	
	// stato finale
	public static final String STATO_EVASO = "EVASO";

	// stato che identifica una problematica nello spostamento, che dovrà essere ritentato
	public static final String STATO_ERRORE = "ERRORE";

	
	public static final String OPERAZIONE_COPIA = "COPIA";
	public static final String OPERAZIONE_SPOSTA = "SPOSTA";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="id_verbale", nullable = false)
	private Integer idVerbale;

	@Column(name="id_documento_master", nullable = false)
	private Integer idDocumentoMaster;
	
	@Column(name="numero_protocollo")
	private String numeroProtocollo;
	
	@Column(name="folder")
	private String folder;
	
	@Column(name="operazione")
	private String operazione;

	@Column(name="stato")
	private String stato;
	
	@Column(name="prenotazione_id")
	private String prenotazioneId;
	
	@Column(name = "data_ora_insert")
	private Timestamp dataOraInsert;
	
	@Column(name = "data_ora_end")
	private Timestamp dataOraEnd;
	
	@Column(name="info")
	private String info;	
	
	@Column(name="parolaChiaveTemp")
	private String parolaChiaveTemp;	
	
	
	public CnmTSpostamentoActa() {
		super();
	}

	public CnmTSpostamentoActa(Integer idVerbale, Integer idDocumentoMaster, String numeroProtocollo, String folder, String operazione, String stato,
			String prenotazioneId, Timestamp dataOraInsert, String info, String parolaChiaveTemp) {
		super();
		this.idVerbale = idVerbale;
		this.idDocumentoMaster = idDocumentoMaster;
		this.numeroProtocollo = numeroProtocollo;
		this.folder = folder;
		this.operazione = operazione;
		this.stato = stato;
		this.prenotazioneId = prenotazioneId;
		this.dataOraInsert = dataOraInsert;
		this.info = info;
		this.parolaChiaveTemp = parolaChiaveTemp;
	}





	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public Integer getIdVerbale() {
		return idVerbale;
	}




	public void setIdVerbale(Integer idVerbale) {
		this.idVerbale = idVerbale;
	}




	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}




	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}


	

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	
	public String getOperazione() {
		return operazione;
	}




	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}




	public String getStato() {
		return stato;
	}




	public void setStato(String stato) {
		this.stato = stato;
	}




	public String getPrenotazioneId() {
		return prenotazioneId;
	}




	public void setPrenotazioneId(String prenotazioneId) {
		this.prenotazioneId = prenotazioneId;
	}




	public Timestamp getDataOraInsert() {
		return dataOraInsert;
	}




	public void setDataOraInsert(Timestamp dataOraInsert) {
		this.dataOraInsert = dataOraInsert;
	}




	public Timestamp getDataOraEnd() {
		return dataOraEnd;
	}




	public void setDataOraEnd(Timestamp dataOraEnd) {
		this.dataOraEnd = dataOraEnd;
	}




	public String getInfo() {
		return info;
	}




	public void setInfo(String info) {
		this.info = info;
	}




	public Integer getIdDocumentoMaster() {
		return idDocumentoMaster;
	}




	public void setIdDocumentoMaster(Integer idDocumentoMaster) {
		this.idDocumentoMaster = idDocumentoMaster;
	}




	@Override
	public String toString() {
		return "CnmTSpostamentoActa [id=" + id + ", idVerbale=" + idVerbale + ", idDocumentoMaster=" + idDocumentoMaster
				+ ", numeroProtocollo=" + numeroProtocollo + ", folder=" + folder + ", operazione=" + operazione + ", stato=" + stato
				+ ", prenotazioneId=" + prenotazioneId + ", dataOraInsert=" + dataOraInsert + ", dataOraEnd="
				+ dataOraEnd + ", info=" + info + "]";
	}

	public String getParolaChiaveTemp() {
		return parolaChiaveTemp;
	}

	public void setParolaChiaveTemp(String parolaChiaveTemp) {
		this.parolaChiaveTemp = parolaChiaveTemp;
	}



	
	
	
	

	

	
	

	

}
