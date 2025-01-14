-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDMessaggio

INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio)
VALUES('SOGNORES', 'Nessun soggetto trasgressore presenta un importo residuo maggiore di zero', 1);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio)
VALUES('SOGTRNORES', 'Per il soggetto trasgressore selezionato è già stato saldato il dovuto', 1);
