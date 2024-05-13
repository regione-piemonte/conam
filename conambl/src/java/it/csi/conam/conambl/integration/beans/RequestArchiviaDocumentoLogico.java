/**
 * RequestArchiviaDocumentoLogico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestArchiviaDocumentoLogico  extends RequestArchiviaDocumento {

	private static final long serialVersionUID = 6965731995622764202L;


    public RequestArchiviaDocumentoLogico() {
        super();
    }

    public RequestArchiviaDocumentoLogico(
        java.lang.String applicativoAlimentante,
        java.lang.String autoreFisico,
        java.lang.String codiceFruitore,
        java.lang.String folder,
        it.csi.conam.conambl.integration.beans.Metadati metadati,
        it.csi.conam.conambl.integration.beans.Soggetto soggetto,
        java.lang.String tipoDocumento, 
        java.lang.String collocazioneCartacea) {

        super(applicativoAlimentante,autoreFisico,codiceFruitore,folder,metadati,soggetto,tipoDocumento, collocazioneCartacea);
    }

    // Type metadata
    static {
        typeDesc = new org.apache.axis.description.TypeDesc(RequestArchiviaDocumentoLogico.class, true);
    }

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestArchiviaDocumentoLogico"));
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
        elemField.setFieldName("codiceFruitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFruitore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
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
    }

}
