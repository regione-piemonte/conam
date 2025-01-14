//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.06.25 alle 02:05:46 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.WSErrorType;



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
 *         &lt;element name="WSResult" type="{}WSResultType"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="WSError">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{}WSErrorType">
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="WarningMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "wsResult",
    "wsError",
    "warningMessage"
})
@XmlRootElement(name = "BaseOutput_WS")
public class BaseOutputWS {

    @XmlElement(name = "WSResult", required = true)
    protected String wsResult;
    @XmlElement(name = "WSError")
    protected BaseOutputWS.WSError wsError;
    @XmlElement(name = "WarningMessage")
    protected String warningMessage;

    /**
     * Recupera il valore della proprietà wsResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWSResult() {
        return wsResult;
    }

    /**
     * Imposta il valore della proprietà wsResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWSResult(String value) {
        this.wsResult = value;
    }

    /**
     * Recupera il valore della proprietà wsError.
     * 
     * @return
     *     possible object is
     *     {@link BaseOutputWS.WSError }
     *     
     */
    public BaseOutputWS.WSError getWSError() {
        return wsError;
    }

    /**
     * Imposta il valore della proprietà wsError.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseOutputWS.WSError }
     *     
     */
    public void setWSError(BaseOutputWS.WSError value) {
        this.wsError = value;
    }

    /**
     * Recupera il valore della proprietà warningMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarningMessage() {
        return warningMessage;
    }

    /**
     * Imposta il valore della proprietà warningMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarningMessage(String value) {
        this.warningMessage = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{}WSErrorType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class WSError
        extends WSErrorType
    {


    }

}
