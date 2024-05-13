import {
  Component,
  OnInit,  OnDestroy,
  Input,  Output,
  ViewChild,
  EventEmitter,} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { UserService } from "../../../core/services/user.service";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { NgForm } from "@angular/forms";
import { SharedTemplateIntestazioneComponent } from "../../../shared-template/components/shared-template-intestazione/shared-template-intestazione.component";

@Component({
  selector: "template-03-lettera-accompagnamento",
  templateUrl: "./template-03-lettera-accompagnamento.component.html",
})
export class Template03LetteraAccompagnamentoComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};

  public funzionario: string;

  public isAnteprima: boolean;
  public isStampa: boolean;

  @Input()  data: DatiTemplateVO;

  @Output()  formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
  formIntestazioneValid: boolean;

  public datiCompilati: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();

  @ViewChild("formTemplate")  private formTemplate: NgForm;
  @ViewChild(SharedTemplateIntestazioneComponent)
  intestazione: SharedTemplateIntestazioneComponent;
  public infoEnteArray: string[]
  constructor(
    private userService: UserService,
    private logger: LoggerService,
  ) {}

  ngOnInit(): void {
    this.logger.init(Template03LetteraAccompagnamentoComponent.name);
    this.subscribers.userProfile = this.userService.profilo$.subscribe(
      (user) => {
        if (user != null) {          this.funzionario = user.nome + " " + user.cognome;        }
      }
    );
    this.subscribers.form = this.formTemplate.valueChanges.subscribe((data) => {
      this.formValid.next(        this.formTemplate.valid && this.formIntestazioneValid      );
    });
    this.infoEnteArray= this.data.sedeEnte.split(";");
    this.datiCompilati.sedeEnteRiga1 =  this.infoEnteArray[0] ? this.infoEnteArray[0] : ' '
    this.datiCompilati.sedeEnteRiga2 =  this.infoEnteArray[1] ? this.infoEnteArray[1] : ' '
    this.datiCompilati.sedeEnteRiga3 =  this.infoEnteArray[2] ? this.infoEnteArray[2] : ' '
    this.datiCompilati.sedeEnteRiga4 =  this.infoEnteArray[3] ? this.infoEnteArray[3] : ' '
    this.datiCompilati.sedeEnteRiga5 =  this.infoEnteArray[4] ? this.infoEnteArray[4] : ' '
    this.popolaCampi();
  }

  setAnteprima(flag: boolean) {
    this.isAnteprima = flag;
    this.intestazione.setAnteprima(flag);
  }

  popolaCampi() {
    this.datiCompilati.testoLibero = this.data.testoLibero;
    this.datiCompilati.intestazioneConoscenza = this.data.intestazioneConoscenza;
    this.datiCompilati.emailOrgano = this.data.emailOrgano;
    this.datiCompilati.email = this.data.email;
  }



  getDatiCompilati(): DatiTemplateCompilatiVO {
    return this.datiCompilati;
  }
  setStampa(flag: boolean) {
    this.isStampa = flag;
    this.intestazione.setAnteprima(flag);
  }

  setDatiCompilati(dati: DatiTemplateCompilatiVO) {
    this.datiCompilati = dati;
  }

  ngOnDestroy(): void {    this.logger.destroy(Template03LetteraAccompagnamentoComponent.name);  }

  setFormIntestazioneValid(event: boolean) {
    this.formIntestazioneValid = event;
    this.formValid.next(this.formTemplate.valid && this.formIntestazioneValid);
  }
}
