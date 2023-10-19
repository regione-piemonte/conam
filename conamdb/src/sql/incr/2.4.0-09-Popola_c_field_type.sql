SET client_encoding TO 'WIN1258';

INSERT INTO cnm_c_field_type
(
  id_field_type,
  desc_field_type
)
VALUES
(
  7,
  'Elenco Soggetti'
);


INSERT INTO cnm_c_field
(
  id_field,
  id_tipo_allegato,
  id_field_type,
  label,
  max_length,
  required,
  ordine,
  riga,
  inizio_validita
)
VALUES
(
  20,
  5,
  7,
  'Soggetto',
  50,
  TRUE,
  0,
  0,
  '2021-11-01'
);

update cnm_c_field set riga = 1 where id_field in (3,6);
update cnm_c_field set riga = 2 where id_field in (1,2);
update cnm_c_field set riga = 3 where id_field in (4,5);