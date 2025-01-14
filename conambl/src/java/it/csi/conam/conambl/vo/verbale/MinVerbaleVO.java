/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;


import java.math.BigInteger;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.web.serializer.CustomDateTimeDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;


/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class MinVerbaleVO extends MinVerbaleVOCommons {

	private static final long serialVersionUID = -884216214905682332L;
	// 25_2023 - OB28
	private String trasgressori;
	private String obbligati;
	private String comuneEnteAccertatore;
	//E24_2022 - OBI30
	private String ambiti;
	private IstruttoreVO assegnatario;
	// OBI32
	private BigInteger annoAccertamento;
	private String enteRiferimentiNormativi;
	private String leggeViolata;

	// OBI39
	private String documenti;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private LocalDateTime dataProcesso;
	
	
	public String getComuneEnteAccertatore() {
		return comuneEnteAccertatore;
	}

	public void setComuneEnteAccertatore(String comuneEnteAccertatore) {
		this.comuneEnteAccertatore = comuneEnteAccertatore;
	}

	public String getTrasgressori() {
		return trasgressori;
	}

	public IstruttoreVO getAssegnatario() {
		return assegnatario;
	}
	
	public String getObbligati() {
		return obbligati;
	}
	
	public void setTrasgressori(String trasgressori) {
		this.trasgressori = trasgressori;
	}
	
	public void setObbligati(String obbligati) {
		this.obbligati = obbligati;
	}
		
	public String getAmbiti() {
		return ambiti;
	}

	public void setAmbiti(String ambiti) {
		this.ambiti = ambiti;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public BigInteger getAnnoAccertamento() {
		return annoAccertamento;
	}

	public void setAnnoAccertamento(BigInteger annoAccertamento) {
		this.annoAccertamento = annoAccertamento;
	}

	public String getEnteRiferimentiNormativi() {
		return enteRiferimentiNormativi;
	}

	public void setEnteRiferimentiNormativi(String enteRiferimentiNormativi) {
		this.enteRiferimentiNormativi = enteRiferimentiNormativi;
	}

	public String getLeggeViolata() {
		return leggeViolata;
	}

	public void setLeggeViolata(String leggeViolata) {
		this.leggeViolata = leggeViolata;
	}

	public LocalDateTime getDataProcesso() {
		return dataProcesso;
	}

	public void setDataProcesso(LocalDateTime dataProcesso) {
		this.dataProcesso = dataProcesso;
	}

	public String getDocumenti() {
		return documenti;
	}

	public void setDocumenti(String documenti) {
		this.documenti = documenti;
	}
	
	public void setAssegnatario(IstruttoreVO istruttoreVO) {
		this.assegnatario = istruttoreVO;
	}
	
}
