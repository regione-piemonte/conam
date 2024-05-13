/**
 * TestataAggiornaPosizioniDebitorie.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 13, 2013 (09:13:21 GMT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class TestataAggiornaPosizioniDebitorie  extends TestataCommons {

    private static final long serialVersionUID = -42435235325353253L;

    public TestataAggiornaPosizioniDebitorie() {
    }

    public TestataAggiornaPosizioniDebitorie(
        java.lang.String idMessaggio,
        java.lang.String CFEnteCreditore,
        java.lang.String codiceVersamento,
        java.lang.Boolean multiBeneficiario,
        java.math.BigInteger numeroPosizioniDebitorie) {
            super(idMessaggio, CFEnteCreditore, codiceVersamento, multiBeneficiario, numeroPosizioniDebitorie);
    }



    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TestataAggiornaPosizioniDebitorie)) return false;
        TestataAggiornaPosizioniDebitorie other = (TestataAggiornaPosizioniDebitorie) obj;
        //	Issue 3 - Sonarqube
        // Condition 'obj == null' is always 'false'
        // if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idMessaggio==null && other.getIdMessaggio()==null) || 
             (this.idMessaggio!=null &&
              this.idMessaggio.equals(other.getIdMessaggio()))) &&
            ((this.CFEnteCreditore==null && other.getCFEnteCreditore()==null) || 
             (this.CFEnteCreditore!=null &&
              this.CFEnteCreditore.equals(other.getCFEnteCreditore()))) &&
            ((this.codiceVersamento==null && other.getCodiceVersamento()==null) || 
             (this.codiceVersamento!=null &&
              this.codiceVersamento.equals(other.getCodiceVersamento()))) &&
            ((this.multiBeneficiario==null && other.getMultiBeneficiario()==null) || 
             (this.multiBeneficiario!=null &&
              this.multiBeneficiario.equals(other.getMultiBeneficiario()))) &&
            ((this.numeroPosizioniDebitorie==null && other.getNumeroPosizioniDebitorie()==null) || 
             (this.numeroPosizioniDebitorie!=null &&
              this.numeroPosizioniDebitorie.equals(other.getNumeroPosizioniDebitorie())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getIdMessaggio() != null) {
            _hashCode += getIdMessaggio().hashCode();
        }
        if (getCFEnteCreditore() != null) {
            _hashCode += getCFEnteCreditore().hashCode();
        }
        if (getCodiceVersamento() != null) {
            _hashCode += getCodiceVersamento().hashCode();
        }
        if (getMultiBeneficiario() != null) {
            _hashCode += getMultiBeneficiario().hashCode();
        }
        if (getNumeroPosizioniDebitorie() != null) {
            _hashCode += getNumeroPosizioniDebitorie().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    static  {
        typeDesc = new org.apache.axis.description.TypeDesc(TestataAggiornaPosizioniDebitorie.class, true);
    }

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "TestataAggiornaPosizioniDebitorie"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMessaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "IdMessaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CFEnteCreditore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "CFEnteCreditore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceVersamento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "CodiceVersamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multiBeneficiario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "MultiBeneficiario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroPosizioniDebitorie");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "NumeroPosizioniDebitorie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }
}
