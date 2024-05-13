/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import it.csi.conam.conambl.request.TipologiaAllegabiliCommonsRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class TipologiaAllegabiliOrdinanzaSoggettoRequest extends TipologiaAllegabiliCommonsRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	@NotNull(message = "REQCON03")
	private List<Integer> id;
	
	public List<Integer> getId() {
		return id;
	}

	public void setId(List<Integer> id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
