export class AccontoVO {
  constructor() {}
  public id: number;
  public idOrdinanza: number;
  public idSoggetto: number;
  public dataPagamento: string;
  public importo: string;
  public contoCorrenteVersamento: string;
  public reversaledOrdine?: string;
  public pagatore?: string;
  public note?: string;
}
