import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  Output,
  EventEmitter,
  Input,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Config } from "../../../shared/module/datatable/classes/config";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { MyUrl } from "../../../shared/module/datatable/classes/url";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { AllegatoVO } from "../../../commons/vo/verbale/allegato-vo";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { UtilSubscribersService } from "../../../core/services/util-subscribers-service";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { SharedVerbaleService } from "../../service/shared-verbale.service";
import { Routing } from "../../../commons/routing";
import { PregressoVerbaleService } from "../../../pregresso/services/pregresso-verbale.service";
import { CurrencyPipe } from "@angular/common";
import { NotaVO } from "../../../commons/vo/verbale/nota-vo";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { MinSoggettoVO } from "../../../commons/vo/verbale/min-soggetto-vo";
import { SoggettoService } from "../../../soggetto/services/soggetto.service";
import { LuoghiService } from "../../../core/services/luoghi.service";
import { NgForm } from "@angular/forms";

import {
  ProvinciaVO,
  ComuneVO,
  RegioneVO,
  RuoloVO,
  NazioneVO,
} from "../../../commons/vo/select-vo";

@Component({
  selector: "shared-verbale-riepilogo",
  templateUrl: "./shared-verbale-riepilogo.component.html",
  styleUrls: ["./shared-verbale-riepilogo.component.scss"],
})
export class SharedVerbaleRiepilogoComponent implements OnInit, OnDestroy {
  @Input()
  pregresso: boolean = false;
  @ViewChild(SharedDialogComponent) sharedDialog: SharedDialogComponent;

  public subscribers: any = {};

  public idVerbale: number;
  public riepilogoVerbale: RiepilogoVerbaleVO;
  public loaded: boolean;
  public loadedAllegato: boolean = true;

  public dataRifNormativi: Array<any>;
  public dataSoggVerbale: Array<any>;
  public notes: Array<NotaVO>;
  public configNote: Config;
  public configVerb: Config;
  public configIstr: Config;
  public configGiurisd: Config;
  public configRateizzazione: Config;

  public eliminaAllegatoFlag: boolean;

  public buttonAnnullaText: string;
  public buttonConfirmText: string;
  public subMessages: Array<string>;


  //Messaggio top
  public showMessageTop: boolean;
  public typeMessageTop: String;
  public messageTop: String;

  private intervalIdS: number = 0;
  private intervalIdW: number = 0;

  //ruolo
  public ruoloModel = new Array<RuoloVO>();

  //edit soggetto
  public isModificaSoggetto: boolean;
  public soggettoModifica: TableSoggettiVerbale;
  public soggetto: SoggettoVO;
  public modalita: string;
  public showResidenza: boolean = false;
  public loadedSalvaRicerca: boolean = true;
  public comuneEstero: boolean = false;
  public comuneEsteroDisabled: boolean = false;
  private indirizzo: string;
  private civico: string;
  private cap: string;
  private indirizzoEstero: string;
  private civicoEstero: string;
  private capEstero: string;
  public importo: number=0;
  public importoVerbale: number=0;

  public loaderRegioni: boolean = false;
  public regioneModel: Array<RegioneVO> = new Array<RegioneVO>();
  public provinciaModel: Array<ProvinciaVO> = new Array<ProvinciaVO>();
  public loaderProvince: boolean = true;
  public comuneModel: Array<ComuneVO> = new Array<ComuneVO>();
  public loaderComuni: boolean = true;
  public loaderNazioni: boolean;
  public nazioneResidenzaModel: Array<NazioneVO> = new Array<NazioneVO>();

  //VALIDAZIONE
  public formGiuridicoValid: boolean;
  public formFisicoValid: boolean;

  //RUOLO
  public loaderRuolo: boolean;

  @Output()
  public delete: EventEmitter<boolean> = new EventEmitter<boolean>();

 
  public showEdit: boolean= true

  constructor(
    private logger: LoggerService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private sharedVerbaleService: SharedVerbaleService,
    private pregressoVerbaleService: PregressoVerbaleService,
    private configSharedService: ConfigSharedService,
    private utilSubscribersService: UtilSubscribersService,
    private luoghiService: LuoghiService,
    private soggettoService: SoggettoService
  ) {}

