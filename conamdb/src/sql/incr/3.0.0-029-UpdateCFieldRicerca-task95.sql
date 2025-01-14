UPDATE cnm_c_field_ricerca
SET id_tipo_ricerca=2, id_field_type=1, "label"='Numero', operatore='EQUALS', max_length=100, "scale"=NULL, required=true, ordine=1, riga=1, id_elenco=NULL, inizio_validita='2024-06-24' 
WHERE id_field_ricerca=1;
UPDATE cnm_c_field_ricerca
SET id_tipo_ricerca=2, id_field_type=2, "label"='Anno', operatore='EQUALS', max_length=100, "scale"=NULL, required=true, ordine=2, riga=1, id_elenco=NULL, inizio_validita='2024-06-24' 
WHERE id_field_ricerca=2;