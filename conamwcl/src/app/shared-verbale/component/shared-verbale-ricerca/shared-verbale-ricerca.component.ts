import {  Component,
  OnInit,  OnDestroy,
  Input,
  Output,  EventEmitter,} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import {
  EnteVO,  StatoVerbaleVO,
  AmbitoVO,  NormaVO,
  SelectVO,
  IstruttoreVO,  ComuneVO } from "../../../commons/vo/select-vo";

import { UserService } from "../../../core/services/user.service";
import { RifNormativiService } from "../../../verbale/services/rif-normativi.service";
import { SoggettoVerbaleRequest } from "../../../commons/request/verbale/soggetto-verbale-request";
import { RicercaVerbaleRequest } from "../../../commons/request/verbale/ricerca-verbale-request";
import { SharedVerbaleService } from "../../service/shared-verbale.service";
import { Constants } from "../../../commons/class/constants";
import { CalendarUtils } from "../../../fase-giurisdizionale/module/calendar/component/commons/calendar-utils";
declare var $: any;
import { LuoghiService } from "../../../core/services/luoghi.service";

@Component({
  selector: "shared-verbale-ricerca",
  templateUrl: "./shared-verbale-ricerca.component.html",
  styleUrls: ["./shared-verbale-ricerca.component.scss"],
})

export class SharedVerbaleRicercaComponent implements OnInit, OnDestroy {
  public subscribers: any = {};

  // tab 1
  public entiLegge: Array<EnteVO> = new Array<EnteVO>();
  public entiAccertatori: Array<EnteVO> = new Array<EnteVO>();
  public tabVerbale: boolean = false;
  public loadedStatoVerbale: boolean;
  public loadedEnte: boolean;
  public loadedEnteAccertatore: boolean;
  public loadedAmbito: boolean;
  public loadedNorma: boolean;
  public statoVerbaleModel: Array<StatoVerbaleVO> = new Array<StatoVerbaleVO>();
  public ambitoModel = new Array<AmbitoVO>();
  public normaModel = new Array<NormaVO>();
  public statoSelected: StatoVerbaleVO;

  // tab 2
  public tabTrasgressore: boolean = false;
  public isTrasgressorePersonaFisica: boolean = false;
  public soggettoVerbaleTrasgressore: SoggettoVerbaleRequest;
  public isTrasgressorePersonaGiuridica: boolean = false;

  // tab 3
  public tabObbligatoInSolido: boolean = false;
  public soggettoVerbaleObbligato: SoggettoVerbaleRequest;
  public isObbligatoInSolidoPersonaFisica: boolean = false;
  public isObbligatoInSolidoPersonaGiuridica: boolean = false;

  // ricerca
  public ricercaVerbale: RicercaVerbaleRequest;
//loadedistruttore: boolean= false;
public loadedistruttore: boolean= true;
//funzionarioIstrModel= new Array<IstruttoreVO>();
public funzionarioIstrModel: IstruttoreVO[];

  public comuneEnteAccertatoreModel: Array<ComuneVO>;
  public loadedComuneEnteAccertatore: boolean = true;

  @Output() onSearch = new EventEmitter<RicercaVerbaleRequest>();
  @Input() request: RicercaVerbaleRequest;
  @Input() config: ConfigVerbaleRicerca;

  constructor(
    private sharedVerbaleService: SharedVerbaleService,
    private logger: LoggerService,
    private rifNormativiService: RifNormativiService,
    private userService: UserService,

    private luoghiService: LuoghiService,
  ) {}

  ngOnInit(): void {
    if (this.config == null) { this.config = this.createDefaultConfig();}
    this.loadedEnte = false;
    this.loadedAmbito = true;
    this.loadedNorma = true;
    this.logger.init(SharedVerbaleRicercaComponent.name);
    this.userService.profilo$.subscribe((data) => {
      if (data != null) {
        this.entiLegge = data.entiLegge;
        this.loadedEnte = true;
        this.entiAccertatori = data.entiAccertatore;
        this.loadedEnteAccertatore = true;
      }
    });
    this.setRequest();
    this.statoVerbale(this.config.tipoRicerca);
    this.loadIstruttoreByVerbale()
    this.comuniEnteValidInDate();
  }

