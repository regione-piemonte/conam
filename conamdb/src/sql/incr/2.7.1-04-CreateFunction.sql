-- ENCODING
SET client_encoding TO 'UTF8';

CREATE OR REPLACE FUNCTION pulisciaccento(pdenomcomune character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
DECLARE
  sDenomComune  cnm_d_comune.denom_comune%TYPE;
  sCarattere    VARCHAR(1);
begin
  sDenomComune = ' ';
  FOR i IN 1 .. LENGTH(pDenomComune)
  LOOP
    sCarattere = SUBSTR(pDenomComune,i,1);
   
	-- RAISE NOTICE 'sCarattere %, ascii %', sCarattere, ASCII(sCarattere) ; 
    IF ASCII(sCarattere) = 224 OR ASCII(sCarattere) = 225 THEN
      sDenomComune = sDenomComune || 'a''';
    ELSIF ASCII(sCarattere) = 192 OR ASCII(sCarattere) = 193 THEN
      sDenomComune = sDenomComune || 'A''';--
    ELSIF ASCII(sCarattere) = 232 OR ASCII(sCarattere) = 233 THEN
      sDenomComune = sDenomComune || 'e''';
    ELSIF ASCII(sCarattere) = 200 OR ASCII(sCarattere) = 201 THEN
      sDenomComune = sDenomComune || 'E''';
    ELSIF ASCII(sCarattere) = 236 OR ASCII(sCarattere) = 237 THEN
      sDenomComune = sDenomComune || 'i''';
    ELSIF ASCII(sCarattere) = 204 OR ASCII(sCarattere) = 205 THEN
      sDenomComune = sDenomComune || 'I''';
    ELSIF ASCII(sCarattere) = 242 OR ASCII(sCarattere) = 243 THEN
      sDenomComune = sDenomComune || 'o''';
    ELSIF ASCII(sCarattere) = 210 OR ASCII(sCarattere) = 211  THEN
      sDenomComune = sDenomComune || 'O''';
    ELSIF ASCII(sCarattere) = 249 OR ASCII(sCarattere) = 250 THEN
      sDenomComune = sDenomComune || 'u''';
    ELSIF ASCII(sCarattere) = 217 OR ASCII(sCarattere) = 218  THEN
      sDenomComune = sDenomComune || 'U''';
    ELSE
      sDenomComune = sDenomComune || sCarattere;
    END IF;
  END LOOP;
  -------
  RETURN TRIM(sDenomComune);
END;
$function$
;
