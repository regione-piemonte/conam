/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.ordinanza;

import java.math.BigDecimal;
import java.util.List;

public class OrdinanzaVO extends MinOrdinanzaVO {

	private static final long serialVersionUID = 852151050709076476L;

	private BigDecimal importoSpesaNotifica;
	private BigDecimal importoTotaleDaPagare;
	private BigDecimal importoPagato;
	private BigDecimal importoResiduo;
	private List<AccontoVO> listaAcconti;
	
	// E25_2022 - OB28
	private String trasgressori;
	private String obbligati;

	
	public String getTrasgressori() {
		return trasgressori;
	}

	public void setTrasgressori(String trasgressori) {
		this.trasgressori = trasgressori;
	}

	public String getObbligati() {
		return obbligati;
	}

	public void setObbligati(String obbligati) {
		this.obbligati = obbligati;
	}

	public BigDecimal getImportoSpesaNotifica() {
		return importoSpesaNotifica;
	}

	public void setImportoSpesaNotifica(BigDecimal importoSpesaNotifica) {
		this.importoSpesaNotifica = importoSpesaNotifica;
	}

	public BigDecimal getImportoTotaleDaPagare() {
		return importoTotaleDaPagare;
	}

	public void setImportoTotaleDaPagare(BigDecimal importoTotaleDaPagare) {
		this.importoTotaleDaPagare = importoTotaleDaPagare;
	}

	public BigDecimal getImportoPagato() {
		return importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public BigDecimal getImportoResiduo() {
		return importoResiduo;
	}

	public void setImportoResiduo(BigDecimal importoResiduo) {
		this.importoResiduo = importoResiduo;
	}
	
	
	public List<AccontoVO> getListaAcconti() {
		return listaAcconti;
	}

	public void setListaAcconti(List<AccontoVO> listaAcconti) {
		this.listaAcconti = listaAcconti;
	}
	
}
