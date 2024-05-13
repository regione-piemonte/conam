import {
  AfterViewInit,
  Component,
  Input,
  OnInit,
  ViewChild,
} from "@angular/core";
import { NoteDetailComponent } from "../note-detail/note-detail.component";
import { NotaVO } from "../../../../../commons/vo/verbale/nota-vo";
import { Config } from "../../../../../shared/module/datatable/classes/config";
import { SharedVerbaleService } from "../../../../service/shared-verbale.service";
import { SharedDialogComponent } from "../../../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../../../core/services/util-subscribers-service";
import { ExceptionVO } from "../../../../../commons/vo/exceptionVO";
import { LoggerService } from "../../../../../core/services/logger/logger.service";
import { concatMap } from "rxjs/operators";
@Component({
  selector: "app-notes-list",
  templateUrl: "./notes-list.component.html",
  styleUrls: ["./notes-list.component.scss"],
})
export class NotesListComponent implements OnInit {
  @Input() noteList: Array<NotaVO> | NotaVO[];
  @Input() idVerbale: number;
  @Input() configNote: any;
  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;
  public subscribers: any = {};
  @ViewChild(NoteDetailComponent) noteDetail: NoteDetailComponent;
  modalLoaded = true;
  loaded: boolean = false;
  constructor(
    private service: SharedVerbaleService,
    private utilSubscribersService: UtilSubscribersService,
    private logger: LoggerService
  ) {}

  ngOnInit(): void {
    console.log(this.noteList, this.configNote);
    this.loaded = true;
  }

  toDetail() {



    this.noteDetail.open(null);
  }

  toDetailNote(el) {


    this.noteDetail.open(el);
  }
  checkReload() {
this.loaded= false
this.modalLoaded= false
    this.service.riepilogoVerbale(this.idVerbale).subscribe((data) => {
      this.noteList = data.verbale.note;
     this.loaded=true;
     this.modalLoaded=true
    });
    //reload the table with the list
  }

  toDeleteNote(el) {
    this.generaMessaggio(el);
    this.buttonAnnullaText = "Annulla";
    this.buttonConfirmText = "Conferma";

    //mostro un messaggio
    this.sharedDialog.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
        this.loaded=false
        this.service
          .deleteNote(el, this.idVerbale)
          .pipe(
            concatMap((data) => this.service.riepilogoVerbale(this.idVerbale))
          )
          .subscribe(
            (data) => {
              this.loaded=true
              this.noteList = data.verbale.note;
            },
            (err) => {
              if (err instanceof ExceptionVO) {
                //this.manageMessage(err.type, err.message);
              }
              this.logger.error("Errore nell'eliminazione della nota " + el.id);
              this.loaded = true;
            }
          );
      },
      (err) => {}
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialog.closeAction.subscribe(
      (data) => {
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
}
