//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.08.30 alle 02:50:27 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Rappresenta un oggetto censito in una tabella di sistema del sistema documentale
 * 
 * <p>Classe Java per OggDiTabDiSistemaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="OggDiTabDiSistemaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="CodId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Decodifica_Nome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OggDiTabDiSistemaType", propOrder = {
    "codId",
    "decodificaNome"
})
public class OggDiTabDiSistemaType {

    @XmlElement(name = "CodId")
    protected String codId;
    @XmlElement(name = "Decodifica_Nome")
    protected String decodificaNome;

    /**
     * Recupera il valore della proprietà codId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodId() {
        return codId;
    }

    /**
     * Imposta il valore della proprietà codId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodId(String value) {
        this.codId = value;
    }

    /**
     * Recupera il valore della proprietà decodificaNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecodificaNome() {
        return decodificaNome;
    }

    /**
     * Imposta il valore della proprietà decodificaNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecodificaNome(String value) {
        this.decodificaNome = value;
    }

}
