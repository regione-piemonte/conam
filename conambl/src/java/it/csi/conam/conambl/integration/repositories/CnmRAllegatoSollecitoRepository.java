/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmRAllegatoSollecito;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoSollecitoPK;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;

@Repository
public interface CnmRAllegatoSollecitoRepository extends CrudRepository<CnmRAllegatoSollecito, CnmRAllegatoSollecitoPK> {

	List<CnmRAllegatoSollecito> findByCnmTSollecito(CnmTSollecito cnmTSollecito);

	@Query("select ao.cnmTAllegato from CnmRAllegatoSollecito ao where ao.cnmTSollecito=?1")
	List<CnmTAllegato> findCnmTAllegatosByCnmTSollecito(CnmTSollecito cnmTSollecito);

	List<CnmRAllegatoSollecito> findByCnmTAllegato(CnmTAllegato cnmTAllegato);
	
	


}
