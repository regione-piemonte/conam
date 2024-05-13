import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { OrdinanzaVO } from "../../../commons/vo/ordinanza/ordinanza-vo";
import { RicercaOrdinanzaRequest } from "../../../commons/request/ordinanza/ricerca-ordinanza-request";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { StatoOrdinanzaVO } from "../../../commons/vo/select-vo";
import { Constants } from "../../../commons/class/constants";

@Component({
    selector: 'fase-giurisdizionale-ricorso-ricerca-ordinanza',
    templateUrl: './fase-giurisdizionale-ricorso-ricerca-ordinanza.component.html'
})
export class FaseGiurisdizionaleRicorsoRicercaOrdinanzaComponent implements OnInit, OnDestroy {

    public showTable: boolean;

    public subscribers: any = {};

    public loaded: boolean = true;
    public config: Config;
    public ordinanzaSel: OrdinanzaVO;
    public ordinanze: Array<OrdinanzaVO>;

    public request: RicercaOrdinanzaRequest

    public statiOrdinanzaModel: Array<StatoOrdinanzaVO>;
    public loadedStatiOrdinanza: boolean;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private configSharedService: ConfigSharedService,
        private sharedOrdinanzaService: SharedOrdinanzaService,
    ) { }

    loadStatiOrdinanza(){
        this.loadedStatiOrdinanza = false;
        this.sharedOrdinanzaService.getStatiOrdinanza().subscribe( data => {
            if(data!=null)
                this.statiOrdinanzaModel = data.filter(a => (a.id != Constants.STATO_ORDINANZA_ATTESA_NOTIFICA && a.id != Constants.STATO_ORDINANZA_NON_NOTIFICA && a.id != Constants.STATO_ORDINANZA_RISCOSSA_CON_SORIS));
            this.loadedStatiOrdinanza = true;
        })
    }

    ngOnInit(): void {
        this.logger.init(FaseGiurisdizionaleRicorsoRicercaOrdinanzaComponent.name);
        this.config = this.configSharedService.configRicercaOrdinanza;
        this.loadStatiOrdinanza();
    }

    ricercaOrdinanza(ricercaOrdinanzaRequest: RicercaOrdinanzaRequest) {
        this.request = ricercaOrdinanzaRequest;
        this.showTable = false;
        this.loaded = false;
        ricercaOrdinanzaRequest.ordinanzaProtocollata = true;
        this.sharedOrdinanzaService.ricercaOrdinanza(ricercaOrdinanzaRequest).subscribe(
            data => {
                this.resetMessageTop2();
                if (data != null){
                    this.ordinanze = data;
                }
                this.showTable = true;
                this.loaded = true;
                this.scrollEnable = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    this.loaded = true;
                    this.manageMessage(err);
                }
                this.logger.error("Errore nella ricerca dell'ordinanza");
            }
        );
    }

    scrollEnable: boolean;

    ngAfterContentChecked() {
        let out: HTMLElement = document.getElementById("scrollBottom");
        if (this.loaded && this.scrollEnable && this.showTable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }
    }

    onDettaglio(el: any | Array<any>) {
        this.ordinanzaSel = el;
        if (el instanceof Array){
            throw Error("errore sono stati selezionati più elementi");
        } else {
            this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_RICORSO_DETTAGLIO_ORDINANZA + el.id);
        }
    }

    //Messaggio top
    public typeMessageTop: String;
    public showMessageTop: boolean;
    private intervalIdS: number = 0;
    public messageTop: String;

    manageMessage(err: ExceptionVO) {
        this.messageTop = err.message;
        this.typeMessageTop = err.type;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        let seconds: number = 30;
        this.showMessageTop = true;
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop();
            }
        }, 1000);
    }
    resetMessageTop() {
        this.showMessageTop = false;
        this.messageTop = null;
        this.typeMessageTop = null;
        clearInterval(this.intervalIdS);
    }

    messaggio(message: string){
        this.manageMessageTop2(message,"DANGER");
    }

    //Messaggio top
    public typeMessageTop2: String;
    public showMessageTop2: boolean;
    private intervalIdS2: number = 0;
    public messageTop2: String;

    timerShowMessageTop2() {
        this.showMessageTop2 = true;
        let seconds: number = 10;
        this.intervalIdS2 = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop2();
            }
        }, 1000);
    }

    manageMessageTop2(message: string, type: string) {
        this.typeMessageTop2 = type;
        this.messageTop2 = message;
        this.timerShowMessageTop2();
        this.scrollTopEnable2 = true;
    }

    scrollTopEnable2: boolean;

    resetMessageTop2() {
        this.showMessageTop2 = false;
        this.typeMessageTop2 = null;
        this.messageTop2 = null;
        clearInterval(this.intervalIdS2);
    }

    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable2 && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable2 = false;
        }
    }

    ngOnDestroy(): void {
        this.logger.destroy(FaseGiurisdizionaleRicorsoRicercaOrdinanzaComponent.name);
    }

    goToFaseGiurisdizionaleElencoOrdinanze() {
        this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_RICORSO_DETTAGLIO_ORDINANZA);
    }
}