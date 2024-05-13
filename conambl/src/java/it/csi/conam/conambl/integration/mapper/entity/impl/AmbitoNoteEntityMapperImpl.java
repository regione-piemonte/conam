/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import org.springframework.stereotype.Component;

import it.csi.conam.conambl.integration.entity.CnmDAmbitoNote;
import it.csi.conam.conambl.integration.mapper.entity.AmbitoNoteEntityMapper;
import it.csi.conam.conambl.vo.common.SelectVO;

@Component
public class AmbitoNoteEntityMapperImpl implements AmbitoNoteEntityMapper {

	@Override
	public SelectVO mapEntityToVO(CnmDAmbitoNote dto) {
		if (dto == null)
			return null;
		SelectVO ambito = new SelectVO();
		ambito.setDenominazione(dto.getDescAmbitoNote());
		ambito.setId(dto.getIdAmbitoNote().longValue());
		//ambito.setAcronimo(dto.getAcronimo());
		return ambito;
	}

	@Override
	public CnmDAmbitoNote mapVOtoEntity(SelectVO vo) {
		if (vo == null)
			return null;
		CnmDAmbitoNote dto = new CnmDAmbitoNote();
		dto.setDescAmbitoNote(vo.getDenominazione());
		dto.setIdAmbitoNote(vo.getId());
		return dto;
	}

}
