/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CampiRicercaFormVO implements Serializable {
	
	public static final Long ID_RICERCA_STILO = 2L;

    private static final long serialVersionUID = -114221230786404845L;
    
    private long idRicerca;

    private List<SearchData> ricerca = new ArrayList<>();

    public List<SearchData> getRicerca() {
        return ricerca;
    }

    public void setRicerca(List<SearchData> ricerca) {
        this.ricerca = ricerca;
    }
    
    public long getIdRicerca() {
		return idRicerca;
	}

	public void setIdRicerca(long idRicerca) {
		this.idRicerca = idRicerca;
	}

	public static class SearchData {
        private Long idField;
        private String value;

        public Long getIdField() {
            return idField;
        }

        public void setIdField(Long idField) {
            this.idField = idField;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

