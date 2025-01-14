package it.csi.conam.conambl.integration.auriga.bean;

public enum TipoRegistrazione {

	PROTOCOLLO("PG"),
	REPERTORIO("R"), 
	ALTRA_NUMERAZIONE("A");

	private String value;

	public String getValue() {
		return value;
	}

	TipoRegistrazione(String value) {
		this.value = value;
	}
	

}
