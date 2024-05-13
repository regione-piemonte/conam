/**
 * RequestSalvaDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestSalvaDocumento  extends RequestSalvaDocumentoCommon {

	private static final long serialVersionUID = -7667577545935797195L;

    private it.csi.conam.conambl.integration.beans.Documento documento;

    public RequestSalvaDocumento() {
    }

    public RequestSalvaDocumento(
        java.lang.String codiceFruitore,
        it.csi.conam.conambl.integration.beans.Documento documento,
        java.lang.String folder,
        it.csi.conam.conambl.integration.beans.Metadati metadati,
        java.lang.String tipoDocumento,
        java.lang.String indexType) {
    	super(codiceFruitore, folder, metadati, tipoDocumento, indexType);
            this.documento = documento;
    }


    /**
     * Gets the documento value for this RequestSalvaDocumento.
     * 
     * @return documento
     */
    public it.csi.conam.conambl.integration.beans.Documento getDocumento() {
        return documento;
    }


    /**
     * Sets the documento value for this RequestSalvaDocumento.
     * 
     * @param documento
     */
    public void setDocumento(it.csi.conam.conambl.integration.beans.Documento documento) {
        this.documento = documento;
    }


    @Override
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RequestSalvaDocumento)) return false;
        RequestSalvaDocumento other = (RequestSalvaDocumento) obj;
    
        if (this == obj) return true;
    
        return super.equals(obj) &&
                ((this.documento == null && other.getDocumento() == null) ||
                (this.documento != null &&
                this.documento.equals(other.getDocumento())));
    }
    
    @Override
    public synchronized int hashCode() {
        int hash = super.hashCode();
        if (getDocumento() != null) {
            hash += getDocumento().hashCode();
        }
        return hash;
    }

    static {
        typeDesc = new org.apache.axis.description.TypeDesc(RequestSalvaDocumento.class, true);
    }

    static {
    	typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestSalvaDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFruitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFruitore"));
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
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indexType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indexType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }
}
