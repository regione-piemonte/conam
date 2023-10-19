import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  ViewChild,
  EventEmitter,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { UserService } from "../../../core/services/user.service";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { NgForm } from "@angular/forms";
import { SharedTemplateIntestazioneComponent } from "../../../shared-template/components/shared-template-intestazione/shared-template-intestazione.component";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";

declare var $: any;

@Component({
  selector: "template-02-verbale-audizione",
  templateUrl: "./template-02-verbale-audizione.component.html",
  styleUrls: ["./template-02-verbale-audizione.component.scss"],
})
export class Template02VerbaleAudizioneComponent implements OnInit {
  public subscribers: any = {};

  public funzionario: string;

  //gestione anteprima
  public isAnteprima: boolean;
  public isStampa: boolean;

  //dati precompilati
  @Input()
  data: DatiTemplateVO;
  public infoEnteArray: string[] 
  denominazioneList: Array<String> = new Array<String>();
  //output
  @Output()
  formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
  formIntestazioneValid: boolean;

  public datiCompilati: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();

  //form view child
  @ViewChild("formTemplate")
  private formTemplate: NgForm;
  @ViewChild(SharedTemplateIntestazioneComponent)
  intestazione: SharedTemplateIntestazioneComponent;

  constructor(
    private logger: LoggerService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.logger.init(Template02VerbaleAudizioneComponent.name);
    this.subscribers.userProfile = this.userService.profilo$.subscribe(
      (user) => {
        if (user != null) {
          this.funzionario = user.nome + " " + user.cognome;
        }
      }
    );
    this.subscribers.form = this.formTemplate.valueChanges.subscribe((data) => {
      this.formValid.next(
        this.formTemplate.valid && this.formIntestazioneValid
      );
    });
    this.infoEnteArray= this.data.sedeEnte.split(";");
    this.datiCompilati.sedeEnteRiga1 =  this.infoEnteArray[0] ? this.infoEnteArray[0] : ' '
    this.datiCompilati.sedeEnteRiga2 =  this.infoEnteArray[1] ? this.infoEnteArray[1] : ' '
    this.datiCompilati.sedeEnteRiga3 =  this.infoEnteArray[2] ? this.infoEnteArray[2] : ' '
    this.datiCompilati.sedeEnteRiga4 =  this.infoEnteArray[3] ? this.infoEnteArray[3] : ' '
    this.datiCompilati.sedeEnteRiga5 =  this.infoEnteArray[4] ? this.infoEnteArray[4] : ' '
    this.setDenominazione();
    this.gestisciSingolariPlurali();
  }

  setDenominazione() {
    let size = this.data.listaSoggetti.length;
    for (let s of this.data.listaSoggetti) {
      let nome = s.personaFisica ? s.nome + " " + s.cognome : s.ragioneSociale;
      if (size != this.denominazioneList.length + 1) nome = nome + ", ";
      this.denominazioneList.push(nome);
   
     
    }
    this.datiCompilati.dichiarante = this.denominazioneList.join('');
    if(this.denominazioneList.length===1){
      this.datiCompilati.testoDichiarante= 'Il Dichiarante'
    }else{
      this.datiCompilati.testoDichiarante= 'I Dichiaranti'
    
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

  getDatiCompilati(): DatiTemplateCompilatiVO {
    
    return this.datiCompilati;
  }

  setDatiCompilati(dati: DatiTemplateCompilatiVO) {
    this.datiCompilati = dati;
  }

  setFormIntestazioneValid(event: boolean) {
    this.formIntestazioneValid = event;
    this.formValid.next(this.formTemplate.valid && this.formIntestazioneValid);
  }

  ngAfterViewChecked() {
    this.manageDatePicker(null, 1, "DD");
    this.manageDatePicker(null, 2, "MM");
    this.manageDatePicker(null, 3, "YYYY");
    this.manageDatePicker(null, 4, "HH:mm");
  }

  manageDatePicker(event: any, i: number, formatDate: string) {
    var str: string = "#datetimepicker" + i.toString();
    if ($(str).length && formatDate != null) {
      $(str).datetimepicker({
        format: formatDate,
      });
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

      if (isPersonaFisica)
        this.comparizione =
          this.data.listaSoggetti[0].sesso == "M"
            ? " è comparso il signor "
            : " è comparsa la signora ";
      else if (isPersonaGiuridica) this.comparizione = " è comparsa l'azienda ";
      else if (personeFisiche.length == size)
        this.comparizione = "sono comparsi i signori";
      else if (personeGiuridiche.length == size)
        this.comparizione = "sono comparse le aziende";
      else this.comparizione = "sono comparsi i soggetti";
    }
    let assistito= this.denominazioneList.length==1 ? 'assistito da' : 'assistiti da'
    this.datiCompilati.testoLibero = this.comparizione + ' ' +  this.denominazioneList.join('') + ' ' + assistito 
  }

  /*
    ngOnDestroy(): void {
        this.logger.destroy(Template02VerbaleAudizioneComponent.name);
    }*/
}
