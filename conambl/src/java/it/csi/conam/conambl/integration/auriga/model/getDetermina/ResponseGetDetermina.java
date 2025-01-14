//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2024.06.19 alle 04:28:10 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model.getDetermina;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
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
 *       &lt;choice>
 *         &lt;element name="Determina">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="IDProposta">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Numero">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                                   &lt;totalDigits value="7"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="TsRegistrazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                             &lt;element name="Autore" type="{}AutoreType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="IDAttoDefinitivo" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Numero">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                                   &lt;totalDigits value="7"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="TsRegistrazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="RifAttiPrecedenti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="OggettoPubbl">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="CIG" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="ConRilevanzaContabile">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="SI"/>
 *                         &lt;enumeration value="NO"/>
 *                         &lt;enumeration value="SI, SENZA VALIDAZIONE/RILASCIO IMPEGNI"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="RegContSuParteCorrente" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="RegContInContoCapitale" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;element name="Specificita" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="a contrarre tramite gara "/>
 *                         &lt;enumeration value="aggiudicazione di gara"/>
 *                         &lt;enumeration value="rimodulazione spesa gara aggiudicata"/>
 *                         &lt;enumeration value="spese di personale"/>
 *                         &lt;enumeration value="riaccertamento"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="StrutturaProponente" type="{}StrutturaType"/>
 *                   &lt;element name="Adottante" type="{}FirmatarioType"/>
 *                   &lt;element name="ResponsabiliDiConcerto" type="{}FirmatarioType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="ResponsabiliPEG" type="{}FirmatarioType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="RdP" type="{}FirmatarioType"/>
 *                   &lt;element name="RUP" type="{}FirmatarioType" minOccurs="0"/>
 *                   &lt;element name="PreverificaRagioneria" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="TsInizio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                             &lt;element name="TsCompletamento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                             &lt;element name="TipoEsito" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="OK"/>
 *                                   &lt;enumeration value="KO"/>
 *                                   &lt;enumeration value="W"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="VistoRegolaritaContabile" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="TsRilascio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                             &lt;element name="Firmatario" type="{}FirmatarioRestrictedType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Pubblicazione" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="DataInizio" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="DataTermine" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="AttachmentFiles" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="File" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="DisplayFilename" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="TipoologiaDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *                                       &lt;element name="Dimensione" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                                       &lt;element name="Digest">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="algoritmo" use="required">
 *                                                 &lt;simpleType>
 *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                     &lt;enumeration value="SHA-1"/>
 *                                                     &lt;enumeration value="SHA-256"/>
 *                                                   &lt;/restriction>
 *                                                 &lt;/simpleType>
 *                                               &lt;/attribute>
 *                                               &lt;attribute name="encoding">
 *                                                 &lt;simpleType>
 *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                     &lt;enumeration value="hex"/>
 *                                                     &lt;enumeration value="base64"/>
 *                                                   &lt;/restriction>
 *                                                 &lt;/simpleType>
 *                                               &lt;/attribute>
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="Mimetype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="Firmato">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>boolean">
 *                                               &lt;attribute name="tipoFirma">
 *                                                 &lt;simpleType>
 *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                     &lt;pattern value="CAdES"/>
 *                                                     &lt;pattern value="PAdES"/>
 *                                                     &lt;pattern value="XAdES"/>
 *                                                   &lt;/restriction>
 *                                                 &lt;/simpleType>
 *                                               &lt;/attribute>
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="nroAttachment" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                                     &lt;attribute name="rappresenta" use="required">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                           &lt;enumeration value="dispositivo"/>
 *                                           &lt;enumeration value="visto_reg_contabile"/>
 *                                           &lt;enumeration value="allegato_parte_integrante"/>
 *                                           &lt;enumeration value="allegato_non_parte_integrante"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ErrorMessage">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="codice" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *       &lt;attribute name="esitoRequest" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="OK"/>
 *             &lt;enumeration value="KO"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "determina",
    "errorMessage"
})
@XmlRootElement(name = "ResponseGetDetermina")
public class ResponseGetDetermina {

    @XmlElement(name = "Determina")
    protected ResponseGetDetermina.Determina determina;
    @XmlElement(name = "ErrorMessage")
    protected ResponseGetDetermina.ErrorMessage errorMessage;
    @XmlAttribute(name = "esitoRequest", required = true)
    protected String esitoRequest;

    /**
     * Recupera il valore della propriet� determina.
     * 
     * @return
     *     possible object is
     *     {@link ResponseGetDetermina.Determina }
     *     
     */
    public ResponseGetDetermina.Determina getDetermina() {
        return determina;
    }

