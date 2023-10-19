/**
 * PosizioneDaAggiornareType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 13, 2013 (09:13:21 GMT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class PosizioneDaAggiornareType  implements java.io.Serializable {
    private TipoAggiornamentoType tipoAggiornamento;

    private java.lang.String idPosizioneDebitoria;

    private java.util.Date dataScadenza;

    private java.util.Date dataInizioValidita;

    private java.util.Date dataFineValidita;

    private java.math.BigDecimal importoTotale;

    private java.math.BigDecimal importoPrincipale;

    private java.math.BigDecimal importoSecondarioAltroEnte;

    private ComponenteImportoType[] componentiImporto;

    private java.lang.String descrizioneCausaleVersamento;

    private java.lang.String motivazione;

    private RiferimentoType[] riferimentiPagamento;

    private SoggettoType soggettoPagatore;

    private PosizioneDaAggiornareTypeComponentiImportoSecondario componentiImportoSecondario;

    public PosizioneDaAggiornareType() {
    }

    public PosizioneDaAggiornareType(
           TipoAggiornamentoType tipoAggiornamento,
           java.lang.String idPosizioneDebitoria,
           java.util.Date dataScadenza,
           java.util.Date dataInizioValidita,
           java.util.Date dataFineValidita,
           java.math.BigDecimal importoTotale,
           java.math.BigDecimal importoPrincipale,
           java.math.BigDecimal importoSecondarioAltroEnte,
           ComponenteImportoType[] componentiImporto,
           java.lang.String descrizioneCausaleVersamento,
           java.lang.String motivazione,
           RiferimentoType[] riferimentiPagamento,
           SoggettoType soggettoPagatore,
           PosizioneDaAggiornareTypeComponentiImportoSecondario componentiImportoSecondario) {
           this.tipoAggiornamento = tipoAggiornamento;
           this.idPosizioneDebitoria = idPosizioneDebitoria;
           this.dataScadenza = dataScadenza;
           this.dataInizioValidita = dataInizioValidita;
           this.dataFineValidita = dataFineValidita;
           this.importoTotale = importoTotale;
           this.importoPrincipale = importoPrincipale;
           this.importoSecondarioAltroEnte = importoSecondarioAltroEnte;
           this.componentiImporto = componentiImporto;
           this.descrizioneCausaleVersamento = descrizioneCausaleVersamento;
           this.motivazione = motivazione;
           this.riferimentiPagamento = riferimentiPagamento;
           this.soggettoPagatore = soggettoPagatore;
           this.componentiImportoSecondario = componentiImportoSecondario;
    }


    /**
     * Gets the tipoAggiornamento value for this PosizioneDaAggiornareType.
     * 
     * @return tipoAggiornamento
     */
    public TipoAggiornamentoType getTipoAggiornamento() {
        return tipoAggiornamento;
    }


    /**
     * Sets the tipoAggiornamento value for this PosizioneDaAggiornareType.
     * 
     * @param tipoAggiornamento
     */
    public void setTipoAggiornamento(TipoAggiornamentoType tipoAggiornamento) {
        this.tipoAggiornamento = tipoAggiornamento;
    }


    /**
     * Gets the idPosizioneDebitoria value for this PosizioneDaAggiornareType.
     * 
     * @return idPosizioneDebitoria
     */
    public java.lang.String getIdPosizioneDebitoria() {
        return idPosizioneDebitoria;
    }


    /**
     * Sets the idPosizioneDebitoria value for this PosizioneDaAggiornareType.
     * 
     * @param idPosizioneDebitoria
     */
    public void setIdPosizioneDebitoria(java.lang.String idPosizioneDebitoria) {
        this.idPosizioneDebitoria = idPosizioneDebitoria;
    }


    /**
     * Gets the dataScadenza value for this PosizioneDaAggiornareType.
     * 
     * @return dataScadenza
     */
    public java.util.Date getDataScadenza() {
        return dataScadenza;
    }


    /**
     * Sets the dataScadenza value for this PosizioneDaAggiornareType.
     * 
     * @param dataScadenza
     */
    public void setDataScadenza(java.util.Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }


    /**
     * Gets the dataInizioValidita value for this PosizioneDaAggiornareType.
     * 
     * @return dataInizioValidita
     */
    public java.util.Date getDataInizioValidita() {
        return dataInizioValidita;
    }


    /**
     * Sets the dataInizioValidita value for this PosizioneDaAggiornareType.
     * 
     * @param dataInizioValidita
     */
    public void setDataInizioValidita(java.util.Date dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }


    /**
     * Gets the dataFineValidita value for this PosizioneDaAggiornareType.
     * 
     * @return dataFineValidita
     */
    public java.util.Date getDataFineValidita() {
        return dataFineValidita;
    }


    /**
     * Sets the dataFineValidita value for this PosizioneDaAggiornareType.
     * 
     * @param dataFineValidita
     */
    public void setDataFineValidita(java.util.Date dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }


    /**
     * Gets the importoTotale value for this PosizioneDaAggiornareType.
     * 
     * @return importoTotale
     */
    public java.math.BigDecimal getImportoTotale() {
        return importoTotale;
    }


    /**
     * Sets the importoTotale value for this PosizioneDaAggiornareType.
     * 
     * @param importoTotale
     */
    public void setImportoTotale(java.math.BigDecimal importoTotale) {
        this.importoTotale = importoTotale;
    }


    /**
     * Gets the importoPrincipale value for this PosizioneDaAggiornareType.
     * 
     * @return importoPrincipale
     */
    public java.math.BigDecimal getImportoPrincipale() {
        return importoPrincipale;
    }


    /**
     * Sets the importoPrincipale value for this PosizioneDaAggiornareType.
     * 
     * @param importoPrincipale
     */
    public void setImportoPrincipale(java.math.BigDecimal importoPrincipale) {
        this.importoPrincipale = importoPrincipale;
    }


    /**
     * Gets the importoSecondarioAltroEnte value for this PosizioneDaAggiornareType.
     * 
     * @return importoSecondarioAltroEnte
     */
    public java.math.BigDecimal getImportoSecondarioAltroEnte() {
        return importoSecondarioAltroEnte;
    }


    /**
     * Sets the importoSecondarioAltroEnte value for this PosizioneDaAggiornareType.
     * 
     * @param importoSecondarioAltroEnte
     */
    public void setImportoSecondarioAltroEnte(java.math.BigDecimal importoSecondarioAltroEnte) {
        this.importoSecondarioAltroEnte = importoSecondarioAltroEnte;
    }


    /**
     * Gets the componentiImporto value for this PosizioneDaAggiornareType.
     * 
     * @return componentiImporto
     */
    public ComponenteImportoType[] getComponentiImporto() {
        return componentiImporto;
    }


    /**
     * Sets the componentiImporto value for this PosizioneDaAggiornareType.
     * 
     * @param componentiImporto
     */
    public void setComponentiImporto(ComponenteImportoType[] componentiImporto) {
        this.componentiImporto = componentiImporto;
    }


    /**
     * Gets the descrizioneCausaleVersamento value for this PosizioneDaAggiornareType.
     * 
     * @return descrizioneCausaleVersamento
     */
    public java.lang.String getDescrizioneCausaleVersamento() {
        return descrizioneCausaleVersamento;
    }


    /**
     * Sets the descrizioneCausaleVersamento value for this PosizioneDaAggiornareType.
     * 
     * @param descrizioneCausaleVersamento
     */
    public void setDescrizioneCausaleVersamento(java.lang.String descrizioneCausaleVersamento) {
        this.descrizioneCausaleVersamento = descrizioneCausaleVersamento;
    }


    /**
     * Gets the motivazione value for this PosizioneDaAggiornareType.
     * 
     * @return motivazione
     */
    public java.lang.String getMotivazione() {
        return motivazione;
    }


    /**
     * Sets the motivazione value for this PosizioneDaAggiornareType.
     * 
     * @param motivazione
     */
    public void setMotivazione(java.lang.String motivazione) {
        this.motivazione = motivazione;
    }


    /**
     * Gets the riferimentiPagamento value for this PosizioneDaAggiornareType.
     * 
     * @return riferimentiPagamento
     */
    public RiferimentoType[] getRiferimentiPagamento() {
        return riferimentiPagamento;
    }


    /**
     * Sets the riferimentiPagamento value for this PosizioneDaAggiornareType.
     * 
     * @param riferimentiPagamento
     */
    public void setRiferimentiPagamento(RiferimentoType[] riferimentiPagamento) {
        this.riferimentiPagamento = riferimentiPagamento;
    }


    /**
     * Gets the soggettoPagatore value for this PosizioneDaAggiornareType.
     * 
     * @return soggettoPagatore
     */
    public SoggettoType getSoggettoPagatore() {
        return soggettoPagatore;
    }


    /**
     * Sets the soggettoPagatore value for this PosizioneDaAggiornareType.
     * 
     * @param soggettoPagatore
     */
    public void setSoggettoPagatore(SoggettoType soggettoPagatore) {
        this.soggettoPagatore = soggettoPagatore;
    }


    /**
     * Gets the componentiImportoSecondario value for this PosizioneDaAggiornareType.
     * 
     * @return componentiImportoSecondario
     */
    public PosizioneDaAggiornareTypeComponentiImportoSecondario getComponentiImportoSecondario() {
        return componentiImportoSecondario;
    }


    /**
     * Sets the componentiImportoSecondario value for this PosizioneDaAggiornareType.
     * 
     * @param componentiImportoSecondario
     */
    public void setComponentiImportoSecondario(PosizioneDaAggiornareTypeComponentiImportoSecondario componentiImportoSecondario) {
        this.componentiImportoSecondario = componentiImportoSecondario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PosizioneDaAggiornareType)) return false;
        PosizioneDaAggiornareType other = (PosizioneDaAggiornareType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tipoAggiornamento==null && other.getTipoAggiornamento()==null) || 
             (this.tipoAggiornamento!=null &&
              this.tipoAggiornamento.equals(other.getTipoAggiornamento()))) &&
            ((this.idPosizioneDebitoria==null && other.getIdPosizioneDebitoria()==null) || 
             (this.idPosizioneDebitoria!=null &&
              this.idPosizioneDebitoria.equals(other.getIdPosizioneDebitoria()))) &&
            ((this.dataScadenza==null && other.getDataScadenza()==null) || 
             (this.dataScadenza!=null &&
              this.dataScadenza.equals(other.getDataScadenza()))) &&
            ((this.dataInizioValidita==null && other.getDataInizioValidita()==null) || 
             (this.dataInizioValidita!=null &&
              this.dataInizioValidita.equals(other.getDataInizioValidita()))) &&
            ((this.dataFineValidita==null && other.getDataFineValidita()==null) || 
             (this.dataFineValidita!=null &&
              this.dataFineValidita.equals(other.getDataFineValidita()))) &&
            ((this.importoTotale==null && other.getImportoTotale()==null) || 
             (this.importoTotale!=null &&
              this.importoTotale.equals(other.getImportoTotale()))) &&
            ((this.importoPrincipale==null && other.getImportoPrincipale()==null) || 
             (this.importoPrincipale!=null &&
              this.importoPrincipale.equals(other.getImportoPrincipale()))) &&
            ((this.importoSecondarioAltroEnte==null && other.getImportoSecondarioAltroEnte()==null) || 
             (this.importoSecondarioAltroEnte!=null &&
              this.importoSecondarioAltroEnte.equals(other.getImportoSecondarioAltroEnte()))) &&
            ((this.componentiImporto==null && other.getComponentiImporto()==null) || 
             (this.componentiImporto!=null &&
              java.util.Arrays.equals(this.componentiImporto, other.getComponentiImporto()))) &&
            ((this.descrizioneCausaleVersamento==null && other.getDescrizioneCausaleVersamento()==null) || 
             (this.descrizioneCausaleVersamento!=null &&
              this.descrizioneCausaleVersamento.equals(other.getDescrizioneCausaleVersamento()))) &&
            ((this.motivazione==null && other.getMotivazione()==null) || 
             (this.motivazione!=null &&
              this.motivazione.equals(other.getMotivazione()))) &&
            ((this.riferimentiPagamento==null && other.getRiferimentiPagamento()==null) || 
             (this.riferimentiPagamento!=null &&
              java.util.Arrays.equals(this.riferimentiPagamento, other.getRiferimentiPagamento()))) &&
            ((this.soggettoPagatore==null && other.getSoggettoPagatore()==null) || 
             (this.soggettoPagatore!=null &&
              this.soggettoPagatore.equals(other.getSoggettoPagatore()))) &&
            ((this.componentiImportoSecondario==null && other.getComponentiImportoSecondario()==null) || 
             (this.componentiImportoSecondario!=null &&
              this.componentiImportoSecondario.equals(other.getComponentiImportoSecondario())));
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
        if (getTipoAggiornamento() != null) {
            _hashCode += getTipoAggiornamento().hashCode();
        }
        if (getIdPosizioneDebitoria() != null) {
            _hashCode += getIdPosizioneDebitoria().hashCode();
        }
        if (getDataScadenza() != null) {
            _hashCode += getDataScadenza().hashCode();
        }
        if (getDataInizioValidita() != null) {
            _hashCode += getDataInizioValidita().hashCode();
        }
        if (getDataFineValidita() != null) {
            _hashCode += getDataFineValidita().hashCode();
        }
        if (getImportoTotale() != null) {
            _hashCode += getImportoTotale().hashCode();
        }
        if (getImportoPrincipale() != null) {
            _hashCode += getImportoPrincipale().hashCode();
        }
        if (getImportoSecondarioAltroEnte() != null) {
            _hashCode += getImportoSecondarioAltroEnte().hashCode();
        }
        if (getComponentiImporto() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComponentiImporto());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComponentiImporto(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDescrizioneCausaleVersamento() != null) {
            _hashCode += getDescrizioneCausaleVersamento().hashCode();
        }
        if (getMotivazione() != null) {
            _hashCode += getMotivazione().hashCode();
        }
        if (getRiferimentiPagamento() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRiferimentiPagamento());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRiferimentiPagamento(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSoggettoPagatore() != null) {
            _hashCode += getSoggettoPagatore().hashCode();
        }
        if (getComponentiImportoSecondario() != null) {
            _hashCode += getComponentiImportoSecondario().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PosizioneDaAggiornareType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "PosizioneDaAggiornareType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoAggiornamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "TipoAggiornamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "TipoAggiornamentoType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPosizioneDebitoria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "IdPosizioneDebitoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataScadenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "DataScadenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizioValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "DataInizioValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "DataFineValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoTotale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ImportoTotale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoPrincipale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ImportoPrincipale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importoSecondarioAltroEnte");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ImportoSecondarioAltroEnte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("componentiImporto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ComponentiImporto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ComponenteImportoType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ComponenteImporto"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneCausaleVersamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "DescrizioneCausaleVersamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivazione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "Motivazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimentiPagamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "RiferimentiPagamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "RiferimentoType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "Riferimento"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggettoPagatore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "SoggettoPagatore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "SoggettoType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("componentiImportoSecondario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ComponentiImportoSecondario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", ">PosizioneDaAggiornareType>ComponentiImportoSecondario"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
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
           java.lang.Class _javaType,  
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
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