  tabActions: any = {
    changeVerbale: () => {
      this.tabVerbale = !this.tabVerbale;
      if (!this.tabVerbale) {
        this.ricercaVerbale.datiVerbale.ente = null;
        this.ricercaVerbale.datiVerbale.numeroVerbale = null;
        this.ricercaVerbale.datiVerbale.numeroProtocollo = null;
        this.ricercaVerbale.datiVerbale.annoAccertamento = null;

        this.ricercaVerbale.datiVerbale.enteAccertatore = null;
        this.ricercaVerbale.datiVerbale.comuneEnteAccertatore = null;
        this.ricercaVerbale.datiVerbale.assegnatario = null;

        this.ricercaVerbale.datiVerbale.dataProcessoVerbaleDa = null;
        this.ricercaVerbale.datiVerbale.dataProcessoVerbaleA = null;
        this.ricercaVerbale.datiVerbale.dataAccertamentoDa =null;
          this.ricercaVerbale.datiVerbale.dataAccertamentoA =null;
       //   this.ricercaVerbale.datiVerbale.assegnatario =null;
          this.ricercaVerbale.datiVerbale.ambito = null;
        this.statoSelected = null;
        this.ricercaVerbale.datiVerbale.norma = null;
      }
    },
    changeTrasgressore: () => {
      this.tabTrasgressore = !this.tabTrasgressore;
      if (!this.tabTrasgressore) {
        this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
        this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
        this.isTrasgressorePersonaGiuridica = false;
        this.isTrasgressorePersonaFisica = false;
      } else {
        this.isTrasgressorePersonaGiuridica = false;
        this.isTrasgressorePersonaFisica = true;
      }
    },
    changeObbligatoInSolido: () => {
      this.tabObbligatoInSolido = !this.tabObbligatoInSolido;
      if (!this.tabObbligatoInSolido) {
        this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
        this.soggettoVerbaleObbligato.tipoSoggetto = "O";
        this.isObbligatoInSolidoPersonaGiuridica = false;
        this.isObbligatoInSolidoPersonaFisica = false;
      } else {
        this.isObbligatoInSolidoPersonaGiuridica = false;
        this.isObbligatoInSolidoPersonaFisica = true;
      }
    },
    checkTrasgressorePersonaFisica: () => {
      this.isTrasgressorePersonaFisica = !this.isTrasgressorePersonaFisica;
      this.isTrasgressorePersonaGiuridica = false;
      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.personaFisica = true;
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
    },
    checkTrasgressorePersonaGiuridica: () => {
      this.isTrasgressorePersonaGiuridica = !this.isTrasgressorePersonaGiuridica;
      this.isTrasgressorePersonaFisica = false;
      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.personaFisica = false;
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
    },
    checkObbligatoInSolidoPersonaFisica: () => {
      this.isObbligatoInSolidoPersonaGiuridica = false;
      this.isObbligatoInSolidoPersonaFisica = !this.isObbligatoInSolidoPersonaFisica;
      this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
      this.soggettoVerbaleObbligato.tipoSoggetto = "O";
      this.soggettoVerbaleObbligato.personaFisica = true;
    },
    checkObbligatoInsolidoPersonaGiuridica: () => {
      this.isObbligatoInSolidoPersonaGiuridica = !this.isObbligatoInSolidoPersonaGiuridica;
      this.isObbligatoInSolidoPersonaFisica = false;
      this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
      this.soggettoVerbaleObbligato.personaFisica = false;
      this.soggettoVerbaleObbligato.tipoSoggetto = "O";
    },
  };

  comuniEnteValidInDate() {
    this.subscribers.comuniEnteValidInDate = this.luoghiService
      .getcomuniEnteValidInDate()
      .subscribe(
        (data) => {
          this.comuneEnteAccertatoreModel = data;
        },
        (err) => { this.logger.error("Errore nel recupero dei comuni Ente");  }
      );
  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }
  ricercaFascicolo() {
    this.ricercaVerbale.soggettoVerbale = new Array<SoggettoVerbaleRequest>();
    if (this.tabTrasgressore) {
      this.soggettoVerbaleTrasgressore.personaFisica = this.isTrasgressorePersonaFisica;
      this.ricercaVerbale.soggettoVerbale.push(        this.soggettoVerbaleTrasgressore      );
    }
    if (this.tabObbligatoInSolido) {
      this.soggettoVerbaleObbligato.personaFisica = this.isObbligatoInSolidoPersonaFisica;
      this.ricercaVerbale.soggettoVerbale.push(this.soggettoVerbaleObbligato);
    }

    if (this.statoSelected != null){
      this.ricercaVerbale.datiVerbale.stato = [this.statoSelected];
    }    else { this.ricercaVerbale.datiVerbale.stato = this.statoVerbaleModel;}
    this.onSearch.emit(this.ricercaVerbale);
  }