  ngOnInit(): void {
    this.load();
    this.isModificaSoggetto = false;
    console.log(this.isModificaSoggetto);
    this.logger.init(SharedVerbaleRiepilogoComponent.name);

    //spostati dentro load
    //this.importo= this.riepilogoVerbale.verbale.importo;
    //this.importoVerbale= this.riepilogoVerbale.verbale.importo;


    this.pulisciFiltri();
    this.loadedSalvaRicerca = true;

  }
	loadDatiVerbale() {
		this.subscribers.riepilogo = this.sharedVerbaleService
        .riepilogoVerbale(this.idVerbale)
        .subscribe((data) => {
          this.riepilogoVerbale = data;
          });
     }
  // TODO Controllare
  load() {
    this.loaded = false;
    this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
      this.idVerbale = +params["id"];
      this.subscribers.riepilogo = this.sharedVerbaleService
        .riepilogoVerbale(this.idVerbale)
        .subscribe((data) => {
            ///
            this.notes= data.verbale.note;
            ////
          this.riepilogoVerbale = data;
        //  this.showEdit = data.verbale.stato.id!=3; //conciliato=3
          this.riepilogoVerbale.allegati.verbale.forEach((all) => {
            all.theUrl = new MyUrl(all.nome, null);
            all.showEdit = this.showEdit && (all.tipo.id==43);
            //quiiiii
          });
          this.riepilogoVerbale.allegati.istruttoria.forEach((all) => {
            all.theUrl = new MyUrl(all.nome, null);
          });
          this.riepilogoVerbale.allegati.giurisdizionale.forEach((all) => {
            all.theUrl = new MyUrl(all.nome, null);
          });
          this.riepilogoVerbale.allegati.rateizzazione.forEach((all) => {
            all.theUrl = new MyUrl(all.nome, null);
          });
          console.log('this.riepilogoVerbale',this.riepilogoVerbale);
          this.importo= this.riepilogoVerbale.verbale.importo;
          this.importoVerbale= this.riepilogoVerbale.verbale.importo;

          this.dataRifNormativi = data.verbale.riferimentiNormativi;

          let soggVerbale = data.soggetto.map(function (obj) {
            let tipoSoggetto = obj.personaFisica
              ? "PERSONA FISICA"
              : "PERSONA GIURIDICA";
            let identificativo = !obj.codiceFiscale
              ? obj.partitaIva
              : obj.codiceFiscale;
            let nomeCognomeRagione = obj.personaFisica
              ? obj.cognome + " " + obj.nome
              : obj.ragioneSociale;
            let currencyPipe = new CurrencyPipe("it-IT");

            return {
              identificativoSoggetto: identificativo,
              nomeCognomeRagioneSociale: nomeCognomeRagione,
              tipoSoggetto: tipoSoggetto,
              importoVerbale: currencyPipe.transform(
                obj.importoVerbale,
                "EUR",
                "symbol"
              ),
              importoResiduoVerbale: currencyPipe.transform(
                obj.importoResiduoVerbale,
                "EUR",
                "symbol"
              ),
              ruolo: obj.ruolo.denominazione,
              id: obj.id,
              noteSoggetto: obj.noteSoggetto,
            };
          });
          this.dataSoggVerbale = soggVerbale; //DA CONTROLLARE
          let call = this.pregresso
            ? this.pregressoVerbaleService.getAzioniVerbale(this.idVerbale)
            : this.sharedVerbaleService.getAzioniVerbale(this.idVerbale);

          this.subscribers.statoVerbale = call.subscribe((data) => {
            this.eliminaAllegatoFlag = data.eliminaAllegatoEnable;

            //setto i config

            this.configVerb =
              this.configSharedService.getConfigDocumentiVerbale(
                //this.eliminaAllegatoFlag
                this.eliminaAllegatoFlag,  (el: any) => true
              );
            this.configIstr =
              this.configSharedService.configDocumentiIstruttoria;
            this.configGiurisd =
              this.configSharedService.configDocumentiGiurisdizionale;
            this.configRateizzazione =
              this.configSharedService.configDocumentiRateizzazione;
            this.configNote = this.configSharedService.configNotes;
            this.loaded = true;
          });
        });
    });
  }

  //RUOLI
  loadRuoli() {
    this.loaderRuolo = false;
    this.subscribers.ruolo = this.sharedVerbaleService.ruoliSoggetto().subscribe(
      (data) => {
        this.ruoloModel = data;
        this.loaderRuolo = true;
      },
      (err) => {
        this.logger.error("Errore nel recupero dei ruoli");
      }
    );
  }

  confermaEliminazione(el: AllegatoVO) {

    this.logger.info("Richiesta eliminazione dell'allegato " + el.id);
    this.generaMessaggio(el);
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
        this.subscribers.eliminaAllegato = this.sharedVerbaleService
          .eliminaAllegato(el.id, this.idVerbale)
          .subscribe(
            (data) => {
              //elimina l'allegato
              this.logger.info("Elimina elemento da tabella");
              this.riepilogoVerbale.allegati.verbale.splice(
                this.riepilogoVerbale.allegati.verbale.indexOf(el),
                1
              );
              this.delete.next(true);
              this.router.navigate([
	           // Routing.PAGAMENTI_RICONCILIA_VERBALE_RIEPILOGO + this.idVerbale,
             Routing.VERBALE_RIEPILOGO + this.idVerbale,
	            {  },
	          ]);
              //this.load();
              //this.loaded = true;

              this.isModificaSoggetto = false;
            },
            (err) => {
              if (err instanceof ExceptionVO) {
                //this.manageMessage(err.type, err.message);
              }
              this.logger.error(
                "Errore nell'eliminazione dell'allegato " + el.id
              );
              this.loaded = true;
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

  generaMessaggio(el: AllegatoVO) {
    this.subMessages = new Array<string>();

    let incipit: string = "Si intende eliminare il seguente allegato?";

    this.subMessages.push(incipit);
    this.subMessages.push(el.theUrl.nomeFile);
  }

  configRifNorm: Config = {
    pagination: {
      enable: false,
    },
    selection: {
      enable: false,
    },
    sort: {
      enable: false,
    },
    columns: [
      {
        columnName: "ambito.acronimo",
        displayName: "Acronimo ambito",
      },
      {
        columnName: "norma.denominazione",
        displayName: "Normativa violata",
      },
      {
        columnName: "articolo.denominazione",
        displayName: "Articolo",
      },
      {
        columnName: "comma.denominazione",
        displayName: "Comma",
      },
      {
        columnName: "lettera.denominazione",
        displayName: "Lettera",
      },
    ],
  };

  onInfo(el: any | Array<any>) {
    if (el instanceof Array)
      throw Error("errore sono stati selezionati più elementi");
    else {
      let azione1: string = el.ruolo;
      let azione2: string = el.noteSoggetto;
      this.router.navigate([
        Routing.SOGGETTO_RIEPILOGO + el.id,
        { ruolo: azione1, nota: azione2, idVerbale: this.idVerbale },
      ]);
    }
  }
  configSoggVerbale: Config = {
    pagination: {
      enable: false,
    },
    selection: {
      enable: false,
    },
    sort: {
      enable: false,
    },
    buttonSelection: {
      label: "",
      enable: false,
    },
    info: {
      enable: true,
      hasInfo: (el: any) => {
        return true;
      },
    },
    edit: {
      enable: true,
      isEditable: (soggetto: SoggettoVO) => {
        return true;
      },
    },
    columns: [
      {
        columnName: "identificativoSoggetto",
        displayName: "Identificativo soggetto",
      },
      {
        columnName: "importoVerbale",
        displayName: "Importo in misura ridotta",
      },
      {
        columnName: "importoResiduoVerbale",
        displayName: "Importo Residuo",
      },
      {
        columnName: "nomeCognomeRagioneSociale",
        displayName: "Cognome Nome/Ragione Sociale",
      },
      {
        columnName: "tipoSoggetto",
        displayName: "Tipo Soggetto",
      },
      {
        columnName: "ruolo",
        displayName: "Ruolo",
      },
    ],
  };

  ngOnDestroy(): void {
    this.logger.destroy(SharedVerbaleRiepilogoComponent.name);
  }

  onLoadedAllegato(loaded: boolean) {
    this.loadedAllegato = loaded;
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
    }else if (currentUrl.includes('pagamenti/')) {
      this.router.navigate([Routing.PAGAMENTI_RICONCILIA_DETTAGLIO_PROVA_PAGAMENTO + this.idVerbale], {
        queryParams: {idAllegato : el.id}
      });
    }
    /*this.router.navigate(['/route-to'], {
        someData: { name: 'Some name', description: 'Some description' },
    });*/
  }

  pulisciFiltri() {
    //this.importo=this.importoVerbale;

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
	      if(this.soggetto.regioneNascita) {this.soggetto.regioneNascita.id = 0;}
	      if(this.soggetto.provinciaNascita) {this.soggetto.provinciaNascita.id = 0;}
	      if(this.soggetto.comuneNascita) {this.soggetto.comuneNascita.id = 0;}
	 }
  }

  manageMessage(err: ExceptionVO) {
    this.typeMessageTop = err.type;
    this.messageTop = err.message;
    this.timerShowMessageTop();
  }

  manageMessageTop(message: string, type: string) {
    this.typeMessageTop = type;
    this.messageTop = message;
    this.timerShowMessageTop();
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

  resetMessageTop() {
    this.showMessageTop = false;
    this.typeMessageTop = null;
    this.messageTop = null;
    clearInterval(this.intervalIdS);
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
    if(this.soggetto.ruolo.id.toString() === "1"){
      this.soggetto.ruolo = this.ruoloModel.find((el)=> el.id===1)
      this.soggetto.importoVerbale =this.importo;
    }else{
      this.soggetto.ruolo = this.ruoloModel.find((el)=> el.id===2)
      this.soggetto.importoVerbale = null
    }

    this.subscribers.salvaSoggettoVerbale = this.sharedVerbaleService
      .salvaSoggetto(this.soggetto, this.idVerbale)
      .subscribe(
        (data) => {
          if (data.comuneNascitaValido) {
	        this.dataSoggVerbale.splice(this.dataSoggVerbale.indexOf(this.soggettoModifica), 1);
          	this.dataSoggVerbale.push(TableSoggettiVerbale.map(data));
            this.pulisciFiltri();

            this.manageMessageTop("Soggetto modificato con successo", "SUCCESS");

            this.isModificaSoggetto = false;
            // ricarico le info del verbale per visualizzare gli importi aggiornati
            this.loadDatiVerbale();
          } else {
            this.manageMessageTop(
              "Il luogo di nascita selezionato non è compatibile con la data di nascita del soggetto. Inserire una regione-provincia-comune validi alla data di nascita.",
              "WARNING"
            );
          }

          this.loadedSalvaRicerca = true;
          //this.load();
        },
        (err) => {
          if (err instanceof ExceptionVO) {
            this.showResidenza = true;
            this.loadedSalvaRicerca = true;
            this.manageMessage(err);
            this.logger.error("Errore nell'eliminazione del soggetto");
          }
        }
      );
  }
  //modifica soggetto
  modifica(el: TableSoggettiVerbale) {
	console.log('modifica');
	console.log(el);
          this.modalita = "E";
	this.soggettoModifica = el;
    this.isModificaSoggetto = true;
	this.loadedSalvaRicerca = true;

	/**
	 * soggettoService.getSoggettoById(el.id,this.idVerbale).subscribe((data) => {
     */
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
    	//this.ricercaSoggetto(this.soggetto);
    });


  }

  manageLuoghiBySoggetto() {

      this.loadNazioni();
      this.loadRegioni();
      this.loadRuoli();
    if (
      this.soggetto.regioneResidenza != null &&
      this.soggetto.regioneResidenza.id != null
    ) {
      this.loadProvince(this.soggetto.regioneResidenza.id);
    }
    if (
      this.soggetto.provinciaResidenza != null &&
      this.soggetto.provinciaResidenza.id != null
    ) {
      this.loadComuni(this.soggetto.provinciaResidenza.id);
    }
  }


  loadNazioni() {
    this.loaderNazioni = false;
    this.subscribers.nazioni = this.luoghiService.getNazioni().subscribe(
      (data) => {
        this.nazioneResidenzaModel = data;
        this.loaderNazioni = true;
      },
      (err) => {
        this.logger.error("Errore nel recupero delle nazioni");
      }
    );
  }

  loadRegioni() {
    this.loaderRegioni = false;
    this.subscribers.regioni = this.luoghiService.getRegioni().subscribe(
      (data) => {
        this.regioneModel = data;
        this.loaderRegioni = true;
      },
      (err) => {
        this.logger.error("Errore nel recupero delle regioni");
      }
    );
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
        (err) => {
          this.logger.error("Errore nel recupero delle province");
        }
      );
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

  //PROVINCIA RESIDENZA
  public isDisabledComuneProvincia(type: string) {
    if (type == "P" && this.soggetto.regioneResidenza.id == null) return true;
    if (type == "C" && this.soggetto.provinciaResidenza.id == null) return true;
    return false;
  }


  formDisabled(form: NgForm) {
    if (this.soggetto.personaFisica)
      return !(this.formFisicoValid && form.valid);
    else return !(this.formGiuridicoValid && form.valid);
  }

  public setFormValid(event: boolean, type: string) {
    if (type == "F") this.formFisicoValid = event;
    if (type == "G") this.formGiuridicoValid = event;
  }

}
