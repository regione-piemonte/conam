/**
 * ResponseSalvaDocumentoLogico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class ResponseSalvaDocumentoLogico extends ResponseOperazioneDocumento {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1137119822905615821L;

    private java.lang.String idDocumento;

    public ResponseSalvaDocumentoLogico() {
    }

    public ResponseSalvaDocumentoLogico(
        it.csi.conam.conambl.integration.beans.Context cxt,
        java.lang.String idDocumento,
        long idRichiesta,
        java.lang.String uuid) {
            super(cxt, idRichiesta, uuid);
            this.idDocumento = idDocumento;

    }

    /**
     * Gets the idDocumento value for this ResponseSalvaDocumentoLogico.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdDocumento() {
        return idDocumento;
    }


    /**
     * Sets the idDocumento value for this ResponseSalvaDocumentoLogico.
     * 
     * @param idDocumento
     */
    public void setIdDocumento(java.lang.String idDocumento) {
        this.idDocumento = idDocumento;
    }


    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseSalvaDocumentoLogico)) return false;
        ResponseSalvaDocumentoLogico other = (ResponseSalvaDocumentoLogico) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idDocumento==null && other.getIdDocumento()==null) || 
             (this.idDocumento!=null &&
              this.idDocumento.equals(other.getIdDocumento())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getIdDocumento() != null) {
            _hashCode += getIdDocumento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    static {
        typeDesc = new org.apache.axis.description.TypeDesc(ResponseSalvaDocumentoLogico.class, true);
    }

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseSalvaDocumentoLogico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cxt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cxt"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Context"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRichiesta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRichiesta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uuid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uuid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }
}
