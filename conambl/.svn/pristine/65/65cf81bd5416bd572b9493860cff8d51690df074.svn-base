/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

import it.csi.conam.conambl.vo.AzioneVO;

public enum AzioneVerbale {
	ACQUISISCI(1L, "Acquisisci", Constants.STATO_VERBALE_ACQUISITO, true), //
	ACQUISISCI_CON_PAGAMENTO(2L, "Acquisisci con Pagamento", Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, true), //
	ACQUISISCI_CON_SCRITTI(3L, "Acquisisci con scritti difensivi", Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI, true), //
	ARCHIVIATO_IN_AUTOTUTELA(4L, "Archivia in Autotutela", Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA, false);//
	
	private final Long id;
	private final String azione;
	private final Long idStatoAvanzabile;
	private final boolean listaIstruttori;

	AzioneVerbale(Long id, String azione, Long idStatoAvanzabile, boolean listaIstruttori) {
		this.id = id;
		this.azione = azione;
		this.idStatoAvanzabile = idStatoAvanzabile;
		this.listaIstruttori = listaIstruttori;
	}

	public Long getId() {
		return id;
	}





	public static AzioneVO getAzioneVerbaleByIdStato(Long idStato) {
		if (idStato == null)
			return null;
		for (AzioneVerbale a : AzioneVerbale.values()) {
			if (a.idStatoAvanzabile.equals(idStato)) {
				AzioneVO azione = new AzioneVO();
				azione.setDenominazione(a.azione);
				azione.setId(a.getId());
				azione.setListaIstruttoriEnable(a.isListaIstruttori());
				return azione;
			}
		}

		return null;
	}


	public boolean isListaIstruttori() {
		return listaIstruttori;
	}
}
