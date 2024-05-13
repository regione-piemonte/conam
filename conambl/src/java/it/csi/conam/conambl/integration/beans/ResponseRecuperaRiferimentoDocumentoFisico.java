/**
 * ResponseRecuperaRiferimentoDocumentoFisico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class ResponseRecuperaRiferimentoDocumentoFisico extends ResponseOperazioneDocumento {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8761226686930930263L;


    public ResponseRecuperaRiferimentoDocumentoFisico() {
    }

    public ResponseRecuperaRiferimentoDocumentoFisico(
        it.csi.conam.conambl.integration.beans.Context cxt,
        long idRichiesta,
        java.lang.String uuid) {
            super(cxt, idRichiesta, uuid);
    }

    static {
        typeDesc = new org.apache.axis.description.TypeDesc(ResponseRecuperaRiferimentoDocumentoFisico.class, true);
    }

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseRecuperaRiferimentoDocumentoFisico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cxt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cxt"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "Context"));
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
