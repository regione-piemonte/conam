import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { RicercaPianoRateizzazioneRequest } from "../../../commons/request/piano-rateizzazione/ricerca-piano-rateizzazione-request";
import { Config } from "../../../shared/module/datatable/classes/config";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { PagamentiService } from "../../services/pagamenti.service";

@Component({
    selector: 'pagamenti-piano-crea',
    templateUrl: './pagamenti-piano-crea.component.html',
})
export class PagamentiPianoCreaComponent implements OnInit, OnDestroy {

    public loaded: boolean = true;
    public subscribers: any = {};
    public config: Config;
    public soggetti: Array<TableSoggettiOrdinanza> = new Array<TableSoggettiOrdinanza>();
    public showTable: boolean;

    public max: boolean = false;

    public request: RicercaPianoRateizzazioneRequest = new RicercaPianoRateizzazioneRequest();
    public showMessageTop: boolean;
    private intervalTop: number = 0;
    public messageTop: String;
    public typeMessageTop: String;

    isSelectable: (el: TableSoggettiOrdinanza) => boolean = (el: TableSoggettiOrdinanza) => {
        return !el.pianoRateizzazioneCreato;
    }

    showDetail: (el: TableSoggettiOrdinanza) => boolean = (el: TableSoggettiOrdinanza) => {
        return !el.pianoRateizzazioneCreato;
    }

    constructor(
        private router: Router,
        private logger: LoggerService,
        private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
        private pagamentiService: PagamentiService,
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiPianoCreaComponent.name);
        this.config = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggettiCreaPiano(true, "Crea Piano", 1, this.isSelectable, this.showDetail,(el: any)=>true);
    }

    ricerca(request: RicercaPianoRateizzazioneRequest) {
        this.loaded = false;
        this.showTable = false;
        this.request.ordinanzaProtocollata = true;
        this.request.tipoRicerca = "CREA_PIANO";
        this.pagamentiService.ricercaSoggettiPiano(request).subscribe(data => {
            this.resetMessageTop();
            this.request = request;
            this.loaded = true;
            this.showTable = true;
            if (data != null) {
                if(data.length == 1){
                    data.map(value => {                        this.max = value.superatoIlMassimo;                    });
                }
                if(this.max){
                    this.manageMessageTop("Il sistema può mostrare solo 100 risultati per volta. Ridurre l'intervallo di ricerca","WARNING");
                }else {
                    this.soggetti = data.map(value => {                        return TableSoggettiOrdinanza.map(value);                    });
                }
            }
        });
    }

    goCreaPiano(event: Array<TableSoggettiOrdinanza>) {
        this.pagamentiService.soggettiCreaPiano = event;
        this.router.navigateByUrl(Routing.PAGAMENTI_PIANO_INSERIMENTO);
    }

    onInfo(el: any | Array<any>){
        if (el instanceof Array)             throw Error("errore sono stati selezionati più elementi");
        else{
            let azione: string = el.ruolo;
            this.router.navigate([Routing.SOGGETTO_RIEPILOGO + el.idSoggetto, { action: azione }]);
        }
    }

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 10;
        this.intervalTop = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {                this.resetMessageTop();            }
        }, 1000);
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalTop);
    }

    messaggio(message: string){        this.manageMessageTop(message,"DANGER");    }

    scrollTopEnable: boolean;
    ngOnDestroy(): void {        this.logger.destroy(PagamentiPianoCreaComponent.name);
    }

    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }


}
