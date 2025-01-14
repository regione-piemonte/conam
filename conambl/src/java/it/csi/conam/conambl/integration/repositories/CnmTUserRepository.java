/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.entity.CnmTUser;

/**
 * @author riccardo.bova
 * @date 12 nov 2018
 */
@Repository
public interface CnmTUserRepository extends CrudRepository<CnmTUser, Long> {

	@Query("Select u from CnmTUser u where u.codiceFiscale=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))")
	CnmTUser findByCodiceFiscaleAndFineValidita(String codiceFiscale);
	
	@Query("Select u from CnmTUser u where u.cnmDRuolo.idRuolo=1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))")
	List<CnmTUser> findIstruttoriByFineValidita();

	CnmTUser findByIdUser(Long idUser);
}
