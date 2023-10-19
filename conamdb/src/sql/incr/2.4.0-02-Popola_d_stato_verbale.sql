SET client_encoding TO 'WIN1258';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		NUOVO STATO VERBALE								%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

INSERT INTO cnm_d_stato_verbale
(
  id_stato_verbale,
  desc_stato_verbale
)
VALUES
(
  16,
  'Improcedibile per mancata identificazione '
);

INSERT INTO cnm_d_stato_verbale
(
  id_stato_verbale,
  desc_stato_verbale
)
VALUES
(
  17,
  'In attesa verifica di pagamento '
);

INSERT INTO cnm_d_stato_verbale
(
  id_stato_verbale,
  desc_stato_verbale
)
VALUES
(
  18,
  'Protocollazione in attesa verifica di pagamento '
); 

INSERT INTO cnm_d_stato_verbale
(
  id_stato_verbale,
  desc_stato_verbale
)
VALUES
(
  19,
  'Prot. improcedibile per mancata identificazione '
);