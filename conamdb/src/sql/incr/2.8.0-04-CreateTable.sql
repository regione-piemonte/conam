-- Drop table

-- DROP TABLE conam.cnm_d_ambito_note;

CREATE TABLE cnm_d_ambito_note (
	id_ambito_note numeric(2) NOT NULL,
	desc_ambito_note varchar(100) NOT NULL,
	inizio_validita date NOT NULL DEFAULT 'now'::text::date,
	fine_validita date NULL,
	CONSTRAINT pk_d_ambito_note PRIMARY KEY (id_ambito_note)
);


-- Drop table

-- DROP TABLE conam.cnm_t_nota;

CREATE TABLE cnm_t_nota (
	id_nota serial NOT NULL,
	oggetto varchar(50) NOT NULL,
	desc_nota varchar(1000) NOT NULL,
	date_nota timestamptz(0) NOT NULL,
	id_ambito_note int4 NOT NULL,
	id_verbale int4 NOT NULL,
	id_user_insert numeric(3) NOT NULL,
	data_ora_insert timestamptz NOT NULL,
	id_user_update numeric(3) NULL,
	data_ora_update timestamptz NULL,
	CONSTRAINT pk_t_nota PRIMARY KEY (id_nota)
);
