import { Component, OnInit, OnDestroy, ViewChild, Inject } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TipoAllegatoVO, SelectVO } from "../../../commons/vo/select-vo";
import { Config } from "../../../shared/module/datatable/classes/config";
import { RiepilogoAllegatoVO } from "../../../commons/vo/verbale/riepilogo-allegato-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { AllegatoVO } from "../../../commons/vo/verbale/allegato-vo";
import { MyUrl } from "../../../shared/module/datatable/classes/url";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { TipologiaAllegabiliRequest } from "../../../commons/request/tipologia-allegabili-request";
import { SalvaAllegatoVerbaleRequest } from "../../../commons/request/verbale/salva-allegato-verbale-request";
import { RicercaProtocolloRequest } from "../../../commons/request/verbale/ricerca-protocollo-request";
import { DatatableService } from "../../../shared/module/datatable/services/datatable-service";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { DOCUMENT } from "@angular/platform-browser";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { FascicoloService } from "../../../fascicolo/services/fascicolo.service";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";

@Component({
  selector: "pregresso-allegato",
  templateUrl: "./pregresso-allegato.component.html",
})
export class PregressoAllegatoComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;

  public idOrdinanza: number;
  public idVerbale: number;
  public numDeterminazione: string;
  public loadedConfig: boolean;
  public loaded: boolean;
  public loadedCategoriaAllegato: boolean;
  public loadedAllegato: boolean = true;

  public subscribers: any = {};

  public tipoAllegatoModel: Array<TipoAllegatoVO>;
  public allegatoModel: RiepilogoAllegatoVO = new RiepilogoAllegatoVO();

  public typeMessageTop: string;
  public showMessageTop: boolean;
  private intervalIdS: number = 0;
  public messageTop: string;

  public allegaDocumentoFlag: boolean;
  public buttonModificaFlag: boolean;
  public eliminaAllegatoFlag: boolean;

  public buttonConfirmTexts: string;
  public buttonAnnullaTexts: string;
  public subMessagess: Array<string>;

  scrollEnable: boolean;

  public configIstr: Config;
  public configVerb: Config;
  public configRateizzazione: Config;
  public configGiurisd: Config;


  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private logger: LoggerService,
    private sharedVerbaleService: SharedVerbaleService,
    private utilSubscribersService: UtilSubscribersService,
    private datatableService: DatatableService,
    private pregressoVerbaleService: PregressoVerbaleService,
    private fascicoloService: FascicoloService,
    private configSharedService: ConfigSharedService,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    this.logger.init(PregressoAllegatoComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      if (isNaN(this.idVerbale))
        this.router.navigateByUrl(Routing.VERBALE_DATI);

      // riferimento ricerca documento protocollato
      this.fascicoloService.ref = this.router.url;
      if (this.activatedRoute.snapshot.paramMap.get("ref")) {
        // riferimento ricerca documento protocollato
        this.fascicoloService.ref = this.activatedRoute.snapshot.paramMap.get(
          "ref"
        );
        this.fascicoloService.backPage = this.router.url;
      } else {
        // riferimento ricerca documento protocollato
        this.fascicoloService.ref = this.router.url;
      }
      if (this.activatedRoute.snapshot.paramMap.get("idOrdinanza")) {
        // setto il riferimento per la ricerca documento protocollato
        this.idOrdinanza = parseInt(
          this.activatedRoute.snapshot.paramMap.get("idOrdinanza")
        );
      }
      if (this.activatedRoute.snapshot.paramMap.get("numDeterminazione")) {
        // set riferimento ricerca documento protocollato
        this.numDeterminazione = this.activatedRoute.snapshot.paramMap.get(
          "numDeterminazione"
        );
      }

      this.subscribers.statoVerbale = this.pregressoVerbaleService
        .getAzioniVerbale(this.idVerbale)
        .subscribe((data) => {
          this.buttonModificaFlag = data.modificaVerbaleEnable;
          this.allegaDocumentoFlag = data.aggiungiAllegatoEnable;
          this.eliminaAllegatoFlag = data.eliminaAllegatoEnable;

          // set  config
          this.configVerb = this.configSharedService.getConfigDocumentiVerbale(
            this.eliminaAllegatoFlag
          );
          this.configIstr = this.configSharedService.configDocumentiIstruttoria;
          this.configGiurisd = this.configSharedService.configDocumentiGiurisdizionale;
          this.configRateizzazione = this.configSharedService.configDocumentiRateizzazione;

          this.loadedConfig = true;
        });

      // allegati da mettere in tabella
      this.subscribers.allegati = this.pregressoVerbaleService
        .getAllegatiByIdVerbale(this.idVerbale)
        .subscribe(
          (data) => {
            this.allegatoModel = data;
            this.allegatoModel.verbale.forEach((all) => {
              all.theUrl = new MyUrl(all.nome, null);
            });
            this.allegatoModel.istruttoria.forEach((all) => {
              all.theUrl = new MyUrl(all.nome, null);
            });
            this.allegatoModel.giurisdizionale.forEach((all) => {
              all.theUrl = new MyUrl(all.nome, null);
            });
            this.allegatoModel.rateizzazione.forEach((all) => {
              all.theUrl = new MyUrl(all.nome, null);
            });
            this.loaded = true;
          },
          (err) => {
            this.logger.error("Errore nel recupero degli allegati");
          }
        );

      this.loadTipoAllegato();
    });
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 20 ;
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }
  manageMessage(type: string, message: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
  }


  //tipologie di allegato allegabili
  loadTipoAllegato() {
    this.loadedCategoriaAllegato = false;
    let request = new TipologiaAllegabiliRequest();
    request.id = this.idVerbale;
    this.subscribers.tipoAllegato = this.pregressoVerbaleService
      .getTipologiaAllegatiAllegabiliVerbale(request)
      .subscribe(
        (data) => {
          this.tipoAllegatoModel = data.sort((a, b) => a.id - b.id);
          this.loadedCategoriaAllegato = true;
          // set rif x  ricerca documento protocollato
          this.fascicoloService.tipoAllegatoModel = this.tipoAllegatoModel;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err.type, err.message);
          }
          this.logger.info("Errore nel recupero dei tipi di allegato");
          this.loadedCategoriaAllegato = true;
        }
      );
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }

  salvaAllegato(nuovoAllegato: SalvaAllegatoVerbaleRequest) {
    nuovoAllegato.idVerbale = this.idVerbale;
    //file > Back End
    this.loaded = false;
    this.subscribers.salvaAllegato = this.pregressoVerbaleService
      .salvaAllegatoVerbale(nuovoAllegato)
      .subscribe(
        (data) => {
          if (nuovoAllegato.file != null) {
            data.theUrl = this.datatableService.creaUrl(nuovoAllegato.file);
            // mostro nuovo allegato in tabella
            if (data.idCategoria == 1) this.allegatoModel.verbale.push(data);
            if (data.idCategoria == 2)
              this.allegatoModel.istruttoria.push(data);
            if (data.idCategoria == 3)
              this.allegatoModel.giurisdizionale.push(data);
            if (data.idCategoria == 4)
              this.allegatoModel.rateizzazione.push(data);
          }

          //recupero allegati da mettere in tabella
          this.subscribers.allegati = this.pregressoVerbaleService
            .getAllegatiByIdVerbale(this.idVerbale)
            .subscribe(
              (data) => {
                this.allegatoModel = data;
                this.allegatoModel.verbale.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                });
                this.allegatoModel.istruttoria.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                });
                this.allegatoModel.giurisdizionale.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                });
                this.allegatoModel.rateizzazione.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                });
              },
              (err) => {
                this.logger.error("Errore nel recupero degli allegati");
              }
            );

          this.loadTipoAllegato();
          //mostro un messaggio
          this.manageMessage("SUCCESS", "Documento caricato con successo");
          this.loaded = true;
          this.scrollEnable = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.manageMessage(err.type, err.message);
          }
          this.logger.error("Errore nel salvataggio dell'allegato");
          this.loaded = true;
          this.scrollEnable = true;
        }
      );
  }

  ricercaProtocollo(ricerca: RicercaProtocolloRequest) {
    ricerca.idVerbale = this.idVerbale;
    ricerca.flagPregresso = true;
    this.loaded = false;

    this.sharedVerbaleService.ricercaProtocolloSuACTA(ricerca).subscribe(
      (data) => {
        this.loaded = true;
        let tipiAllegatoDuplicabili = [];
        if (!data.documentoProtocollatoVOList) {
          data.documentoProtocollatoVOList = [];
        } else if (data.documentoProtocollatoVOList[0]) {
          tipiAllegatoDuplicabili =
            data.documentoProtocollatoVOList[0].tipiAllegatoDuplicabili;
        }
        this.fascicoloService.categoriesDuplicated = tipiAllegatoDuplicabili;

        // set numero di protocollo per ricerca documento protocollato
        this.fascicoloService.searchFormRicercaProtocol =
          ricerca.numeroProtocollo;
        // set i dati per la ricerca documento protocollato
        this.fascicoloService.dataRicercaProtocollo =
          data.documentoProtocollatoVOList;
        this.fascicoloService.successPage =
          Routing.PREGRESSO_RIEPILOGO + this.idVerbale;
        if (data.messaggio) {
          this.fascicoloService.message = data.messaggio;
        }else{
          this.fascicoloService.message = null;
        }

        const numpages:number = Math.ceil(+data.totalLineResp/+data.maxLineReq);
        this.fascicoloService.dataRicercaProtocolloNumPages = numpages;
		    this.fascicoloService.dataRicercaProtocolloNumResults = +data.totalLineResp;

        if (this.idOrdinanza && this.numDeterminazione) {
          this.router.navigate([
            Routing.FASCICOLO_ALLEGATO_DA_ACTA + this.idVerbale,
            {
              idOrdinanza: this.idOrdinanza,
              numDeterminazione: this.numDeterminazione,
            },
          ]);
        } else {
          this.router.navigateByUrl(
            Routing.FASCICOLO_ALLEGATO_DA_ACTA + this.idVerbale
          );
        }
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err.type, err.message);
        }
        this.loaded = true;
        this.logger.error("Errore nel recupero dei documenti protocollati");
      }
    );
  }

  confermaEliminazione(el: AllegatoVO) {
    this.logger.info("Richiesta eliminazione dell'allegato " + el.id);
    this.generaMessaggio(el);
    this.buttonAnnullaTexts = "Annulla";
    this.buttonConfirmTexts = "Conferma";

    //mostro messaggio
    this.sharedDialogs.open();

    // unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //click "Conferma"
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.loaded = false;
        this.subscribers.eliminaAllegato = this.sharedVerbaleService
          .eliminaAllegato(el.id, this.idVerbale)
          .subscribe(
            (data) => {
              // elim allegato
              this.logger.info("Elimina elemento da tabella");
              this.allegatoModel.verbale.splice(
                this.allegatoModel.verbale.indexOf(el),
                1
              );
              this.loadTipoAllegato();
              this.loaded = true;
              this.scrollEnable = true;
              this.manageMessage("SUCCESS", "Documento eliminato con successo");
            },
            (err) => {
              if (err instanceof ExceptionVO) {
                this.manageMessage(err.type, err.message);
              }
              this.logger.error(
                "Errore nell'eliminazione dell'allegato " + el.id
              );
              this.loaded = true;
              this.scrollEnable = true;
            }
          );
      },
      (err) => {
        this.logger.error(err);
      }
    );

    // click"Annulla"
    this.subscribers.close = this.sharedDialogs.closeAction.subscribe(
      (data) => {        this.subMessagess = new Array<string>();      },
      (err) => {        this.logger.error(err);      }
    );
  }


  generaMessaggio(el: AllegatoVO) {
    this.subMessagess = new Array<string>();
    let incipit: string = "Si intende eliminare il seguente allegato?";
    this.subMessagess.push(incipit);
    this.subMessagess.push(el.theUrl.nomeFile);
  }

  ngAfterViewChecked() {
    let out: HTMLElement = document.getElementById("scrollTop");
    if (
      this.loaded &&
      this.scrollEnable &&
      this.loadedConfig &&
      this.loadedCategoriaAllegato &&
      out != null
    ) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  goToVerbaleDati() {    this.router.navigateByUrl(Routing.PREGRESSO_DATI + this.idVerbale);  }
  goToVerbaleRiepilogo() {    this.router.navigateByUrl(Routing.PREGRESSO_RIEPILOGO + this.idVerbale);  }
  onLoadedAllegato(loaded: any) {    this.loadedAllegato = loaded;  }
  ngOnDestroy(): void {    this.logger.destroy(PregressoAllegatoComponent.name);  }
  byId(o1: SelectVO, o2: SelectVO) {    return o1 && o2 ? o1.id === o2.id : o1 === o2;  }
  goToVerbaleSoggetto() {    this.router.navigateByUrl(Routing.PREGRESSO_SOGGETTO + this.idVerbale);  }

}
