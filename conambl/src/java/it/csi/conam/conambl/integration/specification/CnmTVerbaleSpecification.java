/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.integration.entity.CnmDAmbito;
import it.csi.conam.conambl.integration.entity.CnmDArticolo;
import it.csi.conam.conambl.integration.entity.CnmDComma;
import it.csi.conam.conambl.integration.entity.CnmDComune;
import it.csi.conam.conambl.integration.entity.CnmDEnte;
import it.csi.conam.conambl.integration.entity.CnmDLettera;
import it.csi.conam.conambl.integration.entity.CnmDNorma;
import it.csi.conam.conambl.integration.entity.CnmDRuoloSoggetto;
import it.csi.conam.conambl.integration.entity.CnmDStatoManuale;
import it.csi.conam.conambl.integration.entity.CnmDStatoPregresso;
import it.csi.conam.conambl.integration.entity.CnmDStatoVerbale;
import it.csi.conam.conambl.integration.entity.CnmREnteNorma;
import it.csi.conam.conambl.integration.entity.CnmRFunzionarioIstruttore;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleIllecito;
import it.csi.conam.conambl.integration.entity.CnmRVerbaleSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTSoggetto;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.util.VerbaleSearchParam;

public class CnmTVerbaleSpecification {
	
	public static Specification<CnmTVerbale> findBy(
		List<CnmTSoggetto> trasgressore,
		List<CnmTSoggetto> obbligatoInSolido,
		List<CnmDEnte> enteAccertatore,
		VerbaleSearchParam parametriVerbale,
		CnmDComune comuneEnte,
		CnmTUser funzionarioIstruttore

	){
		if (parametriVerbale == null) parametriVerbale = new VerbaleSearchParam();
		
		return findBy(
			trasgressore,
			obbligatoInSolido,
			parametriVerbale.getNumeroProtocollo(),
			parametriVerbale.getNumeroVerbale(),
			parametriVerbale.getLettera(),
			parametriVerbale.getAmbito(),
			parametriVerbale.getStatoVerbale(),
			parametriVerbale.getStatoManuale(),
			enteAccertatore,
			parametriVerbale.getStatiPregresso(),
			parametriVerbale.getDataAccertamentoDa(),
			parametriVerbale.getDataAccertamentoA(),
			parametriVerbale.getDataProcessoVerbaleDa(),
			parametriVerbale.getDataProcessoVerbaleA(),
			parametriVerbale.getAnnoAccertamento(),
			comuneEnte,
			funzionarioIstruttore
		);
	};
	
	
	public static Specification<CnmTVerbale> findBy(
		List<CnmTSoggetto> trasgressore, // soggetti trasgressori
		List<CnmTSoggetto> obbligatoInSolido, // soggetti obbligati
		String numeroPrototocollo, //
		String numeroVerbale, //
		List<CnmDLettera> lettera, // lettera selezionata dal FE
		CnmDAmbito ambito, // ambito selezionato dal FE
		List<CnmDStatoVerbale> statoVerbs, // stato verbale
		List<CnmDStatoManuale> statoManuale,
		List<CnmDEnte> enteAccertatore, // valorizzato solo per utenti accertatori
		List<CnmDStatoPregresso> statiPregresso, // lista stati pregresso,
		Date dataAccertamentoDa,
		Date dataAccertamentoA,
		Date dataProcessoVerbaleDa,
		Date dataProcessoVerbaleA,
		Long  annoAccertamento,
		CnmDComune comuneEnte, // OB31
		CnmTUser funzionarioIstruttore //OB32
	) {
		return new Specification<CnmTVerbale>() {
			@Override
			public Predicate toPredicate(Root<CnmTVerbale> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();

				if (lettera != null && !lettera.isEmpty()) {
					final Join<CnmTVerbale, CnmRVerbaleIllecito> cnmRVerbaleIllecitoJoin = root.join("cnmRVerbaleIllecitos");
					final Join<CnmRVerbaleIllecito, CnmDLettera> cnmDLetteraJoin = cnmRVerbaleIllecitoJoin.join("cnmDLettera");
					predicates.add(cnmDLetteraJoin.in(lettera));
				}
				
				// 20211125_LC Jira 184 - ricerca per ambito
				if (ambito != null) {
					final Join<CnmTVerbale, CnmRVerbaleIllecito> cnmRVerbaleIllecitoJoin = root.join("cnmRVerbaleIllecitos");
					final Join<CnmRVerbaleIllecito, CnmDLettera> cnmDLetteraJoin = cnmRVerbaleIllecitoJoin.join("cnmDLettera");
					final Join<CnmDLettera, CnmDComma> cnmDCommaJoin = cnmDLetteraJoin.join("cnmDComma");
					final Join<CnmDComma, CnmDArticolo> cnmDArticoloJoin = cnmDCommaJoin.join("cnmDArticolo");
					final Join<CnmDArticolo, CnmREnteNorma> cnmREnteNormaJoin = cnmDArticoloJoin.join("cnmREnteNorma");
					final Join<CnmREnteNorma, CnmDNorma> cnmDNormaJoin = cnmREnteNormaJoin.join("cnmDNorma");
					final Join<CnmDNorma, CnmDAmbito> cnmDAmbitoJoin = cnmDNormaJoin.join("cnmDAmbito");
					predicates.add(cnmDAmbitoJoin.in(ambito));
				}
				
				if (trasgressore != null && !trasgressore.isEmpty()) {
					Subquery<CnmRVerbaleSoggetto> subqueryRVerbaleSoggetto = query.subquery(CnmRVerbaleSoggetto.class);
					Root<CnmRVerbaleSoggetto> rootSubqueryRVerbaleSoggetto = subqueryRVerbaleSoggetto.from(CnmRVerbaleSoggetto.class);
					final Join<CnmRVerbaleSoggetto, CnmDRuoloSoggetto> cnmDRuoloSoggettoJoin = rootSubqueryRVerbaleSoggetto.join("cnmDRuoloSoggetto");
					final Join<CnmRVerbaleSoggetto, CnmTSoggetto> cnmRSoggettoJoin = rootSubqueryRVerbaleSoggetto.join("cnmTSoggetto");
					Predicate predicate = builder.and(cnmRSoggettoJoin.in(trasgressore), builder.equal(cnmDRuoloSoggettoJoin.get("idRuoloSoggetto"), Constants.VERBALE_SOGGETTO_RUOLO_TRASGRESSORE_ID));
					subqueryRVerbaleSoggetto.select(rootSubqueryRVerbaleSoggetto.get("cnmTVerbale")).where(predicate).distinct(true);
					predicates.add(root.in(subqueryRVerbaleSoggetto));
				}
				if (obbligatoInSolido != null && !obbligatoInSolido.isEmpty()) {
					Subquery<CnmRVerbaleSoggetto> subqueryRVerbaleSoggetto = query.subquery(CnmRVerbaleSoggetto.class);
					Root<CnmRVerbaleSoggetto> rootSubqueryRVerbaleSoggetto = subqueryRVerbaleSoggetto.from(CnmRVerbaleSoggetto.class);
					final Join<CnmRVerbaleSoggetto, CnmDRuoloSoggetto> cnmDRuoloSoggettoJoin = rootSubqueryRVerbaleSoggetto.join("cnmDRuoloSoggetto");
					final Join<CnmRVerbaleSoggetto, CnmTSoggetto> cnmRSoggettoJoin = rootSubqueryRVerbaleSoggetto.join("cnmTSoggetto");
					Predicate predicate = builder.and(cnmRSoggettoJoin.in(obbligatoInSolido),
							builder.equal(cnmDRuoloSoggettoJoin.get("idRuoloSoggetto"), Constants.VERBALE_SOGGETTO_RUOLO_OBBLIGATO_IN_SOLIDO_ID));
					subqueryRVerbaleSoggetto.select(rootSubqueryRVerbaleSoggetto.get("cnmTVerbale")).where(predicate).distinct(true);
					predicates.add(root.in(subqueryRVerbaleSoggetto));
				}

				if (numeroPrototocollo != null) {
					predicates.add(builder.equal(root.get("numeroProtocollo"), numeroPrototocollo));
				}

				if (annoAccertamento !=null) {
					
					Calendar calendar = Calendar.getInstance();
					
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					
					calendar.set(Calendar.YEAR, annoAccertamento.intValue());
					
					calendar.set(Calendar.DAY_OF_YEAR, 1); // Imposta il giorno dell'anno a 1
					Date dataInizioAccertamento = calendar.getTime();

		            // Trova la data di fine anno
		            calendar.set(Calendar.MONTH, Calendar.DECEMBER); // Imposta il mese a dicembre
		            calendar.set(Calendar.DAY_OF_MONTH, 31); // Imposta il giorno a 31
		            Date dataFineAccertamento =  calendar.getTime();

		            predicates.add(builder.greaterThanOrEqualTo(root.get("dataOraAccertamento"), dataInizioAccertamento));
		            predicates.add(builder.lessThanOrEqualTo(root.get("dataOraAccertamento"), dataFineAccertamento));
					
				}

				//if (dataAccertamentoDa != null && dataAccertamentoA != null) {
				//    predicates.add(builder.between(root.get("dataOraAccertamento"), dataAccertamentoDa, dataAccertamentoA));
				//} else 
				if (dataAccertamentoDa != null) {

				    predicates.add(builder.greaterThan(root.get("dataOraAccertamento"), getFromDay(dataAccertamentoDa)));
				} 
				if (dataAccertamentoA != null) {
				    predicates.add(builder.lessThan(root.get("dataOraAccertamento"), getToDay(dataAccertamentoA)));
				}

				//if (dataProcessoVerbaleDa != null && dataProcessoVerbaleA != null) {
				//    predicates.add(builder.between(root.get("dataOraViolazione"), dataProcessoVerbaleDa, dataProcessoVerbaleA));
				//} else 
				if (dataProcessoVerbaleDa != null) {
				    predicates.add(builder.greaterThan(root.get("dataOraViolazione"), getFromDay(dataProcessoVerbaleDa)));
				} 
				if (dataProcessoVerbaleA != null) {
				    predicates.add(builder.lessThan(root.get("dataOraViolazione"), getToDay(dataProcessoVerbaleA)));
				}
					
								
				
				if (StringUtils.isNotEmpty(numeroVerbale)) {
					// 20230110 PP - ricerca per numero verbale in like
//					predicates.add(builder.equal(root.get("numVerbale"), numeroVerbale.toUpperCase()));
					predicates.add(builder.like(root.get("numVerbale"), "%"+numeroVerbale+"%"));
				}

				if(statiPregresso != null && !statiPregresso.isEmpty()) {
					predicates.add(root.get("cnmDStatoPregresso").in(statiPregresso));
				}

				if (statoVerbs != null) {
					predicates.add(root.get("cnmDStatoVerbale").in(statoVerbs));
				}

				/*if (statoManuale != null) {
					predicates.add(root.get("cnmDStatoManuale").in(statoManuale));
				}*/
				
				if(statoManuale != null && !statoManuale.isEmpty()) {
					final Join<CnmTVerbale, CnmDStatoManuale> cnmDStatoManualeJoin = root.join("cnmDStatoManuale");
					List<Long> idList = new ArrayList<Long>();
					for (CnmDStatoManuale stManuale:statoManuale) {
						idList.add(stManuale.getId());
					}
					predicates.add(cnmDStatoManualeJoin.get("id").in(idList));
				}
				

				// valorizzato solo per gli accertatori
				if (enteAccertatore != null && !enteAccertatore.isEmpty()) {
					final Join<CnmTVerbale, CnmDEnte> enteAccertatoreJoin = root.join("cnmDEnte");
					predicates.add(enteAccertatoreJoin.in(enteAccertatore));
				}
				
				
				// OB31
				if (comuneEnte != null) {
	               final Join<CnmTVerbale, CnmDComune> cnmDComuneJoin = root.join("cnmDComuneEnte");
	               predicates.add(builder.equal(cnmDComuneJoin, comuneEnte));
	            }
				
				// OB32
				if (funzionarioIstruttore != null) {
					Date now = new Date();
					final Join<CnmTVerbale, CnmRFunzionarioIstruttore> cnmRFunzionarioIstruttoreJoin = root.join("cnmRFunzionarioIstruttores");
					final Join<CnmRFunzionarioIstruttore, CnmTUser> cnmTUserJoin = cnmRFunzionarioIstruttoreJoin.join("cnmTUser");
					//  ((u.fineAssegnazione is null or u.fineAssegnazione>NOW()) and (u.inizioAssegnazione is null or u.inizioAssegnazione<=NOW()))")
				    predicates.add(builder.lessThanOrEqualTo(cnmRFunzionarioIstruttoreJoin.get("inizioAssegnazione"), now));				
				    predicates.add(builder.or(
							builder.greaterThanOrEqualTo(cnmRFunzionarioIstruttoreJoin.get("fineAssegnazione"), now),
							builder.isNull(cnmRFunzionarioIstruttoreJoin.get("fineAssegnazione"))));
					predicates.add(builder.equal(cnmTUserJoin, funzionarioIstruttore));
				}
				 
				query.distinct(true);

				return builder.and(predicates.toArray(new Predicate[0]));
			}

		};
	}

	private static Date getFromDay(Date date){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	
	private static Date getToDay(Date date){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

}
