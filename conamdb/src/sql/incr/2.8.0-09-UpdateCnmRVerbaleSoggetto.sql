UPDATE cnm_r_verbale_soggetto
SET importo_mis_ridotta = cnm_t_verbale.importo_verbale
FROM cnm_t_verbale, cnm_t_soggetto
WHERE cnm_r_verbale_soggetto.id_soggetto = cnm_t_soggetto.id_soggetto
AND cnm_r_verbale_soggetto.id_verbale = cnm_t_verbale.id_verbale
AND cnm_r_verbale_soggetto.id_verbale NOT IN (
	select crav.id_verbale from cnm_r_allegato_verbale crav, cnm_t_allegato cta, cnm_d_tipo_allegato cdta 
	where crav.id_allegato = cta.id_allegato and cta.id_tipo_allegato = cdta.id_tipo_allegato 
	and cdta.id_tipo_allegato = 7
);