  statoVerbale(tipoRicerca: string) {
    this.loadedStatoVerbale = false;
    this.subscribers.statoVerbale = this.sharedVerbaleService
      .getStatiRicercaVerbale()
      .subscribe(
        (data) => {
          if (tipoRicerca == "GC") {
            this.statoVerbaleModel = data.filter(
              (a) =>
                a.id == Constants.ID_STATO_VERBALE_IN_SCRITTI_DIFENSIVI ||
                a.id == Constants.ID_STATO_VERBALE_ACQUISITO ||
                a.id == Constants.ID_STATO_VERBALE_ORDINANZA
            );
          } else if (tipoRicerca == "GCORD") {
            this.statoVerbaleModel = data.filter(
              (a) =>
                a.id == Constants.ID_STATO_VERBALE_IN_SCRITTI_DIFENSIVI ||
                a.id == Constants.ID_STATO_VERBALE_ACQUISITO ||
                a.id == Constants.ID_STATO_VERBALE_ORDINANZA
            );
          } else {
            this.statoVerbaleModel = data;
          }
          this.loadedStatoVerbale = true;
        },
        (err) => {          this.logger.error("Errore nel recupero degli stati dei verbali");        }
      );
  }

  loadAmbitiByIdEnte() {
    this.loadedAmbito = false;
    this.subscribers.ambitiByIdEnte = this.rifNormativiService
      .getAmbitiByIdEnte(this.ricercaVerbale.datiVerbale.ente.id, true)
      .subscribe(
        (data) => {
          this.ambitoModel = data;
          this.loadedAmbito = true;
        },
        (err) => {          this.logger.error("Errore nel recupero degli ambiti");
        }
      );
  }

  loadNormeByIdAmbitoAndIdEnte() {
    this.loadedNorma = false;
    this.subscribers.normeByIdAmbitoAndIdEnte = this.rifNormativiService
      .getNormeByIdAmbitoAndIdEnte(
        this.ricercaVerbale.datiVerbale.ambito.id,
        this.ricercaVerbale.datiVerbale.ente.id,
        false
      )
      .subscribe(
        (data) => {
          this.normaModel = data;
          this.loadedNorma = true;
        },
        (err) => {this.logger.error("Errore nel recupero delle norme");
        }
      );
  }

  changeEnte() {
    this.ricercaVerbale.datiVerbale.ambito = null;
    this.ricercaVerbale.datiVerbale.norma = null;
    this.loadAmbitiByIdEnte();
  }
  changeEnteAccertatore(){
    const uno=1;
  }
  changeAmbito() {
    this.ricercaVerbale.datiVerbale.norma = null;
    this.loadNormeByIdAmbitoAndIdEnte();
  }

  pulisciFiltri() {
    this.ricercaVerbale = new RicercaVerbaleRequest();
    this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
    this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
    this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
    this.soggettoVerbaleObbligato.tipoSoggetto = "O";
    this.tabObbligatoInSolido = false;
    this.tabVerbale = false;
    this.isObbligatoInSolidoPersonaGiuridica = false;
    this.isObbligatoInSolidoPersonaFisica = false;
    this.tabTrasgressore = false;
    this.isTrasgressorePersonaGiuridica = false;
    this.isTrasgressorePersonaFisica = false;
    this.statoSelected = null;
  }

  disableForm(validForm: boolean) {
    let condition: boolean =
      !this.tabVerbale &&
      (!this.tabTrasgressore ||
        (!this.isTrasgressorePersonaFisica &&
          !this.isTrasgressorePersonaGiuridica)) &&
      (!this.tabObbligatoInSolido ||
        (!this.isObbligatoInSolidoPersonaFisica &&
          !this.isObbligatoInSolidoPersonaGiuridica));
    if (!validForm || condition) {return true;}
    return false;
  }

