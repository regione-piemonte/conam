/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import java.util.Arrays;

import it.csi.conam.conambl.vo.ParentVO;

public class DocumentoStiloVO extends ParentVO {

	private static final long serialVersionUID = -7714298531166937914L;

	private byte[] file;
	private String filename;
	private String oggetto;
	
	private String numero;
	private String anno;

	private String data;
	
	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DocumentoStiloVO [file=" + Arrays.toString(file) + ", filename=" + filename + ", oggetto=" + oggetto
				+ ", numero=" + numero + ", anno=" + anno + ", data=" + data + "]";
	}

}
