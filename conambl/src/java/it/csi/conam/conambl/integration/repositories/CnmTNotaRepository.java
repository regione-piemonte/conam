/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmTNota;

@Repository
public interface CnmTNotaRepository extends CrudRepository<CnmTNota, Long> {
	
	@Query("Select u from CnmTNota u where u.idVerbale=?1")
	public List<CnmTNota> findByIdVerbale(int idVerbale);
	
}
