import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { DatiTemplateVO } from "../../../commons/vo/template/dati-template-vo";
import { DatiTemplateCompilatiVO } from "../../../commons/vo/template/dati-template-compilati-vo";
import { DomSanitizer } from "@angular/platform-browser";
import { ActivatedRoute, Router } from "@angular/router";
import { TemplateService } from "../../../template/services/template.service";
import { DatiTemplateRequest } from "../../../commons/request/template/dati-template-request";
import { Constants } from "../../../commons/class/constants";
import { Location } from "@angular/common";
import { SharedRiscossioneService } from "../../../shared-riscossione/services/shared-riscossione.service";
import { IsCreatedVO } from "../../../commons/vo/isCreated-vo";
import { saveAs } from "file-saver";
import { Template11SollecitoPagamentoRateComponent } from "../../../template/components/template-11-lettera-sollecito-pagamento-rate/template-11-lettera-sollecito-pagamento-rate.component";

@Component({
  selector: "riscossione-sollecito-rate-template",
  templateUrl: "./riscossione-sollecito-rate-template.component.html",
})
export class RiscossioneSollecitoRateTemplateComponent
  implements OnInit, OnDestroy {

  @ViewChild(Template11SollecitoPagamentoRateComponent)
  template: Template11SollecitoPagamentoRateComponent;

  public subscribers: any = {};

  public idSollecito: number;

  public scrollEnable: boolean;
  public isAnteprima: boolean = false;
  public loaded: boolean;
  public isPrinted: boolean = false;

  public url: string;

  public datiTemplateModelStampa: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
  public datiTemplateModel: DatiTemplateVO;
  public datiCompilati: DatiTemplateCompilatiVO;

  visualizzaAnteprimaValidTemplate: boolean;
  visualizzaAnteprimaValidIntestazione: boolean;

  //Messaggio Top
  private intervalIdS: number = 0;
  public typeMessageTop: String;
  public showMessageTop: boolean;
  public messageTop: String;

  constructor(
    private sharedRiscossioneService: SharedRiscossioneService,
    private logger: LoggerService,
    private activatedRoute: ActivatedRoute,
    private location: Location,
    private templateService: TemplateService,
  ){}

  ngOnInit(): void {
    this.logger.init(RiscossioneSollecitoRateTemplateComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idSollecito = +params["id"];
      let request: DatiTemplateRequest = new DatiTemplateRequest();
      request.codiceTemplate = Constants.TEMPLATE_SOLLECITO_PAGAMENTO_RATE;
      request.idSollecito = this.idSollecito;
      this.subscribers.getTemplate = this.templateService
        .getDatiTemplate(request)
        .subscribe(
          (data) => {
            this.datiTemplateModel = data;
            this.loaded = true;
          },
          (err) => {
            this.logger.error("Errore durante il recupero dei dati");
            this.loaded = true;
          }
        );
    });
  }

  indietro() {
    this.location.back();
  }
  visualizzaAnteprima() {
    this.datiTemplateModelStampa = this.template.getDatiCompilati();
    this.isAnteprima = true;
    this.template.setAnteprima(true);
    this.scrollEnable = true;
  }



  proseguiModifica() {
    this.isStampa = false;
    this.isAnteprima = false;
    this.template.setAnteprima(false);
    this.template.setStampa(false);
    this.scrollEnable = true;
  }

  stampaPDF() {
    this.isStampa = true;
    this.isPrinted = true;

    this.template.setStampa(true);
    this.template.setDatiCompilati(this.datiTemplateModelStampa);

    let tmp: DatiTemplateCompilatiVO = this.template.getDatiCompilati();
    this.resetMessageTop();
    if (this.checkDatiTemplate(tmp)) {
      //this.loaded = false;
      let request: DatiTemplateRequest = new DatiTemplateRequest();
      request.codiceTemplate = Constants.TEMPLATE_SOLLECITO_PAGAMENTO_RATE;
      request.idSollecito = this.idSollecito;
      request.datiTemplateCompilatiVO = tmp;
      let isCreatedVO: IsCreatedVO = new IsCreatedVO();
      isCreatedVO.id = this.idSollecito;
      this.subscribers.isLetteraSaved = this.sharedRiscossioneService
        .isLetteraSollecitoSaved(isCreatedVO)
        .subscribe((data) => {
          if (!data.isCreated) {
            this.subscribers.stampa = this.templateService
              .stampaTemplate(request)
              .subscribe(
                (data) => {
                  //this.url = URL.createObjectURL(data);
                  this.scrollEnable = true;
                  this.loadedScarica = true;
                  this.isStampaProtocollata = true;
                  this.manageMessageTop(
                    "Lettera del sollecito creata con successo",
                    "SUCCESS"
                  );
                },
                (err) => {
                  this.logger.error("Errore durante il download del PDF");
                  //this.loaded = true;
                  this.isStampa = true;
                  this.scrollEnable = true;
                  this.isPrinted = false;
                  this.loadedScarica = false;
                }
              );
          } else {
            this.manageMessageTop(
              "La lettera del sollecito è già stata creata.",
              "DANGER"
            );
            this.isAnteprima = false;
            //this.loaded = true;
            this.isStampa = true;
            this.scrollEnable = true;
            this.isPrinted = false;
            this.loadedScarica = false;
          }
        });
    } else {
      this.manageMessageTop("Compilare tutti i campi obbligatori", "WARNING");
      this.scrollEnable = true;
    }
  }

  public isStampaProtocollata: boolean = false;
  public isStampa: boolean = false;
  public loadedScarica: boolean = false;

  scarica() {
    this.loaded = false;
    let request: DatiTemplateRequest = new DatiTemplateRequest();
    request.codiceTemplate = Constants.TEMPLATE_SOLLECITO_PAGAMENTO_RATE;
    request.idSollecito = this.idSollecito;
    let nome: string;
    this.templateService.nomiTemplate(request).subscribe((data) => {
      nome = data.nome;
      this.templateService.downloadTemplate(request).subscribe(
        (data) => {
          saveAs(data, nome);
          this.loaded = true;
          this.location.back();
        },
        (err) => {
          this.logger.error("Errore durante il download del PDF");
          this.loaded = true;
        }
      );
    });
  }

  manageMessageTop(message: string, type: string) {
    this.messageTop = message;
    this.typeMessageTop = type;
    this.timerShowMessageTop();
  }

  checkDatiTemplate(dati: DatiTemplateCompilatiVO): boolean {
    let flag: boolean = true;
    for (let field in dati) {
      if (!dati[field]) flag = false;
    }
    return flag;
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 20;
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }

  setFormValidTemplate(event: boolean) {
    this.visualizzaAnteprimaValidTemplate = event;
  }

  ngAfterViewChecked() {
    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }


  ngOnDestroy(): void {
    this.logger.init(RiscossioneSollecitoRateTemplateComponent.name);
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }

}
