/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmTNota;
import it.csi.conam.conambl.integration.mapper.entity.NotaEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDAmbitoNoteRepository;
import it.csi.conam.conambl.vo.common.SelectVO;
import it.csi.conam.conambl.vo.verbale.NotaVO;

@Component
public class NotaEntityMapperImpl implements NotaEntityMapper {

	@Autowired
	private CnmDAmbitoNoteRepository cnmDAmbitoNoteRepository;

	@Autowired
	private UtilsDate utilsDate;
	
	@Override
	public NotaVO mapEntityToVO(CnmTNota dto) {
		if (dto == null)
			return null;
		
		SelectVO ambito = new SelectVO();
		ambito.setId(dto.getCnmDAmbitoNote().getIdAmbitoNote());
		ambito.setDenominazione(dto.getCnmDAmbitoNote().getDescAmbitoNote());
		NotaVO nota = new NotaVO(
				dto.getIdNota(), 
				dto.getOggetto(), 
				dto.getDesc(), 
				utilsDate.asLocalDateTime(dto.getDate()), 
				ambito);
		return nota;
	}

	@Override
	public CnmTNota mapVOtoEntity(NotaVO vo) {
		if (vo == null)
			return null;
		CnmTNota dto = new CnmTNota();
		if(vo.getIdNota()!=null) {
			dto.setIdNota(vo.getIdNota());
		}
		dto.setOggetto(vo.getOggetto()==null?"":vo.getOggetto());
		dto.setDesc(vo.getDescrizione());
		dto.setDate(utilsDate.asTimeStamp(vo.getData()));
		dto.setCnmDAmbitoNote(cnmDAmbitoNoteRepository.findOne(vo.getAmbito().getId()));
		return dto;
	}

}
