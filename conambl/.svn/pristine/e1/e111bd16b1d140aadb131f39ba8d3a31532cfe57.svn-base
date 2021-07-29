/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.verbale;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import it.csi.conam.conambl.business.facade.StadocServiceFacade;
import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.business.service.verbale.AllegatoVerbaleSoggettoService;
import it.csi.conam.conambl.common.TipoAllegato;
import it.csi.conam.conambl.common.TipoProtocolloAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.template.DatiTemplateVO;
import it.csi.conam.conambl.vo.verbale.DocumentoScaricatoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author riccardo.bova
 * @date 14 nov 2018
 */
@Service
public class AllegatoVerbaleSoggettoServiceImpl implements AllegatoVerbaleSoggettoService {

	@Autowired
	private CommonAllegatoService commonAllegatoService;
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	@Autowired
	private UtilsDate utilsDate;
	@Autowired
	private CnmRAllegatoVerbSogRepository cnmRAllegatoVerbSogRepository;
	@Autowired
	private CnmRVerbaleSoggettoRepository cnmRVerbaleSoggettoRepository;
	@Autowired
	private UtilsDoqui utilsDoqui;
	@Autowired
	private CnmDTipoAllegatoRepository cnmDTipoAllegatoRepository;
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;

	@Override
	public Boolean isAllegatoVerbaleSoggettoCreato(CnmRVerbaleSoggetto cnmRVerbaleSoggetto, TipoAllegato tipo) {
		List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogList = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggetto(cnmRVerbaleSoggetto);
		if (cnmRAllegatoVerbSogList.isEmpty())
			return Boolean.FALSE;

		Collection<CnmRAllegatoVerbSog> list = Collections2.filter(cnmRAllegatoVerbSogList, new Predicate<CnmRAllegatoVerbSog>() {
			public boolean apply(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) {
				return cnmRAllegatoVerbSog.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == tipo.getId();
			}
		});

		return !(list.size() == 0);
	}

	public Integer getIdVerbaleAudizione(CnmRVerbaleSoggetto cnmRVerbaleSoggetto, TipoAllegato tipo) {
		List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogList = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggetto(cnmRVerbaleSoggetto);
		if (!cnmRAllegatoVerbSogList.isEmpty()) {
			Collection<CnmRAllegatoVerbSog> list = Collections2.filter(cnmRAllegatoVerbSogList, new Predicate<CnmRAllegatoVerbSog>() {
				public boolean apply(CnmRAllegatoVerbSog cnmRAllegatoVerbSog) {
					return cnmRAllegatoVerbSog.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == tipo.getId();
				}
			});

			List<CnmRAllegatoVerbSog> lista = new ArrayList<CnmRAllegatoVerbSog>(list);

			if (list != null && list.size() > 0) {
				List<CnmTAllegato> temp = cnmTAllegatoRepository.findByCnmRAllegatoVerbSogsIn(lista);

				if (temp != null && temp.size() > 0) {
					for (CnmTAllegato a : temp) {
						if (StringUtils.isNotBlank(a.getIdIndex()) && a.getNomeFile().equalsIgnoreCase("Verbale_audizione.pdf") && StringUtils.isBlank(a.getNumeroProtocollo())) {
							return a.getIdAllegato();
						}
					}
				}

			}

		}

		return null;
	}