    /**
     * Imposta il valore della propriet� determina.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseGetDetermina.Determina }
     *     
     */
    public void setDetermina(ResponseGetDetermina.Determina value) {
        this.determina = value;
    }

    /**
     * Recupera il valore della propriet� errorMessage.
     * 
     * @return
     *     possible object is
     *     {@link ResponseGetDetermina.ErrorMessage }
     *     
     */
    public ResponseGetDetermina.ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    /**
     * Imposta il valore della propriet� errorMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseGetDetermina.ErrorMessage }
     *     
     */
    public void setErrorMessage(ResponseGetDetermina.ErrorMessage value) {
        this.errorMessage = value;
    }

    /**
     * Recupera il valore della propriet� esitoRequest.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitoRequest() {
        return esitoRequest;
    }

    /**
     * Imposta il valore della propriet� esitoRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitoRequest(String value) {
        this.esitoRequest = value;
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
     *         &lt;element name="IDProposta">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Numero">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *                         &lt;totalDigits value="7"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="TsRegistrazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="Autore" type="{}AutoreType"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="IDAttoDefinitivo" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Numero">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *                         &lt;totalDigits value="7"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="TsRegistrazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="RifAttiPrecedenti" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="OggettoPubbl">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="CIG" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="ConRilevanzaContabile">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="SI"/>
     *               &lt;enumeration value="NO"/>
     *               &lt;enumeration value="SI, SENZA VALIDAZIONE/RILASCIO IMPEGNI"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="RegContSuParteCorrente" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="RegContInContoCapitale" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;element name="Specificita" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="a contrarre tramite gara "/>
     *               &lt;enumeration value="aggiudicazione di gara"/>
     *               &lt;enumeration value="rimodulazione spesa gara aggiudicata"/>
     *               &lt;enumeration value="spese di personale"/>
     *               &lt;enumeration value="riaccertamento"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="StrutturaProponente" type="{}StrutturaType"/>
     *         &lt;element name="Adottante" type="{}FirmatarioType"/>
     *         &lt;element name="ResponsabiliDiConcerto" type="{}FirmatarioType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="ResponsabiliPEG" type="{}FirmatarioType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="RdP" type="{}FirmatarioType"/>
     *         &lt;element name="RUP" type="{}FirmatarioType" minOccurs="0"/>
     *         &lt;element name="PreverificaRagioneria" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="TsInizio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="TsCompletamento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *                   &lt;element name="TipoEsito" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;enumeration value="OK"/>
     *                         &lt;enumeration value="KO"/>
     *                         &lt;enumeration value="W"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="VistoRegolaritaContabile" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="TsRilascio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="Firmatario" type="{}FirmatarioRestrictedType"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Pubblicazione" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="DataInizio" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="DataTermine" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="AttachmentFiles" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="File" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="DisplayFilename" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="TipoologiaDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
     *                             &lt;element name="Dimensione" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
     *                             &lt;element name="Digest">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="algoritmo" use="required">
     *                                       &lt;simpleType>
     *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                           &lt;enumeration value="SHA-1"/>
     *                                           &lt;enumeration value="SHA-256"/>
     *                                         &lt;/restriction>
     *                                       &lt;/simpleType>
     *                                     &lt;/attribute>
     *                                     &lt;attribute name="encoding">
     *                                       &lt;simpleType>
     *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                           &lt;enumeration value="hex"/>
     *                                           &lt;enumeration value="base64"/>
     *                                         &lt;/restriction>
     *                                       &lt;/simpleType>
     *                                     &lt;/attribute>
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="Mimetype" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="Firmato">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>boolean">
     *                                     &lt;attribute name="tipoFirma">
     *                                       &lt;simpleType>
     *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                           &lt;pattern value="CAdES"/>
     *                                           &lt;pattern value="PAdES"/>
     *                                           &lt;pattern value="XAdES"/>
     *                                         &lt;/restriction>
     *                                       &lt;/simpleType>
     *                                     &lt;/attribute>
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="nroAttachment" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *                           &lt;attribute name="rappresenta" use="required">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                 &lt;enumeration value="dispositivo"/>
     *                                 &lt;enumeration value="visto_reg_contabile"/>
     *                                 &lt;enumeration value="allegato_parte_integrante"/>
     *                                 &lt;enumeration value="allegato_non_parte_integrante"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
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
        "idProposta",
        "idAttoDefinitivo",
        "rifAttiPrecedenti",
        "oggettoPubbl",
        "cig",
        "conRilevanzaContabile",
        "regContSuParteCorrente",
        "regContInContoCapitale",
        "specificita",
        "strutturaProponente",
        "adottante",
        "responsabiliDiConcerto",
        "responsabiliPEG",
        "rdP",
        "rup",
        "preverificaRagioneria",
        "vistoRegolaritaContabile",
        "pubblicazione",
        "attachmentFiles"
    })
    public static class Determina {

        @XmlElement(name = "IDProposta", required = true)
        protected ResponseGetDetermina.Determina.IDProposta idProposta;
        @XmlElement(name = "IDAttoDefinitivo")
        protected ResponseGetDetermina.Determina.IDAttoDefinitivo idAttoDefinitivo;
        @XmlElement(name = "RifAttiPrecedenti")
        protected String rifAttiPrecedenti;
        @XmlElement(name = "OggettoPubbl", required = true)
        protected String oggettoPubbl;
        @XmlElement(name = "CIG")
        protected List<String> cig;
        @XmlElement(name = "ConRilevanzaContabile", required = true)
        protected String conRilevanzaContabile;
        @XmlElement(name = "RegContSuParteCorrente")
        protected boolean regContSuParteCorrente;
        @XmlElement(name = "RegContInContoCapitale")
        protected boolean regContInContoCapitale;
        @XmlElement(name = "Specificita")
        protected String specificita;
        @XmlElement(name = "StrutturaProponente", required = true)
        protected StrutturaType strutturaProponente;
        @XmlElement(name = "Adottante", required = true)
        protected FirmatarioType adottante;
        @XmlElement(name = "ResponsabiliDiConcerto")
        protected List<FirmatarioType> responsabiliDiConcerto;
        @XmlElement(name = "ResponsabiliPEG")
        protected List<FirmatarioType> responsabiliPEG;
        @XmlElement(name = "RdP", required = true)
        protected FirmatarioType rdP;
        @XmlElement(name = "RUP")
        protected FirmatarioType rup;
        @XmlElement(name = "PreverificaRagioneria")
        protected ResponseGetDetermina.Determina.PreverificaRagioneria preverificaRagioneria;
        @XmlElement(name = "VistoRegolaritaContabile")
        protected ResponseGetDetermina.Determina.VistoRegolaritaContabile vistoRegolaritaContabile;
        @XmlElement(name = "Pubblicazione")
        protected ResponseGetDetermina.Determina.Pubblicazione pubblicazione;
        @XmlElement(name = "AttachmentFiles")
        protected ResponseGetDetermina.Determina.AttachmentFiles attachmentFiles;

        /**
         * Recupera il valore della propriet� idProposta.
         * 
         * @return
         *     possible object is
         *     {@link ResponseGetDetermina.Determina.IDProposta }
         *     
         */
        public ResponseGetDetermina.Determina.IDProposta getIDProposta() {
            return idProposta;
        }

