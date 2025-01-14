import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  OnChanges,
  EventEmitter,
  Output,
  ViewChild,
  SimpleChanges,
  AfterViewInit,
} from "@angular/core";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { SharedVerbaleService } from "../../service/shared-verbale.service";
import { ActivatedRoute } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { RiepilogoAllegatoVO } from "../../../commons/vo/verbale/riepilogo-allegato-vo";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { VerbaleService } from "../../../verbale/services/verbale.service";
import { MyUrl } from "../../../shared/module/datatable/classes/url";
import { TipologiaAllegabiliRequest } from "../../../commons/request/tipologia-allegabili-request";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { SelectVO, TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { FascicoloService } from "../../../fascicolo/services/fascicolo.service";
import {
  AllegatoField,
  DatiProvaPagamentoVO,
} from "../../../commons/vo/verbale/dettaglio-prova-pagamento";
import { SalvaAllegatoRequest } from "../../../commons/request/salva-allegato-request";
import { Location } from "@angular/common";
import { filter, map } from "rxjs/operators";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
@Component({
  selector: "dettaglio-prova-pagamento",
  templateUrl: "./dettaglio-prova-pagamento.component.html",
})
export class DettaglioProvaPagamentoComponent
  implements OnInit, OnDestroy, OnChanges, AfterViewInit
{
  public subscribers: any = {};
  public riepilogoVerbale: RiepilogoVerbaleVO;
  public idVerbale: number;
  public idAllegato: number;
  loadedConfig: boolean = false;
  loaded: boolean = false;
  public allegatoModel: RiepilogoAllegatoVO = new RiepilogoAllegatoVO();
  loadedCategoriaAllegato: boolean = false;
  fieldValues: DatiProvaPagamentoVO;
  public configVerb: Config;
  public dataDocumentiVerbale: Array<any>;
  public tipoAllegatoModel: Array<TipoAllegatoVO>;
  public defaultValueTipoAllegato: TipoAllegatoVO;
  payload: SalvaAllegatoRequest;
  requestValid: boolean = false;
  listaSoggetti: SelectVO[];
  public showSpinner = true;
  allReadonly: boolean = false;
  public inputSharedAllegatoMetadatiInserimentoReady = false;
  constructor(
    private logger: LoggerService,
    private configSharedService: ConfigSharedService,
    private route: ActivatedRoute,
    private verbaleService: VerbaleService,
    private fascicoloService: FascicoloService,
    private sharedVerbaleService: SharedVerbaleService,
    private location: Location,
    private allegatoSharedService: AllegatoSharedService
  ) {
    this.idVerbale = +this.route.snapshot.paramMap.get("id");
    console.log(this.route.snapshot.paramMap);
    this.route.queryParams
      .pipe(
        map((params) => {
          return (this.idAllegato = params.idAllegato);
        })
      )

      .subscribe((params) => {
        console.log(params); // { order: "popular" }

        //   this.idAllegato = params.idAllegato;
      });
    this.loadTipoAllegato();
    //this.idAllegato= +this.route.snapshot.paramMap.get("idAllegato");
    this.configVerb = this.configSharedService.getConfigDocumentiVerbale(
      false,
      (el) => false
    );
  }
  ngOnInit(): void {
    console.log("here");
    this.defaultValueTipoAllegato = {
      id: 43,
      denominazione: "Prova del pagamento",
    };

    this.subscribers.riepilogo = this.sharedVerbaleService
      .riepilogoVerbale(this.idVerbale)
      .subscribe((data) => {
        //datasource
        this.allReadonly = data.verbale.stato.id === 3;
        this.riepilogoVerbale = data;
        console.log('this.riepilogoVerbale',this.riepilogoVerbale);
        this.getListaSelect();
        //  this.loaded = true;
      });

    //this.getFormValue();
  }
  ngAfterViewInit() {
    this.subscribers.allegati = this.verbaleService
      .getAllegatiByIdVerbale(this.idVerbale)
      .subscribe(
        (data) => {
          this.allegatoModel = data;

          this.allegatoModel.verbale.forEach((all) => {
            all.theUrl = new MyUrl(all.nome, null);
          });

          let allegato = this.allegatoModel.verbale.filter(
            (el) => el.id === Number(this.idAllegato)
          );
          this.dataDocumentiVerbale = allegato;

          this.loaded = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero degli allegati");
        }
      );
  }
  getListaSelect() {
    this.allegatoSharedService
      .getDecodificaSelectSoggettiAllegatoCompleto(
        this.riepilogoVerbale.verbale.id
      )
      .subscribe((data) => {
        this.listaSoggetti = data;

        console.log(this.listaSoggetti);

        this.getFormValue();
      });
  }
  // recupero tipologie allegato allegabili
  loadTipoAllegato() {
    this.loadedCategoriaAllegato = false;
    let request = new TipologiaAllegabiliRequest();
    request.id = this.idVerbale;
    this.subscribers.tipoAllegato = this.verbaleService
      .getTipologiaAllegatiAllegabiliVerbale(request)
      .subscribe(
        (data) => {
          this.tipoAllegatoModel = data.sort((a, b) => a.id - b.id);
          this.loadedCategoriaAllegato = true;
          // setto riferimento ricerca documento protocollato
          this.fascicoloService.tipoAllegatoModel = this.tipoAllegatoModel;
          ///
        },
        (err) => {
          //  if (err instanceof ExceptionVO) {
          //    this.manageMessage(err.type, err.message);          }
          this.logger.info("Errore nel recupero dei tipi di allegato");
          this.loadedCategoriaAllegato = true;
        }
      );
  }
  getFormValue() {
    this.verbaleService
      .getFieldValuesProvaDelPagameto(this.idAllegato)
      .subscribe((data: DatiProvaPagamentoVO) => {
        console.log(data);
        this.fieldValues = data;
        this.inputSharedAllegatoMetadatiInserimentoReady=true;
      });
  }
  salvaAllegato(el: SalvaAllegatoRequest) {
    console.log("save", el);
    this.payload = el;
    this.requestValid = true;
  }
  save() {
    this.showSpinner = true;
    this.verbaleService
      .updateProvaDelPagameto(this.idAllegato, this.payload)
      .subscribe((data) => {
        console.log(data);
        this.showSpinner = false;
        this.location.back();
      });
  }
  back() {
    this.location.back();
  }
  ngOnChanges(changes: SimpleChanges): void {}
  ngOnDestroy(): void {}
  handleShowSpinner($event) {
    console.log("handleShowSpinner", $event);
    this.showSpinner = $event;
  }
}
