/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.common;

import it.csi.conam.conambl.vo.AzioneVO;

public enum AzioneVerbalePregressi {
	ACQUISISCI(1L, "Acquisisci", Constants.STATO_VERBALE_ACQUISITO, false), //
	ACQUISISCI_CON_PAGAMENTO(2L, "Acquisisci con Pagamento", Constants.STATO_VERBALE_ACQUISITO_CON_PAGAMENTO, false), //
	ACQUISISCI_CON_SCRITTI(3L, "Acquisisci con scritti difensivi", Constants.STATO_VERBALE_ACQUISITO_CON_SCRITTI_DIFENSIVI, false), //
	ARCHIVIATO_IN_AUTOTUTELA(4L, "Archivia in Autotutela", Constants.STATO_VERBALE_ARCHIVIATO_IN_AUTOTUTELA, false),//
	TERMINE_RECUPERO_PREGRESSO(5L, "Recupero pregresso terminato", Constants.STATO_VERBALE_FINE_PREGRESSO, true),//
	ORDINANZA(6L, "Ordinanza", Constants.STATO_VERBALE_ORDINANZA, false);//

	private final Long id;
	private final String azione;
	private final Long idStatoAvanzabile;
	private final boolean listaIstruttori;

	AzioneVerbalePregressi(Long id, String azione, Long idStatoAvanzabile, boolean listaIstruttori) {
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
		for (AzioneVerbalePregressi a : AzioneVerbalePregressi.values()) {
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
