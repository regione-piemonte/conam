import {
  StatoOrdinanzaVO,
  TipoOrdinanzaVO,
  ModalitaNotificaVO,
  SelectVO,
} from "../../../commons/vo/select-vo";

export class NotificaVO {
  public dataNotifica: string;
  public importoSpeseNotifica: number;
  public numeroRaccomandata: string;
  public dataSpedizione: string;
  public notificata: boolean;
  public modalita: ModalitaNotificaVO;
  public idOrdinanza: number;
  public idPiano: number;
  public idSollecito: number;
  public causale: SelectVO;
  public numeroAccertamento: number;
  public annoAccertamento: number;
}
