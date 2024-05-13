import { Component, OnInit, OnDestroy, Inject, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import {  RegioneVO,  ProvinciaVO,  ComuneVO,  NormaVO,  ArticoloVO,  CommaVO,  LetteraVO,  EnteVO,  SelectVO,  AmbitoVO,} from "../../../commons/vo/select-vo";
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
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";

declare var $: any;

@Component({
  selector: "pregresso-dati",
  templateUrl: "./pregresso-dati.component.html",
})
export class PregressoDatiComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

  public subscribers: any = {};

  public singoloEnte: boolean;
  public loadedAmbito: Array<boolean> = [];
  public loadedNorma: Array<boolean> = [];
  public loadedArticolo: Array<boolean> = [];
  public loadedComma: Array<boolean> = [];
  public loadedLettera: Array<boolean> = [];

  public isVisible: Array<boolean> = [true];

  public verbale: VerbaleVO;

  public loaded: boolean;
  public loadedRegioni: boolean;
  public loadedProvince: boolean;
  public loadedComuni: boolean;

  public idVerbale: number;

  public regioneModel: Array<RegioneVO>;
  public provinciaModel: Array<ProvinciaVO>;
  public comuneModel: Array<ComuneVO>;
  public comuneEnteModel: Array<ComuneVO>;
  public enteAccertatoreModel: Array<EnteVO>;
  public enteModel: Array<EnteVO>;
  public ambitoModel: Array<Array<AmbitoVO>> = [];
  public normaModel: Array<Array<NormaVO>> = [];
  public articoloModel: Array<Array<ArticoloVO>> = [];
  public commaModel: Array<Array<CommaVO>> = [];
  public letteraModel: Array<Array<LetteraVO>> = [];

  private uIdCounter: number = 0;
  public rifNormMessage: string = "Impossibile inserire più Riferimenti Normativi identici";

  private intervalIdS: number = 0;
  private intervalIdW: number = 0;
  public countSoggetti: number = 0;

  //warning meta pagina
  public typeWarning: string;
  public showWarning: boolean;
  public messageWarning: string;

  //Messaggio top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  public showAggiornaImportiSoggetto: boolean;

  public showSoggetti: boolean = true;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private luoghiService: LuoghiService,
    private rifNormativiService: RifNormativiService,
    private userService: UserService,
    private verbaleService: VerbaleService,
    private pregressoVerbaleService: PregressoVerbaleService,
    private numberUtilsSharedService: NumberUtilsSharedService,
    private utilSubscribersService: UtilSubscribersService,
    private sharedVerbaleService: SharedVerbaleService,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {

    this.showAggiornaImportiSoggetto = false;
    this.logger.init(PregressoDatiComponent.name);

    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      let verbString = params["id"];
      if (!verbString || isNaN(+verbString)) this.idVerbale = null;
      else this.idVerbale = +verbString;
      this.subscribers.userProfile = this.userService.profilo$.subscribe(
        (data) => {
          if (data != null) {
            this.loaded = false;
            if ( this.activatedRoute.snapshot.paramMap.get("action") == "creazione" ){
              this.manageMessageSuccess("Fascicolo creato con successo","SUCCESS");
            }
            if ( this.activatedRoute.snapshot.paramMap.get("action") == "salvato_con_warning" ){
              this.manageMessageSuccess(
                "Il Fascicolo è stato salvato, ma la Data e ora processo verbale sono successive alla Data e ora accertamento",
                "WARNING"
              );
            }
            this.loadRegioni();
            this.comuniEnteValidInDate();
            this.loadEnti(data.entiAccertatore, data.entiLegge);

            if (!this.idVerbale) {
              this.nuovoVerbale();
            } else {
              this.modificaVerbale();
            }

            if(this.idVerbale){
	            this.subscribers.soggetto = this.sharedVerbaleService
			      .getSoggettiByIdVerbale(this.idVerbale, true)
			      .subscribe((data) => {
			        if (data != null) {
			        data.map((value) => {
			        		console.log('value',value);
			            this.showAggiornaImportiSoggetto = true;
				      });
    					}
			      });
            }
          }
        }
      );
    });
  }

  manageMessageWarning(message: string) {
    this.typeWarning = "WARNING";
    this.messageWarning = message;
    this.timerShowMessageWarning();
  }

  manageMessageSuccess(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 30;
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }

  manageMessage(err: ExceptionVO) {
    this.typeMessageTop = err.type;
    this.messageTop = err.message;
    this.timerShowMessageTop();
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }

  timerShowMessageWarning() {
    this.showWarning = true;
    let seconds: number = 50; //this.configService.getTimeoutMessagge();
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
    if (this.enteModel.length == 1)
      this.verbale.enteRiferimentiNormativi = this.enteModel[0];
    this.nuovoRiferimentoNormativo();
    this.loaded = true;
  }

  resetMessageWarning() {
    this.showWarning = false;
    this.typeWarning = null;
    this.messageWarning = null;
    clearInterval(this.intervalIdW);
  }

  modificaVerbale() {
    this.subscribers.modificaVerbale = this.pregressoVerbaleService
      .getVerbaleById(this.idVerbale)
      .subscribe(
        (data) => {
          this.loaded = true;
          this.verbale = data;
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
          this.scrollEnable = true;
          this.logger.error("Errore nel recupero verbale");
        }
      );
  }

  scrollEnable: boolean;

  goToVerbaleSoggetto() {
    this.router.navigateByUrl(Routing.PREGRESSO_SOGGETTO + this.idVerbale);
  }

  goToVerbaleRiepilogo() {
    this.router.navigateByUrl(Routing.PREGRESSO_RIEPILOGO + this.idVerbale);
  }

  creaFascicolo() {
    // controllo validita riferimenti normativi
    if (!this.validaRiferimenti()) {
      this.manageMessageWarning(this.rifNormMessage);
      return;
    }
    // elimino riferimenti rimossi
    this.cleanRiferimentiNormativi();
    this.loaded = false;
    this.subscribers.salvaVerbale = this.pregressoVerbaleService
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
              [Routing.PREGRESSO_DATI + this.idVerbale, { action: azione }],
              { replaceUrl: true }
            );
          } else {
            this.idVerbale = data;
            if ( this.isAfter(this.verbale.dataOraViolazione, this.verbale.dataOraAccertamento) ){
              azione = "salvato_con_warning";
            } else {
              azione = "modifica";
            }
            if (azione == "modifica"){
              this.manageMessageSuccess("Fascicolo modificato con successo","SUCCESS");
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
          this.logger.error( "Errore nel salvataggio del verbale" );
          this.scrollEnable = true;
        }
      );
  }

  goToVerbaleAllegato() {
    this.router.navigateByUrl(Routing.PREGRESSO_ALLEGATO + this.idVerbale);
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
        ){          flag = false;        }
      }
    }
    return flag;
  }

  uploadImportiSogg() {
    this.showSoggetti = false;
     console.log(this.verbale);
     this.verbaleService
       .uploadImportiSogg(this.verbale.id, this.verbale.importo)
       .subscribe(
         (data) => {
           this.manageMessageSuccess("Importo aggiornato con successo","SUCCESS");
           this.showSoggetti = true;
         },
         (err) => {
           if (err instanceof ExceptionVO) {
             this.loaded = true;
             this.manageMessage(err);
           }
           this.logger.error("Errore nel'upload dati soggetto'");
           this.scrollEnable = true;
           this.showSoggetti = true;
         }
       );
   }

  // Messaggio di conferma eliminazione
  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;

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
  generaMessaggio() {
    this.subMessages = new Array<string>();
    let incipit: string = "Si intende eliminare il seguente fascicolo?";
    this.subMessages.push(incipit);
    this.subMessages.push("Numero verbale: " + this.verbale.numero);
    this.subMessages.push(
      "Data accertamento: " + this.verbale.dataOraAccertamento
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
      (err) => {
        this.logger.error("Errore recupero regioni");
      }
    );
  }

  eliminaFascicolo() {
    this.generaMessaggio();
    this.buttonAnnullaText = "Annulla";
    this.buttonConfirmText = "Conferma";

    //mostro un messaggio
    this.sharedDialog.open();

    //unsubscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");

    //premo "Conferma"
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
        this.loaded = false;
        this.subscribers.eliminaVerbale = this.verbaleService
          .eliminaVerbale(this.idVerbale)
          .subscribe(
            (data) => {
              this.router.navigateByUrl(Routing.PREGRESSO_ELIMINAZIONE);
            },
            (err) => {
              if (err instanceof ExceptionVO) {
                this.loaded = true;
                this.manageMessage(err);
                this.logger.error("Errore eliminazione verbale");
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
            this.provinciaModel = data;
            this.loadedProvince = true;
          },
          (err) => {
            this.logger.error("Errore recupero province");
          }
        );
    } else {
      this.provinciaModel = [];
      this.comuneModel = [];
      this.loadedProvince = true;
      this.verbale.provincia = undefined;
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
          this.logger.error("Errore recupero comuni Ente");
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
          this.logger.error("Errore recupero comuni");
        }
      );
  }

  // chiamata solo in modifica
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
        this.logger.error("Errore recupero ambiti");
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
    this.loadedNorma[0] = true;
    this.loadedArticolo[0] = true;
    this.loadedComma[0] = true;
    this.loadedLettera[0] = true;
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
        this.logger.error("Errore recupero articoli");
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
        this.logger.error("Errore recupero norme");
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
        this.logger.error("Errore recupero commi");
      }
    );
  }

  changeVerbaleContestato(e) {
    this.verbale.contestato = e;
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
        this.logger.error("Errore recupero lettere");
      }
    );
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

  changeProvincia(idProvincia: number) {
    this.verbale.comune = null;
    this.loadComuniByIdProvincia(idProvincia);
  }

  changeAmbito(index: number, idAmbito: number) {
    this.verbale.riferimentiNormativi[index].norma = null;
    this.verbale.riferimentiNormativi[index].articolo = null;
    this.verbale.riferimentiNormativi[index].comma = null;
    this.verbale.riferimentiNormativi[index].lettera = null;
    this.commaModel[index] = null;
    this.letteraModel[index] = null;
    this.loadNormeByIdAmbitoAndIdEnte(
      index,
      idAmbito,
      this.verbale.enteRiferimentiNormativi.id
    );
  }

  changeArticolo(index: number, idArticolo: number) {
    this.verbale.riferimentiNormativi[index].comma = null;
    this.verbale.riferimentiNormativi[index].lettera = null;
    this.letteraModel[index] = null;
    this.loadCommaByIdArticolo(index, idArticolo);
  }

  changeNormativa(index: number, idNorma: number) {
    this.verbale.riferimentiNormativi[index].articolo = null;
    this.verbale.riferimentiNormativi[index].comma = null;
    this.verbale.riferimentiNormativi[index].lettera = null;
    this.commaModel[index] = null;
    this.letteraModel[index] = null;
    this.loadArticoliByIdNormaAndIdEnte(
      index,
      idNorma,
      this.verbale.enteRiferimentiNormativi.id
    );
  }

  isDisabledProvincia(): boolean {
    return (
      !this.verbale.regione ||
      (this.verbale.regione && !this.verbale.regione.id)
    );
  }

  changeComma(index: number, idComma: number) {
    this.verbale.riferimentiNormativi[index].lettera = null;
    this.loadLettereByIdComma(index, idComma);
  }

  // solo il primo può essere disabled, se più enti e nessuno è stato ancora scelto
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
      (this.verbale.provincia && !this.verbale.provincia.id)
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
      (this.verbale.riferimentiNormativi[index].comma &&
        !this.verbale.riferimentiNormativi[index].comma.id)
    );
  }

  isDisabledComma(index: number): boolean {
    return (
      !this.verbale.riferimentiNormativi[index].articolo ||
      (this.verbale.riferimentiNormativi[index].articolo &&
        !this.verbale.riferimentiNormativi[index].articolo.id)
    );
  }

  isDisabledElimina(): boolean {
    let count: number = 0;
    this.isVisible.forEach((el) => {
      if (el) count++;
    });
    return count <= 1;
  }
  isDisabledCreaFascicolo(valid: boolean): boolean {
    return !valid || this.isDisabledAggiungi();
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
    if (this.verbale.enteRiferimentiNormativi)
      this.loadAmbitiByIdEnte(
        newIndex,
        this.verbale.enteRiferimentiNormativi.id
      );
  }


  eliminaRiferimentoNormativo(index: number) {
    if (this.isRiferimentoNormativoDuplicato(index)) {
      this.showWarning = false;
    }
    if (this.verbale.riferimentiNormativi.length <= 1) {
      return; // controllo ulteriore
    }
    this.isVisible[index] = false;
  }

  isRiferimentoNormativoDuplicato(index: number): boolean {
    let elemento = this.verbale.riferimentiNormativi[index];
    let countDuplicati = 0;
    for (let i = 0; i < this.verbale.riferimentiNormativi.length; i++) {
      if (
        this.isVisible[i] &&
        this.verbale.riferimentiNormativi[i].ambito.id == elemento.ambito.id &&
        elemento.ambito != null &&
        this.verbale.riferimentiNormativi[i].norma.id == elemento.norma.id &&
        elemento.norma != null &&
        this.verbale.riferimentiNormativi[i].articolo.id == elemento.articolo.id &&
        elemento.articolo != null &&
        this.verbale.riferimentiNormativi[i].comma.id == elemento.comma.id &&
        elemento.comma != null &&
        elemento.lettera != null &&
        this.verbale.riferimentiNormativi[i].lettera.id == elemento.lettera.id
      )
        countDuplicati++;
    }
    return countDuplicati == 2 ? true : false;
  }

  modelChangeFn(value: string){    this.verbale.dataOraViolazione = value  }

  manageDatePicker(event: any, i: number) {
    var str: string = "#datetimepicker" + i.toString();
    if ($(str).length) {
      $(str).datetimepicker({
        maxDate: new Date().setDate(new Date().getDate() + 1),
        format: "DD/MM/YYYY, HH:mm",
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
          this.verbale.dataOraViolazione = this.verbale.dataOraAccertamento
          break;
      }
    }
  }

  // true se dataOra1 successiva a dataOra2
  isAfter(dataOra1: string, dataOra2: string): boolean {
    let DD1, DD2, MM1, MM2, YYYY1, YYYY2, HH1, HH2, mm1, mm2: string;
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

  trackByFn(index, item) {    return item.__id;  }

  isKeyPressed(code: number): boolean {    return this.numberUtilsSharedService.numberValid(code);  }

  ngAfterViewChecked() {
    let i: number;
    for (i = 1; i < 3; i++) {
      this.manageDatePicker(null, i);
    }
    let out: HTMLElement = document.getElementById("scrollTop");
    if (this.loaded && this.scrollEnable && out != null) {
      out.scrollIntoView();
      this.scrollEnable = false;
    }
  }
  ngOnDestroy(): void {    this.logger.destroy(PregressoDatiComponent.name);  }

  soggettoChange(countSoggetti: number): void {    this.countSoggetti = countSoggetti;  }

  byId(o1: SelectVO, o2: SelectVO) {    return o1 && o2 ? o1.id === o2.id : o1 === o2;  }
}
