/**
 * ElencoPosizioniDaAggiornareType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 13, 2013 (09:13:21 GMT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class ElencoPosizioniDaAggiornareType  implements java.io.Serializable {
    private PosizioneDaAggiornareType[] posizioniDaAggiornare;

    public ElencoPosizioniDaAggiornareType() {
    }

    public ElencoPosizioniDaAggiornareType(
           PosizioneDaAggiornareType[] posizioniDaAggiornare) {
           this.posizioniDaAggiornare = posizioniDaAggiornare;
    }


    /**
     * Gets the posizioniDaAggiornare value for this ElencoPosizioniDaAggiornareType.
     * 
     * @return posizioniDaAggiornare
     */
    public PosizioneDaAggiornareType[] getPosizioniDaAggiornare() {
        return posizioniDaAggiornare;
    }


    /**
     * Sets the posizioniDaAggiornare value for this ElencoPosizioniDaAggiornareType.
     * 
     * @param posizioniDaAggiornare
     */
    public void setPosizioniDaAggiornare(PosizioneDaAggiornareType[] posizioniDaAggiornare) {
        this.posizioniDaAggiornare = posizioniDaAggiornare;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ElencoPosizioniDaAggiornareType)) return false;
        ElencoPosizioniDaAggiornareType other = (ElencoPosizioniDaAggiornareType) obj;
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
            ((this.posizioniDaAggiornare==null && other.getPosizioniDaAggiornare()==null) || 
             (this.posizioniDaAggiornare!=null &&
              java.util.Arrays.equals(this.posizioniDaAggiornare, other.getPosizioniDaAggiornare())));
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
        if (getPosizioniDaAggiornare() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPosizioniDaAggiornare());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPosizioniDaAggiornare(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ElencoPosizioniDaAggiornareType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ElencoPosizioniDaAggiornareType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("posizioniDaAggiornare");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "PosizioniDaAggiornare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "PosizioneDaAggiornareType"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "PosizioneDaAggiornare"));
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
