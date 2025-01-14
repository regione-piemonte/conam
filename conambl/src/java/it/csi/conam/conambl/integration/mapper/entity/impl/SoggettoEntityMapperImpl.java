/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.conam.conambl.business.facade.StasServFacade;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmTPersona;
import it.csi.conam.conambl.integration.entity.CnmTResidenza;
import it.csi.conam.conambl.integration.entity.CnmTSocieta;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.mapper.entity.ComuneEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.NazioneEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.PersonaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ProvinciaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.RegioneEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.ResidenzaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.SocietaEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.mapper.ws.stas.AnagraficaWsOutputMapper;
import it.csi.conam.conambl.integration.repositories.CnmTResidenzaRepository;
import it.csi.conam.conambl.util.StringConamUtils;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoPagamentoVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

@Component
public class SoggettoEntityMapperImpl implements SoggettoEntityMapper {
	@Autowired
	private PersonaEntityMapper personaEntityMapper;
	@Autowired
	private SocietaEntityMapper societaEntityMapper;
	@Autowired
	private AnagraficaWsOutputMapper anagraficaWsOutputMapper;
	@Autowired
	private ResidenzaEntityMapper residenzaEntityMapper;
	@Autowired
	private NazioneEntityMapper nazioneEntityMapper;
	@Autowired
	private RegioneEntityMapper regioneEntityMapper;
	@Autowired
	private ProvinciaEntityMapper provinciaEntityMapper;
	@Autowired
	private ComuneEntityMapper comuneEntityMapper;
	@Autowired
	private StasServFacade stasServFacade;
	@Autowired
	private CnmTResidenzaRepository cnmTResidenzaRepository;

