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
public class TipologiaAllegabiliPregressiRequest extends TipologiaAllegabiliCommonsRequest {

	private static final long serialVersionUID = -3620699452083779279L;

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
