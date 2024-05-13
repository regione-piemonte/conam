/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.leggi;

import it.csi.conam.conambl.vo.common.CommonVO;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author riccardo.bova
 * @date 13 nov 2018
 */
public class NormaVO extends CommonVO {

	private static final long serialVersionUID = 258645679128055956L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
