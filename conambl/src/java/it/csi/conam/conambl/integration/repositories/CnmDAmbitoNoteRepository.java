/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmDAmbitoNote;

@Repository
public interface CnmDAmbitoNoteRepository extends CrudRepository<CnmDAmbitoNote, Long> {

}
