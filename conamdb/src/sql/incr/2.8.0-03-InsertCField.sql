
INSERT INTO cnm_c_field_type
(id_field_type, desc_field_type)
VALUES(8, 'Elenco Soggetti Com');


INSERT INTO cnm_c_field
(id_field, id_tipo_allegato, id_field_type, "label", max_length, "scale", required, ordine, riga, id_elenco, inizio_validita, fine_validita)
VALUES(21, 7, 8, 'Soggetto', 50, NULL, true, 0, 0, NULL, '2023-09-01', NULL);


