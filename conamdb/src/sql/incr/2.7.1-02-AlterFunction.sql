-- ENCODING
SET client_encoding TO 'UTF8';

CREATE OR REPLACE FUNCTION aggiornacomuni()
 RETURNS smallint
 LANGUAGE plpgsql
AS $function$
DECLARE
  rec       RECORD;
  nRetCode  SMALLINT;
BEGIN
  -- Per primi elaboro i cessati
  FOR rec IN  SELECT  id_comune
                    , id_comune_prev
                    , id_comune_next
                    , istat_comune
                    , cod_catasto
                    , desc_comune
                    , istat_provincia
                    , d_start
                    , d_stop
                FROM  dt_limiti_amministrativi_div
                WHERE r_status != '0'
                ORDER BY  d_start
  LOOP
    nRetCode = AggiornaComune( rec.id_comune, rec.id_comune_prev, rec.id_comune_next, rec.istat_comune, rec.cod_catasto, rec.desc_comune, GetIdProvincia(rec.istat_provincia), rec.d_start, rec.d_stop);
  END LOOP;
 
  /**
BEGIN
  -- Per primi elaboro i cessati
  FOR rec IN  SELECT  id_comune
                    , id_comune_prev
                    , id_comune_next
                    , istat_comune
                    , cod_catasto
                    , desc_comune
                    , istat_provincia
                    , d_start
                    , d_stop
                FROM  dt_limiti_amministrativi_div
                WHERE r_status = '0'
                ORDER BY  d_start
  LOOP
    nRetCode = AggiornaComune( rec.id_comune, rec.id_comune_prev, rec.id_comune_next, rec.istat_comune, rec.cod_catasto, rec.desc_comune, GetIdProvincia(rec.istat_provincia), rec.d_start, rec.d_stop);
  END LOOP;
  -- Poi elaboro i modificati
  FOR rec IN  SELECT  id_comune
                    , id_comune_prev
                    , id_comune_next
                    , istat_comune
                    , cod_catasto
                    , desc_comune
                    , istat_provincia
                    , d_start
                    , d_stop
                FROM  dt_limiti_amministrativi_div
                WHERE r_status = '2'
                ORDER BY  d_start
  LOOP
    nRetCode = AggiornaComune( rec.id_comune, rec.id_comune_prev, rec.id_comune_next, rec.istat_comune, rec.cod_catasto, rec.desc_comune, GetIdProvincia(rec.istat_provincia), rec.d_start, rec.d_stop);
  END LOOP;
  -- Poi elaboro gli attivi
  FOR rec IN  SELECT  id_comune
                    , id_comune_prev
                    , id_comune_next
                    , istat_comune
                    , cod_catasto
                    , desc_comune
                    , istat_provincia
                    , d_start
                    , d_stop
                FROM  dt_limiti_amministrativi_div
                WHERE r_status = '1'
                ORDER BY  d_start
  LOOP
    nRetCode = AggiornaComune( rec.id_comune, rec.id_comune_prev, rec.id_comune_next, rec.istat_comune, rec.cod_catasto, rec.desc_comune, GetIdProvincia(rec.istat_provincia), rec.d_start, rec.d_stop);
  END LOOP;
  **/
  -------
  RETURN 0;
END;
$function$
;



CREATE OR REPLACE FUNCTION aggiornacomune(pidcomune numeric, pidcomuneprev numeric, pidcomunenext numeric, pcodistatcomune character varying, pcodbelfiorecomune character varying, pdenomcomune character varying, pidprovincia numeric, piniziovalidita date, pfinevalidita date)
 RETURNS smallint
 LANGUAGE plpgsql
AS $function$
DECLARE
  nRecs         cnm_d_comune.id_comune%TYPE;
  sDenomComune  cnm_d_comune.denom_comune%TYPE;
