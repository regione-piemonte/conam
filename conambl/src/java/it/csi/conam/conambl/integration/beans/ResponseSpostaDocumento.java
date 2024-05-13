/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.beans;

// 20200706_LC
public class ResponseSpostaDocumento  extends ResponseProtocollaDocumento {
    /**
	 * 
	 */
	private static final long serialVersionUID = 333388785900288354L;

    public ResponseSpostaDocumento() {
    }

    public ResponseSpostaDocumento(
           java.lang.String idDocumento,
           java.lang.String indiceClassificazione,
           java.lang.String protocollo) {
            super(idDocumento, indiceClassificazione,protocollo);
    }


    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseSpostaDocumento)) return false;
        ResponseSpostaDocumento other = (ResponseSpostaDocumento) obj;
        //if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idDocumento==null && other.getIdDocumento()==null) || 
             (this.idDocumento!=null &&
              this.idDocumento.equals(other.getIdDocumento()))) &&
            ((this.indiceClassificazione==null && other.getIndiceClassificazione()==null) || 
             (this.indiceClassificazione!=null &&
              this.indiceClassificazione.equals(other.getIndiceClassificazione()))) &&
            ((this.protocollo==null && other.getProtocollo()==null) || 
             (this.protocollo!=null &&
              this.protocollo.equals(other.getProtocollo())));
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
        if (getIdDocumento() != null) {
            _hashCode += getIdDocumento().hashCode();
        }
        if (getIndiceClassificazione() != null) {
            _hashCode += getIndiceClassificazione().hashCode();
        }
        if (getProtocollo() != null) {
            _hashCode += getProtocollo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    static {
    	typeDesc =  new org.apache.axis.description.TypeDesc(ResponseSpostaDocumento.class, true);
    }

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:stadocStadoc", "ResponseSpostaDocumento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indiceClassificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indiceClassificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocollo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "protocollo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }
}
