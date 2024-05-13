import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { RiscossioneService } from "../../services/riscossione.service";
import { Routing } from "../../../commons/routing";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { Constants } from "../../../commons/class/constants";
import { saveAs } from "file-saver";
import { SharedOrdinanzaDettaglio } from "../../../shared-ordinanza/component/shared-ordinanza-dettaglio/shared-ordinanza-dettaglio.component";
import { SharedRiscossioneService } from "../../../shared-riscossione/services/shared-riscossione.service";
import { CurrencyPipe } from "@angular/common";
import { SharedRiscossioneSollecitoDettaglioRateComponent } from "../../../shared-riscossione/components/shared-riscossione-sollecito-dettaglio-rate/shared-riscossione-sollecito-dettaglio-rate.component";
import { PianoRateizzazioneVO } from "../../../commons/vo/piano-rateizzazione/piano-rateizzazione-vo";
import { TableSoggettiRata } from "../../../commons/table/table-soggetti-rata";
import { SharedRateConfigService } from "../../../shared-pagamenti/services/shared-pagamenti-config.service";
import { SalvaSollecitoRateRequest } from "../../../commons/request/riscossione/salva-sollecito-rate-request";
import { TemplateService } from "../../../template/services/template.service";

declare var $: any;

@Component({
  selector: "riscossione-sollecito-rate-dettaglio",
  templateUrl: "./riscossione-sollecito-rate-dettaglio.component.html",
})
export class RiscossioneSollecitoRateDettaglioComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;
  @ViewChild(SharedOrdinanzaDettaglio)  sharedOrdinanzaDettaglio: SharedOrdinanzaDettaglio;
  @ViewChild(SharedRiscossioneSollecitoDettaglioRateComponent)  sharedRiscossioneSollecitoDettaglioRateComponent: SharedRiscossioneSollecitoDettaglioRateComponent;

  public subscribers: any = {};

  public loaded: boolean;
  public loadedAction: boolean = true;

  public idOrdinanzaVerbaleSoggetto: number[];

  public sollecito: SollecitoVO;
  public soggettoSollecito: any[];
  public listaSolleciti: Array<SollecitoVO>;

  public configSoggetti: Config;
  public configSolleciti: Config;
  public piano: PianoRateizzazioneVO;

  public buttonAnnullaText: string;
  public subMessages: Array<string>;
  public buttonConfirmText: string;

  private intervalTop: number = 0;
  public showMessageTop: boolean;
  public messageTop: String;
  public typeMessageTop: String;

  public isModifica: boolean;
  public modificabile: boolean;


  public message: string;

  public isNuovo: boolean = true;

  configRate: Config;
  rate: Array<TableSoggettiRata>;
  dataDettaglioRateDisabled:boolean = false;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private riscossioneService: RiscossioneService,
    private sharedRiscossioneService: SharedRiscossioneService,
    private templateService: TemplateService,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private sharedRateConfigService: SharedRateConfigService
  ) {}

  ngOnInit(): void {
    this.logger.init(RiscossioneSollecitoRateDettaglioComponent.name);
    this.soggettoSollecito = [];
    this.loaded = false;
    this.soggettoSollecito[0] = this.riscossioneService.soggettoSollecito;

    this.templateService.getMessage('PROTLET01').subscribe(data => {
      this.message= data.message
      }, err => {          this.logger.error("Errore nel recupero del messaggio");      });

    if (!this.soggettoSollecito[0]) {      this.router.navigateByUrl(Routing.RISCOSSIONE_SOLLECITO_RICERCA_RATE);
    } else {
      this.configRate = this.sharedRateConfigService.getConfigRateView();
      this.idOrdinanzaVerbaleSoggetto = [];
      this.idOrdinanzaVerbaleSoggetto[0] = this.soggettoSollecito[0].idSoggettoOrdinanza;
      this.loadSollecitiEsistenti();
      this.setConfig();
      this.sollecito = new SollecitoVO();
      this.dataDettaglioRateDisabled = false;
      this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    }
    if(this.idOrdinanzaVerbaleSoggetto[0]){
      this.riscossioneService.getMessaggioManualeByidOrdinanzaVerbaleSoggetto(this.idOrdinanzaVerbaleSoggetto[0]).subscribe(data => {
        if(data){          this.manageMessageTop(data.message, data.type)
        }
      });
     }
    this.riscossioneService.getDettaglioPianoById(this.soggettoSollecito[0].idPianoRateizzazione, false).subscribe(data => {
      this.piano = data;
      this.rate = data.rate.map( value => {        return TableSoggettiRata.map(value);
      });
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
          if (this.listaSolleciti != null && this.listaSolleciti.length > 0) {
            let currencyPipe = new CurrencyPipe("it-IT");
            for (let i = 0; i < this.listaSolleciti.length; i++) {
              this.listaSolleciti[
                i
              ].importoSollecitatoString = currencyPipe.transform(
                this.listaSolleciti[i].importoSollecitato,
                "EUR",
                "symbol"
              );
              this.listaSolleciti[
                i
              ].maggiorazioneString = currencyPipe.transform(
                this.listaSolleciti[i].maggiorazione,
                "EUR",
                "symbol"
              );
            }
          }

          //
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore durante il caricamento dei solleciti");
          this.loaded = true;
        }
      );
  }

  onChangeDataDettaglioRate(event: any) {    this.dataDettaglioRateDisabled = !event.valid;  }

  loadSollecitiEsistentiCopiaPerRichiestaBollettini() {
    this.loaded = false;
    this.listaSolleciti = new Array<SollecitoVO>();
    this.subscribers.solleciti = this.sharedRiscossioneService
      .getSollecitiByIdPianoRateizzazione(this.soggettoSollecito[0].idPianoRateizzazione)
      .subscribe(
        (data) => {
          this.listaSolleciti = data;
          if (this.listaSolleciti != null && this.listaSolleciti.length > 0) {
            let currencyPipe = new CurrencyPipe("it-IT");
            for (let i = 0; i < this.listaSolleciti.length; i++) {
              this.listaSolleciti[
                i
              ].importoSollecitatoString = currencyPipe.transform(
                this.listaSolleciti[i].importoSollecitato,
                "EUR",
                "symbol"
              );
              this.listaSolleciti[
                i
              ].maggiorazioneString = currencyPipe.transform(
                this.listaSolleciti[i].maggiorazione,
                "EUR",
                "symbol"
              );
            }
          }


          this.modificaVediSollecito(
            this.listaSolleciti.find(
              (soll) => soll.idSollecito == this.sollecito.idSollecito
            )
          );
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore durante il caricamento dei solleciti");
          this.loaded = true;
        }
      );
  }


  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 10;
    this.intervalTop = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 2000);
  }

  manageMessageTop(message: string, type: string) {
    this.messageTop = message;
    this.typeMessageTop = type;
    this.scrollTopEnable = true;
    this.timerShowMessageTop();
  }

  richiediEliminazioneSollecito() {
    this.generaMessaggio();
    this.buttonAnnullaText = "Annulla";
    this.sharedDialog.open();
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
        this.eliminaSollecito(this.sollecito);
        this.sollecito = new SollecitoVO();
      },
      (err) => {
        this.logger.error(err);
      }
    );
    this.subscribers.close = this.sharedDialog.closeAction.subscribe(
      (data) => {
        this.subMessages = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }
  resetMessageTop() {
    this.typeMessageTop = null;
    this.showMessageTop = false;
    this.messageTop = null;
    clearInterval(this.intervalTop);
  }

  creaSollecitoRate() {
    this.loaded = false;
    let salvaSollecitoRateRequest: SalvaSollecitoRateRequest = new SalvaSollecitoRateRequest();
    salvaSollecitoRateRequest.idPianoRateizzazione = this.soggettoSollecito[0].idPianoRateizzazione;
    salvaSollecitoRateRequest.sollecito = this.sollecito;
    salvaSollecitoRateRequest.notifica = this.sharedRiscossioneSollecitoDettaglioRateComponent.getNotificaObject();
    this.subscribers.salvataggio = this.riscossioneService
      .salvaSollecitoRate(salvaSollecitoRateRequest)
      .subscribe(
        (data) => {
          this.router.navigateByUrl(
            Routing.RISCOSSIONE_SOLLECITO_RATE_TEMPLATE + data
          );
        },
        (err) => {
          if (err instanceof ExceptionVO) {            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore durante il salvataggio del sollecito");
          this.loaded = true;
        }
      );
  }

  generaMessaggio() {
    this.subMessages = new Array<string>();
    this.subMessages.push("Si intende eliminare il sollecito selezionato?");
    this.subMessages.push("Data Creazione: " + this.sollecito.dataScadenza);
    this.subMessages.push(
      "Importo da sollecitare: " + this.sollecito.importoSollecitato
    );
  }
  modificaVediSollecito(el: SollecitoVO) {
    this.sollecito = JSON.parse(JSON.stringify(el));
    if (el.statoSollecito.id == Constants.SOLLECITO_BOZZA) {      this.modificabile = true;
    } else {      this.modificabile = false;
    }
    this.isModifica = true;
    this.dataDettaglioRateDisabled = false;
    this.isNuovo = false;
    if(this.sharedRiscossioneSollecitoDettaglioRateComponent){      this.sharedRiscossioneSollecitoDettaglioRateComponent.aggiornaNotifica();
    }

  }
  eliminaSollecito(el: SollecitoVO) {
    this.loaded = false;
    this.subscribers.elimina = this.riscossioneService
      .eliminaSollecito(this.sollecito.idSollecito)
      .subscribe(
        (data) => {
          this.loadSollecitiEsistenti();
          this.manageMessageTop("Sollecito eliminato con successo!", "SUCCESS");
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore durante l'eliminazione del sollecito");
          this.loaded = true;
        }
      );
  }
  annullaModifica() {
    this.sollecito = new SollecitoVO();
    this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    this.isNuovo = true;
    this.isModifica = false;
    this.dataDettaglioRateDisabled = false;

    this.loadSollecitiEsistenti();
  }
  isDisabledCreaSollecito(): boolean {
    let checkNotifica: boolean = true;
    if (
      this.sharedRiscossioneSollecitoDettaglioRateComponent != null ||
      this.sharedRiscossioneSollecitoDettaglioRateComponent != undefined
    ) {
      if (
        this.sharedRiscossioneSollecitoDettaglioRateComponent.getNotificaObject() !=          null ||
        this.sharedRiscossioneSollecitoDettaglioRateComponent.getNotificaObject() !=          undefined
      ) {
        if (
          this.sharedRiscossioneSollecitoDettaglioRateComponent.getNotificaObject().importoSpeseNotifica == undefined ||
          this.sharedRiscossioneSollecitoDettaglioRateComponent.getNotificaObject().importoSpeseNotifica == null
        ) {
          checkNotifica = true;
        } else {
          checkNotifica = isNaN(
            Number(
              this.sharedRiscossioneSollecitoDettaglioRateComponent.getNotificaObject().importoSpeseNotifica
            )
          );
        }
      }
    }
    if (checkNotifica) {return true;}

    if(this.dataDettaglioRateDisabled){      return true;    }

    return !this.sollecito.importoSollecitato;
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
      selection: {
        enable: true,
        isSelectable: (el: SollecitoVO) => {
          return el.isCreatoDalloUserCorrente;
        },
      },
      columns: [
        {          columnName: "numeroProtocollo",          displayName: "Numero Protocollo",
        },
        {
          columnName: "dataScadenza",          displayName: "Data Scadenza",
        },
        {          columnName: "importoSollecitatoString",          displayName: "Importo da sollecitare",        },
        {
          columnName: "statoSollecito.denominazione",          displayName: "Stato",
        },
      ],
    };
  }


  ngAfterViewChecked() {
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
  }
  scrollTopEnable: boolean;
  inviaRichiestaBollettini() {
    this.loaded = false;
    this.subscribers.inviaRichiestaBollettini = this.riscossioneService
      .inviaRichiestaBollettiniSollecito(this.sollecito.idSollecito)
      .subscribe(
        (data) => {          this.loadSollecitiEsistentiCopiaPerRichiestaBollettini();        },
        (err) => {
          if (err instanceof ExceptionVO) {            this.manageMessageTop(err.message, err.type);          }
          this.loaded = true;
          this.logger.error("Errore durante il download del PDF");
        }
      );
  }


  downloadLettera() {
    this.loaded = false;
    this.riscossioneService
      .downloadLettera(this.sollecito.idSollecito)
      .subscribe(
        (data) => {
          saveAs(
            data,
            "Lettera_Sollecito_" + this.sollecito.idSollecito + ".pdf"
          );
          this.loaded = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {            this.manageMessageTop(err.message, err.type);          }
          this.loaded = true;
          this.logger.error("Errore durante il download del PDF");
        }
      );
  }

  downloadBollettini() {
    this.loadedAction = false;
    this.riscossioneService
      .downloadBollettini(this.sollecito.idSollecito)
      .subscribe(
        (data) => {
          saveAs(
            data,
            "Bollettini_Sollecito_" +
              this.sharedOrdinanzaDettaglio.ordinanza.numDeterminazione +
              ".pdf"
          );
          this.loadedAction = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {            this.manageMessageTop(err.message, err.type);          }
          this.loadedAction = true;
          this.logger.error("Errore durante il download del PDF");
        }
      );
  }

  goToVisualizzaNotifica() {
    this.router.navigateByUrl(
      Routing.RISCOSSIONE_SOLLECITO_VIEW_NOTIFICA + this.sollecito.idSollecito
    );
  }

  goToCreaNotifica() {
    this.router.navigateByUrl(      Routing.RISCOSSIONE_SOLLECITO_INS_NOTIFICA + this.sollecito.idSollecito    );
  }

  ngOnDestroy(): void {    this.logger.destroy(RiscossioneSollecitoRateDettaglioComponent.name);  }


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
