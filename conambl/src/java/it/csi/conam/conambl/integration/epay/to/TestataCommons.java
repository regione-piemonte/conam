/**
 * TestataAggiornaPosizioniDebitorie.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 13, 2013 (09:13:21 GMT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.epay.to;

public class TestataCommons  implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6470279889775196184L;

	protected java.lang.String idMessaggio;

    protected java.lang.String CFEnteCreditore;

    protected java.lang.String codiceVersamento;

    protected java.lang.Boolean multiBeneficiario;

    protected java.math.BigInteger numeroPosizioniDebitorie;

    public TestataCommons() {
    }

    public TestataCommons(
        java.lang.String idMessaggio,
        java.lang.String CFEnteCreditore,
        java.lang.String codiceVersamento,
        java.lang.Boolean multiBeneficiario,
        java.math.BigInteger numeroPosizioniDebitorie) {
            this.idMessaggio = idMessaggio;
            this.CFEnteCreditore = CFEnteCreditore;
            this.codiceVersamento = codiceVersamento;
            this.multiBeneficiario = multiBeneficiario;
            this.numeroPosizioniDebitorie = numeroPosizioniDebitorie;
    }


    /**
     * Gets the idMessaggio value for this TestataAggiornaPosizioniDebitorie.
     * 
     * @return idMessaggio
     */
    public java.lang.String getIdMessaggio() {
        return idMessaggio;
    }


    /**
     * Sets the idMessaggio value for this TestataAggiornaPosizioniDebitorie.
     * 
     * @param idMessaggio
     */
    public void setIdMessaggio(java.lang.String idMessaggio) {
        this.idMessaggio = idMessaggio;
    }


    /**
     * Gets the CFEnteCreditore value for this TestataAggiornaPosizioniDebitorie.
     * 
     * @return CFEnteCreditore
     */
    public java.lang.String getCFEnteCreditore() {
        return CFEnteCreditore;
    }


    /**
     * Sets the CFEnteCreditore value for this TestataAggiornaPosizioniDebitorie.
     * 
     * @param CFEnteCreditore
     */
    public void setCFEnteCreditore(java.lang.String CFEnteCreditore) {
        this.CFEnteCreditore = CFEnteCreditore;
    }


    /**
     * Gets the codiceVersamento value for this TestataAggiornaPosizioniDebitorie.
     * 
     * @return codiceVersamento
     */
    public java.lang.String getCodiceVersamento() {
        return codiceVersamento;
    }


    /**
     * Sets the codiceVersamento value for this TestataAggiornaPosizioniDebitorie.
     * 
     * @param codiceVersamento
     */
    public void setCodiceVersamento(java.lang.String codiceVersamento) {
        this.codiceVersamento = codiceVersamento;
    }


    /**
     * Gets the multiBeneficiario value for this TestataAggiornaPosizioniDebitorie.
     * 
     * @return multiBeneficiario
     */
    public java.lang.Boolean getMultiBeneficiario() {
        return multiBeneficiario;
    }


    /**
     * Sets the multiBeneficiario value for this TestataAggiornaPosizioniDebitorie.
     * 
     * @param multiBeneficiario
     */
    public void setMultiBeneficiario(java.lang.Boolean multiBeneficiario) {
        this.multiBeneficiario = multiBeneficiario;
    }


    /**
     * Gets the numeroPosizioniDebitorie value for this TestataAggiornaPosizioniDebitorie.
     * 
     * @return numeroPosizioniDebitorie
     */
    public java.math.BigInteger getNumeroPosizioniDebitorie() {
        return numeroPosizioniDebitorie;
    }


    /**
     * Sets the numeroPosizioniDebitorie value for this TestataAggiornaPosizioniDebitorie.
     * 
     * @param numeroPosizioniDebitorie
     */
    public void setNumeroPosizioniDebitorie(java.math.BigInteger numeroPosizioniDebitorie) {
        this.numeroPosizioniDebitorie = numeroPosizioniDebitorie;
    }

    // Type metadata
    protected static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TestataAggiornaPosizioniDebitorie.class, true);

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
