

ALTER TABLE cnm_t_ordinanza ADD id_causale numeric(2) default NULL;
ALTER TABLE cnm_t_ordinanza ADD numero_accertamento varchar(100) default NULL;
ALTER TABLE cnm_t_ordinanza ADD anno_accertamento NUMERIC(4) default NULL;

ALTER TABLE cnm_t_ordinanza
	ADD CONSTRAINT  fk_t_ordinanza_05 FOREIGN KEY (id_causale) REFERENCES cnm_d_causale(id_causale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
	

ALTER TABLE cnm_t_notifica ADD id_causale numeric(2) default NULL;
ALTER TABLE cnm_t_notifica ADD numero_accertamento varchar(100) default NULL;
ALTER TABLE cnm_t_notifica ADD anno_accertamento numeric(4) default NULL;
	
ALTER TABLE cnm_t_notifica
	ADD CONSTRAINT  fk_t_notifica_02 FOREIGN KEY (id_causale) REFERENCES cnm_d_causale(id_causale) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;