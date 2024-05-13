/**
 * UserException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

public class UserException  extends it.csi.conam.conambl.integration.beans.CSIException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1914400721443100586L;
	public UserException() {
    }

    public UserException(
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
        if (!(obj instanceof UserException)) return false;
        //UserException other = (UserException) obj;
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
    	typeDesc = new org.apache.axis.description.TypeDesc(UserException.class, true);
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "UserException"));
    }

}
