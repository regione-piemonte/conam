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
import { RicevutePagamentoVO } from "../../../commons/vo/ordinanza/ricevute-pagamento-vo";

@Component({
    selector: 'pregresso-ricevute-riepilogo',
    templateUrl: './pregresso-ricevute-riepilogo.component.html'
})
export class PregressoRicevuteRiepilogoComponent implements OnInit, OnDestroy {

    public showTable: boolean;

    public subscribers: any = {};
    public idVerbale: number;

    public ricevute: Array<RicevutePagamentoVO>;

    public config: Config;
    public loaded: boolean = true;

    isFirstDownloadBollettini: boolean;
    azione: AzioneOrdinanzaPregressiResponse;
    idOrdinanza: number;
    loadedAction: boolean;
    isRichiestaBollettiniSent: boolean;
    loadedOrdinanza: boolean = false;

    @ViewChild(SharedOrdinanzaRiepilogoComponent)
    sharedOrdinanzaRiepilogo: SharedOrdinanzaRiepilogoComponent;
    @ViewChild(SharedDialogComponent) sharedDialogs: SharedDialogComponent;

    statiOrdinanzaMessage: string;
    salvaStatoOrdinanzaPregressiRequest: SalvaStatoOrdinanzaPregressiRequest = new SalvaStatoOrdinanzaPregressiRequest();

    public subMessagess: Array<string>;
    public buttonConfirmTexts: string;
    public buttonAnnullaTexts: string;
    public alertWarning: string;

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
        this.logger.init(PregressoRicevuteRiepilogoComponent.name);
        this.config = this.configSharedService.configRicevute;
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
        this.pregressoVerbaleService.getRicevutePagamentoByIdOrdinanza(this.idOrdinanza).subscribe(
            data => {
                this.resetMessageTop();
                this.ricevute = data;
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

    ngAfterContentChecked() {
        let out: HTMLElement = document.getElementById("scrollBottom");
        if (this.loaded && this.scrollEnable && this.showTable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }
    }

    messaggio(message: string){
        this.manageMessageTop(message,"DANGER");
    }


    //Messaggio top
    public typeMessageTop: String;
    public showMessageTop: boolean;
    private intervalIdS: number = 0;
    public messageTop: String;

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
        this.scrollTopEnable = true;
    }

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

    ngAfterViewChecked() {
        let scrollTop: HTMLElement = document.getElementById("scrollTop");
        if (this.scrollTopEnable && scrollTop != null) {
            scrollTop.scrollIntoView();
            this.scrollTopEnable = false;
        }
    }
    scrollTopEnable: boolean;

    goBack():void{
        this.router.navigateByUrl(Routing.PREGRESSO_RIEPILOGO_ORDINANZE + this.idVerbale);
    }

    ngOnDestroy(): void {
        this.logger.destroy(PregressoRicevuteRiepilogoComponent.name);
    }

}