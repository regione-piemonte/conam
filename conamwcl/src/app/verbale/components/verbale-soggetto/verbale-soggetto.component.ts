import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import {  ProvinciaVO,  ComuneVO,  RegioneVO,
  RuoloVO,  NazioneVO,} from "../../../commons/vo/select-vo";
import { Config } from "../../../shared/module/datatable/classes/config";
import { VerbaleService } from "../../services/verbale.service";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { MinSoggettoVO } from "../../../commons/vo/verbale/min-soggetto-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { LuoghiService } from "../../../core/services/luoghi.service";
import { NgForm } from "@angular/forms";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { SharedVerbaleConfigService } from "../../../shared-verbale/service/shared-verbale-config.service";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { VerbaleVO } from "../../../commons/vo/verbale/verbale-vo";
import { SoggettoService } from "../../../soggetto/services/soggetto.service";

declare var $: any;

@Component({
  selector: "verbale-soggetto",
  templateUrl: "./verbale-soggetto.component.html",
})
export class VerbaleSoggettoComponent implements OnInit, OnDestroy {
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

  // global
  public loadedSoggetti: boolean;
  public idVerbale: number;
  public subscribers: any = {};

  // datatable
  public soggetti: Array<TableSoggettiVerbale> = new Array<TableSoggettiVerbale>();
  public config: Config;

  private indirizzo: string;
  private cap: string;
  private civico: string;
  private indirizzoEstero: string;
  private capEstero: string;
  private civicoEstero: string;
	public importoVerbale: number=0;
	public importo: number=0;

  public loaderRegioni: boolean = false;
  public provinciaModel: Array<ProvinciaVO> = new Array<ProvinciaVO>();
  public regioneModel: Array<RegioneVO> = new Array<RegioneVO>();
  public nazioneResidenzaModel: Array<NazioneVO> = new Array<NazioneVO>();
  public loaderProvince: boolean = true;
  public loaderComuni: boolean = true;
  public comuneModel: Array<ComuneVO> = new Array<ComuneVO>();
  public loaderNazioni: boolean;

  // ruolo
  public ruoloModel = new Array<RuoloVO>();

  // insert soggetto
  public isAggiungiSoggetto: boolean;
  public modalita: string;
  public soggetto: SoggettoVO;
  public loadedSalvaRicerca: boolean;
  public showResidenza: boolean = false;
  public comuneEsteroDisabled: boolean = false;
  public comuneEstero: boolean = false;

  // edit soggetto
  public soggettoModifica: TableSoggettiVerbale;
  public isModificaSoggetto: boolean;

  // Messaggio top
  public typeMessageTop: String;
  public showMessageTop: boolean;
  public messageTop: String;

  // Messaggio conferma eliminazione
  public buttonConfirmText: string;
  public buttonAnnullaText: string;
  public subMessages: Array<string>;

  private intervalIdS: number = 0;

  //warning meta pagina
  public typeMessageBottom: String;
  public showMessageBottom: boolean;
  public messageBottom: String;

  //RUOLO
  public loaderRuolo: boolean;

  //VALIDAZIONE
  public formGiuridicoValid: boolean;
  public formFisicoValid: boolean;

  private intervalIdSBottom: number = 0;

  constructor(
    private router: Router,
    private logger: LoggerService,
    private luoghiService: LuoghiService,
    private activatedRoute: ActivatedRoute,
    private utilSubscribersService: UtilSubscribersService,
    private verbaleService: VerbaleService,
    private sharedVerbaleService: SharedVerbaleService,
    private soggettoService: SoggettoService,
    private sharedVerbaleConfigService: SharedVerbaleConfigService,
  ) {}

