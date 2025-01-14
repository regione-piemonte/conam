/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmRAllegatoVerbalePK;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnmRAllegatoVerbaleRepository extends CrudRepository<CnmRAllegatoVerbale, CnmRAllegatoVerbalePK> {

	List<CnmRAllegatoVerbale> findByCnmTVerbale(CnmTVerbale cnmTVerbale);

//	@Query("select av from CnmRAllegatoVerbale av where av.cnmTVerbale=?1 and av.cnmTAllegato.numeroProtocollo != null")
//	List<CnmRAllegatoVerbale> findByCnmTVerbaleSoloProtocollati(CnmTVerbale cnmTVerbale);
	
	List<CnmRAllegatoVerbale> findByCnmTVerbaleAndCnmTAllegatoNumeroProtocolloIsNotNull(CnmTVerbale cnmTVerbale);

	@Query("select av.cnmTAllegato from CnmRAllegatoVerbale av where av.cnmTVerbale=?1")
	List<CnmTAllegato> findCnmTAllegatosByCnmTVerbale(CnmTVerbale cnmTVerbale);
	

	@Query("select av.cnmTAllegato from CnmRAllegatoVerbale av where av.cnmTVerbale=?1 and av.cnmTAllegato.cnmDTipoAllegato.idTipoAllegato in (41) and av.cnmTAllegato.idActa is null")
	List<CnmTAllegato> findCnmTAllegatosByCnmTVerbaleNoProt(CnmTVerbale cnmTVerbale);	

	@Query("select av.cnmTAllegato from CnmRAllegatoVerbale av where av.cnmTVerbale=?1 and av.cnmTAllegato.cnmDTipoAllegato.idTipoAllegato in (42) and av.cnmTAllegato.idActa is null and av.cnmTAllegato.idActaMaster=?2")
	List<CnmTAllegato> findCnmTAllegatosByCnmTVerbaleAllegatoNoProt(CnmTVerbale cnmTVerbale, String idPadre);

	CnmRAllegatoVerbale findByCnmTAllegato(CnmTAllegato cnmTAllegato);

}
