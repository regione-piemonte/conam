/**
 * RequestSalvaDocumentoLogico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestSalvaDocumentoLogico extends RequestSalvaDocumentoCommon {

	private static final long serialVersionUID = -6660595829803341843L;

    private java.lang.String nomeFile;
    
    public RequestSalvaDocumentoLogico() {
    }

    public RequestSalvaDocumentoLogico(
        java.lang.String codiceFruitore,
        java.lang.String folder,
        it.csi.conam.conambl.integration.beans.Metadati metadati,
        java.lang.String nomeFile,
        java.lang.String tipoDocumento,
        java.lang.String indexType) {
            super(codiceFruitore, folder, metadati, tipoDocumento, indexType);
            this.nomeFile = nomeFile;
    }


    /**
     * Gets the nomeFile value for this RequestSalvaDocumentoLogico.
     * 
     * @return nomeFile
     */
    public java.lang.String getNomeFile() {
        return nomeFile;
    }


    /**
     * Sets the nomeFile value for this RequestSalvaDocumentoLogico.
     * 
     * @param nomeFile
     */
    public void setNomeFile(java.lang.String nomeFile) {
        this.nomeFile = nomeFile;
    }
    

    @Override
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RequestSalvaDocumentoLogico)) return false;
        RequestSalvaDocumentoLogico other = (RequestSalvaDocumentoLogico) obj;
    
        if (this == obj) return true;
    
        return super.equals(obj) &&
                ((this.nomeFile == null && other.getNomeFile() == null) ||
                (this.nomeFile != null &&
                this.nomeFile.equals(other.getNomeFile())));
    }
    
    @Override
    public synchronized int hashCode() {
        int hash = super.hashCode();
        if (getNomeFile() != null) {
            hash += getNomeFile().hashCode();
        }
        return hash;
    }


    static {
        typeDesc = new org.apache.axis.description.TypeDesc(RequestSalvaDocumentoLogico.class, true);
    }

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestSalvaDocumentoLogico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("nomeFile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeFile"));
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
        elemField.setFieldName("indexType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indexType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

}
