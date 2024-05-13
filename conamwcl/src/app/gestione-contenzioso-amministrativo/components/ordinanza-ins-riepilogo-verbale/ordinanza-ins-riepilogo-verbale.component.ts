import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { UserService } from "../../../core/services/user.service";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";

@Component({
    selector: 'ordinanza-ins-riepilogo-verbale',
    templateUrl: './ordinanza-ins-riepilogo-verbale.component.html'
})
export class OrdinanzaInsVerbaleRiepilogoGestContAmministrativoComponent implements OnInit, OnDestroy {

    public subscribers: any = {};

    public loaded: boolean;
    public idVerbale: number;
    public showMessageTop: boolean;
    public messageTop: String;
    public typeMessageTop: String;
    private intervalIdS: number = 0;

    constructor(
        private router: Router,
        private logger: LoggerService,
        private activatedRoute: ActivatedRoute,
        private sharedVerbaleService: SharedVerbaleService
    ) { }

    ngOnInit(): void {
        this.logger.init(OrdinanzaInsVerbaleRiepilogoGestContAmministrativoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            if (isNaN(this.idVerbale))  this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_RICERCA_VERBALE);
            this.loaded = true;
        });
        this.sharedVerbaleService.getMessaggioManualeByIdVerbale(this.idVerbale).subscribe(data => {
            if(data){              this.manageMessage(data.message, data.type)            }
         });

    }

    goToCreaOrdinanza() {
        this.router.navigate([Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_CREA_ORDINANZA + this.idVerbale], { queryParams: { listaOrdinanze: true } });
    }

    ngOnDestroy(): void {
        this.logger.destroy(OrdinanzaInsVerbaleRiepilogoGestContAmministrativoComponent.name);
    }

    manageMessage(message: string, type: String) {
        this.messageTop = message;
        this.typeMessageTop = type;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 30
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {                this.resetMessageTop();
            }
        }, 1000);
    }

    resetMessageTop() {
        this.typeMessageTop = null;
        this.showMessageTop = false;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }


}