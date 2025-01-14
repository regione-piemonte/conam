/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.entity.CnmDEnte;
import it.csi.conam.conambl.integration.entity.CnmDLettera;
import it.csi.conam.conambl.integration.entity.CnmDStatoManuale;
import it.csi.conam.conambl.integration.entity.CnmDStatoPregresso;
import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

/**
 * @author Lucio Rosadini
 * @date 23 mar 2021
 */
public class VerbaleSearchParam{
	private String numeroProtocollo = null;
	private String numeroVerbale = null;
	private List<CnmDLettera> lettera = null;
	private List<CnmDStatoVerbale> statoVerbale = null;
	private List<CnmDStatoManuale> statoManuale = null;
	private List<CnmDStatoPregresso> statiPregresso = null;
	private CnmDAmbito ambito = null;	// 20211125_LC Jira 184
	private Long annoAccertamento; 
	private Date DataAccertamentoA;
	private Date DataAccertamentoDa;
	private Date dataProcessoVerbaleA;
	private Date dataProcessoVerbaleDa;
	private ComuneVO comuneEnteAccertatore;
	private IstruttoreVO assegnatario;
	private CnmDEnte enteAccertatore;
	
	
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public String getNumeroVerbale() {
		return numeroVerbale;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public void setNumeroVerbale(String numeroVerbale) {
		this.numeroVerbale = numeroVerbale;
	}

	public List<CnmDLettera> getLettera() {
		return lettera;
	}

	public void setLettera(List<CnmDLettera> lettera) {
		this.lettera = lettera;
	}

	public List<CnmDStatoVerbale> getStatoVerbale() {
		return statoVerbale;
	}

	public void setStatoVerbale(List<CnmDStatoVerbale> statoVerbale) {
		this.statoVerbale = statoVerbale;
	}

	public List<CnmDStatoManuale> getStatoManuale() {
		return statoManuale;
	}

	public void setStatoManuale(List<CnmDStatoManuale> statoManuale) {
		this.statoManuale = statoManuale;
	}

	public List<CnmDStatoPregresso> getStatiPregresso() {
		return statiPregresso;
	}

	public void setStatiPregresso(List<CnmDStatoPregresso> statiPregresso) {
		this.statiPregresso = statiPregresso;
	}

	
	public CnmDAmbito getAmbito() {
		return ambito;
	}

	public void setAmbito(CnmDAmbito ambito) {
		this.ambito = ambito;
	}
	
	public Long getAnnoAccertamento() {
		return annoAccertamento;
	}

	public void setAnnoAccertamento(Long annoAccertamento) {
		this.annoAccertamento = annoAccertamento;
	}

	public Date getDataAccertamentoA() {
		return DataAccertamentoA;
	}

	public void setDataAccertamentoA(Date dataAccertamentoA) {
		DataAccertamentoA = dataAccertamentoA;
	}

	public Date getDataAccertamentoDa() {
		return DataAccertamentoDa;
	}

	public void setDataAccertamentoDa(Date dataAccertamentoDa) {
		DataAccertamentoDa = dataAccertamentoDa;
	}

	public Date getDataProcessoVerbaleA() {
		return dataProcessoVerbaleA;
	}

	public void setDataProcessoVerbaleA(Date dataProcessoVerbaleA) {
		this.dataProcessoVerbaleA = dataProcessoVerbaleA;
	}

	public Date getDataProcessoVerbaleDa() {
		return dataProcessoVerbaleDa;
	}

	public void setDataProcessoVerbaleDa(Date dataProcessoVerbaleDa) {
		this.dataProcessoVerbaleDa = dataProcessoVerbaleDa;
	}
	
	public ComuneVO getComuneEnteAccertatore() {
		return comuneEnteAccertatore;
	}

	public void setComuneEnteAccertatore(ComuneVO comuneEnteAccertatore) {
		this.comuneEnteAccertatore = comuneEnteAccertatore;
	}
	
	public IstruttoreVO getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(IstruttoreVO assegnatario) {
		this.assegnatario = assegnatario;
	}

	public CnmDEnte getEnteAccertatore() {
		return enteAccertatore;
	}

	public void setEnteAccertatore(CnmDEnte enteAccertatore) {
		this.enteAccertatore = enteAccertatore;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
