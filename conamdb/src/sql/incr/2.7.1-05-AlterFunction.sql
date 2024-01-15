-- ENCODING
SET client_encoding TO 'UTF8';
CREATE OR REPLACE FUNCTION aggiornanazione(pidstato numeric, pidstatoprev numeric, pidstatonext numeric, pcodistatnazione character varying, pcodbelfiorenazione character varying, pdenomnazione character varying, piniziovalidita date, pfinevalidita date)
 RETURNS smallint
 LANGUAGE plpgsql
AS $function$
DECLARE
  nRecs           cnm_d_nazione.id_nazione%TYPE;
  nRecsc           cnm_d_nazione.id_nazione%TYPE;
  sDenomNazione   cnm_d_nazione.denom_nazione%TYPE;
begin
  sDenomNazione = PulisciDenominazione(pDenomNazione);
  sDenomNazione = pulisciaccento(sDenomNazione);
  ------
  --RAISE NOTICE '1 pid %, pidPrev %',pIdStato, pIdStatoPrev ; 
  IF pIdStatoPrev = 0 THEN
    -- Non ha precedenti quindi provo stare nella tabella degli attuali
    UPDATE  cnm_d_nazione
      SET cod_istat_nazione = pCodIstatNazione
        , cod_belfiore_nazione = pCodBelfioreNazione
        , denom_nazione = sDenomNazione
        , inizio_validita = pInizioValidita
        , fine_validita = pFineValidita
        , dt_id_stato = pIdStato
        , dt_id_stato_prev = pIdStatoPrev
        , dt_id_stato_next = pIdStatoNext
      WHERE dt_id_stato = findidnazione(pIdStato)
        AND id_origine = 1;
    GET DIAGNOSTICS nRecs = ROW_COUNT;
   RAISE NOTICE '1 nRecs %',nRecs ; 
    IF nRecs = 0 THEN
      UPDATE  cnm_s_nazione
        SET cod_istat_nazione = pCodIstatNazione
          , cod_belfiore_nazione = pCodBelfioreNazione
          , denom_nazione = sDenomNazione
          , inizio_validita = pInizioValidita
          , fine_validita = pFineValidita
          , dt_id_stato = pIdStato
          , dt_id_stato_prev = pIdStatoPrev
          , dt_id_stato_next = pIdStatoNext
        WHERE dt_id_stato = pIdStato
          AND id_origine = 1;
      --------
      GET DIAGNOSTICS nRecs = ROW_COUNT;
      IF nRecs = 0 THEN
        INSERT INTO cnm_d_nazione
                    ( id_nazione
                    , cod_istat_nazione
                    , cod_belfiore_nazione
                    , denom_nazione
                    , inizio_validita
                    , fine_validita
                    , dt_id_stato
                    , dt_id_stato_prev
                    , dt_id_stato_next
                    , id_origine
                    )
          VALUES  ( GetNextIdNazione()
                  , pCodIstatNazione
                  , pCodBelfioreNazione
                  , sDenomNazione
                  , pInizioValidita
                  , pFineValidita
                  , pIdStato
                  , pIdStatoPrev
                  , pIdStatoNext
                  , 1
                  );
      END IF;
    END IF;
  ELSE
    SELECT  COUNT(1)
      INTO  nRecs
      FROM  cnm_d_nazione
      WHERE dt_id_stato = pIdStatoPrev
        AND id_origine = 1
        AND cod_istat_nazione = pCodIstatNazione
        AND COALESCE(cod_belfiore_nazione,' ') = COALESCE(pCodBelfioreNazione,' ')
        AND denom_nazione = sDenomNazione
        AND inizio_validita = pInizioValidita
        AND COALESCE(fine_validita,TO_DATE('31/12/9999','DD/MM/YYYY')) = COALESCE(pFineValidita,TO_DATE('31/12/9999','DD/MM/YYYY'));
    IF nRecs = 0 THEN
      -- Ha un precedente che quindi vado a spostare nello storico
      --RAISE NOTICE 'try insert, pIdStatoPrev % ', pIdStatoPrev ;
     INSERT INTO cnm_s_nazione
                  ( id_s_nazione
                  , id_nazione
                  , cod_istat_nazione
                  , cod_belfiore_nazione
                  , denom_nazione
                  , inizio_validita
                  , fine_validita
                  , dt_id_stato
                  , dt_id_stato_prev
                  , dt_id_stato_next
                  , id_origine
                  )
        SELECT  GetNextIdSnazione()
              , id_nazione
                  , pCodIstatNazione
                  , pCodBelfioreNazione
                  , sDenomNazione
                  , pInizioValidita
                  , pFineValidita
                  , pIdStato
                  , pIdStatoPrev
                  , pIdStatoNext
              , 1
          FROM  cnm_d_nazione
          WHERE dt_id_stato = findidnazione(pIdStatoPrev);
         
         
      GET DIAGNOSTICS nRecs = ROW_COUNT;
    --RAISE NOTICE 'INSERT result %',nRecs ; 
      -- Aggiorno i dati nell'attuale
      UPDATE  cnm_d_nazione
        SET cod_istat_nazione = pCodIstatNazione
          , cod_belfiore_nazione = pCodBelfioreNazione
          , denom_nazione = sDenomNazione
          , inizio_validita = pInizioValidita
          , fine_validita = pFineValidita
          , dt_id_stato = pIdStato
          , dt_id_stato_prev = pIdStatoPrev
          , dt_id_stato_next = pIdStatoNext
        WHERE dt_id_stato = pIdStatoPrev
          AND id_origine = 1;
    END IF;
  END IF;
  -------
  RETURN 0;
END;
$function$
;


