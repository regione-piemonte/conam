-- ENCODING
SET client_encoding TO 'UTF8';

-- Endpoint epay senza token di autenticazione
INSERT INTO cnm_c_proprieta VALUES (30, 'EPAY_ENDPOINT', 'http://tst-api-ent.ecosis.csi.it/api/BILANCIO_epay_Enti2Epaywso/1.0');
-- Endpoint epay con token di autenticazione
INSERT INTO cnm_c_proprieta VALUES (31, 'EPAY_ENDPOINT_SECURED', 'https://tu-exp-srv-paywso-fe.bilancio.csi.it/wso001/services/Enti2EPaywsoSecureProxy');
-- Flag che indica se usare l'endpoint standard (FALSE) oppure quello securizzato (TRUE)
INSERT INTO cnm_c_proprieta VALUES (32, 'EPAY_WSSECURED', 'FALSE');
-- Utente per la creazione del token su endpoint securizzato
INSERT INTO cnm_c_proprieta VALUES (33, 'EPAY_WSUSER', 'CONAM-TEST');
-- Password per la creazione del token su endpoint securizzato
INSERT INTO cnm_c_proprieta VALUES (34, 'EPAY_WSPASS', 'yg4aIAMSJkEy293y');
