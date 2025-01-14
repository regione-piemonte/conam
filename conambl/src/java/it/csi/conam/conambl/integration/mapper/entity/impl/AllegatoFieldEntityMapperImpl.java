/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.integration.entity.CnmTAllegatoField;
import it.csi.conam.conambl.integration.mapper.entity.AllegatoFieldEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.CfieldTypeEntityMapper;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoFieldVO;

@Component
public class AllegatoFieldEntityMapperImpl implements AllegatoFieldEntityMapper {

	@Autowired
	private CfieldTypeEntityMapper cfieldTypeEntityMapper;
	@Autowired
	private UtilsDate utilsDate;

	@Override
	public AllegatoFieldVO mapEntityToVO(CnmTAllegatoField dto) {
		if (dto == null)
			return null;
		
		AllegatoFieldVO allegatoFieldVO = new AllegatoFieldVO();
		allegatoFieldVO.setBooleanValue(dto.getValoreBoolean());
		allegatoFieldVO.setDateTimeValue(dto.getValoreDataOra()!=null?dto.getValoreDataOra().toLocalDateTime():null);
		allegatoFieldVO.setDateValue(dto.getValoreData()!=null?utilsDate.asLocalDate(dto.getValoreData()):null);
		allegatoFieldVO.setIdField(dto.getCnmCField().getIdField());
		allegatoFieldVO.setNumberValue(dto.getValoreNumber());
		allegatoFieldVO.setStringValue(dto.getValoreString());
		allegatoFieldVO.setFieldType(cfieldTypeEntityMapper.mapEntityToVO(dto.getCnmCField().getCnmCFieldType()));
		if(dto.getValoreBoolean()!= null) {
			if(dto.getValoreBoolean()) allegatoFieldVO.setGenericValue("true");
			else allegatoFieldVO.setGenericValue("false");
		}
		
		if(dto.getValoreDataOra()!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			allegatoFieldVO.setGenericValue(sdf.format(dto.getValoreDataOra()));
		}
		if(dto.getValoreData()!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			allegatoFieldVO.setGenericValue(sdf.format(dto.getValoreData()));
		}
		if(dto.getValoreNumber()!=null) {
			allegatoFieldVO.setGenericValue(dto.getValoreString());
		}
		if(dto.getValoreString()!=null) {
			allegatoFieldVO.setGenericValue(dto.getValoreString());
		}
		return allegatoFieldVO;
	}

}
