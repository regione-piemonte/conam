import { SelectVO } from "../select-vo";

export class NotaVO {
  public idNota: number;
  public oggetto: string;
  public descrizione: string;
  public data:string;
  public ambito: SelectVO;
}
