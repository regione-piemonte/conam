/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.service;

import java.util.List;

import it.csi.conam.conambl.integration.doqui.bean.DocumentiProtocollatiActaPaged;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoActa;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoElettronicoActa;
import it.csi.conam.conambl.integration.doqui.bean.DocumentoProtocollatoActa;
import it.csi.conam.conambl.integration.doqui.bean.KeyDocumentoActa;
import it.csi.conam.conambl.integration.doqui.bean.UtenteActa;
import it.csi.conam.conambl.integration.doqui.exception.IntegrationException;
import it.csi.conam.conambl.integration.doqui.exception.TroppiAllegatiPerSpostamentoException;
import it.doqui.acta.actasrv.dto.acaris.type.common.ObjectIdType;

public interface ActaManagementService {
	
	
	public void init();
	
	public ObjectIdType infoMoveDocument(UtenteActa utenteActa, String objectIdRichiestaPrenotazione) throws IntegrationException;
	
	public KeyDocumentoActa archiviaDocumentoLogico(DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa protocollaDocumentoLogico(DocumentoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa archiviaDocumentoFisico(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa protocollaDocumentoFisico(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa,boolean isProtocollazioneInUscitaSenzaDocumento, String parolaChiaveFolderTemp) throws IntegrationException;
	
	public KeyDocumentoActa protocollaDocumentoFisicoEsistente(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa,boolean isProtocollazioneInUscitaSenzaDocumento, String objectIdClassificazioneEsistente, String objectIdDocumentoEsistente) throws IntegrationException;
	
	public List<DocumentoElettronicoActa> ricercaDocumentoByParolaChiave(String parolaChiave, UtenteActa utenteActa) throws IntegrationException;	// 20200717_LC
	
	public void associaDocumentoFisico(DocumentoElettronicoActa documentoActa, UtenteActa utenteActa) throws IntegrationException;
	
	public KeyDocumentoActa aggiungiAllegato(DocumentoElettronicoActa documentoElettronicoActa, UtenteActa utenteActa, String objectIdClassificazionePadre, String numeroProtocolloPadre, String pkAllegato) throws IntegrationException;
	
	public List<DocumentoProtocollatoActa>  ricercaDocumentoProtocollatoPostSpostaCopia(String numProtocollo, UtenteActa utenteActa, List<String> objectIdDocumentoList) throws IntegrationException;
	
	//20220321_SB modifica per gestione della paginazione nella ricerca
	public DocumentiProtocollatiActaPaged  ricercaDocumentoProtocollatoPaged(String numProtocollo, UtenteActa utenteActa, int pagina, int numeroRigheMax) throws IntegrationException;
	
	// 20200626_LC
	public  KeyDocumentoActa spostaDocumento(DocumentoElettronicoActa documentoElettronicoActa, String numeroProtocollo, UtenteActa utenteActa, String parolaChiaveFolderTemp, boolean offlineRequest) throws IntegrationException, TroppiAllegatiPerSpostamentoException;

	// 20200709_LC
	public void aggiornaPropertiesActaPostSpostamento(String documentoId, String newParolaChiave, String newOggetto, UtenteActa utenteActa) throws IntegrationException;

	// 20200717_LC
	public List<DocumentoElettronicoActa> ricercaDocumentoByObjectIdDocumento(String objectIdDocumento, UtenteActa utenteActa) throws IntegrationException;

	// 20200825_LC
	public List<DocumentoElettronicoActa> ricercaDocumentoByObjectIdDocumentoFisico(String objectIdDocumentoFisico, UtenteActa utenteActa) throws IntegrationException;
	
	
	// 20200728_LC
	public  KeyDocumentoActa copiaDocumento(DocumentoElettronicoActa documentoElettronicoActa, String numeroProtocollo, UtenteActa utenteActa, String parolaChiaveFolderTemp, boolean offlineRequest) throws IntegrationException, TroppiAllegatiPerSpostamentoException;;

	// 20200804_LC
	public String getParolaChiaveByObjectIdDocumento(String objectIdDocumento, UtenteActa utenteActa) throws IntegrationException;
} 
