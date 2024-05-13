import { SoggettoVO } from "../vo/verbale/soggetto-vo";
import { TipoAllegatoVO } from "../vo/select-vo";
import { CurrencyPipe } from "@angular/common";

export class TableSoggettiVerbale {
  identificativoSoggetto: string;
  nome: string;
  tipoSoggetto: string;
  ruolo: string;
  idVerbaleSoggetto: number;
  ordinanzaCreata: boolean;
  idTipoOrdinanza: TipoAllegatoVO;
  verbaleAudizioneCreato: boolean;
  idAllegatoVerbaleAudizione: number;
  nomeCognomeRagioneSociale: string;
  importoVerbale: string;
  importoResiduoVerbale: string;

  id: number;
  name: string;
  surname: string;
  birthdayDate: string;
  ragioneSociale: string;
  partitaIva: string;
  listaOrdinanze?: Array<any>;
  public static map(value: SoggettoVO): TableSoggettiVerbale {
    let tableSoggetti = new TableSoggettiVerbale();

    tableSoggetti.idVerbaleSoggetto = value.idSoggettoVerbale;
    tableSoggetti.identificativoSoggetto = value.codiceFiscale;
    tableSoggetti.nome = value.personaFisica
      ? value.nome + " " + value.cognome
      : value.ragioneSociale;
    if (value.ruolo) {
      tableSoggetti.ruolo = value.ruolo.denominazione;
    }
    tableSoggetti.tipoSoggetto = value.personaFisica
      ? "PERSONA FISICA"
      : "PERSONA GIURIDICA";
    tableSoggetti.ordinanzaCreata =
      value.idSoggettoOrdinanza != null ? true : false;
    tableSoggetti.verbaleAudizioneCreato = value.verbaleAudizioneCreato;
    tableSoggetti.idAllegatoVerbaleAudizione = value.idAllegatoVerbaleAudizione;
    tableSoggetti.nomeCognomeRagioneSociale = value.personaFisica
      ? value.cognome + " " + value.nome
      : value.ragioneSociale;

    tableSoggetti.id = value.id;
    tableSoggetti.name = value.nome;
    tableSoggetti.surname = value.cognome;
    tableSoggetti.birthdayDate = value.dataNascita;
    tableSoggetti.ragioneSociale = value.ragioneSociale;
    tableSoggetti.partitaIva = value.partitaIva;
    tableSoggetti.listaOrdinanze = value.listaOrdinanze;

    let currencyPipe = new CurrencyPipe("it-IT");
    tableSoggetti.importoVerbale = currencyPipe.transform(
      value.importoVerbale,
      "EUR",
      "symbol"
    );

    tableSoggetti.importoResiduoVerbale = currencyPipe.transform(
      value.importoResiduoVerbale,
      "EUR",
      "symbol"
    );

    return tableSoggetti;
  }
}