        /**
         * Imposta il valore della propriet� idProposta.
         * 
         * @param value
         *     allowed object is
         *     {@link ResponseGetDetermina.Determina.IDProposta }
         *     
         */
        public void setIDProposta(ResponseGetDetermina.Determina.IDProposta value) {
            this.idProposta = value;
        }

        /**
         * Recupera il valore della propriet� idAttoDefinitivo.
         * 
         * @return
         *     possible object is
         *     {@link ResponseGetDetermina.Determina.IDAttoDefinitivo }
         *     
         */
        public ResponseGetDetermina.Determina.IDAttoDefinitivo getIDAttoDefinitivo() {
            return idAttoDefinitivo;
        }

        /**
         * Imposta il valore della propriet� idAttoDefinitivo.
         * 
         * @param value
         *     allowed object is
         *     {@link ResponseGetDetermina.Determina.IDAttoDefinitivo }
         *     
         */
        public void setIDAttoDefinitivo(ResponseGetDetermina.Determina.IDAttoDefinitivo value) {
            this.idAttoDefinitivo = value;
        }

        /**
         * Recupera il valore della propriet� rifAttiPrecedenti.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRifAttiPrecedenti() {
            return rifAttiPrecedenti;
        }

        /**
         * Imposta il valore della propriet� rifAttiPrecedenti.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRifAttiPrecedenti(String value) {
            this.rifAttiPrecedenti = value;
        }

        /**
         * Recupera il valore della propriet� oggettoPubbl.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOggettoPubbl() {
            return oggettoPubbl;
        }

        /**
         * Imposta il valore della propriet� oggettoPubbl.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOggettoPubbl(String value) {
            this.oggettoPubbl = value;
        }

        /**
         * Gets the value of the cig property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cig property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCIG().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getCIG() {
            if (cig == null) {
                cig = new ArrayList<String>();
            }
            return this.cig;
        }

        /**
         * Recupera il valore della propriet� conRilevanzaContabile.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConRilevanzaContabile() {
            return conRilevanzaContabile;
        }

        /**
         * Imposta il valore della propriet� conRilevanzaContabile.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConRilevanzaContabile(String value) {
            this.conRilevanzaContabile = value;
        }

        /**
         * Recupera il valore della propriet� regContSuParteCorrente.
         * 
         */
        public boolean isRegContSuParteCorrente() {
            return regContSuParteCorrente;
        }

