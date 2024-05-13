import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { RicercaSoggettiOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-soggetti-ordinanza-request";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { PagamentiUtilService } from "../../services/pagamenti-util.serivice";

@Component({
    selector: 'pagamenti-riconcilia-sollecito-ricerca',
    templateUrl: './pagamenti-riconcilia-sollecito-ricerca.component.html'
})
export class PagamentiRiconciliaSollecitoRicercaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};
    public soggetti: Array<TableSoggettiOrdinanza> = new Array<TableSoggettiOrdinanza>();
    public loaded: boolean = true;
    public showTable: boolean;
    public config: Config;

    public request: RicercaSoggettiOrdinanzaRequest = new RicercaSoggettiOrdinanzaRequest();

    constructor(
        private logger: LoggerService,
        private pagamentiUtilService: PagamentiUtilService,
        private router: Router,
        private sharedOrdinanzaService: SharedOrdinanzaService,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiRiconciliaSollecitoRicercaComponent.name);
        this.config = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(true, "Dettaglio Sollecito", 0,(el:any)=>true, null,null);
    }

    ricerca(request: RicercaSoggettiOrdinanzaRequest) {
        this.loaded = false;
        this.showTable = false;
        request.ordinanzeSollecitate = true;
        request.statoManualeDiCompetenza  = true;
        request.tipoRicerca = "RICERCA_SOLLECITO";
        this.subscribers.ricerca = this.sharedOrdinanzaService.ricercaSoggettiOrdinanza(request).subscribe(data => {
            this.resetMessageTop();
            this.request = request;
            this.loaded = true;
            this.showTable = true;
            if (data != null) {
                this.soggetti = data.map(value => {                    return TableSoggettiOrdinanza.map(value);                });
            }
        });
    }

    messaggio(message: string){
        this.manageMessageTop(message,"DANGER");
    }

    onDettaglio(event: TableSoggettiOrdinanza) {
        this.pagamentiUtilService.soggettoSollecito = event;
        this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_SOLLECITO_DETTAGLIO);
    }

    public showMessageTop: boolean;
    public messageTop: String;
    public typeMessageTop: String;
    private intervalIdS: number = 0;

    manageMessageTop(message: string, type: string) {
        this.messageTop = message;
        this.typeMessageTop = type;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
    }

    timerShowMessageTop() {
        let seconds: number = 10;
        this.showMessageTop = true;
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {                this.resetMessageTop();            }
        }, 1000);
    }

    resetMessageTop() {
        this.typeMessageTop = null;
        this.showMessageTop = false;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }

    scrollTopEnable: boolean;

    ngOnDestroy(): void {        this.logger.destroy(PagamentiRiconciliaSollecitoRicercaComponent.name);    }

    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

}