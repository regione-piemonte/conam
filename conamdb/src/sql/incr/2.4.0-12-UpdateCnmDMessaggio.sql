-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDMessaggio

UPDATE cnm_d_messaggio set desc_messaggio = 'i documenti associati al protocollo cercato sono già presenti nella base dati Conam.'
where cod_messaggio = 'SRCHPROT02'; 