/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common.exception;

public class BollettinoException extends BusinessException {

	private static final long serialVersionUID = -2631969419398877860L;

	private String codiceEsito;

	public String getCodiceEsito() {
		return codiceEsito;
	}

	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
	}

	public BollettinoException(String codice, String codiceEsito) {
		super(codice);
		this.codiceEsito = codiceEsito;
	}

}
