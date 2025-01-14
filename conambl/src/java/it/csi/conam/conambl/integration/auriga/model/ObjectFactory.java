//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2020.06.25 alle 02:06:55 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.adsp.sua.model.auriga.createFolder package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    private final static QName _BaseOutputWS_QNAME = new QName("", "baseOutputWS");

   
    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.adsp.sua.model.auriga.createFolder
     * 
     */
    public ObjectFactory() {
    }
    
    
    public BaseOutputWS createBaseOutputWS() {
        return new BaseOutputWS();
    }
    @XmlElementDecl(namespace = "", name = "baseOutputWS")
    public JAXBElement<BaseOutputWS> createBaseOutputWS(BaseOutputWS value) {
        return new JAXBElement<BaseOutputWS>(_BaseOutputWS_QNAME, BaseOutputWS.class, null, value);
    }
    
}
