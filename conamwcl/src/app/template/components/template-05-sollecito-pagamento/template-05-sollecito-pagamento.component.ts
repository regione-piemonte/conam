import {  Component,  OnInit,  OnDestroy,  ViewChild,  Output,  Input,  EventEmitter,} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { NgForm } from "@angular/forms";
import { UserService } from "../../../core/services/user.service";
import { SharedTemplateIntestazioneComponent } from "../../../shared-template/components/shared-template-intestazione/shared-template-intestazione.component";

@Component({
  selector: "template-05-sollecito-pagamento",
  templateUrl: "./template-05-sollecito-pagamento.component.html",
})
export class Template05SollecitoPagamentoComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};

  public isStampa: boolean;
  public isAnteprima: boolean;

  @Input()  data: DatiTemplateVO;
  funzionario: string;
  public infoEnteArray: string[]
  @Output()  formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
  formIntestazioneValid: boolean;

  public datiCompilati: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();

  @ViewChild("formTemplate")  private formTemplate: NgForm;
  @ViewChild(SharedTemplateIntestazioneComponent)  intestazione: SharedTemplateIntestazioneComponent;

  constructor(
    private userService: UserService,
    private logger: LoggerService,
  ) {}

  ngOnInit(): void {
    this.logger.init(Template05SollecitoPagamentoComponent.name);
    this.subscribers.userProfile = this.userService.profilo$.subscribe(
      (user) => {
        if (user != null) {          this.funzionario = user.nome + " " + user.cognome;        }
      }
    );
    this.subscribers.form = this.formTemplate.valueChanges.subscribe((data) => {
      this.formValid.next(        this.formTemplate.valid && this.formIntestazioneValid      );
    });
    this.data.mailSettoreTributi = null;
    this.infoEnteArray= this.data.sedeEnte.split(";");
    this.datiCompilati.sedeEnteRiga1 =  this.infoEnteArray[0] ? this.infoEnteArray[0] : ' '
    this.datiCompilati.sedeEnteRiga2 =  this.infoEnteArray[1] ? this.infoEnteArray[1] : ' '
    this.datiCompilati.sedeEnteRiga3 =  this.infoEnteArray[2] ? this.infoEnteArray[2] : ' '
    this.datiCompilati.sedeEnteRiga4 =  this.infoEnteArray[3] ? this.infoEnteArray[3] : ' '
    this.datiCompilati.sedeEnteRiga5 =  this.infoEnteArray[4] ? this.infoEnteArray[4] : ' '
  }

  setStampa(flag: boolean) {
    this.isStampa = flag;
    this.intestazione.setAnteprima(flag);
  }

  setAnteprima(flag: boolean) {
    this.isAnteprima = flag;
    this.intestazione.setAnteprima(flag);
  }

  getDatiCompilati(): DatiTemplateCompilatiVO {    return this.datiCompilati;  }

  setDatiCompilati(dati: DatiTemplateCompilatiVO) {    this.datiCompilati = dati;  }

  setFormIntestazioneValid(event: boolean) {
    this.formIntestazioneValid = event;
    this.formValid.next(this.formTemplate.valid && this.formIntestazioneValid);
  }

  ngOnDestroy(): void {    this.logger.destroy(Template05SollecitoPagamentoComponent.name);
    }
}
