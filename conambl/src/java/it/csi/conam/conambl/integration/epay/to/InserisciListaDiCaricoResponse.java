/**
 * InserisciListaDiCaricoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 13, 2013 (09:13:21 GMT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class InserisciListaDiCaricoResponse  extends ResponseType  implements java.io.Serializable {
    private EsitoInserimentoType esitoInserimento;

    public InserisciListaDiCaricoResponse() {
    }

    public InserisciListaDiCaricoResponse(
           ResultType result,
           EsitoInserimentoType esitoInserimento) {
        super(
            result);
        this.esitoInserimento = esitoInserimento;
    }


    /**
     * Gets the esitoInserimento value for this InserisciListaDiCaricoResponse.
     * 
     * @return esitoInserimento
     */
    public EsitoInserimentoType getEsitoInserimento() {
        return esitoInserimento;
    }


    /**
     * Sets the esitoInserimento value for this InserisciListaDiCaricoResponse.
     * 
     * @param esitoInserimento
     */
    public void setEsitoInserimento(EsitoInserimentoType esitoInserimento) {
        this.esitoInserimento = esitoInserimento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InserisciListaDiCaricoResponse)) return false;
        InserisciListaDiCaricoResponse other = (InserisciListaDiCaricoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.esitoInserimento==null && other.getEsitoInserimento()==null) || 
             (this.esitoInserimento!=null &&
              this.esitoInserimento.equals(other.getEsitoInserimento())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getEsitoInserimento() != null) {
            _hashCode += getEsitoInserimento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InserisciListaDiCaricoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", ">InserisciListaDiCaricoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esitoInserimento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/enti2epaywso/types", "EsitoInserimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.csi.it/epay/epaywso/types", "EsitoInserimentoType"));
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
