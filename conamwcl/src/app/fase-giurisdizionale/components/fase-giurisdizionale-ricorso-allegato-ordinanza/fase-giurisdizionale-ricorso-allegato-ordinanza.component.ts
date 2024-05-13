import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { TipoAllegatoVO } from "../../../commons/vo/select-vo";
import { Routing } from "../../../commons/routing";
import { TipologiaAllegabiliRequest } from "../../../commons/request/tipologia-allegabili-request";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { Constants } from "../../../commons/class/constants";
import { SalvaAllegatoOrdinanzaRequest } from "../../../commons/request/ordinanza/salva-allegato-ordinanza-request";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { FascicoloService } from "../../../fascicolo/services/fascicolo.service";
import { RicercaProtocolloRequest } from "../../../commons/request/verbale/ricerca-protocollo-request";

@Component({
    selector: 'fase-giurisdizionale-ricorso-allegato-ordinanza',
    templateUrl: './fase-giurisdizionale-ricorso-allegato-ordinanza.component.html'
})
export class FaseGiurisdizionaleAllegatoRicorsoOrdinanzaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public tipoAllegatoModel: Array<TipoAllegatoVO>;

    public loadedCategoriaAllegato: boolean;
    public idOrdinanza: number;
    public loadedConfig: boolean;
    public loaded: boolean;

    public typeMessageTop: string;
    public showMessageTop: boolean;
    private intervalIdS: number = 0;
    public messageTop: string;

    constructor(
        private router: Router,        private logger: LoggerService,
        private activatedRoute: ActivatedRoute,        private sharedVerbaleService: SharedVerbaleService,
        private sharedOrdinanzaService: SharedOrdinanzaService,        private fascicoloService: FascicoloService,
    ) { }

    ngOnInit(): void {
        this.logger.init(FaseGiurisdizionaleAllegatoRicorsoOrdinanzaComponent.name);

        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idOrdinanza = +params['idOrdinanza'];
            if (isNaN(this.idOrdinanza))
                this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_RICORSO_RICERCA_ORDINANZA);
            // set riferimento x ricerca documento protocollato
            this.fascicoloService.ref = this.router.url;
            this.loadTipoAllegato();
        });
    }

    //recupero le tipologie di allegato allegabili
    loadTipoAllegato() {
        this.loadedCategoriaAllegato = false;
        let request = new TipologiaAllegabiliRequest();
        request.id = this.idOrdinanza;
        request.tipoDocumento = Constants.OPPOSIZIONE_GIURISDIZIONALE;
        this.subscribers.tipoAllegato = this.sharedOrdinanzaService.getTipologiaAllegatiAllegabiliByOrdinanza(request).subscribe(data => {
            this.tipoAllegatoModel = data;
            this.loadedCategoriaAllegato = true;
            this.loaded = true;
            // set riferimento x ricerca documento protocollato
            this.fascicoloService.tipoAllegatoModel = this.tipoAllegatoModel;
        }, err => {
            if (err instanceof ExceptionVO) {                this.manageMessage(err.type, err.message);            }
            this.logger.info("Errore nel recupero dei tipi di allegato");
            this.loadedCategoriaAllegato = true;
        });
    }

    ricercaProtocollo (ricerca: RicercaProtocolloRequest) {
        ricerca.idVerbale = this.idOrdinanza;
        this.loaded = false;
        this.sharedVerbaleService.ricercaProtocolloSuACTA(ricerca).subscribe(data => {
            this.loaded = true;
            let tipiAllegatoDuplicabili = [];
            if(!data.documentoProtocollatoVOList){                data.documentoProtocollatoVOList = [];
            } else if(data.documentoProtocollatoVOList[0]){                tipiAllegatoDuplicabili = data.documentoProtocollatoVOList[0].tipiAllegatoDuplicabili;
            }
            this.fascicoloService.categoriesDuplicated = tipiAllegatoDuplicabili;
            // set numero protocollo x ricerca documento protocollato
            this.fascicoloService.searchFormRicercaProtocol = ricerca.numeroProtocollo;
            // set dati x ricerca documento protocollato
            this.fascicoloService.dataRicercaProtocollo = data.documentoProtocollatoVOList;

            this.fascicoloService.successPage = Routing.FASE_GIURISDIZIONALE_RICORSO_DETTAGLIO_ORDINANZA + this.idOrdinanza;
            if (data.messaggio) {                this.fascicoloService.message = data.messaggio;
            }else{                this.fascicoloService.message = null;
            }
            const numpages:number = Math.ceil(+data.totalLineResp/+data.maxLineReq);
            this.fascicoloService.dataRicercaProtocolloNumPages = numpages;
			this.fascicoloService.dataRicercaProtocolloNumResults = +data.totalLineResp;
            this.router.navigateByUrl(Routing.FASCICOLO_ALLEGATO_DA_ACTA + this.idOrdinanza);
        }, err => {
            if (err instanceof ExceptionVO) {                this.manageMessage(err.type, err.message);            }
            this.loaded = true;
            this.logger.error("Errore nel recupero dei documenti protocollati");
        });
    }

    salvaAllegato(nuovoAllegato: SalvaAllegatoOrdinanzaRequest) {
        nuovoAllegato.idOrdinanza = this.idOrdinanza;
        // file > Back End
        this.loaded = false;
        this.subscribers.salvaAllegato = this.sharedOrdinanzaService.salvaAllegatoOrdinanza(nuovoAllegato).subscribe(data => {
            this.loadTipoAllegato();
            // reindirizzo pagina successo
            let azione = "allegato";
            this.router.navigate([Routing.FASE_GIURISDIZIONALE_RICORSO_DETTAGLIO_ORDINANZA + this.idOrdinanza, {action: azione}]);
            this.loaded = true;
        }, err => {
            if (err instanceof ExceptionVO) {                this.manageMessage(err.type, err.message);            }
            this.logger.error("Errore nel salvataggio dell'allegato");
            this.loaded = true;
        });
    }

    manageMessage(type: string, message: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 20;
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {                this.resetMessageTop();            }
        }, 1000);
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }

    ngOnDestroy(): void {
        this.logger.destroy(FaseGiurisdizionaleAllegatoRicorsoOrdinanzaComponent.name);
    }

}