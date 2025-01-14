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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EstremiXidentificazioneVerDocType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiXidentificazioneVerDocType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EstremiXIdentificazioneUD" type="{}EstremiXIdentificazioneUDType" minOccurs="0"/>
 *         &lt;element name="EstremixIdentificazioneFileUD" type="{}EstremiXIdentificazioneFileUDType"/>
 *         &lt;choice>
 *           &lt;element name="GetSbustato" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *           &lt;element name="GetVersPdf" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *           &lt;element name="Timbro" type="{}TimbroType" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "FileUDToExtract")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXidentificazioneVerDocType", propOrder = {
    "estremiXIdentificazioneUD",
    "estremixIdentificazioneFileUD",
    "getSbustato",
    "getVersPdf",
    "timbro"
})
public class EstremiXidentificazioneVerDocType {

    @XmlElement(name = "EstremiXIdentificazioneUD")
    protected EstremiXIdentificazioneUDType estremiXIdentificazioneUD;
    @XmlElement(name = "EstremixIdentificazioneFileUD", required = true)
    protected EstremiXIdentificazioneFileUDType estremixIdentificazioneFileUD;
    @XmlElement(name = "GetSbustato")
    protected BigInteger getSbustato;
    @XmlElement(name = "GetVersPdf")
    protected BigInteger getVersPdf;
    @XmlElement(name = "Timbro")
    protected TimbroType timbro;

    /**
     * Recupera il valore della propriet� estremiXIdentificazioneUD.
     * 
     * @return
     *     possible object is
     *     {@link EstremiXIdentificazioneUDType }
     *     
     */
    public EstremiXIdentificazioneUDType getEstremiXIdentificazioneUD() {
        return estremiXIdentificazioneUD;
    }

    /**
     * Imposta il valore della propriet� estremiXIdentificazioneUD.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiXIdentificazioneUDType }
     *     
     */
    public void setEstremiXIdentificazioneUD(EstremiXIdentificazioneUDType value) {
        this.estremiXIdentificazioneUD = value;
    }

    /**
     * Recupera il valore della propriet� estremixIdentificazioneFileUD.
     * 
     * @return
     *     possible object is
     *     {@link EstremiXIdentificazioneFileUDType }
     *     
     */
    public EstremiXIdentificazioneFileUDType getEstremixIdentificazioneFileUD() {
        return estremixIdentificazioneFileUD;
    }

    /**
     * Imposta il valore della propriet� estremixIdentificazioneFileUD.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiXIdentificazioneFileUDType }
     *     
     */
    public void setEstremixIdentificazioneFileUD(EstremiXIdentificazioneFileUDType value) {
        this.estremixIdentificazioneFileUD = value;
    }

    /**
     * Recupera il valore della propriet� getSbustato.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGetSbustato() {
        return getSbustato;
    }

    /**
     * Imposta il valore della propriet� getSbustato.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGetSbustato(BigInteger value) {
        this.getSbustato = value;
    }

    /**
     * Recupera il valore della propriet� getVersPdf.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getGetVersPdf() {
        return getVersPdf;
    }

    /**
     * Imposta il valore della propriet� getVersPdf.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setGetVersPdf(BigInteger value) {
        this.getVersPdf = value;
    }

    /**
     * Recupera il valore della propriet� timbro.
     * 
     * @return
     *     possible object is
     *     {@link TimbroType }
     *     
     */
    public TimbroType getTimbro() {
        return timbro;
    }

    /**
     * Imposta il valore della propriet� timbro.
     * 
     * @param value
     *     allowed object is
     *     {@link TimbroType }
     *     
     */
    public void setTimbro(TimbroType value) {
        this.timbro = value;
    }

}
