/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.ordinanza;

import it.csi.conam.conambl.integration.entity.CnmTOrdinanza;
import it.csi.conam.conambl.integration.entity.CnmTVerbale;

public interface UtilsOrdinanza {
    CnmTOrdinanza validateAndGetCnmTOrdinanza(Integer idOrdinanza) ;
    
    boolean gestisciOrdinanzaComePregressa(Integer idOrdinanza);
    
    boolean soggettiVerbaleCompletiDiOrdinanza(CnmTVerbale cnmTVerbale);
}
