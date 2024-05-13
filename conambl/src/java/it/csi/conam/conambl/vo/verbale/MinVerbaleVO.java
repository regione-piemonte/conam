/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class MinVerbaleVO extends MinVerbaleVOCommons {

	private static final long serialVersionUID = -884216214905682332L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
