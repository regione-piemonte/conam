//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2024.06.19 alle 04:28:10 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model.getDetermina;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="IDDetermina">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TipoNumerazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}gYear"/>
 *                   &lt;element name="Numero">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                         &lt;totalDigits value="7"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SceltaFileRichiesti">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DispositivoAtto" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="VistoRegolaritaContabile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="AllegatiParteIntegranteSeparati" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="AllegatiNonParteIntegrante" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "idDetermina",
    "sceltaFileRichiesti"
})
@XmlRootElement(name = "RequestGetDetermina")
@XmlSeeAlso({RequestGetDetermina.IDDetermina.class, RequestGetDetermina.SceltaFileRichiesti.class})
public class RequestGetDetermina {

    @XmlElement(name = "IDDetermina", required = true)
    protected RequestGetDetermina.IDDetermina idDetermina;
    @XmlElement(name = "SceltaFileRichiesti", required = true)
    protected RequestGetDetermina.SceltaFileRichiesti sceltaFileRichiesti;

    /**
     * Recupera il valore della propriet� idDetermina.
     * 
     * @return
     *     possible object is
     *     {@link RequestGetDetermina.IDDetermina }
     *     
     */
    public RequestGetDetermina.IDDetermina getIDDetermina() {
        return idDetermina;
    }

    /**
     * Imposta il valore della propriet� idDetermina.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestGetDetermina.IDDetermina }
     *     
     */
    public void setIDDetermina(RequestGetDetermina.IDDetermina value) {
        this.idDetermina = value;
    }

    /**
     * Recupera il valore della propriet� sceltaFileRichiesti.
     * 
     * @return
     *     possible object is
     *     {@link RequestGetDetermina.SceltaFileRichiesti }
     *     
     */
    public RequestGetDetermina.SceltaFileRichiesti getSceltaFileRichiesti() {
        return sceltaFileRichiesti;
    }

    /**
     * Imposta il valore della propriet� sceltaFileRichiesti.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestGetDetermina.SceltaFileRichiesti }
     *     
     */
    public void setSceltaFileRichiesti(RequestGetDetermina.SceltaFileRichiesti value) {
        this.sceltaFileRichiesti = value;
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
     *         &lt;element name="TipoNumerazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}gYear"/>
     *         &lt;element name="Numero">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;totalDigits value="7"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
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
        "tipoNumerazione",
        "anno",
        "numero"
    })
    public static class IDDetermina {

        @XmlElement(name = "TipoNumerazione", required = true)
        protected String tipoNumerazione;
        @XmlElement(name = "Anno", required = true)
        //@XmlSchemaType(name = "gYear")
        protected String anno;
        @XmlElement(name = "Numero", required = true)
        protected BigInteger numero;

        /**
         * Recupera il valore della propriet� tipoNumerazione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipoNumerazione() {
            return tipoNumerazione;
        }

        /**
         * Imposta il valore della propriet� tipoNumerazione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipoNumerazione(String value) {
            this.tipoNumerazione = value;
        }

        /**
         * Recupera il valore della propriet� anno.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public String getAnno() {
            return anno;
        }

        /**
         * Imposta il valore della propriet� anno.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setAnno(String value) {
            this.anno = value;
        }

        /**
         * Recupera il valore della propriet� numero.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNumero() {
            return numero;
        }

        /**
         * Imposta il valore della propriet� numero.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNumero(BigInteger value) {
            this.numero = value;
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
     *         &lt;element name="DispositivoAtto" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="VistoRegolaritaContabile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="AllegatiParteIntegranteSeparati" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="AllegatiNonParteIntegrante" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
        "dispositivoAtto",
        "vistoRegolaritaContabile",
        "allegatiParteIntegranteSeparati",
        "allegatiNonParteIntegrante"
    })
    public static class SceltaFileRichiesti {

        @XmlElement(name = "DispositivoAtto", defaultValue = "false")
        protected boolean dispositivoAtto;
        @XmlElement(name = "VistoRegolaritaContabile", defaultValue = "false")
        protected boolean vistoRegolaritaContabile;
        @XmlElement(name = "AllegatiParteIntegranteSeparati", defaultValue = "false")
        protected boolean allegatiParteIntegranteSeparati;
        @XmlElement(name = "AllegatiNonParteIntegrante", defaultValue = "false")
        protected boolean allegatiNonParteIntegrante;

        /**
         * Recupera il valore della propriet� dispositivoAtto.
         * 
         */
        public boolean isDispositivoAtto() {
            return dispositivoAtto;
        }

        /**
         * Imposta il valore della propriet� dispositivoAtto.
         * 
         */
        public void setDispositivoAtto(boolean value) {
            this.dispositivoAtto = value;
        }

        /**
         * Recupera il valore della propriet� vistoRegolaritaContabile.
         * 
         */
        public boolean isVistoRegolaritaContabile() {
            return vistoRegolaritaContabile;
        }

        /**
         * Imposta il valore della propriet� vistoRegolaritaContabile.
         * 
         */
        public void setVistoRegolaritaContabile(boolean value) {
            this.vistoRegolaritaContabile = value;
        }

        /**
         * Recupera il valore della propriet� allegatiParteIntegranteSeparati.
         * 
         */
        public boolean isAllegatiParteIntegranteSeparati() {
            return allegatiParteIntegranteSeparati;
        }

        /**
         * Imposta il valore della propriet� allegatiParteIntegranteSeparati.
         * 
         */
        public void setAllegatiParteIntegranteSeparati(boolean value) {
            this.allegatiParteIntegranteSeparati = value;
        }

        /**
         * Recupera il valore della propriet� allegatiNonParteIntegrante.
         * 
         */
        public boolean isAllegatiNonParteIntegrante() {
            return allegatiNonParteIntegrante;
        }

        /**
         * Imposta il valore della propriet� allegatiNonParteIntegrante.
         * 
         */
        public void setAllegatiNonParteIntegrante(boolean value) {
            this.allegatiNonParteIntegrante = value;
        }

    }

}
