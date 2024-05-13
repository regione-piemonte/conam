import { Component, OnInit, OnDestroy, Inject, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { VerbaleService } from "../../services/verbale.service";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { DOCUMENT } from "@angular/platform-browser";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { Config } from "protractor";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { RicercaScrittoDifensivoRequest } from "../../../commons/request/verbale/ricerca-scritto-difensivo-request";
import { TableScrittiDifensiviVerbale } from "../../../commons/table/table-scritti-difensivi-verbale";

declare var $: any;

@Component({
  selector: "verbale-ricerca-scritti-difensivi",
  templateUrl: "./verbale-ricerca-scritti-difensivi.component.html",
})
export class VerbaleRicercaScrittiDifensiviComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

  public showTable: boolean;

  public scrittiDifensivi: Array<TableScrittiDifensiviVerbale> = new Array<TableScrittiDifensiviVerbale>();
  public config: Config;

  public loaded: boolean = true;
  public scrittiDifensivoSel: TableScrittiDifensiviVerbale;

  public request: RicercaScrittoDifensivoRequest;

  constructor(
    private configSharedService: ConfigSharedService,
    private logger: LoggerService,
    private router: Router,
    private verbaleService: VerbaleService,
    @Inject(DOCUMENT) private document: Document

  ) {}

  ngOnInit(): void {
    this.logger.init(VerbaleRicercaScrittiDifensiviComponent.name);
    this.config = this.configSharedService.configRicercaScrittoDifensivo;
    this.scrittiDifensivi = new Array();
  }

  scrollEnable: boolean;
  ricercaScrittoDifensivo(ricercaScrittoDifensivoRequest: RicercaScrittoDifensivoRequest) {
    this.request = ricercaScrittoDifensivoRequest;
    this.request.tipoRicerca = 'RICERCA_SCRITTO';
    this.loaded = false;
    this.showTable = false;
    this.verbaleService.ricercaScrittoDifensivo(ricercaScrittoDifensivoRequest).subscribe(
      (data) => {
        if (data != null){
          this.scrittiDifensivi = data.map((value) => {
            return TableScrittiDifensiviVerbale.map(value);
          });
          this.showTable = true;
        }
        this.loaded = true;
        this.scrollEnable = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.loaded = true;
          this.manageMessage(err);
        }
        this.scrollEnable = true;
        this.logger.error("Errore nella ricerca di scritti difensivi");
      }
    );
  }

  onDettaglio(el: any | Array<any>) {
    this.scrittiDifensivoSel = el;
    if (el instanceof Array) {      throw Error("errore sono stati selezionati più elementi");}
    else {this.router.navigateByUrl(Routing.VERBALE_SCRITTO_DIFENSIVO + el.id);}
  }

  // Messaggio t o p
  public showMessageTop: boolean;
  public messageTop: String;
  public typeMessageTop: String;
  private intervalIdS: number = 0;

  manageMessage(err: ExceptionVO) {
    this.messageTop = err.message;
    this.typeMessageTop = err.type;
    this.timerShowMessageTop();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 30;
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {        this.resetMessageTop();      }
    }, 1000);
  }
  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }

  ngAfterContentChecked() {
    let out: HTMLElement = document.getElementById("scrollBottom");
    if (this.loaded && this.scrollEnable && out != null && this.showTable) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }
  ngOnDestroy(): void {    this.logger.destroy(VerbaleRicercaScrittiDifensiviComponent.name);
  }
}
