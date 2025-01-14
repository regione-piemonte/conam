
package it.csi.conam.conambl.integration.beans;

public abstract class RequestArchiviaDocumento implements  java.io.Serializable {

	private static final long serialVersionUID = -118976532175575503L;
	
	protected java.lang.String applicativoAlimentante;

    protected java.lang.String autoreFisico;

    protected java.lang.String codiceFruitore;

    protected java.lang.String folder;

    protected java.lang.String tipoDocumento;

    protected it.csi.conam.conambl.integration.beans.Metadati metadati;

    protected it.csi.conam.conambl.integration.beans.Soggetto soggetto;

	// 20200731_LC
    protected java.lang.String collocazioneCartacea;
    
	// Type metadata
	protected static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(RequestArchiviaDocumento.class, true);
	
	protected java.lang.String oggetto;
	protected java.lang.String origine;

    public RequestArchiviaDocumento() {

    }

    public RequestArchiviaDocumento(
        java.lang.String applicativoAlimentante,
        java.lang.String autoreFisico,
        java.lang.String codiceFruitore,
        java.lang.String folder,
        it.csi.conam.conambl.integration.beans.Metadati metadati,
        it.csi.conam.conambl.integration.beans.Soggetto soggetto,
        java.lang.String tipoDocumento, 
        java.lang.String collocazioneCartacea) {
        this.applicativoAlimentante = applicativoAlimentante;
        this.autoreFisico = autoreFisico;
        this.codiceFruitore = codiceFruitore;
        this.folder = folder;
        this.metadati = metadati;
        this.soggetto = soggetto;
        this.tipoDocumento = tipoDocumento;
        this.collocazioneCartacea = collocazioneCartacea;
    }

    /**
     * Gets the applicativoAlimentante value for this RequestArchiviaDocumentoLogico.
     * 
     * @return applicativoAlimentante
     */
    public java.lang.String getApplicativoAlimentante() {
        return applicativoAlimentante;
    }

    /**
     * Sets the applicativoAlimentante value for this RequestArchiviaDocumentoLogico.
     * 
     * @param applicativoAlimentante
     */
    public void setApplicativoAlimentante(java.lang.String applicativoAlimentante) {
        this.applicativoAlimentante = applicativoAlimentante;
    }

    /**
     * Gets the autoreFisico value for this RequestArchiviaDocumentoLogico.
     * 
     * @return autoreFisico
     */
    public java.lang.String getAutoreFisico() {
        return autoreFisico;
    }

    /**
     * Sets the autoreFisico value for this RequestArchiviaDocumentoLogico.
     * 
     * @param autoreFisico
     */
    public void setAutoreFisico(java.lang.String autoreFisico) {
        this.autoreFisico = autoreFisico;
    }

    /**
     * Gets the codiceFruitore value for this RequestArchiviaDocumentoLogico.
     * 
     * @return codiceFruitore
     */
    public java.lang.String getCodiceFruitore() {
        return codiceFruitore;
    }

    /**
     * Sets the codiceFruitore value for this RequestArchiviaDocumentoLogico.
     * 
     * @param codiceFruitore
     */
    public void setCodiceFruitore(java.lang.String codiceFruitore) {
        this.codiceFruitore = codiceFruitore;
    }

    /**
     * Gets the folder value for this RequestArchiviaDocumentoLogico.
     * 
     * @return folder
     */
    public java.lang.String getFolder() {
        return folder;
    }

    /**
     * Sets the folder value for this RequestArchiviaDocumentoLogico.
     * 
     * @param folder
     */
    public void setFolder(java.lang.String folder) {
        this.folder = folder;
    }

    /**
     * Gets the metadati value for this RequestArchiviaDocumentoLogico.
     * 
     * @return metadati
     */
    public it.csi.conam.conambl.integration.beans.Metadati getMetadati() {
        return metadati;
    }

    /**
     * Sets the metadati value for this RequestArchiviaDocumentoLogico.
     * 
     * @param metadati
     */
    public void setMetadati(it.csi.conam.conambl.integration.beans.Metadati metadati) {
        this.metadati = metadati;
    }

    /**
     * Gets the soggetto value for this RequestArchiviaDocumentoLogico.
     * 
     * @return soggetto
     */
    public it.csi.conam.conambl.integration.beans.Soggetto getSoggetto() {
        return soggetto;
    }

    /**
     * Sets the soggetto value for this RequestArchiviaDocumentoLogico.
     * 
     * @param soggetto
     */
    public void setSoggetto(it.csi.conam.conambl.integration.beans.Soggetto soggetto) {
        this.soggetto = soggetto;
    }

    /**
     * Gets the tipoDocumento value for this RequestArchiviaDocumentoLogico.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the tipoDocumento value for this RequestArchiviaDocumentoLogico.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * Gets the collocazioneCartacea value for this RequestProtocollaDocumentoLogico.
     * 
     * @return collocazioneCartacea
     */
    public java.lang.String getCollocazioneCartacea() {
        return collocazioneCartacea;
    }

    /**
     * Sets the collocazioneCartacea value for this RequestProtocollaDocumentoLogico.
     * 
     * @param collocazioneCartacea
     */
    public void setCollocazioneCartacea(java.lang.String collocazioneCartacea) {
        this.collocazioneCartacea = collocazioneCartacea;
    }
    

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    
    
    public java.lang.String getOggetto() {
		return oggetto;
	}

	public void setOggetto(java.lang.String oggetto) {
		this.oggetto = oggetto;
	}

	public java.lang.String getOrigine() {
		return origine;
	}

	public void setOrigine(java.lang.String origine) {
		this.origine = origine;
	}

	/**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
        java.lang.String mechType, 
        java.lang.Class<?> _javaType,  
        javax.xml.namespace.QName _xmlType) {
        return new  org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, getTypeDesc());
    }

    /**
    * Get Custom Deserializer
    */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
        java.lang.String mechType, 
        java.lang.Class<?> _javaType,  
        javax.xml.namespace.QName _xmlType) {
        return new  org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, getTypeDesc());
    }
}
