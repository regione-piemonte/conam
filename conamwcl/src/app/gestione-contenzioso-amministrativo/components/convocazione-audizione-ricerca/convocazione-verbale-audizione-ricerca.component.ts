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

@Component({
    selector: 'convocazione-audizione-ricerca',
    templateUrl: './convocazione-audizione-ricerca.component.html',

})
export class ConvocazioneAudizioneRicercaComponent implements OnInit, OnDestroy {

    public verbali: Array<MinVerbaleVO>;
    public config: Config;
    public loaded: boolean = true;
    public verbaleSel: MinVerbaleVO;

    public showTable: boolean;

    request: RicercaVerbaleRequest;

    public configVerbaleRicerca: ConfigVerbaleRicerca = { showFieldStatoVerbale: true, tipoRicerca: 'GC' };

    @ViewChild(SharedVerbaleRicercaComponent)
    private sharedVerbaleRicercaComponent: SharedVerbaleRicercaComponent

    constructor(
        private logger: LoggerService,
        private configSharedService: ConfigSharedService,
        private sharedVerbaleService: SharedVerbaleService,
        private router: Router,
    ) { }

    ngOnInit(): void {
        this.logger.init(ConvocazioneAudizioneRicercaComponent.name);
        this.config = this.configSharedService.configRicercaVerbale;
        this.verbali = new Array();
    }

    ricercaFascicolo(ricercaVerbaleRequest: RicercaVerbaleRequest) {
        this.request = ricercaVerbaleRequest;
        this.showTable = false;
        this.loaded = false;
        this.sharedVerbaleService.ricercaVerbale(ricercaVerbaleRequest).subscribe(
            data => {
                if (data != null){
                    this.verbali = data;
                }
                this.showTable = true;
                this.loaded = true;
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

    scrollEnable: boolean;
    downloadList(list:any){
        this.loaded=false
        this.sharedVerbaleService.dowloadReport(this.request, list)
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
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_CONVOCAZIONE_AUDIZIONE_RIEPILOGO + el.id)
    }

    ngAfterContentChecked() {
        let out: HTMLElement = document.getElementById("scrollBottom");
        if (this.loaded && this.scrollEnable && this.showTable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }
    }

    //Messaggio top
    public typeMessageTop: String;
    public showMessageTop: boolean;
    private intervalIdS: number = 0;
    public messageTop: String;

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
    ngOnDestroy(): void {
        this.logger.destroy(ConvocazioneAudizioneRicercaComponent.name);
    }
    manageMessage(err: ExceptionVO) {
        this.typeMessageTop = err.type;
        this.messageTop = err.message;
        this.timerShowMessageTop();
    }
    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }

}