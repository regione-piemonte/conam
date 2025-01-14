import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  OnChanges,
  ElementRef,
  ViewChild,
  Output,
  EventEmitter,
  AfterViewInit,
  SimpleChanges,
} from "@angular/core";
import { LoggerService } from "../../../../core/services/logger/logger.service";
import { RicercaProtocolloRequest } from "../../../../commons/request/verbale/ricerca-protocollo-request";

import { AllegatoFieldVO } from "../../../../commons/vo/verbale/allegato-field-vo";
import { AllegatoSharedService } from "../../../service/allegato-shared.service";
import { ConfigAllegatoVO } from "../../../../commons/vo/verbale/config-allegato-vo";
import { SalvaAllegatoRequest } from "../../../../commons/request/salva-allegato-request";
import { RiepilogoVerbaleVO } from "../../../../commons/vo/verbale/riepilogo-verbale-vo";
import { Constants } from "../../../../commons/class/constants";
import { NumberUtilsSharedService } from "../../../service/number-utils-shared.service";
import { Router } from "@angular/router";
import {
  FieldTypeVO,
  SelectVO,
  TipoAllegatoVO,
} from "../../../../commons/vo/select-vo";

import { SafeValue } from "@angular/platform-browser";
import {
  DocumentoStiloVolist,

  StiloResearch,
  fieldStiloResearch,
  ResponseSearchStilo,
} from "../../../../commons/vo/ordinanza/stilo-research-vo";
import { ExceptionVO } from "../../../../commons/vo/exceptionVO";
import { MessageVO } from "../../../../commons/vo/messageVO";
import { DatePipe } from "@angular/common";
import { saveAs } from "file-saver";
import { Config } from "../../../module/datatable/classes/config";
import { AllegatoVO } from "../../../../commons/vo/verbale/allegato-vo";
import { MyUrl } from "../../../module/datatable/classes/url";
import { winLoadingHtml } from "../../../../fascicolo/components/fascicolo-allegato-da-acta/win-loading-html";
import { DocumentoProtocollatoVO } from "../../../../commons/vo/verbale/documento-protocollato-vo";
declare var $: any;
interface Elem {
  value: any;
  fieldType: FieldTypeVO;
  idField: number;
}
@Component({
  selector: "ricerca-stilo",
  templateUrl: "./ricerca-stilo.component.html",
})
export class RicercaStiloComponent implements OnInit {
  @Input() idStiloResearch: any;
  ////
  @Input()
  tipoAllegatoInput?: Array<TipoAllegatoVO>;
  @Input()
  allegati?: boolean;
  @Input()
  pregresso?: boolean = false;
  @Input() allegatoFormValues?: SalvaAllegatoRequest = null;
  @Input()
  riepilogoVerbale?: RiepilogoVerbaleVO;
  @Output()
  onChangeCategoriaDocumento = new EventEmitter<any>();
  @Input()
  current?: number = 0;
  @Output()
  onNewFile = new EventEmitter<any>();
  public comboModel: Array<Array<SelectVO>>;
  public metaDataModel: Array<AllegatoFieldVO>;
  public tmpModel: Array<Elem>; //un array di elementi senza tipo, da rimappare nell'array effettivo alla fine
  public maxSize: number = 5242880;
  public sizeAlert: boolean = false;
  public typeAlert: boolean = false;
  public subscribers: any = {};

  public senzaAllegati: boolean = true;
  public tipoAllegatoModel: Array<TipoAllegatoVO>;
  public tipoAllegatoSelezionato: TipoAllegatoVO;
  public configAllegatoSelezionato: Array<Array<ConfigAllegatoVO>>;
  public nuovoAllegato: SalvaAllegatoRequest;
  public nomeAllegatoTmp: string;

  public loadedConfig: boolean;
  public comboLoaded: Array<boolean>;
  public validMetadata: boolean;
  public disableAll: boolean;
  public mapConfigAllegati: Map<number, Array<Array<ConfigAllegatoVO>>>;

  //Messaggio top
  private intervalTop: number = 0;
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  showTable: boolean = false;
  loaded: boolean = false;
  configStiloFiles: Config;
  searchedFiles: DocumentoStiloVolist[];
  documentSelected: DocumentoStiloVolist = null;
  constructor(
    private numberUtilsSharedService: NumberUtilsSharedService,
    private router: Router,
    private logger: LoggerService,
    private datePipe: DatePipe,
    private allegatoSharedService: AllegatoSharedService
  ) {}

  ngOnInit(): void {}

