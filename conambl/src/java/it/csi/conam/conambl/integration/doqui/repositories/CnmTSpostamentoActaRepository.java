/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.conam.conambl.integration.doqui.entity.CnmTSpostamentoActa;

@Repository
public interface CnmTSpostamentoActaRepository extends CrudRepository<CnmTSpostamentoActa, Integer> {

	// in fase di copia e sposta, verifica che il protocollo non sia gia in spostamento
	CnmTSpostamentoActa findByIdVerbaleAndNumeroProtocolloAndStato(Integer idVerbale, String numeroProtocollo, String stato);

	// durante la ricerca, verifica che il protocollo non sia gia in spostamento
	List<CnmTSpostamentoActa>  findByNumeroProtocolloAndStatoIn(String numProtocollo, List<String> statiInCorso);

	// nel batch recuper atutti i protocolli in spostamento
	List<CnmTSpostamentoActa> findByStato(String statoInCorso);

	// nel batch recuper atutti i protocolli in spostamento
	List<CnmTSpostamentoActa> findByStatoIn(List<String> statiInCorso);

	List<CnmTSpostamentoActa> findByIdVerbale(Integer idVerbale);
	

}
