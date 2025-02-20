import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { MinVerbaleVO } from "../../../commons/vo/verbale/min-verbale-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { RicercaVerbaleRequest } from "../../../commons/request/verbale/ricerca-verbale-request";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { SharedVerbaleRicercaComponent, ConfigVerbaleRicerca } from "../../../shared-verbale/component/shared-verbale-ricerca/shared-verbale-ricerca.component";
import { BehaviorSubject } from "rxjs";

@Component({
    selector: 'controdeduzioni-verbale-ricerca',
    templateUrl: './controdeduzioni-verbale-ricerca.component.html',
})
export class ControdeduzioniVerbaleRicercaGestContAmministrativoComponent implements OnInit, OnDestroy {

    public verbali: Array<MinVerbaleVO>;
    public config: Config;
    public loaded: boolean = true;
    public verbaleSel: MinVerbaleVO;
    public payloadRicerca: RicercaVerbaleRequest;
    public showTable: boolean;

    public configVerbaleRicerca: ConfigVerbaleRicerca = { showFieldStatoVerbale: true, tipoRicerca: 'GC' };

    private sharedVerbaleRicercaComponent: SharedVerbaleRicercaComponent
    @ViewChild(SharedVerbaleRicercaComponent)

    request: RicercaVerbaleRequest;

    request$ = new BehaviorSubject<any>(null);

    constructor(
        private sharedVerbaleService: SharedVerbaleService,
        private configSharedService: ConfigSharedService,
        private logger: LoggerService,
        private router: Router,
    ) { }

    ngOnInit(): void {
        this.logger.init(ControdeduzioniVerbaleRicercaGestContAmministrativoComponent.name);
        this.config = this.configSharedService.configRicercaVerbale;
        this.verbali = new Array();
    }

    scrollEnable: boolean;
    ricercaFascicolo(ricercaVerbaleRequest: RicercaVerbaleRequest) {
        this.request = ricercaVerbaleRequest;
        this.request$.next(ricercaVerbaleRequest);

        this.payloadRicerca= ricercaVerbaleRequest
        this.showTable = false;
        this.loaded = false;
        this.sharedVerbaleService.ricercaVerbale(ricercaVerbaleRequest).subscribe(
            data => {
                if (data != null){
                    this.verbali = data;
                }
                this.loaded = true;
                this.showTable = true;
                this.scrollEnable = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    this.loaded = true;
                    this.manageMessage(err);
                }
                this.logger.error("Errore nel salvataggio del verbale");
            }
        );
    }
    downloadList(list:any){
        this.loaded=false

        this.sharedVerbaleService.dowloadReport(this.payloadRicerca, list)
        .subscribe(
          (response) => {
          this.sharedVerbaleService.saveData(response);
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.loaded = true;
            this.manageMessage(err);
          }
          this.scrollEnable = true;
          this.logger.error("Errore nel dowload report");
        }
        );

      }
    onDettaglio(el: any | Array<any>) {
        this.verbaleSel = el;
        if (el instanceof Array){
            throw Error("errore sono stati selezionati più elementi");
        }
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_INSERIMENTO_CONTRODEDUZIONI_RIEPILOGO + el.id)
    }

    ngAfterContentChecked() {
        let out: HTMLElement = document.getElementById("scrollBottom");
        if (this.loaded && this.scrollEnable && this.showTable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }
    }

    manageMessage(err: ExceptionVO) {
        this.typeMessageTop = err.type;
        this.messageTop = err.message;
        this.timerShowMessageTop();
    }

    //Messaggio top
    public typeMessageTop: String;
    public showMessageTop: boolean;
    private intervalIdS: number = 0;
    public messageTop: String;

    ngOnDestroy(): void {
        this.logger.destroy(ControdeduzioniVerbaleRicercaGestContAmministrativoComponent.name);
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 30//this.configService.getTimeoutMessagge();
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop();
            }
        }, 1000);
    }

}