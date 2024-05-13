/**
 * ResponseArchiviaDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class ResponseArchiviaDocumento  extends ResponseGestioneDocumenti {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3417521236199084820L;

    // 20210804_PP
    private java.lang.String idFolder;

    public ResponseArchiviaDocumento() {
        super();
    }

    public ResponseArchiviaDocumento(
        java.lang.String idDocumento,
        java.lang.String objectIdDocumento) {
            super(idDocumento,objectIdDocumento);
    }

    public java.lang.String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(java.lang.String idFolder) {
		this.idFolder = idFolder;
	}


	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseArchiviaDocumento)) return false;
        ResponseArchiviaDocumento other = (ResponseArchiviaDocumento) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && super.equals(obj) &&
            ((this.idFolder==null && other.getIdFolder()==null) || 
            (this.idFolder!=null &&
            this.idFolder.equals(other.getIdFolder())));
        return _equals;
    }
    
    @Override
    public synchronized int hashCode() {
        int _hashCode = super.hashCode();
        if (getIdFolder() != null) {
            _hashCode += getIdFolder().hashCode();
        }
        return _hashCode;
    }

    // Type metadata
    static {
        typeDesc = new org.apache.axis.description.TypeDesc(ResponseArchiviaDocumento.class, true);
    }

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseArchiviaDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objectIdDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "objectIdDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

}
