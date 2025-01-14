//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.08.30 alle 02:50:27 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Rappresenta un folder attraverso il suo path (a partire dalla libreria di appartenenza esclusa)+ nome o il suo identificativo
 * 
 * <p>Classe Java per EstremiXIdentificazioneFolderType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EstremiXIdentificazioneFolderType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="IdFolder" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;sequence&gt;
 *           &lt;element name="Libreria" type="{}OggDiTabDiSistemaType" minOccurs="0"/&gt;
 *           &lt;element name="Path_Nome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/sequence&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EstremiXIdentificazioneFolderType", propOrder = {
    "idFolder",
    "libreria",
    "pathNome"
})
@XmlSeeAlso({
    it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.TrovaDocFolder.FiltriPrincipali.CercaInFolder.class
})
public class EstremiXIdentificazioneFolderType {

    @XmlElement(name = "IdFolder")
    protected BigInteger idFolder;
    @XmlElement(name = "Libreria")
    protected OggDiTabDiSistemaType libreria;
    @XmlElement(name = "Path_Nome")
    protected String pathNome;

    /**
     * Recupera il valore della proprietà idFolder.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdFolder() {
        return idFolder;
    }

    /**
     * Imposta il valore della proprietà idFolder.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdFolder(BigInteger value) {
        this.idFolder = value;
    }

    /**
     * Recupera il valore della proprietà libreria.
     * 
     * @return
     *     possible object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public OggDiTabDiSistemaType getLibreria() {
        return libreria;
    }

    /**
     * Imposta il valore della proprietà libreria.
     * 
     * @param value
     *     allowed object is
     *     {@link OggDiTabDiSistemaType }
     *     
     */
    public void setLibreria(OggDiTabDiSistemaType value) {
        this.libreria = value;
    }

    /**
     * Recupera il valore della proprietà pathNome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathNome() {
        return pathNome;
    }

    /**
     * Imposta il valore della proprietà pathNome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathNome(String value) {
        this.pathNome = value;
    }

}
