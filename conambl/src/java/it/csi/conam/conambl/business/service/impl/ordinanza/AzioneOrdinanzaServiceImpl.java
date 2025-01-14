/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import bsh.StringUtil;
import it.csi.conam.conambl.business.service.notifica.NotificaService;
import it.csi.conam.conambl.business.service.ordinanza.AllegatoOrdinanzaService;
import it.csi.conam.conambl.business.service.ordinanza.AzioneOrdinanzaService;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.repositories.CnmROrdinanzaVerbSogRepository;
import it.csi.conam.conambl.integration.repositories.CnmTAllegatoRepository;
import it.csi.conam.conambl.integration.repositories.CnmTOrdinanzaRepository;
import it.csi.conam.conambl.request.ordinanza.AzioneOrdinanzaRequest;
import it.csi.conam.conambl.response.AzioneOrdinanzaResponse;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class AzioneOrdinanzaServiceImpl implements AzioneOrdinanzaService {

	@Autowired
	private AllegatoOrdinanzaService allegatoOrdinanzaService;
	
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;

	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;

	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;

	@Autowired
	private NotificaService ordinanzaNotificaService;

	@Override
	public AzioneOrdinanzaResponse azioneOrdinanza(AzioneOrdinanzaRequest azioniOrdinanzaRequest, UserDetails userDetails) {
		if (azioniOrdinanzaRequest == null) {
			throw new IllegalArgumentException("azioniOrdinanzaRequest ==null");
		}
		Integer idOrdinanza = azioniOrdinanzaRequest.getId();
		String tipoDocumento = azioniOrdinanzaRequest.getTipoDocumento();
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza ==null");

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		if (cnmTOrdinanza == null)
			throw new IllegalArgumentException("cnmTOrdinanza ==null");

		Boolean isAllegatoEnable = Boolean.FALSE;
		AzioneOrdinanzaResponse azioneOrdinanzaResponse = new AzioneOrdinanzaResponse();
		azioneOrdinanzaResponse.setSoloLettera(false);
		List<TipoAllegatoVO> listTipoAllegatiAllegabili = allegatoOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanza(idOrdinanza, tipoDocumento, false);
		if (listTipoAllegatiAllegabili != null && !listTipoAllegatiAllegabili.isEmpty())
			isAllegatoEnable = Boolean.TRUE;

//		Date dataScadenza = cnmTOrdinanza.getDataScadenzaOrdinanza();

//		20201117_ET per le ordinanze pregresse non e' possibile generare i bollettini e lettere
		boolean isOrdinanzaPregressa = cnmTOrdinanza.isFlagDocumentoPregresso();
		long idTipoOrdinanza = cnmTOrdinanza.getCnmDTipoOrdinanza().getIdTipoOrdinanza();
		boolean isRichiestaBollettiniInviata = allegatoOrdinanzaService.isRichiestaBollettiniInviata(cnmTOrdinanza);
		Boolean isLetteraOrdinanzaCreata = allegatoOrdinanzaService.isLetteraOrdinanzaCreata(cnmTOrdinanza);

		// 20210308_LC bollettini (invio e download) solo per ordinanza ingiunzione ed ordinanza annullamento - ingiunzione
		//E10_2024 rimossa l'obbligatorietà della data scadenza per il download dei dei bollettini: "dataScadenza"
		//Boolean dowloadBollettiniEnable = isRichiestaBollettiniInviata && isLetteraOrdinanzaCreata && dataScadenza != null && idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_ARCHIVIATO && idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE;		
		//sostituito con:
		Boolean dowloadBollettiniEnable = isRichiestaBollettiniInviata && isLetteraOrdinanzaCreata && idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_ARCHIVIATO && idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE;		
		//E10_2024 rimossa l'obbligatorietà della data scadenza per l'invio dei bollettini: "dataScadenza"
		//Boolean bollettiniDaInviareEnable = !isOrdinanzaPregressa && !isRichiestaBollettiniInviata && isLetteraOrdinanzaCreata && dataScadenza != null && idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_ARCHIVIATO && idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE;
		//diventa la seguente
		Boolean bollettiniDaInviareEnable = !isOrdinanzaPregressa && !isRichiestaBollettiniInviata && isLetteraOrdinanzaCreata && idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_ARCHIVIATO && idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_ANNULLAMENTO_ARCHIVIAZIONE;
		
		Boolean isNotificaOrdinanzaCreata = ordinanzaNotificaService.isNotificaCreata(cnmTOrdinanza);
		Boolean downloadNotificaOrdinanza = isNotificaOrdinanzaCreata && isLetteraOrdinanzaCreata;

		azioneOrdinanzaResponse.setAggiungiAllegatoEnable(isAllegatoEnable);
		
		if(idTipoOrdinanza != Constants.ID_TIPO_ORDINANZA_INGIUNZIONE){
			azioneOrdinanzaResponse.setLetteraDaCompilareEnable(!isOrdinanzaPregressa && !isLetteraOrdinanzaCreata);			
			azioneOrdinanzaResponse.setScaricaLetteraEnable(!azioneOrdinanzaResponse.getLetteraDaCompilareEnable());
		}else{
			boolean letteraFound = false;
			boolean bollettinoProtocollato = false;
			List<AllegatoVO> allegatiOrdinanza = allegatoOrdinanzaService.getAllegatiByIdOrdinanza(idOrdinanza);
			for(AllegatoVO allegato : allegatiOrdinanza){
				if(allegato.getTipo().getId().intValue() == TipoAllegato.LETTERA_ORDINANZA.getId()
				&& StringUtils.isEmpty(allegato.getNumProtocollo())){
					letteraFound = true;
				}
			}
			int numBollettini = 0;
			for(AllegatoVO allegato : allegatiOrdinanza){
				if(allegato.getTipo().getId().intValue() == TipoAllegato.BOLLETTINI_ORDINANZA_SOLLECITO.getId()){
					numBollettini ++;
					if(!StringUtils.isEmpty(allegato.getNumProtocollo())){
						bollettinoProtocollato = true;
					}else {
						bollettinoProtocollato = false;
						break;
					}
				}
			}
			if(numBollettini == 0) {
				bollettinoProtocollato = true;
			}

			// verifico se l'ultimo bollettino è stato creato dal vecchio (solo lettera) o è stato reinviato a ppay
			for(AllegatoVO allegato : allegatiOrdinanza){
				if(allegato.getTipo().getId().intValue() == TipoAllegato.BOLLETTINI_ORDINANZA_SOLLECITO.getId()){
					CnmTAllegato tAllegato = cnmTAllegatoRepository.findOne(allegato.getId());
					if(tAllegato.getCnmTUser2().getCodiceFiscale().equalsIgnoreCase(Constants.CFSYSTEM)){
						azioneOrdinanzaResponse.setSoloLettera(true);
					}else {
						azioneOrdinanzaResponse.setSoloLettera(false);
					}
				}
			}

			// OBI38
			// restituisce true, se non c'è ancora una lettere, oppure se c'è già una lettera, ma questa è protocollata
			azioneOrdinanzaResponse.setLetteraDaCompilareEnable(!letteraFound && bollettinoProtocollato);
			// restituire true se c'è una lettera ancora non protocollata e non è stata fatta ancora richiesta di bollettini
			//E10_2024 rimosso controllo su dataScadenza
			// questo azioneOrdinanzaResponse.setProtocollaLetteraSenzaBollettini(letteraFound && bollettinoProtocollato && numBollettini > 0 && !isRichiestaBollettiniInviata && dataScadenza != null);
			//è sostituito da:
			azioneOrdinanzaResponse.setProtocollaLetteraSenzaBollettini(letteraFound && bollettinoProtocollato && numBollettini > 0 && !isRichiestaBollettiniInviata);
			
			if(azioneOrdinanzaResponse.getProtocollaLetteraSenzaBollettini()) {
				dowloadBollettiniEnable = false;
				bollettiniDaInviareEnable = true;
			}
			//bollettiniDaInviareEnable = !isOrdinanzaPregressa && !azioneOrdinanzaResponse.getLetteraDaCompilareEnable() && isLetteraOrdinanzaCreata && dataScadenza != null;

			//dowloadBollettiniEnable = dowloadBollettiniEnable && bollettinoProtocollato && !bollettiniDaInviareEnable;						
			//azioneOrdinanzaResponse.setScaricaLetteraEnable(dowloadBollettiniEnable);
		}
		

		azioneOrdinanzaResponse.setDownloadBollettiniEnable(dowloadBollettiniEnable);
		azioneOrdinanzaResponse.setBollettiniDaInviareEnable(bollettiniDaInviareEnable);

		azioneOrdinanzaResponse.setAggiungiNotificaEnable(!isNotificaOrdinanzaCreata && isLetteraOrdinanzaCreata);
		azioneOrdinanzaResponse.setVisualizzaNotificaEnable(downloadNotificaOrdinanza);

		return azioneOrdinanzaResponse;
	}

	@Override
	public AzioneOrdinanzaResponse azioneOrdinanzaSoggetto(AzioneOrdinanzaRequest azioniOrdinanzaRequest, UserDetails userDetails) {
		if (azioniOrdinanzaRequest == null) {
			throw new IllegalArgumentException("azioniOrdinanzaRequest ==null");
		}
		Integer idOrdinanza = azioniOrdinanzaRequest.getId();
		String tipoDocumento = azioniOrdinanzaRequest.getTipoDocumento();
		if (idOrdinanza == null)
			throw new IllegalArgumentException("idOrdinanza ==null");

		AzioneOrdinanzaResponse azioneOrdinanzaResponse = new AzioneOrdinanzaResponse();
		azioneOrdinanzaResponse.setAggiungiAllegatoEnable(Boolean.FALSE);
		List<TipoAllegatoVO> listTipoAllegatiAllegabili;

		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(idOrdinanza);
		List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogList = cnmROrdinanzaVerbSogRepository.findByCnmTOrdinanza(cnmTOrdinanza);
		Collection<Integer> idcol = Collections2.transform(cnmROrdinanzaVerbSogList, new Function<CnmROrdinanzaVerbSog, Integer>() {
			@Override
			public Integer apply(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog) {
				return cnmROrdinanzaVerbSog.getIdOrdinanzaVerbSog();
			}
		});
		for (Integer i : idcol) {
			listTipoAllegatiAllegabili = allegatoOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanzaSoggetto( Collections.singletonList(i), tipoDocumento, false);
			if (listTipoAllegatiAllegabili != null && !listTipoAllegatiAllegabili.isEmpty()) {
				azioneOrdinanzaResponse.setAggiungiAllegatoEnable(Boolean.TRUE);
				return azioneOrdinanzaResponse;
			}
		}

		return azioneOrdinanzaResponse;
	}

}
