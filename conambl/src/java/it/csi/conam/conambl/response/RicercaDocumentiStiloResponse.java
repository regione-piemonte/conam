/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.common.MessageVO;
import it.csi.conam.conambl.vo.verbale.PaginatorVO;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;
import it.csi.conam.conambl.vo.verbale.DocumentoStiloVO;

import java.util.List;


//20200903_LC per gestione messaggio in caso di pregresso
public class RicercaDocumentiStiloResponse extends PaginatorVO {
	
	private static final long serialVersionUID = -4060006032304909549L;
	
	private List<DocumentoStiloVO> documentoStiloVOList;
	
	private MessageVO messaggio;
	
	public List<DocumentoStiloVO> getDocumentoStiloVOList() {
		return documentoStiloVOList;
	}

	public void setDocumentoStiloVOList(List<DocumentoStiloVO> documentoStiloVOList) {
		this.documentoStiloVOList = documentoStiloVOList;
	}

	public MessageVO getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(MessageVO messaggio) {
		this.messaggio = messaggio;
	}
	
	
	
}
