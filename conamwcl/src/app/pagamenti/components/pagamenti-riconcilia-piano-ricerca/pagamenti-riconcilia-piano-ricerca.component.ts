import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { RicercaPianoRateizzazioneRequest } from "../../../commons/request/piano-rateizzazione/ricerca-piano-rateizzazione-request";
import { Config } from "protractor";
import { PagamentiService } from "../../services/pagamenti.service";
import { TablePianoRateizzazione } from "../../../commons/table/table-piano-rateizzazione";
import { Constants } from "../../../commons/class/constants";

@Component({
    selector: 'pagamenti-riconcilia-piano-ricerca',
    templateUrl: './pagamenti-riconcilia-piano-ricerca.component.html'
})
export class PagamentiRiconciliaPianoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public request: RicercaPianoRateizzazioneRequest = new RicercaPianoRateizzazioneRequest();

    public showTable: boolean = false;
    public loaded: boolean = true;
    public pianiRateizzazione: Array<TablePianoRateizzazione> = new Array<TablePianoRateizzazione>();


    constructor(
        private router: Router,
        private logger: LoggerService,
        private pagamentiService: PagamentiService
    ) { }

    ngOnInit(): void {
        this.logger.init(PagamentiRiconciliaPianoComponent.name);
    }

    dettaglioPiano(event: TablePianoRateizzazione) {
        if (event instanceof Array)
            throw new Error("Errore sono stati selezionati più elementi");
        else
            this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_PIANO_DETTAGLIO + event.id);
    }

    ricerca(request: RicercaPianoRateizzazioneRequest) {
        this.loaded = false;
        this.showTable = false;
        this.request.statoManualeDiCompetenza  = true;
        request.tipoRicerca = "RICONCILIA_PIANO";
        if(this.request.statoPiano==null){
            this.request.statoPiano = new Array(Constants.STATOPIANOVO_NON_NOTIFICATO, Constants.STATOPIANOVO_NOTIFICATO);
        }
        this.pagamentiService.ricercaPiani(request).subscribe(data => {
            this.resetMessageTop();
            this.loaded = true;
            this.showTable = true;
            this.request = request;
            if (data != null) {
                this.pianiRateizzazione = data.map(value => {
                    return TablePianoRateizzazione.map(value);
                });
            }
        });

    }

    public config: Config = {
        pagination: {
            enable: true
        },
        sort: {
            enable: true,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "Aggiungi pagamento al piano",
            enable: true,
        },
        columns: [
            {                columnName: 'identificativoSoggetto',                displayName: 'Codice Fiscale / Partita iva'            },
            {                columnName: 'stato.denominazione',                displayName: 'Stato'            },
            {                columnName: 'saldo',                displayName: 'Importo Totale'            },
            {                columnName: 'dataCreazione',                displayName: 'Data Creazione'            },
            {                columnName: 'numProtocollo',                displayName: 'Numero Protocollo Piano'            }
        ]
    };

    messaggio(message: string){
        this.manageMessageTop(message,"DANGER");
    }

    //Messaggio top
    public typeMessageTop: String;
    public showMessageTop: boolean;
    private intervalIdS: number = 0;
    public messageTop: String;

    manageMessageTop(message: string, type: string) {
        this.messageTop = message;
        this.typeMessageTop = type;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }
    timerShowMessageTop() {
        let seconds: number = 10;
        this.showMessageTop = true;
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop();
            }
        }, 1000);
    }
    ngOnDestroy(): void {
        this.logger.destroy(PagamentiRiconciliaPianoComponent.name);
    }


    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    scrollTopEnable: boolean;

}