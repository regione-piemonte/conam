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
 * <p>Classe Java per CriterioRicercaSuAttributoAddType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CriterioRicercaSuAttributoAddType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Nome" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OperatoreLogico"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="uguale"/&gt;
 *               &lt;enumeration value="simile a (case-sensitive)"/&gt;
 *               &lt;enumeration value="simile a (case-insensitive)"/&gt;
 *               &lt;enumeration value="minore"/&gt;
 *               &lt;enumeration value="maggiore o uguale"/&gt;
 *               &lt;enumeration value="minore"/&gt;
 *               &lt;enumeration value="minore o uguale"/&gt;
 *               &lt;enumeration value="compreso tra"/&gt;
 *               &lt;enumeration value="non valorizzato"/&gt;
 *               &lt;enumeration value="valorizzato"/&gt;
 *               &lt;enumeration value="spuntato"/&gt;
 *               &lt;enumeration value="non spuntato"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ValoreConfronto_1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ValoreConfronto_2" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CriterioRicercaSuAttributoAddType", propOrder = {
    "nome",
    "operatoreLogico",
    "valoreConfronto1",
    "valoreConfronto2"
})
public class CriterioRicercaSuAttributoAddType {

    @XmlElement(name = "Nome", required = true)
    protected String nome;
    @XmlElement(name = "OperatoreLogico", required = true)
    protected String operatoreLogico;
    @XmlElement(name = "ValoreConfronto_1")
    protected String valoreConfronto1;
    @XmlElement(name = "ValoreConfronto_2")
    protected Object valoreConfronto2;

    /**
     * Recupera il valore della proprietà nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietà nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Recupera il valore della proprietà operatoreLogico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperatoreLogico() {
        return operatoreLogico;
    }

    /**
     * Imposta il valore della proprietà operatoreLogico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperatoreLogico(String value) {
        this.operatoreLogico = value;
    }

    /**
     * Recupera il valore della proprietà valoreConfronto1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValoreConfronto1() {
        return valoreConfronto1;
    }

    /**
     * Imposta il valore della proprietà valoreConfronto1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValoreConfronto1(String value) {
        this.valoreConfronto1 = value;
    }

    /**
     * Recupera il valore della proprietà valoreConfronto2.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getValoreConfronto2() {
        return valoreConfronto2;
    }

    /**
     * Imposta il valore della proprietà valoreConfronto2.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setValoreConfronto2(Object value) {
        this.valoreConfronto2 = value;
    }

}
