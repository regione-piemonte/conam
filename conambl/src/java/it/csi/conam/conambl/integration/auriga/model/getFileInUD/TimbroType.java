//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.07.04 alle 03:42:00 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model.getFileInUD;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per TimbroType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TimbroType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Finalita" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="CONFORMITA_ORIG_CARTACEO"/>
 *               &lt;enumeration value="CONFORMITA_ORIG_DIGITALE"/>
 *               &lt;enumeration value="CERTIFICAZIONE_FIRMA"/>
 *               &lt;enumeration value="VERS_STAMPA_PUBBLICAZIONE"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element name="FlgBustaPdf">
 *             &lt;complexType>
 *               &lt;simpleContent>
 *                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>integer">
 *                   &lt;attribute name="VersPubblicabile" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                 &lt;/extension>
 *               &lt;/simpleContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="OpzioniTimbro">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="PosizioneTimbro">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                           &lt;enumeration value="ALTO_DX"/>
 *                           &lt;enumeration value="ALTO_SN"/>
 *                           &lt;enumeration value="BASSO_SN"/>
 *                           &lt;enumeration value="BASSO_DX"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="RotazioneTimbro">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                           &lt;enumeration value="VERTICALE"/>
 *                           &lt;enumeration value="ORIZZONTALE"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;choice>
 *                       &lt;element name="PaginaTimbro">
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                             &lt;enumeration value="PRIMA"/>
 *                             &lt;enumeration value="ULTIMA"/>
 *                             &lt;enumeration value="TUTTE"/>
 *                             &lt;enumeration value="INTERVALLO"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/element>
 *                       &lt;element name="IntervalloPagine" type="{}IntervalloPagineType"/>
 *                     &lt;/choice>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimbroType", propOrder = {
    "finalita",
    "flgBustaPdf",
    "opzioniTimbro"
})
public class TimbroType {

    @XmlElement(name = "Finalita")
    protected String finalita;
    @XmlElement(name = "FlgBustaPdf")
    protected TimbroType.FlgBustaPdf flgBustaPdf;
    @XmlElement(name = "OpzioniTimbro")
    protected TimbroType.OpzioniTimbro opzioniTimbro;

    /**
     * Recupera il valore della propriet� finalita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinalita() {
        return finalita;
    }

    /**
     * Imposta il valore della propriet� finalita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinalita(String value) {
        this.finalita = value;
    }

    /**
     * Recupera il valore della propriet� flgBustaPdf.
     * 
     * @return
     *     possible object is
     *     {@link TimbroType.FlgBustaPdf }
     *     
     */
    public TimbroType.FlgBustaPdf getFlgBustaPdf() {
        return flgBustaPdf;
    }

    /**
     * Imposta il valore della propriet� flgBustaPdf.
     * 
     * @param value
     *     allowed object is
     *     {@link TimbroType.FlgBustaPdf }
     *     
     */
    public void setFlgBustaPdf(TimbroType.FlgBustaPdf value) {
        this.flgBustaPdf = value;
    }

    /**
     * Recupera il valore della propriet� opzioniTimbro.
     * 
     * @return
     *     possible object is
     *     {@link TimbroType.OpzioniTimbro }
     *     
     */
    public TimbroType.OpzioniTimbro getOpzioniTimbro() {
        return opzioniTimbro;
    }

    /**
     * Imposta il valore della propriet� opzioniTimbro.
     * 
     * @param value
     *     allowed object is
     *     {@link TimbroType.OpzioniTimbro }
     *     
     */
    public void setOpzioniTimbro(TimbroType.OpzioniTimbro value) {
        this.opzioniTimbro = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>integer">
     *       &lt;attribute name="VersPubblicabile" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class FlgBustaPdf {

        @XmlValue
        protected BigInteger value;
        @XmlAttribute(name = "VersPubblicabile")
        protected Boolean versPubblicabile;

        /**
         * Recupera il valore della propriet� value.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getValue() {
            return value;
        }

        /**
         * Imposta il valore della propriet� value.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setValue(BigInteger value) {
            this.value = value;
        }

        /**
         * Recupera il valore della propriet� versPubblicabile.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isVersPubblicabile() {
            if (versPubblicabile == null) {
                return false;
            } else {
                return versPubblicabile;
            }
        }

        /**
         * Imposta il valore della propriet� versPubblicabile.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setVersPubblicabile(Boolean value) {
            this.versPubblicabile = value;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="PosizioneTimbro">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="ALTO_DX"/>
     *               &lt;enumeration value="ALTO_SN"/>
     *               &lt;enumeration value="BASSO_SN"/>
     *               &lt;enumeration value="BASSO_DX"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="RotazioneTimbro">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="VERTICALE"/>
     *               &lt;enumeration value="ORIZZONTALE"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;choice>
     *           &lt;element name="PaginaTimbro">
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                 &lt;enumeration value="PRIMA"/>
     *                 &lt;enumeration value="ULTIMA"/>
     *                 &lt;enumeration value="TUTTE"/>
     *                 &lt;enumeration value="INTERVALLO"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/element>
     *           &lt;element name="IntervalloPagine" type="{}IntervalloPagineType"/>
     *         &lt;/choice>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "posizioneTimbro",
        "rotazioneTimbro",
        "paginaTimbro",
        "intervalloPagine"
    })
    public static class OpzioniTimbro {

        @XmlElement(name = "PosizioneTimbro", required = true)
        protected String posizioneTimbro;
        @XmlElement(name = "RotazioneTimbro", required = true)
        protected String rotazioneTimbro;
        @XmlElement(name = "PaginaTimbro")
        protected String paginaTimbro;
        @XmlElement(name = "IntervalloPagine")
        protected IntervalloPagineType intervalloPagine;

        /**
         * Recupera il valore della propriet� posizioneTimbro.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPosizioneTimbro() {
            return posizioneTimbro;
        }

        /**
         * Imposta il valore della propriet� posizioneTimbro.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPosizioneTimbro(String value) {
            this.posizioneTimbro = value;
        }

        /**
         * Recupera il valore della propriet� rotazioneTimbro.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRotazioneTimbro() {
            return rotazioneTimbro;
        }

        /**
         * Imposta il valore della propriet� rotazioneTimbro.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRotazioneTimbro(String value) {
            this.rotazioneTimbro = value;
        }

        /**
         * Recupera il valore della propriet� paginaTimbro.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPaginaTimbro() {
            return paginaTimbro;
        }

        /**
         * Imposta il valore della propriet� paginaTimbro.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPaginaTimbro(String value) {
            this.paginaTimbro = value;
        }

        /**
         * Recupera il valore della propriet� intervalloPagine.
         * 
         * @return
         *     possible object is
         *     {@link IntervalloPagineType }
         *     
         */
        public IntervalloPagineType getIntervalloPagine() {
            return intervalloPagine;
        }

        /**
         * Imposta il valore della propriet� intervalloPagine.
         * 
         * @param value
         *     allowed object is
         *     {@link IntervalloPagineType }
         *     
         */
        public void setIntervalloPagine(IntervalloPagineType value) {
            this.intervalloPagine = value;
        }

    }

}