  ngOnInit(): void {
    this.logger.init(VerbaleSoggettoComponent.name);
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      // condizione di uscita
      if (isNaN(this.idVerbale))
        this.router.navigateByUrl(Routing.VERBALE_RICERCA);
        this.verbaleService.getVerbaleById(this.idVerbale).subscribe((data: VerbaleVO)=>{
          this.importo= data.importo;
          this.importoVerbale= data.importo;
      })
      this.loadSoggettiAssociatiAVerbale();
      this.loadNazioni();
      this.loadRegioni();
      this.loadRuoli();
      this.manageDatePicker();
      this.pulisciFiltri();
      this.loadedSalvaRicerca = true;
    });
  }

  loadNazioni() {
    this.loaderNazioni = false;
    this.subscribers.nazioni = this.luoghiService.getNazioni().subscribe(
      (data) => {
        this.nazioneResidenzaModel = data;
        this.loaderNazioni = true;
      },
      (err) => {        this.logger.error("Errore nel recupero delle nazioni");
      }
    );
  }

  // PROVINCIA  RESIDENZA
  public isDisabledComuneProvincia(type: string) {
    if (type == "P" && this.soggetto.regioneResidenza.id == null) {return true;}
    if (type == "C" && this.soggetto.provinciaResidenza.id == null) {return true;}
    return false;
  }

  loadProvince(id: number) {
    this.loaderProvince = false;
    this.subscribers.provinceByRegione = this.luoghiService
      .getProvinciaByIdRegione(id)
      .subscribe(
        (data) => {
          this.comuneModel = null;
          this.provinciaModel = data;
          this.loaderProvince = true;
        },
        (err) => {          this.logger.error("Errore nel recupero delle province");        }
      );
  }

  loadRegioni() {
    this.loaderRegioni = false;
    this.subscribers.regioni = this.luoghiService.getRegioni().subscribe(
      (data) => {
        this.regioneModel = data;
        this.loaderRegioni = true;
      },
      (err) => {        this.logger.error("Errore nel recupero delle regioni");
      }
    );
  }

  manageLuoghiBySoggetto() {
    if (
      this.soggetto.regioneResidenza != null &&
      this.soggetto.regioneResidenza.id != null
    ) {      this.loadProvince(this.soggetto.regioneResidenza.id);    }
    if (
      this.soggetto.provinciaResidenza != null &&
      this.soggetto.provinciaResidenza.id != null
    ) {      this.loadComuni(this.soggetto.provinciaResidenza.id);    }
  }

  loadComuni(id: number) {
    this.loaderComuni = false;
    this.subscribers.comuniByProvince = this.luoghiService
      .getComuneByIdProvincia(id)
      .subscribe(
        (data) => {
          this.comuneModel = data;
          this.loaderComuni = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero dei comuni");
        }
      );
  }

  // RUOLI
  loadRuoli() {
    this.loaderRuolo = false;
    this.subscribers.ruolo = this.verbaleService.ruoliSoggetto().subscribe(
      (data) => {
        this.ruoloModel = data;
        this.loaderRuolo = true;
      },
      (err) => {        this.logger.error("Errore nel recupero dei ruoli");      }
    );
  }

  //CHANGE PERSONA
  cambiaPersona(type: string) {
    this.soggetto = new SoggettoVO();
    this.modalita = "R";
    this.soggetto.personaFisica = type == "G" ? false : true;
    this.showResidenza = false;
  }

  loadSoggettiAssociatiAVerbale() {
    this.config = this.sharedVerbaleConfigService.getConfigVerbaleSoggetti(
      false,      null,
      null,      true,
      true
    );
    this.subscribers.soggetto = this.sharedVerbaleService
      .getSoggettiByIdVerbale(this.idVerbale, true)
      .subscribe((data) => {
        if (data != null) {
          this.soggetti = data.map((value) => {            return TableSoggettiVerbale.map(value);          });
        }
        this.loadedSoggetti = true;
      });
  }

  ricerca(event: any, tipoPersona: string) {
    let min: MinSoggettoVO = MinSoggettoVO.constructrFromSoggetto(
      this.soggetto
    );
    this.loadedSalvaRicerca = false;
    if (
      tipoPersona == "G" &&
      min.partitaIva != null &&
      min.partitaIva.length > 0
    ) {
      this.subscribers.ricercaSoggetto = this.verbaleService
        .ricercaSoggettoPerPIva(min)
        .subscribe((data) => {
          if (data == null) {
            this.manageMessageBottom(
              "Soggetto non trovato, provare con codice fiscale ",
              "WARNING"
            );

            this.loadedSalvaRicerca = true;
          } else {            this.ricercaSoggetto(min);          }
        });
    } else {
      this.ricercaSoggetto(min);
    }
  }

  // CHANGE RESIDENZA
  cambiaResidenza(type: string) {    this.soggetto.residenzaEstera = type == "I" ? false : true;
  }

  ricercaSoggetto(min: MinSoggettoVO) {
    this.subscribers.ricercaSoggetto = this.verbaleService
      .ricercaSoggetto(min)
      .subscribe(
        (data) => {
          this.comuneEstero = true;
          if (data.nazioneNascitaEstera && !data.denomComuneNascitaEstero)
            this.comuneEsteroDisabled = false;
          else this.comuneEsteroDisabled = true;
          this.loadedSalvaRicerca = true;
          this.soggetto = SoggettoVO.editSoggettoFromSoggetto(
            this.soggetto,
            data
          );

          if (this.soggetto.residenzaEstera) {
            this.indirizzoEstero = this.soggetto.indirizzoResidenza;
            this.civicoEstero = this.soggetto.civicoResidenza;
            this.capEstero = this.soggetto.cap;
            if (this.soggetto.idStas != null) {
              this.indirizzo = this.soggetto.indirizzoResidenzaStas;
              this.civico = this.soggetto.civicoResidenzaStas;
              this.cap = this.soggetto.capStas;
            }
          } else {
            this.indirizzo = this.soggetto.indirizzoResidenza;
            this.civico = this.soggetto.civicoResidenza;
            this.cap = this.soggetto.cap;
          }
          this.manageLuoghiBySoggetto();
          this.modalita = "E";
          this.showResidenza = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.showResidenza = true;
            this.loadedSalvaRicerca = true;
            this.modalita = "E";
            this.manageMessage(err);
            this.logger.error("Errore nell'eliminazione del verbale");
          }
        }
      );
  }

  manageMessageTop(message: string, type: string) {
    this.messageTop = message;
    this.typeMessageTop = type;
    this.timerShowMessageTop();
  }

  manageMessage(err: ExceptionVO) {
    this.typeMessageTop = err.type;
    this.messageTop = err.message;
    this.timerShowMessageTop();
  }

  timerShowMessageTop() {
    this.showMessageTop = true;
    let seconds: number = 30; // this. configService. getTimeoutMessagge();
    this.intervalIdS = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageTop();
      }
    }, 1000);
  }

  manageMessageBottom(message: string, type: string) {
    this.typeMessageBottom = type;
    this.messageBottom = message;
    this.timerShowMessageBottom();
  }

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
  }

  timerShowMessageBottom() {
    this.showMessageBottom = true;
    let seconds: number = 30; // this. configService .getTimeoutMessagge();
    this.intervalIdSBottom = window.setInterval(() => {
      seconds -= 1;
      if (seconds === 0) {
        this.resetMessageBottom();
      }
    }, 1000);
  }

  pulisciFiltri() {
    this.importo=this.importoVerbale;

    this.comuneEstero = false;
    this.comuneEsteroDisabled = false;
    if(!this.isModificaSoggetto){
    	this.soggetto = new SoggettoVO();
	    this.soggetto.personaFisica = true;
	    this.modalita = "R";
	    this.indirizzo = null;
	    this.indirizzoEstero = null;
	    this.civico = null;
	    this.civicoEstero = null;
	    this.cap = null;
	    this.capEstero = null;
	    this.showResidenza = false;
	 }else{
		this.soggetto.personaFisica = true;
		this.soggetto.nome = '';
		this.soggetto.cognome = '';
	    this.soggetto.personaFisica = true;
	    this.modalita = "R";
	    this.soggetto.dataNascita = null;
	    this.soggetto.nazioneNascitaEstera = false;
	      if(this.soggetto.regioneNascita) { this.soggetto.regioneNascita.id = 0;}
	      if(this.soggetto.provinciaNascita) { this.soggetto.provinciaNascita.id = 0;}
	      if(this.soggetto.comuneNascita) { this.soggetto.comuneNascita.id = 0;}
	 }
  }

  resetMessageBottom() {
    this.showMessageBottom = false;
    this.typeMessageBottom = null;
    this.messageBottom = null;
    clearInterval(this.intervalIdSBottom);
  }

  salvaSoggetto() {
    this.loadedSalvaRicerca = false;
    if (this.soggetto.residenzaEstera) {
      this.soggetto.indirizzoResidenza = this.indirizzoEstero;
      this.soggetto.civicoResidenza = this.civicoEstero;
      this.soggetto.cap = this.capEstero;
      this.soggetto.regioneResidenza = null;
      this.soggetto.provinciaResidenza = null;
      this.soggetto.comuneResidenza = null;
    } else {
      this.soggetto.indirizzoResidenza = this.indirizzo;
      this.soggetto.civicoResidenza = this.civico;
      this.soggetto.cap = this.cap;
      this.soggetto.nazioneResidenza = null;
      this.soggetto.denomComuneResidenzaEstero = null;
    }
    this.soggetto.importoVerbale =this.importo;
    this.subscribers.salvaSoggettoVerbale = this.verbaleService
      .salvaSoggetto(this.soggetto, this.idVerbale)
      .subscribe(
        (data) => {
          if (data.comuneNascitaValido) {
	        if(this.isModificaSoggetto){
	          	this.soggetti.splice( this.soggetti.indexOf(this.soggettoModifica), 1);
          	}
            this.soggetti.push(TableSoggettiVerbale.map(data));
            this.isAggiungiSoggetto = false;
            this.pulisciFiltri();

            if(this.isModificaSoggetto){            	this.manageMessageTop("Soggetto modificato con successo", "SUCCESS");
            }else {            	this.manageMessageTop("Soggetto aggiunto con successo", "SUCCESS");
            }
            this.isModificaSoggetto = false;
          } else {
            this.manageMessageTop(
              "Il luogo di nascita selezionato non è compatibile con la data di nascita del soggetto. Inserire una regione-provincia-comune validi alla data di nascita.",
              "WARNING"
            );
          }

          this.loadedSalvaRicerca = true;
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.loadedSalvaRicerca = true;
            this.showResidenza = true;
            this.manageMessage(err);
            this.logger.error("Errore nell'eliminazione del verbale");
          }
        }
      );
  }

  // modifica soggetto
  modifica(el: TableSoggettiVerbale) {
	console.log('modifica');
	console.log(el);
  this.modalita = "E";
	this.soggettoModifica = el;
    this.isModificaSoggetto = true;
    this.isAggiungiSoggetto = false;

    this.subscribers.soggetto = this.soggettoService.getSoggettoById(el.id,this.idVerbale).subscribe((data) => {
        this.soggetto = data;
		console.log(this.soggetto);
          this.comuneEstero = true;
          if (data.nazioneNascitaEstera && !data.denomComuneNascitaEstero)
            this.comuneEsteroDisabled = false;
          else this.comuneEsteroDisabled = true;
          this.loadedSalvaRicerca = true;
          this.soggetto = SoggettoVO.editSoggettoFromSoggetto(
            this.soggetto,
            data
          );
    	  this.importo = this.soggetto.importoVerbale;
          if (this.soggetto.residenzaEstera) {
            this.indirizzoEstero = this.soggetto.indirizzoResidenza;
            this.civicoEstero = this.soggetto.civicoResidenza;
            this.capEstero = this.soggetto.cap;
            if (this.soggetto.idStas != null) {
              this.indirizzo = this.soggetto.indirizzoResidenzaStas;
              this.civico = this.soggetto.civicoResidenzaStas;
              this.cap = this.soggetto.capStas;
            }
          } else {
            this.indirizzo = this.soggetto.indirizzoResidenza;
            this.civico = this.soggetto.civicoResidenza;
            this.cap = this.soggetto.cap;
          }
          this.manageLuoghiBySoggetto();
          this.showResidenza = true;

    });


  }

  //elimina soggetto
  confermaEliminazione(el: TableSoggettiVerbale) {
    this.logger.info(      "Richiesta eliminazione del soggetto " + el.idVerbaleSoggetto    );
    this.generaMessaggio(el);
    this.buttonAnnullaText = "Annulla";
    this.buttonConfirmText = "Conferma";

    // messaggio
    this.sharedDialog.open();

    // un subscribe
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "close");
    this.utilSubscribersService.unsbscribeByName(this.subscribers, "save");


    //"Conferma"
    this.subscribers.save = this.sharedDialog.salvaAction.subscribe(
      (data) => {
        this.loadedSoggetti = false;
        this.subscribers.eliminaSoggetto = this.verbaleService
          .eliminaSoggettoByIdVerbaleSoggetto(el.idVerbaleSoggetto)
          .subscribe(
            (data) => {
              //elimina il soggetto
              this.logger.info("Elimina elemento da tabella");
              this.soggetti.splice(this.soggetti.indexOf(el), 1);
              this.loadSoggettiAssociatiAVerbale();
              this.loadedSoggetti = true;
              this.manageMessageTop(                "Soggetto eliminato con successo",                "SUCCESS"
              );
              this.isModificaSoggetto = false;
            },
            (err) => {
              if (err instanceof ExceptionVO) {
                this.manageMessage(err);
              }
              this.logger.error(                "Errore nell'eliminazione del soggetto " +                  el.identificativoSoggetto
              );
              this.loadedSoggetti = true;
            }
          );
      },
      (err) => {
        this.logger.error(err);
      }
    );

    //premo "Annulla"
    this.subscribers.close = this.sharedDialog.closeAction.subscribe(
      (data) => {        this.subMessages = new Array<string>();      },
      (err) => {        this.logger.error(err);      }
    );
  }

  // ROUT ING
  goToVerbaleRiepilogo() {    this.router.navigateByUrl(Routing.VERBALE_RIEPILOGO + this.idVerbale);
  }

  goToVerbaleDati() {    this.router.navigateByUrl(Routing.VERBALE_DATI + this.idVerbale);
  }

  generaMessaggio(el: TableSoggettiVerbale) {
    this.subMessages = new Array<string>();
    let incipit: string = "Si intende eliminare il seguente soggetto?";
    this.subMessages.push(incipit);
    this.subMessages.push(el.nomeCognomeRagioneSociale);
  }

  goToVerbaleAllegato() {
    this.router.navigateByUrl(Routing.VERBALE_ALLEGATO + this.idVerbale);
  }

  ngOnDestroy(): void {
    this.logger.destroy(VerbaleSoggettoComponent.name);
  }

  isDisable(field: string) {
    let s = this.soggetto;
    if (s.idStas != null) return true;
    else {
      if (field == "PR") {
        return this.isDisabledComuneProvincia("P");
      } else if (["CR", "IR", "CI", "CAP"].indexOf(field) >= 0) {
        return this.isDisabledComuneProvincia("C");
      }
    }
    return false;
  }

  // DATE PICKER
  manageDatePicker() {
    if ($("#datepicker").length) {
      $("#datepicker").datetimepicker({
        format: "DD/MM/YYYY",
        locale: "it",
      });
    }
  }

  public setFormValid(event: boolean, type: string) {
    if (type == "G") this.formGiuridicoValid = event;
    if (type == "F") this.formFisicoValid = event;
  }

  annullaModifica(){
    this.pulisciFiltri();
    this.isModificaSoggetto = false;
    this.isAggiungiSoggetto = false;
  }

  formDisabled(form: NgForm) {
    if (this.soggetto.personaFisica) {
      return !(this.formFisicoValid && form.valid);}
    else {
      return !(this.formGiuridicoValid && form.valid);}
  }

}
