SET client_encoding TO 'WIN1258';

ALTER TABLE cnm_t_allegato 
ALTER COLUMN nome_file TYPE varchar(200) USING nome_file::varchar;
 
ALTER TABLE cnm_t_scritto_difensivo 
ALTER COLUMN nome_file TYPE varchar(200) USING nome_file::varchar;