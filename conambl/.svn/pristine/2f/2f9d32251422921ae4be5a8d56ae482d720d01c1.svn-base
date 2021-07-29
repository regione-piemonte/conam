/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbSogPK;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Repository
public interface CnmRAllegatoVerbSogRepository extends CrudRepository<CnmRAllegatoVerbSog, CnmRAllegatoVerbSogPK> {

	List<CnmRAllegatoVerbSog> findByCnmRVerbaleSoggettoIn(List<CnmRVerbaleSoggetto> cnmRVerbaleSoggetto);

	List<CnmRAllegatoVerbSog> findByCnmRVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto);

	List<CnmRAllegatoVerbSog> findByCnmTAllegato(CnmTAllegato cnmTAllegato);
}
