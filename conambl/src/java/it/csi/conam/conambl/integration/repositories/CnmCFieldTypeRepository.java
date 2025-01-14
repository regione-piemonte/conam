/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmCFieldType;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CnmCFieldTypeRepository extends CrudRepository<CnmCFieldType, Long> {


}
