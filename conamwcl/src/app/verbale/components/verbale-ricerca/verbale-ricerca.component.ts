import { Component, OnInit, OnDestroy, Inject } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { MinVerbaleVO } from "../../../commons/vo/verbale/min-verbale-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { RicercaVerbaleRequest } from "../../../commons/request/verbale/ricerca-verbale-request";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { DOCUMENT } from "@angular/platform-browser";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";

@Component({
  selector: "verbale-ricerca",
  templateUrl: "./verbale-ricerca.component.html",
  styleUrls: ["./verbale-ricerca.component.scss"],
})
export class VerbaleRicercaComponent implements OnInit, OnDestroy {
  public showTable: boolean;

  public request: RicercaVerbaleRequest;

  public config: Config;
  public verbali: Array<MinVerbaleVO>;
  public loaded: boolean = true;
  public verbaleSel: MinVerbaleVO;

  constructor(
    private configSharedService: ConfigSharedService,
    private logger: LoggerService,
    private router: Router,
    private sharedVerbaleService: SharedVerbaleService,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    this.logger.init(VerbaleRicercaComponent.name);
    this.config = this.configSharedService.configRicercaVerbale;
    this.verbali = new Array();
  }

  scrollEnable: boolean;
  ricercaFascicolo(ricercaVerbaleRequest: RicercaVerbaleRequest) {
    this.request = ricercaVerbaleRequest;
    this.showTable = false;
    this.loaded = false;
    this.sharedVerbaleService.ricercaVerbale(ricercaVerbaleRequest).subscribe(
      (data) => {
        if (data != null) {
          this.verbali = data;
        }
        this.showTable = true;
        this.loaded = true;
        this.scrollEnable = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.loaded = true;
          this.manageMessage(err);
        }
        this.scrollEnable = true;
        this.logger.error("Errore nel salvataggio del verbale");
      }
    );
  }

  onDettaglio(el: any | Array<any>) {
    this.verbaleSel = el;
    if (el instanceof Array){
      throw Error("errore sono stati selezionati più elementi");
    } else if (this.verbaleSel.modificabile) {
      this.router.navigateByUrl(Routing.VERBALE_DATI + el.id);
    } else {
      this.router.navigateByUrl(Routing.VERBALE_RIEPILOGO + el.id);
    }
  }

  //Messaggio top
  private intervalIdS: number = 0;
  public showMessageTop: boolean;
  public messageTop: String;
  public typeMessageTop: String;

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 30; //this.configService.getTimeoutMessagge();
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }
  manageMessage(err: ExceptionVO) {
    this.typeMessageTop = err.type;
    this.messageTop = err.message;
    this.timerShowMessageTop();
  }

  ngOnDestroy(): void {
    this.logger.destroy(VerbaleRicercaComponent.name);
  }

  ngAfterContentChecked() {
    let out: HTMLElement = document.getElementById("scrollBottom");
    if (this.loaded && this.scrollEnable && out != null && this.showTable) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }

}
