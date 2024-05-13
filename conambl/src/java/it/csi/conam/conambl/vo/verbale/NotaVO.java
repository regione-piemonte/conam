/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import java.time.LocalDateTime;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.web.serializer.CustomDateTimeDeserializer;
import it.csi.conam.conambl.web.serializer.CustomDateTimeSerializer;

/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class NotaVO {

	Long idNota;
	String oggetto;
	String descrizione;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	LocalDateTime data;	
	SelectVO ambito;
	
	public NotaVO(Long idNota, String oggetto, String descrizione, LocalDateTime data, SelectVO ambito) {
		super();
		this.idNota = idNota;
		this.oggetto = oggetto;
		this.descrizione = descrizione;
		this.data = data;
		this.ambito = ambito;
	}
	

	public NotaVO() {
	}


	public Long getIdNota() {
		return idNota;
	}


	public void setIdNota(Long idNota) {
		this.idNota = idNota;
	}


	public String getOggetto() {
		return oggetto;
	}


	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public LocalDateTime getData() {
		return data;
	}


	public void setData(LocalDateTime data) {
		this.data = data;
	}


	public SelectVO getAmbito() {
		return ambito;
	}


	public void setAmbito(SelectVO ambito) {
		this.ambito = ambito;
	}
	
}
