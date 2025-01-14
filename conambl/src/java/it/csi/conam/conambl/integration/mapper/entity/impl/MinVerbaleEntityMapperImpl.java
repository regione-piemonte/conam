/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.mapper.entity.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.entity.CnmDArticolo;
import it.csi.conam.conambl.integration.entity.CnmDComma;
import it.csi.conam.conambl.integration.entity.CnmDLettera;
import it.csi.conam.conambl.integration.entity.CnmDNorma;
import it.csi.conam.conambl.integration.entity.CnmREnteNorma;
import it.csi.conam.conambl.integration.entity.CnmRFunzionarioIstruttore;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleIllecito;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.mapper.entity.EnteEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.IstruttoreEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.MinVerbaleEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoManualeEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoVerbaleEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmRFunzionarioIstruttoreRepository;
import it.csi.conam.conambl.integration.repositories.CnmRVerbaleIllecitoRepository;
import it.csi.conam.conambl.vo.verbale.MinVerbaleVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.RiepilogoAllegatoVO;

@Component
public class MinVerbaleEntityMapperImpl implements MinVerbaleEntityMapper {

	@Autowired
	private StatoManualeEntityMapper statoManualeEntityMapper;
	@Autowired
	private StatoVerbaleEntityMapper statoEntityMapper;
	@Autowired
	private EnteEntityMapper enteEntityMapper;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private IstruttoreEntityMapper istruttoreEntityMapper;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;
//	@Autowired
//	private CnmREnteNormaRepository cnmREnteNormaRepository;
	@Autowired
	private CnmRFunzionarioIstruttoreRepository cnmRFunzionarioIstruttoreRepository;

	@Autowired
	private AllegatoVerbaleService allegatoVerbaleService;

	@Override
	public MinVerbaleVO mapEntityToVO(CnmTVerbale dto, Long idUser) {
		if (dto == null)
			return null;
		MinVerbaleVO verbale = new MinVerbaleVO();

		// E25_2022 - OBI28
        if (!dto.getCnmRVerbaleSoggettos().isEmpty()) {
            StringBuilder ob = new StringBuilder();
            StringBuilder tr = new StringBuilder();

            for (CnmRVerbaleSoggetto r : dto.getCnmRVerbaleSoggettos()) {
                CnmTSoggetto s = r.getCnmTSoggetto();
                String soggettoInfos = "";
                if (StringUtils.isNotBlank(s.getRagioneSociale())) {
                	soggettoInfos = s.getRagioneSociale();
                }
                else {
                	soggettoInfos = StringUtils.isNotBlank(s.getNome()) ? s.getNome() + " " + s.getCognome() : s.getCognome();                	
                }
                
                if (r.getCnmDRuoloSoggetto().getIdRuoloSoggetto() == Constants.VERBALE_SOGGETTO_RUOLO_TRASGRESSORE_ID) {
                    tr.append(StringUtils.isNotBlank(tr) ?"\n":"");
	                tr.append(soggettoInfos);
	            }
                else {
                	ob.append(StringUtils.isNotBlank(ob) ?"\n":"");
	                ob.append(soggettoInfos);
                }
            }

            verbale.setTrasgressori(tr.length() > 0 ? tr.toString():null);
            verbale.setObbligati(ob.length() > 0 ? ob.toString():null);
        }
        
		verbale.setEnteAccertatore(enteEntityMapper.mapEntityToVO(dto.getCnmDEnte()));
		verbale.setId(dto.getIdVerbale().longValue());
		verbale.setNumero(dto.getNumVerbale());
		StatoVerbaleVO stato = statoEntityMapper.mapEntityToVO(dto.getCnmDStatoVerbale());
		verbale.setStato(stato);
		verbale.setId(dto.getIdVerbale().longValue());
		verbale.setNumeroProtocollo(dto.getNumeroProtocollo());
		verbale.setDataCaricamento(utilsDate.asLocalDateTime(dto.getDataOraInsert()));
		verbale.setUser(dto.getCnmTUser2().getNome() + " " + dto.getCnmTUser2().getCognome());
		// E23_2022 - OBI31
		if (dto.getCnmDComuneEnte() !=null)
			verbale.setComuneEnteAccertatore(dto.getCnmDComuneEnte().getDenomComune());
		
		// E1_2022 - OB32
		CnmRFunzionarioIstruttore funz = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(dto);
		if (funz !=null && funz.getCnmTUser() !=null) {
		    verbale.setAssegnatario(istruttoreEntityMapper.mapEntityToVO(funz.getCnmTUser()));
		}
	
		verbale.setModificabile(Boolean.FALSE);
		if ((stato.getId() == Constants.STATO_VERBALE_INCOMPLETO) && dto.getCnmTUser2().getIdUser() == idUser)
			verbale.setModificabile(Boolean.TRUE);

		verbale.setStatoManuale(statoManualeEntityMapper.mapEntityToVO(dto.getCnmDStatoManuale()));
		
		verbale.setDataOraAccertamento(utilsDate.asLocalDateTime(dto.getDataOraAccertamento()));
		
		// E1_2022 - OBI32 
		verbale.setDataProcesso(dto.getDataOraViolazione()!=null ? utilsDate.asLocalDateTime(dto.getDataOraViolazione()):null); 
		
		if (dto.getDataOraAccertamento() != null)
			verbale.setAnnoAccertamento(utilsDate.getYear(dto.getDataOraAccertamento()));
	

		// E24_2022 - OBI30 + OBI32
		// start
		List<CnmRVerbaleIllecito> cnmRVerbaleIllecitoList= cnmRVerbaleIllecitoRepository.findByCnmTVerbale(dto);		
		
        if (!cnmRVerbaleIllecitoList.isEmpty()) {
            StringBuilder ambiti = new StringBuilder();
            StringBuilder leggi = new StringBuilder();
			StringBuilder normaSb = new StringBuilder();

            for (CnmRVerbaleIllecito verbaleItem : cnmRVerbaleIllecitoList) {
        	   CnmDLettera lettera = verbaleItem.getCnmDLettera();
        	   
        	   String ambito = getAcronimoAmbito(lettera); 
        	   // OB32
        	   String rifNormativo = lettera.getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDEnte().getDescEnte();//.getCnmDNorma().getRiferimentoNormativo();

        	   String leggeViolata = lettera.getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDNorma().getRiferimentoNormativo();//lettera.getLettera();
        	   
        	   if(StringUtils.isNotBlank(ambito)) {
        		   ambiti.append(StringUtils.isNotBlank(ambiti) ?"\n":"");
        		   ambiti.append(ambito); 
        	   }
         	   if(StringUtils.isNotBlank(leggeViolata)) {
         		  leggi.append(StringUtils.isNotBlank(leggi) ?"\n":"");
         		  leggi.append(leggeViolata); 
        	   }
         	   if(StringUtils.isNotBlank(rifNormativo)) {
         		  normaSb.append(StringUtils.isNotBlank(normaSb) ?"\n":"");
         		  normaSb.append(rifNormativo); 
         	   }
        	  
           }
           verbale.setEnteRiferimentiNormativi(StringUtils.isNotBlank(normaSb)?normaSb.toString():null);
           verbale.setAmbiti(ambiti.length() > 0 ? ambiti.toString():null);
           verbale.setLeggeViolata(StringUtils.isNotBlank(leggi)?leggi.toString():null);
        }
        // end
        		
		return verbale;
	}

