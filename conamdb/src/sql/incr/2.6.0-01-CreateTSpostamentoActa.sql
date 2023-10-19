
-----------------------------
-- TABELLE ONLINE -----------
-----------------------------

--	CnmTSpostamentoActa
CREATE TABLE cnm_t_spostamento_acta (
	id SERIAL NOT NULL,
	id_verbale int4 NOT NULL,
	id_documento_master int4 NOT NULL,
	numero_protocollo VARCHAR(50),
	folder VARCHAR(500),
	operazione VARCHAR(10),
	stato VARCHAR(50),
	prenotazione_id VARCHAR(300),
	data_ora_insert TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	data_ora_end TIMESTAMP,
	info VARCHAR(250),
	parolaChiaveTemp VARCHAR(250)	
);

ALTER TABLE cnm_t_spostamento_acta ADD CONSTRAINT pk_t_spostamento_acta PRIMARY KEY (id);
