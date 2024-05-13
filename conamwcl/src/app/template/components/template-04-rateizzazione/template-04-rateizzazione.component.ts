import {  Component,
  OnInit,  Input,
  Output, EventEmitter,  ViewChild,} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { NgForm } from "@angular/forms";
import { SharedTemplateIntestazioneComponent } from "../../../shared-template/components/shared-template-intestazione/shared-template-intestazione.component";

@Component({
  selector: "template-04-rateizzazione",
  templateUrl: "./template-04-rateizzazione.component.html",
})
export class Template04RateizzazioneComponent implements OnInit {
  public subscribers: any = {};

  //gestione anteprima
  public isAnteprima: boolean;
  public isStampa: boolean;
  public infoEnteArray: string[]
  //dati precompilati
  @Input()  data: DatiTemplateVO;

  //output
  @Output()  formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
  formIntestazioneValid: boolean;

  public datiCompilati: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();

  //form view child
  @ViewChild("formTemplate")  private formTemplate: NgForm;
  @ViewChild(SharedTemplateIntestazioneComponent)  intestazione: SharedTemplateIntestazioneComponent;

  constructor(
    private logger: LoggerService
  ) {}

  ngOnInit(): void {
    this.logger.init(Template04RateizzazioneComponent.name);
    this.subscribers.form = this.formTemplate.valueChanges.subscribe((data) => {
      this.formValid.next(        this.formTemplate.valid && this.formIntestazioneValid      );
    });
    // La richiesta   Bertinetti riguarda solo l'ordinanza
    this.data.mailSettoreTributi = null;
    this.infoEnteArray= this.data.sedeEnte.split(";");
    this.datiCompilati.sedeEnteRiga1 =  this.infoEnteArray[0] ? this.infoEnteArray[0] : ' '
    this.datiCompilati.sedeEnteRiga2 =  this.infoEnteArray[1] ? this.infoEnteArray[1] : ' '
    this.datiCompilati.sedeEnteRiga3 =  this.infoEnteArray[2] ? this.infoEnteArray[2] : ' '
    this.datiCompilati.sedeEnteRiga4 =  this.infoEnteArray[3] ? this.infoEnteArray[3] : ' '
    this.datiCompilati.sedeEnteRiga5 =  this.infoEnteArray[4] ? this.infoEnteArray[4] : ' '
  }

  setAnteprima(flag: boolean) {
    this.isAnteprima = flag;
    this.intestazione.setAnteprima(flag);
  }

  setStampa(flag: boolean) {
    this.isStampa = flag;
    this.intestazione.setAnteprima(flag);
  }

  setDatiCompilati(dati: DatiTemplateCompilatiVO) {    this.datiCompilati = dati;  }

  getDatiCompilati(): DatiTemplateCompilatiVO {    return this.datiCompilati;
  }

  setFormIntestazioneValid(event: boolean) {
    this.formIntestazioneValid = event;
    this.formValid.next(this.formTemplate.valid && this.formIntestazioneValid);
  }
}
