package it.csi.conam.conambl.integration.auriga.service;

import java.util.List;

import it.csi.conam.conambl.integration.auriga.bean.ConamConcreteDocument;
import it.csi.conam.conambl.integration.auriga.bean.ConamDocument;
import it.csi.conam.conambl.integration.auriga.exception.DocumentaleException;
import it.csi.conam.conambl.integration.auriga.model.getDetermina.RequestGetDetermina;
import it.csi.conam.conambl.integration.auriga.service.getAllFilesInFolder.CercaDocumentiByRequest;
import it.csi.conam.conambl.vo.verbale.DocumentoStiloVO;

public interface AurigaService {

    public List<ConamDocument> cercaDocumentiByRequest(CercaDocumentiByRequest request) throws DocumentaleException;
    public ConamConcreteDocument getDocumentById(String idDocument, String operatoreCF) throws DocumentaleException;
    public ConamConcreteDocument getDocumentByIdAndVersion(String idDocument, String version, String idDoc, String operatoreCF, boolean pdfVersion)
			throws DocumentaleException;
    
    public List<DocumentoStiloVO> searchDetermina(RequestGetDetermina getDetermina)
			throws DocumentaleException;
}
