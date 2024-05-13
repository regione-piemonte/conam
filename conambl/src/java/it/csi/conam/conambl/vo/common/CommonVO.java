/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.common;

import java.time.LocalDate;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import it.csi.conam.conambl.web.serializer.CustomDateDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class CommonVO extends SelectVO {

	private static final long serialVersionUID = -2641198993382229145L;

	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataFineValidita;
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dataInizioValidita;

	public LocalDate getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(LocalDate dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public LocalDate getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(LocalDate dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((dataFineValidita == null) ? 0 : dataFineValidita.hashCode());
        result = prime * result + ((dataInizioValidita == null) ? 0 : dataInizioValidita.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof CommonVO))
            return false;
        CommonVO other = (CommonVO) obj;
        if (dataFineValidita == null) {
            if (other.dataFineValidita != null)
                return false;
        } else if (!dataFineValidita.equals(other.dataFineValidita))
            return false;
        if (dataInizioValidita == null) {
            if (other.dataInizioValidita != null)
                return false;
        } else if (!dataInizioValidita.equals(other.dataInizioValidita))
            return false;

        return true;
    }
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
