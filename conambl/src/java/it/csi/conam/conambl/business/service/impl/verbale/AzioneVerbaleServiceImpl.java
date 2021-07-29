/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.verbale.*;
import it.csi.conam.conambl.common.AzioneVerbale;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.RegoleAllegatiCambiamentoStato;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.beans.ResponseProtocollaDocumento;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.IstruttoreEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.request.verbale.SalvaAzioneRequest;
import it.csi.conam.conambl.response.AzioneVerbaleResponse;
import it.csi.conam.conambl.security.AppGrantedAuthority;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.UtilsTipoAllegato;
import it.csi.conam.conambl.vo.IstruttoreVO;
import it.csi.conam.conambl.vo.UtenteVO;
import it.csi.conam.conambl.vo.verbale.SoggettoVO;
import it.csi.conam.conambl.vo.verbale.allegato.TipoAllegatoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class AzioneVerbaleServiceImpl implements AzioneVerbaleService {

	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;

	@Autowired
	private CnmRUserEnteRepository cnmRUserEnteRepository;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	protected AllegatoVerbaleService allegatoVerbaleService;
	@Autowired
	private IstruttoreEntityMapper istruttoreEntityMapper;
	@Autowired
	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;
	@Autowired
	private StoricizzazioneVerbaleService storicizzazioneVerbaleService;
	@Autowired
	protected SoggettoVerbaleService soggettoVerbaleService;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmRVerbaleIllecitoRepository cnmRVerbaleIllecitoRepository;
	@Autowired
	private CnmRFunzionarioIstruttoreRepository cnmRFunzionarioIstruttoreRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private CnmRAllegatoVerbSogRepository cnmRAllegatoVerbSogRepository;
	@Autowired
	private CnmRAllegatoVerbaleRepository cnmRAllegatoVerbaleRepository;
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	@Autowired
	private CnmROrdinanzaVerbSogRepository cnmROrdinanzaVerbSogRepository;

	@Autowired
	protected UtilsVerbale utilsVerbale;
	
	
	@Override
	public List<IstruttoreVO> getIstruttoreByVerbale(Integer idVerbale, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);
		List<CnmRVerbaleIllecito> rel = cnmRVerbaleIllecitoRepository.findByCnmTVerbale(cnmTVerbale);
		if (rel.isEmpty())
			throw new SecurityException("errore non è possibile trovarsi in questa situazione");

		CnmDEnte cnmDEnte = rel.get(0).getCnmDLettera().getCnmDComma().getCnmDArticolo().getCnmREnteNorma().getCnmDEnte();
		if (cnmDEnte == null)
			throw new SecurityException("ente non trovato");

		Long idEnte = cnmDEnte.getIdEnte();
		List<IstruttoreVO> istruttoreList = new ArrayList<>();
		if (!SecurityUtils.isEnteNormaValido(idEnte))
			throw new SecurityException("Ente non abilitato");

		List<CnmRUserEnte> cnmRUserEnteList = cnmRUserEnteRepository.findByCnmDEnteAndTipoUtilizzoAndFineValidita(cnmDEnte, Constants.ID_UTILIZZO_ENTE_LEGGE);
		if (cnmRUserEnteList == null || cnmRUserEnteList.isEmpty())
			throw new SecurityException(" nessun utente con ente leggi corrispondenti");
		for (CnmRUserEnte cnmRUserEnte : cnmRUserEnteList) {
			CnmTUser cnmTUser = cnmRUserEnte.getCnmTUser();
			if (cnmTUser.getCnmDRuolo().getIdRuolo() == Long.parseLong(Constants.RUOLO_UTENTE_ISTRUTTORE)) {
				istruttoreList.add(istruttoreEntityMapper.mapEntityToVO(cnmTUser));
			}
		}

		return istruttoreList;
	}

	@Override
	public UtenteVO getUtenteRuolo(Integer idVerbale, UserDetails userDetails) {
		if (idVerbale == null)
			throw new SecurityException("Request non valida");

		CnmTVerbale cnmTVerbale = cnmTVerbaleRepository.findOne(idVerbale);

		UtenteVO utente = new UtenteVO();

		UserDetails user = SecurityUtils.getUser();
		CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());

		utente.setIdRuolo(cnmTUser.getCnmDRuolo().getIdRuolo());
		utente.setDescRuolo(cnmTUser.getCnmDRuolo().getDescRuolo());
		utente.setCodiceFiscale(cnmTUser.getCodiceFiscale());
		utente.setNome(cnmTUser.getNome());
		utente.setCognome(cnmTUser.getCognome());
		utente.setId(cnmTUser.getIdUser());

		if (cnmTUser.getCnmDRuolo().getIdRuolo() == Long.parseLong(Constants.RUOLO_UTENTE_ISTRUTTORE) && cnmTUser.getIdUser() == cnmTVerbale.getCnmTUser2().getIdUser())
			utente.setIsIstruttore(1);
		else
			utente.setIsIstruttore(0);

		return utente;
	}

	@Override
	public AzioneVerbaleResponse getAzioniVerbale(Integer id, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);
		AzioneVerbaleResponse azioneVerbale = new AzioneVerbaleResponse();

		Boolean isAllegatiEnable = Boolean.FALSE;
		AppGrantedAuthority appGrantedAuthority = SecurityUtils.getRuolo(userDetails.getAuthorities());
		Long idStatoVerbale = cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale();

		// aggiungi allegati enable
		List<TipoAllegatoVO> tipologiaAllegabili = allegatoVerbaleService.getTipologiaAllegatiAllegabiliVerbale(id, null, false);

		if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ISTRUTTORE)) {
			if (idStatoVerbale != Constants.STATO_VERBALE_INCOMPLETO) {
				CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
				if (cnmRFunzionarioIstruttore != null && cnmRFunzionarioIstruttore.getCnmTUser().getIdUser() == userDetails.getIdUser() && tipologiaAllegabili != null
						&& !tipologiaAllegabili.isEmpty())
					isAllegatiEnable = Boolean.TRUE;
			} else {
				if (tipologiaAllegabili != null && !tipologiaAllegabili.isEmpty() && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
					isAllegatiEnable = Boolean.TRUE;
			}
		} else if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ACCERTATORE) || appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_AMMINISTRATIVO)) {
			if (idStatoVerbale == Constants.STATO_VERBALE_INCOMPLETO && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
				isAllegatiEnable = Boolean.TRUE;
		} else
			throw new SecurityException("Ruolo non riconosciuto dal sistema");

		Boolean modificaVerbaleEnable = cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser()
				? Boolean.TRUE
				: Boolean.FALSE;
		Boolean isEliminaAllegatoEnable = cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale() == Constants.STATO_VERBALE_INCOMPLETO
				&& cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser() ? Boolean.TRUE : Boolean.FALSE;

		azioneVerbale.setAzione(AzioneVerbale.getAzioneVerbaleByIdStato(getStatoAvanzabileByCnmTVerbale(cnmTVerbale, userDetails)));
		azioneVerbale.setModificaVerbaleEnable(modificaVerbaleEnable);
		azioneVerbale.setEliminaAllegatoEnable(isEliminaAllegatoEnable);
		azioneVerbale.setAggiungiAllegatoEnable(isAllegatiEnable);
		return azioneVerbale;
	}

	@Override
	@Transactional
	public void salvaAzioneVerbale(SalvaAzioneRequest salvaAzione, UserDetails userDetails) {
		Long idAzione = salvaAzione.getIdAzione();
		Integer idVerbale = salvaAzione.getId();
		Integer idFunzionario = salvaAzione.getIdFunzionario();
		if (idAzione == null)
			throw new IllegalArgumentException("id azione = null");

		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(idVerbale);

		if (!isAzionePermessa(idVerbale, userDetails, idAzione))
			throw new SecurityException("Azione non permessa");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
		storicizzazioneVerbaleService.storicizzaStatoVerbale(cnmTVerbale, cnmTUser);

		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		CnmDStatoVerbale cnmDStatoVerbale;
		boolean assegnaEnable;
		if (AzioneVerbale.ACQUISISCI.getId().equals(idAzione)) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO);
			assegnaEnable = AzioneVerbale.ACQUISISCI.isListaIstruttori();
		} else if (AzioneVerbale.ACQUISISCI_CON_PAGAMENTO.getId().equals(idAzione)) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
			assegnaEnable = AzioneVerbale.ACQUISISCI_CON_PAGAMENTO.isListaIstruttori();
		} else if (AzioneVerbale.ACQUISISCI_CON_SCRITTI.getId().equals(idAzione)) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
			assegnaEnable = AzioneVerbale.ACQUISISCI_CON_SCRITTI.isListaIstruttori();
		} else if (AzioneVerbale.ARCHIVIATO_IN_AUTOTUTELA.getId().equals(idAzione)) {
			cnmDStatoVerbale = cnmDStatoVerbaleRepository.findOne(Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA);
			assegnaEnable = AzioneVerbale.ARCHIVIATO_IN_AUTOTUTELA.isListaIstruttori();
		} else
			throw new IllegalArgumentException("azione non gestita");

		// se la lista istruttori è presente controllo che sia valorizzato il
		// funzionario
		if (assegnaEnable && idFunzionario == null)
			throw new IllegalArgumentException("idFunzioanrio =null");

		if (cnmTVerbale.getNumeroProtocollo() == null) {
			List<CnmRAllegatoVerbale> cnmRAllegatoVerbaleList = cnmRAllegatoVerbaleRepository.findByCnmTVerbale(cnmTVerbale);
			if (!cnmRAllegatoVerbaleList.isEmpty()) {
				ResponseProtocollaDocumento response = commonAllegatoService.avviaProtocollazione(cnmRAllegatoVerbaleList, cnmTUser);
				cnmTVerbale.setNumeroProtocollo(response.getProtocollo());
				cnmTVerbale.setDataOraProtocollo(now);
			}
		}

		cnmTVerbale.setCnmDStatoVerbale(cnmDStatoVerbale);
		cnmTVerbale.setDataOraUpdate(now);
		cnmTVerbale.setCnmTUser1(cnmTUser);

		if (assegnaEnable) {
			Boolean notUguale = Boolean.FALSE;
			CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
			if (cnmRFunzionarioIstruttore != null && cnmRFunzionarioIstruttore.getCnmTUser().getIdUser() != userDetails.getIdUser()) {
				cnmRFunzionarioIstruttore.setFineAssegnazione(new Date());
				cnmRFunzionarioIstruttoreRepository.save(cnmRFunzionarioIstruttore);
				notUguale = Boolean.TRUE;
			}

			if (cnmRFunzionarioIstruttore == null || notUguale) {	
				CnmTUser cnmTUserIstruttore = cnmTUserRepository.findOne(idFunzionario.longValue());

				cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTUserAndCnmTVerbale(cnmTUserIstruttore, cnmTVerbale);
				if(cnmRFunzionarioIstruttore == null) {
					cnmRFunzionarioIstruttore = new CnmRFunzionarioIstruttore();
				}else {
					cnmRFunzionarioIstruttore.setFineAssegnazione(null);
				}
				cnmRFunzionarioIstruttore.setCnmTUser(cnmTUserIstruttore);
				cnmRFunzionarioIstruttore.setCnmTVerbale(cnmTVerbale);
				cnmRFunzionarioIstruttore.setInizioAssegnazione(new Date());
				cnmRFunzionarioIstruttoreRepository.save(cnmRFunzionarioIstruttore);
			}
		}

		cnmTVerbaleRepository.save(cnmTVerbale);

	}

	private Long getStatoAvanzabileByCnmTVerbale(CnmTVerbale cnmTVerbale, UserDetails userDetails) {
		Long idStatoVerbale = cnmTVerbale.getCnmDStatoVerbale().getIdStatoVerbale();

		// controllo soggetti associati
		List<SoggettoVO> soggetti = soggettoVerbaleService.getSoggettiByIdVerbale(cnmTVerbale.getIdVerbale(), userDetails, false);
		if (soggetti == null || soggetti.isEmpty())
			return null;

		// controllo allegati associati
		List<TipoAllegatoVO> tipologiaAllegatiList = allegatoVerbaleService.getTipologiaAllegatiByCnmVerbale(cnmTVerbale);
		if (tipologiaAllegatiList == null || tipologiaAllegatiList.isEmpty())
			return null;

		List<Long> idAllegatoList = FluentIterable.from(tipologiaAllegatiList).transform(UtilsTipoAllegato.converTipoAllegatoToLong()).toList();

		Long idStato;

		// Controllo permessi azione
		long idProprietarioVerbale = cnmTVerbale.getCnmTUser2().getIdUser();
		long idUserConnesso = userDetails.getIdUser();
		Long idUserAssegnato = null;
		CnmRFunzionarioIstruttore istr = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
		if (istr != null)
			idUserAssegnato = istr.getCnmTUser().getIdUser();
		if (!controllaPermessiAzione(idStatoVerbale, idProprietarioVerbale, idUserConnesso, idUserAssegnato))
			return null;

		if (idStatoVerbale == Constants.STATO_VERBALE_INCOMPLETO) {
			idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI, idAllegatoList,
					RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
			if (idStato == null) {
				idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, idAllegatoList,
						RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
			}
			if (idStato == null) {
				idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO, idAllegatoList, RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO);
			}

			return idStato;
		} else if (idStatoVerbale == Constants.STATO_VERBALE_ACQUISITO) {
			idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA, idAllegatoList, RegoleAllegatiCambiamentoStato.STATO_VERBALE_ARCHIVIAZIONE);

			if (idStato == null) {
				idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI, idAllegatoList,
						RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI);
			}
			if (idStato == null) {
				idStato = RegoleAllegatiCambiamentoStato.statoAvanzabile(Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, idAllegatoList,
						RegoleAllegatiCambiamentoStato.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO);
			}

			return idStato;
		}

		return null;
	}

	/*
	 * true utente ha i permessi false utente non ha i permessi
	 */
	@Override
	public boolean controllaPermessiAzione(Long idStatoVerbale, long idProprietarioVerbale, long idUserConnesso, Long idUserAssegnato) {
		if (idStatoVerbale == Constants.STATO_VERBALE_INCOMPLETO) {
			if (idProprietarioVerbale == idUserConnesso)
				return true;
		}
		if (idStatoVerbale == Constants.STATO_VERBALE_ACQUISITO || idStatoVerbale == Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI
				|| idStatoVerbale == Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO || idStatoVerbale == Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA) {
			return idUserAssegnato != null && idUserAssegnato == idUserConnesso;
		}

		return false;
	}

	private boolean isAzionePermessa(Integer id, UserDetails userDetails, Long idAzioneRichiesta) {
		AzioneVerbaleResponse az = getAzioniVerbale(id, userDetails);
		Long idAzione = az.getAzione() != null ? az.getAzione().getId() : null;
		return idAzione != null && idAzione.equals(idAzioneRichiesta);
	}

	@Override
	public Boolean isTipoAllegatoAllegabile(Integer id, String codiceDocumento, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);
		Boolean isAllegatiEnable = Boolean.FALSE;
		TipoAllegato tipo = TipoAllegato.getTipoAllegatoByTipoDocumento(codiceDocumento);

		// aggiungi allegati enable
		List<TipoAllegatoVO> tipologiaAllegabili = allegatoVerbaleService.getTipologiaAllegatiAllegabiliVerbale(id, tipo.getTipoDocumento(), false);

		switch (tipo) {
		case RICEVUTA_PAGAMENTO_VERBALE:
			isAllegatiEnable = isAggiugiAllegatoRiconciliazioneVerbale(cnmTVerbale, userDetails, tipologiaAllegabili);
			break;
		case VERBALE_AUDIZIONE:
			isAllegatiEnable = isAggiungiVerbaleDiAudizione(cnmTVerbale);
			break;
		case CONVOCAZIONE_AUDIZIONE:
			isAllegatiEnable = isAggiungiConvocazioneAudizione(cnmTVerbale);
			break;
		case CONTRODEDUZIONE:
			isAllegatiEnable = isAggiugiAllegatoVerbaleControdeduzioni(cnmTVerbale, userDetails, tipologiaAllegabili);
			break;
		case COMPARSA:
			isAllegatiEnable = isAggiugiAllegatoVerbaleControdeduzioni(cnmTVerbale, userDetails, tipologiaAllegabili);
			break;
		default:
			throw new RuntimeException("Tipo Allegato non dichiarato");
		}

		return isAllegatiEnable;

	}

	@Override
	public Boolean isEnableCaricaVerbaleAudizione(Integer id, UserDetails userDetails) {
		CnmTVerbale cnmTVerbale = utilsVerbale.validateAndGetCnmTVerbale(id);

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleSoggettoList == null || cnmRVerbaleSoggettoList.isEmpty())
			return false;

		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
			List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogList = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggetto(cnmRVerbaleSoggetto);
			if (!cnmRAllegatoVerbSogList.isEmpty()) {
				Collection<CnmRAllegatoVerbSog> list = Collections2.filter(cnmRAllegatoVerbSogList, new Predicate<CnmRAllegatoVerbSog>() {
					public boolean apply(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) {
						return cnmRAllegatoVerbSog.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.VERBALE_AUDIZIONE.getId();
					}
				});

				List<CnmRAllegatoVerbSog> lista = new ArrayList<CnmRAllegatoVerbSog>(list);

				if (list != null && list.size() > 0) {
					List<CnmTAllegato> temp = cnmTAllegatoRepository.findByCnmRAllegatoVerbSogsIn(lista);

					if (temp != null && temp.size() > 0) {
						for (CnmTAllegato a : temp) {
							if (StringUtils.isNotBlank(a.getIdIndex()) && StringUtils.isBlank(a.getNumeroProtocollo())) {
								return true;
							}
						}
					}

				}

			}
		}

		return false;

	}

	private Boolean isAggiungiVerbaleDiAudizione(CnmTVerbale cnmTVerbale) {
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleSoggettoList.isEmpty())
			return Boolean.FALSE;

		for (CnmRVerbaleSoggetto vs : cnmRVerbaleSoggettoList) {
			List<CnmRAllegatoVerbSog> allegato = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggetto(vs);
			if (allegato == null || allegato.isEmpty())
				return true;
			boolean flag = false;
			for (CnmRAllegatoVerbSog a : allegato) {
				if (a.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.VERBALE_AUDIZIONE.getId())
					flag = true;
			}
			if (!flag)
				return Boolean.TRUE;

		}
		return Boolean.FALSE;

		/*
		 * List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogList =
		 * cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggettoIn(
		 * cnmRVerbaleSoggettoList); if (cnmRAllegatoVerbSogList.isEmpty())
		 * return true;
		 * 
		 * Collection<CnmRAllegatoVerbSog> collectionsFiltered =
		 * Collections2.filter(cnmRAllegatoVerbSogList, new
		 * Predicate<CnmRAllegatoVerbSog>() { public boolean
		 * apply(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) { return
		 * cnmRAllegatoVerbSog.getCnmTAllegato().getCnmDTipoAllegato().
		 * getIdTipoAllegato() == TipoAllegato.VERBALE_AUDIZIONE.getId(); } });
		 * 
		 * return !(cnmRAllegatoVerbSogList.size() ==
		 * collectionsFiltered.size());
		 */
	}

	private Boolean isAggiungiConvocazioneAudizione(CnmTVerbale cnmTVerbale) {
		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (cnmRVerbaleSoggettoList.isEmpty())
			return false;

		for (CnmRVerbaleSoggetto vs : cnmRVerbaleSoggettoList) {
			List<CnmRAllegatoVerbSog> allegato = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggetto(vs);
			if (allegato == null || allegato.isEmpty())
				return true;
			boolean flag = false;
			for (CnmRAllegatoVerbSog a : allegato) {
				if (a.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId())
					flag = true;
			}
			if (!flag)
				return true;
		}
		return false;
	}

	private Boolean isAggiugiAllegatoRiconciliazioneVerbale(CnmTVerbale cnmTVerbale, UserDetails userDetails, List<TipoAllegatoVO> tipologiaAllegabili) {
		Boolean isAllegatiEnable = Boolean.FALSE;
		AppGrantedAuthority appGrantedAuthority = SecurityUtils.getRuolo(userDetails.getAuthorities());

		if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ISTRUTTORE)) {
			CnmRFunzionarioIstruttore cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale);
			if (cnmRFunzionarioIstruttore != null) {
				if (cnmRFunzionarioIstruttore.getCnmTUser().getIdUser() == userDetails.getIdUser() && tipologiaAllegabili != null && !tipologiaAllegabili.isEmpty())
					isAllegatiEnable = Boolean.TRUE;
			} else if (tipologiaAllegabili != null && !tipologiaAllegabili.isEmpty() && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
				isAllegatiEnable = Boolean.TRUE;
		} else if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_AMMINISTRATIVO)) {
			if (tipologiaAllegabili != null && !tipologiaAllegabili.isEmpty() && cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
				isAllegatiEnable = Boolean.TRUE;
		} else
			throw new SecurityException("Ruolo non abilitato ad accedere a questo servizio");

		return isAllegatiEnable;
	}

	private Boolean isAggiugiAllegatoVerbaleControdeduzioni(CnmTVerbale cnmTVerbale, UserDetails userDetails, List<TipoAllegatoVO> tipologiaAllegabili) {
		Boolean isAllegatiEnable = Boolean.FALSE;

		List<CnmRVerbaleSoggetto> verbaleSoggetto = cnmRVerbaleSoggettoRepository.findByCnmTVerbale(cnmTVerbale);
		if (verbaleSoggetto != null && verbaleSoggetto.size() > 0) {
			List<CnmROrdinanzaVerbSog> ordinanzaVerbSog = cnmROrdinanzaVerbSogRepository.findByCnmRVerbaleSoggettoIn(verbaleSoggetto);
			if (ordinanzaVerbSog != null && ordinanzaVerbSog.size() > 0) {
				for (CnmROrdinanzaVerbSog tmp : ordinanzaVerbSog) {
					Long stato = new Long(tmp.getCnmDStatoOrdVerbSog().getIdStatoOrdVerbSog());
					if (stato.compareTo(new Long(7)) != 0 || stato.compareTo(new Long(8)) != 0 || stato.compareTo(new Long(9)) != 0) {
						return Boolean.TRUE;
					}
				}
			} else
				isAllegatiEnable = Boolean.TRUE;
		} else
			isAllegatiEnable = Boolean.TRUE;

		/*
		 * AppGrantedAuthority appGrantedAuthority =
		 * SecurityUtils.getRuolo(userDetails.getAuthorities());
		 * 
		 * if (appGrantedAuthority.getCodice().equals(Constants.
		 * RUOLO_UTENTE_ISTRUTTORE)) { CnmRFunzionarioIstruttore
		 * cnmRFunzionarioIstruttore = cnmRFunzionarioIstruttoreRepository.
		 * findByCnmTVerbaleAndDataAssegnazione(cnmTVerbale); if
		 * (cnmRFunzionarioIstruttore != null) { if
		 * (cnmRFunzionarioIstruttore.getCnmTUser().getIdUser() ==
		 * userDetails.getIdUser() && tipologiaAllegabili != null &&
		 * !tipologiaAllegabili.isEmpty()) isAllegatiEnable = Boolean.TRUE; }
		 * else if (tipologiaAllegabili != null &&
		 * !tipologiaAllegabili.isEmpty() &&
		 * cnmTVerbale.getCnmTUser2().getIdUser() == userDetails.getIdUser())
		 * isAllegatiEnable = Boolean.TRUE; }
		 */

		return isAllegatiEnable;
	}

	


	
}
