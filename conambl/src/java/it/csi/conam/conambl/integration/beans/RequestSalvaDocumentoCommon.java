/**
 * RequestSalvaDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public abstract class RequestSalvaDocumentoCommon  implements java.io.Serializable {
    /**
	 * 
	 */
	protected static final long serialVersionUID = -833024566743297195L;

	protected java.lang.String codiceFruitore;

    protected java.lang.String folder;

    protected it.csi.conam.conambl.integration.beans.Metadati metadati;

    protected java.lang.String tipoDocumento;

    // 20200714_LC
    protected String indexType;

    public RequestSalvaDocumentoCommon() {
    }

    public RequestSalvaDocumentoCommon(
        java.lang.String codiceFruitore,
        java.lang.String folder,
        it.csi.conam.conambl.integration.beans.Metadati metadati,
        java.lang.String tipoDocumento,
        java.lang.String indexType) {
           this.codiceFruitore = codiceFruitore;
           this.folder = folder;
           this.metadati = metadati;
           this.tipoDocumento = tipoDocumento;
           this.indexType = indexType;
    }


    /**
     * Gets the codiceFruitore value for this RequestSalvaDocumento.
     * 
     * @return codiceFruitore
     */
    public java.lang.String getCodiceFruitore() {
        return codiceFruitore;
    }


    /**
     * Sets the codiceFruitore value for this RequestSalvaDocumento.
     * 
     * @param codiceFruitore
     */
    public void setCodiceFruitore(java.lang.String codiceFruitore) {
        this.codiceFruitore = codiceFruitore;
    }


    /**
     * Gets the folder value for this RequestSalvaDocumento.
     * 
     * @return folder
     */
    public java.lang.String getFolder() {
        return folder;
    }


    /**
     * Sets the folder value for this RequestSalvaDocumento.
     * 
     * @param folder
     */
    public void setFolder(java.lang.String folder) {
        this.folder = folder;
    }


    /**
     * Gets the metadati value for this RequestSalvaDocumento.
     * 
     * @return metadati
     */
    public it.csi.conam.conambl.integration.beans.Metadati getMetadati() {
        return metadati;
    }


    /**
     * Sets the metadati value for this RequestSalvaDocumento.
     * 
     * @param metadati
     */
    public void setMetadati(it.csi.conam.conambl.integration.beans.Metadati metadati) {
        this.metadati = metadati;
    }


    /**
     * Gets the tipoDocumento value for this RequestSalvaDocumento.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this RequestSalvaDocumento.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
 
    
    /**
     * Gets the indexType value for this RequestRicercaAllegato.
     * 
     * @return indexType
     */
    public String getIndexType() {
        return indexType;
    }


    /**
     * Sets the indexType value for this RequestRicercaAllegato.
     * 
     * @param indexType
     */
    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }


    // Type metadata
    protected static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestSalvaDocumento.class, true);
    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestSalvaDocumentoCommon)) return false;
        RequestSalvaDocumentoCommon other = (RequestSalvaDocumentoCommon) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFruitore==null && other.getCodiceFruitore()==null) || 
             (this.codiceFruitore!=null &&
              this.codiceFruitore.equals(other.getCodiceFruitore()))) &&
            ((this.folder==null && other.getFolder()==null) || 
             (this.folder!=null &&
              this.folder.equals(other.getFolder()))) &&
            ((this.metadati==null && other.getMetadati()==null) || 
             (this.metadati!=null &&
              this.metadati.equals(other.getMetadati()))) &&
            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento()))) &&
            ((this.indexType==null && other.getIndexType()==null) || 
             (this.indexType!=null &&
              this.indexType.equals(other.getIndexType())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCodiceFruitore() != null) {
            _hashCode += getCodiceFruitore().hashCode();
        }
        if (getFolder() != null) {
            _hashCode += getFolder().hashCode();
        }
        if (getMetadati() != null) {
            _hashCode += getMetadati().hashCode();
        }
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
        }
        if (getIndexType() != null) {
            _hashCode += getIndexType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }
    
    
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class<?> _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class<?> _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
