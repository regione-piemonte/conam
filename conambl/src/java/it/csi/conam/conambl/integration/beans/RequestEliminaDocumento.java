/**
 * RequestEliminaDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestEliminaDocumento extends RequestOperazioneDocumento  {
    
	private static final long serialVersionUID = -153643581935945401L;

    public RequestEliminaDocumento() {
        super();
    }

    public RequestEliminaDocumento(
        java.lang.String codiceFruitore,
        java.lang.String idDocumento) {

        super(codiceFruitore,idDocumento);
    }

    static {
        typeDesc = new org.apache.axis.description.TypeDesc(RequestEliminaDocumento.class, true);
    }

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestEliminaDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFruitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFruitore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

}
