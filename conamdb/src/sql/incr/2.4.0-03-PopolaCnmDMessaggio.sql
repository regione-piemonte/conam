-- ENCODING
SET client_encoding TO 'UTF8';

-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- CnmDMessaggio

INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('NOCFWARN', 'Attenzione! c’è almeno un soggetto, collegato al verbale, non identificato. Il fascicolo diventa improcedibile.', 2);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('NOCFCONF', 'Si sta rendendo improcedibile il fascicolo con Numero Verbale %s per soggetto non identificato. Al click sul Conferma il fascicolo non potrà più essere modificato.', 0);

INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('VERPWARN', 'Attenzione! Se si desidera verificare il pagamento, selezionare il tasto IN ATTESA DI VERIFICA PAGAMENTO.', 2);
INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('VERPCONF', 'Si sta aggiornando il fascicolo con Numero Verbale %s IN ATTESA VERIFICA DI PAGAMENTO. Per procedere con l’aggiornamento selezionare il tasto Conferma. Annulla per tornare indietro.', 0);

INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('ERRCOMENT', 'Attenzione! Il Comune Ente inserito non è valido alla data accertamento impostata.', 1);


INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('ACQUIWARN', 'Attenzione: Selezionare un istruttore per procedere all’acquisizione del fascicolo.', 2);


INSERT INTO cnm_d_messaggio (cod_messaggio, desc_messaggio, id_tipo_messaggio) 
VALUES ('DOQUIND07', 'Impossibile procedere con il recupero dei documenti in quanto manca il file elettronico per il protocollo indicato.', 2);

INSERT INTO cnm_d_messaggio (cod_messaggio,desc_messaggio,id_tipo_messaggio) 
VALUES ('NOCODFIS','E’ stata richiesta la creazione di un’ordinanza per un soggetto senza CF, per la generazione del bollettino di pagamento è essenziale il Codice Fiscale.',1);

INSERT INTO cnm_d_messaggio (cod_messaggio,desc_messaggio,id_tipo_messaggio) 
VALUES ('ERRSOGGREL','Attenzione! E’ stato impostato lo stesso soggetto su relate di notifica differenti. Modificare prima di procedere.',1);


