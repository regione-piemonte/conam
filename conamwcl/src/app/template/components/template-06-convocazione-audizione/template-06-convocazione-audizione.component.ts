import {  Component,
  OnInit,
  Input,  Output,
  ViewChild,
  EventEmitter,} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { UserService } from "../../../core/services/user.service";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { NgForm } from "@angular/forms";
import { SharedTemplateIntestazioneComponent } from "../../../shared-template/components/shared-template-intestazione/shared-template-intestazione.component";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
declare var $: any;

@Component({
  selector: "template-06-convocazione-audizione",
  templateUrl: "./template-06-convocazione-audizione.component.html",
  styleUrls: ["./template-06-convocazione-audizione.component.scss"],
})
export class Template06ConvocazioneAudizioneComponent implements OnInit {
  //Variabili per il singolare/plurale
  public signoriaVostra: string;
  public comparire: string;
  public convocare: string;

  public dirigenteLettera: string
  public dirigente: string; //user
  public sedeEnte: string;
  public subscribers: any = {};

  public funzionario: string;

  public isAnteprima: boolean;
  public infoEnteArray: string[]
  public sedeEnteArray: string[]
  public isStampa: boolean;
  @Input()  data: DatiTemplateVO;

  //public destinatariSoggetti: { soggRiga1: string, soggRiga2: string, soggRiga3: string, soggRiga4: string }[] = [];
  //public destinatariAggiuntivi: string = '';

  denominazioneList: Array<String> = new Array<String>();
  @Output()  formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
  formIntestazioneValid: boolean;
  public datiCompilati: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
  @ViewChild("formTemplate")  private formTemplate: NgForm;
  @ViewChild(SharedTemplateIntestazioneComponent)  intestazione: SharedTemplateIntestazioneComponent;

  constructor(
    private userService: UserService,
    private logger: LoggerService
  ) {}

  ngOnInit(): void {
    this.logger.init(Template06ConvocazioneAudizioneComponent.name);
    this.subscribers.userProfile = this.userService.profilo$.subscribe(
      (user) => {
        if (user != null) {          this.funzionario = user.nome + " " + user.cognome;
        }
      }
    );
    this.subscribers.form = this.formTemplate.valueChanges.subscribe((data) => {
      this.formValid.next(        this.formTemplate.valid && this.formIntestazioneValid      );
    });

    this.datiCompilati.oggetto =      "Legge. 24.11.1981, n. 689, art. 18 - Richiesta di audizione a seguito di processi verbali n. ";
    this.gestisciSingolariPlurali();

    this.dirigente = this.data.dirigente;
    this.dirigenteLettera = this.data.dirigenteLettera
    this.sedeEnte = this.data.sedeEnteTesto
    this.sedeEnteArray= this.data.sedeEnteTesto.split(";");
    this.infoEnteArray= this.data.sedeEnte.split(";");
    this.datiCompilati.sedeEnteRiga1 =  this.infoEnteArray[0] ? this.infoEnteArray[0] : ' '
    this.datiCompilati.sedeEnteRiga2 =  this.infoEnteArray[1] ? this.infoEnteArray[1] : ' '
    this.datiCompilati.sedeEnteRiga3 =  this.infoEnteArray[2] ? this.infoEnteArray[2] : ' '
    this.datiCompilati.sedeEnteRiga4 =  this.infoEnteArray[3] ? this.infoEnteArray[3] : ' '
    this.datiCompilati.sedeEnteRiga5 =  this.infoEnteArray[4] ? this.infoEnteArray[4] : ' '
    this.data.mailSettoreTributi = null;

    this.datiCompilati.destinatariAggiuntivi = "";
    this.datiCompilati.destinatariSoggetti = [];

    this.popolaDestinatariSoggetti(this.data)
  }

