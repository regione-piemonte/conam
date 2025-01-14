import {  Component,  OnInit,  OnDestroy,
  ViewChild,} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute } from "@angular/router";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { Template04RateizzazioneComponent } from "../../../template/components/template-04-rateizzazione/template-04-rateizzazione.component";
import { TemplateService } from "../../../template/services/template.service";
import { DatiTemplateRequest } from "../../../commons/request/template/dati-template-request";
import { Constants } from "../../../commons/class/constants";
import { Location } from "@angular/common";
import { SharedPagamentiService } from "../../../shared-pagamenti/services/shared-pagamenti.service";
import { IsCreatedVO } from "../../../commons/vo/isCreated-vo";
import { saveAs } from "file-saver";

@Component({
  selector: "pagamenti-template",
  templateUrl: "./pagamenti-template.component.html",
  styleUrls: ["./pagamenti-template.component.scss"],
})
export class PagamentiTemplateComponent implements OnInit, OnDestroy {
  @ViewChild(Template04RateizzazioneComponent)
  template: Template04RateizzazioneComponent;

  public subscribers: any = {};

  public isAnteprima: boolean = false;
  public loaded: boolean;
  public isPrinted: boolean = false;
  public scrollEnable: boolean;


  public datiTemplateModel: DatiTemplateVO;
  public idPiano: number;
  public datiCompilati: DatiTemplateCompilatiVO;

  public url: string;

  public typeMessageTop: String;
  public messageTop: String;
  public showMessageTop: boolean;
  private intervalIdS: number = 0;


  visualizzaAnteprimaValidIntestazione: boolean;
  public datiTemplateModelStampa: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
  visualizzaAnteprimaValidTemplate: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private logger: LoggerService,
    private location: Location,
    private templateService: TemplateService,
    private sharedPagamentiService: SharedPagamentiService
  )
  {}

  ngOnInit(): void {
    this.logger.init(PagamentiTemplateComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idPiano = +params["id"];

      let request: DatiTemplateRequest = new DatiTemplateRequest();
      request.codiceTemplate = Constants.TEMPLATE_RATEIZZAZIONI;
      request.idPiano = this.idPiano;
      this.subscribers.getTemplate = this.templateService
        .getDatiTemplate(request)
        .subscribe(
          (data) => {
            this.datiTemplateModel = data;
            this.scrollEnable = true;
            this.loaded = true;
          },
          (err) => {
            this.logger.error("Errore durante il recupero dei dati");
            this.loaded = true;
          }
        );
    });
  }

  indietro() {    this.location.back();  }

  visualizzaAnteprima() {
    this.isAnteprima = true;
    this.datiTemplateModelStampa = this.template.getDatiCompilati();
    this.template.setAnteprima(true);
    this.scrollEnable = true;
  }

  proseguiModifica() {
    this.isAnteprima = false;
    this.template.setAnteprima(false);
    this.isStampa = false;
    this.scrollEnable = true;
    this.template.setStampa(false);
  }
  public isStampa: boolean = false;
  public isStampaProtocollata: boolean = false;
  public loadedScarica: boolean = false;

  stampaPDF() {
    this.isStampa = true;
    this.isPrinted = true;
    this.template.setStampa(true);
    this.template.setDatiCompilati(this.datiTemplateModelStampa);
    let tmp: DatiTemplateCompilatiVO = this.template.getDatiCompilati();
    this.resetMessageTop();
    if (this.checkDatiTemplate(tmp)) {
      let request: DatiTemplateRequest = new DatiTemplateRequest();
      request.codiceTemplate = Constants.TEMPLATE_RATEIZZAZIONI;
      request.idPiano = this.idPiano;
      request.datiTemplateCompilatiVO = tmp;
      let isCreatedVO: IsCreatedVO = new IsCreatedVO();
      isCreatedVO.id = this.idPiano;
      this.subscribers.isLetteraSaved = this.sharedPagamentiService
        .isLetteraPianoSaved(isCreatedVO)
        .subscribe((data) => {
          if (!data.isCreated) {
            this.subscribers.stampa = this.templateService
              .stampaTemplate(request)
              .subscribe(
                (data) => {
                  this.scrollEnable = true;
                  this.isStampaProtocollata = true;
                  this.manageMessageTop(
                    "Lettera di rateizzazione creata con successo.",
                    "SUCCESS"
                  );
                },
                (err) => {
                  this.logger.error("Errore durante il download del PDF");
                  this.scrollEnable = true;
                }
              );
          } else {
            this.manageMessageTop(
              "La lettera di rateizzazione del piano a è già stata creata.",
              "DANGER"
            );
            this.isAnteprima = false;
            this.scrollEnable = true;
          }
        });
    } else {
      this.manageMessageTop("Compilare tutti i campi obbligatori", "WARNING");
      this.scrollEnable = true;
    }
  }

  scarica() {
    this.loaded = false;
    let request: DatiTemplateRequest = new DatiTemplateRequest();
    request.codiceTemplate = Constants.TEMPLATE_RATEIZZAZIONI;
    request.idPiano = this.idPiano;

    let nome: string;
    this.templateService.nomiTemplate(request).subscribe(
      (data) => {
        nome = data.nome;

        this.templateService.downloadTemplate(request).subscribe(
          (data1) => {
            saveAs(data1, nome);
            this.loaded = true;
            this.location.back();
          },
          (err) => {
            this.logger.error("Errore durante il download del PDF");
            this.loaded = true;
          }
        );
      },
      (err) => {
        this.logger.error("Errore durante il download del PDF");
        this.loaded = true;
      }
    );
  }

  checkDatiTemplate(dati: DatiTemplateCompilatiVO): boolean {
    let flag: boolean = true;
    let fieldsToIgnore = ['destinatariAggiuntivi','destinatariSoggetti'];
    for (let field in dati) {
      if (fieldsToIgnore.includes(field)) continue;
      if (!dati[field]) flag = false;
    }
    return flag;
  }

  manageMessageTop(message: string, type: string) {
    this.messageTop = message;
    this.typeMessageTop = type;
    this.timerShowMessageTop();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 20; //this.configService.getTimeoutMessagge();
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {        this.resetMessageTop();
      }
    }, 1000);
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }
  ngOnDestroy(): void {    this.logger.destroy(PagamentiTemplateComponent.name);
  }
  ngAfterViewChecked() {
    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  setFormValidTemplate(event: boolean) {    this.visualizzaAnteprimaValidTemplate = event;
  }


}
