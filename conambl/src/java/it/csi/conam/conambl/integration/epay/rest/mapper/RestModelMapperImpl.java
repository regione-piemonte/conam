/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.epay.rest.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;

import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmCParametro;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmRSoggRata;
import it.csi.conam.conambl.integration.entity.CnmTNotifica;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTRata;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTSollecito;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.integration.epay.rest.model.DebtPositionData;
import it.csi.conam.conambl.integration.epay.rest.model.PaymentComponent;
import it.csi.conam.conambl.integration.epay.rest.util.RestUtils;
import it.csi.conam.conambl.integration.epay.to.PersonaFisicaType;
import it.csi.conam.conambl.integration.epay.to.PersonaGiuridicaType;
import it.csi.conam.conambl.integration.epay.to.SoggettoType;
import it.csi.conam.conambl.integration.mapper.entity.SoggettoEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmCParametroRepository;
import it.csi.conam.conambl.util.UtilsParametro;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;

/**
 * @author riccardo.bova
 * @date 31 gen 2019
 */

@Component
public class RestModelMapperImpl implements RestModelMapper {

	@Autowired
	private RestUtils restUtils;
	@Autowired
	private SoggettoEntityMapper soggettoEntityMapper;
	@Autowired
	private CnmCParametroRepository cnmCParametroRepository;
	@Autowired
	private CommonSoggettoService commonSoggettoService;

	
	private static final List<Long> PARAMETRI = Arrays.asList(Constants.ID_CODICE_VERSAMENTO_PIEMONTE_PAY, //
			Constants.ID_CODICE_FISCALE_ENTE_CREDITORE, //
			Constants.ID_OGGETTO_PAGAMENTO_RATEIZZAZIONE, //
			Constants.ID_OGGETTO_PAGAMENTO_ORDINANZA, //
			Constants.ID_OGGETTO_PAGAMENTO_SOLLECITO, //
			Constants.ID_OGGETTO_PAGAMENTO_SOLLECITO_RATE);

	@Override
	public DebtPositionData mapRateSoggettoToDebtPositionData(CnmRSoggRata cnmRSoggRata) {
		DebtPositionData debtPositionData = new DebtPositionData();
		
//			causale
		String descrizioneCausaleVersamento = null;
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI);
		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_RATEIZZAZIONE)).orNull();
		if (cnmCParametro != null)
			descrizioneCausaleVersamento = cnmCParametro.getValoreString();
		debtPositionData.setCausale(descrizioneCausaleVersamento);
		
		
	//	dataFineValidita
		Date dataFineValidita = cnmRSoggRata.getCnmTRata().getDataFineValidita();
		if (dataFineValidita != null) {
			debtPositionData.setDataFineValidita(restUtils.formatRestParamDate(dataFineValidita));
		}
		
//			dataInizioValidita --> non obbligatoria, la omettiamo non essendo presente in cnmRSoggRata.getCnmTRata()?
		OffsetDateTime offsetDateTimeUTC = OffsetDateTime.now(ZoneOffset.UTC);
		debtPositionData.setDataInizioValidita(offsetDateTimeUTC.toString());
		
		
//			dataScadenza
		CnmTRata cnmTRata = cnmRSoggRata.getCnmTRata();
		Date dataScadenza = cnmTRata.getDataScadenza();
		if (dataScadenza != null) {
			debtPositionData.setDataScadenza(restUtils.formatRestParamDate(dataScadenza));
		}
	
//			email		
		CnmTSoggetto cnmTSoggetto = cnmRSoggRata.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto();
		SoggettoVO soggettoVoToMap = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
		SoggettoType soggettoPagatore  = mapSoggettoVOtoSoggettoType(soggettoVoToMap);
		if (soggettoPagatore != null) {
			debtPositionData.setEmail(soggettoPagatore.getEMail());	
//			nome e cognome --> come lo gestiamo  l'anonimato  (vedi NOTe del pdf)
//			codiceFiscalePartitaIVAPagatore
//			ragioneSociale
			if (soggettoPagatore.getPersonaFisica()!=null) {
				debtPositionData.setCognome(soggettoPagatore.getPersonaFisica().getCognome());
				debtPositionData.setNome(soggettoPagatore.getPersonaFisica().getNome());
				debtPositionData.setCodiceFiscalePartitaIVAPagatore(soggettoVoToMap.getCodiceFiscale());
			}else {
				debtPositionData.setCognome("ANONIMO");
				debtPositionData.setCognome("ANONIMO");
				debtPositionData.setCodiceFiscalePartitaIVAPagatore(soggettoVoToMap.getPartitaIva());
				debtPositionData.setRagioneSociale(soggettoVoToMap.getRagioneSociale());
				
				
			}
		}
		
