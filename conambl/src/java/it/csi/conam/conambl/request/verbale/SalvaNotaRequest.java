/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.verbale;

import org.apache.commons.lang3.builder.ToStringBuilder;

import it.csi.conam.conambl.request.ParentRequest;
import it.csi.conam.conambl.vo.verbale.NotaVO;

/**
 * @author 
 */
public class SalvaNotaRequest extends ParentRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private Long idVerbale;
	private NotaVO nota;


	public Long getIdVerbale() {
		return idVerbale;
	}


	public void setIdVerbale(Long idVerbale) {
		this.idVerbale = idVerbale;
	}


	public NotaVO getNota() {
		return nota;
	}


	public void setNota(NotaVO nota) {
		this.nota = nota;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
