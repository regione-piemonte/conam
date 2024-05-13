/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.leggi;

import it.csi.conam.conambl.vo.common.CommonVO;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class LetteraVO extends CommonVO {

	private static final long serialVersionUID = 8011990771663997222L;

	private BigDecimal sanzioneMinima;
	private BigDecimal sanzioneMassima;
	private BigDecimal importoMisuraRidotta;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate scadenzaImporti;
	private String descrizioneIllecito;

	public BigDecimal getSanzioneMinima() {
		return sanzioneMinima;
	}
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = super.hashCode();
	    result = prime * result + ((sanzioneMinima == null) ? 0 : sanzioneMinima.hashCode());
	    result = prime * result + ((sanzioneMassima == null) ? 0 : sanzioneMassima.hashCode());
	    result = prime * result + ((importoMisuraRidotta == null) ? 0 : importoMisuraRidotta.hashCode());
	    result = prime * result + ((scadenzaImporti == null) ? 0 : scadenzaImporti.hashCode());
	    result = prime * result + ((descrizioneIllecito == null) ? 0 : descrizioneIllecito.hashCode());
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null || getClass() != obj.getClass())
	        return false;
	    if (!super.equals(obj))
	        return false;

	    LetteraVO other = (LetteraVO) obj;

	    if (sanzioneMinima != null ? !sanzioneMinima.equals(other.sanzioneMinima) : other.sanzioneMinima != null)
	        return false;
	    if (sanzioneMassima != null ? !sanzioneMassima.equals(other.sanzioneMassima) : other.sanzioneMassima != null)
	        return false;
	    if (importoMisuraRidotta != null ? !importoMisuraRidotta.equals(other.importoMisuraRidotta) : other.importoMisuraRidotta != null)
	        return false;
	    if (scadenzaImporti != null ? !scadenzaImporti.equals(other.scadenzaImporti) : other.scadenzaImporti != null)
	        return false;
	    return descrizioneIllecito != null ? descrizioneIllecito.equals(other.descrizioneIllecito) : other.descrizioneIllecito == null;
	}


	public BigDecimal getSanzioneMassima() {
		return sanzioneMassima;
	}

	public BigDecimal getImportoMisuraRidotta() {
		return importoMisuraRidotta;
	}

	public LocalDate getScadenzaImporti() {
		return scadenzaImporti;
	}

	public String getDescrizioneIllecito() {
		return descrizioneIllecito;
	}

	public void setSanzioneMinima(BigDecimal sanzioneMinima) {
		this.sanzioneMinima = sanzioneMinima;
	}

	public void setSanzioneMassima(BigDecimal sanzioneMassima) {
		this.sanzioneMassima = sanzioneMassima;
	}

	public void setImportoMisuraRidotta(BigDecimal importoMisuraRidotta) {
		this.importoMisuraRidotta = importoMisuraRidotta;
	}

	public void setScadenzaImporti(LocalDate scadenzaImporti) {
		this.scadenzaImporti = scadenzaImporti;
	}

	public void setDescrizioneIllecito(String descrizioneIllecito) {
		this.descrizioneIllecito = descrizioneIllecito;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
