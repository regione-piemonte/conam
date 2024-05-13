import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Config } from "../../../shared/module/datatable/classes/config";
import { ExceptionVO } from "../../../commons/vo/exceptionVO";
import { ConfigSharedService } from "../../../shared/service/config-shared.service";
import { SharedOrdinanzaService } from "../../../shared-ordinanza/service/shared-ordinanza.service";
import { PregressoVerbaleService } from "../../services/pregresso-verbale.service";
import { SharedOrdinanzaRiepilogoComponent } from "../../../shared-ordinanza/component/shared-ordinanza-riepilogo/shared-ordinanza-riepilogo.component";
import { SharedDialogComponent } from "../../../shared/component/shared-dialog/shared-dialog.component";
import { TemplateService } from "../../../template/services/template.service";
import { SalvaStatoOrdinanzaPregressiRequest } from "../../../commons/request/pregresso/salva-stato-ordinanza-pregressi.request";
import { AzioneOrdinanzaPregressiResponse } from "../../../commons/response/pregresso/azione-ordinanza-pregressi-response";
import { AzioneOrdinanzaRequest } from "../../../commons/request/ordinanza/azione-ordinanza-request";
import { SentenzaVO } from "../../../commons/vo/ordinanza/sentenza-vo";

@Component({
    selector: 'pregresso-disposizioni-riepilogo',
    templateUrl: './pregresso-disposizioni-riepilogo.component.html'
})
export class PregressoDisposizioniRiepilogoComponent implements OnInit, OnDestroy {

    public showTable: boolean;

    public subscribers: any = {};
    public idVerbale: number;

    public sentenze: Array<SentenzaVO>;

    public config: Config;
    public loaded: boolean = true;

    isRichiestaBollettiniSent: boolean;
    isFirstDownloadBollettini: boolean;
    loadedAction: boolean;
    loadedOrdinanza: boolean = false;
    idOrdinanza: number;
    azione: AzioneOrdinanzaPregressiResponse;

    salvaStatoOrdinanzaPregressiRequest: SalvaStatoOrdinanzaPregressiRequest = new SalvaStatoOrdinanzaPregressiRequest();
    statiOrdinanzaMessage: string;

    @ViewChild(SharedOrdinanzaRiepilogoComponent)
    sharedOrdinanzaRiepilogo: SharedOrdinanzaRiepilogoComponent;
    @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;

    public alertWarning: string;
    public subMessagess: Array<string>;
    public buttonConfirmTexts: string;
    public buttonAnnullaTexts: string;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private configSharedService: ConfigSharedService,
        private sharedOrdinanzaService: SharedOrdinanzaService,
        private activatedRoute: ActivatedRoute,
        private pregressoVerbaleService: PregressoVerbaleService,
        private templateService: TemplateService
    ) { }

    ngOnInit(): void {
        this.logger.init(PregressoDisposizioniRiepilogoComponent.name);
        this.config = this.configSharedService.configSentenze;
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            if (isNaN(this.idVerbale)){
                this.router.navigateByUrl(Routing.PREGRESSO_DATI);
            }
            if(this.activatedRoute.snapshot.paramMap.get('idOrdinanza')){
                // setto il riferimento per la ricerca documento protocollato
                this.idOrdinanza = parseInt(this.activatedRoute.snapshot.paramMap.get('idOrdinanza'));
            }

            if (isNaN(this.idOrdinanza)){
                this.router.navigateByUrl(Routing.PREGRESSO_DATI);
            }
            this.load();
        });
    }

    load():void{
        this.loaded = false;
        this.pregressoVerbaleService.getDatiSentenzaByIdOrdinanza(this.idOrdinanza).subscribe(
            data => {
                this.resetMessageTop();
                this.sentenze = data;
                this.showTable = true;
                this.loaded = true;
                this.scrollEnable = true;
            }, err => {
                if (err instanceof ExceptionVO) {
                    this.loaded = true;
                    this.manageMessageTop(err.message,err.type);
                }
                this.logger.error("Errore nella ricerca dell'ordinanza");
            }
        );
    }
    scrollEnable: boolean;

    messaggio(message: string){
        this.manageMessageTop(message,"DANGER");
    }

    ngAfterContentChecked() {
        let out: HTMLElement = document.getElementById("scrollBottom");
        if (this.loaded && this.scrollEnable && this.showTable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }
    }

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
    }

    //Messaggio top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;
    private intervalIdS: number = 0;

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 10;
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop();
            }
        }, 1000);
    }

    scrollTopEnable: boolean;
    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }

    goBack():void{
        this.router.navigateByUrl(Routing.PREGRESSO_RIEPILOGO_ORDINANZE + this.idVerbale);
    }

    ngOnDestroy(): void {
        this.logger.destroy(PregressoDisposizioniRiepilogoComponent.name);
    }

}