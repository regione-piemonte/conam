import { AfterViewInit, Component, EventEmitter, Input, Output } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { Config } from "../../../../shared/module/datatable/classes/config";
//x bootstrap
declare var jquery: any;
declare var $: any;

@Component({
  selector: "app-lista-ordinanze",
  templateUrl: "./lista-ordinanze.html",
})
export class ListaOrdinanzeComponent implements AfterViewInit{
  constructor() {}
  config: Config = {
    columns: [
      {        displayName: "Numero Determinazione",        columnName: "numDeterminazione",      },
      {        displayName: "Tipo Ordinanza",        columnName: "tipo.denominazione",      },
      {        displayName: "Stato",        columnName: "stato.denominazione",      },
    ],
  };
  @Input()  ordinanze: Array<any>;
  loaded: boolean;
  @Input() testo: string;
  @Input() titolo: string;
  @Input() listaOrdinanze: any;
  @Input() buttonConfirmText: string;
  @Input() buttonAnnullaText: string;
  @Input() subMessages: Array<string>;
  @Input() subLinks: Array<any>;
  @Input() hideButton: boolean;
  @Input() id: string = "myModal";
  @Output()  clickLinkEvent: EventEmitter<any> = new EventEmitter<any>();

  ngOnInit(): void {
    if (!this.titolo) {
      this.titolo = "titolo non definito";
    }
    if (!this.buttonAnnullaText) {
      this.buttonAnnullaText = "No";
    }
    if (!this.buttonConfirmText) {
      this.buttonConfirmText = "Si";
    }
  }
  ngAfterViewInit(){

  }
  // APRE
  public open() {
    $("#" + this.id).modal("show");
    this.loaded = true;
  }
  // CHIUDE
  public close() {
    $("#" + this.id).modal("hide");
    this.loaded=false;
  }

  // SUBJECT x SALVATAGGIO - DA  fare SUBSCRIBE
  private saveSubject = new Subject<Boolean>();
  public salvaAction: Observable<Boolean> = this.saveSubject.asObservable();

  private saveS(): void {
    this.close();
    this.saveSubject.next(true);
  }

  // SUBJECT x SALVATAGGIO - DA FARE SUBSCRIBE
  private closeSubject = new Subject<Boolean>();
  public closeAction: Observable<Boolean> = this.closeSubject.asObservable();

  private closeS(): void {
    this.close();
    this.closeSubject.next(true);
  }

  private XSubject = new Subject<Boolean>();
  public XAction: Observable<Boolean> = this.XSubject.asObservable();

  private closeX(): void {
    this.close();
    this.XSubject.next(true);
  }

  public clickLink(l: any): void {    this.clickLinkEvent.emit(l);  }
}