//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.11 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.08.30 alle 02:50:27 PM CEST 
//


package it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.service.Service;
import it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.service.ServiceRequest;
import it.csi.conam.conambl.integration.auriga.model.getAllFilesInFolder.service.ServiceResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.aurgia.model.getAllFilesInFolder package. 
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

    private final static QName _Service_QNAME = new QName("http://trovadocfolder.webservices.repository2.auriga.eng.it", "service");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.aurgia.model.getAllFilesInFolder
     * 
     */
    public ObjectFactory() {
    }

    public ServiceRequest createServiceRequest() {
        return new ServiceRequest();
    }
    public ServiceResponse createServiceResponse() {
        return new ServiceResponse();
    }

    public Service createService() {
        return new Service();
    }
    @XmlElementDecl(namespace = "http://trovadocfolder.webservices.repository2.auriga.eng.it", name = "service")
    public JAXBElement<Service> createService(Service value) {
        return new JAXBElement<Service>(_Service_QNAME, Service.class, null, value);
    }
    /**
     * Create an instance of {@link TrovaDocFolder }
     * 
     */
    public TrovaDocFolder createTrovaDocFolder() {
        return new TrovaDocFolder();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.Ordinamento }
     * 
     */
    public TrovaDocFolder.Ordinamento createTrovaDocFolderOrdinamento() {
        return new TrovaDocFolder.Ordinamento();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.FiltriAvanzati }
     * 
     */
    public TrovaDocFolder.FiltriAvanzati createTrovaDocFolderFiltriAvanzati() {
        return new TrovaDocFolder.FiltriAvanzati();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.FiltriPrincipali }
     * 
     */
    public TrovaDocFolder.FiltriPrincipali createTrovaDocFolderFiltriPrincipali() {
        return new TrovaDocFolder.FiltriPrincipali();
    }

    /**
     * Create an instance of {@link PaginazioneType }
     * 
     */
    public PaginazioneType createPaginazioneType() {
        return new PaginazioneType();
    }

    /**
     * Create an instance of {@link UserType }
     * 
     */
    public UserType createUserType() {
        return new UserType();
    }

    /**
     * Create an instance of {@link LivelloGerarchiaType }
     * 
     */
    public LivelloGerarchiaType createLivelloGerarchiaType() {
        return new LivelloGerarchiaType();
    }

    /**
     * Create an instance of {@link EstremiRegNumType }
     * 
     */
    public EstremiRegNumType createEstremiRegNumType() {
        return new EstremiRegNumType();
    }

    /**
     * Create an instance of {@link CriterioRicercaSuAttributoAddType }
     * 
     */
    public CriterioRicercaSuAttributoAddType createCriterioRicercaSuAttributoAddType() {
        return new CriterioRicercaSuAttributoAddType();
    }

    /**
     * Create an instance of {@link OggDiTabDiSistemaType }
     * 
     */
    public OggDiTabDiSistemaType createOggDiTabDiSistemaType() {
        return new OggDiTabDiSistemaType();
    }

    /**
     * Create an instance of {@link EstremiXIdentificazioneFolderType }
     * 
     */
    public EstremiXIdentificazioneFolderType createEstremiXIdentificazioneFolderType() {
        return new EstremiXIdentificazioneFolderType();
    }

    /**
     * Create an instance of {@link ClassifUAType }
     * 
     */
    public ClassifUAType createClassifUAType() {
        return new ClassifUAType();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.Ordinamento.PerCampo }
     * 
     */
    public TrovaDocFolder.Ordinamento.PerCampo createTrovaDocFolderOrdinamentoPerCampo() {
        return new TrovaDocFolder.Ordinamento.PerCampo();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.FiltriAvanzati.StatoDocumento }
     * 
     */
    public TrovaDocFolder.FiltriAvanzati.StatoDocumento createTrovaDocFolderFiltriAvanzatiStatoDocumento() {
        return new TrovaDocFolder.FiltriAvanzati.StatoDocumento();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.FiltriAvanzati.StatoFolder }
     * 
     */
    public TrovaDocFolder.FiltriAvanzati.StatoFolder createTrovaDocFolderFiltriAvanzatiStatoFolder() {
        return new TrovaDocFolder.FiltriAvanzati.StatoFolder();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.FiltriAvanzati.SoloConLock }
     * 
     */
    public TrovaDocFolder.FiltriAvanzati.SoloConLock createTrovaDocFolderFiltriAvanzatiSoloConLock() {
        return new TrovaDocFolder.FiltriAvanzati.SoloConLock();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.FiltriAvanzati.ApplicazionePropietaria }
     * 
     */
    public TrovaDocFolder.FiltriAvanzati.ApplicazionePropietaria createTrovaDocFolderFiltriAvanzatiApplicazionePropietaria() {
        return new TrovaDocFolder.FiltriAvanzati.ApplicazionePropietaria();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.FiltriAvanzati.RegistrazioneDoc }
     * 
     */
    public TrovaDocFolder.FiltriAvanzati.RegistrazioneDoc createTrovaDocFolderFiltriAvanzatiRegistrazioneDoc() {
        return new TrovaDocFolder.FiltriAvanzati.RegistrazioneDoc();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.FiltriPrincipali.CercaInFolder }
     * 
     */
    public TrovaDocFolder.FiltriPrincipali.CercaInFolder createTrovaDocFolderFiltriPrincipaliCercaInFolder() {
        return new TrovaDocFolder.FiltriPrincipali.CercaInFolder();
    }

    /**
     * Create an instance of {@link TrovaDocFolder.FiltriPrincipali.FiltroFullText }
     * 
     */
    public TrovaDocFolder.FiltriPrincipali.FiltroFullText createTrovaDocFolderFiltriPrincipaliFiltroFullText() {
        return new TrovaDocFolder.FiltriPrincipali.FiltroFullText();
    }

    /**
     * Create an instance of {@link Lista }
     *
     */
    public Lista createLista() {
        return new Lista();
    }

    /**
     * Create an instance of {@link Lista.Riga }
     *
     */
    public Lista.Riga createListaRiga() {
        return new Lista.Riga();
    }

    /**
     * Create an instance of {@link Lista.Riga.Colonna }
     *
     */
    public Lista.Riga.Colonna createListaRigaColonna() {
        return new Lista.Riga.Colonna();
    }


}
