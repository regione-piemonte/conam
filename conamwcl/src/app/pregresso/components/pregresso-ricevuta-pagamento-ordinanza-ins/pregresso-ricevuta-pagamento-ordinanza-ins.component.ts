import {  Component,  OnInit,  OnDestroy,  ViewChild,  Input,  Output,  EventEmitter,} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { SollecitoVO } from "../../../commons/vo/riscossione/sollecito-vo";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { TableSoggettiOrdinanza } from "../../../commons/table/table-soggetti-ordinanza";
import { Routing } from "../../../commons/routing";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { Constants } from "../../../commons/class/constants";
import { SharedOrdinanzaDettaglio } from "../../../shared-ordinanza/component/shared-ordinanza-dettaglio/shared-ordinanza-dettaglio.component";
import { SharedRiscossioneService } from "../../../shared-riscossione/services/shared-riscossione.service";
//JIRA - Gestione Notifica
import { SharedRiscossioneSollecitoDettaglioComponent } from "../../../shared-riscossione/components/shared-riscossione-sollecito-dettaglio/shared-riscossione-sollecito-dettaglio.component";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";
import { SharedOrdinanzaConfigService } from "../../../shared-ordinanza/service/shared-ordinanza-config.service";
import { NgForm } from "@angular/forms";
import { ConfigAllegatoVO } from "../../../commons/vo/verbale/config-allegato-vo";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
import {  FieldTypeVO,  SelectVO,  TipoAllegatoVO,} from "../../../commons/vo/select-vo";
import { AllegatoFieldVO } from "../../../commons/vo/verbale/allegato-field-vo";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";

declare var $: any;
interface Elem {
  value: any;
  fieldType: FieldTypeVO;
  idField: number;
}