        /**
         * Imposta il valore della propriet� regContSuParteCorrente.
         * 
         */
        public void setRegContSuParteCorrente(boolean value) {
            this.regContSuParteCorrente = value;
        }

        /**
         * Recupera il valore della propriet� regContInContoCapitale.
         * 
         */
        public boolean isRegContInContoCapitale() {
            return regContInContoCapitale;
        }

        /**
         * Imposta il valore della propriet� regContInContoCapitale.
         * 
         */
        public void setRegContInContoCapitale(boolean value) {
            this.regContInContoCapitale = value;
        }

        /**
         * Recupera il valore della propriet� specificita.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpecificita() {
            return specificita;
        }

        /**
         * Imposta il valore della propriet� specificita.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpecificita(String value) {
            this.specificita = value;
        }

        /**
         * Recupera il valore della propriet� strutturaProponente.
         * 
         * @return
         *     possible object is
         *     {@link StrutturaType }
         *     
         */
        public StrutturaType getStrutturaProponente() {
            return strutturaProponente;
        }

        /**
         * Imposta il valore della propriet� strutturaProponente.
         * 
         * @param value
         *     allowed object is
         *     {@link StrutturaType }
         *     
         */
        public void setStrutturaProponente(StrutturaType value) {
            this.strutturaProponente = value;
        }

        /**
         * Recupera il valore della propriet� adottante.
         * 
         * @return
         *     possible object is
         *     {@link FirmatarioType }
         *     
         */
        public FirmatarioType getAdottante() {
            return adottante;
        }

        /**
         * Imposta il valore della propriet� adottante.
         * 
         * @param value
         *     allowed object is
         *     {@link FirmatarioType }
         *     
         */
        public void setAdottante(FirmatarioType value) {
            this.adottante = value;
        }

        /**
         * Gets the value of the responsabiliDiConcerto property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the responsabiliDiConcerto property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getResponsabiliDiConcerto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FirmatarioType }
         * 
         * 
         */
        public List<FirmatarioType> getResponsabiliDiConcerto() {
            if (responsabiliDiConcerto == null) {
                responsabiliDiConcerto = new ArrayList<FirmatarioType>();
            }
            return this.responsabiliDiConcerto;
        }

        /**
         * Gets the value of the responsabiliPEG property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the responsabiliPEG property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getResponsabiliPEG().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FirmatarioType }
         * 
         * 
         */
        public List<FirmatarioType> getResponsabiliPEG() {
            if (responsabiliPEG == null) {
                responsabiliPEG = new ArrayList<FirmatarioType>();
            }
            return this.responsabiliPEG;
        }

        /**
         * Recupera il valore della propriet� rdP.
         * 
         * @return
         *     possible object is
         *     {@link FirmatarioType }
         *     
         */
        public FirmatarioType getRdP() {
            return rdP;
        }

        /**
         * Imposta il valore della propriet� rdP.
         * 
         * @param value
         *     allowed object is
         *     {@link FirmatarioType }
         *     
         */
        public void setRdP(FirmatarioType value) {
            this.rdP = value;
        }

        /**
         * Recupera il valore della propriet� rup.
         * 
         * @return
         *     possible object is
         *     {@link FirmatarioType }
         *     
         */
        public FirmatarioType getRUP() {
            return rup;
        }

        /**
         * Imposta il valore della propriet� rup.
         * 
         * @param value
         *     allowed object is
         *     {@link FirmatarioType }
         *     
         */
        public void setRUP(FirmatarioType value) {
            this.rup = value;
        }

        /**
         * Recupera il valore della propriet� preverificaRagioneria.
         * 
         * @return
         *     possible object is
         *     {@link ResponseGetDetermina.Determina.PreverificaRagioneria }
         *     
         */
        public ResponseGetDetermina.Determina.PreverificaRagioneria getPreverificaRagioneria() {
            return preverificaRagioneria;
        }

