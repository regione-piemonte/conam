INSERT INTO cnm_c_field
(id_field, id_tipo_allegato, id_field_type, "label", max_length, "scale", required, ordine, riga, id_elenco, inizio_validita, fine_validita)
VALUES
	(26, 43, 3, 'Data di pagamento', NULL, NULL, true, 0, 0, NULL, '2018-12-12', NULL),
    (27, 43, 8, 'Soggetto Pagante', 50, NULL, true, 1, 1, NULL, '2023-09-01', NULL),
	(28, 43, 2, 'Tipologia pagamento', 50, NULL, true, 3, 2, NULL, '2018-12-12', NULL),
	(29, 43, 2, 'Reversale d''ordine', 100, NULL, false, 2, 1, NULL, '2023-02-14', NULL),
	(30, 43, 2, 'Note', 500, NULL, false, 4, 2, NULL, '2023-02-14', NULL),
	
	(31, 44, 1, 'Importo pagato', 8, 1, 'true', 2, 0, NULL, '2024-02-29', NULL),
	(32, 44, 2, 'Pagatore', 100, NULL, false, 6, 1, NULL, '2024-02-29', NULL),
	(33, 44, 2, 'Note', 500, NULL, false, 3, 1, NULL, '2024-03-14', NULL),
	(34, 44, 3, 'Data pagamento', NULL, NULL, 'true', 1, 0, NULL, '2024-02-29', NULL),
	(35, 44, 2, 'Reversale d''ordine', 100, NULL, false, 2, 1, NULL, '2024-03-14', NULL),	
		
	(36, 45, 3, 'Data di pagamento', NULL, NULL, 'true', 1, 0, NULL, '2024-02-29', NULL),
	(37, 45, 2, 'Tipologia pagamento', 50, NULL, true, 3, 2, NULL, '2018-12-12', NULL),
	(38, 45, 2, 'Pagatore', 100, NULL, false, 1, 1, NULL, '2024-03-14', NULL),
	(39, 45, 1, 'Importo pagato', 8, 1, 'true', 2, 0, NULL, '2024-02-29', NULL),
	(40, 45, 2, 'Reversale d''ordine', 100, NULL, false, 2, 1, NULL, '2024-03-14', NULL),
	(41, 45, 2, 'Note', 500, NULL, false, 3, 1, NULL, '2024-03-14', NULL),
	
	(42, 46, 3, 'Data di pagamento', NULL, NULL, 'true', 0, 0, NULL, '2024-02-29', NULL),
	(43, 46, 2, 'Tipologia pagamento', 50, NULL, true, 2, 1, NULL, '2018-12-12', NULL),
	(44, 46, 2, 'Pagatore', 100, NULL, false, 4, 2, NULL, '2024-03-14', NULL),
	(45, 46, 1, 'Importo pagato', 8, 1, 'true', 1, 0, NULL, '2024-02-29', NULL),
	(46, 46, 2, 'Reversale d''ordine', 100, NULL, false, 3, 1, NULL, '2024-03-14', NULL),
	(47, 46, 2, 'Note', 500, NULL, false, 5, 2, NULL, '2024-03-14', NULL)