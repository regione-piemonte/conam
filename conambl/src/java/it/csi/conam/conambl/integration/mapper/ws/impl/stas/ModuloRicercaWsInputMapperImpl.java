/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.ws.impl.stas;

import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.conam.conambl.integration.entity.CnmDComune;
import it.csi.conam.conambl.integration.entity.CnmDProvincia;
import it.csi.conam.conambl.integration.mapper.ws.stas.ModuloRicercaWsInputMapper;
import it.csi.conam.conambl.integration.repositories.CnmDComuneRepository;
import it.csi.conam.conambl.integration.repositories.CnmDProvinciaRepository;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.gmscore.dto.LuogoNascitaFiltroRicerca;
import it.csi.gmscore.dto.ModuloRicercaPF;

@Component
public class ModuloRicercaWsInputMapperImpl implements ModuloRicercaWsInputMapper {

	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;

	@Autowired
	private CnmDProvinciaRepository cnmDProvinciaRepository;

	@Override
	public ModuloRicercaPF mapVOtoWsType(MinSoggettoVO vo) {
		if (vo == null)
			return null;
		ModuloRicercaPF moduloRicercaPF = new ModuloRicercaPF();
		moduloRicercaPF.setCognome(StringUtils.isNotEmpty(vo.getCognome()) ? vo.getCognome().toUpperCase() : null);
		moduloRicercaPF.setNome(StringUtils.isNotEmpty(vo.getNome()) ? vo.getNome().toUpperCase() : null);
		moduloRicercaPF.setSesso(StringUtils.isNotEmpty(vo.getSesso()) ? vo.getSesso().toUpperCase() : null);

		boolean isNazioneNascitaEstera = vo.getNazioneNascitaEstera() != null && vo.getNazioneNascitaEstera() ;

		// 20221103 Jira-228 - ricerca con lugoo di nascita (rimane estero)
		if (!isNazioneNascitaEstera) {
			if (vo.getComuneNascita() != null && vo.getProvinciaNascita() != null) {
				CnmDComune comune = cnmDComuneRepository.findOne(vo.getComuneNascita().getId());
				CnmDProvincia provincia = cnmDProvinciaRepository.findOne(vo.getProvinciaNascita().getId());
				LuogoNascitaFiltroRicerca lnfr = LuogoNascitaFiltroRicerca.creaLuogoNascitaNazionale(comune.getDenomComune().toUpperCase(), provincia.getDenomProvincia().toUpperCase());
				//LuogoNascitaFiltroRicerca lnfr2 = LuogoNascitaFiltroRicerca.creaLuogoNascitaNazionale(comune.getDenomComune().toUpperCase(), "ITALIA");
				moduloRicercaPF.setLuogoNascita(lnfr);
			}
		} else {
			// TODO STATO ESTERO
//			if (vo.getNazioneNascita() != null) {
//			CnmDNazione nazione = cnmDNazioneRepository.findOne(vo.getNazioneNascita().getId());
//			LuogoNascitaFiltroRicerca lnfr1 = LuogoNascitaFiltroRicerca.creaLuogoNascitaEstero("?", nazione.getDenomNazione().toUpperCase());
//			moduloRicercaPF.setLuogoNascita(lnfr1);
//			}
		}

		if (vo.getDataNascita() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ModuloRicercaPF.FORMATO_DATA_NASCITA_COMPLETA);
			String formatDateTime = vo.getDataNascita().format(formatter);
			moduloRicercaPF.setDataNascita(formatDateTime);
		}
		return moduloRicercaPF;
	}

}
