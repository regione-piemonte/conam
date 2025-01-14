import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  OnChanges,
  Output,
  EventEmitter,
  AfterViewInit,
  SimpleChanges,
  ElementRef,
  ViewChild,
} from "@angular/core";
import { LoggerService } from "../../../../core/services/logger/logger.service";

import { AllegatoFieldVO } from "../../../../commons/vo/verbale/allegato-field-vo";
import { AllegatoSharedService } from "../../../service/allegato-shared.service";
import { ConfigAllegatoVO } from "../../../../commons/vo/verbale/config-allegato-vo";
import {
  AllegatiSecondariRequest,
  AllegatoMultipleFields,
  SalvaAllegatoRequest,
} from "../../../../commons/request/salva-allegato-request";
import { RiepilogoVerbaleVO } from "../../../../commons/vo/verbale/riepilogo-verbale-vo";
import { NumberUtilsSharedService } from "../../../service/number-utils-shared.service";
import { Router } from "@angular/router";
import {
  FieldTypeVO,
  SelectVO,
  TipoAllegatoVO,
} from "../../../../commons/vo/select-vo";

import {
  DocumentoStiloVolist,
  ResponseSearchStilo,
  StiloResearch,
  fieldStiloResearch,
} from "../../../../commons/vo/ordinanza/stilo-research-vo";
import { ExceptionVO } from "../../../../commons/vo/exceptionVO";
import { MessageVO } from "../../../../commons/vo/messageVO";
import { DatePipe } from "@angular/common";
import { FormArray, FormBuilder, FormControl, FormGroup } from "@angular/forms";
import { AllegatoMultipleFieldVO } from "../../../../commons/vo/verbale/allegato-multiple-field-vo";
import { AllegatoUtilsSharedService } from "../../../service/allegato-utils-shared.service";
import { FileItem, FileUploader } from "ng2-file-upload";
import { timer } from "rxjs/observable/timer";
import { Config } from "protractor";

interface Elem {
  value: any;
  fieldType: FieldTypeVO;
  idField: number;
}
const URL = "https://evening-anchorage-3159.herokuapp.com/api/";

interface Upload {
  dtConfig: Config;
  uploader: ElementRef;
  actions: any;
}

@Component({
  selector: "documento-non-prot",
  templateUrl: "./documento-non-prot.component.html",
})
export class DocumentoNonProtComponent implements OnInit {
  @ViewChild("file") set uploader(uploader: ElementRef) {
    this.upload.uploader = uploader;
  }
  @Input() isConfermaMetadatiDisabled: boolean = false;
  @Input() isDisableAll: boolean = false;
  @Output() masterAttachments = new EventEmitter<any>();

  tableForm: FormGroup;
  public maxSize: number = 5242880;
  public sizeAlert: boolean = false;
  public typeAlert: boolean = false;
  allowedFilesType = ["pdf", "tiff", "jpg", "jpeg", "p7m"];
  allowedFilesTypeMessage: string;
  public eliminaM: boolean = true;
  public confermaM: boolean = true;
  index: number;
  nuovoAllegato: AllegatoMultipleFields = new AllegatoMultipleFields();
  nomeAllegatoTmp: string;
  rowsCount: number = 1;
  public subscribers: any = {};

  origin = [
    { id: 10, denominazione: "Interna" },
    { id: 11, denominazione: "Esterna" },
  ];
  //Messaggio top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  public typeMessageTopM: String;
  public messageTopM: String;
  attachmentsRequest: AllegatiSecondariRequest[] = [];
  get docAttachments() {
    return this.tableForm.get("docAttachments") as FormArray;
  }

  constructor(
    private fb: FormBuilder,
    private allegatoService: AllegatoUtilsSharedService,
    private logger: LoggerService
  ) {}