	@Override
	public MinSoggettoVO mapEntityToMinVO(CnmTSoggetto dto) {
		if (dto == null)
			return null;
		MinSoggettoVO minSoggettoVO = new MinSoggettoVO();

		minSoggettoVO.setId(dto.getIdSoggetto());
		minSoggettoVO.setCognome(dto.getCognome());
		minSoggettoVO.setNome(dto.getNome());

		return minSoggettoVO;
	}
	@Override
	public SoggettoVO mapEntityToVO(CnmTSoggetto dto, BigDecimal importoMisuraRidotta, BigDecimal importoPagato) {
		SoggettoVO soggettoVO = mapEntityToVO(dto);
		soggettoVO.setImportoVerbale(importoMisuraRidotta.doubleValue());
		soggettoVO.setImportoResiduoVerbale(importoMisuraRidotta.doubleValue()-importoPagato.doubleValue());
		return soggettoVO;
	}
	@Override
	public SoggettoVO mapEntityToVO(CnmTSoggetto dto) {
		if (dto == null)
			return null;
		SoggettoVO soggettoVO = new SoggettoVO();
		CnmTPersona cnmTPersona = dto.getCnmTPersona();
		CnmTSocieta cnmTSocieta = dto.getCnmTSocieta();

		// FIX issue 45
		//CnmTResidenza cnmTResidenza = cnmTResidenzaRepository.findByCnmTSoggetto(dto);
		CnmTResidenza cnmTResidenza = getLastResidenza(dto);
		/*
		if(cnmTResidenza == null){
			cnmTResidenza = getLastResidenza(dto);
		}
		 */
		SoggettoVO sogStas = new SoggettoVO();
		
		//dataFineValidita
		// soggetto presente su stas
		if (dto.getIdStas() != null) {
			sogStas = anagraficaWsOutputMapper.mapWsTypeToVO(stasServFacade.ricercaSoggettoID(dto.getIdStas().longValue()));
			if (cnmTPersona != null) {
				if (cnmTPersona.getCnmDNazione() != null) {
					sogStas.setNazioneNascitaEstera(true);
					sogStas.setNazioneNascita(nazioneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDNazione()));
				}
				if (cnmTPersona.getDenomComuneNascitaEstero() != null) {
					sogStas.setDenomComuneNascitaEstero(cnmTPersona.getDenomComuneNascitaEstero());
				}
				if (cnmTPersona.getCnmDComune() != null) {
					sogStas.setRegioneNascita(regioneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune().getCnmDProvincia().getCnmDRegione()));
					sogStas.setProvinciaNascita(provinciaEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune().getCnmDProvincia()));
					sogStas.setComuneNascita(comuneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune()));
				}
			}
			
			// 20211124_LC Jira 177	-	Se ci sono dati Stas non mappa dalla residenza del DB ma direttamente da Stas (fatto sopra in: mapWsTypeToVO), se non ci sonol mappa alal fine del metodo
			
			//if (cnmTResidenza != null) {
				sogStas.setIndirizzoResidenzaStas(sogStas.getIndirizzoResidenza());
				sogStas.setCivicoResidenzaStas(sogStas.getCivicoResidenza());
				sogStas.setCapStas(sogStas.getCap());
				//sogStas = residenzaEntityMapper.mapEntitytoVOUpdate(sogStas, cnmTResidenza);
			//}
				
				
			sogStas.setId(dto.getIdSoggetto());
			sogStas.setIdStas(dto.getIdStas());
			return sogStas;
		}
		
		
		// soggetto non presente su stas
		if (cnmTPersona != null) {
			soggettoVO = personaEntityMapper.mapEntityToVO(cnmTPersona);
			soggettoVO.setCodiceFiscale(dto.getCodiceFiscale());
			soggettoVO.setCognome(dto.getCognome());
			soggettoVO.setNome(dto.getNome());
			if (cnmTPersona != null && cnmTPersona.getCnmDNazione() != null) {
				soggettoVO.setNazioneNascitaEstera(true);
				soggettoVO.setNazioneNascita(nazioneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDNazione()));
			}
			if (cnmTPersona.getDenomComuneNascitaEstero() != null) {
				soggettoVO.setDenomComuneNascitaEstero(cnmTPersona.getDenomComuneNascitaEstero());
			}
			if (cnmTPersona.getCnmDComune() != null) {
				soggettoVO.setRegioneNascita(regioneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune().getCnmDProvincia().getCnmDRegione()));
				soggettoVO.setProvinciaNascita(provinciaEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune().getCnmDProvincia()));
				soggettoVO.setComuneNascita(comuneEntityMapper.mapEntityToVO(cnmTPersona.getCnmDComune()));
			}
		} else if (cnmTSocieta != null) {
			soggettoVO = societaEntityMapper.mapEntityToVO(cnmTSocieta);
			soggettoVO.setPartitaIva(dto.getPartitaIva());
			soggettoVO.setRagioneSociale(dto.getRagioneSociale());
			soggettoVO.setCodiceFiscale(dto.getCodiceFiscaleGiuridico());
		} else {
			soggettoVO.setCodiceFiscale(!StringUtils.isBlank(dto.getCodiceFiscale()) ? dto.getCodiceFiscale() : dto.getCodiceFiscaleGiuridico());
			soggettoVO.setPartitaIva(dto.getPartitaIva());
			soggettoVO.setRagioneSociale(dto.getRagioneSociale());
		}

		
		if (cnmTResidenza != null) {
			soggettoVO = residenzaEntityMapper.mapEntitytoVOUpdate(soggettoVO, cnmTResidenza);
		}
		
		
		soggettoVO.setId(dto.getIdSoggetto());
		soggettoVO.setIdStas(dto.getIdStas());

		return soggettoVO;
	}

	private CnmTResidenza getLastResidenza(CnmTSoggetto dto) {
		List<CnmTResidenza> cnmTResidenzaList = cnmTResidenzaRepository.findAllByCnmTSoggetto(dto);
		CnmTResidenza cnmTResidenza = null;
		for(CnmTResidenza residenza : cnmTResidenzaList){
			if(cnmTResidenza == null){
				cnmTResidenza = residenza;
			}else{
				Date date1 = residenza.getDataOraUpdate()!=null?residenza.getDataOraUpdate():residenza.getDataOraInsert();
				Date date2 = cnmTResidenza.getDataOraUpdate()!=null?cnmTResidenza.getDataOraUpdate():cnmTResidenza.getDataOraInsert();
				if(date1.compareTo(date2)>0){
					cnmTResidenza=residenza;
				}
			}
		}
		return cnmTResidenza;
	}


	@Override
	public CnmTSoggetto mapVOtoEntity(SoggettoVO soggettoVO) {
		if (soggettoVO == null)
			return null;
		CnmTSoggetto cnmTSoggetto = new CnmTSoggetto();

		boolean isPersonaFisica = soggettoVO.getPersonaFisica() != null && soggettoVO.getPersonaFisica();

		if (isPersonaFisica) {
			cnmTSoggetto.setCnmTPersona(personaEntityMapper.mapVOtoEntity(soggettoVO));
			cnmTSoggetto.setCodiceFiscale(StringConamUtils.nullOrUppercase(soggettoVO.getCodiceFiscale()));
			cnmTSoggetto.setCognome(StringConamUtils.nullOrUppercase(soggettoVO.getCognome()));
			cnmTSoggetto.setNome(StringConamUtils.nullOrUppercase(soggettoVO.getNome()));
		} else {
			cnmTSoggetto.setCnmTSocieta(societaEntityMapper.mapVOtoEntity(soggettoVO));
			cnmTSoggetto.setPartitaIva(StringConamUtils.nullOrUppercase(soggettoVO.getPartitaIva()));
			cnmTSoggetto.setCodiceFiscaleGiuridico(StringConamUtils.nullOrUppercase(soggettoVO.getCodiceFiscale()));
			cnmTSoggetto.setRagioneSociale(soggettoVO.getRagioneSociale());
		}

		cnmTSoggetto.setIdStas(soggettoVO.getIdStas());

		return cnmTSoggetto;
	}

	@Override
	public void mapVOtoUpdateEntity(SoggettoVO soggetto, CnmTSoggetto cnmTSoggetto) {
		if (soggetto == null)
			return;

		boolean isPersonaFisica = soggetto.getPersonaFisica() != null && soggetto.getPersonaFisica();

		if (isPersonaFisica) {
			if(cnmTSoggetto.getCnmTPersona()!=null)
				cnmTSoggetto.setCnmTPersona(personaEntityMapper.mapVOtoEntityUpdate(soggetto, cnmTSoggetto.getCnmTPersona()));
			cnmTSoggetto.setCodiceFiscale(StringConamUtils.nullOrUppercase(soggetto.getCodiceFiscale()));
			cnmTSoggetto.setCognome(StringConamUtils.nullOrUppercase(soggetto.getCognome()));
			cnmTSoggetto.setNome(StringConamUtils.nullOrUppercase(soggetto.getNome()));
		} else {
			if(cnmTSoggetto.getCnmTSocieta()!=null)
				cnmTSoggetto.setCnmTSocieta(societaEntityMapper.mapVOtoEntityUpdate(soggetto, cnmTSoggetto.getCnmTSocieta()));
			cnmTSoggetto.setPartitaIva(StringConamUtils.nullOrUppercase(soggetto.getPartitaIva()));
			cnmTSoggetto.setCodiceFiscaleGiuridico(StringConamUtils.nullOrUppercase(soggetto.getCodiceFiscale()));
			cnmTSoggetto.setRagioneSociale(soggetto.getRagioneSociale());
		}

		cnmTSoggetto.setIdStas(soggetto.getIdStas());

		return;

	}

}
