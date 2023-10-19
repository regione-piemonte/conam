/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.doqui.helper;

import java.util.List;

import it.csi.conam.conambl.integration.beans.RequestRicercaDocumento;
import it.csi.conam.conambl.integration.beans.ResponseRicercaDocumentoMultiplo;
import it.csi.conam.conambl.integration.doqui.exception.RicercaDocumentoException;
import it.csi.conam.conambl.response.RicercaProtocolloSuActaResponse;
import it.csi.conam.conambl.vo.verbale.DocumentoProtocollatoVO;

public interface ManageRicercaDocumentoHelper  extends CommonManageDocumentoHelper{
	
	
	/**
	 * 
	 * @param  RequestRicercaDocumento
	 * @return ResponseRicercaDocumento
	 * @throws RicercaDocumentoException
	 */
	public ResponseRicercaDocumentoMultiplo ricercaDocumento(RequestRicercaDocumento request) throws RicercaDocumentoException;
	
	
	public List<DocumentoProtocollatoVO> ricercaDocumentoProtocollatoPostSpostaCopia(String numProtocollo, String codiceFruitore, List<String> objectIdDocumentoList) throws RicercaDocumentoException;
	
	//20220321_SB modifica per gestione della paginazione nella ricerca
	public RicercaProtocolloSuActaResponse ricercaDocumentoProtocollatoPaged(String numProtocollo, String codiceFruitore, int pagina, int numeroRigheMax) throws RicercaDocumentoException;
	
	// 20200717_LC
	public ResponseRicercaDocumentoMultiplo ricercaDocumentoByObjectIdDocumento(RequestRicercaDocumento request) throws RicercaDocumentoException;
	
	// 20200825_LC
	public ResponseRicercaDocumentoMultiplo ricercaDocumentoByObjectIdDocumentoFisico(RequestRicercaDocumento request) throws RicercaDocumentoException;
	
	public String recuperaInfoMoveDocument(String objectIdRichiestaPrenotazione) throws RicercaDocumentoException ;
	public String recuperaParolaChiave(String objectIdDocumento) throws RicercaDocumentoException ;

}