  isDisable(field: string, type: string) {

    if (type == "T") {
      let condFisica: Boolean =
        !this.tabTrasgressore || !this.isTrasgressorePersonaFisica;
      if (field == "CFT")
        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.cognome) ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.nome)
        );
      if (field == 'CG')
      {

        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.codiceFiscale)

        );}
        if (field == 'NM')
      {

        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.codiceFiscale)
          //|| !this.isEmpty(this.soggettoVerbaleTrasgressore.cognome)
        );}

      let condGiuridica: Boolean =
        !this.tabTrasgressore || !this.isTrasgressorePersonaGiuridica;
      if (field == "DN")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.partitaIva) ||
          !this.isEmpty(
            this.soggettoVerbaleTrasgressore.codiceFiscalePersGiuridica
          )
        );
      if (field == "PI")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.ragioneSociale) ||
          !this.isEmpty(
            this.soggettoVerbaleTrasgressore.codiceFiscalePersGiuridica
          )
        );
      if (field == "CFPG")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.partitaIva) ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.ragioneSociale)
        );
    }
    if (type == "O") {
      let condFisica: Boolean =
        !this.tabObbligatoInSolido || !this.isObbligatoInSolidoPersonaFisica;
      if (field == "CFT")
        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.cognome) ||
          !this.isEmpty(this.soggettoVerbaleObbligato.nome)
        );
      if (field == "CG" )

        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.codiceFiscale)
        );
        if (field == 'NM')
        {

          return (
            condFisica ||
            !this.isEmpty(this.soggettoVerbaleObbligato.codiceFiscale)
            //|| !this.isEmpty(this.soggettoVerbaleObbligato.cognome)
          );}
      let condGiuridica: Boolean =
        !this.tabObbligatoInSolido || !this.isObbligatoInSolidoPersonaGiuridica;
      if (field == "DN")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.partitaIva) ||
          !this.isEmpty(
            this.soggettoVerbaleObbligato.codiceFiscalePersGiuridica
          )
        );
      if (field == "PI")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.ragioneSociale) ||
          !this.isEmpty(
            this.soggettoVerbaleObbligato.codiceFiscalePersGiuridica
          )
        );
      if (field == "CFPG")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.partitaIva) ||
          !this.isEmpty(this.soggettoVerbaleObbligato.ragioneSociale)
        );
    }
  }

  isEmpty(str) {    return !str || 0 === str.length;  }