  ngOnChanges(changes: SimpleChanges) {
    this.formField();
  }
  ngAfterViewInit(): void {
    let i: number;
    //console.log(this.tmpModel, this.allegatoFormValues);
    if (this.tmpModel) {
      for (i = 0; i < this.tmpModel.length; i++) this.manageDatePicker(i);
    }
  }

  ngAfterViewChecked() {}
  formField() {
    this.mapConfigAllegati = new Map();
    this.callConfig();
    //this.initModel();
  }
  initModel() {
    this.tmpModel = new Array<Elem>();
    this.metaDataModel = new Array<AllegatoFieldVO>();
    this.comboModel = new Array<Array<SelectVO>>();
    this.comboLoaded = new Array<boolean>();
    this.nuovoAllegato = new SalvaAllegatoRequest();
    this.tipoAllegatoModel = this.tipoAllegatoInput;
    this.senzaAllegati = this.allegati;
    //  this.nuovoAllegatoMultiple = new SalvaAllegatoMultipleRequest();
  }
  mapMetadati() {
    let i: number;
    for (i = 0; i < this.tmpModel.length; i++) {
      this.metaDataModel[i].idField = this.tmpModel[i].idField;
      this.metaDataModel[i].fieldType = this.tmpModel[i].fieldType;
      if (this.typeAction.isNumeric(this.metaDataModel[i].fieldType))
        this.metaDataModel[i].numberValue = this.tmpModel[i].value;
      else if (this.typeAction.isText(this.metaDataModel[i].fieldType))
        this.metaDataModel[i].stringValue = this.tmpModel[i].value;
      else if (this.typeAction.isDate(this.metaDataModel[i].fieldType))
        this.metaDataModel[i].dateValue = this.tmpModel[i].value;
      else if (this.typeAction.isCheckbox(this.metaDataModel[i].fieldType))
        this.metaDataModel[i].booleanValue = this.tmpModel[i].value;
      else if (
        this.typeAction.isCombo(this.metaDataModel[i].fieldType) &&
        this.tmpModel[i].value
      )
        this.metaDataModel[i].numberValue = this.tmpModel[i].value.id;
      //salvo l'ID
      else if (this.typeAction.isDataOra(this.metaDataModel[i].fieldType))
        this.metaDataModel[i].dateTimeValue = this.tmpModel[i].value;
    }
  }
  onBlur($event, index: number, type: FieldTypeVO) {
    if (!(this.typeAction.isDate(type) || this.typeAction.isDataOra(type)))
      return;
    this.tmpModel[index].value = $event.srcElement.value;
  }

  confermaMetadati() {
    //bisogna mappare tmpModel in metaDataModel
    this.mapMetadati();
    //disabilito tutti i campi
    this.disableAll = true;
    //copio i metadati
    this.nuovoAllegato = new SalvaAllegatoRequest();
    this.nuovoAllegato.allegatoField = this.metaDataModel;
    this.validMetadata = true;
    this._onValid();

    // this.onNewFile.emit(this.nuovoAllegato);

    //remap the obj for the Stilo API, and searche the doc on stilo
    this.setFormValues(this.nuovoAllegato);
    this.createRequest();
  }

  //Remap the obj for the research on Stilo
  createRequest() {
    let request: StiloResearch;
    let fields: fieldStiloResearch[] = [];
    this.tmpModel.forEach((el) => {
      let field: fieldStiloResearch = { idField: el.idField, value: el.value };
      fields.push(field);
    });
    request = {
      idRicerca: this.idStiloResearch,
      ricerca: fields,
    };
    this.searchOnStilo(request);
  }

  completeInfo(lst) {
    //this.setDob = datePipe.transform(userdate, 'dd/MM/yyyy');
  
    lst.map(
      (item) => (item.data = this.datePipe.transform(item.data, "dd/MM/yyyy"))
    );
    lst.map(
      (item) => (item.fileName =   new MyUrl(<string>item.filename, null))
    );
    //lst.map((item) => (item.myUrl =          item.theUrl = new MyUrl(item.nome, null)));
  }

