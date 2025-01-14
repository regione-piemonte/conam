//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.08.30 alle 02:50:27 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Rappresenta una classificazione e/o un'unità archivistica (UA), vale a dire un fascicolo basato sul titolario di calssificazione e identificato attraverso anno, classificazione, n.ro progressivo (all'interno di anno e classificazione) ed eventuali n.ro di sottofascicolo e di inserto
 * 
 * <p>Classe Java per ClassifUAType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ClassifUAType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AnnoAperturaUA" type="{}AnnoType" minOccurs="0"/&gt;
 *         &lt;element name="LivelloClassificazione" type="{}LivelloGerarchiaType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="NroProgrUA" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="NroSottofasc" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *         &lt;element name="NroInserto" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClassifUAType", propOrder = {
    "annoAperturaUA",
    "livelloClassificazione",
    "nroProgrUA",
    "nroSottofasc",
    "nroInserto"
})
public class ClassifUAType {

    @XmlElement(name = "AnnoAperturaUA")
    @XmlSchemaType(name = "integer")
    protected Integer annoAperturaUA;
    @XmlElement(name = "LivelloClassificazione", required = true)
    protected List<LivelloGerarchiaType> livelloClassificazione;
    @XmlElement(name = "NroProgrUA")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroProgrUA;
    @XmlElement(name = "NroSottofasc")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroSottofasc;
    @XmlElement(name = "NroInserto")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nroInserto;

    /**
     * Recupera il valore della proprietà annoAperturaUA.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoAperturaUA() {
        return annoAperturaUA;
    }

    /**
     * Imposta il valore della proprietà annoAperturaUA.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoAperturaUA(Integer value) {
        this.annoAperturaUA = value;
    }

    /**
     * Gets the value of the livelloClassificazione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the livelloClassificazione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLivelloClassificazione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LivelloGerarchiaType }
     * 
     * 
     */
    public List<LivelloGerarchiaType> getLivelloClassificazione() {
        if (livelloClassificazione == null) {
            livelloClassificazione = new ArrayList<LivelloGerarchiaType>();
        }
        return this.livelloClassificazione;
    }

    /**
     * Recupera il valore della proprietà nroProgrUA.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroProgrUA() {
        return nroProgrUA;
    }

    /**
     * Imposta il valore della proprietà nroProgrUA.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroProgrUA(BigInteger value) {
        this.nroProgrUA = value;
    }

    /**
     * Recupera il valore della proprietà nroSottofasc.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroSottofasc() {
        return nroSottofasc;
    }

    /**
     * Imposta il valore della proprietà nroSottofasc.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroSottofasc(BigInteger value) {
        this.nroSottofasc = value;
    }

    /**
     * Recupera il valore della proprietà nroInserto.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNroInserto() {
        return nroInserto;
    }

    /**
     * Imposta il valore della proprietà nroInserto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNroInserto(BigInteger value) {
        this.nroInserto = value;
    }

}