  ngOnInit() {
    this.tableForm = this.fb.group({
      docAttachments: this.fb.array([
        this.fb.group({
          oggetto: new FormControl({ value: "", disabled: false }),
          origine: new FormControl({ value: "", disabled: false }),
        }),
      ]),
    });
  }
  uploadAttachment() {
    //per ogni riga se il doc c'è il pulsante viene sostituito con il remove
    // e viene mostrato il nome del file
    //per ogni upload ci sarà un emitter verso il padre per aggiornare la lista degli allegati
    //il campo origina è una select
    //restringere i due campi e spostarli al centro
  }
  getDenominazioneById(id: number): string {
    const item = this.origin.find((o) => o.id === id);
    return item ? item.denominazione : "ID non trovato";
  }
  confermaAllegato(files: FileItem[]) {
    for (let i = 0; i < files.length; i++) {
      let key: File = files[i]._file;
      if (key.size > this.maxSize) {
        this.sizeAlert = true;
        this.logger.info("File size: " + key.size);
        break;
      } else {
        let allegato: AllegatoMultipleFieldVO = new AllegatoMultipleFieldVO();
        this.sizeAlert = false;
        allegato.file = key;
        allegato.filename = allegato.file.name;
        //allegato.idTipoAllegato =  28;

        //spezza il nome del file da visualizzare se è troppo lungo e senza spazi. Non copre tutti i casi, ma dovrebbe bastare
        if (
          allegato.filename.length > 30 &&
          allegato.filename.split(" ").length == 1
        ) {
          if (this.nuovoAllegato.filename.length <= 60) {
            this.nomeAllegatoTmp =
              allegato.filename.slice(0, 30) +
              " " +
              allegato.filename.slice(30);
          } else
            this.nomeAllegatoTmp =
              allegato.filename.slice(0, 30) +
              " " +
              allegato.filename.slice(30, 60) +
              " " +
              allegato.filename.slice(60);
        }
        let type: string = "";
        let array: string[] = allegato.filename.split(".");
        if (array.length > 1) {
          type = array[array.length - 1];
        }
        if (
          this.allowedFilesType.filter((x) => x == type.toLowerCase()).length ==
          0
        ) {
          this.typeAlert = true;
          this.confermaM = true;
          this.allowedFilesTypeMessage =
            "I documenti caricati non sono idonei all'archiviazione su Doqui ACTA. Si prega di ricontrollare l'estensione dei file selezionati";
          break;
        } else {
          this.typeAlert = false;
          this.confermaM = false;
          this.eliminaM = true;
          this.manageMessageTop(
            " I documenti caricati possono essere archiviati su Doqui ACTA. Per proseguire cliccare sul tasto Allega",
            "SUCCESS",
            true
          );

          //this.nuovoAllegatoMultiple.allegati.push(allegato);
        }
      }
    }
  }

  refactorObject(i) {
    this.tableForm.value.docAttachments.forEach((el, index) => {
      if (index === i) {
        this.attachmentsRequest.push(el);
        this.attachmentsRequest[i].file = this.nuovoAllegato.file;
        this.attachmentsRequest[i].filename = this.nuovoAllegato.filename;
      }
      //console.log('refactorObject',this.attachmentsRequest, this.tableForm);
      //console.log(this.attachmentsRequest[0].file);
      this.masterAttachments.emit(this.attachmentsRequest);
    });
  }
  addRow() {
    this.docAttachments.push(
      this.fb.group({
        oggetto: new FormControl(),
        origine: new FormControl(),
      })
    );
    return this.rowsCount++;
  }



  removeAttachment(i) {
    this.docAttachments.removeAt(i);
    this.attachmentsRequest.splice(i, 1);
    //console.log(this.attachmentsRequest, this.tableForm);
    this.masterAttachments.emit(this.attachmentsRequest);




    // Verifica se esiste già un gruppo con tutti i controlli vuoti
    const existsEmptyGroup = this.docAttachments.controls.some((group: FormGroup) => {
      // Ottieni tutti i valori dei controlli del gruppo come array
      const controlValues = Object.keys(group.controls).map(key => group.controls[key].value);
      // Verifica se tutti i valori sono vuoti (null, undefined o stringa vuota)
      return controlValues.every(value => value === null || value === undefined || value === '');
    });
    // Se non esiste un gruppo vuoto, aggiungi un nuovo gruppo
    if (!existsEmptyGroup) {
      this.addRow();
    }

  }
  onSubmit() {
    //console.log(this.tableForm.value);
  }

