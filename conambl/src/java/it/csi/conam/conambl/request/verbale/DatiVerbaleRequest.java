/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.verbale;

import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.request.ParentRequest;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.leggi.AmbitoVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.leggi.NormaVO;
import it.csi.conam.conambl.vo.luoghi.ComuneVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.time.LocalDate;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class DatiVerbaleRequest extends ParentRequest {

	private static final long serialVersionUID = -9081500001773481835L;

	private String numeroProtocollo;
	private String numeroVerbale;
	private EnteVO ente;
	private AmbitoVO ambito;
	private NormaVO norma;
	private List<StatoVerbaleVO> stato;
	private boolean pregresso;
	private List<Long> listaStatiManuali;
	private Boolean statoManualeDiCompetenza;
	private Long annoAccertamento;
	private ComuneVO comuneEnteAccertatore; // OB_31
	private EnteVO enteAccertatore; // OB_31
	private IstruttoreVO assegnatario; // OB_32


	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataAccertamentoDa;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataAccertamentoA;

	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataProcessoVerbaleDa;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataProcessoVerbaleA;

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public String getNumeroVerbale() {
		return numeroVerbale;
	}

	public NormaVO getNorma() {
		return norma;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public void setNumeroVerbale(String numeroVerbale) {
		this.numeroVerbale = numeroVerbale;
	}

	public void setNorma(NormaVO norma) {
		this.norma = norma;
	}

	public EnteVO getEnte() {
		return ente;
	}

	public void setEnte(EnteVO ente) {
		this.ente = ente;
	}

	public List<StatoVerbaleVO> getStato() {
		return stato;
	}

	public void setStato(List<StatoVerbaleVO> stato) {
		this.stato = stato;
	}

	public boolean isPregresso() {
		return pregresso;
	}

	public void setPregresso(boolean pregresso) {
		this.pregresso = pregresso;
	}

	public AmbitoVO getAmbito() {
		return ambito;
	}

	public void setAmbito(AmbitoVO ambito) {
		this.ambito = ambito;
	}

	public void setStatoManualeDiCompetenza(Boolean statoManualeDiCompetenza) {
		this.statoManualeDiCompetenza = statoManualeDiCompetenza;
		if (this.statoManualeDiCompetenza)
			listaStatiManuali = Constants.LISTA_STATI_MANUALI_NON_DI_COMPETENZA;
		else
			listaStatiManuali = Constants.LISTA_STATI_MANUALI_DEVOLUTI_PER_COMPETENZA;
	}

	public List<Long> getListaStatiManuali() {
		return listaStatiManuali;
	}
	
	public Long getAnnoAccertamento() {
		return annoAccertamento;
	}

	public void setAnnoAccertamento(Long annoAccertamento) {
		this.annoAccertamento = annoAccertamento;
	}

	public LocalDate getDataAccertamentoDa() {
		return dataAccertamentoDa;
	}

	public void setDataAccertamentoDa(LocalDate dataAccertamentoDa) {
		this.dataAccertamentoDa = dataAccertamentoDa;
	}

	public LocalDate getDataAccertamentoA() {
		return dataAccertamentoA;
	}

	public void setDataAccertamentoA(LocalDate dataAccertamentoA) {
		this.dataAccertamentoA = dataAccertamentoA;
	}
	
	public LocalDate getDataProcessoVerbaleDa() {
		return dataProcessoVerbaleDa;
	}

	public void setDataProcessoVerbaleDa(LocalDate dataProcessoVerbaleDa) {
		this.dataProcessoVerbaleDa = dataProcessoVerbaleDa;
	}

	public LocalDate getDataProcessoVerbaleA() {
		return dataProcessoVerbaleA;
	}

	public void setDataProcessoVerbaleA(LocalDate dataProcessoVerbaleA) {
		this.dataProcessoVerbaleA = dataProcessoVerbaleA;
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
	
	public EnteVO getEnteAccertatore() {
		return enteAccertatore;
	}

	public void setEnteAccertatore(EnteVO enteAccertatore) {
		this.enteAccertatore = enteAccertatore;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
