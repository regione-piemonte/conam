/**
 * RequestProtocollaDocumentoLogico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class RequestProtocollaDocumentoLogico extends RequestProtocollaDocumento {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1906092327737416624L;
    
    private java.lang.Integer numeroAllegati;

    private java.lang.String soggettoProduttore;

    public RequestProtocollaDocumentoLogico() {
    }

    public RequestProtocollaDocumentoLogico(
        java.lang.String annoRegistrazionePrecedente,
        java.lang.String applicativoAlimentante,
        java.lang.String codiceFruitore,
        java.lang.String collocazioneCartacea,
        java.lang.String descrizioneTipoLettera,
        java.lang.String destinatarioFisico,
        java.lang.String destinatarioGiuridico,
        java.lang.String folder,
        it.csi.conam.conambl.integration.beans.Metadati metadati,
        java.lang.Integer numeroAllegati,
        java.lang.String numeroRegistrazionePrecedente,
        java.lang.String originatore,
        java.lang.String scrittore,
        it.csi.conam.conambl.integration.beans.Soggetto soggetto,
        java.lang.String soggettoProduttore,
        java.lang.String tipoDocumento) {
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
        this.numeroAllegati = numeroAllegati;
        this.soggettoProduttore = soggettoProduttore;
    }

    /**
     * Gets the numeroAllegati value for this RequestProtocollaDocumentoLogico.
     * 
     * @return numeroAllegati
     */
    public java.lang.Integer getNumeroAllegati() {
        return numeroAllegati;
    }


    /**
     * Sets the numeroAllegati value for this RequestProtocollaDocumentoLogico.
     * 
     * @param numeroAllegati
     */
    public void setNumeroAllegati(java.lang.Integer numeroAllegati) {
        this.numeroAllegati = numeroAllegati;
    }


    /**
     * Gets the soggettoProduttore value for this RequestProtocollaDocumentoLogico.
     * 
     * @return soggettoProduttore
     */
    public java.lang.String getSoggettoProduttore() {
        return soggettoProduttore;
    }


    /**
     * Sets the soggettoProduttore value for this RequestProtocollaDocumentoLogico.
     * 
     * @param soggettoProduttore
     */
    public void setSoggettoProduttore(java.lang.String soggettoProduttore) {
        this.soggettoProduttore = soggettoProduttore;
    }
    
    @Override
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RequestProtocollaDocumentoLogico)) return false;
        RequestProtocollaDocumentoLogico other = (RequestProtocollaDocumentoLogico) obj;

        if (this == obj) return true;
        return super.equals(obj) &&
                ((this.numeroAllegati == null && other.getNumeroAllegati() == null) ||
                (this.numeroAllegati != null &&
                this.numeroAllegati.equals(other.getNumeroAllegati()))) &&
                ((this.soggettoProduttore==null && other.getSoggettoProduttore()==null) || 
                (this.soggettoProduttore!=null &&
                this.soggettoProduttore.equals(other.getSoggettoProduttore()))) ;
    }

    @Override
    public synchronized int hashCode() {
        int hash = super.hashCode();
        if (getNumeroAllegati() != null) {
            hash += getNumeroAllegati().hashCode();
        }
        if (getSoggettoProduttore() != null) {
        	hash += getSoggettoProduttore().hashCode();
        }
        return hash;
    }

    static {
        typeDesc = new org.apache.axis.description.TypeDesc(RequestProtocollaDocumentoLogico.class, true);
    }

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "RequestProtocollaDocumentoLogico"));
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
        elemField.setFieldName("codiceFruitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceFruitore"));
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
        elemField.setFieldName("numeroAllegati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroAllegati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
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
        elemField.setFieldName("soggettoProduttore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soggettoProduttore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

}