        /**
         * Imposta il valore della propriet� preverificaRagioneria.
         * 
         * @param value
         *     allowed object is
         *     {@link ResponseGetDetermina.Determina.PreverificaRagioneria }
         *     
         */
        public void setPreverificaRagioneria(ResponseGetDetermina.Determina.PreverificaRagioneria value) {
            this.preverificaRagioneria = value;
        }

        /**
         * Recupera il valore della propriet� vistoRegolaritaContabile.
         * 
         * @return
         *     possible object is
         *     {@link ResponseGetDetermina.Determina.VistoRegolaritaContabile }
         *     
         */
        public ResponseGetDetermina.Determina.VistoRegolaritaContabile getVistoRegolaritaContabile() {
            return vistoRegolaritaContabile;
        }

        /**
         * Imposta il valore della propriet� vistoRegolaritaContabile.
         * 
         * @param value
         *     allowed object is
         *     {@link ResponseGetDetermina.Determina.VistoRegolaritaContabile }
         *     
         */
        public void setVistoRegolaritaContabile(ResponseGetDetermina.Determina.VistoRegolaritaContabile value) {
            this.vistoRegolaritaContabile = value;
        }

        /**
         * Recupera il valore della propriet� pubblicazione.
         * 
         * @return
         *     possible object is
         *     {@link ResponseGetDetermina.Determina.Pubblicazione }
         *     
         */
        public ResponseGetDetermina.Determina.Pubblicazione getPubblicazione() {
            return pubblicazione;
        }

        /**
         * Imposta il valore della propriet� pubblicazione.
         * 
         * @param value
         *     allowed object is
         *     {@link ResponseGetDetermina.Determina.Pubblicazione }
         *     
         */
        public void setPubblicazione(ResponseGetDetermina.Determina.Pubblicazione value) {
            this.pubblicazione = value;
        }

        /**
         * Recupera il valore della propriet� attachmentFiles.
         * 
         * @return
         *     possible object is
         *     {@link ResponseGetDetermina.Determina.AttachmentFiles }
         *     
         */
        public ResponseGetDetermina.Determina.AttachmentFiles getAttachmentFiles() {
            return attachmentFiles;
        }

