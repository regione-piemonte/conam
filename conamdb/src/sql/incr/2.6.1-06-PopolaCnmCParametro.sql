-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (38, 'Info Ente web', 'la Regione Piemonte – Settore Politiche Fiscali - Contenzioso Amministrativo – Corso Regina Margherita n.153 bis - TORINO; – (Palazzina A piano I)', NULL, NULL, NULL, '2023-06-05', NULL);

INSERT INTO cnm_c_parametro (id_parametro, desc_parametro, valore_string, valore_number, valore_date, valore_boolean, inizio_validita, fine_validita)
VALUES (39, 'Info Ente testo', 'la Regione Piemonte – Settore Politiche Fiscali - Contenzioso Amministrativo – Corso Regina Margherita n.153 bis - TORINO – (Palazzina A piano I)', NULL, NULL, NULL, '2023-06-05', NULL);

UPDATE cnm_c_parametro set valore_string = 'Corso Regina Margherita, 153 bis;10122  TO;Tel: 011 4321288;Fax: 011 4321286;Email: settore.tributi@regione.piemonte.it'
WHERE id_parametro = 26;
