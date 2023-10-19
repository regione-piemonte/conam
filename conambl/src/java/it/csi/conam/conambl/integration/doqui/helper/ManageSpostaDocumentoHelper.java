/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper;


import java.util.List;

import it.csi.conam.conambl.integration.beans.RequestSpostaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseSpostaDocumento;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.conam.conambl.integration.doqui.bean.UtenteActa;
import it.csi.conam.conambl.integration.doqui.entity.CnmDTipoDocumento;
import it.csi.conam.conambl.integration.doqui.entity.CnmTSpostamentoActa;
import it.csi.conam.conambl.integration.doqui.exception.FruitoreException;
import it.csi.conam.conambl.integration.doqui.exception.SpostaDocumentoException;
import it.csi.conam.conambl.integration.doqui.repositories.CnmTDocumentoRepository;
import it.csi.conam.conambl.integration.entity.CnmDTipoAllegato;
import it.csi.conam.conambl.integration.entity.CnmTAllegato;
import it.csi.conam.conambl.integration.entity.CnmTUser;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;

public interface ManageSpostaDocumentoHelper  extends CommonManageDocumentoHelper{
	// 20200626_LC
	
	public ResponseSpostaDocumento spostaDocumento(RequestSpostaDocumento request) throws SpostaDocumentoException;
	
	// 20200728_LC
	public ResponseSpostaDocumento copiaDocumento(RequestSpostaDocumento request) throws SpostaDocumentoException;
	
	//20200917_ET
	public ResponseSpostaDocumento salvaAllegatoGiaPresenteNelFascicoloActa(DocumentoProtocollatoVO doc, CnmDTipoAllegato cnmDTipoAllegato, CnmTVerbale cnmTVerbale, CnmTUser cnmTUser, List<CnmTAllegato> allegatiGiaSalvati) throws SpostaDocumentoException;
	
	public CnmDTipoDocumento getTipoDocumento(String codice) throws FruitoreException;
	
	public CnmTDocumentoRepository getCnmTDocumentoRepository();
	
	public void changeRiferimentiFornitoreDocumentoPostSpostamento(Integer idDocumento, String uid, String protocollo , String classificazioneId, String registrazioneId, String objectIdClassificazione, String objectIdDocumento, String parolaChiave) throws Exception;
	
	public void updateInfoPostSpostaCopia(CnmTSpostamentoActa cnmTSpostamentoActa, ResponseSpostaDocumento response, boolean operazioneCopia) throws Exception;
	
	public ResponseSpostaDocumento manageSposta (ResponseSpostaDocumento response, DocumentoElettronicoActa documentoElettronicoActa, CnmTSpostamentoActa cnmTSpostamentoActa, UtenteActa utenteActa, String parolaChiaveFolderTemp, boolean isPregresso) throws Exception;
	
	public ResponseSpostaDocumento manageCopia (ResponseSpostaDocumento response, DocumentoElettronicoActa documentoElettronicoActa, CnmTSpostamentoActa cnmTSpostamentoActa, UtenteActa utenteActa, String parolaChiaveFolderTemp, boolean isPregresso) throws Exception;
	
	public void tracciaSuCsiLogAudit(boolean isCopia, boolean isPregresso, List<String> objectIdDocumentoToTraceList);

	void checkUpdateStatusVerbale(CnmTSpostamentoActa cnmTSpostamentoActa, CnmTUser cnmTUser);
}