        /**
         * Imposta il valore della propriet� attachmentFiles.
         * 
         * @param value
         *     allowed object is
         *     {@link ResponseGetDetermina.Determina.AttachmentFiles }
         *     
         */
        public void setAttachmentFiles(ResponseGetDetermina.Determina.AttachmentFiles value) {
            this.attachmentFiles = value;
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
         *         &lt;element name="File" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="DisplayFilename" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="TipoologiaDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
         *                   &lt;element name="Dimensione" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
         *                   &lt;element name="Digest">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="algoritmo" use="required">
         *                             &lt;simpleType>
         *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                 &lt;enumeration value="SHA-1"/>
         *                                 &lt;enumeration value="SHA-256"/>
         *                               &lt;/restriction>
         *                             &lt;/simpleType>
         *                           &lt;/attribute>
         *                           &lt;attribute name="encoding">
         *                             &lt;simpleType>
         *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                 &lt;enumeration value="hex"/>
         *                                 &lt;enumeration value="base64"/>
         *                               &lt;/restriction>
         *                             &lt;/simpleType>
         *                           &lt;/attribute>
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="Mimetype" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="Firmato">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>boolean">
         *                           &lt;attribute name="tipoFirma">
         *                             &lt;simpleType>
         *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                                 &lt;pattern value="CAdES"/>
         *                                 &lt;pattern value="PAdES"/>
         *                                 &lt;pattern value="XAdES"/>
         *                               &lt;/restriction>
         *                             &lt;/simpleType>
         *                           &lt;/attribute>
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="nroAttachment" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
         *                 &lt;attribute name="rappresenta" use="required">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                       &lt;enumeration value="dispositivo"/>
         *                       &lt;enumeration value="visto_reg_contabile"/>
         *                       &lt;enumeration value="allegato_parte_integrante"/>
         *                       &lt;enumeration value="allegato_non_parte_integrante"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
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
            "file"
        })
        public static class AttachmentFiles {

            @XmlElement(name = "File")
            protected List<ResponseGetDetermina.Determina.AttachmentFiles.File> file;

            /**
             * Gets the value of the file property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the file property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getFile().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ResponseGetDetermina.Determina.AttachmentFiles.File }
             * 
             * 
             */
            public List<ResponseGetDetermina.Determina.AttachmentFiles.File> getFile() {
                if (file == null) {
                    file = new ArrayList<ResponseGetDetermina.Determina.AttachmentFiles.File>();
                }
                return this.file;
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
             *         &lt;element name="DisplayFilename" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="TipoologiaDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
             *         &lt;element name="Dimensione" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
             *         &lt;element name="Digest">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                 &lt;attribute name="algoritmo" use="required">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;enumeration value="SHA-1"/>
             *                       &lt;enumeration value="SHA-256"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *                 &lt;attribute name="encoding">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;enumeration value="hex"/>
             *                       &lt;enumeration value="base64"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="Mimetype" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Firmato">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>boolean">
             *                 &lt;attribute name="tipoFirma">
             *                   &lt;simpleType>
             *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *                       &lt;pattern value="CAdES"/>
             *                       &lt;pattern value="PAdES"/>
             *                       &lt;pattern value="XAdES"/>
             *                     &lt;/restriction>
             *                   &lt;/simpleType>
             *                 &lt;/attribute>
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="nroAttachment" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
             *       &lt;attribute name="rappresenta" use="required">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *             &lt;enumeration value="dispositivo"/>
             *             &lt;enumeration value="visto_reg_contabile"/>
             *             &lt;enumeration value="allegato_parte_integrante"/>
             *             &lt;enumeration value="allegato_non_parte_integrante"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "displayFilename",
                "tipoologiaDoc",
                "descrizione",
                "dimensione",
                "digest",
                "mimetype",
                "firmato"
            })
            public static class File {

                @XmlElement(name = "DisplayFilename", required = true)
                protected String displayFilename;
                @XmlElement(name = "TipoologiaDoc")
                protected String tipoologiaDoc;
                @XmlElement(name = "Descrizione")
                protected Object descrizione;
                @XmlElement(name = "Dimensione", required = true)
                @XmlSchemaType(name = "positiveInteger")
                protected BigInteger dimensione;
                @XmlElement(name = "Digest", required = true)
                protected ResponseGetDetermina.Determina.AttachmentFiles.File.Digest digest;
                @XmlElement(name = "Mimetype", required = true)
                protected String mimetype;
                @XmlElement(name = "Firmato", required = true)
                protected ResponseGetDetermina.Determina.AttachmentFiles.File.Firmato firmato;
                @XmlAttribute(name = "nroAttachment", required = true)
                @XmlSchemaType(name = "positiveInteger")
                protected BigInteger nroAttachment;
                @XmlAttribute(name = "rappresenta", required = true)
                protected String rappresenta;

                /**
                 * Recupera il valore della propriet� displayFilename.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDisplayFilename() {
                    return displayFilename;
                }

                /**
                 * Imposta il valore della propriet� displayFilename.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDisplayFilename(String value) {
                    this.displayFilename = value;
                }

                /**
                 * Recupera il valore della propriet� tipoologiaDoc.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTipoologiaDoc() {
                    return tipoologiaDoc;
                }

                /**
                 * Imposta il valore della propriet� tipoologiaDoc.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTipoologiaDoc(String value) {
                    this.tipoologiaDoc = value;
                }

                /**
                 * Recupera il valore della propriet� descrizione.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getDescrizione() {
                    return descrizione;
                }

                /**
                 * Imposta il valore della propriet� descrizione.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setDescrizione(Object value) {
                    this.descrizione = value;
                }

                /**
                 * Recupera il valore della propriet� dimensione.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getDimensione() {
                    return dimensione;
                }

                /**
                 * Imposta il valore della propriet� dimensione.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setDimensione(BigInteger value) {
                    this.dimensione = value;
                }

                /**
                 * Recupera il valore della propriet� digest.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ResponseGetDetermina.Determina.AttachmentFiles.File.Digest }
                 *     
                 */
                public ResponseGetDetermina.Determina.AttachmentFiles.File.Digest getDigest() {
                    return digest;
                }

                /**
                 * Imposta il valore della propriet� digest.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ResponseGetDetermina.Determina.AttachmentFiles.File.Digest }
                 *     
                 */
                public void setDigest(ResponseGetDetermina.Determina.AttachmentFiles.File.Digest value) {
                    this.digest = value;
                }

                /**
                 * Recupera il valore della propriet� mimetype.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getMimetype() {
                    return mimetype;
                }

                /**
                 * Imposta il valore della propriet� mimetype.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setMimetype(String value) {
                    this.mimetype = value;
                }

                /**
                 * Recupera il valore della propriet� firmato.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ResponseGetDetermina.Determina.AttachmentFiles.File.Firmato }
                 *     
                 */
                public ResponseGetDetermina.Determina.AttachmentFiles.File.Firmato getFirmato() {
                    return firmato;
                }

                /**
                 * Imposta il valore della propriet� firmato.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ResponseGetDetermina.Determina.AttachmentFiles.File.Firmato }
                 *     
                 */
                public void setFirmato(ResponseGetDetermina.Determina.AttachmentFiles.File.Firmato value) {
                    this.firmato = value;
                }

                /**
                 * Recupera il valore della propriet� nroAttachment.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getNroAttachment() {
                    return nroAttachment;
                }

                /**
                 * Imposta il valore della propriet� nroAttachment.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setNroAttachment(BigInteger value) {
                    this.nroAttachment = value;
                }

                /**
                 * Recupera il valore della propriet� rappresenta.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRappresenta() {
                    return rappresenta;
                }

                /**
                 * Imposta il valore della propriet� rappresenta.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRappresenta(String value) {
                    this.rappresenta = value;
                }


                /**
                 * <p>Classe Java per anonymous complex type.
                 * 
                 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *       &lt;attribute name="algoritmo" use="required">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;enumeration value="SHA-1"/>
                 *             &lt;enumeration value="SHA-256"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
                 *       &lt;attribute name="encoding">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;enumeration value="hex"/>
                 *             &lt;enumeration value="base64"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
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
                public static class Digest {

                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "algoritmo", required = true)
                    protected String algoritmo;
                    @XmlAttribute(name = "encoding")
                    protected String encoding;

                    /**
                     * Recupera il valore della propriet� value.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getValue() {
                        return value;
                    }

                    /**
                     * Imposta il valore della propriet� value.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setValue(String value) {
                        this.value = value;
                    }

                    /**
                     * Recupera il valore della propriet� algoritmo.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAlgoritmo() {
                        return algoritmo;
                    }

                    /**
                     * Imposta il valore della propriet� algoritmo.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAlgoritmo(String value) {
                        this.algoritmo = value;
                    }

                    /**
                     * Recupera il valore della propriet� encoding.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getEncoding() {
                        return encoding;
                    }

                    /**
                     * Imposta il valore della propriet� encoding.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setEncoding(String value) {
                        this.encoding = value;
                    }

                }


                /**
                 * <p>Classe Java per anonymous complex type.
                 * 
                 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>boolean">
                 *       &lt;attribute name="tipoFirma">
                 *         &lt;simpleType>
                 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
                 *             &lt;pattern value="CAdES"/>
                 *             &lt;pattern value="PAdES"/>
                 *             &lt;pattern value="XAdES"/>
                 *           &lt;/restriction>
                 *         &lt;/simpleType>
                 *       &lt;/attribute>
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
                public static class Firmato {

                    @XmlValue
                    protected boolean value;
                    @XmlAttribute(name = "tipoFirma")
                    protected String tipoFirma;

                    /**
                     * Recupera il valore della propriet� value.
                     * 
                     */
                    public boolean isValue() {
                        return value;
                    }

                    /**
                     * Imposta il valore della propriet� value.
                     * 
                     */
                    public void setValue(boolean value) {
                        this.value = value;
                    }

                    /**
                     * Recupera il valore della propriet� tipoFirma.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTipoFirma() {
                        return tipoFirma;
                    }

                    /**
                     * Imposta il valore della propriet� tipoFirma.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTipoFirma(String value) {
                        this.tipoFirma = value;
                    }

                }

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
         *         &lt;element name="Numero">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
         *               &lt;totalDigits value="7"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="TsRegistrazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
            "numero",
            "tsRegistrazione"
        })
        public static class IDAttoDefinitivo {

            @XmlElement(name = "Numero", required = true)
            protected BigInteger numero;
            @XmlElement(name = "TsRegistrazione", required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar tsRegistrazione;

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

            /**
             * Recupera il valore della propriet� tsRegistrazione.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getTsRegistrazione() {
                return tsRegistrazione;
            }

            /**
             * Imposta il valore della propriet� tsRegistrazione.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setTsRegistrazione(XMLGregorianCalendar value) {
                this.tsRegistrazione = value;
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
         *         &lt;element name="Numero">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
         *               &lt;totalDigits value="7"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="TsRegistrazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="Autore" type="{}AutoreType"/>
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
            "numero",
            "tsRegistrazione",
            "autore"
        })
        public static class IDProposta {

            @XmlElement(name = "Numero", required = true)
            protected BigInteger numero;
            @XmlElement(name = "TsRegistrazione", required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar tsRegistrazione;
            @XmlElement(name = "Autore", required = true)
            protected AutoreType autore;

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

            /**
             * Recupera il valore della propriet� tsRegistrazione.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getTsRegistrazione() {
                return tsRegistrazione;
            }

            /**
             * Imposta il valore della propriet� tsRegistrazione.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setTsRegistrazione(XMLGregorianCalendar value) {
                this.tsRegistrazione = value;
            }

            /**
             * Recupera il valore della propriet� autore.
             * 
             * @return
             *     possible object is
             *     {@link AutoreType }
             *     
             */
            public AutoreType getAutore() {
                return autore;
            }

            /**
             * Imposta il valore della propriet� autore.
             * 
             * @param value
             *     allowed object is
             *     {@link AutoreType }
             *     
             */
            public void setAutore(AutoreType value) {
                this.autore = value;
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
         *         &lt;element name="TsInizio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="TsCompletamento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
         *         &lt;element name="TipoEsito" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;enumeration value="OK"/>
         *               &lt;enumeration value="KO"/>
         *               &lt;enumeration value="W"/>
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
            "tsInizio",
            "tsCompletamento",
            "tipoEsito"
        })
        public static class PreverificaRagioneria {

            @XmlElement(name = "TsInizio", required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar tsInizio;
            @XmlElement(name = "TsCompletamento")
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar tsCompletamento;
            @XmlElement(name = "TipoEsito")
            protected String tipoEsito;

            /**
             * Recupera il valore della propriet� tsInizio.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getTsInizio() {
                return tsInizio;
            }

            /**
             * Imposta il valore della propriet� tsInizio.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setTsInizio(XMLGregorianCalendar value) {
                this.tsInizio = value;
            }

            /**
             * Recupera il valore della propriet� tsCompletamento.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getTsCompletamento() {
                return tsCompletamento;
            }

            /**
             * Imposta il valore della propriet� tsCompletamento.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setTsCompletamento(XMLGregorianCalendar value) {
                this.tsCompletamento = value;
            }

            /**
             * Recupera il valore della propriet� tipoEsito.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoEsito() {
                return tipoEsito;
            }

            /**
             * Imposta il valore della propriet� tipoEsito.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoEsito(String value) {
                this.tipoEsito = value;
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
         *         &lt;element name="DataInizio" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="DataTermine" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
            "dataInizio",
            "dataTermine"
        })
        public static class Pubblicazione {

            @XmlElement(name = "DataInizio", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar dataInizio;
            @XmlElement(name = "DataTermine")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar dataTermine;

            /**
             * Recupera il valore della propriet� dataInizio.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDataInizio() {
                return dataInizio;
            }

            /**
             * Imposta il valore della propriet� dataInizio.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDataInizio(XMLGregorianCalendar value) {
                this.dataInizio = value;
            }

            /**
             * Recupera il valore della propriet� dataTermine.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDataTermine() {
                return dataTermine;
            }

            /**
             * Imposta il valore della propriet� dataTermine.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDataTermine(XMLGregorianCalendar value) {
                this.dataTermine = value;
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
         *         &lt;element name="TsRilascio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="Firmatario" type="{}FirmatarioRestrictedType"/>
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
            "tsRilascio",
            "firmatario"
        })
        public static class VistoRegolaritaContabile {

            @XmlElement(name = "TsRilascio", required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar tsRilascio;
            @XmlElement(name = "Firmatario", required = true)
            protected FirmatarioRestrictedType firmatario;

            /**
             * Recupera il valore della propriet� tsRilascio.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getTsRilascio() {
                return tsRilascio;
            }

            /**
             * Imposta il valore della propriet� tsRilascio.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setTsRilascio(XMLGregorianCalendar value) {
                this.tsRilascio = value;
            }

            /**
             * Recupera il valore della propriet� firmatario.
             * 
             * @return
             *     possible object is
             *     {@link FirmatarioRestrictedType }
             *     
             */
            public FirmatarioRestrictedType getFirmatario() {
                return firmatario;
            }

            /**
             * Imposta il valore della propriet� firmatario.
             * 
             * @param value
             *     allowed object is
             *     {@link FirmatarioRestrictedType }
             *     
             */
            public void setFirmatario(FirmatarioRestrictedType value) {
                this.firmatario = value;
            }

        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="codice" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    public static class ErrorMessage {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "codice", required = true)
        protected String codice;

        /**
         * Recupera il valore della propriet� value.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Imposta il valore della propriet� value.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Recupera il valore della propriet� codice.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodice() {
            return codice;
        }

        /**
         * Imposta il valore della propriet� codice.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodice(String value) {
            this.codice = value;
        }

    }

}
