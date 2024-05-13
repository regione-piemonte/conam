import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Observable } from "rxjs";
import { VerbaleVO } from "../../commons/vo/verbale/verbale-vo";
import { RiepilogoAllegatoVO } from "../../commons/vo/verbale/riepilogo-allegato-vo";
import { RuoloVO, TipoAllegatoVO, IstruttoreVO } from "../../commons/vo/select-vo";
import { SoggettoVO } from "../../commons/vo/verbale/soggetto-vo";
import { MinSoggettoVO } from "../../commons/vo/verbale/min-soggetto-vo";
import { RecidivoVo } from "../../commons/vo/verbale/recidivo-vo";
import { UtenteVO } from "../../commons/vo/utente-vo";
import { SalvaScrittoDifensivoResponse } from "../../commons/response/verbale/salva-scritto-difensivo-response";
import { SalvaAzioneRequest } from "../../commons/request/verbale/salva-azione.request";
import { TipologiaAllegabiliRequest } from "../../commons/request/tipologia-allegabili-request";
import { SalvaStatoRequest } from "../../commons/request/verbale/salva-stato-request";
import { SalvaScrittoDifensivoRequest } from "../../commons/request/verbale/salva-scritto-difensivo-request";
import { ScrittoDifensivoVO } from "../../commons/vo/verbale/scritto-difensivo-vo";
import { RicercaScrittoDifensivoRequest } from "../../commons/request/verbale/ricerca-scritto-difensivo-request";

@Injectable()
export class VerbaleService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.createService(VerbaleService.name);
    }

    getVerbaleById(idVerbale: number): Observable<VerbaleVO> {
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        var url: string = this.config.getBEServer() + '/restfacade/verbale/dettaglioVerbale';
        return this.http.get<VerbaleVO>(url, { params: params });
    }

    salvaVerbale(verbale: VerbaleVO): Observable<number> {
        let body = verbale;
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaVerbale';
        return this.http.post<number>(url, body);
    }

    ricercaSoggetto(minSoggetto: MinSoggettoVO) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/ricercaSoggetto';
        return this.http.post<SoggettoVO>(url, minSoggetto);
    }

    eliminaVerbale(idVerbale: number) {
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        var url: string = this.config.getBEServer() + '/restfacade/verbale/eliminaVerbale';
        return this.http.get(url, { params: params });
    }

    ricercaSoggettoPerPIva(minSoggetto: MinSoggettoVO) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/ricercaSoggettoPerPIva';
        return this.http.post<SoggettoVO>(url, minSoggetto);
    }

    salvaSoggetto(soggetto: SoggettoVO, idVerbale: number): Observable<SoggettoVO> {
        let body = { "idVerbale": idVerbale, "soggetto": soggetto };
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaSoggetto';
        return this.http.post<SoggettoVO>(url, body);
    }

    ruoliSoggetto(): Observable<Array<RuoloVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/ruoliSoggetto';
        return this.http.get<Array<RuoloVO>>(url);
    }

    eliminaSoggettoByIdVerbaleSoggetto(idVerbaleSoggetto: number) {
        let params = new HttpParams().set('idVerbaleSoggetto', idVerbaleSoggetto.toString());
        var url: string = this.config.getBEServer() + '/restfacade/verbale/eliminaSoggettoByIdVerbaleSoggetto';
        return this.http.get(url, { params: params });
    }

    getAllegatiByIdVerbale(idVerbale: number): Observable<RiepilogoAllegatoVO> {
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getAllegatiByIdVerbale';
        return this.http.get<RiepilogoAllegatoVO>(url, { params: params });
    }

    getIstruttoreByIdVerbale(idVerbale: number): Observable<Array<IstruttoreVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getIstruttoreByVerbale';
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        return this.http.get<Array<IstruttoreVO>>(url, { params: params });
    }

    getTipologiaAllegatiAllegabiliVerbale(request: TipologiaAllegabiliRequest): Observable<Array<TipoAllegatoVO>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getTipologiaAllegatiAllegabiliVerbale';
        return this.http.post<Array<TipoAllegatoVO>>(url, request);
    }

    salvaAzioneVerbale(salvaAzioneRequest: SalvaAzioneRequest) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaAzioneVerbale';
        return this.http.post(url, salvaAzioneRequest);
    }

    listaStati(): Observable<Array<any>> {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getStatiManuali';
        return this.http.get<Array<any>>(url);
    }

    getUtenteRuolo(idVerbale: number) : Observable<UtenteVO>{
        let params = new HttpParams().set('idVerbale', idVerbale.toString());
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getUtenteRuolo';
        return this.http.get<UtenteVO>(url, { params: params });
    }

    salvaStatoVerbale(salavaStatoFascicolo: SalvaStatoRequest) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/salvaStatoManuale';
        return this.http.post(url, salavaStatoFascicolo);
    }

    getVerbaleSoggettoByIdSoggetto(idSogg: number, recidivo?: boolean): Observable<RecidivoVo> {
        let params = new HttpParams().set('idSoggetto', idSogg.toString());
        var url: string = this.config.getBEServer() + '/restfacade/verbale/getVerbaleSoggettoRaggruppatoPerSoggettoByIdSoggetto';
        if(recidivo){
            params =  new HttpParams().set('idSoggetto', idSogg.toString()).set('recidivo',recidivo.toString());
        }
        return this.http.get<RecidivoVo>(url, { params: params });
    }

    salvaScrittoDifensivo(salvaScrittoDifensivoRequest: SalvaScrittoDifensivoRequest) {
        var url: string = this.config.getBEServer() + '/restfacade/scrittoDifensivo/salvaScrittoDifensivo';
        let form = new FormData();
        form.append("data", JSON.stringify(salvaScrittoDifensivoRequest, (k, v) => {
            if (v instanceof File)
                return undefined;
            return null === v ? undefined : v;
        }));
        if(salvaScrittoDifensivoRequest.file){
            form.append("file", salvaScrittoDifensivoRequest.file)
        }
        return this.http.post<SalvaScrittoDifensivoResponse>(url, form);
    }

    setRecidivoVerbaleSoggetto (setRecidivoVerbaleSoggetto : any) {
        var url: string = this.config.getBEServer() + '/restfacade/verbale/setRecidivoVerbaleSoggetto';
        return this.http.post(url, setRecidivoVerbaleSoggetto );
    }

    getScrittoDifensivoById(idScrittoDifensivo: number): Observable<ScrittoDifensivoVO> {
        var url: string = this.config.getBEServer() + '/restfacade/scrittoDifensivo/dettaglioScrittoDifensivo';
        let params = new HttpParams().set('idScrittoDifensivo', idScrittoDifensivo.toString());
        return this.http.get<ScrittoDifensivoVO>(url, { params: params });
    }

    uploadImportiSogg(idVerbale:number, importoVerbale: number){
       console.log(idVerbale)
        var url: string = this.config.getBEServer() + '/restfacade/verbale/modificaImportoSoggetti';
        let params = new HttpParams()
        .set('idVerbale', idVerbale.toString()).set('importoVerbale', importoVerbale.toString());
        return this.http.post(url,null, {params: params} );
    }

    ricercaScrittoDifensivo(ricercaScrittoDifensivoRequest: RicercaScrittoDifensivoRequest): Observable<Array<ScrittoDifensivoVO>>{
        var url: string = this.config.getBEServer() + '/restfacade/scrittoDifensivo/ricercaScrittoDifensivo';
        return this.http.post<Array<ScrittoDifensivoVO>>(url, ricercaScrittoDifensivoRequest);
    }

}