BEGIN
  sDenomComune = PulisciDenominazione(pDenomComune);
  -- Se Esiste dt_id_comune negli attivi eseguo l'update e termino
  UPDATE  cnm_d_comune
    SET cod_istat_comune = pCodIstatComune
      , cod_belfiore_comune = pCodBelfioreComune
      , denom_comune = sDenomComune
      , id_provincia = pIdProvincia
      , inizio_validita = pInizioValidita
      , fine_validita = pFineValidita
      , dt_id_comune_prev = pIdComunePrev
      , dt_id_comune_next = pIdComuneNext
    WHERE dt_id_comune = pIdComune;
  GET DIAGNOSTICS nRecs = ROW_COUNT;
 --RAISE NOTICE '1 pid %, nRecs %', pIdComune, nRecs ; 
  IF nRecs = 0 THEN
  -- Se Esiste dt_id_comune nello storico eseguo l'update e termino
    UPDATE  cnm_s_comune
      SET cod_istat_comune = pCodIstatComune
        , cod_belfiore_comune = pCodBelfioreComune
        , denom_comune = sDenomComune
        , id_provincia = pIdProvincia
        , inizio_validita = pInizioValidita
        , fine_validita = pFineValidita
        , dt_id_comune_prev = pIdComunePrev
        , dt_id_comune_next = pIdComuneNext
      WHERE dt_id_comune = pIdComune;
    --------
    GET DIAGNOSTICS nRecs = ROW_COUNT;
    IF nRecs = 0 then
    
 --		RAISE NOTICE '2 pid %, nRecs %', pIdComune, nRecs ; 
      -- Se Esiste dt_id_comune_prev tra i dt_id_comune degli attivi sposto il record nello storico e aggiorno il record negli attivi
      INSERT INTO cnm_s_comune  ( id_s_comune, id_comune, cod_istat_comune, cod_belfiore_comune, denom_comune, id_provincia, inizio_validita, fine_validita
                                , dt_id_comune, dt_id_comune_prev, dt_id_comune_next
                                ) 
        SELECT  GetNextIdScomune(), id_comune, pCodIstatComune, pCodBelfioreComune, sDenomComune, pIdProvincia, pInizioValidita, pFineValidita
              , pidcomune, pIdComunePrev, pIdComuneNext
          FROM  cnm_d_comune 
          where dt_id_comune = findidcomune(pidcomune);
      GET DIAGNOSTICS nRecs = ROW_COUNT;
