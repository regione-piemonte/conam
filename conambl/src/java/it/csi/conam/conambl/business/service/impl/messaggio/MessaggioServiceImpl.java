/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.messaggio;

import it.csi.conam.conambl.business.service.messaggio.MessaggioService;
import it.csi.conam.conambl.common.ErrorCode;
import it.csi.conam.conambl.integration.entity.CnmDMessaggio;
import it.csi.conam.conambl.integration.repositories.CnmDMessaggioRepository;
import it.csi.conam.conambl.vo.common.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author riccardo.bova
 * @date 21 feb 2019
 */
@Service
public class MessaggioServiceImpl implements MessaggioService {

	@Autowired
	private CnmDMessaggioRepository cnmDMessaggioRepository;

	@Override
	public MessageVO getMessaggio(String errorCode) {
		CnmDMessaggio cnmDMessaggio = cnmDMessaggioRepository.findByCodMessaggio(errorCode);
		if(cnmDMessaggio!=null) {
			return new MessageVO(cnmDMessaggio.getDescMessaggio(), cnmDMessaggio.getCnmDTipoMessaggio().getDescTipoMessaggio());
		}else {
			throw new SecurityException("Messaggio non trovato");
		}
	}
}