  // Search on Stilo
  searchOnStilo(request: StiloResearch) {
    this.loaded = false;
    this.setConfigTable();

    this.allegatoSharedService.getDocOnStilo(request).subscribe(
      (data: ResponseSearchStilo) => {
        this.searchedFiles = data.documentoStiloVOList;
        this.completeInfo(this.searchedFiles);
 
        this.showTable = true;
        this.loaded = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err);
        }
        this.logger.error("Errore nel salvataggio dell'allegato");
        this.loaded = true;
      }
    );
  }
  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 20; //this.configService.getTimeoutMessagge();
    this.intervalTop = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }
  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalTop);
  }
  manageMessage(mess: ExceptionVO | MessageVO) {
    this.typeMessageTop = mess.type;
    this.messageTop = mess.message;
    this.timerShowMessageTop();
  }
  onSelected(event) {
    this.documentSelected = event;
  }
  callConfig() {
    //recupero la lista di config

    //  let idRicerca= 1;

    this.subscribers.configAllegato = this.allegatoSharedService
      .getConfigStiloSearch(this.idStiloResearch)
      .map((config) => {
        this.mapConfigAllegati = this.remapConfig(config);
      })
      .subscribe(
        (data) => {
          console.log(data);
          //console.log(this.mapConfigAllegati)
          this.mostraMetadati();

          // set Form Values
          if (this.allegatoFormValues != null) {
            this.setFormValues(this.allegatoFormValues);
          }
        },
        (err) => {
          this.logger.error("Errore nel recupero dei config degli allegati");
        }
      );
  }
  /**
   * Trasforma i dati di configurazione da BE del form di allegati in una mappa {key=id tipo,value=array di config}
   * Copiato da GRMED :)
   * @param data dati da trasformare
   */
  remapConfig(
    data: Array<ConfigAllegatoVO>
  ): Map<number, Array<Array<ConfigAllegatoVO>>> {
    let res: Map<number, Array<Array<ConfigAllegatoVO>>> = new Map();

    data.forEach((e, i) => {
      let c;
      if (!res.has(e.idTipo)) res.set(e.idTipo, []);
      c = res.get(e.idTipo);
      // Dividere i config in più array per elemento.riga identici:
      // 1) se inizio del ciclo sul tipo
      // 2) nuovo valore prop "riga" rispetto a elemento precedente
      if (c.length === 0 || e.riga !== (i < 1 ? 0 : data[i - 1].riga))
        c.push([]);
      c[c.length - 1].push(e);
    });
    return res;
  }

  emitCat() {
    this.onChangeCategoriaDocumento.emit({
      tipoAllegatoSelezionato: this.tipoAllegatoSelezionato,
      current: this.current,
    });
  }

  manageDatePicker(i: number) {
    var str: string =
      "#datetimepicker_" + this.current.toString() + "_" + i.toString();
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
  //restituisce true se tutti i campi combo sono stati caricati
  isComboLoaded(): boolean {
    let flag: boolean = true;
    this.tmpModel.forEach((el, i) => {
      if (
        flag &&
        this.typeAction.isCombo(el.fieldType) &&
        !this.comboLoaded[i]
      ) {
        flag = false;
      }
    });
    return flag;
  }

  mostraMetadati() {
    this.loadedConfig = false;
    this.validMetadata = false;
    this.disableAll = false;
    this.initModel();

    let $idTipo: number = 2;

    this.emitCat();

    if ($idTipo == 7 || $idTipo == 22 || $idTipo == 1) {
      this.senzaAllegati = true;
    } else {
      this.senzaAllegati = false;
    }
    // se trovo un id, cerco il config corrispondente
    //ID for the Stilo Research
    if ($idTipo === 2) {
      this.configAllegatoSelezionato = this.mapConfigAllegati.get($idTipo);

      //inizializzo i model
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
            if (el.fieldType.id == 5) {
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
            } else if (el.fieldType.id == 7) {
              this.subscribers.getSelect = this.allegatoSharedService
                .getDecodificaSelectSoggettiAllegato(
                  this.riepilogoVerbale.verbale.id
                )
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
            } else {
              this.subscribers.getSelect = this.allegatoSharedService
                .getDecodificaSelectSoggettiAllegatoCompleto(
                  this.riepilogoVerbale.verbale.id
                )
                .subscribe(
                  (data) => {
                    this.comboModel[el.idModel] = data;
                    this.comboLoaded[el.idModel] = true;
                    this.loadedConfig = true;
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
          }
        });
      });
      this.loadedConfig = true;
      this.loaded = true;
    }

    this._onValid();
  }
  setFormValues(nuovoAllegato: SalvaAllegatoRequest) {
    //console.log(this.tmpModel, this.allegatoFormValues.allegatoField);
    //RIMAPPATO L'OGGETTO PER IL SET DEL FORM PERCHE'
    //ALLEGATO FIELD E TMPMODEL DIVERSI
    nuovoAllegato.allegatoField.forEach((el) => {
      this.tmpModel.forEach((formInstance, key) => {
        if (el.idField === formInstance.idField) {
          let value;
          if (el.booleanValue) {
            value = el.booleanValue;
          } else if (el.dateTimeValue) {
            value = el.dateTimeValue;
          } else if (el.dateValue) {
            value = el.dateValue;
          } else if (el.stringValue) {
            value = el.stringValue;
          } else if (el.numberValue) {
            //VA CATCHATO IL CASO IN CUI SIA UNA SELECT
            // PERCHE GESTITA DIVERSAMENTE
            if (el.idField === 21) {
              value = { id: el.numberValue };
            } else {
              value = el.numberValue;
            }
          }

          this.tmpModel[key].value = value;
          console.log(this.tmpModel);
        }
      });
    });
  }
  private _onValid() {
    let $idTipo: number = 2;
    let onValidObj = {
      current: this.current,
      valid: this.validMetadata || !this.loadedConfig,
      validMetadata: this.validMetadata,
      idTipo: $idTipo,
      metaDataModel: this.nuovoAllegato.allegatoField,
    };
    // this.onValid.emit(onValidObj);
    //console.log(onValidObj);
  }
  typeAction: any = {
    isCombo: (t: SelectVO): boolean => {
      if (t.id === Constants.FT_ELENCO) {
        return t ? t.id === Constants.FT_ELENCO : false;
      } else {
        return t
          ? t.id === Constants.FT_ELENCO_SOGG ||
              t.id === Constants.FT_ELENCO_SOGG_COMPL
          : false;
      }
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
      if (
        this.typeAction.isText(ft) ||
        this.typeAction.isDate(ft) ||
        this.typeAction.isDataOra(ft)
      )
        return "text";
      if (this.typeAction.isNumeric(ft)) return "number";

      if (this.typeAction.isCheckbox(ft)) return "checkbox";
    },
  };

  check(index: number, type: FieldTypeVO) {
    if (!this.typeAction.isCheckbox(type)) return;
    if (this.tmpModel[index].value == null || this.tmpModel[index].value == "")
      this.tmpModel[index].value = "false";
    this.tmpModel[index].value =
      this.tmpModel[index].value == "false" ? "true" : "false";
  }
  isKeyPressed(code: number, type: FieldTypeVO, label: string): boolean {

    if (!this.typeAction.isNumeric(type)) return true;
    if (label !== "Numero raccomandata") {
      return this.numberUtilsSharedService.numberValid(code);
    } else {
      return (
        (code >= 48 && code <= 57) ||
        (code >= 96 && code <= 105) ||
        code == 8 ||
        code == 46
      );
    }
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  ///

  onSave() {
    this.onNewFile.emit(this.documentSelected);
    this.searchedFiles = [];
    this.showTable = false;
    //send file throught output
  }

  onReset() {
    this.searchedFiles = [];
    this.showTable = false;
    this.documentSelected = null;
    this.disableAll = false;
  }

  getAllegato(el: DocumentoProtocollatoVO) {
  
    this.openAllegato(el);
  }

  openAllegato(el: DocumentoProtocollatoVO) {
    console.log(el);
    const myFilename =
      typeof el.filename == "string" ? el.filename : el.filename.nomeFile;

    let type = myFilename.slice(myFilename.lastIndexOf(".") + 1);

    const blob = this.dataURItoBlob(el.file, type);
    const a = document.createElement("a");
    document.body.appendChild(a);
    const url = URL.createObjectURL(blob);
    a.href = url;
    a.download = myFilename;
    a.click();
    window.URL.revokeObjectURL(url);
    document.body.removeChild(a);
  }
  dataURItoBlob(dataURI, type) {
    const byteString = window.atob(dataURI);
    const arrayBuffer = new ArrayBuffer(byteString.length);

    const int8Array = new Uint8Array(arrayBuffer);
    for (let i = 0; i < byteString.length; i++) {
      int8Array[i] = byteString.charCodeAt(i);
    }
    const blob = new Blob([int8Array], { type: `application/${{ type }}` });
    return blob;
  }
  setConfigTable() {
    this.configStiloFiles = {
      pagination: {
        enable: false,
      },
      selection: {
        enable: true,
        selectionType: 0,
      },
      sort: {
        enable: false,
      },
      columns: [
        {
          columnName: "fileName",
          displayName: "Nome File",
          link: {
            enable: true,
          },
        },
        {
          columnName: "oggetto",
          displayName: "Oggetto",
        },
        {
          columnName: "numero",
          displayName: "Numero",
        },
        {
          columnName: "anno",
          displayName: "Anno",
        },
        {
          columnName: "data",
          displayName: "Data adozione atto",
        },
      ],
    };
  }
}