-- 		RAISE NOTICE '3 pid %, nRecs %', pIdComune, nRecs ; 
      IF nRecs = 0 THEN
        -- Controllo se esiste un record identico tra gli attivi (in quel caso termino)
        SELECT  COUNT(1)
          INTO  nRecs
          FROM  cnm_d_comune
          WHERE COALESCE(cod_istat_comune,' ') = COALESCE(pCodIstatComune,' ')
            AND COALESCE(cod_belfiore_comune,' ') = COALESCE(pCodBelfioreComune,' ')
            AND denom_comune = sDenomComune
            AND id_provincia = pIdProvincia
            AND inizio_validita = pInizioValidita
            AND COALESCE(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = COALESCE(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
        IF nRecs = 0 THEN
          -- Controllo se esiste un record identico nello storico (in quel caso termino)
          SELECT  COUNT(1)
            INTO  nRecs
            FROM  cnm_s_comune
            WHERE COALESCE(cod_istat_comune,' ') = COALESCE(pCodIstatComune,' ')
              AND COALESCE(cod_belfiore_comune,' ') = COALESCE(pCodBelfioreComune,' ')
              AND denom_comune = sDenomComune
              AND id_provincia = pIdProvincia
              AND inizio_validita = pInizioValidita
              AND COALESCE(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = COALESCE(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
          IF nRecs = 0 THEN
            -- Se esiste un record con almeni istat, denominazione e provincia uguali lo sposto nello storico e aggiorno il record negli attivi
            INSERT INTO cnm_s_comune  ( id_s_comune, id_comune, cod_istat_comune, cod_belfiore_comune, denom_comune, id_provincia, inizio_validita, fine_validita
                                      , dt_id_comune, dt_id_comune_prev, dt_id_comune_next
                                      )
              SELECT  GetNextIdScomune(), id_comune, pCodIstatComune, pCodBelfioreComune, sDenomComune, pIdProvincia, pInizioValidita, pFineValidita
                    , dt_id_comune, dt_id_comune_prev, dt_id_comune_next
                FROM  cnm_d_comune
          		where dt_id_comune = findidcomune(pidcomune);
          GET DIAGNOSTICS nRecs = ROW_COUNT;
          IF nRecs = 0 THEN
            INSERT INTO cnm_d_comune  ( id_comune, cod_istat_comune, cod_belfiore_comune, denom_comune, id_provincia, inizio_validita, fine_validita
                                      , dt_id_comune, dt_id_comune_prev, dt_id_comune_next
                                      )
              VALUES  ( GetNextIdComune(), pCodIstatComune, pCodBelfioreComune, sDenomComune, pIdProvincia, pInizioValidita, pFineValidita
                      , pIdComune, pIdComunePrev, pIdComuneNext
                      );
          ELSE
            UPDATE  cnm_d_comune
              SET inizio_validita = pInizioValidita
                , fine_validita = pFineValidita
                , dt_id_comune = pIdComune
                , dt_id_comune_prev = pIdComunePrev
                , dt_id_comune_next = pIdComuneNext
              WHERE COALESCE(cod_istat_comune,' ') = COALESCE(pCodIstatComune,' ')
                AND COALESCE(cod_belfiore_comune,' ') = COALESCE(pCodBelfioreComune,' ')
                AND denom_comune = sDenomComune
                AND id_provincia = pIdProvincia;
            END IF;
          END IF;
        END IF;
      ELSE
        UPDATE  cnm_d_comune
          SET cod_istat_comune = pCodIstatComune
            , cod_belfiore_comune = pCodBelfioreComune
            , denom_comune = sDenomComune
            , id_provincia = pIdProvincia
            , inizio_validita = pInizioValidita
            , fine_validita = pFineValidita
            , dt_id_comune = pIdComune
            , dt_id_comune_prev = pIdComunePrev
            , dt_id_comune_next = pIdComuneNext
          WHERE dt_id_comune = pIdComunePrev;
      END IF;
    END IF;
  END IF;
  -------
  RETURN 0;
END;
$function$
;

CREATE OR REPLACE FUNCTION puliscidenominazione(pdenomcomune character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$
DECLARE
  sDenomComune  cnm_d_comune.denom_comune%TYPE;
  sCarattere    VARCHAR(1);
begin
	
	if pdenomcomune = 'VALLE D''AOSTA/VALLÉE D''AOSTE' then
		RETURN TRIM('VALLE D''AOSTA/VALLE'' D''AOSTE');
	end if;
  sDenomComune = ' ';
  FOR i IN 1 .. LENGTH(pDenomComune)
  LOOP
    sCarattere = SUBSTR(pDenomComune,i,1);
   
	-- RAISE NOTICE 'sCarattere %, ascii %', sCarattere, ASCII(sCarattere) ; 
    IF ASCII(sCarattere) = 9562 THEN
      sDenomComune = sDenomComune || CHR(200);
    ELSIF ASCII(sCarattere) = 9556 THEN
      sDenomComune = sDenomComune || CHR(201);
    ELSIF ASCII(sCarattere) = 9516 THEN
      sDenomComune = sDenomComune || CHR(194);
    ELSIF ASCII(sCarattere) = 9600 THEN
      sDenomComune = sDenomComune || 'SS';
    ELSIF ASCII(sCarattere) = 9560 THEN
      sDenomComune = sDenomComune || CHR(212);
    ELSIF ASCII(sCarattere) = 9567 THEN
      sDenomComune = sDenomComune || CHR(199);
    ELSIF ASCII(sCarattere) = 9577 THEN
      sDenomComune = sDenomComune || CHR(202);
    ELSIF ASCII(sCarattere) = 3616 THEN
      sDenomComune = sDenomComune || 'A''';
    ELSIF ASCII(sCarattere) = 3619 THEN
      sDenomComune = sDenomComune || 'A';
    ELSIF ASCII(sCarattere) = 3625 or ASCII(sCarattere) = 201 THEN
      sDenomComune = sDenomComune || 'E''';
    ELSIF ASCII(sCarattere) = 3641 THEN
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


