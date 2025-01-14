import { StatoSollecitoVO } from "../select-vo";
import { SoggettoVO } from "../verbale/soggetto-vo";
import { SalvaAllegatoRequest } from "../../request/salva-allegato-request";

export class SollecitoVO {
    public idSollecito: number;
    public idSoggettoOrdinanza: number;
    public idPianoRateizzazione: number;
    public numeroProtocollo: string;
    public dataScadenza: string;
    public dataFineValidita: string;
    public importoSollecitato: number;
    public maggiorazione: number;
    public statoSollecito: StatoSollecitoVO;
    public bollettinoDaCreare: boolean;
    public downloadBollettiniEnable: boolean;
    public isNotificaCreata: boolean;
    public importoPagato: number;
    public dataPagamento: string;
    public isRiconciliaEnable: boolean;

    public importoSollecitatoString: string;
    public maggiorazioneString: string;

    public isCreatoDalloUserCorrente: boolean;

    public soggetto: SoggettoVO;

    public reversaledOrdine?: string;
    public pagatore?: string;
    public note?: string;
    public salvaAllegatoRequest?: SalvaAllegatoRequest;

    constructor() {
		if (!this.statoSollecito) this.statoSollecito = new StatoSollecitoVO();
    }
}

export class SollecitoRequestVO {
  sollecitoVO: SollecitoVO;
  file?: File;
filename?: string;
}