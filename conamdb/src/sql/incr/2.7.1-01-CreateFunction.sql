-- ENCODING
SET client_encoding TO 'UTF8';

CREATE OR REPLACE FUNCTION findidComune(dt_id_comune numeric)
 RETURNS smallint
 LANGUAGE plpgsql
AS $function$
DECLARE
  nIdComune  dt_limiti_amministrativi_div.id_comune%TYPE;
BEGIN
  SELECT  dlad.id_comune_next
    INTO  nIdComune
    FROM  dt_limiti_amministrativi_div dlad
   where dlad.id_comune = dt_id_comune;
  
   if(nIdComune = 0) then   
  		RETURN dt_id_comune;   
   else
   		return findidComune(nIdComune);
   END IF;
   
END;
$function$
;
