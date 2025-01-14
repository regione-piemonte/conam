import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  ViewChild,
} from "@angular/core";
import { NotaVO } from "../../../../../commons/vo/verbale/nota-vo";
import { SelectVO } from "../../../../../commons/vo/select-vo";
import { Observable, Subject } from "rxjs";
import { SharedVerbaleService } from "../../../../service/shared-verbale.service";
import { UtilSubscribersService } from "../../../../../core/services/util-subscribers-service";
import { LoggerService } from "../../../../../core/services/logger/logger.service";
import { SharedDialogComponent } from "../../../../../shared/component/shared-dialog/shared-dialog.component";
import { concatMap } from "rxjs/operators";
import { ExceptionVO } from "../../../../../commons/vo/exceptionVO";
import { MessageVO } from "../../../../../commons/vo/messageVO";
//Con bootstap va fatto cosi
declare var jquery: any;
declare var $: any;

@Component({
  selector: "app-note-detail",
  templateUrl: "./note-detail.component.html",
  styleUrls: ["./note-detail.component.scss"],
})
export class NoteDetailComponent implements OnInit, OnDestroy {
  ///Modal features
  @Input() testo: string;
  @Input() titolo: string;
  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;
  public subscribers: any = {};

  @Input() subLinks: Array<any>;
  @Input() hideButton: boolean;
  @Input() id: string = "noteModal";
  @Output()
  clickLinkEvent: EventEmitter<any> = new EventEmitter<any>();
  //////
  loaded = false;
  modalLoaded: boolean = false;
  nota: NotaVO;

  public showMessageTop: boolean;
  public typeMessageTop: string;
  public messageTop: string;
  private intervalIdS: number = 0;
  @Input() idVerbale: number;
  ambitoList: SelectVO[];
  constructor(
    private utilSubscribersService: UtilSubscribersService,
    private logger: LoggerService,

    public service: SharedVerbaleService
  ) {}

  ngOnInit(): void {
    if (!this.nota || this.nota === undefined) {
      this.nota = new NotaVO();
   
    }
    if (!this.titolo) this.titolo = "titolo non definito";
    if (!this.buttonAnnullaText) this.buttonAnnullaText = "No";
    if (!this.buttonConfirmText) this.buttonConfirmText = "Si";
    this.service.ambitiList().subscribe((data) => {
     
      this.ambitoList = data;
     
      this.loaded = true;
    
    });
    this.manageDatePicker(null, 1);
  }
  
  
  //APRE
  public open(nota?: NotaVO) {
    this.subMessages = new Array<string>();
    this.loaded = false;
    if (nota === null || !nota) {
      this.nota = new NotaVO();
      this.loaded = true;
      //SET DEFAULT VALUE
      if(this.nota.ambito === null ||this.nota.ambito === undefined){
        let itemVO= this.ambitoList.find((el)=> el.id===5)
        this.nota.ambito = itemVO
      }
    } else {
      this.nota = nota;
      this.loaded = true;
    }
    $("#" + this.id).modal("show");
  }
  //CHIUDE
  public close(event: any) {
    $("#" + this.id).modal("hide");
    this.clickLink(event);
  }
  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  toDeleteNote() {
    this.generaMessaggio(this.nota);
    this.buttonAnnullaText = "Annulla";
    this.buttonConfirmText = "Conferma";

    //mostro un messaggio
    //this.closeX(this.idVerbale);

    this.sharedDialog.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
        this.service
          .deleteNote(this.nota, this.idVerbale)

          .subscribe(
            (data) => {
              this.loaded = true;
              this.closeX(this.idVerbale);
            },
            (err) => {
              if (err instanceof ExceptionVO) {
                //this.manageMessage(err.type, err.message);
              }
              this.logger.error(
                "Errore nell'eliminazione della nota " + this.nota.idNota
              );
            }
          );
      },
      (err) => {}
    );

    //premo "Annulla"

    this.subscribers.close = this.sharedDialog.closeAction.subscribe(
      (data) => {
        this.open(this.nota);
        this.sharedDialog.close();
        this.subMessages = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  generaMessaggio(el: NotaVO) {
    this.subMessages = new Array<string>();

    let incipit: string = "Si intende eliminare la nota del fascicolo?";

    this.subMessages.push(incipit);
    this.subMessages.push(el.oggetto);
  }
  manageMessage(mess: ExceptionVO | MessageVO) {
    this.typeMessageTop = mess.type;
    this.messageTop = mess.message;
    this.timerShowMessageTop();
  }
  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 20; //this.configService.getTimeoutMessagge();
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }
  saveNota() {
    if (this.nota.idNota) {
      this.service.updateNote(this.nota, this.idVerbale).subscribe(
        (data) => {
          this.closeX(this.idVerbale);
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err);
            this.closeX(this.idVerbale);
          }
          this.logger.info("Errore nel recupero dei tipi di allegato");
        }
      );
      //call PUT
    } else {
      //call POST
      this.service.saveNote(this.nota, this.idVerbale).subscribe(
        (data) => {
          this.closeX(this.idVerbale);
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err);
            this.closeX(this.idVerbale);
          }
          this.logger.info("Errore nel recupero dei tipi di allegato");
        }
      );
    }
  }

  goBack() {
    this.closeX(this.idVerbale);
  }

  /////
  //SUBJECT PER IL SALVATAGGIO -DA  EFFETTUARE IL SUBSCRIBE
  private saveSubject = new Subject<Boolean>();
  public salvaAction: Observable<Boolean> = this.saveSubject.asObservable();

  private saveS(event): void {
    this.close(event);
    this.saveSubject.next(true);
  }

  //SUBJECT PER IL SALVATAGGIO -DA  EFFETTUARE IL SUBSCRIBE
  private closeSubject = new Subject<Boolean>();
  public closeAction: Observable<Boolean> = this.closeSubject.asObservable();

  private closeS(event): void {
    this.close(event);
    this.closeSubject.next(true);
  }

  private XSubject = new Subject<Boolean>();
  public XAction: Observable<Boolean> = this.XSubject.asObservable();

  private closeX(event): void {
    this.close(event);
    this.XSubject.next(true);
  }
  manageDatePicker(event: any, i: number) {
	  
	  console.log('manage');
    var str: string = "#datetimepicker" + i.toString();
    //if ($(str).length) {
      $(str).datetimepicker({
        format: "DD/MM/YYYY, HH:mm",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
      });
    //}
    console.log(event);
    if (event != null) {
      switch (i) {
        case 1:
          this.nota.data = event.srcElement.value;
          console.log(event);
          break;
      }
    }
  }
  public clickLink(l: any): void {
    this.clickLinkEvent.emit(l);
  }
  ngOnDestroy(): void {
    this.logger.destroy(NoteDetailComponent.name);
  }
}
