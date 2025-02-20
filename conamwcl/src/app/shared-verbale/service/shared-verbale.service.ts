import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { MinVerbaleVO } from "../../commons/vo/verbale/min-verbale-vo";
import { RicercaVerbaleRequest } from "../../commons/request/verbale/ricerca-verbale-request";
import { Observable } from "rxjs";
import {
  IstruttoreVO,
  RuoloVO,
  SelectVO,
  StatoVerbaleVO,
  TipoAllegatoVO,
} from "../../commons/vo/select-vo";
import { RiepilogoVerbaleVO } from "../../commons/vo/verbale/riepilogo-verbale-vo";
import { AzioneVerbaleResponse } from "../../commons/response/verbale/azione-verbale-response";
import { SoggettoVO } from "../../commons/vo/verbale/soggetto-vo";
import { TipologiaAllegabiliRequest } from "../../commons/request/tipologia-allegabili-request";
import { SalvaAllegatoVerbaleRequest } from "../../commons/request/verbale/salva-allegato-verbale-request";
import { AllegatoVO } from "../../commons/vo/verbale/allegato-vo";
import { SalvaAllegatoMultipleRequest } from "../../commons/request/salva-allegato-multiple-request";
import { IsCreatedVO } from "../../commons/vo/isCreated-vo";
import { RicercaProtocolloRequest } from "../../commons/request/verbale/ricerca-protocollo-request";
import { DocumentoProtocollatoVO } from "../../commons/vo/verbale/documento-protocollato-vo";
import { SalvaAllegatoProtocollatoVerbaleRequest } from "../../commons/request/verbale/salva-allegato-protocollato-verbale-request";
import { RicercaProtocolloResponse } from "../../commons/response/verbale/ricerca-protocollo-response";
import { NotaVO } from "../../commons/vo/verbale/nota-vo";
import { VerbaleVO } from "../../commons/vo/verbale/verbale-vo";
import { MinSoggettoVO } from "../../commons/vo/verbale/min-soggetto-vo";

@Injectable()
export class SharedVerbaleService {
  constructor(
    private http: HttpClient,
    private config: ConfigService,
    private logger: LoggerService
  ) {
    this.logger.createService(SharedVerbaleService.name);
  }

