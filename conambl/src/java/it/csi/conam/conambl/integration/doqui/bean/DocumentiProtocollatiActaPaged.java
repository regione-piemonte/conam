package it.csi.conam.conambl.integration.doqui.bean;

import java.io.Serializable;
import java.util.List;

public class DocumentiProtocollatiActaPaged implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int paginaRichiesta;
	private int numeroRigheMax;
	
	private int numeroRigheTotale;
	private int numeroRigheRestituite;
	
	List<DocumentoProtocollatoActa> documentoProtocollatoActa;
	
	public DocumentiProtocollatiActaPaged() {}

	public int getPaginaRichiesta() {
		return paginaRichiesta;
	}

	public void setPaginaRichiesta(int paginaRichiesta) {
		this.paginaRichiesta = paginaRichiesta;
	}

	public int getNumeroRigheMax() {
		return numeroRigheMax;
	}

	public void setNumeroRigheMax(int numeroRigheMax) {
		this.numeroRigheMax = numeroRigheMax;
	}

	public int getNumeroRigheTotale() {
		return numeroRigheTotale;
	}

	public void setNumeroRigheTotale(int numeroRigheTotale) {
		this.numeroRigheTotale = numeroRigheTotale;
	}

	public int getNumeroRigheRestituite() {
		return numeroRigheRestituite;
	}

	public void setNumeroRigheRestituite(int numeroRigheRestituite) {
		this.numeroRigheRestituite = numeroRigheRestituite;
	}

	public List<DocumentoProtocollatoActa> getDocumentoProtocollatoActa() {
		return documentoProtocollatoActa;
	}
	
	public void setDocumentoProtocollatoActa(List<DocumentoProtocollatoActa> documentoProtocollatoActa) {
		this.documentoProtocollatoActa = documentoProtocollatoActa;
	}

	@Override
	public String toString() {
		return "DocumentiProtocollatiActaPaged [paginaRichiesta=" + paginaRichiesta 
			+ ", numeroRigheMax=" + numeroRigheMax 
			+ ", numeroRigheTotale=" + numeroRigheTotale 
			+ ", numeroRigheRestituite=" + numeroRigheRestituite 
			+ ", documentoProtocollatoActa=" + documentoProtocollatoActa 
			+ "]";
	}


	
	
	
	

}
