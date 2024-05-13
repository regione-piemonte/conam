/**
 * PosizioneDaAggiornareTypeComponentiImportoSecondario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 13, 2013 (09:13:21 GMT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class PosizioneDaAggiornareTypeComponentiImportoSecondario  implements java.io.Serializable {
    private ComponenteImportoType componenteImporto;

    public PosizioneDaAggiornareTypeComponentiImportoSecondario() {
    }

    public PosizioneDaAggiornareTypeComponentiImportoSecondario(
           ComponenteImportoType componenteImporto) {
           this.componenteImporto = componenteImporto;
    }


    /**
     * Gets the componenteImporto value for this PosizioneDaAggiornareTypeComponentiImportoSecondario.
     * 
     * @return componenteImporto
     */
    public ComponenteImportoType getComponenteImporto() {
        return componenteImporto;
    }


    /**
     * Sets the componenteImporto value for this PosizioneDaAggiornareTypeComponentiImportoSecondario.
     * 
     * @param componenteImporto
     */
    public void setComponenteImporto(ComponenteImportoType componenteImporto) {
        this.componenteImporto = componenteImporto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PosizioneDaAggiornareTypeComponentiImportoSecondario)) return false;
        PosizioneDaAggiornareTypeComponentiImportoSecondario other = (PosizioneDaAggiornareTypeComponentiImportoSecondario) obj;
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
            ((this.componenteImporto==null && other.getComponenteImporto()==null) || 
             (this.componenteImporto!=null &&
              this.componenteImporto.equals(other.getComponenteImporto())));
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
        if (getComponenteImporto() != null) {
            _hashCode += getComponenteImporto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PosizioneDaAggiornareTypeComponentiImportoSecondario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", ">PosizioneDaAggiornareType>ComponentiImportoSecondario"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("componenteImporto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ComponenteImporto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "ComponenteImportoType"));
        elemField.setMinOccurs(0);
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
