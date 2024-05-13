/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity;

import java.math.BigDecimal;

import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.mapper.EntityMapper;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

public interface SoggettoPregressiEntityMapper extends EntityMapper<CnmTSoggetto, SoggettoPregressiVO> {

	void mapVOtoUpdateEntity(SoggettoPregressiVO soggetto, CnmTSoggetto cnmTSoggetto);

	SoggettoPregressiVO createSoggettoPregressiVO(SoggettoVO soggettoVO, CnmTSoggetto dto);

	SoggettoPregressiVO mapEntityToVO(CnmTSoggetto dto, BigDecimal importoMisuraRidotta, BigDecimal importoPagato);
}
