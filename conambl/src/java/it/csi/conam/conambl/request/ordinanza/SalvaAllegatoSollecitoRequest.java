/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request.ordinanza;

import org.apache.commons.lang3.builder.ToStringBuilder;

import it.csi.conam.conambl.request.SalvaAllegatoRequest;
import it.csi.conam.conambl.vo.sollecito.SollecitoVO;

/**
 * @author riccardo.bova
 * @date 19 nov 2018
 */
public class SalvaAllegatoSollecitoRequest extends SalvaAllegatoRequest {

	private static final long serialVersionUID = -3620699452083779279L;

	private SollecitoVO sollecitoVO;

	public SollecitoVO getSollecitoVO() {
		return sollecitoVO;
	}

	public void setSollecitoVO(SollecitoVO sollecitoVO) {
		this.sollecitoVO = sollecitoVO;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
