import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { SharedOrdinanzaDettaglio } from "../../../shared-ordinanza/component/shared-ordinanza-dettaglio/shared-ordinanza-dettaglio.component";
import {  SollecitoRequestVO, SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { RiscossioneSollecitoDettaglioComponent } from "../../../riscossione/components/riscossione-sollecito-dettaglio/riscossione-sollecito-dettaglio.component";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { PagamentiUtilService } from "../../services/pagamenti-util.serivice";
import { SharedRiscossioneService } from "../../../shared-riscossione/services/shared-riscossione.service";
import { PagamentiService } from "../../services/pagamenti.service";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";
import { SalvaAllegatoRequest } from "../../../commons/request/salva-allegato-request";

declare var $: any;

@Component({
  selector: "pagamenti-riconcilia-sollecito-dettaglio",
  //templateUrl: "./pagamenti-riconcilia-sollecito-dettaglio.component.html",
  templateUrl: "../pagamenti-riconcilia-sollecito-rate-dettaglio/pagamenti-riconcilia-sollecito-rate-dettaglio.component.html",
})
export class PagamentiRiconciliaSollecitoDettaglioComponent
  implements OnInit, OnDestroy {

  @ViewChild(SharedOrdinanzaDettaglio)
  sharedOrdinanzaDettaglio: SharedOrdinanzaDettaglio;


  public loaded: boolean;

  public subscribers: any = {};


  public sollecito: SollecitoVO;
  public listaSolleciti: Array<SollecitoVO>;
  public soggettoSollecito: TableSoggettiOrdinanza[];

  public idOrdinanzaVerbaleSoggetto: number[];

  public configSolleciti: Config;
  public configSoggetti: Config;

  public isRiconcilia: boolean = false;


  public nuovoAllegato: SalvaAllegatoRequest;
  public uploader: ElementRef;
  public senzaAllegati: boolean = true;


  //Messaggio top
  private intervalTop: number = 0;
  public typeMessageTop: string;
  public showMessageTop: boolean;
  public messageTop: string;

  constructor(
    private logger: LoggerService,
    private sharedRiscossioneService: SharedRiscossioneService,
    private router: Router,
    private pagamentiUtilService: PagamentiUtilService,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private pagamentiService: PagamentiService,
    private numberUtilsSharedService: NumberUtilsSharedService
  ) {}

  ngOnInit(): void {
    this.logger.init(RiscossioneSollecitoDettaglioComponent.name);
    this.loaded = false;
    this.soggettoSollecito = [];
    
    this.soggettoSollecito[0] = this.pagamentiUtilService.soggettoSollecito;
    if (!this.soggettoSollecito[0]) {
      this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_SOLLECITO_RICERCA);
    } else {
      this.idOrdinanzaVerbaleSoggetto = [];
      this.idOrdinanzaVerbaleSoggetto[0] = this.soggettoSollecito[0].idSoggettoOrdinanza;
      this.loadSollecitiEsistenti();
      this.setConfig();
      this.sollecito = new SollecitoVO();
      this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    }
  }

  loadSollecitiEsistenti() {
    this.loaded = false;
    this.listaSolleciti = new Array<SollecitoVO>();
    this.subscribers.solleciti = this.sharedRiscossioneService
      .getListaSolleciti(this.idOrdinanzaVerbaleSoggetto[0])
      .subscribe(
        (data) => {
          this.listaSolleciti = data;
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
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }

  riconciliaSollecito(el: SollecitoVO) {
    this.sollecito = JSON.parse(JSON.stringify(el));

    this.isRiconcilia = true;
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalTop);
  }

  confermaRiconciliazione() {
    this.loaded = false;
    let body = new SollecitoRequestVO();
body.sollecitoVO= this.sollecito;
    if (this.nuovoAllegato){
      body.file = this.nuovoAllegato.file;
      body.filename = this.nuovoAllegato.filename;
    }
    this.subscribers.riconcilia = this.pagamentiService
      .riconciliaSollecito(body)
      .subscribe(
        (data) => {
          this.soggettoSollecito[0] = TableSoggettiOrdinanza.map(data.soggetto);
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
          if (err instanceof ExceptionVO) {
            this.manageMessageTop(err.message, err.type);
          }
          this.logger.error("Errore durante la riconciliazione del sollecito");
          this.loaded = true;
        }
      );
  }

  annullaRiconciliazione() {
    this.sollecito = new SollecitoVO();
    this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    this.isRiconcilia = false;
  }


  isKeyPressed(code: number): boolean {
    return this.numberUtilsSharedService.numberValid(code);
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
        {
          columnName: "numeroProtocollo",
          displayName: "Numero Protocollo",
        },
        {
          columnName: "dataScadenza",
          displayName: "Data Scadenza",
        },
        {
          columnName: "importoSollecitato",
          displayName: "Importo da sollecitare",
        },
        {
          columnName: "maggiorazione",
          displayName: "Maggiorazione",
        },
        {
          columnName: "statoSollecito.denominazione",
          displayName: "Stato",
        },
      ],
    };
  }
  addFile() {
    this.logger.info("=> Add file");
    this.uploader.nativeElement.click();
    this.senzaAllegati = false;
  }

  salvaAllegato(nuovoAllegato: SalvaAllegatoRequest){
    console.log(nuovoAllegato);
    this.nuovoAllegato = nuovoAllegato;
  }
  scrollTopEnable: boolean;
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
  ngOnDestroy(): void {
    this.logger.destroy(PagamentiRiconciliaSollecitoDettaglioComponent.name);
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
  ngAfterViewChecked() {
    this.manageDatePicker(null, 1);
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
  }

}
