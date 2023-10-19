/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import java.math.BigDecimal;
import java.util.Date;

import it.csi.conam.conambl.vo.ParentVO;

/**
 * @author riccardo.bova
 * @date 12 feb 2019
 */
public class DatiRelataNotificaVO extends ParentVO {

	private static final long serialVersionUID = 1L;

	private Boolean notificata;
	private String dataNotifica;
	private BigDecimal numeroRaccomandata;
	private String dataSpedizione;
	private BigDecimal importoSpeseNotifica;
	private String modalita;
	public Boolean getNotificata() {
		return notificata;
	}
	public void setNotificata(Boolean notificata) {
		this.notificata = notificata;
	}
	public String getDataNotifica() {
		return dataNotifica;
	}
	public void setDataNotifica(String dataNotifica) {
		this.dataNotifica = dataNotifica;
	}
	public BigDecimal getNumeroRaccomandata() {
		return numeroRaccomandata;
	}
	public void setNumeroRaccomandata(BigDecimal numeroRaccomandata) {
		this.numeroRaccomandata = numeroRaccomandata;
	}
	public String getDataSpedizione() {
		return dataSpedizione;
	}
	public void setDataSpedizione(String dataSpedizione) {
		this.dataSpedizione = dataSpedizione;
	}
	public BigDecimal getImportoSpeseNotifica() {
		return importoSpeseNotifica;
	}
	public void setImportoSpeseNotifica(BigDecimal importoSpeseNotifica) {
		this.importoSpeseNotifica = importoSpeseNotifica;
	}
	public String getModalita() {
		return modalita;
	}
	public void setModalita(String modalita) {
		this.modalita = modalita;
	}
	
}