  ///
  manageMessageTop(message: string, type: string, multipli: boolean) {
    if (multipli) {
      this.typeMessageTopM = type;
      this.messageTopM = message;
      this.timerShowMessageTop();
    } else {
      this.typeMessageTop = type;
      this.messageTop = message;
      this.timerShowMessageTop();
    }
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    const source = timer(160, 160).take(31);
    this.subscribers.timer = source.subscribe((val) => {
      if (val == 30) this.resetMessageTop();
    });
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
  }

  // Gestione allegati
  public uploaderM: FileUploader = new FileUploader({ url: URL });
  public upload: Upload = {
    dtConfig: null,
    uploader: null,
    actions: {
      addFile: (i?: number) => {
        this.logger.info("=> Add file");
        //console.log(i);
        this.index = i;
        this.upload.uploader.nativeElement.click();
      },
      onFilesAdded: (i?: number) => {
        //console.log("dff", i);
        const files: { [key: string]: File } =
          this.upload.uploader.nativeElement.files;
        for (let key in files) {
          if (!isNaN(parseInt(key))) {
            if (files[key].size > this.maxSize) {
              this.sizeAlert = true;
              this.logger.info("File size: " + files[key].size);
            } else {
              this.sizeAlert = false;
              this.nuovoAllegato.file = files[key];
              this.nuovoAllegato.filename = this.nuovoAllegato.file.name;
              /*  this.nuovoAllegato.idTipoAllegato =
                  this.tipoAllegatoSelezionato.id;  */
              let type: string = "";
              let array: string[] = this.nuovoAllegato.filename.split(".");
              if (array.length > 1) {
                type = array[array.length - 1];
              }
              this.nuovoAllegato.filename = this.nuovoAllegato.filename.replace(
                type,
                type.toLowerCase()
              );

              //spezza il nome del file da visualizzare se è troppo lungo e senza spazi. Non copre tutti i casi, ma dovrebbe bastare
              if (
                this.nuovoAllegato.filename.length > 30 &&
                this.nuovoAllegato.filename.split(" ").length == 1
              ) {
                if (this.nuovoAllegato.filename.length <= 60) {
                  this.nomeAllegatoTmp =
                    this.nuovoAllegato.filename.slice(0, 30) +
                    " " +
                    this.nuovoAllegato.filename.slice(30);
                } else
                  this.nomeAllegatoTmp =
                    this.nuovoAllegato.filename.slice(0, 30) +
                    " " +
                    this.nuovoAllegato.filename.slice(30, 60) +
                    " " +
                    this.nuovoAllegato.filename.slice(60);
              }

              if (
                this.allowedFilesType.filter((x) => x == type.toLowerCase())
                  .length == 0
              ) {
                this.typeAlert = true;
                this.nuovoAllegato.filename = null;
                this.allowedFilesTypeMessage =
                  "I documenti caricati non sono idonei all'archiviazione su Doqui ACTA. Si prega di ricontrollare l'estensione dei file selezionati";
                this.nuovoAllegato = new SalvaAllegatoRequest();
                this.nomeAllegatoTmp = null;
              } else {
                this.typeAlert = false;
                //  if (!this.allegatiMultipli) {
                this.resetMessageTop();
                this.nomeAllegatoTmp = this.nuovoAllegato.filename;
                /*  if (this.isCreaOrdinanza)
                      this.onNewFile.emit(this.nuovoAllegato);*/
                //} else {
                let allegato: AllegatoMultipleFieldVO =
                  new AllegatoMultipleFieldVO();
                allegato.file = files[key];
                allegato.filename = this.nuovoAllegato.filename;
                //  allegato.idTipoAllegato = this.nuovoAllegato.idTipoAllegato;
                allegato.master = true;

                /*  this.nuovoAllegatoMultiple.allegati =
                      new Array<AllegatoMultipleFieldVO>();
                    this.nuovoAllegatoMultiple.allegati.push(allegato);
                    this.nomeAllegatoTmp = allegato.filename;

                    if (!this.flagAllegatoMaster) {
                      this.resetMessageTop();
                    }
                  }*/
              }

              this.refactorObject(this.index);
            }
          }
        }
        //console.log('onFilesAdded',this.nuovoAllegato, this.tableForm);

        this.upload.uploader.nativeElement.value = "";
      },
    },
  };
}
