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
public class ArticoloVO extends CommonVO {

	private static final long serialVersionUID = -8881880219104118149L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
