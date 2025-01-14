//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2024.06.19 alle 04:28:10 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model.getDetermina;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per FirmatarioType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FirmatarioType">
 *   &lt;complexContent>
 *     &lt;extension base="{}AutoreType">
 *       &lt;sequence>
 *         &lt;element name="Struttura" type="{}StrutturaType"/>
 *         &lt;element name="TsFirmaVisto" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>dateTime">
 *                 &lt;attribute name="firmaDigitale" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FirmatarioType", propOrder = {
    "struttura",
    "tsFirmaVisto"
})
public class FirmatarioType
    extends AutoreType
{

    @XmlElement(name = "Struttura", required = true)
    protected StrutturaType struttura;
    @XmlElement(name = "TsFirmaVisto")
    protected List<FirmatarioType.TsFirmaVisto> tsFirmaVisto;

    /**
     * Recupera il valore della propriet� struttura.
     * 
     * @return
     *     possible object is
     *     {@link StrutturaType }
     *     
     */
    public StrutturaType getStruttura() {
        return struttura;
    }

    /**
     * Imposta il valore della propriet� struttura.
     * 
     * @param value
     *     allowed object is
     *     {@link StrutturaType }
     *     
     */
    public void setStruttura(StrutturaType value) {
        this.struttura = value;
    }

    /**
     * Gets the value of the tsFirmaVisto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tsFirmaVisto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTsFirmaVisto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FirmatarioType.TsFirmaVisto }
     * 
     * 
     */
    public List<FirmatarioType.TsFirmaVisto> getTsFirmaVisto() {
        if (tsFirmaVisto == null) {
            tsFirmaVisto = new ArrayList<FirmatarioType.TsFirmaVisto>();
        }
        return this.tsFirmaVisto;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>dateTime">
     *       &lt;attribute name="firmaDigitale" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
    public static class TsFirmaVisto {

        @XmlValue
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar value;
        @XmlAttribute(name = "firmaDigitale", required = true)
        protected boolean firmaDigitale;

        /**
         * Recupera il valore della propriet� value.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getValue() {
            return value;
        }

        /**
         * Imposta il valore della propriet� value.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setValue(XMLGregorianCalendar value) {
            this.value = value;
        }

        /**
         * Recupera il valore della propriet� firmaDigitale.
         * 
         */
        public boolean isFirmaDigitale() {
            return firmaDigitale;
        }

        /**
         * Imposta il valore della propriet� firmaDigitale.
         * 
         */
        public void setFirmaDigitale(boolean value) {
            this.firmaDigitale = value;
        }

    }

}
