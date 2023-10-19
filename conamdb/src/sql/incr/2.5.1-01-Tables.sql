-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%		NEW TABLES										%%%%%%%%%%%%%%%%%%%%%%%%
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

CREATE TABLE cnm_d_causale (
	id_causale numeric(2) NOT NULL,
	desc_causale varchar(100) NOT NULL,
	inizio_validita date NOT NULL DEFAULT 'now'::text::date,
	fine_validita date NULL
);


ALTER TABLE cnm_d_causale
	ADD CONSTRAINT  pk_t_causale PRIMARY KEY (id_causale);