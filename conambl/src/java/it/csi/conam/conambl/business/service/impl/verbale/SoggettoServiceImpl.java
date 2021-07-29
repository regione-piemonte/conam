/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.verbale.SoggettoService;
import it.csi.conam.conambl.business.service.verbale.UtilsSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoPregressiEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmTSoggettoRepository;
import it.csi.conam.conambl.integration.specification.CnmTSoggettoSpecification;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPregressiVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoggettoServiceImpl implements SoggettoService {

	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private SoggettoPregressiEntityMapper soggettoPregressiEntityMapper;
	@Autowired
	private UtilsSoggetto utilsSoggetto;
	/*@Autowired
	private SoggettoVerbaleService soggettoVerbaleService;*/
	@Autowired
	private CommonSoggettoService commonSoggettoService;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;

	/*LUCIO 2021/04/23 - FINE Gestione casi di recidività*/
	public List<SoggettoVO> ricercaSoggetti(MinSoggettoVO soggetto, UserDetails userDetails){
		List<CnmTSoggetto> soggetti = cnmTSoggettoRepository.findAll(
			CnmTSoggettoSpecification.findSoggetti(soggetto)
		);
		return soggettoEntityMapper.mapListEntityToListVO(soggetti);
	}
	/*LUCIO 2021/04/23 - Gestione casi di recidività*/
	@Override
	public SoggettoVO getSoggettoById(Integer id, UserDetails userDetails, boolean includiControlloUtenteProprietario) {
		CnmTSoggetto cnmTSoggetto = utilsSoggetto.validateAndGetCnmTSoggetto(id);

		return soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);

	}
	
	@Override
	public SoggettoPregressiVO getSoggettoPregressiById(Integer id, UserDetails userDetails, boolean includiControlloUtenteProprietario) {
		CnmTSoggetto cnmTSoggetto = utilsSoggetto.validateAndGetCnmTSoggetto(id);

		return soggettoPregressiEntityMapper.mapEntityToVO(cnmTSoggetto);

	}

	@Override
	public SoggettoVO getSoggettoByIdAndIdVerbale(Integer id, Integer idVerbale, UserDetails userDetails, boolean includiControlloUtenteProprietario) {
		CnmTSoggetto cnmTSoggetto = utilsSoggetto.validateAndGetCnmTSoggetto(id);

		SoggettoVO soggetto = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
		soggetto = commonSoggettoService.attachResidenzaPregressi(soggetto, cnmTSoggetto, idVerbale);
		
		return soggetto;

	}
}