	@Override
	@Transactional
	public List<CnmTAllegato> salvaVerbaleAudizione(List<Integer> idVerbaleSoggettoList, byte[] file, UserDetails user) {
		if (idVerbaleSoggettoList == null)
			throw new IllegalArgumentException("idVerbaleSoggettoList ==null");
		if (file == null)
			throw new IllegalArgumentException("file==null");

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettoList == null)
			throw new IllegalArgumentException("cnmTOrdinanza ==null");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		List<CnmTAllegato> allegatos = new ArrayList<>();
		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
			if (isAllegatoVerbaleSoggettoCreato(cnmRVerbaleSoggetto, TipoAllegato.VERBALE_AUDIZIONE))
				throw new SecurityException("verbale audizione già  esistente");
			else {

				if (allegatos != null && allegatos.size() == 0) {
					List<CnmTSoggetto> cnmTSoggettoList = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoList);
					allegatos.add(salvaAllegatoVerbaleSoggetto(cnmRVerbaleSoggetto, file, cnmTUser, "Verbale_audizione.pdf", TipoAllegato.VERBALE_AUDIZIONE, false, false, false, cnmTSoggettoList));
				} else
					salvaAllegatoVerbaleSoggetto(cnmRVerbaleSoggetto, allegatos.get(0), cnmTUser);

			}
		}
		return allegatos;
	}

	@Transactional
	public List<CnmTAllegato> salvaConvocazioneAudizione(List<Integer> idVerbaleSoggettoList, byte[] file, UserDetails user) {
		if (idVerbaleSoggettoList == null)
			throw new IllegalArgumentException("idVerbaleSoggettoList ==null");
		if (file == null)
			throw new IllegalArgumentException("file==null");

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettoList == null)
			throw new IllegalArgumentException("cnmTOrdinanza ==null");

		CnmTUser cnmTUser = cnmTUserRepository.findOne(user.getIdUser());
		List<CnmTAllegato> allegatos = new ArrayList<>();
		for (CnmRVerbaleSoggetto cnmRVerbaleSoggetto : cnmRVerbaleSoggettoList) {
			if (isAllegatoVerbaleSoggettoCreato(cnmRVerbaleSoggetto, TipoAllegato.CONVOCAZIONE_AUDIZIONE))
				throw new SecurityException("convocazione audizione già  esistente");
			else {

				if (allegatos != null && allegatos.size() == 0) {
					List<CnmTSoggetto> cnmTSoggettoList = cnmTSoggettoRepository.findByCnmRVerbaleSoggettosIn(cnmRVerbaleSoggettoList);
					String nome = "Convocazione_audizione_" + cnmRVerbaleSoggetto.getCnmTVerbale().getNumVerbale();
					String result = verificaNome(nome) + ".pdf";
					allegatos.add(salvaAllegatoVerbaleSoggetto(cnmRVerbaleSoggetto, file, cnmTUser, result, TipoAllegato.CONVOCAZIONE_AUDIZIONE, true, true, false, cnmTSoggettoList));
				} else
					salvaAllegatoVerbaleSoggetto(cnmRVerbaleSoggetto, allegatos.get(0), cnmTUser);
			}
		}
		return allegatos;
	}

	private CnmTAllegato salvaAllegatoVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto, byte[] file, CnmTUser cnmTUser, String nomeFile, TipoAllegato tipoAllegato, boolean protocolla,
			boolean isProtocollazioneInUscita, boolean isMaster, List<CnmTSoggetto> cnmTSoggettoList) {
		if (cnmRVerbaleSoggetto == null)
			throw new IllegalArgumentException("cnmRVerbaleSoggetto ==null");
		if (nomeFile == null)
			throw new IllegalArgumentException("nomeFile ==null");
		if (cnmTUser == null)
			throw new IllegalArgumentException("cnmTUser ==null");
		if (tipoAllegato == null)
			throw new IllegalArgumentException("tipoAllegato ==null");

		String folder = null;
		String idEntitaFruitore = null;
		TipoProtocolloAllegato tipoProtocolloAllegato = TipoProtocolloAllegato.NON_PROTOCOLLARE;
		String soggettoActa = null;
		String rootActa = null;
		if (protocolla) {
			folder = utilsDoqui.createOrGetfolder(cnmRVerbaleSoggetto);
			idEntitaFruitore = utilsDoqui.createIdEntitaFruitore(cnmRVerbaleSoggetto, cnmDTipoAllegatoRepository.findOne(tipoAllegato.getId()));
			soggettoActa = utilsDoqui.getSoggettoActa(cnmRVerbaleSoggetto);
			tipoProtocolloAllegato = TipoProtocolloAllegato.PROTOCOLLARE;
			rootActa = utilsDoqui.getRootActa(cnmRVerbaleSoggetto);

		}

		CnmTAllegato cnmTAllegato = commonAllegatoService.salvaAllegato(file, nomeFile, tipoAllegato.getId(), null, cnmTUser, tipoProtocolloAllegato, folder, idEntitaFruitore, isMaster,
				isProtocollazioneInUscita, soggettoActa, rootActa, 0, 0, StadocServiceFacade.TIPOLOGIA_DOC_ACTA_DOC_USCITA_SENZA_ALLEGATI_GENERERATI, cnmTSoggettoList);

		// aggiungo alla tabella
		CnmRAllegatoVerbSogPK cnmRAllegatoVerbSogPK = new CnmRAllegatoVerbSogPK();
		cnmRAllegatoVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
		cnmRAllegatoVerbSogPK.setIdVerbaleSoggetto(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());

		CnmRAllegatoVerbSog cnmRAllegatoVerbSog = new CnmRAllegatoVerbSog();
		cnmRAllegatoVerbSog.setId(cnmRAllegatoVerbSogPK);
		cnmRAllegatoVerbSog.setCnmTAllegato(cnmTAllegato);
		cnmRAllegatoVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmRAllegatoVerbSog.setCnmTUser(cnmTUser);
		cnmRAllegatoVerbSogRepository.save(cnmRAllegatoVerbSog);

		return cnmTAllegato;
	}

	private CnmTAllegato salvaAllegatoVerbaleSoggetto(CnmRVerbaleSoggetto cnmRVerbaleSoggetto, CnmTAllegato cnmTAllegato, CnmTUser cnmTUser) {
		if (cnmTAllegato == null)
			throw new IllegalArgumentException("cnmTAllegato ==null");
		if (cnmRVerbaleSoggetto == null)
			throw new IllegalArgumentException("cnmRVerbaleSoggetto ==null");
		if (cnmTUser == null)
			throw new IllegalArgumentException("cnmTUser ==null");

		// aggiungo alla tabella
		CnmRAllegatoVerbSogPK cnmRAllegatoVerbSogPK = new CnmRAllegatoVerbSogPK();
		cnmRAllegatoVerbSogPK.setIdAllegato(cnmTAllegato.getIdAllegato());
		cnmRAllegatoVerbSogPK.setIdVerbaleSoggetto(cnmRVerbaleSoggetto.getIdVerbaleSoggetto());

		CnmRAllegatoVerbSog cnmRAllegatoVerbSog = new CnmRAllegatoVerbSog();
		cnmRAllegatoVerbSog.setId(cnmRAllegatoVerbSogPK);
		cnmRAllegatoVerbSog.setCnmTAllegato(cnmTAllegato);
		cnmRAllegatoVerbSog.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
		cnmRAllegatoVerbSog.setCnmTUser(cnmTUser);
		cnmRAllegatoVerbSogRepository.save(cnmRAllegatoVerbSog);

		return cnmTAllegato;
	}

	@Override
	public List<DocumentoScaricatoVO> downloadVerbaleAudizione(List<Integer> idVerbaleSoggettoList) {
		if (idVerbaleSoggettoList == null)
			throw new IllegalArgumentException("idVerbaleSoggettoList ==null");

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettoList.isEmpty())
			throw new IllegalArgumentException("cnmRVerbaleSoggettoList ==empty");

		List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
		if (cnmRAllegatoVerbSogs.isEmpty())
			throw new IllegalArgumentException("cnmRAllegatoVerbSogs ==empty");

		for (CnmRAllegatoVerbSog avs : cnmRAllegatoVerbSogs) {
			if (avs.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.VERBALE_AUDIZIONE.getId()) {
				return commonAllegatoService.downloadAllegatoById(avs.getCnmTAllegato().getIdAllegato());
			}

		}
		return null;
	}

	@Override
	public List<DocumentoScaricatoVO> downloadConvocazioneAudizione(List<Integer> idVerbaleSoggettoList) {
		if (idVerbaleSoggettoList == null)
			throw new IllegalArgumentException("idVerbaleSoggettoList ==null");

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettoList.isEmpty())
			throw new IllegalArgumentException("cnmRVerbaleSoggettoList ==empty");

		List<CnmRAllegatoVerbSog> cnmRAllegatoVerbSogs = cnmRAllegatoVerbSogRepository.findByCnmRVerbaleSoggettoIn(cnmRVerbaleSoggettoList);
		if (cnmRAllegatoVerbSogs.isEmpty())
			throw new IllegalArgumentException("cnmRAllegatoVerbSogs ==empty");

		for (CnmRAllegatoVerbSog avs : cnmRAllegatoVerbSogs) {
			if (avs.getCnmTAllegato().getCnmDTipoAllegato().getIdTipoAllegato() == TipoAllegato.CONVOCAZIONE_AUDIZIONE.getId()) {
				return commonAllegatoService.downloadAllegatoById(avs.getCnmTAllegato().getIdAllegato());
			}

		}
		return null;

	}

	@Override
	public DatiTemplateVO nomeVerbaleAudizione(List<Integer> idVerbaleSoggettoList) {
		DatiTemplateVO vo = new DatiTemplateVO();
		vo.setNome("Verbale_audizione.pdf");

		return vo;
	}

	@Override
	public DatiTemplateVO nomeConvocazioneAudizione(List<Integer> idVerbaleSoggettoList) {
		DatiTemplateVO vo = new DatiTemplateVO();

		if (idVerbaleSoggettoList == null)
			throw new IllegalArgumentException("idVerbaleSoggettoList ==null");

		List<CnmRVerbaleSoggetto> cnmRVerbaleSoggettoList = (List<CnmRVerbaleSoggetto>) cnmRVerbaleSoggettoRepository.findAll(idVerbaleSoggettoList);
		if (cnmRVerbaleSoggettoList.isEmpty())
			throw new IllegalArgumentException("cnmRVerbaleSoggettoList ==empty");

		String nome = "Convocazione_audizione_" + cnmRVerbaleSoggettoList.get(0).getCnmTVerbale().getNumVerbale();
		vo.setNome(verificaNome(nome) + ".pdf");

		return vo;
	}

	public String verificaNome(String nome) {
		Pattern pattern = Pattern.compile("[^A-Za-z0-9]+");
		String nomePDF = "";

		for (int i = 0; i < nome.length(); i++) {
			Matcher matcher = pattern.matcher("" + nome.charAt(i));
			if (matcher.matches()) {
				nomePDF = nomePDF + "_";
			} else {
				nomePDF = nomePDF + nome.charAt(i);
			}
		}

		return nomePDF;
	}
}
