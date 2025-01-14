//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.07.02 alle 05:13:52 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model.getFileInUD;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="NomeFile" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="NroVersione" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="NroUltimaVersione" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="NroAllegato" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *               &lt;minInclusive value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TipoDocAllegato" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="CodiceIdentificativo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DesAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "nomeFile",
    "nroVersione",
    "nroUltimaVersione",
    "nroAllegato",
    "tipoDocAllegato",
    "desAllegato"
})
@XmlRootElement(name = "Output_FileUDToExtract")
public class OutputFileUDToExtract {

    @XmlElement(name = "NomeFile", required = true)
    protected String nomeFile; //fix
    @XmlElement(name = "NroVersione", required = true)
    protected Object nroVersione;
    @XmlElement(name = "NroUltimaVersione", required = true)
    protected Object nroUltimaVersione;
    @XmlElement(name = "NroAllegato")
    protected BigInteger nroAllegato;
    @XmlElement(name = "TipoDocAllegato")
    protected OutputFileUDToExtract.TipoDocAllegato tipoDocAllegato;
    @XmlElement(name = "DesAllegato")
    protected String desAllegato;

    /**
     * Recupera il valore della proprietà nomeFile.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public String getNomeFile() {
        return nomeFile;
    }

    /**
     * Imposta il valore della proprietà nomeFile.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNomeFile(String value) {
        this.nomeFile = value;
    }

    /**
     * Recupera il valore della proprietà nroVersione.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNroVersione() {
        return nroVersione;
    }

    /**
     * Imposta il valore della proprietà nroVersione.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNroVersione(Object value) {
        this.nroVersione = value;
    }

    /**
     * Recupera il valore della proprietà nroUltimaVersione.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNroUltimaVersione() {
        return nroUltimaVersione;
    }

    /**
     * Imposta il valore della proprietà nroUltimaVersione.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNroUltimaVersione(Object value) {
        this.nroUltimaVersione = value;
    }

    /**
     * Recupera il valore della proprietà nroAllegato.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroAllegato() {
        return nroAllegato;
    }

    /**
     * Imposta il valore della proprietà nroAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroAllegato(BigInteger value) {
        this.nroAllegato = value;
    }

    /**
     * Recupera il valore della proprietà tipoDocAllegato.
     * 
     * @return
     *     possible object is
     *     {@link OutputFileUDToExtract.TipoDocAllegato }
     *     
     */
    public OutputFileUDToExtract.TipoDocAllegato getTipoDocAllegato() {
        return tipoDocAllegato;
    }

    /**
     * Imposta il valore della proprietà tipoDocAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputFileUDToExtract.TipoDocAllegato }
     *     
     */
    public void setTipoDocAllegato(OutputFileUDToExtract.TipoDocAllegato value) {
        this.tipoDocAllegato = value;
    }

    /**
     * Recupera il valore della proprietà desAllegato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesAllegato() {
        return desAllegato;
    }

    /**
     * Imposta il valore della proprietà desAllegato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesAllegato(String value) {
        this.desAllegato = value;
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
     *       &lt;attribute name="CodiceIdentificativo" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "content"
    })
    public static class TipoDocAllegato {

        @XmlValue
        protected String content;
        @XmlAttribute(name = "CodiceIdentificativo", required = true)
        protected String codiceIdentificativo;

        /**
         * Recupera il valore della proprietà content.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContent() {
            return content;
        }

        /**
         * Imposta il valore della proprietà content.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContent(String value) {
            this.content = value;
        }

        /**
         * Recupera il valore della proprietà codiceIdentificativo.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodiceIdentificativo() {
            return codiceIdentificativo;
        }

        /**
         * Imposta il valore della proprietà codiceIdentificativo.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodiceIdentificativo(String value) {
            this.codiceIdentificativo = value;
        }

    }

}