  popolaDestinatariSoggetti(data: DatiTemplateVO) {
    if (data!=null && data.listaSoggetti!=null){

      let propDenominazione: string = "";
      let propIndirizzo: string = "";
      let propCapCittaProvincia: string = "";
      let propTestoLibero: string = "";
      for (let sogg of data.listaSoggetti) {
        if (sogg){
          if (sogg.personaFisica){
            propDenominazione = sogg.nome + " " + sogg.cognome;
          }else{
            propDenominazione = sogg.ragioneSociale;
          }
          if (sogg.indirizzoResidenza != null){
            propIndirizzo = sogg.indirizzoResidenza;
            if (sogg.civicoResidenza!=null){
              propIndirizzo += ", n° " + sogg.civicoResidenza;
            }
          }
          if(sogg.comuneResidenza !=null && sogg.provinciaResidenza !=null){
            propCapCittaProvincia = sogg.cap + " " + sogg.comuneResidenza.denominazione + " (" + sogg.provinciaResidenza.sigla + ")";
          }
          //this.destinatariSoggetti.push({
          this.datiCompilati.destinatariSoggetti.push({
            soggRiga1: propDenominazione,
            soggRiga2: propIndirizzo,
            soggRiga3: propCapCittaProvincia,
            soggRiga4: propTestoLibero
          });
          propDenominazione = "";
          propIndirizzo = "";
          propCapCittaProvincia = "";
          propTestoLibero = "";
        }
      }
    }
  }

  setAnteprima(flag: boolean) {
    this.isAnteprima = flag;
    this.intestazione.setAnteprima(flag);
  }

  setStampa(flag: boolean) {
    this.isStampa = flag;
    this.intestazione.setAnteprima(flag);
  }


  setFormIntestazioneValid(event: boolean) {
    this.formIntestazioneValid = event;
    this.formValid.next(this.formTemplate.valid && this.formIntestazioneValid);
  }
  getDatiCompilati(): DatiTemplateCompilatiVO {    return this.datiCompilati;  }
  setDatiCompilati(dati: DatiTemplateCompilatiVO) {    this.datiCompilati = dati;  }
  ngAfterViewChecked() {
    this.manageDatePicker(null, 1, "DD");
    this.manageDatePicker(null, 2, "MM");
    this.manageDatePicker(null, 3, "YYYY");
    this.manageDatePicker(null, 4, "HH:mm");
  }

  manageDatePicker(event: any, i: number, formatDate: string) {
    var str: string = "#datetimepicker" + i.toString();
    if ($(str).length && formatDate != null) {
      $(str).datetimepicker({        format: formatDate,      });
    }
    if (event != null) {
      switch (i) {
        case 1:
          this.datiCompilati.giorno = event.srcElement.value;
          break;
        case 2:
          this.datiCompilati.mese = event.srcElement.value;
          break;
        case 3:
          this.datiCompilati.anno = event.srcElement.value;
          break;
        case 4:
          this.datiCompilati.oraInizio = event.srcElement.value;
          break;
      }
    }
  }

  comparizione: string = "";
  gestisciSingolariPlurali() {
    if (this.data.listaSoggetti != null) {
      let personeFisiche: Array<SoggettoVO> = this.data.listaSoggetti.filter(
        (x) => x.personaFisica
      );
      let personeGiuridiche: Array<SoggettoVO> = this.data.listaSoggetti.filter(
        (x) => !x.personaFisica
      );
      let size: number = this.data.listaSoggetti.length;
      let isPersonaFisica: Boolean =
        personeFisiche.length == 1 && personeFisiche.length == size;
      let isPersonaGiuridica: Boolean =
        personeGiuridiche.length == 1 && personeFisiche.length == size;

      if (this.data.listaSoggetti.length <= 1) {
        this.signoriaVostra = "la S.V.";
        this.convocare = "è convocata";
        this.comparire = "comparisse";
      } else {
        this.signoriaVostra = "le SS.VV.";
        this.convocare = "sono convocate";
        this.comparire = "comparissero";
      }
    }
  }
}
