/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.epay.rest.mapper;

import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRSoggRata;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import it.csi.conam.conambl.integration.epay.rest.model.DebtPositionData;


/**
 * @author riccardo.bova
 * @date 31 gen 2019
 */
public interface RestModelMapper {

	DebtPositionData mapRateSoggettoToDebtPositionData(CnmRSoggRata cnmRSoggRata);

	DebtPositionData mapOrdinanzaToDebtPositionData(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog);

	DebtPositionData mapRataSollecitoToDebtPositionData(CnmTSollecito cnmTSollecito);
}
