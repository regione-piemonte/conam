/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class TipologiaAllegabiliCommonsRequest extends ParentRequest {

	private static final long serialVersionUID = -523406994523779279L;

	protected String tipoDocumento;
	protected boolean aggiungiCategoriaEmail;

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isAggiungiCategoriaEmail() {
		return aggiungiCategoriaEmail;
	}

	public void setAggiungiCategoriaEmail(boolean aggiungiCategoriaEmail) {
		this.aggiungiCategoriaEmail = aggiungiCategoriaEmail;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
