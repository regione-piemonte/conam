import { AllegatoFieldVO } from "../vo/verbale/allegato-field-vo";
import { DocumentoProtocollatoVO } from "../vo/verbale/documento-protocollato-vo";
import { SoggettoPagamentoVO } from "../vo/verbale/soggetto-pagamento-vo";

export interface SalvaAllegatoProtocollatoVerbaleRequestAllegato {
    idTipoAllegato: number;
    allegatoField?: Array<AllegatoFieldVO>; //i metedati
}

export class SalvaAllegatoProtocollatoRequestCommon{
    public allegati: SalvaAllegatoProtocollatoVerbaleRequestAllegato[];
    public soggettiPagamentoVO?: SoggettoPagamentoVO [];
	public documentoProtocollato: DocumentoProtocollatoVO;
}
