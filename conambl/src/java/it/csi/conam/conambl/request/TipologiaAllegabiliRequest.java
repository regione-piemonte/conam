/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class TipologiaAllegabiliRequest extends TipologiaAllegabiliCommonsRequest {

	private static final long serialVersionUID = -43299452083779279L;

	@NotNull(message = "REQCON06")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
