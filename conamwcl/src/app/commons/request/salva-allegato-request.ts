import { AllegatoFieldVO } from "../vo/verbale/allegato-field-vo";
import { SoggettoPagamentoVO } from "../vo/verbale/soggetto-pagamento-vo";

export class SalvaAllegatoRequest {
    public file: File;
    public filename: string;
    public idTipoAllegato: number;
    public allegatoField: Array<AllegatoFieldVO>; //i metedati
    public idOrdinanzaDaAnnullare?: number;
    public soggettiPagamentoVO?: Array<SoggettoPagamentoVO>
    public allegati?: Array<AllegatiSecondariRequest>;
}


export class AllegatoMultipleFields{
    public file: File;
    public filename: string;
}

export class AllegatiSecondariRequest extends AllegatoMultipleFields  {
    origine: string;
    oggetto:string;

}