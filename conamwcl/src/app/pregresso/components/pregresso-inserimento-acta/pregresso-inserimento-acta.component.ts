import { Component, OnInit, OnDestroy, Inject, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import {  RegioneVO,  ProvinciaVO,  ComuneVO,  NormaVO,  ArticoloVO,  CommaVO,  LetteraVO,  EnteVO,  SelectVO,  AmbitoVO,  TipoAllegatoVO,} from "../../../commons/vo/select-vo";
import { RifNormativiService } from "../../services/rif-normativi.service";
import { UserService } from "../../../core/services/user.service";
import { VerbaleService } from "../../services/verbale.service";
import { VerbaleVO } from "../../../commons/vo/verbale/verbale-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { RiferimentiNormativiVO } from "../../../commons/vo/verbale/riferimenti-normativi-vo";
import { LuoghiService } from "../../../core/services/luoghi.service";
import { DOCUMENT } from "@angular/platform-browser";
import { NumberUtilsSharedService } from "../../../shared/service/number-utils-shared.service";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { SalvaAllegatoVerbaleRequest } from "../../../commons/request/verbale/salva-allegato-verbale-request";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";
import { DocumentoProtocollatoVO } from "../../../commons/vo/verbale/documento-protocollato-vo";
import {  FascicoloService,  NumberDocument,} from "../../../fascicolo/services/fascicolo.service";
import { Config } from "../../../shared/module/datatable/classes/config";
import { MyUrl } from "../../../shared/module/datatable/classes/url";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { RicercaProtocolloRequest } from "../../../commons/request/verbale/ricerca-protocollo-request";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { AllegatoSharedService } from "../../../shared/service/allegato-shared.service";
import { saveAs } from "file-saver";
import { winLoadingHtml } from "../../../fascicolo/components/fascicolo-allegato-da-acta/win-loading-html";
import { SalvaAllegatoProtocollatoVerbaleRequest } from "../../../commons/request/verbale/salva-allegato-protocollato-verbale-request";
import { SalvaAllegatoProtocollatoVerbaleRequestAllegato } from "../../../commons/request/salva-allegato-protocollato-request-common";
import { MessageVO } from "../../../commons/vo/messageVO";
import { TipologiaAllegabiliRequest } from "../../../commons/request/tipologia-allegabili-request";

declare var $: any;

@Component({
  selector: "pregresso-inserimento-acta",
  templateUrl: "./pregresso-inserimento-acta.component.html",
})
export class PregressoInserimentoActaComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

  public loadedProvince: boolean;
  public loaded: boolean;
  public loadedRegioni: boolean;
  public loadedComuni: boolean;

  public subscribers: any = {};

  public loadedLettera: Array<boolean> = [];
  public loadedArticolo: Array<boolean> = [];
  public loadedNorma: Array<boolean> = [];
  public loadedComma: Array<boolean> = [];
  public singoloEnte: boolean;
  public loadedAmbito: Array<boolean> = [];

  public idVerbale: number;

  public isVisible: Array<boolean> = [true];

  public verbale: VerbaleVO;

  public letteraModel: Array<Array<LetteraVO>> = [];
  public provinciaModel: Array<ProvinciaVO>;
  public regioneModel: Array<RegioneVO>;
  public comuneEnteModel: Array<ComuneVO>;
  public enteAccertatoreModel: Array<EnteVO>;
  public enteModel: Array<EnteVO>;
  public comuneModel: Array<ComuneVO>;
  public normaModel: Array<Array<NormaVO>> = [];
  public ambitoModel: Array<Array<AmbitoVO>> = [];
  public commaModel: Array<Array<CommaVO>> = [];
  public articoloModel: Array<Array<ArticoloVO>> = [];

  private uIdCounter: number = 0;
  public rifNormMessage: string =    "Impossibile inserire più Riferimenti Normativi identici";

  private intervalIdS: number = 0;
  private intervalIdW: number = 0;

  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  public showWarning: boolean;
  public messageWarning: string;
  public typeWarning: string;

  public nuovoAllegato: SalvaAllegatoVerbaleRequest;
  public tipoAllegatoModel: Array<TipoAllegatoVO>;
  public loadedCategoriaAllegato: boolean;

  public showLocalita: boolean;
  public showVerbale: boolean;
  public showRiferimentiNormativi: boolean;
  public countSoggetti: number;
  public showSoggetto: boolean;

  /// da f a s c i c o l o allegato da ACTA
  public configRicercaProtocollo: Config;
  searchFormRicercaProtocol: string;
  dataRicercaProtocollo: Array<DocumentoProtocollatoVO>;
  numberDocument: NumberDocument;
  validsAllegatoMetadata: any[] = [];
  dataRicercaProtocolloSelected: DocumentoProtocollatoVO;
  saveDisabled: boolean = true;
  incompleted: boolean = false;
  public subLinks: Array<any>;
  public buttonAnnullaTexts: string;
  public buttonConfirmTexts: string;
  public subMessagess: Array<string>;

  numPages: number;
  numResults: number;
  currentPage: number = 1;

  constructor(
    private verbaleService: VerbaleService,
    private router: Router,
    private luoghiService: LuoghiService,
    private activatedRoute: ActivatedRoute,
    private configSharedService: ConfigSharedService,
    private logger: LoggerService,
    private fascicoloService: FascicoloService,
    private userService: UserService,
    private pregressoVerbaleService: PregressoVerbaleService,
    private utilSubscribersService: UtilSubscribersService,
    private allegatoSharedService: AllegatoSharedService,
    private rifNormativiService: RifNormativiService,
    private numberUtilsSharedService: NumberUtilsSharedService,
    private sharedVerbaleService: SharedVerbaleService,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    this.logger.init(PregressoInserimentoActaComponent.name);
    this.showVerbale = false;
    this.showSoggetto = false;
    this.showLocalita = false;
    this.countSoggetti = 0;
    this.showRiferimentiNormativi = false;
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      let verbString = params["id"];
      if (!verbString || isNaN(+verbString)) {
        this.idVerbale = null;
      } else {
        this.idVerbale = +verbString;
      }
      this.subscribers.userProfile = this.userService.profilo$.subscribe(
        (data) => {
          if (data != null) {
            this.loaded = false;
            if (              this.activatedRoute.snapshot.paramMap.get("action") == "creazione"            ){
              this.manageMessageSuccess(
                "Fascicolo creato con successo",
                "SUCCESS"
              );
            }
            if (              this.activatedRoute.snapshot.paramMap.get("action") ==              "salvato_con_warning"            ){
              this.manageMessageSuccess(
                "Il Fascicolo è stato salvato, ma la Data e ora processo verbale sono successive alla Data e ora accertamento",
                "WARNING"
              );
            }
            this.loadTipoAllegato();
            this.loadRegioni();
            this.comuniEnteValidInDate();
            this.loadEnti(data.entiAccertatore, data.entiLegge);
            if (!this.idVerbale) {
              this.nuovoVerbale();
            } else {
              this.modificaVerbale();
            }
          }
        }
      );

      this.configRicercaProtocollo = this.configSharedService.getConfigRicercaProtocollo(
        true
      );

      // carico la stringa di ricerca da cui arrivo
      this.searchFormRicercaProtocol = this.fascicoloService.searchFormRicercaProtocol;

      // carico i dati della ricerca da cui arrivo e utilizzo il tipo url per lo scaricamento dei file
      this.dataRicercaProtocollo = this.fascicoloService.dataRicercaProtocollo;
      if (this.dataRicercaProtocollo && this.dataRicercaProtocollo.length > 0) {
        this.dataRicercaProtocollo.forEach((all) => {
          all.filename = new MyUrl(<string>all.filename, null);
        });
      }
      // setto numPages
      this.numResults = this.fascicoloService.dataRicercaProtocolloNumResults;
      this.numPages = this.fascicoloService.dataRicercaProtocolloNumPages;
      this.currentPage = 1;
      // setto e recupero le info del numberdocument
      this.numberDocument = this.fascicoloService.setNumberDocument();
      // forzo il readonly in questa pagina
      this.numberDocument.readonly = true;
      if (this.fascicoloService.message) {
        this.manageMessage(this.fascicoloService.message);
        this.fascicoloService.message = null;
      }
    });
  }

  manageMessageSuccess(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
  }

  //recupero le tipologie di allegato allegabili
  loadTipoAllegato() {
    this.loadedCategoriaAllegato = false;
    let request = new TipologiaAllegabiliRequest();
    this.subscribers.tipoAllegato = this.pregressoVerbaleService
      .getTipologiaAllegatiAllegabiliVerbale(request)
      .subscribe(
        (data) => {
          this.tipoAllegatoModel = data.sort((a, b) => a.id - b.id);
          this.loadedCategoriaAllegato = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {            this.manageMessage(err);          }
          this.loadedCategoriaAllegato = true;
          this.logger.info("Errore nel recupero dei tipi di allegato");
        }
      );
  }

  manageMessageWarning(message: string) {
    this.typeWarning = "WARNING";
    this.messageWarning = message;
    this.timerShowMessageWarning();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 30; //this.configService.getTimeoutMessagge();
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }

  manageMessage(err: ExceptionVO | MessageVO) {
    this.typeMessageTop = err.type;
    this.messageTop = err.message;
    this.timerShowMessageTop();
  }

  resetMessageTop() {
    this.typeMessageTop = null;
    this.showMessageTop = false;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }

  timerShowMessageWarning() {
    this.showWarning = true;
    let seconds: number = 50;
    this.intervalIdW = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageWarning();
      }
    }, 1000);
  }

  nuovoVerbale() {
    this.verbale = new VerbaleVO();
    this.verbale.contestato = false;
    if (this.enteModel.length == 1){
      this.verbale.enteRiferimentiNormativi = this.enteModel[0];
    }
    this.nuovoRiferimentoNormativo();
    this.loaded = true;
  }

  resetMessageWarning() {
    this.messageWarning = null;
    this.showWarning = false;
    this.typeWarning = null;
    clearInterval(this.intervalIdW);
  }
  modificaVerbale() {
    this.subscribers.modificaVerbale = this.verbaleService
      .getVerbaleById(this.idVerbale)
      .subscribe(
        (data) => {
          this.verbale = data;
          this.loaded = true;
          this.loadProvinceByIdRegione(this.verbale.regione.id);
          this.loadComuniByIdProvincia(this.verbale.provincia.id);
          this.loadRiferimentiNormativiByIdEnte(
            this.verbale.enteRiferimentiNormativi.id
          );
          this.scrollEnable = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.loaded = true;
            this.manageMessage(err);
          }
          this.logger.error("Errore nel recupero verbale");
          this.scrollEnable = true;
        }
      );
  }
  scrollEnable: boolean;
  salvaAllegato(nuovoAllegato: SalvaAllegatoVerbaleRequest) {
    this.nuovoAllegato = nuovoAllegato;
  }

  goToVerbaleSoggetto() {
    this.router.navigateByUrl(Routing.VERBALE_SOGGETTO + this.idVerbale);
  }

  goToVerbaleRiepilogo() {
    this.router.navigateByUrl(Routing.VERBALE_RIEPILOGO + this.idVerbale);
  }

  goToVerbaleAllegato() {
    this.router.navigateByUrl(Routing.VERBALE_ALLEGATO + this.idVerbale);
  }
  creaFascicolo() {
    // primo click dati verbale
    if (!this.showVerbale) {
      this.configRicercaProtocollo = this.configSharedService.getConfigRicercaProtocollo(
        false
      );
      this.showVerbale = true;
      return;
    }
    // secondo click dati localita
    if (!this.showLocalita) {
      this.loaded = false;
      this.verbale.indirizzo = "";
      this.verbale.provincia = new ProvinciaVO();
      this.subscribers.salvaVerbale = this.pregressoVerbaleService
        .checkDatiVerbale(this.verbale)
        .subscribe(
          (data) => {
            if (
              this.isAfter(
                this.verbale.dataOraViolazione,
                this.verbale.dataOraAccertamento
              )
            ){
              this.manageMessageSuccess(
                "Il Fascicolo è stato salvato, ma la Data e ora processo verbale sono successive alla Data e ora accertamento",
                "WARNING"
              );
            }
            this.verbale.regione = {
              denominazione: "PIEMONTE",
              id: 1,
            };
            this.changeRegione(this.verbale.regione.id);
            this.loaded = true;
            this.showLocalita = true;
          },
          (err) => {
            if (err instanceof ExceptionVO) {
              this.manageMessage(err);
            }
            this.logger.error("Errore nel check dei dati del verbale");
            this.loaded = true;
          }
        );
      return;
    }
    // terzo click riferimenti normativi
    if (!this.showRiferimentiNormativi) {
      let newIndex = this.verbale.riferimentiNormativi.length;
      if (this.verbale.enteRiferimentiNormativi){
        this.loadAmbitiByIdEnte(
          newIndex,
          this.verbale.enteRiferimentiNormativi.id
        );
      }
      this.showRiferimentiNormativi = true;
      return;
    }

    //controllo che i riferimenti normativi siano validi
    if (!this.validaRiferimenti()) {
      this.manageMessageWarning(this.rifNormMessage);
      return;
    }
    //elimino i riferimenti rimossi
    this.cleanRiferimentiNormativi();
    this.loaded = false;
    let goToDettaglio = !this.idVerbale ? false : true;
    this.subscribers.salvaVerbale = this.pregressoVerbaleService
      .salvaVerbale(this.verbale)
      .subscribe(
        (data) => {
          this.verbale.id = data;
          this.idVerbale = data;
          if (this.showSoggetto && goToDettaglio) {
            this._confirmAggiungiAllegati();
          } else {
            this.salvaAllegatoACTA(goToDettaglio);
          }
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.loaded = true;
            this.manageMessage(err);
          }
          this.logger.error("Errore nel salvataggio del verbale");
          this.scrollEnable = true;
          this.nuovoAllegato = null;
        }
      );
  }

  salvaAllegatoACTA(goToDettaglio: boolean) {
    let saveRequest: SalvaAllegatoProtocollatoVerbaleRequest;
    saveRequest = new SalvaAllegatoProtocollatoVerbaleRequest(this.idVerbale);
    let documentoProtocollato: DocumentoProtocollatoVO = {
      ...this.dataRicercaProtocolloSelected,
    };
    const filenameTmp: any = documentoProtocollato.filename;
    if (filenameTmp.nomeFile) {
      documentoProtocollato.filename = filenameTmp.nomeFile;
    }
    saveRequest.allegati = [];
    this.validsAllegatoMetadata.forEach((value, index) => {
      let allegato: SalvaAllegatoProtocollatoVerbaleRequestAllegato = {
        idTipoAllegato: <number>value.idTipo,
      };
      if (value.metaDataModel) {
        allegato.allegatoField = value.metaDataModel;
      }
      saveRequest.allegati[value.current] = allegato;
    });
    saveRequest.documentoProtocollato = documentoProtocollato;
    this._saveRequestAllegatoACTA(saveRequest);
  }

  _confirmAggiungiAllegati() {
    /* genera messaggio */
    this.subLinks = new Array<any>();
    this.subMessagess = new Array<string>();
    let incipit: string =
      "Si vuole continuare con il recupero della documentazione individuata tramite il protocollo " +
      this.fascicoloService.searchFormRicercaProtocol +
      "?";
    this.subMessagess.push(incipit);

    this.buttonConfirmTexts = "Si";
    this.buttonAnnullaTexts = "No";

    //mostro un messaggio
    this.sharedDialog.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
        this.fascicoloService.ref = Routing.PREGRESSO_ALLEGATO + this.idVerbale;
        this.fascicoloService.successPage =
          Routing.PREGRESSO_RIEPILOGO + this.idVerbale;
          this.fascicoloService.backPage =  Routing.PREGRESSO_DATI + this.idVerbale;
        this.router.navigateByUrl(
          Routing.FASCICOLO_ALLEGATO_DA_ACTA + this.idVerbale
        );
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialog.closeAction.subscribe(
      (data) => {
        this.subMessagess = new Array<string>();
        this.subLinks = new Array<any>();
        this.router.navigateByUrl(Routing.PREGRESSO_DATI + this.idVerbale);
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }


  _saveRequestAllegatoACTA(
    saveRequest: SalvaAllegatoProtocollatoVerbaleRequest
  ) {
    let call;
    call = this.pregressoVerbaleService.salvaAllegatoProtocollato(
      <SalvaAllegatoProtocollatoVerbaleRequest>saveRequest
    );

    this.loaded = false;
    return call.subscribe(
      (data) => {
        this.loaded = true;
        const msgOk =
          data && data.msg ? data.msg : "Documento salvato correttamente";
        this._messageSave(msgOk);
        this.showSoggetto = true;
      },
      (err) => {
        this.loaded = true;
        this.logger.error("Errore nel recupero degli allegati");
        this._messageSave("Errore nel salvataggio");
      }
    );
  }

  _messageSave(message: string) {

    /* genera messaggio */
    this.subLinks = new Array<any>();
    this.subMessagess = new Array<string>();

    this.subMessagess.push(message);

    this.buttonConfirmTexts = "Ok";
    this.buttonAnnullaTexts = "";

    // mostro messaggio
    this.sharedDialog.open();

    // unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "xclose");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");

    // premo Conferma
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
       this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();

      },
      (err) => {
        this.logger.error(err);
      }
    );

    // premo Annulla
    this.subscribers.xclose = this.sharedDialog.XAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  validaRiferimenti(): boolean {
    let flag: boolean = true;
    let i: number,
      j: number,
      lenght: number = this.verbale.riferimentiNormativi.length;
    for (i = 0; flag && i < lenght - 1; i++) {
      for (j = i + 1; this.isVisible[i] && flag && j < lenght; j++) {
        if (
          this.isVisible[j] &&
          this.verbale.riferimentiNormativi[i].ambito.id ==
            this.verbale.riferimentiNormativi[j].ambito.id &&
          this.verbale.riferimentiNormativi[i].norma.id ==
            this.verbale.riferimentiNormativi[j].norma.id &&
          this.verbale.riferimentiNormativi[i].articolo.id ==
            this.verbale.riferimentiNormativi[j].articolo.id &&
          this.verbale.riferimentiNormativi[i].comma.id ==
            this.verbale.riferimentiNormativi[j].comma.id &&
          this.verbale.riferimentiNormativi[i].lettera.id ==
            this.verbale.riferimentiNormativi[j].lettera.id
        ){
          flag = false;
        }
      }
    }
    return flag;
  }

  cleanRiferimentiNormativi() {
    let i: number,
      lenght: number = this.verbale.riferimentiNormativi.length;
    let arr: Array<RiferimentiNormativiVO> = new Array<RiferimentiNormativiVO>();
    for (i = 0; i < lenght; i++) {
      if (this.isVisible[i]) {
        arr.push(this.verbale.riferimentiNormativi[i]);
      }
    }
    this.verbale.riferimentiNormativi = arr;
  }

  //Messaggio conferma eliminazione
  public subMessages: Array<string>;
  public buttonAnnullaText: string;
  public buttonConfirmText: string;

  eliminaFascicolo() {
    this.generaMessaggio();
    this.buttonConfirmText = "Conferma";
    this.buttonAnnullaText = "Annulla";
    // mostro messaggio
    this.sharedDialog.open();
    // unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");
    // premo Conferma
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
        this.loaded = false;
        this.subscribers.eliminaVerbale = this.verbaleService
          .eliminaVerbale(this.idVerbale)
          .subscribe(
            (data) => {
              this.router.navigateByUrl(Routing.VERBALE_ELIMINAZIONE);
            },
            (err) => {
              if (err instanceof ExceptionVO) {
                this.loaded = true;
                this.manageMessage(err);
                this.logger.error("Errore nell'eliminazione del verbale");
              }
            }
          );
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialog.closeAction.subscribe(
      (data) => {
        this.subMessages = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }

  generaMessaggio() {
    this.subMessages = new Array<string>();
    let incipit: string = "Si intende eliminare il seguente fascicolo?";
    this.subMessages.push(incipit);
    this.subMessages.push("Numero verbale: " + this.verbale.numero);
    this.subMessages.push(      "Data accertamento: " + this.verbale.dataOraAccertamento    );
  }

  getProvinceComuniByAccertamentoDate(
    isChangedDataOraAccertamento: boolean
  ): void {
    if (      this.verbale.regione &&      this.verbale.regione.id &&      isChangedDataOraAccertamento    ) {
      this.loadProvinceByIdRegione(this.verbale.regione.id);
    }
  }

  loadRegioni() {
    this.loadedRegioni = false;
    this.subscribers.regioni = this.luoghiService.getRegioni().subscribe(
      (data) => {
        this.loadedRegioni = true;
        this.loadedComuni = true;
        this.loadedProvince = true;
        this.regioneModel = data;
      },
      (err) => {
        this.logger.error("Errore nel recupero delle regioni");
      }
    );
  }

  loadProvinceByIdRegione(idRegione: number) {
    this.loadedProvince = false;
    if (this.verbale.dataOraAccertamento) {
      this.subscribers.provinceByIdRegione = this.luoghiService
        .getProvinciaByIdRegioneDate(
          idRegione,
          this.verbale.dataOraAccertamento
        )
        .subscribe(
          (data) => {
            if ( !this.provinciaModel || this.provinciaModel.length === 0 || JSON.stringify(this.provinciaModel.sort()) !== JSON.stringify(data.sort())) {
              this.provinciaModel = data;
              this.verbale.comune = undefined;
              this.verbale.provincia = undefined;
            }
            this.loadedProvince = true;
          },
          (err) => {
            this.logger.error("Errore nel recupero delle province");
          }
        );
    } else {
      this.comuneModel = [];
      this.provinciaModel = [];
      this.verbale.provincia = undefined;
      this.loadedProvince = true;
      this.verbale.comune = undefined;
    }
  }
  comuniEnteValidInDate() {
    this.subscribers.comuniEnteValidInDate = this.luoghiService
      .getcomuniEnteValidInDate()
      .subscribe(
        (data) => {
          this.comuneEnteModel = data;
        },
        (err) => {
          this.logger.error("Errore nel recupero dei comuni Ente");
        }
      );
  }


  //chiamata solo nel modifica
  loadRiferimentiNormativiByIdEnte(idEnte: number) {
    let i: number,
      lenght: number = this.verbale.riferimentiNormativi.length;
    if (lenght == 0) this.nuovoRiferimentoNormativo();
    for (i = 0; i < lenght; i++) {
      this.isVisible[i] = true;
      this.loadAmbitiByIdEnte(i, idEnte);
      this.loadNormeByIdAmbitoAndIdEnte(
        i,
        this.verbale.riferimentiNormativi[i].ambito.id,
        idEnte
      );
      this.loadArticoliByIdNormaAndIdEnte(
        i,
        this.verbale.riferimentiNormativi[i].norma.id,
        idEnte
      );
      this.loadCommaByIdArticolo(
        i,
        this.verbale.riferimentiNormativi[i].articolo.id
      );
      this.loadLettereByIdComma(
        i,
        this.verbale.riferimentiNormativi[i].comma.id
      );
    }
  }

  loadAmbitiByIdEnte(index: number, idEnte: number) {
    this.loadedAmbito[index] = false;
    const call = this.verbale.dataOraAccertamento
      ? this.rifNormativiService.getAmbitiByIdEnte(
          idEnte,
          true,
          true,
          this.verbale.dataOraAccertamento
        )
      : this.rifNormativiService.getAmbitiByIdEnte(idEnte, true);
    this.subscribers.ambitiByIdEnte = call.subscribe(
      (data) => {
        this.ambitoModel[index] = data;
        this.loadedAmbito[index] = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err);
        }
        this.logger.error("Errore nel recupero degli ambiti");
      }
    );
  }
  loadComuniByIdProvincia(idProvincia: number) {
    this.loadedComuni = false;
    this.subscribers.comuniByIdProvince = this.luoghiService
      .getComuneByIdProvinciaDate(idProvincia, this.verbale.dataOraAccertamento)
      .subscribe(
        (data) => {
          this.comuneModel = data;
          this.loadedComuni = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero dei comuni");
        }
      );
  }
  loadEnti(listEnteAccertatore: Array<EnteVO>, listEnteLegge: Array<EnteVO>) {
    this.singoloEnte = false;
    this.enteAccertatoreModel = listEnteAccertatore;
    this.enteModel = listEnteLegge;
    if (listEnteLegge.length == 1) {
      this.singoloEnte = true;
    } else {
      this.loadedAmbito[0] = true;
    }
    this.loadedLettera[0] = true;
    this.loadedNorma[0] = true;
    this.loadedComma[0] = true;
    this.loadedArticolo[0] = true;
  }

  loadArticoliByIdNormaAndIdEnte(index: number, idNorma: number, idEnte) {
    this.loadedArticolo[index] = false;
    const call = this.verbale.dataOraAccertamento
      ? this.rifNormativiService.getArticoliByIdNormaAndIdEnte(
          idNorma,
          idEnte,
          true,
          true,
          this.verbale.dataOraAccertamento
        )
      : this.rifNormativiService.getArticoliByIdNormaAndIdEnte(
          idNorma,
          idEnte,
          true
        );

    this.subscribers.articoliByIdNorma = call.subscribe(
      (data) => {
        this.articoloModel[index] = data;
        this.loadedArticolo[index] = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err);
        }
        this.logger.error("Errore nel recupero degli articoli");
      }
    );
  }

  loadNormeByIdAmbitoAndIdEnte(index: number, idAmbito: number, idEnte) {
    this.loadedNorma[index] = false;
    const call = this.verbale.dataOraAccertamento
      ? this.rifNormativiService.getNormeByIdAmbitoAndIdEnte(
          idAmbito,
          idEnte,
          true,
          true,
          this.verbale.dataOraAccertamento
        )
      : this.rifNormativiService.getNormeByIdAmbitoAndIdEnte(
          idAmbito,
          idEnte,
          true
        );
    this.subscribers.normeByIdAmbito = call.subscribe(
      (data) => {
        this.normaModel[index] = data;
        this.loadedNorma[index] = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err);
        }
        this.logger.error("Errore nel recupero delle norme");
      }
    );
  }

  loadLettereByIdComma(index: number, idComma: number) {
    this.loadedLettera[index] = false;
    const call = this.verbale.dataOraAccertamento
      ? this.rifNormativiService.getLetteraByIdComma(
          idComma,
          true,
          true,
          this.verbale.dataOraAccertamento
        )
      : this.rifNormativiService.getLetteraByIdComma(idComma, true);
    this.subscribers.lettereByIdComma = call.subscribe(
      (data) => {
        this.letteraModel[index] = data;
        this.loadedLettera[index] = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err);
        }
        this.logger.error("Errore nel recupero delle lettere");
      }
    );
  }

  loadCommaByIdArticolo(index: number, idArticolo: number) {
    this.loadedComma[index] = false;
    const call = this.verbale.dataOraAccertamento
      ? this.rifNormativiService.getCommaByIdArticolo(
          idArticolo,
          true,
          true,
          this.verbale.dataOraAccertamento
        )
      : this.rifNormativiService.getCommaByIdArticolo(idArticolo, true);
    this.subscribers.commaByIdArticolo = call.subscribe(
      (data) => {
        this.commaModel[index] = data;
        this.loadedComma[index] = true;
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err);
        }
        this.logger.error("Errore nel recupero dei commi");
      }
    );
  }

  changeRegione(idRegione: number) {
    this.verbale.comune = null;
    this.verbale.provincia = null;
    this.comuneModel = null;
    this.loadProvinceByIdRegione(idRegione);
  }

  changeVerbaleContestato(e) {
    this.verbale.contestato = e;
  }

  changeEnte(idEnte: number) {
    this.verbale.riferimentiNormativi = new Array<RiferimentiNormativiVO>();
    this.verbale.riferimentiNormativi.push(new RiferimentiNormativiVO());
    this.isVisible = [true];
    this.loadAmbitiByIdEnte(0, idEnte);
  }

  changeProvincia(idProvincia: number) {
    this.verbale.comune = null;
    this.loadComuniByIdProvincia(idProvincia);
  }

  changeNormativa(index: number, idNorma: number) {
    this.verbale.riferimentiNormativi[index].comma = null;
    this.letteraModel[index] = null;
    this.verbale.riferimentiNormativi[index].articolo = null;
    this.commaModel[index] = null;
    this.verbale.riferimentiNormativi[index].lettera = null;
    this.loadArticoliByIdNormaAndIdEnte(
      index,
      idNorma,
      this.verbale.enteRiferimentiNormativi.id
    );
  }

  changeAmbito(index: number, idAmbito: number) {
    this.verbale.riferimentiNormativi[index].articolo = null;
    this.verbale.riferimentiNormativi[index].norma = null;
    this.verbale.riferimentiNormativi[index].lettera = null;
    this.verbale.riferimentiNormativi[index].comma = null;
    this.letteraModel[index] = null;
    this.commaModel[index] = null;
    this.loadNormeByIdAmbitoAndIdEnte(
      index,
      idAmbito,
      this.verbale.enteRiferimentiNormativi.id
    );
  }

  changeComma(index: number, idComma: number) {
    this.verbale.riferimentiNormativi[index].lettera = null;
    this.loadLettereByIdComma(index, idComma);
  }

  changeArticolo(index: number, idArticolo: number) {
    this.verbale.riferimentiNormativi[index].lettera = null;
    this.verbale.riferimentiNormativi[index].comma = null;
    this.letteraModel[index] = null;
    this.loadCommaByIdArticolo(index, idArticolo);
  }
  isDisabledProvincia(): boolean {
    return (
      !this.verbale.regione ||
      (this.verbale.regione && !this.verbale.regione.id) ||
      this.provinciaModel.length === 0
    );
  }

  isDisabledRegione(): boolean {
    return !this.verbale.dataOraAccertamento;
  }

  // solo il primo può essere disabled, nel caso più enti e nessuno è stato ancora scelto
  isDisabledAmbito(index: number): boolean {
    return (
      index == 0 &&
      this.enteModel.length > 1 &&
      !this.verbale.enteRiferimentiNormativi
    );
  }

  isDisabledComune(): boolean {
    return (
      !this.verbale.provincia ||
      (this.verbale.provincia && !this.verbale.provincia.id) ||
      this.comuneModel.length === 0
    );
  }

  isDisabledArticolo(index: number): boolean {
    return (
      !this.verbale.riferimentiNormativi[index].norma ||
      (this.verbale.riferimentiNormativi[index].norma &&
        !this.verbale.riferimentiNormativi[index].norma.id)
    );
  }

  isDisabledNorma(index: number): boolean {
    return (
      !this.verbale.riferimentiNormativi[index].ambito ||
      (this.verbale.riferimentiNormativi[index].ambito &&
        !this.verbale.riferimentiNormativi[index].ambito.id)
    );
  }

  isDisabledLettera(index: number): boolean {
    return (
      !this.verbale.riferimentiNormativi[index].comma ||
      ( this.verbale.riferimentiNormativi[index].comma &&
        !this.verbale.riferimentiNormativi[index].comma.id )
    );
  }

  isDisabledComma(index: number): boolean {
    return (
      !this.verbale.riferimentiNormativi[index].articolo ||
      ( this.verbale.riferimentiNormativi[index].articolo &&
        !this.verbale.riferimentiNormativi[index].articolo.id )
    );
  }

  isDisabledSoggetto(): boolean {
    let flag: boolean = false;
    if ( this.showSoggetto && this.idVerbale && this.countSoggetti === 0 ) {
      return true;
    }
    return flag;
  }
  soggettoChange(countSoggetti: number): void {
    this.countSoggetti = countSoggetti;
  }

  isDisabledElimina(): boolean {
    let count: number = 0;
    this.isVisible.forEach( (el) => {
      if (el) count++;
    } );
    return count <= 1;
  }

  isDisabledAggiungi(): boolean {
    let flag: boolean = false;
    let i: number;
    for (i = 0; !flag && i < this.verbale.riferimentiNormativi.length; i++) {
      if (  this.isVisible[i] &&
        (!this.verbale.riferimentiNormativi[i].lettera ||
          (this.verbale.riferimentiNormativi[i].lettera &&
            !this.verbale.riferimentiNormativi[i].lettera.id))
      )
        flag = true;
    }
    if (!this.showRiferimentiNormativi) {
      flag = false;
    }
    if(this.verbale.numero && this.verbale.numero.length>0 && this.verbale.numero.length>50){
      flag=true;
    }
    return flag;
  }

  nuovoRiferimentoNormativo() {
    if (!this.validaRiferimenti()) {
      this.manageMessageWarning(this.rifNormMessage);
      return;
    }
    this.resetMessageWarning();
    let newElement = new RiferimentiNormativiVO();
    Object.defineProperty(newElement, "__id", {
      value: this.uIdCounter++,
      enumerable: false,
    });
    let newIndex = this.verbale.riferimentiNormativi.push(newElement) - 1;
    this.isVisible[newIndex] = true;
    this.loadedComma[newIndex] = true;
    this.loadedArticolo[newIndex] = true;
    this.loadedNorma[newIndex] = true;
    this.loadedLettera[newIndex] = true;
    if (this.verbale.enteRiferimentiNormativi){
      this.loadAmbitiByIdEnte(
        newIndex,
        this.verbale.enteRiferimentiNormativi.id
      );
    }
  }

  isDisabledCreaFascicolo(valid: boolean): boolean {
    return (
      !valid ||
      this.isDisabledAggiungi() ||
      this.isDisabledSoggetto() ||
      this.saveDisabled
    );
  }
  isRiferimentoNormativoDuplicato(index: number): boolean {
    let elemento = this.verbale.riferimentiNormativi[index];
    let countDuplicati = 0;
    for (let i = 0; i < this.verbale.riferimentiNormativi.length; i++) {
      if (
        this.isVisible[i] &&
        elemento.ambito != null &&
        this.verbale.riferimentiNormativi[i].ambito.id == elemento.ambito.id &&
        elemento.norma != null &&
        this.verbale.riferimentiNormativi[i].norma.id == elemento.norma.id &&
        elemento.articolo != null &&
        this.verbale.riferimentiNormativi[i].articolo.id ==
          elemento.articolo.id &&
        elemento.comma != null &&
        this.verbale.riferimentiNormativi[i].comma.id == elemento.comma.id &&
        elemento.lettera != null &&
        this.verbale.riferimentiNormativi[i].lettera.id == elemento.lettera.id
      )
        countDuplicati++;
    }
    return countDuplicati == 2 ? true : false;
  }

  eliminaRiferimentoNormativo(index: number) {
    if (this.isRiferimentoNormativoDuplicato(index)) {
      this.showWarning = false;
    }
    if (this.verbale.riferimentiNormativi.length <= 1) {
      return; // controllo aggiuntivo
    }
    this.isVisible[index] = false;
  }

  manageDatePicker(event: any, i: number) {
    var str: string = "#datetimepicker" + i.toString();
    if ($(str).length) {
      $(str).datetimepicker({
        format: "DD/MM/YYYY, HH:mm",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
      });
    }
    if (event != null) {
      switch (i) {
        case 1:
          this.verbale.dataOraViolazione = event.srcElement.value;
          break;
        case 2:
          const isChangedDataOraAccertamento =
          this.verbale.dataOraAccertamento !== event.srcElement.value;
          this.verbale.dataOraAccertamento = event.srcElement.value;
          this.verbale.dataOraViolazione = this.verbale.dataOraAccertamento
          break;
      }
    }
  }

  //restituisce true se la dataOra1 è successiva alla dataOra2
  isAfter(dataOra1: string, dataOra2: string): boolean {
    let YYYY1, YYYY2, MM1, MM2, DD1, DD2, HH1, HH2, mm1, mm2: string;
    YYYY2 = dataOra2.substring(6, 10);
    YYYY1 = dataOra1.substring(6, 10);

    if (YYYY1 > YYYY2) {
      return true;
    } else if (YYYY1 < YYYY2) {
      return false;
    } else {
      MM2 = dataOra2.substring(3, 5);
      MM1 = dataOra1.substring(3, 5);
      if (MM1 > MM2) {
        return true;
      } else if (MM1 < MM2) {
        return false;
      } else {
        DD2 = dataOra2.substring(0, 2);
        DD1 = dataOra1.substring(0, 2);
        if (DD1 > DD2) {
          return true;
        } else if (DD1 < DD2) {
          return false;
        } else {
          HH2 = dataOra2.substring(12, 14);
          HH1 = dataOra1.substring(12, 14);
          if (HH1 > HH2) {
            return true;
          } else if (HH1 < HH2) {
            return false;
          } else {
            mm2 = dataOra2.substring(15, 17);
            mm1 = dataOra1.substring(15, 17);
            if (mm1 > mm2) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  trackByFn(index, item) {
    return item.__id;
  }

  isKeyPressed(code: number): boolean {
    return this.numberUtilsSharedService.numberValid(code);
  }

  ngAfterViewChecked() {
    let i: number;
    for (i = 1; i < 3; i++) this.manageDatePicker(null, i);
    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  pageChange(page:number){
    let ricercaProtocolloRequest:RicercaProtocolloRequest = new RicercaProtocolloRequest();
    ricercaProtocolloRequest.pageRequest = page;
    ricercaProtocolloRequest.numeroProtocollo = this.searchFormRicercaProtocol;
    this.ricercaProtocollo(ricercaProtocolloRequest);
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  ngOnDestroy(): void {
    this.logger.destroy(PregressoInserimentoActaComponent.name);
  }
  ricercaProtocollo(ricerca: RicercaProtocolloRequest) {
    ricerca.flagPregresso = true;
    this.loaded = false;
    this.sharedVerbaleService.ricercaProtocolloSuACTA(ricerca).subscribe(
      (data) => {
        let tipiAllegatoDuplicabili = [];

        if (
          data.documentoProtocollatoVOList &&
          data.documentoProtocollatoVOList.length > 0
        ) {
          if (data.documentoProtocollatoVOList[0]) {
            tipiAllegatoDuplicabili =
              data.documentoProtocollatoVOList[0].tipiAllegatoDuplicabili;
          }
          data.documentoProtocollatoVOList.forEach((all) => {
            all.filename = new MyUrl(<string>all.filename, null);
          });
        } else {
          data.documentoProtocollatoVOList = [];
        }

        if (data.messaggio) {
          this.manageMessage(data.messaggio);
        }

        const numpages:number = Math.ceil(+data.totalLineResp/+data.maxLineReq);
        this.fascicoloService.dataRicercaProtocolloNumPages = numpages;
        this.numPages = numpages;
        this.currentPage = +data.pageResp;
        this.numResults  = +data.totalLineResp;

        this.fascicoloService.categoriesDuplicated = tipiAllegatoDuplicabili;
        this.loaded = true;
        this.fascicoloService.searchFormRicercaProtocol =
          ricerca.numeroProtocollo;
        this.searchFormRicercaProtocol = this.fascicoloService.searchFormRicercaProtocol;
        this.dataRicercaProtocollo = data.documentoProtocollatoVOList;
        // r e s e t sezione assegna documenti
        this._resetCategories();
      },
      (err) => {
        if (err instanceof ExceptionVO) {
          this.manageMessage(err);
        }
        this.loaded = true;
        this.logger.error("Errore recupero allegati");
      }
    );
  }

  onNumberChange(v: number): void {
    this._checkSave();
  }
  _resetCategories() {
    this.numberDocument = this.fascicoloService.setNumberDocument();
    // forzo readonly in pagina
    this.numberDocument.readonly = true;
    this.saveDisabled = true;
    this.dataRicercaProtocolloSelected = null;
    this.validsAllegatoMetadata = [];
  }
  _checkSave() {
    if (
      this.validsAllegatoMetadata.length !== this.numberDocument.value ||
      this.validsAllegatoMetadata.find((item: any) => item.valid === false)
    ) {
      this.saveDisabled = true;
    } else {
      this.saveDisabled = false;
    }
  }
  onValidAllegatoMetadati(event: any) {
    if ( this.validsAllegatoMetadata.find((item: any) => item.current === event.current)) {
      this.validsAllegatoMetadata = this.validsAllegatoMetadata.map((item) => {
        return item.current === event.current ? event : item;
      });
    } else {
      this.validsAllegatoMetadata.push(event);
    }
    this._checkSave();
  }

  getAllegato(el: DocumentoProtocollatoVO) {
    const myFilename =
      typeof el.filename == "string" ? el.filename : el.filename.nomeFile;
    if (myFilename.toLowerCase().startsWith("documento multiplo")) {
      this.loaded = false;
      this.allegatoSharedService
        .getDocFisiciByObjectIdDocumento(el.objectIdDocumento)
        .subscribe((data) => {
          this.loaded = true;
          this.openAllegatoMultiplo(data);
        });
    } else {
      this.openAllegato(el);
    }
  }
  openAllegatoMultiplo(els: any[]) {
    this.subMessagess = new Array<string>();

    this.subLinks = new Array<any>();

    els.forEach((item) => {
      this.subLinks.push({ ...item, label: item.nomeFile });
    });

    this.buttonAnnullaTexts = "";
    this.buttonConfirmTexts = "Ok";

    // mostro messaggio
    this.sharedDialog.open();

    // unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "xclose");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");

    // premo Conferma
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
    // premo Annulla
    this.subscribers.xclose = this.sharedDialog.XAction.subscribe(
      (data) => {
        this.subLinks = new Array<any>();
        this.subMessagess = new Array<string>();
      },
      (err) => {
        this.logger.error(err);
      }
    );
  }
  onSelected(el: DocumentoProtocollatoVO) {
    this.dataRicercaProtocolloSelected = el;
  }

  openAllegatoFisico(el: any) {
    const donwloadTypes = ["tiff", "p7m"];
    const myFilename = el.nomeFile;
    const ext = myFilename.split(".").pop();

    const winUrl = URL.createObjectURL(
      new Blob([winLoadingHtml], { type: "text/html" })
    );
    var win;
    if (donwloadTypes.indexOf(ext) == -1) {      win = window.open(winUrl);    }
    else {      this.loaded = false;    }
    this.allegatoSharedService
      .getAllegatoByObjectIdDocumentoFisico(el.objectIdDocumentoFisico)
      .subscribe((data) => {
        if (donwloadTypes.indexOf(ext) > -1) {
          this.loaded = true;
          saveAs(data, myFilename || "Unknown");
        } else {
          let myBlob: any = data;
          if(myBlob.type=='application/json'){
            win.close();
            myBlob.text().then(text => {
              this.manageMessage(JSON.parse(text));
            });
          }else{
            myBlob.name = myFilename;
            var fileURL = URL.createObjectURL(myBlob);
            win.location.href = fileURL;
          }
        }
      });
  }
  modelChangeFn( value: string ){
    this.verbale.dataOraViolazione = value
  }
  openAllegato( el: DocumentoProtocollatoVO ) {
    const donwloadTypes = ["tiff", "p7m"];
    const myFilename = typeof el.filename == "string" ? el.filename : el.filename.nomeFile;
    const ext = myFilename.split(".").pop();
    const winUrl = URL.createObjectURL(
      new Blob([winLoadingHtml], { type: "text/html" })
    );
    var win;
    if (donwloadTypes.indexOf(ext) == -1) {
      win = window.open(winUrl);
    } else {
      this.loaded = false;
    }
    this.allegatoSharedService
      .getAllegatoByParolaChiave(el.objectIdDocumento)
      .subscribe((data) => {
        if (donwloadTypes.indexOf(ext) > -1) {
          this.loaded = true;
          saveAs(data, myFilename || "Unknown");
        } else {
          let myBlob: any = data;
          if(myBlob.type=='application/json'){
            win.close();
            myBlob.text().then(text => {
              this.manageMessage(JSON.parse(text));
            });
          }else{
            myBlob.name = myFilename;
            var fileURL = URL.createObjectURL(myBlob);
            win.location.href = fileURL;
          }
        }
      });
  }

}