//			importo
		debtPositionData.setImporto(cnmTRata.getImportoRata().setScale(2, RoundingMode.HALF_UP));
		
//			identificativoPagamento				
		debtPositionData.setIdentificativoPagamento(cnmRSoggRata.getCodPosizioneDebitoria());

		
		CnmTOrdinanza cnmTOrdinanza = cnmRSoggRata.getCnmROrdinanzaVerbSog().getCnmTOrdinanza();
		CnmTVerbale cnmTVerbale = cnmRSoggRata.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTVerbale();
		
	//  Payment Component
		List<PaymentComponent> paymentsComponent = restUtils.buildPaymentComponent(descrizioneCausaleVersamento, debtPositionData.getImporto(), cnmTOrdinanza.getNumeroAccertamento(),cnmTOrdinanza.getAnnoAccertamento(), cnmTVerbale.getDataOraAccertamento());
		debtPositionData.setComponentiPagamento(paymentsComponent);
			
		
//		PARAMETRI FACOLTATIVI non trovati decidere se vanno inseriti e come	
//		notePerIlPagatore
//		requiresCostUpdate

		return debtPositionData;
	}
	
	
	@Override
	public DebtPositionData mapOrdinanzaToDebtPositionData(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
		DebtPositionData debtPositionData = new DebtPositionData();
		

	//		PARAMETRI FACOLTATIVI non trovati decidere se vanno inseriti e come	
	//		componentiPagamento
	//		notePerIlPagatore
	//		requiresCostUpdate
			
	//		PARAMETRI OBBLIGATORI da identificare
	//		identificativoPagamento	
	
	//		causale
		String descrizioneCausaleVersamento = null;
		List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI);
		CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_ORDINANZA)).orNull();
		if (cnmCParametro != null)
			descrizioneCausaleVersamento = cnmCParametro.getValoreString();
		debtPositionData.setCausale(descrizioneCausaleVersamento);
		
		
	//	dataFineValidita
		CnmTOrdinanza cnmTOrdinanza = cnmROrdinanzaVerbSog.getCnmTOrdinanza();
		Date dataFineValidita = cnmTOrdinanza.getDataFineValidita();
		if (dataFineValidita != null) {
			debtPositionData.setDataFineValidita(restUtils.formatRestParamDate(dataFineValidita));
		}
		
	//	dataInizioValidita --> non obbligatoria, la omettiamo non essendo presente in cnmRSoggRata.getCnmTRata()?
		OffsetDateTime offsetDateTimeUTC = OffsetDateTime.now(ZoneOffset.UTC);
		debtPositionData.setDataInizioValidita(offsetDateTimeUTC.toString());
		
		
		Date dataScadenza = cnmTOrdinanza.getDataScadenzaOrdinanza();
		if (dataScadenza != null) {
			debtPositionData.setDataScadenza(restUtils.formatRestParamDate(dataScadenza));
		}
		
	//		email			
		CnmTSoggetto cnmTSoggetto = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTSoggetto();
		SoggettoVO soggettoVoToMap = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);
		SoggettoType soggettoPagatore  = mapSoggettoVOtoSoggettoType(soggettoVoToMap);
		CnmTVerbale cnmTVerbale = cnmROrdinanzaVerbSog.getCnmRVerbaleSoggetto().getCnmTVerbale();			
		if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
			soggettoVoToMap = commonSoggettoService.attachResidenzaPregressi(soggettoVoToMap, cnmTSoggetto, cnmTVerbale.getIdVerbale());
		}						
		soggettoPagatore = mapSoggettoVOtoSoggettoType(soggettoVoToMap);
		
		if (soggettoPagatore != null) {
			debtPositionData.setEmail(soggettoPagatore.getEMail());	
	//		nome e cognome --> come lo gestiamo  l'anonimato  (vedi NOTe del pdf)
	//		codiceFiscalePartitaIVAPagatore
	//		ragioneSociale
			if (soggettoPagatore.getPersonaFisica()!=null) {
				debtPositionData.setCognome(soggettoPagatore.getPersonaFisica().getCognome());
				debtPositionData.setNome(soggettoPagatore.getPersonaFisica().getNome());
				debtPositionData.setCodiceFiscalePartitaIVAPagatore(soggettoVoToMap.getCodiceFiscale());
			}else {
				debtPositionData.setCognome("ANONIMO");
				debtPositionData.setNome("ANONIMO");
				debtPositionData.setCodiceFiscalePartitaIVAPagatore(soggettoVoToMap.getPartitaIva());
				debtPositionData.setRagioneSociale(soggettoVoToMap.getRagioneSociale());				
			}
		}
		
	//		importo
		BigDecimal importNotifica = BigDecimal.ZERO;
		if(cnmTOrdinanza.getCnmTNotificas()!=null && !cnmTOrdinanza.getCnmTNotificas().isEmpty()) {
			Iterator<CnmTNotifica> iterNotifica = cnmTOrdinanza.getCnmTNotificas().iterator();
			while(iterNotifica.hasNext()) {
				CnmTNotifica notificaItem = iterNotifica.next();
				// 20230519 PP - CR abb 167 (issue 5)
				// Importo spese notifica diventa opzionale
				if(notificaItem.getImportoSpeseNotifica()!=null) {
					importNotifica = importNotifica.add(notificaItem.getImportoSpeseNotifica());
				}
			}			
		}
		
		BigDecimal imp = cnmTOrdinanza.getImportoOrdinanza().setScale(2, RoundingMode.HALF_UP).add(importNotifica);
		debtPositionData.setImporto(imp);
		
	//  Payment Component
		List<PaymentComponent> paymentsComponent = restUtils.buildPaymentComponent(
				descrizioneCausaleVersamento, imp, cnmTOrdinanza.getNumeroAccertamento(),cnmTOrdinanza.getAnnoAccertamento(), cnmTVerbale.getDataOraAccertamento()
		);
		debtPositionData.setComponentiPagamento(paymentsComponent);
				
		
	//		identificativoPagamento				
		debtPositionData.setIdentificativoPagamento(cnmROrdinanzaVerbSog.getCodPosizioneDebitoria());
	
		
		return debtPositionData;
		
	}

	@Override
	public DebtPositionData mapRataSollecitoToDebtPositionData(CnmTSollecito cnmTSollecito) {
		DebtPositionData debtPositionData = new DebtPositionData();
		

		//		PARAMETRI FACOLTATIVI non trovatidecidere se vanno inseriti e come	
		//		componentiPagamento
		//		notePerIlPagatore
		//		requiresCostUpdate
				
		//		PARAMETRI OBBLIGATORI da identificare
		//		identificativoPagamento	
				
		
		//		causale
			String descrizioneCausaleVersamento = null;
			List<CnmCParametro> cnmCParametroList = cnmCParametroRepository.findByIdParametroIn(PARAMETRI);
			CnmCParametro cnmCParametro = Iterables.tryFind(cnmCParametroList, UtilsParametro.findByIdParametro(Constants.ID_OGGETTO_PAGAMENTO_SOLLECITO)).orNull();
			if (cnmCParametro != null)
				descrizioneCausaleVersamento = cnmCParametro.getValoreString();
			debtPositionData.setCausale(descrizioneCausaleVersamento);
			
			
//			dataFineValidita
			Date dataFineValidita = cnmTSollecito.getDataFineValidita();;
			if (dataFineValidita != null) {
				debtPositionData.setDataFineValidita(restUtils.formatRestParamDate(dataFineValidita));
			}
			
		//	dataInizioValidita --> non obbligatoria, la omettiamo non essendo presente in cnmRSoggRata.getCnmTRata()?
			OffsetDateTime offsetDateTimeUTC = OffsetDateTime.now(ZoneOffset.UTC);
			debtPositionData.setDataInizioValidita(offsetDateTimeUTC.toString());

			
			Date dataScadenza = cnmTSollecito.getDataScadenzaRata();
			if (dataScadenza != null) {
				debtPositionData.setDataScadenza(restUtils.formatRestParamDate(dataScadenza));
			}
			
			
		//		email			
			CnmTSoggetto cnmTSoggetto = cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTSoggetto();
			SoggettoVO soggettoVoToMap = soggettoEntityMapper.mapEntityToVO(cnmTSoggetto);			
			CnmTVerbale cnmTVerbale = cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmRVerbaleSoggetto().getCnmTVerbale();		
			if(cnmTVerbale.getCnmDStatoPregresso() != null && cnmTVerbale.getCnmDStatoPregresso().getIdStatoPregresso() != 1) {
				soggettoVoToMap = commonSoggettoService.attachResidenzaPregressi(soggettoVoToMap, cnmTSoggetto, cnmTVerbale.getIdVerbale());
			}			

			SoggettoType soggettoPagatore  = mapSoggettoVOtoSoggettoType(soggettoVoToMap);
			
			if (soggettoPagatore != null) {
				debtPositionData.setEmail(soggettoPagatore.getEMail());	
		//		nome e cognome --> come lo gestiamo  l'anonimato  (vedi NOTe del pdf)
		//		codiceFiscalePartitaIVAPagatore
		//		ragioneSociale
				if (soggettoPagatore.getPersonaFisica()!=null) {
					debtPositionData.setCognome(soggettoPagatore.getPersonaFisica().getCognome());
					debtPositionData.setNome(soggettoPagatore.getPersonaFisica().getNome());
					debtPositionData.setCodiceFiscalePartitaIVAPagatore(soggettoVoToMap.getCodiceFiscale());
				}else {
					debtPositionData.setCognome("ANONIMO");
					debtPositionData.setCognome("ANONIMO");
					debtPositionData.setCodiceFiscalePartitaIVAPagatore(soggettoVoToMap.getPartitaIva());
					debtPositionData.setRagioneSociale(soggettoVoToMap.getRagioneSociale());				
				}
			}
			
		//		importo
			BigDecimal importoSollecito = cnmTSollecito.getImportoSollecito() != null ? cnmTSollecito.getImportoSollecito() : BigDecimal.ZERO;
			BigDecimal importoMaggiorazione = cnmTSollecito.getMaggiorazione() != null ? cnmTSollecito.getMaggiorazione() : BigDecimal.ZERO;
			BigDecimal importoTotale = importoSollecito.add(importoMaggiorazione);
			debtPositionData.setImporto(importoTotale.setScale(2, RoundingMode.HALF_UP));
			
			
		//		identificativoPagamento				
			debtPositionData.setIdentificativoPagamento(cnmTSollecito.getCodPosizioneDebitoria());
		
			CnmTOrdinanza cnmTOrdinanza = cnmTSollecito.getCnmROrdinanzaVerbSog().getCnmTOrdinanza();
			
		//  Payment Component
			List<PaymentComponent> paymentsComponent = restUtils.buildPaymentComponent(descrizioneCausaleVersamento, debtPositionData.getImporto(), cnmTOrdinanza.getNumeroAccertamento(),cnmTOrdinanza.getAnnoAccertamento(), cnmTVerbale.getDataOraAccertamento());
			debtPositionData.setComponentiPagamento(paymentsComponent);
				
			
			return debtPositionData;
	}
	
	

	private SoggettoType mapSoggettoVOtoSoggettoType(SoggettoVO soggetto) {
		SoggettoType soggettoPagatore = new SoggettoType();

		// soggettoPagatore.setCAP(); non lo abbiamo sul nostro DB
		soggettoPagatore.setCivico(soggetto.getCivicoResidenza());
		soggettoPagatore.setIndirizzo(soggetto.getIndirizzoResidenza());
		soggettoPagatore.setLocalita(soggetto.getComuneResidenza() != null ? soggetto.getComuneResidenza().getDenominazione() : null);
		soggettoPagatore.setNazione(soggetto.getNazioneNascita() != null ? soggetto.getNazioneNascita().getDenominazione().substring(0, 2) : null);
		soggettoPagatore.setProvincia(soggetto.getProvinciaResidenza() != null ? soggetto.getProvinciaResidenza().getDenominazione() : null);
		soggettoPagatore.setIdentificativoUnivocoFiscale(StringUtils.defaultString(soggetto.getCodiceFiscale(), soggetto.getPartitaIva()));
		if (soggetto.getPersonaFisica()) {
			PersonaFisicaType personaFisica = new PersonaFisicaType();
			personaFisica.setCognome(soggetto.getCognome());
			personaFisica.setNome(soggetto.getNome());
			soggettoPagatore.setPersonaFisica(personaFisica);
		} else {
			PersonaGiuridicaType personaGiuridica = new PersonaGiuridicaType();
			personaGiuridica.setRagioneSociale(StringUtils.abbreviate(soggetto.getRagioneSociale(), 70));
			soggettoPagatore.setPersonaGiuridica(personaGiuridica);
		}

		return soggettoPagatore;
	}


}
