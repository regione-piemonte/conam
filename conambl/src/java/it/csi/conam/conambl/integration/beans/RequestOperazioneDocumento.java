package it.csi.conam.conambl.integration.beans;

public abstract class RequestOperazioneDocumento implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8436774435935945401L;

	protected java.lang.String codiceFruitore;

    protected java.lang.String idDocumento;

    public RequestOperazioneDocumento() {
    }

    public RequestOperazioneDocumento(
        java.lang.String codiceFruitore,
        java.lang.String idDocumento) {
        this.codiceFruitore = codiceFruitore;
        this.idDocumento = idDocumento;
    }

    /**
     * Gets the codiceFruitore value for this RequestOperazioneDocumento.
     * 
     * @return codiceFruitore
     */
    public java.lang.String getCodiceFruitore() {
        return codiceFruitore;
    }

    /**
     * Sets the codiceFruitore value for this RequestOperazioneDocumento.
     * 
     * @param codiceFruitore
     */
    public void setCodiceFruitore(java.lang.String codiceFruitore) {
        this.codiceFruitore = codiceFruitore;
    }

    /**
     * Gets the idDocumento value for this RequestOperazioneDocumento.
     * 
     * @return idDocumento
     */
    public java.lang.String getIdDocumento() {
        return idDocumento;
    }

    /**
     * Sets the idDocumento value for this RequestOperazioneDocumento.
     * 
     * @param idDocumento
     */
    public void setIdDocumento(java.lang.String idDocumento) {
        this.idDocumento = idDocumento;
    }

    // Type metadata
    protected static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RequestOperazioneDocumento.class, true);

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
