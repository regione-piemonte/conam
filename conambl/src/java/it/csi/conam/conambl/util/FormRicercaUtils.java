/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.util;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.conam.conambl.integration.entity.CnmCFieldRicerca;
import it.csi.conam.conambl.integration.repositories.CnmCFieldRicercaRepository;

@Service
public class FormRicercaUtils {

	@Autowired
	private CnmCFieldRicercaRepository cnmCFieldRicercaRepository;
	
	public CnmCFieldRicerca getFiltroActa(Long id) throws IllegalArgumentException, EntityNotFoundException {
	    if (id == null) {
	        throw new IllegalArgumentException("ID cannot be null");
	    }

	    CnmCFieldRicerca fieldRicerca = cnmCFieldRicercaRepository.findOne(id);
	    if (fieldRicerca == null) {
	        throw new EntityNotFoundException("Record not found for ID: " + id);
	    }

	    return fieldRicerca;
	}

}
