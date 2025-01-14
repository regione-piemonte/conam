/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import it.csi.conam.conambl.integration.entity.CnmCFieldRicerca;
import it.csi.conam.conambl.integration.mapper.entity.CfieldTypeEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ConfigAllegatoRicercaEntityMapper;
import it.csi.conam.conambl.vo.verbale.allegato.ConfigAllegatoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigAllegatoRicercaEntityMapperImpl implements ConfigAllegatoRicercaEntityMapper {

	@Autowired
	private CfieldTypeEntityMapper cfieldTypeEntityMapper;

	public ConfigAllegatoVO mapEntityToVO(CnmCFieldRicerca dto) {
		if (dto == null)
			return null;
		ConfigAllegatoVO conf = new ConfigAllegatoVO();

		conf.setRiga(dto.getRiga());
		conf.setFieldType(cfieldTypeEntityMapper.mapEntityToVO(dto.getCnmCFieldType()));
		conf.setIdTipo(dto.getCnmDTipoRicerca().getIdTipoRicerca());
		conf.setMaxLength(dto.getMaxLength());
		conf.setLabel(dto.getLabel());
		conf.setRequired(dto.getRequired());
		conf.setScale(dto.getScale());
		conf.setIdFonteElenco(dto.getCnmDElenco() == null ? null : dto.getCnmDElenco().getIdElenco());
		conf.setOrdine(dto.getOrdine());
		conf.setIdField(dto.getIdFieldRicerca());
		return conf;

	}

}
