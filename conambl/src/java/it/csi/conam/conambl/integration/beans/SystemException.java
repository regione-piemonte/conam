/**
 * SystemException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class SystemException  extends it.csi.conam.conambl.integration.beans.CSIException  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7017873612433695977L;
	public SystemException() {
    }

    public SystemException(
           java.lang.String nestedExcClassName,
           java.lang.String nestedExcMsg,
           java.lang.String stackTraceMessage) {
        super(
            nestedExcClassName,
            nestedExcMsg,
            stackTraceMessage);
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SystemException)) return false;
        //SystemException other = (SystemException) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj);
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
        __hashCodeCalc = false;
        return _hashCode;
    }
 
	static {
    	typeDesc = new org.apache.axis.description.TypeDesc(SystemException.class, true);
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "SystemException"));
    }

}
