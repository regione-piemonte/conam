/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import it.csi.conam.conambl.business.service.ordinanza.UtilsOrdinanza;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleSoggettoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilsOrdinanzaImpl implements UtilsOrdinanza {

	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	

	@Override
	public CnmTOrdinanza validateAndGetCnmTOrdinanza(Integer idOrdinanza) {
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza = null");
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza non valido ");

		return cnmTOrdinanza;
	}
	
	@Override
	public boolean gestisciOrdinanzaComePregressa(Integer idOrdinanza) {
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza = null");
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza non valido ");

		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmROrdinanzaVerbSogsIn(cnmROrdinanzaVerbSogList);
		CnmTVerbale cnmTVerbale = cnmRVerbaleSoggettoList.get(0).getCnmTVerbale();
		
		return cnmTOrdinanza.isFlagDocumentoPregresso() && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso()==Constants.STATO_PREGRESSO_IN_LAVORAZIONE;
	}
	
	@Override
	public boolean soggettiVerbaleCompletiDiOrdinanza(CnmTVerbale cnmTVerbale) {
		List<CnmRVerbaleSoggetto>cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
		return cnmRVerbaleSoggettoList.size() == cnmROrdinanzaVerbSogList.size();
	}
}
