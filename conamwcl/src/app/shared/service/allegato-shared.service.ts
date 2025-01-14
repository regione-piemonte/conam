import { Injectable, OnDestroy } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs/Observable";
import { ConfigAllegatoVO } from "../../commons/vo/verbale/config-allegato-vo";
import { SelectVO } from "../../commons/vo/select-vo";
import { SoggettoPagamentoVO } from "../../commons/vo/verbale/soggetto-pagamento-vo";
import { BehaviorSubject } from "rxjs";
import { AllegatoVO } from "../../commons/vo/verbale/allegato-vo";
import {
  ResponseSearchStilo,
  StiloResearch,
} from "../../commons/vo/ordinanza/stilo-research-vo";

@Injectable()
export class AllegatoSharedService implements OnDestroy {
  private listaTrasgressoriSource = new BehaviorSubject<SoggettoPagamentoVO[]>(
    null
  );
  currentListaTrasgressori = this.listaTrasgressoriSource.asObservable();

  constructor(
    private http: HttpClient,
    private config: ConfigService,
    private logger: LoggerService
  ) {
    this.logger.createService(AllegatoSharedService.name);
  }
  changeLista(lista: SoggettoPagamentoVO[]) {
    console.log(lista);
    this.listaTrasgressoriSource.next(lista);
  }
  getConfigAllegato(): Observable<Array<ConfigAllegatoVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/getConfigAllegatiByIdVerbale";
    return this.http.get<Array<ConfigAllegatoVO>>(url);
  }

  getAllegato(idAllegato: number): Observable<Blob> {
    var url: string =
      this.config.getBEServer() + "/restfacade/allegato/downloadAllegato";
    let params = new HttpParams().set("idAllegato", idAllegato.toString());
    return this.http
      .get(url, { params: params, responseType: "blob" })
      .map((res) => new Blob([res]));
  }

  getAllegatoByParolaChiave(objectIdDocumento: string): Observable<Blob> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/downloadAllegatoByObjectIdDocumento";
    let params = new HttpParams().set("objectIdDocumento", objectIdDocumento);
    return this.http
      .get(url, { params: params, responseType: "blob" as "json" })
      .map((res) => <Blob>res);
  }

  getAllegatoByObjectIdDocumentoFisico(
    objectIdDocumentoFisico: string
  ): Observable<Blob> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/downloadAllegatoByObjectIdDocumentoFisico";
    let params = new HttpParams().set(
      "objectIdDocumentoFisico",
      objectIdDocumentoFisico
    );
    return this.http
      .get(url, { params: params, responseType: "blob" as "json" })
      .map((res) => <Blob>res);
  }

  getDocFisiciByObjectIdDocumento(objectIdDocumento: string): Observable<any> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/getDocFisiciByObjectIdDocumento";
    let params = new HttpParams().set("objectIdDocumento", objectIdDocumento);
    return this.http.get(url, { params: params }).map((res) => res);
  }

  getDocFisiciByIdAllegato(idAllegato: number): Observable<any> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/getDocFisiciByIdAllegato";
    let params = new HttpParams().set("idAllegato", idAllegato.toString());
    return this.http.get(url, { params: params }).map((res) => res);
  }

  getDecodificaSelectAllegato(
    idDecodifica: number
  ): Observable<Array<SelectVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/getDecodificaSelectAllegato/" +
      idDecodifica.toString();
    return this.http.get<Array<SelectVO>>(url);
  }
  getDecodificaSelectSoggettiAllegato(
    idVerbale: number
  ): Observable<Array<SelectVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/getDecodificaSelectSoggettiAllegato/" +
      idVerbale.toString();
    return this.http.get<Array<SelectVO>>(url);
  }
  getDecodificaSelectSoggettiAllegatoCompleto(
    idVerbale: number
  ): Observable<Array<SelectVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/getDecodificaSelectSoggettiAllegatoCompleto/" +
      idVerbale.toString();
    return this.http.get<Array<SelectVO>>(url);
  }

  getListaTrasgressori(
    idVerbale: number,
    idSoggettoPagatore: number,
    controlloUtente: boolean,
    idAllegato?: number
  ): Observable<Array<SoggettoPagamentoVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/getSoggettiTrasgressoriConResiduo";
    var urlProvaPagamento: string =
      this.config.getBEServer() +
      "/restfacade/verbale/getSoggettiTrasgressoriConResiduoByAllegato";

    let params;

    //flusso prova dati del pagamento
    if (idAllegato) {
      params = new HttpParams()
        .set("idVerbale", idVerbale.toString())
        .set("idSoggettoPagatore", idSoggettoPagatore.toString())
        .set("controlloUtente", controlloUtente.toString())
        .set("idAllegato", idAllegato.toString());
      return this.http.get<Array<SoggettoPagamentoVO>>(urlProvaPagamento, {
        params: params,
      });

      ////
    } else {
      params = new HttpParams()
        .set("idVerbale", idVerbale.toString())
        .set("idSoggettoPagatore", idSoggettoPagatore.toString())
        .set("controlloUtente", controlloUtente.toString());
      return this.http.get<Array<SoggettoPagamentoVO>>(url, { params: params });
    }
  }

  getConfigStiloSearch(
    idRicerca?: number
  ): Observable<Array<ConfigAllegatoVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/getRicercaFieldsByIdRicerca";
    let params = new HttpParams().set("idRicerca", idRicerca.toString());

    return this.http.get<Array<ConfigAllegatoVO>>(url, { params: params });
  }

  getDocOnStilo(
    request: StiloResearch,
    pageRequest: number = 10,
    maxLineRequest: number = 100
  ) {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/ricercaDocumentoSuStilo";

    let params = new HttpParams()
      .set("pageRequest", pageRequest.toString())
      .set("maxLineRequest", maxLineRequest.toString());

    return this.http.post<ResponseSearchStilo>(url, request, {
      params: params,
    });
  }

  ngOnDestroy(): void {
    this.logger.destroyService(AllegatoSharedService.name);
  }
}
