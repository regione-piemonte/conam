import { MessageVO } from "../../vo/messageVO";
import { DocumentoProtocollatoVO } from "../../vo/verbale/documento-protocollato-vo";

export class RicercaProtocolloResponse{

    public messaggio: MessageVO;
    public documentoProtocollatoVOList: Array<DocumentoProtocollatoVO>;
    public lineRes: number;
    public maxLineReq: number;
    public pageReq: number;
    public pageResp: number;
    public totalLineResp: number;
    
    constructor(){
    }
}