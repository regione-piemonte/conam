/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.request;

import java.io.Serializable;
import java.util.List;

import it.csi.conam.conambl.vo.verbale.allegato.AllegatoFieldVO;

/**
 * @author riccardo.bova
 * @date 9 jul 2024
 */
public class AllegatiAlMaster implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private byte[] file;
	private String filename;
	private String oggetto;
	private Long origine;


	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Long getOrigine() {
		return origine;
	}

	public void setOrigine(Long origine) {
		this.origine = origine;
	}
	
}
