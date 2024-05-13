import { Component, OnInit, OnDestroy, Inject, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import {  RegioneVO,  ProvinciaVO,  ComuneVO,  NormaVO,  ArticoloVO,  CommaVO,  LetteraVO,
  EnteVO,  SelectVO,  AmbitoVO,} from "../../../commons/vo/select-vo";
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
import { RicercaProtocolloRequest } from "../../../commons/request/verbale/ricerca-protocollo-request";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { FascicoloService } from "../../../fascicolo/services/fascicolo.service";
import { MessageVO } from "../../../commons/vo/messageVO";

declare var $: any;

@Component({
  selector: "pregresso-inserimento",
  styleUrls: ["./pregresso-inserimento.component.scss"],
  templateUrl: "./pregresso-inserimento.component.html",
})
export class PregressoInserimentoComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

  public loadedComuni: boolean;
  public loadedProvince: boolean;
  public loaded: boolean;
  public loadedRegioni: boolean;

  public verbale: VerbaleVO;

  public loadedLettera: Array<boolean> = [];
  public singoloEnte: boolean;
  public loadedAmbito: Array<boolean> = [];
  public loadedComma: Array<boolean> = [];
  public loadedNorma: Array<boolean> = [];
  public loadedArticolo: Array<boolean> = [];

  public isVisible: Array<boolean> = [true];

  public subscribers: any = {};

  public enteModel: Array<EnteVO>;
  public letteraModel: Array<Array<LetteraVO>> = [];
  public normaModel: Array<Array<NormaVO>> = [];
  public enteAccertatoreModel: Array<EnteVO>;
  public commaModel: Array<Array<CommaVO>> = [];
  public ambitoModel: Array<Array<AmbitoVO>> = [];
  public articoloModel: Array<Array<ArticoloVO>> = [];

  public regioneModel: Array<RegioneVO>;
  public provinciaModel: Array<ProvinciaVO>;
  public comuneModel: Array<ComuneVO>;

  public rifNormMessage: string =  "Impossibile inserire più Riferimenti Normativi identici";

  private uIdCounter: number = 0;

  public idVerbale: number;

  // Messaggio top
  public messageTop: String;
  public showMessageTop: boolean;
  public typeMessageTop: String;


  public tabInserimento: boolean = false;
  public tabRicerca: boolean = false;

  private intervalIdW: number = 0;
  private intervalIdS: number = 0;

  public numeroProtocollo: string;

  // warning meta pagina
  public messageWarning: string;
  public showWarning: boolean;
  public typeWarning: string;


  tabActions: any = {
    changeRicerca: () => {
      this.tabRicerca = !this.tabRicerca;
      if (!this.tabRicerca) {        this.tabInserimento = true;
      } else {        this.tabInserimento = false;
      }
    },
    changeInserimento: () => {
      this.tabInserimento = !this.tabInserimento;
      if (!this.tabInserimento) {        this.tabRicerca = true;
      } else {        this.tabRicerca = false;
      }
    },
  };

  constructor(
    private router: Router,
    private logger: LoggerService,
    private luoghiService: LuoghiService,
    private rifNormativiService: RifNormativiService,
    private activatedRoute: ActivatedRoute,
    private verbaleService: VerbaleService,
    private numberUtilsSharedService: NumberUtilsSharedService,
    private userService: UserService,
    private utilSubscribersService: UtilSubscribersService,
    private sharedVerbaleService: SharedVerbaleService,
    private fascicoloService: FascicoloService,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    this.logger.init(PregressoInserimentoComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      let verbString = params["id"];
      if (!verbString || isNaN(+verbString)) {
        this.idVerbale = null;
      }
      else {
        this.idVerbale = +verbString;
      }
      this.subscribers.userProfile = this.userService.profilo$.subscribe(
        (data) => {
          if (data != null) {
            this.loaded = false;
            if (              this.activatedRoute.snapshot.paramMap.get("action") == "creazione"            ) {
              this.manageMessageSuccess(                "Fascicolo creato con successo",                "SUCCESS"              );
            }
            if (              this.activatedRoute.snapshot.paramMap.get("action") ==              "salvato_con_warning"            ) {
              this.manageMessageSuccess(
                "Il Fascicolo è stato salvato, ma la Data e ora processo verbale sono successive alla Data e ora accertamento",
                "WARNING"
              );
            }
            this.loadRegioni();
            this.loadEnti(data.entiAccertatore, data.entiLegge);

            if (!this.idVerbale) {              this.nuovoVerbale();
            } else {              this.modificaVerbale();
            }
          }
        }
      );
    });
  }

  manageMessageSuccess(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
  }


  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 30; //this.configService.getTimeoutMessagge();
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {        this.resetMessageTop();
      }
    }, 1000);
  }

  manageMessageWarning(message: string) {
    this.typeWarning = "WARNING";
    this.messageWarning = message;
    this.timerShowMessageWarning();
  }

  timerShowMessageWarning() {
    this.showWarning = true;
    let seconds: number = 50; //this.configService.getTimeoutMessagge();
    this.intervalIdW = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {        this.resetMessageWarning();
      }
    }, 1000);
  }

  manageMessage(mess: ExceptionVO | MessageVO) {
    this.typeMessageTop = mess.type;
    this.messageTop = mess.message;
    this.timerShowMessageTop();
  }

  resetMessageTop() {
    this.typeMessageTop = null;
    this.showMessageTop = false;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
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
          if (err instanceof ExceptionVO) {            this.loaded = true;            this.manageMessage(err);          }
          this.scrollEnable = true;
          this.logger.error("Errore nel recupero verbale");
        }
      );
  }
  scrollEnable: boolean;
  nuovoVerbale() {
    this.verbale = new VerbaleVO();
    this.verbale.contestato = false;
    if (this.enteModel.length == 1){
      this.verbale.enteRiferimentiNormativi = this.enteModel[0];
    }
    this.nuovoRiferimentoNormativo();
    this.loaded = true;
  }

  goToVerbaleRiepilogo() {    this.router.navigateByUrl(Routing.VERBALE_RIEPILOGO + this.idVerbale);  }

  goToVerbaleSoggetto() {    this.router.navigateByUrl(Routing.VERBALE_SOGGETTO + this.idVerbale);  }
  creaFascicolo() {
    //controllo che i riferimenti normativi siano validi
    if (!this.validaRiferimenti()) {
      this.manageMessageWarning(this.rifNormMessage);
      return;
    }
    //elimino i riferimenti rimossi
    this.cleanRiferimentiNormativi();
    this.loaded = false;
    this.subscribers.salvaVerbale = this.verbaleService
      .salvaVerbale(this.verbale)
      .subscribe(
        (data) => {
          let azione: string;
          if (!this.idVerbale) {
            this.idVerbale = data;
            if (
              this.isAfter(
                this.verbale.dataOraViolazione,
                this.verbale.dataOraAccertamento
              )
            )
              azione = "salvato_con_warning";
            else azione = "creazione";
            this.router.navigate(
              [Routing.VERBALE_DATI + this.idVerbale, { action: azione }],
              { replaceUrl: true }
            );
          } else {
            this.idVerbale = data;
            if (
              this.isAfter(                this.verbale.dataOraViolazione,                this.verbale.dataOraAccertamento              )
            ){
              azione = "salvato_con_warning";
            } else {
              azione = "modifica";
            }
            if (azione == "modifica"){
              this.manageMessageSuccess(
                "Fascicolo modificato con successo",
                "SUCCESS"
              );
            }
            if (azione == "salvato_con_warning"){
              this.manageMessageSuccess(
                "Il Fascicolo è stato salvato, ma la Data e ora processo verbale sono successive alla Data e ora accertamento",
                "WARNING"
              );
            }
            this.modificaVerbale();
          }
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.loaded = true;
            this.manageMessage(err);
          }
          this.logger.error("Errore nel salvataggio del verbale");
          this.scrollEnable = true;
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

  goToVerbaleAllegato() {    this.router.navigateByUrl(Routing.VERBALE_ALLEGATO + this.idVerbale);
  }
  // Messaggio conferma elimina
  public buttonAnnullaText: string;
  public subMessages: Array<string>;
  public buttonConfirmText: string;

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


  eliminaFascicolo() {
    this.generaMessaggio();
    this.buttonConfirmText = "Conferma";
    this.buttonAnnullaText = "Annulla";

    // messaggio
    this.sharedDialog.open();

    // unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");

    //"Conferma"
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

    // "Annulla"
    this.subscribers.close = this.sharedDialog.closeAction.subscribe(
      (data) => {        this.subMessages = new Array<string>();      },
      (err) => {        this.logger.error(err);      }
    );
  }

  generaMessaggio() {
    this.subMessages = new Array<string>();
    let incipit: string = "Si intende eliminare il seguente fascicolo?";
    this.subMessages.push(incipit);
    this.subMessages.push("Numero verbale: " + this.verbale.numero);
    this.subMessages.push(      "Data accertamento: " + this.verbale.dataOraAccertamento    );
  }
  loadProvinceByIdRegione(idRegione: number) {
    this.loadedProvince = false;
    this.subscribers.provinceByIdRegione = this.luoghiService
      .getProvinciaByIdRegione(idRegione)
      .subscribe(
        (data) => {
          this.provinciaModel = data;
          this.loadedProvince = true;
        },
        (err) => {          this.logger.error("Errore nel recupero delle province");
        }
      );
  }

  loadRegioni() {
    this.loadedRegioni = false;
    this.subscribers.regioni = this.luoghiService.getRegioni().subscribe(
      (data) => {
        this.loadedProvince = true;
        this.loadedComuni = true;
        this.regioneModel = data;
        this.loadedRegioni = true;
      },
      (err) => {        this.logger.error("Errore nel recupero delle regioni");
      }
    );
  }


  // solo nel modifica
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

  loadComuniByIdProvincia(idProvincia: number) {
    this.loadedComuni = false;
    this.subscribers.comuniByIdProvince = this.luoghiService
      .getComuneByIdProvincia(idProvincia)
      .subscribe(
        (data) => {
          this.comuneModel = data;
          this.loadedComuni = true;
        },
        (err) => {          this.logger.error("Errore nel recupero dei comuni");        }
      );
  }

  loadAmbitiByIdEnte(index: number, idEnte: number) {
    this.loadedAmbito[index] = false;
    this.subscribers.ambitiByIdEnte = this.rifNormativiService
      .getAmbitiByIdEnte(idEnte, true)
      .subscribe(
        (data) => {
          this.ambitoModel[index] = data;
          this.loadedAmbito[index] = true;
        },
        (err) => {          this.logger.error("Errore nel recupero degli ambiti");
        }
      );
  }


  loadArticoliByIdNormaAndIdEnte(index: number, idNorma: number, idEnte) {
    this.loadedArticolo[index] = false;
    this.subscribers.articoliByIdNorma = this.rifNormativiService
      .getArticoliByIdNormaAndIdEnte(idNorma, idEnte, true)
      .subscribe(
        (data) => {
          this.articoloModel[index] = data;
          this.loadedArticolo[index] = true;
        },
        (err) => {          this.logger.error("Errore nel recupero degli articoli");        }
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
    this.loadedArticolo[0] = true;
    this.loadedComma[0] = true;
    this.loadedNorma[0] = true;
    this.loadedLettera[0] = true;
  }


  loadLettereByIdComma(index: number, idComma: number) {
    this.loadedLettera[index] = false;
    this.subscribers.lettereByIdComma = this.rifNormativiService
      .getLetteraByIdComma(idComma, true)
      .subscribe(
        (data) => {
          this.letteraModel[index] = data;
          this.loadedLettera[index] = true;
        },
        (err) => {          this.logger.error("Errore nel recupero delle lettere");
        }
      );
  }

  loadNormeByIdAmbitoAndIdEnte(index: number, idAmbito: number, idEnte) {
    this.loadedNorma[index] = false;
    this.subscribers.normeByIdAmbito = this.rifNormativiService
      .getNormeByIdAmbitoAndIdEnte(idAmbito, idEnte, true)
      .subscribe(
        (data) => {
          this.normaModel[index] = data;
          this.loadedNorma[index] = true;
        },
        (err) => {          this.logger.error("Errore nel recupero delle norme");        }
      );
  }

  loadCommaByIdArticolo(index: number, idArticolo: number) {
    this.loadedComma[index] = false;
    this.subscribers.commaByIdArticolo = this.rifNormativiService
      .getCommaByIdArticolo(idArticolo, true)
      .subscribe(
        (data) => {
          this.commaModel[index] = data;
          this.loadedComma[index] = true;
        },
        (err) => {          this.logger.error("Errore nel recupero dei commi");
        }
      );
  }

  changeProvincia(idProvincia: number) {
    this.verbale.comune = null;
    this.loadComuniByIdProvincia(idProvincia);
  }

  changeVerbaleContestato(e) {
    this.verbale.contestato = e;
  }

  changeRegione(idRegione: number) {
    this.verbale.provincia = null;
    this.verbale.comune = null;
    this.comuneModel = null;
    this.loadProvinceByIdRegione(idRegione);
  }

  changeEnte(idEnte: number) {
    this.verbale.riferimentiNormativi = new Array<RiferimentiNormativiVO>();
    this.verbale.riferimentiNormativi.push(new RiferimentiNormativiVO());
    this.isVisible = [true];
    this.loadAmbitiByIdEnte(0, idEnte);
  }

  changeNormativa(index: number, idNorma: number) {
    this.verbale.riferimentiNormativi[index].lettera = null;
    this.verbale.riferimentiNormativi[index].articolo = null;
    this.verbale.riferimentiNormativi[index].comma = null;
    this.letteraModel[index] = null;
    this.commaModel[index] = null;
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
    this.commaModel[index] = null;
    this.verbale.riferimentiNormativi[index].comma = null;
    this.letteraModel[index] = null;
    this.loadNormeByIdAmbitoAndIdEnte(
      index,
      idAmbito,
      this.verbale.enteRiferimentiNormativi.id
    );
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
      (this.verbale.regione && !this.verbale.regione.id)
    );
  }

  // solo il primo disabled, se più enti e nessuno è stato ancora scelto
  isDisabledAmbito(index: number): boolean {
    return (
      index == 0 &&
      this.enteModel.length > 1 &&
      !this.verbale.enteRiferimentiNormativi
    );
  }

  changeComma(index: number, idComma: number) {
    this.verbale.riferimentiNormativi[index].lettera = null;
    this.loadLettereByIdComma(index, idComma);
  }

  isDisabledComune(): boolean {
    return (
      !this.verbale.provincia ||
      (this.verbale.provincia && !this.verbale.provincia.id)
    );
  }


  isDisabledComma(index: number): boolean {
    return (
      !this.verbale.riferimentiNormativi[index].articolo ||
      (this.verbale.riferimentiNormativi[index].articolo &&
        !this.verbale.riferimentiNormativi[index].articolo.id)
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
      (this.verbale.riferimentiNormativi[index].comma &&
        !this.verbale.riferimentiNormativi[index].comma.id)
    );
  }

  isDisabledArticolo(index: number): boolean {
    return (
      !this.verbale.riferimentiNormativi[index].norma ||
      (this.verbale.riferimentiNormativi[index].norma &&
        !this.verbale.riferimentiNormativi[index].norma.id)
    );
  }

  isDisabledAggiungi(): boolean {
    let flag: boolean = false;
    let i: number;
    for (i = 0; !flag && i < this.verbale.riferimentiNormativi.length; i++) {
      if (
        this.isVisible[i] &&
        (!this.verbale.riferimentiNormativi[i].lettera ||
          (this.verbale.riferimentiNormativi[i].lettera &&
            !this.verbale.riferimentiNormativi[i].lettera.id))
      )
        flag = true;
    }
    return flag;
  }

  isDisabledCreaFascicolo(valid: boolean): boolean {    return !valid || this.isDisabledAggiungi();  }

  isDisabledElimina(): boolean {
    let count: number = 0;
    this.isVisible.forEach((el) => {
      if (el) { count++; }
    });
    return count <= 1;
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
    this.loadedNorma[newIndex] = true;
    this.loadedArticolo[newIndex] = true;
    this.loadedLettera[newIndex] = true;
    this.loadedComma[newIndex] = true;
    if (this.verbale.enteRiferimentiNormativi)
      this.loadAmbitiByIdEnte(
        newIndex,
        this.verbale.enteRiferimentiNormativi.id
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
      ){
        countDuplicati++;
      }
    }
    return countDuplicati == 2 ? true : false;
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
          this.verbale.dataOraAccertamento = event.srcElement.value;
          break;
      }
    }
  }

  eliminaRiferimentoNormativo(index: number) {
    if (this.isRiferimentoNormativoDuplicato(index)) { this.showWarning = false;}
    if (this.verbale.riferimentiNormativi.length <= 1) {return;} //controllo aggiuntivo
    this.isVisible[index] = false;
  }

  //restituisce true se la dataOra1 è successiva alla dataOra2
  isAfter(dataOra1: string, dataOra2: string): boolean {
    let DD1, DD2, MM1, MM2, YYYY1, YYYY2, HH1, HH2, mm1, mm2: string;
    YYYY2 = dataOra2.substring(6, 10);
    YYYY1 = dataOra1.substring(6, 10);
    if (YYYY1 > YYYY2) {return true;}
    else if (YYYY1 < YYYY2) {return false;}
    else {
      MM1 = dataOra1.substring(3, 5);
      MM2 = dataOra2.substring(3, 5);
      if (MM1 > MM2) {return true;}
      else if (MM1 < MM2) {return false;}
      else {
        DD1 = dataOra1.substring(0, 2);
        DD2 = dataOra2.substring(0, 2);

        if (DD1 > DD2) {return true;}
        else if (DD1 < DD2) {return false;}
        else {
          HH1 = dataOra1.substring(12, 14);
          HH2 = dataOra2.substring(12, 14);

          if (HH1 > HH2) {return true;}
          else if (HH1 < HH2) {return false;}
          else {
            mm1 = dataOra1.substring(15, 17);
            mm2 = dataOra2.substring(15, 17);

            if (mm1 > mm2) {return true;}
          }
        }
      }
    }

    return false;
  }
  isKeyPressed(code: number): boolean {    return this.numberUtilsSharedService.numberValid(code);  }

  trackByFn(index, item) {    return item.__id;
  }

  ngAfterViewChecked() {
    let i: number;
    for (i = 1; i < 3; i++) {this.manageDatePicker(null, i);}

    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }

  byId(o1: SelectVO, o2: SelectVO) {    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  ngOnDestroy(): void {    this.logger.destroy(PregressoInserimentoComponent.name);
  }

  emitManuale(): void {    this.router.navigateByUrl(Routing.PREGRESSO_INSERIMENTO_MANUALE);  }

  emitRicerca(): void {
    let ricerca: RicercaProtocolloRequest = new RicercaProtocolloRequest();
    ricerca.numeroProtocollo = this.numeroProtocollo;
    ricerca.flagPregresso = true;
    this.loaded = false;

    this.sharedVerbaleService.ricercaProtocolloSuACTA(ricerca).subscribe(
      (data) => {
        this.loaded = true;
        if (
          data.documentoProtocollatoVOList &&
          data.documentoProtocollatoVOList.length > 0
        ) {
          let tipiAllegatoDuplicabili = [];
          if (!data.documentoProtocollatoVOList) {
            data.documentoProtocollatoVOList = [];
          } else if (data.documentoProtocollatoVOList[0]) {
            tipiAllegatoDuplicabili =
              data.documentoProtocollatoVOList[0].tipiAllegatoDuplicabili;
          }
          this.fascicoloService.categoriesDuplicated = tipiAllegatoDuplicabili;

          // numero protocollo per ricerca documento protocollato
          this.fascicoloService.searchFormRicercaProtocol =
            ricerca.numeroProtocollo;
          // dati per ricerca documento protocollato
          this.fascicoloService.dataRicercaProtocollo =
            data.documentoProtocollatoVOList;

          if (data.messaggio) {
            this.fascicoloService.message = data.messaggio;
          }else{
            this.fascicoloService.message = null;
          }

          const numpages:number = Math.ceil(+data.totalLineResp/+data.maxLineReq);
          this.fascicoloService.dataRicercaProtocolloNumPages = numpages;
          this.fascicoloService.dataRicercaProtocolloNumResults = +data.totalLineResp;
          this.router.navigateByUrl(Routing.PREGRESSO_INSERIMENTO_ACTA);
        } else {
          this.manageMessage(data.messaggio);
        }
      },
      (err) => {
        if (err instanceof ExceptionVO) {          this.manageMessage(err);        }
        this.loaded = true;
        this.logger.error("Errore nel recupero dei documenti protocollati");
      }
    );
  }
}
