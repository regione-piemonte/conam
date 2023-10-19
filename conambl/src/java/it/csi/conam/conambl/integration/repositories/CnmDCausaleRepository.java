/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmDCausale;

@Repository
public interface CnmDCausaleRepository extends CrudRepository<CnmDCausale, Long> {
}
