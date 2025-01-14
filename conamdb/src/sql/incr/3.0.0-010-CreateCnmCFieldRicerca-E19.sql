CREATE TABLE IF NOT EXISTS "cnm_c_field_ricerca" (
	"id_field_ricerca" NUMERIC(5,0) NOT NULL,
	"id_tipo_ricerca" NUMERIC(2,0) NOT NULL,
	"id_field_type" NUMERIC(2,0) NOT NULL,
	"label" VARCHAR(100) NOT NULL,
	"operatore" VARCHAR(100) NOT NULL,
	"max_length" NUMERIC(3,0) NULL DEFAULT NULL,
	"scale" NUMERIC(2,0) NULL DEFAULT NULL,
	"required" BOOLEAN NOT NULL,
	"ordine" NUMERIC(3,0) NOT NULL,
	"riga" NUMERIC(2,0) NOT NULL,
	"id_elenco" NUMERIC(2,0) NULL DEFAULT NULL,
	"inizio_validita" DATE NOT NULL DEFAULT CURRENT_DATE,
	"fine_validita" DATE NULL DEFAULT NULL,
	PRIMARY KEY ("id_field_ricerca"),
	CONSTRAINT "fk_c_field_01" FOREIGN KEY ("id_tipo_ricerca") REFERENCES "cnm_d_tipo_ricerca" ("id_tipo_ricerca") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_c_field_02" FOREIGN KEY ("id_field_type") REFERENCES "cnm_c_field_type" ("id_field_type") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fk_c_field_03" FOREIGN KEY ("id_elenco") REFERENCES "cnm_d_elenco" ("id_elenco") ON UPDATE NO ACTION ON DELETE NO ACTION
);