/**
 * RequestProtocollaDocumentoFisico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestProtocollaDocumentoFisico  extends RequestProtocollaDocumento {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6647569906482005147L;

	private java.lang.String autoreFisico;

	private java.lang.String autoreGiuridico;

	private it.csi.conam.conambl.integration.beans.Documento documento;

	private java.lang.String mimeType;

	private java.lang.String mittentiEsterni;

	private boolean protocollazioneInUscitaSenzaDocumento;

	private java.lang.String rootActa;

	private java.lang.String soggettoActa;


	// 20210506_LC
    private java.lang.String parolaChiaveFolderTemp;

	// 20211014
	private java.lang.String dataTopica;
	private java.util.Date dataCronica;
    

	public RequestProtocollaDocumentoFisico() {
	}

	public RequestProtocollaDocumentoFisico(java.lang.String annoRegistrazionePrecedente, java.lang.String applicativoAlimentante, java.lang.String autoreFisico, java.lang.String autoreGiuridico,
		java.lang.String codiceFruitore, java.lang.String descrizioneTipoLettera, java.lang.String destinatarioFisico, java.lang.String destinatarioGiuridico,
		it.csi.conam.conambl.integration.beans.Documento documento, java.lang.String folder, it.csi.conam.conambl.integration.beans.Metadati metadati, java.lang.String mimeType,
		java.lang.String mittentiEsterni, java.lang.String numeroRegistrazionePrecedente, java.lang.String originatore, boolean protocollazioneInUscitaSenzaDocumento, java.lang.String rootActa,
		java.lang.String scrittore, it.csi.conam.conambl.integration.beans.Soggetto soggetto, java.lang.String soggettoActa, java.lang.String tipoDocumento, java.lang.String collocazioneCartacea, 
		java.lang.String parolaChiaveFolderTemp, java.lang.String dataTopica, java.util.Date dataCronica) {
			super(annoRegistrazionePrecedente,
				applicativoAlimentante,
				codiceFruitore,
				collocazioneCartacea,
				descrizioneTipoLettera,
				destinatarioFisico,
				destinatarioGiuridico,
				folder,
				metadati,
				numeroRegistrazionePrecedente,
				originatore,
				scrittore,
				soggetto,
				tipoDocumento
			);
			this.autoreFisico = autoreFisico;
			this.autoreGiuridico = autoreGiuridico;
			this.documento = documento;
			this.mimeType = mimeType;
			this.mittentiEsterni = mittentiEsterni;
			this.protocollazioneInUscitaSenzaDocumento = protocollazioneInUscitaSenzaDocumento;
			this.rootActa = rootActa;
			this.soggettoActa = soggettoActa;
			this.parolaChiaveFolderTemp = parolaChiaveFolderTemp;
			this.dataTopica = dataTopica;
			this.dataCronica = dataCronica;
	}

	
	/**
	 * Gets the dataTopica value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return dataTopica
	 */
	public java.lang.String getDataTopica() {
		return dataTopica;
	}

	/**
	 * Sets the dataTopica value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param dataTopica
	 */
	public void setDataTopica(java.lang.String dataTopica) {
		this.dataTopica = dataTopica;
	}


	/**
	 * Gets the dataCronica value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return dataCronica
	 */
	public java.util.Date getDataCronica() {
		return dataCronica;
	}


	/**
	 * Sets the dataCronica value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param dataCronica
	 */
	public void setDataCronica(java.util.Date dataCronica) {
		this.dataCronica = dataCronica;
	}


	/**
	 * Gets the autoreFisico value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return autoreFisico
	 */
	public java.lang.String getAutoreFisico() {
		return autoreFisico;
	}


	/**
	 * Sets the autoreFisico value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param autoreFisico
	 */
	public void setAutoreFisico(java.lang.String autoreFisico) {
		this.autoreFisico = autoreFisico;
	}


	/**
	 * Gets the autoreGiuridico value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return autoreGiuridico
	 */
	public java.lang.String getAutoreGiuridico() {
		return autoreGiuridico;
	}


	/**
	 * Sets the autoreGiuridico value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param autoreGiuridico
	 */
	public void setAutoreGiuridico(java.lang.String autoreGiuridico) {
		this.autoreGiuridico = autoreGiuridico;
	}


	/**
	 * Gets the documento value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return documento
	 */
	public it.csi.conam.conambl.integration.beans.Documento getDocumento() {
		return documento;
	}

	/**
	 * Sets the documento value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param documento
	 */
	public void setDocumento(it.csi.conam.conambl.integration.beans.Documento documento) {
		this.documento = documento;
	}


	/**
	 * Gets the mimeType value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return mimeType
	 */
	public java.lang.String getMimeType() {
		return mimeType;
	}

	/**
	 * Sets the mimeType value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param mimeType
	 */
	public void setMimeType(java.lang.String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Gets the mittentiEsterni value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return mittentiEsterni
	 */
	public java.lang.String getMittentiEsterni() {
		return mittentiEsterni;
	}

	/**
	 * Sets the mittentiEsterni value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param mittentiEsterni
	 */
	public void setMittentiEsterni(java.lang.String mittentiEsterni) {
		this.mittentiEsterni = mittentiEsterni;
	}


	/**
	 * Gets the protocollazioneInUscitaSenzaDocumento value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @return protocollazioneInUscitaSenzaDocumento
	 */
	public boolean isProtocollazioneInUscitaSenzaDocumento() {
		return protocollazioneInUscitaSenzaDocumento;
	}

	/**
	 * Sets the protocollazioneInUscitaSenzaDocumento value for this
	 * RequestProtocollaDocumentoFisico.
	 * 
	 * @param protocollazioneInUscitaSenzaDocumento
	 */
	public void setProtocollazioneInUscitaSenzaDocumento(boolean protocollazioneInUscitaSenzaDocumento) {
		this.protocollazioneInUscitaSenzaDocumento = protocollazioneInUscitaSenzaDocumento;
	}

	/**
	 * Gets the rootActa value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return rootActa
	 */
	public java.lang.String getRootActa() {
		return rootActa;
	}

	/**
	 * Sets the rootActa value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param rootActa
	 */
	public void setRootActa(java.lang.String rootActa) {
		this.rootActa = rootActa;
	}


	/**
	 * Gets the soggettoActa value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @return soggettoActa
	 */
	public java.lang.String getSoggettoActa() {
		return soggettoActa;
	}

	/**
	 * Sets the soggettoActa value for this RequestProtocollaDocumentoFisico.
	 * 
	 * @param soggettoActa
	 */
	public void setSoggettoActa(java.lang.String soggettoActa) {
		this.soggettoActa = soggettoActa;
	}


	@Override
	public synchronized boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof RequestProtocollaDocumentoFisico) || !super.equals(obj)) return false;
	    RequestProtocollaDocumentoFisico other = (RequestProtocollaDocumentoFisico) obj;
	    return super.equals(obj) &&
	            (this.autoreFisico == null && other.autoreFisico == null ||
	            (this.autoreFisico != null && this.autoreFisico.equals(other.autoreFisico))) &&
	            (this.autoreGiuridico == null && other.autoreGiuridico == null ||
	            (this.autoreGiuridico != null && this.autoreGiuridico.equals(other.autoreGiuridico))) &&
	            (this.documento == null && other.documento == null ||
	            (this.documento != null && this.documento.equals(other.documento))) &&
	            (this.mimeType == null && other.mimeType == null ||
	            (this.mimeType != null && this.mimeType.equals(other.mimeType))) &&
	            (this.mittentiEsterni == null && other.mittentiEsterni == null ||
	            (this.mittentiEsterni != null && this.mittentiEsterni.equals(other.mittentiEsterni))) &&
	            this.protocollazioneInUscitaSenzaDocumento == other.protocollazioneInUscitaSenzaDocumento &&
	            (this.rootActa == null && other.rootActa == null ||
	            (this.rootActa != null && this.rootActa.equals(other.rootActa))) &&
	            (this.soggettoActa == null && other.soggettoActa == null ||
	            (this.soggettoActa != null && this.soggettoActa.equals(other.soggettoActa))) &&
	            (this.parolaChiaveFolderTemp == null && other.parolaChiaveFolderTemp == null ||
	            (this.parolaChiaveFolderTemp != null && this.parolaChiaveFolderTemp.equals(other.parolaChiaveFolderTemp))) &&
	            (this.dataTopica == null && other.dataTopica == null ||
	            (this.dataTopica != null && this.dataTopica.equals(other.dataTopica))) &&
	            (this.dataCronica == null && other.dataCronica == null ||
	            (this.dataCronica != null && this.dataCronica.equals(other.dataCronica)));
	}
    @Override
    public synchronized int hashCode() {
        int hash = super.hashCode();
		if (getAutoreFisico() != null) {
			hash += getAutoreFisico().hashCode();
		}
		if (getAutoreGiuridico() != null) {
			hash += getAutoreGiuridico().hashCode();
		}
		if (getDocumento() != null) {
			hash += getDocumento().hashCode();
		}
		if (getMimeType() != null) {
			hash += getMimeType().hashCode();
		}
		if (getMittentiEsterni() != null) {
			hash += getMittentiEsterni().hashCode();
		}
		hash += (isProtocollazioneInUscitaSenzaDocumento() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		if (getRootActa() != null) {
			hash += getRootActa().hashCode();
		}
		if (getSoggettoActa() != null) {
			hash += getSoggettoActa().hashCode();
		}
        if (getParolaChiaveFolderTemp() != null) {
        	hash += getParolaChiaveFolderTemp().hashCode();
        }
		return hash;
	}

	// Type metadata
	static {
		typeDesc = new org.apache.axis.description.TypeDesc(RequestProtocollaDocumentoFisico.class, true);
	}

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestProtocollaDocumentoFisico"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("annoRegistrazionePrecedente");
		elemField.setXmlName(new javax.xml.namespace.QName("", "annoRegistrazionePrecedente"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("applicativoAlimentante");
		elemField.setXmlName(new javax.xml.namespace.QName("", "applicativoAlimentante"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("autoreFisico");
		elemField.setXmlName(new javax.xml.namespace.QName("", "autoreFisico"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("autoreGiuridico");
		elemField.setXmlName(new javax.xml.namespace.QName("", "autoreGiuridico"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("codiceFruitore");
		elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFruitore"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("descrizioneTipoLettera");
		elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneTipoLettera"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("destinatarioFisico");
		elemField.setXmlName(new javax.xml.namespace.QName("", "destinatarioFisico"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("destinatarioGiuridico");
		elemField.setXmlName(new javax.xml.namespace.QName("", "destinatarioGiuridico"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("documento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "documento"));
		elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Documento"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("folder");
		elemField.setXmlName(new javax.xml.namespace.QName("", "folder"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("metadati");
		elemField.setXmlName(new javax.xml.namespace.QName("", "metadati"));
		elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Metadati"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("mimeType");
		elemField.setXmlName(new javax.xml.namespace.QName("", "mimeType"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("mittentiEsterni");
		elemField.setXmlName(new javax.xml.namespace.QName("", "mittentiEsterni"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("numeroRegistrazionePrecedente");
		elemField.setXmlName(new javax.xml.namespace.QName("", "numeroRegistrazionePrecedente"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("originatore");
		elemField.setXmlName(new javax.xml.namespace.QName("", "originatore"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("protocollazioneInUscitaSenzaDocumento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "protocollazioneInUscitaSenzaDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("rootActa");
		elemField.setXmlName(new javax.xml.namespace.QName("", "rootActa"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("scrittore");
		elemField.setXmlName(new javax.xml.namespace.QName("", "scrittore"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("soggetto");
		elemField.setXmlName(new javax.xml.namespace.QName("", "soggetto"));
		elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Soggetto"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("soggettoActa");
		elemField.setXmlName(new javax.xml.namespace.QName("", "soggettoActa"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("tipoDocumento");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collocazioneCartacea");
        elemField.setXmlName(new javax.xml.namespace.QName("", "collocazioneCartacea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parolaChiaveFolderTemp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parolaChiaveFolderTemp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dataTopica");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("dataCronica");
		elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "date"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
	}

	@Override
	public String toString() {
		return "RequestProtocollaDocumentoFisico [annoRegistrazionePrecedente=" + annoRegistrazionePrecedente + ", applicativoAlimentante=" + applicativoAlimentante + ", autoreFisico=" + autoreFisico
				+ ", autoreGiuridico=" + autoreGiuridico + ", codiceFruitore=" + codiceFruitore + ", descrizioneTipoLettera=" + descrizioneTipoLettera + ", destinatarioFisico=" + destinatarioFisico
				+ ", destinatarioGiuridico=" + destinatarioGiuridico + ", documento=" + documento + ", folder=" + folder + ", metadati=" + metadati + ", mimeType=" + mimeType + ", mittentiEsterni="
				+ mittentiEsterni + ", numeroRegistrazionePrecedente=" + numeroRegistrazionePrecedente + ", originatore=" + originatore + ", protocollazioneInUscitaSenzaDocumento="
				+ protocollazioneInUscitaSenzaDocumento + ", rootActa=" + rootActa + ", scrittore=" + scrittore + ", soggetto=" + soggetto + ", soggettoActa=" + soggettoActa + ", tipoDocumento="
				+ tipoDocumento + ", dataTopica="	+ dataTopica + ", dataCronica="	+ dataCronica + "]";
	}

	public java.lang.String getParolaChiaveFolderTemp() {
		return parolaChiaveFolderTemp;
	}

	public void setParolaChiaveFolderTemp(java.lang.String parolaChiaveFolderTemp) {
		this.parolaChiaveFolderTemp = parolaChiaveFolderTemp;
	}

}
