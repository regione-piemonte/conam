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
  selector: "pregresso-ricerca",
  templateUrl: "./pregresso-ricerca.component.html",
  styleUrls: ["./pregresso-ricerca.component.scss"],
})
export class PregressoRicercaComponent implements OnInit, OnDestroy {
  public showTable: boolean;

  public request: RicercaVerbaleRequest;

  public loaded: boolean = true;
  public config: Config;
  public verbaleSel: MinVerbaleVO;
  public verbali: Array<MinVerbaleVO>;

  constructor(
    private configSharedService: ConfigSharedService,
    private logger: LoggerService,
    private router: Router,
    private sharedVerbaleService: SharedVerbaleService,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    this.logger.init(PregressoRicercaComponent.name);
    this.verbali = new Array();
    this.config = this.configSharedService.configRicercaVerbale;
  }

  ricercaFascicolo(ricercaVerbaleRequest: RicercaVerbaleRequest) {
    this.request = ricercaVerbaleRequest;
    this.showTable = false;
    this.loaded = false;
    ricercaVerbaleRequest.datiVerbale.pregresso = true;
    this.sharedVerbaleService.ricercaVerbale(ricercaVerbaleRequest).subscribe(
      (data) => {
        if (data != null) this.verbali = data;
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
  scrollEnable: boolean;


  //Messaggio top
  private intervalIdS: number = 0;
  public showMessageTop: boolean;
  public messageTop: String;
  public typeMessageTop: String;

  onDettaglio(el: any | Array<any>) {
    this.verbaleSel = el;
    if (el instanceof Array)
      throw Error("errore sono stati selezionati più elementi");
    else if (this.verbaleSel.modificabile)
      this.router.navigateByUrl(Routing.PREGRESSO_DATI + el.id);
    else this.router.navigateByUrl(Routing.PREGRESSO_RIEPILOGO + el.id);
  }

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
    this.logger.destroy(PregressoRicercaComponent.name);
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
    this.messageTop = null;
    this.typeMessageTop = null;
    clearInterval(this.intervalIdS);
  }

}