  salvaSoggetto(
    soggetto: SoggettoVO,
    idVerbale: number
  ): Observable<SoggettoVO> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/salvaSoggetto";
    let body = { idVerbale: idVerbale, soggetto: soggetto };
    return this.http.post<SoggettoVO>(url, body);
  }

  ruoliSoggetto(): Observable<Array<RuoloVO>> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/ruoliSoggetto";
    return this.http.get<Array<RuoloVO>>(url);
  }
  ricercaVerbale(
    ricercaVerbaleRequest: RicercaVerbaleRequest
  ): Observable<Array<MinVerbaleVO>> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/ricercaVerbale";
    return this.http.post<Array<MinVerbaleVO>>(url, ricercaVerbaleRequest);
  }

  getIstruttoreByIdVerbale(
    idVerbale?: number
  ): Observable<Array<IstruttoreVO>> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/getIstruttoreByVerbale";
    // let params = new HttpParams().set('idVerbale', idVerbale.toString()?idVerbale.toString() : null );
    //  let params = new HttpParams().set('idVerbale', idVerbale.toString()?idVerbale.toString() : null );

    return this.http.get<Array<IstruttoreVO>>(url);
  }

  getStatiRicercaVerbale(): Observable<Array<StatoVerbaleVO>> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/getStatiRicercaVerbale";
    return this.http.get<Array<StatoVerbaleVO>>(url);
  }

  riepilogoVerbale(idVerbale: number): Observable<RiepilogoVerbaleVO> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/riepilogo";
    let params = new HttpParams().set("idVerbale", idVerbale.toString());
    return this.http.get<RiepilogoVerbaleVO>(url, { params: params });
  }

  riepilogoVerbaleAudizione(idVerbale: number): Observable<RiepilogoVerbaleVO> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/riepilogoVerbaleAudizione";
    let params = new HttpParams().set("idVerbale", idVerbale.toString());
    return this.http.get<RiepilogoVerbaleVO>(url, { params: params });
  }

  eliminaAllegato(idAllegato: number, idVerbale: number) {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/eliminaAllegato";
    let params = new HttpParams()
      .set("idAllegato", idAllegato.toString())
      .set("idVerbale", idVerbale.toString());
    return this.http.get(url, { params: params });
  }

  getAzioniVerbale(idVerbale: number): Observable<AzioneVerbaleResponse> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/getAzioniVerbale";
    let params = new HttpParams().set("idVerbale", idVerbale.toString());
    return this.http.get<AzioneVerbaleResponse>(url, { params: params });
  }

  getSoggettiByIdVerbale(
    idVerbale: number,
    includiControlloUtenteProprietario: Boolean
  ): Observable<Array<SoggettoVO>> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/getSoggettiByIdVerbale";
    let params = new HttpParams()
      .set("idVerbale", idVerbale.toString())
      .set("controlloUtente", includiControlloUtenteProprietario.toString());
    return this.http.get<Array<SoggettoVO>>(url, { params: params });
  }

  getSoggettiByIdVerbaleConvocazione(
    idVerbale: number,
    includiControlloUtenteProprietario: Boolean
  ): Observable<Array<SoggettoVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/getSoggettiByIdVerbaleConvocazione";
    let params = new HttpParams()
      .set("idVerbale", idVerbale.toString())
      .set("controlloUtente", includiControlloUtenteProprietario.toString());
    return this.http.get<Array<SoggettoVO>>(url, { params: params });
  }

  getSoggettiByIdVerbaleAudizione(
    idAllegatoVerbaleAudizione: number
  ): Observable<Array<SoggettoVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/getSoggettiByIdVerbaleAudizione";
    let params = new HttpParams().set(
      "idAllegatoVerbaleAudizione",
      idAllegatoVerbaleAudizione.toString()
    );
    return this.http.get<Array<SoggettoVO>>(url, { params: params });
  }

  getTipologiaAllegatiAllegabiliVerbale(
    request: TipologiaAllegabiliRequest
  ): Observable<Array<TipoAllegatoVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/getTipologiaAllegatiAllegabiliVerbale";
    return this.http.post<Array<TipoAllegatoVO>>(url, request);
  }

  getTipologiaAllegatiVerbale(
    request: TipologiaAllegabiliRequest
  ): Observable<Array<TipoAllegatoVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/getTipologiaAllegatiVerbale";
    return this.http.post<Array<TipoAllegatoVO>>(url, request);
  }

  salvaAllegatoVerbale(
    input: SalvaAllegatoVerbaleRequest
  ): Observable<AllegatoVO> {
    //console.log(input)
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/salvaAllegatoVerbale";
    let form = new FormData();
    form.append(
      "data",
      JSON.stringify(input, (k, v) => {
        //console.log(k,v)

        if (v instanceof File) return undefined;

        return null === v ? undefined : v;
      })
    );
    form.append("files", input.file);
    if (input.allegati && input.allegati != null) {
      input.allegati.forEach((allegato, index) => {
        form.append(
          `allegati[${index}].file`,
          allegato.file,
          allegato.filename
        );
      
      });
    }

    return this.http.post<AllegatoVO>(url, form);
  }

  salvaAllegatoOrdinanzaMaster(
    input: SalvaAllegatoMultipleRequest
  ): Observable<AllegatoVO> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/ordinanza/salvaAllegatoOrdinanzaMaster";
    let form = new FormData();
    form.append(
      "data",
      JSON.stringify(input, (k, v) => {
        if (v instanceof File) return undefined;
        return null === v ? undefined : v;
      })
    );

    input.allegati.forEach((e) => {
      let name = null;
      if (e.file != null) {
        name = e.file.name;
        form.append("files", e.file, name);
      }
    });

    return this.http.post<AllegatoVO>(url, form);
  }

  salvaAllegatoVerbaleMaster(
    input: SalvaAllegatoMultipleRequest
  ): Observable<AllegatoVO> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/salvaAllegatoVerbaleMaster";
    let form = new FormData();
    form.append(
      "data",
      JSON.stringify(input, (k, v) => {
        if (v instanceof File) return undefined;
        return null === v ? undefined : v;
      })
    );

    input.allegati.forEach((e) => {
      let name = null;
      if (e.file != null) {
        name = e.file.name;
        form.append("files", e.file, name);
      }
    });

    return this.http.post<AllegatoVO>(url, form);
  }

  creaAllegatoVerbale(
    input: SalvaAllegatoVerbaleRequest
  ): Observable<AllegatoVO> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/salvaAllegatoVerbale";
    let form = new FormData();
    form.append(
      "data",
      JSON.stringify(input, (k, v) => {
        //console.log(k,v)

        if (v instanceof File) return undefined;

        return null === v ? undefined : v;
      })
    );
    form.append("files", input.file);
    if (input.allegati && input.allegati != null) {
      input.allegati.forEach((allegato, index) => {
        form.append(
          `allegati[${index}].file`,
          allegato.file,
          allegato.filename
        );
      
      });
    }

    return this.http.post<AllegatoVO>(url, form);
  }

  isTipoAllegatoAllegabile(
    idVerbale: number,
    codiceDocumento: string
  ): Observable<boolean> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/isTipoAllegatoAllegabile";
    let params = new HttpParams()
      .set("idVerbale", idVerbale.toString())
      .set("codiceDocumento", codiceDocumento);
    return this.http.get<boolean>(url, { params: params });
  }

  isEnableCaricaVerbaleAudizione(idVerbale: number): Observable<boolean> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/isEnableCaricaVerbaleAudizione";
    let params = new HttpParams().set("idVerbale", idVerbale.toString());
    return this.http.get<boolean>(url, { params: params });
  }

  ricercaProtocolloSuACTA(ricerca: RicercaProtocolloRequest) {
    if (!("flagPregresso" in ricerca)) {
      ricerca.flagPregresso = false;
    }
    if (!("pageRequest" in ricerca)) {
      ricerca.pageRequest = 1;
    }
    if (!("maxLineRequest" in ricerca)) {
      ricerca.maxLineRequest = 5;
    }
    var url: string =
      this.config.getBEServer() +
      "/restfacade/allegato/ricercaProtocolloSuACTA";
    let params;
    if (ricerca.idVerbale) {
      params = new HttpParams()
        .set("idVerbale", ricerca.idVerbale.toString())
        .set("protocollo", ricerca.numeroProtocollo)
        .set("flagPregresso", ricerca.flagPregresso.toString());
    } else {
      params = new HttpParams()
        .set("protocollo", ricerca.numeroProtocollo)
        .set("flagPregresso", ricerca.flagPregresso.toString());
    }
    params = params.set("pageRequest", ricerca.pageRequest.toString());
    params = params.set("maxLineRequest", ricerca.maxLineRequest.toString());
    return this.http.get<RicercaProtocolloResponse>(url, { params: params });
  }

  salvaAllegatoProtocollato(
    salvaAllegatoProtocollatoVerbaleRequest: SalvaAllegatoProtocollatoVerbaleRequest
  ): Observable<Array<AllegatoVO>> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/salvaAllegatoProtocollatoVerbale";
    return this.http.post<Array<AllegatoVO>>(
      url,
      salvaAllegatoProtocollatoVerbaleRequest
    );
  }

  getVerbaleByIdOrdinanza(idOrdinanza: number): Observable<MinVerbaleVO> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/getVerbaleByIdOrdinanza";
    let params = new HttpParams().set("idOrdinanza", idOrdinanza.toString());
    return this.http.get<MinVerbaleVO>(url, { params: params });
  }
  getMessaggioManualeByIdVerbale(idVerbale: number): Observable<any> {
    var url: string =
      this.config.getBEServer() +
      "/restfacade/verbale/getMessaggioManualeByIdVerbale";
    let params = new HttpParams().set("idVerbale", idVerbale.toString());
    return this.http.get<any>(url, { params: params });
  }

  ////Notes API/////
  saveNote(nota: NotaVO, idVerbale: number): Observable<any> {
    let request = {
      idVerbale: idVerbale,
      nota: nota,
    };
    var url: string = this.config.getBEServer() + "/restfacade/verbale/nota";
    return this.http.post<any>(url, request);
  }
  updateNote(nota: NotaVO, idVerbale: number): Observable<any> {
    let request = {
      idVerbale: idVerbale,
      nota: nota,
    };
    var url: string = this.config.getBEServer() + "/restfacade/verbale/nota";
    let params = new HttpParams().set("idNota", request.nota.idNota.toString());
    return this.http.put<any>(url, request, { params: params });
  }

  deleteNote(nota: NotaVO, idVerbale: number): Observable<any> {
    let request = {
      idVerbale: idVerbale,
      nota: nota,
    };
    var url: string = this.config.getBEServer() + "/restfacade/verbale/nota";
    let params = new HttpParams().set("idNota", request.nota.idNota.toString());
    return this.http.delete<any>(url, { params: params });
  }

  ambitiList(): Observable<Array<SelectVO>> {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/getAmbitiNote";
    return this.http.get<Array<SelectVO>>(url);
  }
  ricercaSoggettoPerPIva(minSoggetto: MinSoggettoVO) {
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/ricercaSoggettoPerPIva";
    return this.http.post<SoggettoVO>(url, minSoggetto);
  }

  dowloadReport(request: RicercaVerbaleRequest, itemsColumns: any[]) {
    let input: RicercaVerbaleRequest = request;
    input.columnList = itemsColumns;
    //console.log(input)
    var url: string =
      this.config.getBEServer() + "/restfacade/verbale/downloadReport";

    return this.http.post<any>(url, input);
  }

  saveData(response: any) {
    let type = response.nomeFile.slice(response.nomeFile.lastIndexOf(".") + 1);

    const blob = this.dataURItoBlob(response.file, type);
    const a = document.createElement("a");
    document.body.appendChild(a);
    const url = URL.createObjectURL(blob);
    a.href = url;
    a.download = response.nomeFile;
    a.click();
    window.URL.revokeObjectURL(url);
    document.body.removeChild(a);
  }

  dataURItoBlob(dataURI, type) {
    const byteString = window.atob(dataURI);
    const arrayBuffer = new ArrayBuffer(byteString.length);

    const int8Array = new Uint8Array(arrayBuffer);
    for (let i = 0; i < byteString.length; i++) {
      int8Array[i] = byteString.charCodeAt(i);
    }
    const blob = new Blob([int8Array], { type: `application/${{ type }}` });
    return blob;
  }
}
