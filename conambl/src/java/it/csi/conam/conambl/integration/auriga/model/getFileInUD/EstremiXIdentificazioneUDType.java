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
import javax.xml.bind.annotation.XmlType;


/**
 * Contiene i dati attraverso cui identificare
 * 				univocamente un'unit� documentaria
 * 
 * <p>Classe Java per EstremiXIdentificazioneUDType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiXIdentificazioneUDType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="IdUD" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="EstremiRegNum" type="{}EstremiRegNumType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXIdentificazioneUDType", propOrder = {
    "idUD",
    "estremiRegNum"
})
public class EstremiXIdentificazioneUDType {

    @XmlElement(name = "IdUD")
    protected BigInteger idUD;
    @XmlElement(name = "EstremiRegNum")
    protected EstremiRegNumType estremiRegNum;

    /**
     * Recupera il valore della propriet� idUD.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdUD() {
        return idUD;
    }

    /**
     * Imposta il valore della propriet� idUD.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdUD(BigInteger value) {
        this.idUD = value;
    }

    /**
     * Recupera il valore della propriet� estremiRegNum.
     * 
     * @return
     *     possible object is
     *     {@link EstremiRegNumType }
     *     
     */
    public EstremiRegNumType getEstremiRegNum() {
        return estremiRegNum;
    }

    /**
     * Imposta il valore della propriet� estremiRegNum.
     * 
     * @param value
     *     allowed object is
     *     {@link EstremiRegNumType }
     *     
     */
    public void setEstremiRegNum(EstremiRegNumType value) {
        this.estremiRegNum = value;
    }

}
