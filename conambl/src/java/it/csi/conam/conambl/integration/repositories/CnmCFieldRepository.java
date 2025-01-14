/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmCField;
import it.csi.conam.conambl.integration.entity.CnmDTipoAllegato;

@Repository
public interface CnmCFieldRepository extends CrudRepository<CnmCField, Long> {

	@Query("Select u from CnmCField u where (u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())")
	List<CnmCField> findAllByFineValidita();
	

	@Query("Select u from CnmCField u where (u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()) AND u.cnmDTipoAllegato = ?1")
	List<CnmCField> findAllByFineValiditaAndCnmDTipoallegato(CnmDTipoAllegato cnmDTipoAllegato);

}
