/**
 * RequestArchiviaDocumentoFisico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestArchiviaDocumentoFisico extends RequestArchiviaDocumento {

	private static final long serialVersionUID = -5844294324175575503L;

	
	private java.lang.String autoreGiuridico;

	private java.lang.String destinatarioFisico;

	private java.lang.String destinatarioGiuridico;

	private it.csi.conam.conambl.integration.beans.Documento documento;

	private java.lang.String mimeType;

	private java.lang.String originatore;

	private java.lang.String rootFolder;

	// 20211014
	private java.lang.String dataTopica;
	private java.util.Date dataCronica;


	public RequestArchiviaDocumentoFisico() {
		super();
	}

	public RequestArchiviaDocumentoFisico(java.lang.String applicativoAlimentante, java.lang.String autoreFisico, java.lang.String autoreGiuridico, java.lang.String codiceFruitore,
			java.lang.String destinatarioFisico, java.lang.String destinatarioGiuridico, it.csi.conam.conambl.integration.beans.Documento documento, java.lang.String folder,
			it.csi.conam.conambl.integration.beans.Metadati metadati, java.lang.String mimeType, java.lang.String originatore, java.lang.String rootFolder,
			it.csi.conam.conambl.integration.beans.Soggetto soggetto, java.lang.String tipoDocumento, java.lang.String collocazioneCartacea, java.lang.String dataTopica, java.util.Date dataCronica) {
		
		super(applicativoAlimentante,autoreFisico,codiceFruitore,folder,metadati,soggetto,tipoDocumento,collocazioneCartacea);
	
		this.autoreGiuridico = autoreGiuridico;
		this.destinatarioFisico = destinatarioFisico;
		this.destinatarioGiuridico = destinatarioGiuridico;
		this.mimeType = mimeType;
		this.originatore = originatore;
		this.rootFolder = rootFolder;
		this.dataTopica = dataTopica;
		this.dataCronica = dataCronica;
		this.documento = documento;
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
	 * Gets the documento value for this RequestArchiviaDocumentoFisico.
	 * 
	 * @return documento
	 */
	public it.csi.conam.conambl.integration.beans.Documento getDocumento() {
		return documento;
	}

	/**
	 * Sets the documento value for this RequestArchiviaDocumentoFisico.
	 * 
	 * @param documento
	 */
	public void setDocumento(it.csi.conam.conambl.integration.beans.Documento documento) {
		this.documento = documento;
	}

	/**
	 * Gets the autoreGiuridico value for this RequestArchiviaDocumentoFisico.
	 * 
	 * @return autoreGiuridico
	 */
	public java.lang.String getAutoreGiuridico() {
		return autoreGiuridico;
	}

	/**
	 * Sets the autoreGiuridico value for this RequestArchiviaDocumentoFisico.
	 * 
	 * @param autoreGiuridico
	 */
	public void setAutoreGiuridico(java.lang.String autoreGiuridico) {
		this.autoreGiuridico = autoreGiuridico;
	}

	/**
	 * Gets the destinatarioFisico value for this
	 * RequestArchiviaDocumentoFisico.
	 * 
	 * @return destinatarioFisico
	 */
	public java.lang.String getDestinatarioFisico() {
		return destinatarioFisico;
	}

	/**
	 * Sets the destinatarioFisico value for this
	 * RequestArchiviaDocumentoFisico.
	 * 
	 * @param destinatarioFisico
	 */
	public void setDestinatarioFisico(java.lang.String destinatarioFisico) {
		this.destinatarioFisico = destinatarioFisico;
	}

	/**
	 * Gets the destinatarioGiuridico value for this
	 * RequestArchiviaDocumentoFisico.
	 * 
	 * @return destinatarioGiuridico
	 */
	public java.lang.String getDestinatarioGiuridico() {
		return destinatarioGiuridico;
	}

	/**
	 * Sets the destinatarioGiuridico value for this
	 * RequestArchiviaDocumentoFisico.
	 * 
	 * @param destinatarioGiuridico
	 */
	public void setDestinatarioGiuridico(java.lang.String destinatarioGiuridico) {
		this.destinatarioGiuridico = destinatarioGiuridico;
	}

	/**
	 * Gets the mimeType value for this RequestArchiviaDocumentoFisico.
	 * 
	 * @return mimeType
	 */
	public java.lang.String getMimeType() {
		return mimeType;
	}

	/**
	 * Sets the mimeType value for this RequestArchiviaDocumentoFisico.
	 * 
	 * @param mimeType
	 */
	public void setMimeType(java.lang.String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * Gets the originatore value for this RequestArchiviaDocumentoFisico.
	 * 
	 * @return originatore
	 */
	public java.lang.String getOriginatore() {
		return originatore;
	}

	/**
	 * Sets the originatore value for this RequestArchiviaDocumentoFisico.
	 * 
	 * @param originatore
	 */
	public void setOriginatore(java.lang.String originatore) {
		this.originatore = originatore;
	}

	/**
	 * Gets the rootFolder value for this RequestArchiviaDocumentoFisico.
	 * 
	 * @return rootFolder
	 */
	public java.lang.String getRootFolder() {
		return rootFolder;
	}

	/**
	 * Sets the rootFolder value for this RequestArchiviaDocumentoFisico.
	 * 
	 * @param rootFolder
	 */
	public void setRootFolder(java.lang.String rootFolder) {
		this.rootFolder = rootFolder;
	}

	private java.lang.Object __equalsCalc = null;

	@Override
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof RequestArchiviaDocumentoFisico))
			return false;
		RequestArchiviaDocumentoFisico other = (RequestArchiviaDocumentoFisico) obj;
		/*if (obj == null)
			return false;*/
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj)
				&& ((this.autoreGiuridico == null && other.getAutoreGiuridico() == null) || (this.autoreGiuridico != null && this.autoreGiuridico.equals(other.getAutoreGiuridico())))
				&& ((this.destinatarioFisico == null && other.getDestinatarioFisico() == null) || (this.destinatarioFisico != null && this.destinatarioFisico.equals(other.getDestinatarioFisico())))
				&& ((this.destinatarioGiuridico == null && other.getDestinatarioGiuridico() == null)
						|| (this.destinatarioGiuridico != null && this.destinatarioGiuridico.equals(other.getDestinatarioGiuridico())))
				&& ((this.documento == null && other.getDocumento() == null) || (this.documento != null && this.documento.equals(other.getDocumento())))
				&& ((this.mimeType == null && other.getMimeType() == null) || (this.mimeType != null && this.mimeType.equals(other.getMimeType())))
				&& ((this.originatore == null && other.getOriginatore() == null) || (this.originatore != null && this.originatore.equals(other.getOriginatore())))
				&& ((this.rootFolder == null && other.getRootFolder() == null) || (this.rootFolder != null && this.rootFolder.equals(other.getRootFolder())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;
	@Override
	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = super.hashCode();
		if (getAutoreFisico() != null) {
			_hashCode += getAutoreFisico().hashCode();
		}
		if (getAutoreGiuridico() != null) {
			_hashCode += getAutoreGiuridico().hashCode();
		}
		if (getDestinatarioFisico() != null) {
			_hashCode += getDestinatarioFisico().hashCode();
		}
		if (getDestinatarioGiuridico() != null) {
			_hashCode += getDestinatarioGiuridico().hashCode();
		}
		if (getDocumento() != null) {
			_hashCode += getDocumento().hashCode();
		}
		if (getMimeType() != null) {
			_hashCode += getMimeType().hashCode();
		}
		if (getOriginatore() != null) {
			_hashCode += getOriginatore().hashCode();
		}
		if (getRootFolder() != null) {
			_hashCode += getRootFolder().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}
	
    static {
        typeDesc = new org.apache.axis.description.TypeDesc(RequestArchiviaDocumentoFisico.class, true);
    }
    
	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestArchiviaDocumentoFisico"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
		elemField.setFieldName("originatore");
		elemField.setXmlName(new javax.xml.namespace.QName("", "originatore"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
		elemField.setNillable(true);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("rootFolder");
		elemField.setXmlName(new javax.xml.namespace.QName("", "rootFolder"));
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
		return "RequestArchiviaDocumentoFisico [applicativoAlimentante=" + applicativoAlimentante + ", autoreFisico=" + autoreFisico + ", autoreGiuridico=" + autoreGiuridico + ", codiceFruitore="
				+ codiceFruitore + ", destinatarioFisico=" + destinatarioFisico + ", destinatarioGiuridico=" + destinatarioGiuridico + ", documento=" + documento + ", folder=" + folder + ", metadati="
				+ metadati + ", mimeType=" + mimeType + ", originatore=" + originatore + ", rootFolder=" + rootFolder + ", soggetto=" + soggetto + ", tipoDocumento=" + tipoDocumento + ", dataTopica="	+ dataTopica + ", dataCronica="	+ dataCronica + "]";
	}

}