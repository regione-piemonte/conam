/**
 * RequestProtocollaDocumentoFisico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public abstract class RequestProtocollaDocumento implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -453634773432305147L;

	protected java.lang.String annoRegistrazionePrecedente;

    protected java.lang.String applicativoAlimentante;

    protected java.lang.String codiceFruitore;

    protected java.lang.String collocazioneCartacea;

    protected java.lang.String descrizioneTipoLettera;

    protected java.lang.String destinatarioFisico;

    protected java.lang.String destinatarioGiuridico;

    protected java.lang.String folder;

    protected it.csi.conam.conambl.integration.beans.Metadati metadati;

    protected java.lang.String numeroRegistrazionePrecedente;

    protected java.lang.String originatore;

    protected java.lang.String scrittore;

    protected it.csi.conam.conambl.integration.beans.Soggetto soggetto;

    protected java.lang.String tipoDocumento;


	public RequestProtocollaDocumento() {
	}

	public RequestProtocollaDocumento(
		java.lang.String annoRegistrazionePrecedente,
		java.lang.String applicativoAlimentante,
		java.lang.String codiceFruitore,
		java.lang.String collocazioneCartacea,
		java.lang.String descrizioneTipoLettera,
		java.lang.String destinatarioFisico,
		java.lang.String destinatarioGiuridico,
		java.lang.String folder,
		it.csi.conam.conambl.integration.beans.Metadati metadati,
		java.lang.String numeroRegistrazionePrecedente,
		java.lang.String originatore,
		java.lang.String scrittore,
		it.csi.conam.conambl.integration.beans.Soggetto soggetto,
		java.lang.String tipoDocumento) {
		this.annoRegistrazionePrecedente = annoRegistrazionePrecedente;
		this.applicativoAlimentante = applicativoAlimentante;
		this.codiceFruitore = codiceFruitore;
		this.collocazioneCartacea = collocazioneCartacea;
		this.descrizioneTipoLettera = descrizioneTipoLettera;
		this.destinatarioFisico = destinatarioFisico;
		this.destinatarioGiuridico = destinatarioGiuridico;
		this.folder = folder;
		this.metadati = metadati;
		this.numeroRegistrazionePrecedente = numeroRegistrazionePrecedente;
		this.originatore = originatore;
		this.scrittore = scrittore;
		this.soggetto = soggetto;
		this.tipoDocumento = tipoDocumento;
}

/**
     * Gets the annoRegistrazionePrecedente value for this RequestProtocollaDocumento.
     * 
     * @return annoRegistrazionePrecedente
     */
    public java.lang.String getAnnoRegistrazionePrecedente() {
        return annoRegistrazionePrecedente;
    }


    /**
     * Sets the annoRegistrazionePrecedente value for this RequestProtocollaDocumento.
     * 
     * @param annoRegistrazionePrecedente
     */
    public void setAnnoRegistrazionePrecedente(java.lang.String annoRegistrazionePrecedente) {
        this.annoRegistrazionePrecedente = annoRegistrazionePrecedente;
    }


    /**
     * Gets the applicativoAlimentante value for this RequestProtocollaDocumento.
     * 
     * @return applicativoAlimentante
     */
    public java.lang.String getApplicativoAlimentante() {
        return applicativoAlimentante;
    }


    /**
     * Sets the applicativoAlimentante value for this RequestProtocollaDocumento.
     * 
     * @param applicativoAlimentante
     */
    public void setApplicativoAlimentante(java.lang.String applicativoAlimentante) {
        this.applicativoAlimentante = applicativoAlimentante;
    }


    /**
     * Gets the codiceFruitore value for this RequestProtocollaDocumento.
     * 
     * @return codiceFruitore
     */
    public java.lang.String getCodiceFruitore() {
        return codiceFruitore;
    }


    /**
     * Sets the codiceFruitore value for this RequestProtocollaDocumento.
     * 
     * @param codiceFruitore
     */
    public void setCodiceFruitore(java.lang.String codiceFruitore) {
        this.codiceFruitore = codiceFruitore;
    }


    /**
     * Gets the collocazioneCartacea value for this RequestProtocollaDocumento.
     * 
     * @return collocazioneCartacea
     */
    public java.lang.String getCollocazioneCartacea() {
        return collocazioneCartacea;
    }


    /**
     * Sets the collocazioneCartacea value for this RequestProtocollaDocumento.
     * 
     * @param collocazioneCartacea
     */
    public void setCollocazioneCartacea(java.lang.String collocazioneCartacea) {
        this.collocazioneCartacea = collocazioneCartacea;
    }


    /**
     * Gets the descrizioneTipoLettera value for this RequestProtocollaDocumento.
     * 
     * @return descrizioneTipoLettera
     */
    public java.lang.String getDescrizioneTipoLettera() {
        return descrizioneTipoLettera;
    }


    /**
     * Sets the descrizioneTipoLettera value for this RequestProtocollaDocumento.
     * 
     * @param descrizioneTipoLettera
     */
    public void setDescrizioneTipoLettera(java.lang.String descrizioneTipoLettera) {
        this.descrizioneTipoLettera = descrizioneTipoLettera;
    }


    /**
     * Gets the destinatarioFisico value for this RequestProtocollaDocumento.
     * 
     * @return destinatarioFisico
     */
    public java.lang.String getDestinatarioFisico() {
        return destinatarioFisico;
    }


    /**
     * Sets the destinatarioFisico value for this RequestProtocollaDocumento.
     * 
     * @param destinatarioFisico
     */
    public void setDestinatarioFisico(java.lang.String destinatarioFisico) {
        this.destinatarioFisico = destinatarioFisico;
    }


    /**
     * Gets the destinatarioGiuridico value for this RequestProtocollaDocumento.
     * 
     * @return destinatarioGiuridico
     */
    public java.lang.String getDestinatarioGiuridico() {
        return destinatarioGiuridico;
    }


    /**
     * Sets the destinatarioGiuridico value for this RequestProtocollaDocumento.
     * 
     * @param destinatarioGiuridico
     */
    public void setDestinatarioGiuridico(java.lang.String destinatarioGiuridico) {
        this.destinatarioGiuridico = destinatarioGiuridico;
    }


    /**
     * Gets the folder value for this RequestProtocollaDocumento.
     * 
     * @return folder
     */
    public java.lang.String getFolder() {
        return folder;
    }


    /**
     * Sets the folder value for this RequestProtocollaDocumento.
     * 
     * @param folder
     */
    public void setFolder(java.lang.String folder) {
        this.folder = folder;
    }


    /**
     * Gets the metadati value for this RequestProtocollaDocumento.
     * 
     * @return metadati
     */
    public it.csi.conam.conambl.integration.beans.Metadati getMetadati() {
        return metadati;
    }


    /**
     * Sets the metadati value for this RequestProtocollaDocumento.
     * 
     * @param metadati
     */
    public void setMetadati(it.csi.conam.conambl.integration.beans.Metadati metadati) {
        this.metadati = metadati;
    }


    /**
     * Gets the numeroRegistrazionePrecedente value for this RequestProtocollaDocumento.
     * 
     * @return numeroRegistrazionePrecedente
     */
    public java.lang.String getNumeroRegistrazionePrecedente() {
        return numeroRegistrazionePrecedente;
    }


    /**
     * Sets the numeroRegistrazionePrecedente value for this RequestProtocollaDocumento.
     * 
     * @param numeroRegistrazionePrecedente
     */
    public void setNumeroRegistrazionePrecedente(java.lang.String numeroRegistrazionePrecedente) {
        this.numeroRegistrazionePrecedente = numeroRegistrazionePrecedente;
    }


    /**
     * Gets the originatore value for this RequestProtocollaDocumento
     * 
     * @return originatore
     */
    public java.lang.String getOriginatore() {
        return originatore;
    }


    /**
     * Sets the originatore value for this RequestProtocollaDocumento
     * 
     * @param originatore
     */
    public void setOriginatore(java.lang.String originatore) {
        this.originatore = originatore;
    }


    /**
     * Gets the scrittore value for this RequestProtocollaDocumento
     * 
     * @return scrittore
     */
    public java.lang.String getScrittore() {
        return scrittore;
    }


    /**
     * Sets the scrittore value for this RequestProtocollaDocumento
     * 
     * @param scrittore
     */
    public void setScrittore(java.lang.String scrittore) {
        this.scrittore = scrittore;
    }


    /**
     * Gets the soggetto value for this RequestProtocollaDocumentp
     * 
     * @return soggetto
     */
    public it.csi.conam.conambl.integration.beans.Soggetto getSoggetto() {
        return soggetto;
    }


    /**
     * Sets the soggetto value for this RequestProtocollaDocumentoLogico.
     * 
     * @param soggetto
     */
    public void setSoggetto(it.csi.conam.conambl.integration.beans.Soggetto soggetto) {
        this.soggetto = soggetto;
    }


    /**
     * Gets the tipoDocumento value for this RequestProtocollaDocumentoLogico.
     * 
     * @return tipoDocumento
     */
    public java.lang.String getTipoDocumento() {
        return tipoDocumento;
    }


    /**
     * Sets the tipoDocumento value for this RequestProtocollaDocumentoLogico.
     * 
     * @param tipoDocumento
     */
    public void setTipoDocumento(java.lang.String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }


	// Type metadata
	protected static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(RequestProtocollaDocumento.class, true);

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestProtocollaDocumento)) return false;
        RequestProtocollaDocumento other = (RequestProtocollaDocumento) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.annoRegistrazionePrecedente==null && other.getAnnoRegistrazionePrecedente()==null) || 
             (this.annoRegistrazionePrecedente!=null &&
              this.annoRegistrazionePrecedente.equals(other.getAnnoRegistrazionePrecedente()))) &&
            ((this.applicativoAlimentante==null && other.getApplicativoAlimentante()==null) || 
             (this.applicativoAlimentante!=null &&
              this.applicativoAlimentante.equals(other.getApplicativoAlimentante()))) &&
            ((this.codiceFruitore==null && other.getCodiceFruitore()==null) || 
             (this.codiceFruitore!=null &&
              this.codiceFruitore.equals(other.getCodiceFruitore()))) &&
            ((this.collocazioneCartacea==null && other.getCollocazioneCartacea()==null) || 
             (this.collocazioneCartacea!=null &&
              this.collocazioneCartacea.equals(other.getCollocazioneCartacea()))) &&
            ((this.descrizioneTipoLettera==null && other.getDescrizioneTipoLettera()==null) || 
             (this.descrizioneTipoLettera!=null &&
              this.descrizioneTipoLettera.equals(other.getDescrizioneTipoLettera()))) &&
            ((this.destinatarioFisico==null && other.getDestinatarioFisico()==null) || 
             (this.destinatarioFisico!=null &&
              this.destinatarioFisico.equals(other.getDestinatarioFisico()))) &&
            ((this.destinatarioGiuridico==null && other.getDestinatarioGiuridico()==null) || 
             (this.destinatarioGiuridico!=null &&
              this.destinatarioGiuridico.equals(other.getDestinatarioGiuridico()))) &&
            ((this.folder==null && other.getFolder()==null) || 
             (this.folder!=null &&
              this.folder.equals(other.getFolder()))) &&
            ((this.metadati==null && other.getMetadati()==null) || 
             (this.metadati!=null &&
              this.metadati.equals(other.getMetadati()))) &&
            ((this.numeroRegistrazionePrecedente==null && other.getNumeroRegistrazionePrecedente()==null) || 
             (this.numeroRegistrazionePrecedente!=null &&
              this.numeroRegistrazionePrecedente.equals(other.getNumeroRegistrazionePrecedente()))) &&
            ((this.originatore==null && other.getOriginatore()==null) || 
             (this.originatore!=null &&
              this.originatore.equals(other.getOriginatore()))) &&
            ((this.scrittore==null && other.getScrittore()==null) || 
             (this.scrittore!=null &&
              this.scrittore.equals(other.getScrittore()))) &&
            ((this.soggetto==null && other.getSoggetto()==null) || 
             (this.soggetto!=null &&
              this.soggetto.equals(other.getSoggetto()))) &&

            ((this.tipoDocumento==null && other.getTipoDocumento()==null) || 
             (this.tipoDocumento!=null &&
              this.tipoDocumento.equals(other.getTipoDocumento())));
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
        if (getAnnoRegistrazionePrecedente() != null) {
            _hashCode += getAnnoRegistrazionePrecedente().hashCode();
        }
        if (getApplicativoAlimentante() != null) {
            _hashCode += getApplicativoAlimentante().hashCode();
        }
        if (getCodiceFruitore() != null) {
            _hashCode += getCodiceFruitore().hashCode();
        }
        if (getCollocazioneCartacea() != null) {
            _hashCode += getCollocazioneCartacea().hashCode();
        }
        if (getDescrizioneTipoLettera() != null) {
            _hashCode += getDescrizioneTipoLettera().hashCode();
        }
        if (getDestinatarioFisico() != null) {
            _hashCode += getDestinatarioFisico().hashCode();
        }
        if (getDestinatarioGiuridico() != null) {
            _hashCode += getDestinatarioGiuridico().hashCode();
        }
        if (getFolder() != null) {
            _hashCode += getFolder().hashCode();
        }
        if (getMetadati() != null) {
            _hashCode += getMetadati().hashCode();
        }
        if (getNumeroRegistrazionePrecedente() != null) {
            _hashCode += getNumeroRegistrazionePrecedente().hashCode();
        }
        if (getOriginatore() != null) {
            _hashCode += getOriginatore().hashCode();
        }
        if (getScrittore() != null) {
            _hashCode += getScrittore().hashCode();
        }
        if (getSoggetto() != null) {
            _hashCode += getSoggetto().hashCode();
        }
  
        if (getTipoDocumento() != null) {
            _hashCode += getTipoDocumento().hashCode();
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
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType, java.lang.Class<?> _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType, java.lang.Class<?> _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
