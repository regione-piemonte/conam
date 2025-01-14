//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.7 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2024.06.19 alle 04:28:10 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model.getDetermina;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import it.csi.conam.conambl.integration.auriga.model.getDetermina.service.Service;
import it.csi.conam.conambl.integration.auriga.model.getDetermina.service.ServiceRequest;
import it.csi.conam.conambl.integration.auriga.model.getDetermina.service.ServiceResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
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

	private final static QName _Service_QNAME = new QName("http://extractone.webservices.repository2.auriga.eng.it", "service");


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.conam.conambl.integration.auriga.service.getFileInUd
     * 
     */
    public ObjectFactory() {
    }

	public ServiceRequest createServiceRequest() { return new ServiceRequest(); }
    public ServiceResponse createServiceResponse() { return new ServiceResponse(); }
	public Service createService() { return new Service(); }
	
	@XmlElementDecl(namespace = "http://extractone.webservices.repository2.auriga.eng.it", name = "service")
    public JAXBElement<Service> createService(Service value) {
        return new JAXBElement<Service>(_Service_QNAME, Service.class, null, value);
    }

    /**
     * Create an instance of {@link RequestGetDetermina }
     * 
     */
    public RequestGetDetermina createRequestGetDetermina() {
        return new RequestGetDetermina();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina }
     * 
     */
    public ResponseGetDetermina createResponseGetDetermina() {
        return new ResponseGetDetermina();
    }

    /**
     * Create an instance of {@link FirmatarioType }
     * 
     */
    public FirmatarioType createFirmatarioType() {
        return new FirmatarioType();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.Determina }
     * 
     */
    public ResponseGetDetermina.Determina createResponseGetDeterminaDetermina() {
        return new ResponseGetDetermina.Determina();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.Determina.AttachmentFiles }
     * 
     */
    public ResponseGetDetermina.Determina.AttachmentFiles createResponseGetDeterminaDeterminaAttachmentFiles() {
        return new ResponseGetDetermina.Determina.AttachmentFiles();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.Determina.AttachmentFiles.File }
     * 
     */
    public ResponseGetDetermina.Determina.AttachmentFiles.File createResponseGetDeterminaDeterminaAttachmentFilesFile() {
        return new ResponseGetDetermina.Determina.AttachmentFiles.File();
    }

    /**
     * Create an instance of {@link RequestGetDetermina.IDDetermina }
     * 
     */
    public RequestGetDetermina.IDDetermina createRequestGetDeterminaIDDetermina() {
        return new RequestGetDetermina.IDDetermina();
    }

    /**
     * Create an instance of {@link RequestGetDetermina.SceltaFileRichiesti }
     * 
     */
    public RequestGetDetermina.SceltaFileRichiesti createRequestGetDeterminaSceltaFileRichiesti() {
        return new RequestGetDetermina.SceltaFileRichiesti();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.ErrorMessage }
     * 
     */
    public ResponseGetDetermina.ErrorMessage createResponseGetDeterminaErrorMessage() {
        return new ResponseGetDetermina.ErrorMessage();
    }

    /**
     * Create an instance of {@link AutoreType }
     * 
     */
    public AutoreType createAutoreType() {
        return new AutoreType();
    }

    /**
     * Create an instance of {@link StrutturaType }
     * 
     */
    public StrutturaType createStrutturaType() {
        return new StrutturaType();
    }

    /**
     * Create an instance of {@link FirmatarioRestrictedType }
     * 
     */
    public FirmatarioRestrictedType createFirmatarioRestrictedType() {
        return new FirmatarioRestrictedType();
    }

    /**
     * Create an instance of {@link FirmatarioType.TsFirmaVisto }
     * 
     */
    public FirmatarioType.TsFirmaVisto createFirmatarioTypeTsFirmaVisto() {
        return new FirmatarioType.TsFirmaVisto();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.Determina.IDProposta }
     * 
     */
    public ResponseGetDetermina.Determina.IDProposta createResponseGetDeterminaDeterminaIDProposta() {
        return new ResponseGetDetermina.Determina.IDProposta();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.Determina.IDAttoDefinitivo }
     * 
     */
    public ResponseGetDetermina.Determina.IDAttoDefinitivo createResponseGetDeterminaDeterminaIDAttoDefinitivo() {
        return new ResponseGetDetermina.Determina.IDAttoDefinitivo();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.Determina.PreverificaRagioneria }
     * 
     */
    public ResponseGetDetermina.Determina.PreverificaRagioneria createResponseGetDeterminaDeterminaPreverificaRagioneria() {
        return new ResponseGetDetermina.Determina.PreverificaRagioneria();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.Determina.VistoRegolaritaContabile }
     * 
     */
    public ResponseGetDetermina.Determina.VistoRegolaritaContabile createResponseGetDeterminaDeterminaVistoRegolaritaContabile() {
        return new ResponseGetDetermina.Determina.VistoRegolaritaContabile();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.Determina.Pubblicazione }
     * 
     */
    public ResponseGetDetermina.Determina.Pubblicazione createResponseGetDeterminaDeterminaPubblicazione() {
        return new ResponseGetDetermina.Determina.Pubblicazione();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.Determina.AttachmentFiles.File.Digest }
     * 
     */
    public ResponseGetDetermina.Determina.AttachmentFiles.File.Digest createResponseGetDeterminaDeterminaAttachmentFilesFileDigest() {
        return new ResponseGetDetermina.Determina.AttachmentFiles.File.Digest();
    }

    /**
     * Create an instance of {@link ResponseGetDetermina.Determina.AttachmentFiles.File.Firmato }
     * 
     */
    public ResponseGetDetermina.Determina.AttachmentFiles.File.Firmato createResponseGetDeterminaDeterminaAttachmentFilesFileFirmato() {
        return new ResponseGetDetermina.Determina.AttachmentFiles.File.Firmato();
    }

}
