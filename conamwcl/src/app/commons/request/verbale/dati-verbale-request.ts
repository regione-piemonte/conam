import { NormaVO, StatoVerbaleVO, EnteVO, AmbitoVO, IstruttoreVO, ComuneVO } from "../../../commons/vo/select-vo";

export class DatiVerbaleRequest {

    public numeroProtocollo: string;
    public numeroVerbale: string;
    public annoAccertamento?: number;
    public ente: EnteVO;
    public ambito: AmbitoVO;
    public assegnatario?:IstruttoreVO;
    public norma: NormaVO;
    public stato: Array<StatoVerbaleVO>;
    public pregresso?:boolean;
    public dataAccertamentoDa?:string;
    public dataAccertamentoA?:string;
    public dataProcessoVerbaleDa?:string;
    public dataProcessoVerbaleA?:string;
    public enteAccertatore: EnteVO;
    public comuneEnteAccertatore?: ComuneVO;

}