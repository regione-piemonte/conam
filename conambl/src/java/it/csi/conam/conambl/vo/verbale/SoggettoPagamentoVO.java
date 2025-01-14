/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.luoghi.NazioneVO;
import it.csi.conam.conambl.vo.luoghi.ProvinciaVO;
import it.csi.conam.conambl.vo.luoghi.RegioneVO;
import it.csi.conam.conambl.vo.ordinanza.MinOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoOrdinanzaVO;
import it.csi.conam.conambl.vo.ordinanza.StatoSoggettoOrdinanzaVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
//REQ_69 
public class SoggettoPagamentoVO extends SoggettoVO {

	private static final long serialVersionUID = 6974001856644552788L;

	private Double importoPagato;
	private boolean pagamentoParziale;
	
	public Double getImportoPagato() {
		return importoPagato;
	}
	public void setImportoPagato(Double importoPagato) {
		this.importoPagato = importoPagato;
	}
	public boolean isPagamentoParziale() {
		return pagamentoParziale;
	}
	public void setPagamentoParziale(boolean pagamentoParziale) {
		this.pagamentoParziale = pagamentoParziale;
	}

	

}
