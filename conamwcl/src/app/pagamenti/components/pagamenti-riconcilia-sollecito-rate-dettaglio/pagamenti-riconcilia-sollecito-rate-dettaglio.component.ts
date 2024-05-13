import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { SharedOrdinanzaDettaglio } from "../../../shared-ordinanza/component/shared-ordinanza-dettaglio/shared-ordinanza-dettaglio.component";
import { SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { PagamentiUtilService } from "../../services/pagamenti-util.serivice";
import { SharedRiscossioneService } from "../../../shared-riscossione/services/shared-riscossione.service";
import { PagamentiService } from "../../services/pagamenti.service";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";
import { PianoRateizzazioneVO } from "../../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";

declare var $: any;

@Component({
  selector: "pagamenti-riconcilia-sollecito-rate-dettaglio",
  templateUrl: "./pagamenti-riconcilia-sollecito-rate-dettaglio.component.html",
})
export class PagamentiRiconciliaSollecitoRateDettaglioComponent
  implements OnInit, OnDestroy {
  @ViewChild(SharedOrdinanzaDettaglio)  sharedOrdinanzaDettaglio: SharedOrdinanzaDettaglio;
  public subscribers: any = {};
  public idOrdinanzaVerbaleSoggetto: number[];
  public loaded: boolean;
  public sollecito: SollecitoVO;
  public soggettoSollecito: TableSoggettiOrdinanza[];
  public listaSolleciti: Array<SollecitoVO>;
  public configSolleciti: Config;
  public configSoggetti: Config;
  public isRiconcilia: boolean = false;
  public piano: PianoRateizzazioneVO;
  public showMessageTop: boolean;
  private intervalTop: number = 0;
  public typeMessageTop: string;
  public isPagamentiRiconciliaSollecitoRateDettaglioComponent: boolean = true;
  public messageTop: string;

  constructor(
    private router: Router,
    private logger: LoggerService,
    private sharedRiscossioneService: SharedRiscossioneService,
    private pagamentiUtilService: PagamentiUtilService,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private pagamentiService: PagamentiService,
    private numberUtilsSharedService: NumberUtilsSharedService
  ) {}

  ngOnInit(): void {
    this.logger.init(PagamentiRiconciliaSollecitoRateDettaglioComponent.name);
    this.isPagamentiRiconciliaSollecitoRateDettaglioComponent = true;
    this.loaded = false;

    this.soggettoSollecito = [];
    this.soggettoSollecito[0] = this.pagamentiUtilService.soggettoSollecito;

    if (!this.soggettoSollecito[0]) {      this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_SOLLECITO_RATE_RICERCA);
    } else {
      this.idOrdinanzaVerbaleSoggetto = [];
      this.idOrdinanzaVerbaleSoggetto[0] = this.soggettoSollecito[0].idSoggettoOrdinanza;

      this.loadSollecitiEsistenti();

      this.setConfig();

      this.sollecito = new SollecitoVO();
      this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    }
    this.pagamentiService.getDettaglioPianoById(this.soggettoSollecito[0].idPianoRateizzazione, false).subscribe(data => {
      this.piano = data;

    }, err => {
        this.logger.error("Errore durante il caricamento del piano");
        this.loaded = true;
    });
  }

  loadSollecitiEsistenti() {
    this.loaded = false;
    this.listaSolleciti = new Array<SollecitoVO>();
    this.subscribers.solleciti = this.sharedRiscossioneService
      .getSollecitiByIdPianoRateizzazione(this.soggettoSollecito[0].idPianoRateizzazione)
      .subscribe(
        (data) => {
          this.listaSolleciti = data;
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {            this.manageMessageTop(err.message, err.type);          }
          this.logger.error("Errore durante il caricamento dei solleciti");
          this.loaded = true;
        }
      );
  }

  manageMessageTop(message: string, type: string) {
    this.messageTop = message;
    this.typeMessageTop = type;
    this.scrollTopEnable = true;
    this.timerShowMessageTop();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 10;
    this.intervalTop = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {        this.resetMessageTop();      }
    }, 1000);
  }

  resetMessageTop() {
    this.typeMessageTop = null;
    this.showMessageTop = false;
    this.messageTop = null;
    clearInterval(this.intervalTop);
  }

  riconciliaSollecito(el: SollecitoVO) {
    this.sollecito = JSON.parse(JSON.stringify(el));
    this.isRiconcilia = true;
  }

  annullaRiconciliazione() {
    this.sollecito = new SollecitoVO();
    this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    this.isRiconcilia = false;
  }

  confermaRiconciliazione() {
    this.loaded = false;
    this.subscribers.riconcilia = this.pagamentiService
      .riconciliaSollecito(this.sollecito)
      .subscribe(
        (data) => {
          let index = this.listaSolleciti.findIndex(
            (soll) =>
              soll.idSollecito == data.sollecito.idSollecito &&
              soll.idSoggettoOrdinanza == data.sollecito.idSoggettoOrdinanza
          );
          this.listaSolleciti[index] = data.sollecito;
          this.isRiconcilia = false;
          this.loadSollecitiEsistenti();
          this.manageMessageTop(
            "Sollecito riconciliato con successo",
            "SUCCESS"
          );
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore durante la riconciliazione del sollecito");
          this.loaded = true;
        }
      );
  }

  setConfig() {
    this.configSoggetti = this.sharedOrdinanzaConfigService.getConfigOrdinanzaSoggetti(
      false,
      null,
      0,
      null,
      null,
      (el: any) => true
    );
    this.configSolleciti = {
      edit: {
        enable: true,
        isEditable: (sollecito: SollecitoVO) => {
          return sollecito.isRiconciliaEnable;
        },
      },
      columns: [
        {          columnName: "numeroProtocollo",
          displayName: "Numero Protocollo",
        },
        {          columnName: "dataScadenza",
          displayName: "Data Scadenza",
        },
        {          columnName: "importoSollecitato",
          displayName: "Importo da sollecitare",
        },
        {          columnName: "maggiorazione",
          displayName: "Maggiorazione",
        },
        {          columnName: "statoSollecito.denominazione",
          displayName: "Stato",
        },
      ],
    };
  }

  isKeyPressed(code: number): boolean {
    return this.numberUtilsSharedService.numberValid(code);
  }

  manageDatePicker(event: any, i: number) {
    var str: string = "#datetimepicker" + i.toString();
    if ($(str).length) {
      $(str).datetimepicker({
        format: "DD/MM/YYYY",
      });
    }
    if (event != null) {
      switch (i) {
        case 1:
          this.sollecito.dataPagamento = event.srcElement.value;
          break;
      }
    }
  }

  scrollTopEnable: boolean;
  ngAfterViewChecked() {
    this.manageDatePicker(null, 1);
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
  }

  ngOnDestroy(): void { this.logger.destroy(PagamentiRiconciliaSollecitoRateDettaglioComponent.name);
  }

  onInfo(el: any | Array<any>) {
    if (el instanceof Array)
      throw Error("errore sono stati selezionati più elementi");
    else {
      let azione1: string = el.ruolo;
      let azione2: string = el.noteSoggetto;
      this.router.navigate([
        Routing.SOGGETTO_RIEPILOGO + el.idSoggetto,
        { ruolo: azione1, nota: azione2 },
      ]);
    }
  }
}
