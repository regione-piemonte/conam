import { Component, OnInit, OnDestroy } from "@angular/core";
import { Router } from "@angular/router";
import { Config } from "protractor";
import { Constants } from "../../../commons/class/constants";
import { RicercaPianoRateizzazioneRequest } from "../../../commons/request/piano-rateizzazione/ricerca-piano-rateizzazione-request";
import { Routing } from "../../../commons/routing";
import { TablePianoRateizzazione } from "../../../commons/table/table-piano-rateizzazione";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { PagamentiUtilService } from "../../services/pagamenti-util.serivice";
import { PagamentiService } from "../../services/pagamenti.service";

@Component({
    selector: 'pagamenti-riconcilia-sollecito-rate-ricerca',
    templateUrl: './pagamenti-riconcilia-sollecito-rate-ricerca.component.html',
    styleUrls: ['./pagamenti-riconcilia-sollecito-rate-ricerca.component.scss']
})
export class PagamentiRiconciliaSollecitoRateRicercaComponent implements OnInit, OnDestroy {

    public soggetti: Array<TableSoggettiOrdinanza> = new Array<TableSoggettiOrdinanza>();
    public subscribers: any = {};
    public loaded: boolean = true;
    public config: Config;
    public pianiRateizzazione: Array<TablePianoRateizzazione> = new Array<TablePianoRateizzazione>();
    public showTable: boolean = false;


    public max: boolean = false;
    public request: RicercaPianoRateizzazioneRequest = new RicercaPianoRateizzazioneRequest();
    //Messaggio top
    public showMessageTop: boolean;
    private intervalTop: number = 0;
    public messageTop: String;
    public typeMessageTop: String;

    isSelectable: (el: TableSoggettiOrdinanza) => boolean = (el: TableSoggettiOrdinanza) => {
        return el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_ARCHIVIATO &&
            el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_RICORSO_ACCOLTO
            && el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_OFFLINE
            && el.statoSoggettoOrdinanza.id != Constants.STATO_ORDINANZA_SOGGETTO_PAGATO_ONLINE;
    }

    constructor(
        private router: Router,
        private logger: LoggerService,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private pagamentiService: PagamentiService,
        private pagamentiUtilService: PagamentiUtilService,
    ) { }

    ricerca(request: RicercaPianoRateizzazioneRequest) {
        this.loaded = false;
        this.showTable = false;
        this.request.ordinanzaProtocollata = true;
        this.request.statoManualeDiCompetenza  = true;
        this.request.tipoRicerca = "RICERCA_SOLLECITO_RATE";
        this.pagamentiService.ricercaSoggettiPiano(request).subscribe(data => {
            this.resetMessageTop();
            this.request = request;
            this.loaded = true;
            this.showTable = true;
            if (data != null) {
                if(data.length == 1){
                    data.map(value => {
                        this.max = value.superatoIlMassimo;
                    });
                }
                if(this.max){
                    this.manageMessageTop("Il sistema può mostrare solo 100 risultati per volta. Ridurre l'intervallo di ricerca","WARNING");
                }else {
                    this.soggetti = data.map(value => {
                        return TableSoggettiOrdinanza.map(value);
                    });
                }
            }
        });
    }

    ngOnInit(): void {
        this.logger.init(PagamentiRiconciliaSollecitoRateRicercaComponent.name);
        this.config = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(true, "Dettaglio", 0, this.isSelectable, null,null);
    }

    timerShowMessageTop() {
        let seconds: number = 10;
        this.showMessageTop = true;
        this.intervalTop = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop();
            }
        }, 1000);
    }

    manageMessageTop(message: string, type: string) {
        this.messageTop = message;
        this.typeMessageTop = type;
        this.scrollTopEnable = true;
        this.timerShowMessageTop();
    }
    scrollTopEnable: boolean;
    resetMessageTop() {
        this.typeMessageTop = null;
        this.showMessageTop = false;
        this.messageTop = null;
        clearInterval(this.intervalTop);
    }

    messaggio(message: string){
        this.manageMessageTop(message,"DANGER");
    }

    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(PagamentiRiconciliaSollecitoRateRicercaComponent.name);
    }
    dettaglioPiano(event: TableSoggettiOrdinanza) {
        this.pagamentiUtilService.soggettoSollecito = event;
        this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_SOLLECITO_RATE_DETTAGLIO);
    }

}