loadIstruttoreByVerbale() {
  this.subscribers.istruttoreByIdRegione = this.sharedVerbaleService
    .getIstruttoreByIdVerbale()
    .subscribe(
      (data) => {
        this.funzionarioIstrModel = data;
        if (this.ricercaVerbale.datiVerbale && this.ricercaVerbale.datiVerbale.assegnatario){
          const matchingFunz = this.funzionarioIstrModel.find(funz => funz.denominazione === this.ricercaVerbale.datiVerbale.assegnatario.denominazione);
          this.ricercaVerbale.datiVerbale.assegnatario = matchingFunz;
        }
      },
      (err) => { this.logger.error("Errore nel recupero dei funzionari istruttori"); }
    );
}

  setRequest() {
    if (this.request == null) this.pulisciFiltri();
    else {
      this.ricercaVerbale = this.request;

      /*
      if (this.ricercaVerbale.datiVerbale.ente != null) {
        this.loadAmbitiByIdEnte();
        this.loadNormeByIdAmbitoAndIdEnte();
        this.tabVerbale = true;
      }*/

      if(
        (this.ricercaVerbale.datiVerbale.numeroProtocollo && this.ricercaVerbale.datiVerbale.numeroProtocollo != null) ||
        (this.ricercaVerbale.datiVerbale.numeroVerbale && this.ricercaVerbale.datiVerbale.numeroVerbale != null) ||
        (this.ricercaVerbale.datiVerbale.annoAccertamento && this.ricercaVerbale.datiVerbale.annoAccertamento > 0) ||
        (this.ricercaVerbale.datiVerbale.ente && this.ricercaVerbale.datiVerbale.ente != null) ||
        (this.ricercaVerbale.datiVerbale.enteAccertatore && this.ricercaVerbale.datiVerbale.enteAccertatore != null) ||
        (this.ricercaVerbale.datiVerbale.comuneEnteAccertatore && this.ricercaVerbale.datiVerbale.comuneEnteAccertatore != null) ||
        (this.ricercaVerbale.datiVerbale.assegnatario && this.ricercaVerbale.datiVerbale.assegnatario != null) ||
        (this.ricercaVerbale.datiVerbale.dataAccertamentoDa && this.ricercaVerbale.datiVerbale.dataAccertamentoDa != null) ||
        (this.ricercaVerbale.datiVerbale.dataAccertamentoA && this.ricercaVerbale.datiVerbale.dataAccertamentoA != null) ||
        (this.ricercaVerbale.datiVerbale.dataProcessoVerbaleDa && this.ricercaVerbale.datiVerbale.dataProcessoVerbaleDa != null) ||
        (this.ricercaVerbale.datiVerbale.dataProcessoVerbaleA && this.ricercaVerbale.datiVerbale.dataProcessoVerbaleA != null)
      ){
        this.tabVerbale = true;
        if (this.ricercaVerbale.datiVerbale.ente != null) {
          this.loadAmbitiByIdEnte();
          this.loadNormeByIdAmbitoAndIdEnte();
        }
      }

      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
      this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
      this.soggettoVerbaleObbligato.tipoSoggetto = "O";
      this.statoSelected =
        this.ricercaVerbale.datiVerbale.stato != null &&
        this.ricercaVerbale.datiVerbale.stato.length == 1
          ? this.ricercaVerbale.datiVerbale.stato[0]
          : null;
      if (
        this.request.soggettoVerbale != null &&
        this.request.soggettoVerbale.length > 0
      ) {
        for (let x of this.request.soggettoVerbale) {
          if (x.tipoSoggetto == "T") {
            this.soggettoVerbaleTrasgressore = x;
            this.tabTrasgressore = true;
            if (x.personaFisica) {this.isTrasgressorePersonaFisica = true;}
            else {this.isTrasgressorePersonaGiuridica = true;}
          } else if (x.tipoSoggetto == "O") {
            this.soggettoVerbaleObbligato = x;
            this.tabObbligatoInSolido = true;
            if (x.personaFisica) {this.isObbligatoInSolidoPersonaFisica = true;}
            else this.isObbligatoInSolidoPersonaGiuridica = true;
          }
        }
      }
    }
  }
  manageDatePicker(event: any, i: number) {
    if ($("#datetimepicker1").length > 0) {
      $("#datetimepicker1").datetimepicker({
        format: "DD/MM/YYYY",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if ($("#datetimepicker2").length > 0) {
      $("#datetimepicker2").datetimepicker({
        format: "DD/MM/YYYY",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if ($("#datetimepicker3").length > 0) {
      $("#datetimepicker3").datetimepicker({
        format: "DD/MM/YYYY",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if ($("#datetimepicker4").length > 0) {
      $("#datetimepicker4").datetimepicker({
        format: "DD/MM/YYYY",
        maxDate: new Date().setDate(new Date().getDate() + 1),
        disabledDates: [new Date().setDate(new Date().getDate() + 1)],
        widgetPositioning: {
          horizontal: "right",
          vertical: "top",
        },
      });
    }
    if (event != null) {
      switch (i) {
        case 1: {
          this.ricercaVerbale.datiVerbale.dataAccertamentoDa = event.srcElement.value;
          this.ricercaVerbale.datiVerbale.dataAccertamentoA = event.srcElement.value;
          break;
        }
        case 2: {
          this.ricercaVerbale.datiVerbale.dataAccertamentoA = event.srcElement.value;
          let flag: boolean = CalendarUtils.before(
            this.ricercaVerbale.datiVerbale.dataAccertamentoDa,
            this.ricercaVerbale.datiVerbale.dataAccertamentoA
          );
          if (!flag) {
            this.ricercaVerbale.datiVerbale.dataAccertamentoDa = event.srcElement.value;
          }
          break;
        }
        case 3: {
          this.ricercaVerbale.datiVerbale.dataProcessoVerbaleDa = event.srcElement.value;
          this.ricercaVerbale.datiVerbale.dataProcessoVerbaleA = event.srcElement.value;
          break;
        }
        case 4: {
          this.ricercaVerbale.datiVerbale.dataProcessoVerbaleA = event.srcElement.value;
          let flag: boolean = CalendarUtils.before(
            this.ricercaVerbale.datiVerbale.dataProcessoVerbaleDa,
            this.ricercaVerbale.datiVerbale.dataProcessoVerbaleA
          );
          if (!flag) {
            this.ricercaVerbale.datiVerbale.dataProcessoVerbaleDa = event.srcElement.value;
          }
          break;
        }
      }
    }
  }
  interceptor(event: Event) {    event.preventDefault();  }
  compareFn(c1: SelectVO, c2: SelectVO): boolean {    return c1 && c2 ? c1.id === c2.id : c1 === c2;  }

  ngOnDestroy(): void {    this.logger.destroy(SharedVerbaleRicercaComponent.name);  }

  private createDefaultConfig(): ConfigVerbaleRicerca {    return { showFieldStatoVerbale: true, tipoRicerca: "GF" }; //GF: Gestione Fascicolo
  }
}

// aggiungere eventuali altre proprieta
export interface ConfigVerbaleRicerca {
  showFieldStatoVerbale: boolean;
  tipoRicerca: string;
}
