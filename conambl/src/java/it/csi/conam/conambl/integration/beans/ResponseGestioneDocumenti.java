
package it.csi.conam.conambl.integration.beans;

public abstract class ResponseGestioneDocumenti  implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -11153253689232469L;

	protected java.lang.String idDocumento;

    // 20201120_LC
    protected java.lang.String objectIdDocumento;

    public ResponseGestioneDocumenti() {
    }

    public ResponseGestioneDocumenti(
        java.lang.String idDocumento,
        java.lang.String objectIdDocumento) {
            this.idDocumento = idDocumento;
            this.objectIdDocumento = objectIdDocumento;
    }


    /**
     * Gets the idDocumento value for this ResponseGestioneDocumenti.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdDocumento() {
        return idDocumento;
    }


    /**
     * Sets the idDocumento value for this ResponseGestioneDocumenti.
     * 
     * @param idDocumento
     */
    public void setIdDocumento(java.lang.String idDocumento) {
        this.idDocumento = idDocumento;
    }

    
    /**
     * Gets the objectIdDocumento value for this ResponseGestioneDocumenti.
     * 
     * @return objectIdDocumento
     */
    public java.lang.String getObjectIdDocumento() {
        return objectIdDocumento;
    }


    /**
     * Sets the objectIdDocumento value for this ResponseGestioneDocumenti.
     * 
     * @param objectIdDocumento
     */
    public void setObjectIdDocumento(java.lang.String objectIdDocumento) {
        this.objectIdDocumento = objectIdDocumento;
    }


    // Type metadata
    protected static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseGestioneDocumenti.class, true);

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
        java.lang.Class<?> _javaType,  
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
        java.lang.Class<?> _javaType,  
        javax.xml.namespace.QName _xmlType) {
        return 
        new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
