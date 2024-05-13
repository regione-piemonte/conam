/**
 * AggiornaPosizioniDebitorieRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 13, 2013 (09:13:21 GMT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class AggiornaPosizioniDebitorieRequest  implements java.io.Serializable {
    private TestataAggiornaPosizioniDebitorie testata;

    private ElencoPosizioniDaAggiornareType elencoPosizioniDaAggiornare;

    public AggiornaPosizioniDebitorieRequest() {
    }

    public AggiornaPosizioniDebitorieRequest(
           TestataAggiornaPosizioniDebitorie testata,
           ElencoPosizioniDaAggiornareType elencoPosizioniDaAggiornare) {
           this.testata = testata;
           this.elencoPosizioniDaAggiornare = elencoPosizioniDaAggiornare;
    }


    /**
     * Gets the testata value for this AggiornaPosizioniDebitorieRequest.
     * 
     * @return testata
     */
    public TestataAggiornaPosizioniDebitorie getTestata() {
        return testata;
    }


    /**
     * Sets the testata value for this AggiornaPosizioniDebitorieRequest.
     * 
     * @param testata
     */
    public void setTestata(TestataAggiornaPosizioniDebitorie testata) {
        this.testata = testata;
    }


    /**
     * Gets the elencoPosizioniDaAggiornare value for this AggiornaPosizioniDebitorieRequest.
     * 
     * @return elencoPosizioniDaAggiornare
     */
    public ElencoPosizioniDaAggiornareType getElencoPosizioniDaAggiornare() {
        return elencoPosizioniDaAggiornare;
    }


    /**
     * Sets the elencoPosizioniDaAggiornare value for this AggiornaPosizioniDebitorieRequest.
     * 
     * @param elencoPosizioniDaAggiornare
     */
    public void setElencoPosizioniDaAggiornare(ElencoPosizioniDaAggiornareType elencoPosizioniDaAggiornare) {
        this.elencoPosizioniDaAggiornare = elencoPosizioniDaAggiornare;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AggiornaPosizioniDebitorieRequest)) return false;
        AggiornaPosizioniDebitorieRequest other = (AggiornaPosizioniDebitorieRequest) obj;
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
            ((this.testata==null && other.getTestata()==null) || 
             (this.testata!=null &&
              this.testata.equals(other.getTestata()))) &&
            ((this.elencoPosizioniDaAggiornare==null && other.getElencoPosizioniDaAggiornare()==null) || 
             (this.elencoPosizioniDaAggiornare!=null &&
              this.elencoPosizioniDaAggiornare.equals(other.getElencoPosizioniDaAggiornare())));
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
        if (getTestata() != null) {
            _hashCode += getTestata().hashCode();
        }
        if (getElencoPosizioniDaAggiornare() != null) {
            _hashCode += getElencoPosizioniDaAggiornare().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AggiornaPosizioniDebitorieRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", ">AggiornaPosizioniDebitorieRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("testata");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "Testata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "TestataAggiornaPosizioniDebitorie"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoPosizioniDaAggiornare");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ElencoPosizioniDaAggiornare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ElencoPosizioniDaAggiornareType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
