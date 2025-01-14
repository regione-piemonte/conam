import { Component, OnInit, OnDestroy, ViewChild, Inject } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { TipoAllegatoVO, SelectVO } from "../../../commons/vo/select-vo";
import { VerbaleService } from "../../services/verbale.service";
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
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";

@Component({
  selector: "verbale-allegato",
  templateUrl: "./verbale-allegato.component.html",
})
export class VerbaleAllegatoComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;

  public subscribers: any = {};

  public loaded: boolean;
  public idVerbale: number;
  public loadedAllegato: boolean = true;
  public loadedConfig: boolean;
  public riepilogoVerbale: RiepilogoVerbaleVO;
  public loadedCategoriaAllegato: boolean;

  public tipoAllegatoModel: Array<TipoAllegatoVO>;
  public allegatoModel: RiepilogoAllegatoVO = new RiepilogoAllegatoVO();

  public typeMessageTop: string;
  public showMessageTop: boolean;
  public alertBottom: boolean =  false;
  public messageTop: string;
  private intervalIdS: number = 0;

  public allegaDocumentoFlag: boolean;
  public buttonModificaFlag: boolean;
  public eliminaAllegatoFlag: boolean;

  public buttonConfirmTexts: string;
  public buttonAnnullaTexts: string;
  public subMessagess: Array<string>;

  scrollEnable: boolean;

  public configVerb: Config;
  public configGiurisd: Config;
  public configIstr: Config;
  public configRateizzazione: Config;

  public showEdit: boolean= true

  constructor(
    private logger: LoggerService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private verbaleService: VerbaleService,
    private sharedVerbaleService: SharedVerbaleService,
    private utilSubscribersService: UtilSubscribersService,
    private configSharedService: ConfigSharedService,
    private fascicoloService: FascicoloService,
    private datatableService: DatatableService,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    this.logger.init(VerbaleAllegatoComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      if (isNaN(this.idVerbale)){
        this.router.navigateByUrl(Routing.VERBALE_DATI);
      }
      // setto il riferimento della ricerca documento protocollato
      this.fascicoloService.ref = this.router.url;

      this.subscribers.statoVerbale = this.sharedVerbaleService
        .getAzioniVerbale(this.idVerbale)
        .subscribe((data) => {
          this.allegaDocumentoFlag = data.aggiungiAllegatoEnable;
          this.buttonModificaFlag = data.modificaVerbaleEnable;
          this.eliminaAllegatoFlag = data.eliminaAllegatoEnable;
          // set config
          //console.log('here');
          //console.log('here - data',data);
          this.configVerb = this.configSharedService.getConfigDocumentiVerbale(
            //this.eliminaAllegatoFlag,
            this.eliminaAllegatoFlag, (el: any) => true
          );
          this.configIstr = this.configSharedService.configDocumentiIstruttoria;
          this.configGiurisd = this.configSharedService.configDocumentiGiurisdizionale;
          this.configRateizzazione = this.configSharedService.configDocumentiRateizzazione;
          this.loadedConfig = true;
        });

      /* spostato dentro recupero dati verbale
      // allegati x tabella
      this.subscribers.allegati = this.verbaleService
        .getAllegatiByIdVerbale(this.idVerbale)
        .subscribe(
          (data) => {
            console.log('verbale-allegato data', data);
            console.log('this.riepilogoVerbale',this.riepilogoVerbale);
            this.allegatoModel = data;
            this.allegatoModel.istruttoria.forEach((all) => {
              all.theUrl = new MyUrl(all.nome, null);
            });
            this.allegatoModel.verbale.forEach((all) => {
              all.theUrl = new MyUrl(all.nome, null);
            });
            this.allegatoModel.rateizzazione.forEach((all) => {
              all.theUrl = new MyUrl(all.nome, null);
            });
            this.allegatoModel.giurisdizionale.forEach((all) => {
              all.theUrl = new MyUrl(all.nome, null);
            });
            this.loaded = true;
          },
          (err) => {
            this.logger.error("Errore nel recupero degli allegati");
          }
        );
      this.loadTipoAllegato();
      */
      // recupero riepilogo verbale
      this.subscribers.riepilogo = this.sharedVerbaleService.riepilogoVerbale(this.idVerbale).subscribe(data => {
        this.riepilogoVerbale = data;
     //     this.showEdit = data.verbale.stato.id!=3; //conciliato=3
          // allegati x tabella
          this.subscribers.allegati = this.verbaleService
            .getAllegatiByIdVerbale(this.idVerbale)
            .subscribe(
              (data) => {
                console.log('verbale-allegato data', data);
                //console.log('this.riepilogoVerbale',this.riepilogoVerbale);
                this.allegatoModel = data;
                this.allegatoModel.istruttoria.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                });
                this.allegatoModel.verbale.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                  all.showEdit = this.showEdit && all.tipo.id==43;
                  //quiiiii
                });
                this.allegatoModel.rateizzazione.forEach((all) => {
                  all.theUrl = new MyUrl(all.nome, null);
                });
                this.allegatoModel.giurisdizionale.forEach((all) => {
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
    });
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

  manageMessage(type: string, message: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
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
        },
        (err) => {
          if (err instanceof ExceptionVO) {            this.manageMessage(err.type, err.message);          }
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
    //console.log('verbale-allegato nuovoAllegato>>>>>>>>', nuovoAllegato);
    nuovoAllegato.idVerbale = this.idVerbale;
    //this.showEdit già valorizzato true se non conciliato
    //mando il file al Back End
    this.loaded = false;
    this.subscribers.salvaAllegato = this.sharedVerbaleService
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

          // recupero allegati x tabella
          this.subscribers.allegati = this.verbaleService
            .getAllegatiByIdVerbale(this.idVerbale)
            .subscribe(
              (data) => {
                this.allegatoModel = data;
                this.allegatoModel.verbale.forEach((all) => {
                  all.showEdit = this.showEdit && all.tipo.id==43;
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
          // mostro messaggio
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

        // setto numero protocollo ricerca documento protocollato
        this.fascicoloService.searchFormRicercaProtocol =
          ricerca.numeroProtocollo;
        // setto dati ricerca documento protocollato
        this.fascicoloService.dataRicercaProtocollo =
          data.documentoProtocollatoVOList;
        if (data.messaggio) {
          this.alertBottom = true;
          this.fascicoloService.message = data.messaggio;
          this.manageMessage(data.messaggio.type, data.messaggio.message, )
        }else{
          this.fascicoloService.message = null;
        const numpages:number = Math.ceil(+data.totalLineResp/+data.maxLineReq);
        this.fascicoloService.dataRicercaProtocolloNumPages = numpages;
        this.fascicoloService.dataRicercaProtocolloNumResults = +data.totalLineResp;
        this.fascicoloService.successPage =
          Routing.VERBALE_RIEPILOGO + this.idVerbale;
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

    // mostro messaggio
    this.sharedDialogs.open();

    // unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    // premo Conferma
    this.subscribers.save = this.sharedDialogs.salvaAction.subscribe(
      (data) => {
        this.loaded = false;
        this.subscribers.eliminaAllegato = this.sharedVerbaleService
          .eliminaAllegato(el.id, this.idVerbale)
          .subscribe(
            (data) => {
              // elimina allegato
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

    // premo Annulla
    this.subscribers.close = this.sharedDialogs.closeAction.subscribe(
      (data) => {
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
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

  goToVerbaleRiepilogo() {
    this.router.navigateByUrl(Routing.VERBALE_RIEPILOGO + this.idVerbale);
  }

  generaMessaggio(el: AllegatoVO) {
    this.subMessagess = new Array<string>();

    let incipit: string = "Si intende eliminare il seguente allegato?";

    this.subMessagess.push(incipit);
    this.subMessagess.push(el.theUrl.nomeFile);
  }

  onLoadedAllegato(loaded: any) {
    this.loadedAllegato = loaded;
  }

  goToVerbaleSoggetto() {
    this.router.navigateByUrl(Routing.VERBALE_SOGGETTO + this.idVerbale);
  }

  goToVerbaleDati() {
    this.router.navigateByUrl(Routing.VERBALE_DATI + this.idVerbale);
  }
  goToDatiProvaPagamento(el){
    console.log(el);
    const currentUrl = this.router.url;
    if (currentUrl.includes('pregresso/')) {
      this.router.navigate([Routing.PREGRESSO_DETTAGLIO_PROVA_PAGAMENTO + this.idVerbale], {
        queryParams: {idAllegato : el.id}
      });
    } else if (currentUrl.includes('verbale/')) {
      this.router.navigate([Routing.DETTAGLIO_PROVA_PAGAMENTO + this.idVerbale], {
        queryParams: {idAllegato : el.id}
      });
    }
    /*this.router.navigate(['/route-to'], {
        someData: { name: 'Some name', description: 'Some description' },
    });*/
  }
  ngOnDestroy(): void {
    this.logger.destroy(VerbaleAllegatoComponent.name);
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

}