@Component({
  selector: "pregresso-ricevuta-pagamento-ordinanza-ins",
  templateUrl: "./pregresso-ricevuta-pagamento-ordinanza-ins.component.html",
})
export class PregressoRicevutaPagamentoOrdinanzaInsComponent
  implements OnInit, OnDestroy {
  @Input()  idOrdinanza: number;
  @Input()  idTipo: number;
  @Input()  numDeterminazione: string;
  @Input()  tipoAllegatoInput: Array<TipoAllegatoVO>;
  @Input()  allegati: boolean;
  @Output()  onChangeData: EventEmitter<any> = new EventEmitter<any>();
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;
  @ViewChild(SharedOrdinanzaDettaglio)  sharedOrdinanzaDettaglio: SharedOrdinanzaDettaglio;
  @ViewChild(SharedRiscossioneSollecitoDettaglioComponent)  sharedRiscossioneSollecitoDettaglioComponent: SharedRiscossioneSollecitoDettaglioComponent;
  @ViewChild("creaDisposizioneForm")  private creaDisposizioneForm: NgForm;

  public subscribers: any = {};

  public mapConfigAllegati: Map<number, Array<Array<ConfigAllegatoVO>>>;

  public loadedAction: boolean = true;
  public loaded: boolean;

  public soggettoSollecito: TableSoggettiOrdinanza[];
  public sollecito: SollecitoVO;
  public listaSolleciti: Array<SollecitoVO>;

  public idOrdinanzaVerbaleSoggetto: number[];

  public configSolleciti: Config;
  public configSoggetti: Config;

  //Pop-up
  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;

  //Messaggio top
  public showMessageTop: boolean;
  private intervalTop: number = 0;
  public messageTop: String;
  public typeMessageTop: String;

  //FLAG MODIFICA
  public modificabile: boolean;
  public isModifica: boolean = true;

  //flag nuovo
  public isNuovo: boolean = true;

  public comboLoaded: Array<boolean>;
  public loadedConfig: boolean;
  public disableAll: boolean;
  public validMetadata: boolean;

  public tipoAllegatoSelezionato: TipoAllegatoVO;
  public tipoAllegatoModel: Array<TipoAllegatoVO>;
  public nomeAllegatoTmp: string;
  public configAllegatoSelezionato: Array<Array<ConfigAllegatoVO>>;

  public metaDataModel: Array<AllegatoFieldVO>;
  public comboModel: Array<Array<SelectVO>>;
  public maxSize: number = 5242880;
  public tmpModel: Array<Elem>; //array elementi senza tipo, da rimappare alla fine in array effettivo
  public typeAlert: boolean = false;
  public sizeAlert: boolean = false;

  public confermaM: boolean = true;
  public eliminaM: boolean = true;
  public allegatoM: boolean = false;
  public senzaAllegati: boolean = true;
  public flagAllegatoMaster: boolean = false;
  typeAction: any = {
    isCombo: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_ELENCO : false;
    },
    isDate: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_DATE : false;
    },
    isText: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_STRING : false;
    },
    isNumeric: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_NUMERIC : false;
    },
    isCheckbox: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_BOOLEAN : false;
    },
    isDataOra: (t: SelectVO): boolean => {
      return t ? t.id === Constants.FT_DATA_ORA : false;
    },
    getInputType: (ft: SelectVO): string => {
      if ( this.typeAction.isText(ft) || this.typeAction.isDate(ft) || this.typeAction.isDataOra(ft)){
        return "text";
      }
      if (this.typeAction.isNumeric(ft)) {
        return "number";
      }
      if (this.typeAction.isCheckbox(ft)) {
        return "checkbox";
      }
    },
  };

  constructor(
    private logger: LoggerService,
    private sharedRiscossioneService: SharedRiscossioneService,
    private allegatoSharedService: AllegatoSharedService,
    private router: Router,
    private sharedOrdinanzaConfigService: SharedOrdinanzaConfigService,
    private numberUtilsSharedService: NumberUtilsSharedService
  ) {}

  ngOnInit(): void {
    this.logger.init(PregressoRicevutaPagamentoOrdinanzaInsComponent.name);
    this.loaded = false;
    this.setConfig();

    this.sollecito = new SollecitoVO();
    this.idOrdinanzaVerbaleSoggetto = [];
    this.loaded = true;
    this.mapConfigAllegati = new Map();
    this.callConfig();
    setTimeout(() => {
      this.subscribers.form = this.creaDisposizioneForm.valueChanges.subscribe(
        (data) => {
          this.onChangeDataEmitter();
        }
      );
    });
  }

  onSelect(els: TableSoggettiOrdinanza) {
    if (els) {
      this.idOrdinanzaVerbaleSoggetto = [];
      this.idOrdinanzaVerbaleSoggetto[0] = els.idSoggettoOrdinanza;
      this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    } else {
      this.idOrdinanzaVerbaleSoggetto = [];
      this.sollecito.idSoggettoOrdinanza = null;
    }
    this.onChangeDataEmitter();
  }

  onChangeDataEmitter() {
    let obj: any = {};
    if (
      !this.creaDisposizioneForm.valid ||
      this.idOrdinanzaVerbaleSoggetto.length == 0
    ) {
      obj.disabled = true;
    } else {
      obj.disabled = false;
      obj.idOrdinanzaVerbaleSoggetto = this.idOrdinanzaVerbaleSoggetto;
      obj.tipoAllegatoSelezionatoId = this.tipoAllegatoSelezionato.id;
      this.mapMetadati();
      obj.metaDataModel = this.metaDataModel;
    }
    this.onChangeData.emit(obj);
  }

  mapMetadati() {
    let i: number;
    for (i = 0; i < this.tmpModel.length; i++) {
      this.metaDataModel[i].idField = this.tmpModel[i].idField;
      this.metaDataModel[i].fieldType = this.tmpModel[i].fieldType;
      if (this.typeAction.isNumeric(this.metaDataModel[i].fieldType)){
        this.metaDataModel[i].numberValue = this.tmpModel[i].value;
      } else if (this.typeAction.isText(this.metaDataModel[i].fieldType)){
        this.metaDataModel[i].stringValue = this.tmpModel[i].value;
      } else if (this.typeAction.isDate(this.metaDataModel[i].fieldType)){
        this.metaDataModel[i].dateValue = this.tmpModel[i].value;
      } else if (this.typeAction.isCheckbox(this.metaDataModel[i].fieldType)){
        this.metaDataModel[i].booleanValue = this.tmpModel[i].value;
      } else if (
        this.typeAction.isCombo(this.metaDataModel[i].fieldType) &&
        this.tmpModel[i].value
      ){
        this.metaDataModel[i].numberValue = this.tmpModel[i].value.id;
      }  else if (this.typeAction.isDataOra(this.metaDataModel[i].fieldType)) {//salvo l'ID
        this.metaDataModel[i].dateTimeValue = this.tmpModel[i].value;
      }
    }
  }

  callConfig() {
    // recupero lista config
    this.subscribers.configAllegato = this.allegatoSharedService
      .getConfigAllegato()
      .map((config) => {
        this.mapConfigAllegati = this.remapConfig(
          // IMPORTANTE: campo "ordine" e "riga" coerenti come numerazione crescente sul DB!
          config.sort((el, el2) => {
            if (el.idTipo > el2.idTipo) return 1;
            if (el.idTipo < el2.idTipo) return -1;
            return el.ordine > el2.ordine ? 1 : -1;
          })
        );
        this.mostraMetadati();
      })
      .subscribe(
        (data) => {          /**/        },
        (err) => {          this.logger.error("Errore nel recupero dei config degli allegati");        }
      );
  }
  /**
   * Trasforma dati configurazione da BE del form di allegati in mappa { key=id tipo,value=array config }
   *  Copiato da G R M E D :)
   * @param data  dati da trasformare
   */
  remapConfig(
    data: Array<ConfigAllegatoVO>
  ): Map<number, Array<Array<ConfigAllegatoVO>>> {
    let res: Map<number, Array<Array<ConfigAllegatoVO>>> = new Map();
    data.forEach((e, i) => {
      let c;
      if (!res.has(e.idTipo)) res.set(e.idTipo, []);
      c = res.get(e.idTipo);
      // Dividere config in più array x elemento.riga identici:
      // 1) se inizio ciclo sul tipo
      // 2) nuovo valore prop "riga" rispetto elemento precedente
      if (c.length === 0 || e.riga !== (i < 1 ? 0 : data[i - 1].riga))
        c.push([]);
      c[c.length - 1].push(e);
    });
    return res;
  }

  onBlur($event, index: number, type: FieldTypeVO) {
    if (!(this.typeAction.isDate(type) || this.typeAction.isDataOra(type)))
      return;
    this.tmpModel[index].value = $event.srcElement.value;
  }
  check(index: number, type: FieldTypeVO) {
    if (!this.typeAction.isCheckbox(type)) return;
    this.tmpModel[index].value = !this.tmpModel[index].value;
  }



  manageDatePicker(i: number) {
    var str: string = "#datetimepicker_" + i.toString();
    if (this.typeAction.isDataOra(this.tmpModel[i].fieldType)) {
      if ($(str).length) {
        $(str).datetimepicker({
          format: "DD/MM/YYYY, HH:mm",
        });
      }
    } else if (this.typeAction.isDate(this.tmpModel[i].fieldType)) {
      if ($(str).length) {
        $(str).datetimepicker({
          format: "DD/MM/YYYY",
        });
      }
    }
  }



  initModel() {
    this.tmpModel = new Array<Elem>();
    this.metaDataModel = new Array<AllegatoFieldVO>();
    this.comboModel = new Array<Array<SelectVO>>();
    this.comboLoaded = new Array<boolean>();
    this.tipoAllegatoModel = this.tipoAllegatoInput;
    this.senzaAllegati = this.allegati;
  }
  isKeyPressed(code: number, type: FieldTypeVO): boolean {
    if (!this.typeAction.isNumeric(type)) return true;
    return this.numberUtilsSharedService.numberValid(code);
  }
  mostraMetadati() {
    // disposizioni del giudice
    
    this.tipoAllegatoSelezionato = this.tipoAllegatoInput.find(
      (item) => item.id == this.idTipo
    );
    this.loadedConfig = false;
    this.validMetadata = false;
    this.disableAll = false;
    this.initModel();
    let $idTipo: number = this.tipoAllegatoSelezionato.id;

    this.senzaAllegati = false;

    // se trovo un id  cerco config corrispondente
    if ($idTipo != 10 && this.mapConfigAllegati.has($idTipo)) {
      this.configAllegatoSelezionato = this.mapConfigAllegati.get($idTipo);

      // inizializzo model
      let index = 0;
      this.configAllegatoSelezionato.forEach((arr, i) => {
        arr.forEach((el, j) => {
          let elem: Elem = {
            value: null,
            fieldType: el.fieldType,
            idField: el.idField,
          };
          el.idModel = index;
          index++;
          this.tmpModel.push(elem);
          this.metaDataModel.push(new AllegatoFieldVO());
          if (this.typeAction.isCombo(el.fieldType)) {
            this.subscribers.getSelect = this.allegatoSharedService
              .getDecodificaSelectAllegato(el.idFonteElenco)
              .subscribe(
                (data) => {
                  this.comboModel[el.idModel] = data;
                  this.comboLoaded[el.idModel] = true;
                  this.logger.info(
                    "Caricata lista per il campo numero " + el.idModel
                  );
                },
                (err) => {
                  this.logger.error(
                    "Errore nel recupero dell'elenco di " + el.idFonteElenco
                  );
                }
              );
          }
        });
      });

      this.loadedConfig = true;
    }
  }

  loadSollecitiEsistentiCopiaPerRichiestaBollettini() {
    this.loaded = false;
    this.listaSolleciti = new Array<SollecitoVO>();
    this.subscribers.solleciti = this.sharedRiscossioneService
      .getListaSolleciti(this.idOrdinanzaVerbaleSoggetto[0])
      .subscribe(
        (data) => {
          this.listaSolleciti = data;
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
    }, 1000);
  }

  manageMessageTop(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.scrollTopEnable = true;
    this.timerShowMessageTop();
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
    if (el.statoSollecito.id == Constants.SOLLECITO_BOZZA) {
      this.modificabile = true;
    } else {
      this.modificabile = false;
    }
    this.isModifica = true;
    this.isNuovo = false;

    if(this.sharedRiscossioneSollecitoDettaglioComponent){
      this.sharedRiscossioneSollecitoDettaglioComponent.aggiornaNotifica();
    }
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalTop);
  }

  getData() {
    return {
      sollecito: this.sollecito,
      notifica: this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject(),
    };
  }

  annullaModifica() {
    this.sollecito = new SollecitoVO();
    this.sollecito.idSoggettoOrdinanza = this.idOrdinanzaVerbaleSoggetto[0];
    this.isModifica = false;
    this.isNuovo = true;
  }


  isDisabledCreaSollecito(): boolean {
    //JIRA - Gestione Notifica: non necessaria qui in questa sezione
    let checkNotifica: boolean = true;
    if (
      this.sharedRiscossioneSollecitoDettaglioComponent != null ||
      this.sharedRiscossioneSollecitoDettaglioComponent != undefined
    ) {
      if (
        this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject() !=
          null ||
        this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject() !=
          undefined
      ) {
        if (
          this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()
            .importoSpeseNotifica == undefined ||
          this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()
            .importoSpeseNotifica == null
        ) {
          checkNotifica = true;
        } else {
          checkNotifica = isNaN(
            Number(
              this.sharedRiscossioneSollecitoDettaglioComponent.getNotificaObject()
                .importoSpeseNotifica
            )
          );
        }
      }
    }
    if (checkNotifica) return true;

    return !this.sollecito.importoSollecitato;
  }

  scrollTopEnable: boolean;
  ngAfterViewChecked() {
    let scrollTop: HTMLElement = document.getElementById("scrollTop");
    if (this.scrollTopEnable && scrollTop != null) {
      scrollTop.scrollIntoView();
      this.scrollTopEnable = false;
    }
    let i: number;
    if (this.tmpModel) {
      for (i = 0; i < this.tmpModel.length; i++) this.manageDatePicker(i);
    }
  }

  ngOnDestroy(): void {
    this.logger.destroy(PregressoRicevutaPagamentoOrdinanzaInsComponent.name);
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
        {          columnName: "numeroProtocollo",          displayName: "Numero Protocollo",        },
        {          columnName: "dataScadenza",          displayName: "Data Scadenza",        },
        {          columnName: "importoSollecitatoString",          displayName: "Importo da sollecitare",        },
        {          columnName: "maggiorazioneString",          displayName: "Maggiorazione",        },
        {          columnName: "statoSollecito.denominazione",          displayName: "Stato",        },
      ],
    };
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

}
