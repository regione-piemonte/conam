/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmCFieldRicerca;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmCFieldRicercaRepository extends CrudRepository<CnmCFieldRicerca, Long> {

	@Query("Select u from CnmCFieldRicerca u "
			+ "where (u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())"
			+ "and u.cnmDTipoRicerca.idTipoRicerca is null or u.cnmDTipoRicerca.idTipoRicerca = ?1")
	List<CnmCFieldRicerca> findAllByFineValidita(Long id);

}
