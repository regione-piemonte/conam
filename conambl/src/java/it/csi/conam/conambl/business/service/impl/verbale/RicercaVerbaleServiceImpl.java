/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import it.csi.conam.conambl.business.service.RiferimentiNormativiService;
import it.csi.conam.conambl.business.service.common.CommonSoggettoService;
import it.csi.conam.conambl.business.service.util.xls.ExcelDataUtil;
import it.csi.conam.conambl.business.service.util.xls.ExcelSheet;
import it.csi.conam.conambl.business.service.verbale.RicercaVerbaleService;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.security.SecurityUtils;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.MinVerbaleEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoManualeEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.StatoVerbaleEntityMapper;
import it.csi.conam.conambl.integration.mapper.entity.VerbaleEntityMapper;
import it.csi.conam.conambl.integration.repositories.CnmDComuneRepository;
import it.csi.conam.conambl.integration.repositories.CnmDEnteRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoManualeRepository;
import it.csi.conam.conambl.integration.repositories.CnmDStatoVerbaleRepository;
import it.csi.conam.conambl.integration.repositories.CnmTUserRepository;
import it.csi.conam.conambl.integration.repositories.CnmTVerbaleRepository;
import it.csi.conam.conambl.integration.specification.CnmTVerbaleSpecification;
import it.csi.conam.conambl.request.SoggettoRequest;
import it.csi.conam.conambl.request.verbale.DatiVerbaleRequest;
import it.csi.conam.conambl.request.verbale.RicercaVerbaleRequest;
import it.csi.conam.conambl.security.AppGrantedAuthority;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.util.VerbaleSearchParam;
import it.csi.conam.conambl.vo.ReportColumnVO;
import it.csi.conam.conambl.vo.leggi.EnteVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import it.csi.conam.conambl.vo.verbale.MinSoggettoVO;
import it.csi.conam.conambl.vo.verbale.MinVerbaleVO;
import it.csi.conam.conambl.vo.verbale.StatoManualeVO;
import it.csi.conam.conambl.vo.verbale.StatoVerbaleVO;
import it.csi.coopdiag.utils.LoggerUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class RicercaVerbaleServiceImpl implements RicercaVerbaleService {
	@Autowired
	private StatoManualeEntityMapper statoManaualeEntityMapper;

	@Autowired
	private CnmDStatoManualeRepository cnmDStatoManualeRepository;
	
	@Autowired
	private StatoVerbaleEntityMapper statoVerbaleEntityMapper;

	@Autowired
	private RiferimentiNormativiService riferimentiNormativiService;

	@Autowired
	private CnmDStatoVerbaleRepository cnmDStatoVerbaleRepository;

	@Autowired
	private CnmTVerbaleRepository cnmTVerbaleRepository;

	@Autowired
	private CnmDEnteRepository cnmDEnteRepository;

	@Autowired
	private CommonSoggettoService commonSoggettoService;

	@Autowired
	private MinVerbaleEntityMapper minVerbaleEntityMapper;

	@Autowired
	private VerbaleEntityMapper verbaleEntityMapper;

	@Autowired
	private CnmDComuneRepository cnmDComuneRepository;
	
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	
	@Override
	public List<StatoVerbaleVO> getStatiRicercaVerbale() {
		return statoVerbaleEntityMapper.mapListEntityToListVO((List<CnmDStatoVerbale>) cnmDStatoVerbaleRepository.findAll());
	}

	@Override
	public List<StatoManualeVO> getStatiManuali() {
		return statoManaualeEntityMapper.mapListEntityToListVO((List<CnmDStatoManuale>) cnmDStatoManualeRepository.findAll());
	}


	@Override
	public List<MinVerbaleVO> ricercaVerbale(RicercaVerbaleRequest request, UserDetails userDetails, boolean withDoc) {
		DatiVerbaleRequest dati = request.getDatiVerbale();
		List<SoggettoRequest> soggetti = request.getSoggettoVerbale();
		List<MinVerbaleVO> verbaliList = new ArrayList<>();

		if (dati == null && soggetti == null) {
			throw new IllegalArgumentException("Nessun Parametro di ricerca valorizzato");
		}

		List<CnmDEnte> enteAccertatore = null;
		AppGrantedAuthority appGrantedAuthority = SecurityUtils.getRuolo(userDetails.getAuthorities());

		List<CnmDLettera> lettera = null;

		// OB31 
		if (dati != null && dati.getEnteAccertatore() != null && dati.getEnteAccertatore().getId() !=null) {
			CnmDEnte ente = cnmDEnteRepository.findByIdEnte(dati.getEnteAccertatore().getId());
			if (ente != null) {
				enteAccertatore = new ArrayList<>();
				enteAccertatore.add(ente);
			}
		} else {
			//Avra una sola legge
			if (
				appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_AMMINISTRATIVO) ||
				appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ISTRUTTORE)
			) {
				List<EnteVO> enteLegge = userDetails.getEntiLegge();
				if (enteLegge != null && !enteLegge.isEmpty() && enteLegge.size() == 1) {
					lettera = riferimentiNormativiService.getLettereByEnte(enteLegge.get(0).getId(), false);
				} else
					throw new SecurityException("Errore l'utente ammistritativo o funzionario deve avere un solo ente legge, quello di sua competenza");
			} else if (appGrantedAuthority.getCodice().equals(Constants.RUOLO_UTENTE_ACCERTATORE)) {
				List<Long> idEnteList = new ArrayList<>();
				for (EnteVO ente : userDetails.getEntiAccertatore()) {
					idEnteList.add(ente.getId());
				}
				enteAccertatore = (List<CnmDEnte>) cnmDEnteRepository.findAll(idEnteList);
			} else {				
				throw new SecurityException("Ruolo non riconosciuto dal sistema");
			}

		}
		//RECUPERO I PARAMETRI PER IL FILTRO DEL VERBALE
		VerbaleSearchParam parametriVerbale = verbaleEntityMapper.getVerbaleParamFromRequest(dati);

		if (dati == null) parametriVerbale.setLettera(lettera);
		
		// OB31
		CnmDComune comuneS = null;
		if(parametriVerbale.getComuneEnteAccertatore() != null && parametriVerbale.getComuneEnteAccertatore().getId() != null) {
			comuneS = cnmDComuneRepository.findByIdComune(parametriVerbale.getComuneEnteAccertatore().getId() );
		}
		// OB32
		CnmTUser funzionarioIstruttore = null;
		if(parametriVerbale.getAssegnatario() != null && parametriVerbale.getAssegnatario().getId() != null) {
			funzionarioIstruttore = cnmTUserRepository.findByIdUser(parametriVerbale.getAssegnatario().getId());
		}	

		List<CnmTSoggetto> trasgressore = new ArrayList<>();
		List<CnmTSoggetto> obbligatoInSolido = new ArrayList<>();
		if (soggetti != null && !soggetti.isEmpty()) {
			for (SoggettoRequest s : soggetti) {
				
				// 20220921 PP - Fix jira CONAM-223
				List<CnmTSoggetto> cnmTSoggetto = commonSoggettoService.getSoggettiFromDb(new MinSoggettoVO(s), true);
				String tipo = s.getTipoSoggetto();
				if (cnmTSoggetto == null || cnmTSoggetto.size() == 0)
					return verbaliList;
				if (tipo != null && tipo.equals(Constants.TYPE_SOGGETTO_RICERCA_TRASGRESSORE))
					trasgressore.addAll(cnmTSoggetto);
				if (tipo != null && tipo.equals(Constants.TYPE_SOGGETTO_RICERCA_OBBLIGATO_IN_SOLIDO))
					obbligatoInSolido.addAll(cnmTSoggetto);
				
//				CnmTSoggetto cnmTSoggetto = null;
//				String tipo = s.getTipoSoggetto();
//				if ((cnmTSoggetto = commonSoggettoService.getSoggettoFromDb(new MinSoggettoVO(s), true)) == null)
//					return verbaliList;
//				if (tipo != null && tipo.equals(Constants.TYPE_SOGGETTO_RICERCA_TRASGRESSORE))
//					trasgressore.add(cnmTSoggetto);
//				if (tipo != null && tipo.equals(Constants.TYPE_SOGGETTO_RICERCA_OBBLIGATO_IN_SOLIDO))
//					obbligatoInSolido.add(cnmTSoggetto);
			}

		}

		List<CnmTVerbale> verb = cnmTVerbaleRepository.findAll(
			CnmTVerbaleSpecification.findBy(
				trasgressore,
				obbligatoInSolido,
				enteAccertatore,
				parametriVerbale,
				comuneS, // OB31
				funzionarioIstruttore // OB32
			)
		);
		if(withDoc){
			return minVerbaleEntityMapper.mapListEntityToListVOAndDoc(verb, userDetails.getIdUser());
		}else{
			return minVerbaleEntityMapper.mapListEntityToListVO(verb, userDetails.getIdUser());
		}

	}

	@Override
	public DocumentoScaricatoVO downloadReport(RicercaVerbaleRequest request, UserDetails userDetails) {
		//LoggerUtil.debug(logger, "[::downloadReport] BEGIN");

		//metodo per riportare i filtri su nome file
		
		List<MinVerbaleVO> verbaleList = ricercaVerbale(request, userDetails, true);
		String suffix = getFilterByRequest(verbaleList.size());
				
        ExcelDataUtil excel = new ExcelDataUtil("CONAM-RICERCA-FASCICOLI", suffix);
        HSSFSheet sheet = excel.addSheet("Export_"+new SimpleDateFormat("ddMMyyyy").format(new Date()) + "_Tot." + verbaleList.size());
        HSSFRow row = excel.addRow(sheet, 0);

		int columnN = 0;
		for(ReportColumnVO column : request.getColumnList()){
			if(column.isChecked()
			 || column.getENUMColumnName() == ReportColumnVO.ColumnName.NUMERO_VERBALE
			 || column.getENUMColumnName() == ReportColumnVO.ColumnName.AMBITO
			 || column.getENUMColumnName() == ReportColumnVO.ColumnName.TRASGRESSORI
			 || column.getENUMColumnName() == ReportColumnVO.ColumnName.OBBLIGATI){
				excel.addTitleColumn(row, columnN,column.getDisplayName());
				column.setChecked(true);
				columnN ++;
			}
		}

		// TODO - Aggiungere sempre la colonna 'Documenti' che riportera le categorie di documenti allegati e l'eventuale protocollo
		excel.addTitleColumn(row, columnN,"Documenti");
		if(verbaleList.size()>0){
        	addContent(excel, sheet, verbaleList, request.getColumnList());
		}else{
			HSSFRow rowNoDAta = excel.addRow(sheet, 1);
			excel.addStringColumn(rowNoDAta, 0,"Nessun dato");
			sheet.autoSizeColumn(0);
		}
        
        DocumentoScaricatoVO report = null;
		try {
			report = new DocumentoScaricatoVO(excel.getFileName(), excel.getFile());
		} catch (IOException e) {
			//LoggerUtil.warn(logger, "[::downloadReport] problemi in generazione report");
		}
		return report;
	}

	private String getFilterByRequest(int i){
		return "TOT-"+i;
	}
	
	private void addContent(ExcelDataUtil excel, HSSFSheet sheet, List<MinVerbaleVO> list, List<ReportColumnVO> columnList) {

		Map<ReportColumnVO.ColumnName, String> columnChecked = new HashMap<ReportColumnVO.ColumnName, String>();
		for(ReportColumnVO column : columnList){
			if (column.isChecked()) {
				columnChecked.put(column.getENUMColumnName(), column.getDisplayName());
				System.out.println(column.getColumnName() + " - " + column.getDisplayName());
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		int rowNum = 1;
		int cellNum = 0;
		for(MinVerbaleVO verbale : list){
			cellNum = 0;

			HSSFRow row = excel.addRow(sheet, rowNum);
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.NUMERO_PROTOCOLLO)){
				excel.addStringColumn(row, cellNum,verbale.getNumeroProtocollo()!=null?verbale.getNumeroProtocollo():"");
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.NUMERO_VERBALE)){
				excel.addStringColumn(row, cellNum,verbale.getNumero());
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.ANNO_ACCERTAMENTO)){
				excel.addStringColumn(row, cellNum,verbale.getAnnoAccertamento().toString());
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.ENTE_RIF_NOR)){
				excel.addStringColumn(row, cellNum,verbale.getEnteRiferimentiNormativi());
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.AMBITO)){
				excel.addStringColumn(row, cellNum,verbale.getAmbiti());
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.LEGGE_VIOLATA)){
				excel.addStringColumn(row, cellNum,verbale.getLeggeViolata());
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.ENTE_ACCERTATORE)){
				excel.addStringColumn(row, cellNum,verbale.getEnteAccertatore().getDenominazione());
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.COMUNE_ENTE_ACC)){
				excel.addStringColumn(row, cellNum,verbale.getComuneEnteAccertatore());
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.STATO)){
				excel.addStringColumn(row, cellNum,verbale.getStato().getDenominazione());
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.ASSEGNATARIO)){
				if(verbale.getAssegnatario() != null){
					excel.addStringColumn(row, cellNum,verbale.getAssegnatario().getDenominazione());
				}
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.DATA_ORA_ACCERTAMENTO)){
				excel.addStringColumn(row, cellNum,verbale.getDataOraAccertamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.DATA_PROCESSO)){
				excel.addStringColumn(row, cellNum,verbale.getDataProcesso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.TRASGRESSORI)){
				excel.addStringColumn(row, cellNum,verbale.getTrasgressori());
				cellNum++;
			}
			if(columnChecked.containsKey(ReportColumnVO.ColumnName.OBBLIGATI)){
				excel.addStringColumn(row, cellNum,verbale.getObbligati());
				cellNum++;
			} 
			
		// TODO - gestire i documenti
			excel.addStringColumn(row, cellNum,verbale.getDocumenti());
			rowNum++;
		}
		for(int i = 0; i<=cellNum;i++){
			sheet.autoSizeColumn(i);
		}
	}
}