	private String getAcronimoAmbito(CnmDLettera cnmDLettera) {
		// TODO Auto-generated method stub
		
		CnmDComma cndComma = cnmDLettera.getCnmDComma();
		if(cndComma==null) return null;
		
		CnmDArticolo cnmDArticolo = cndComma.getCnmDArticolo();
		if(cnmDArticolo==null) return null;
		
		CnmREnteNorma cnmREnteNorma = cnmDArticolo.getCnmREnteNorma();
		if(cnmREnteNorma==null) return null;
		
		CnmDNorma cnmDNorma = cnmREnteNorma.getCnmDNorma();
		if(cnmDNorma==null) return null;
		
		CnmDAmbito cnmDAmbito = cnmDNorma.getCnmDAmbito();
		if(cnmDAmbito==null) return null;
		
		
		return cnmDAmbito.getAcronimo();
	}

	@Override
	public MinVerbaleVO mapEntityToVOAndDoc(CnmTVerbale dto, Long idUser) {
		MinVerbaleVO verbale = mapEntityToVO(dto, idUser);
		// OBI39
		RiepilogoAllegatoVO allegatoList = allegatoVerbaleService.getAllegatiByIdVerbale(verbale.getId().intValue(), null, false);
		if(allegatoList != null){
			StringBuilder doc = new StringBuilder();
			for(AllegatoVO allegato : allegatoList.getVerbale()){
				doc.append(StringUtils.isNotBlank(doc) ?"\n":"");
	            doc.append(allegato.getTipo().getDenominazione() + 
					(StringUtils.isNotBlank(allegato.getNumProtocollo())? (" - "+ allegato.getNumProtocollo()):"" ));
			}
			for(AllegatoVO allegato : allegatoList.getIstruttoria()){
				doc.append(StringUtils.isNotBlank(doc) ?"\n":"");
	            doc.append(allegato.getTipo().getDenominazione() + 
					(StringUtils.isNotBlank(allegato.getNumProtocollo())? (" - "+ allegato.getNumProtocollo()):"" ));
			}
			for(AllegatoVO allegato : allegatoList.getGiurisdizionale()){
				doc.append(StringUtils.isNotBlank(doc) ?"\n":"");
	            doc.append(allegato.getTipo().getDenominazione() + 
					(StringUtils.isNotBlank(allegato.getNumProtocollo())? (" - "+ allegato.getNumProtocollo()):"" ));
			}
			for(AllegatoVO allegato : allegatoList.getRateizzazione()){
				doc.append(StringUtils.isNotBlank(doc) ?"\n":"");
	            doc.append(allegato.getTipo().getDenominazione() + 
					(StringUtils.isNotBlank(allegato.getNumProtocollo())? (" - "+ allegato.getNumProtocollo()):"" ));
			}
			verbale.setDocumenti(StringUtils.isNotBlank(doc)?doc.toString():null);
		}
		return verbale;

	}

}
