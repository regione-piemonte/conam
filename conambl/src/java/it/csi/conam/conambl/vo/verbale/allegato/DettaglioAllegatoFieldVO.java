/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale.allegato;
/**
 *  @author riccardo.bova
 *  @date 21 nov 2018
 */

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import it.csi.conam.conambl.vo.ParentVO;

public class DettaglioAllegatoFieldVO extends ParentVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	List<AllegatoFieldVO> allegatoFields;
	
	List<ConfigAllegatoVO> config;
	
	Boolean edit = false;
	
	public DettaglioAllegatoFieldVO(List<AllegatoFieldVO> allegatoFields, List<ConfigAllegatoVO> config, Boolean edit) {
		super();
		this.allegatoFields = allegatoFields;
		this.config = config;
		this.edit = edit;
	}


	public List<AllegatoFieldVO> getAllegatoFields() {
		return allegatoFields;
	}


	public void setAllegatoFields(List<AllegatoFieldVO> allegatoFields) {
		this.allegatoFields = allegatoFields;
	}


	public List<ConfigAllegatoVO> getConfig() {
		return config;
	}


	public void setConfig(List<ConfigAllegatoVO> config) {
		this.config = config;
	}

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
