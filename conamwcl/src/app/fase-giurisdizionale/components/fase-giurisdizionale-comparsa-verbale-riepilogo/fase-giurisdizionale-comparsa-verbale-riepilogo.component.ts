import { Component, OnInit, OnDestroy } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { Constants } from "../../../commons/class/constants";
import { timer } from "rxjs/observable/timer";

@Component({
    selector: 'fase-giurisdizionale-comparsa-verbale-riepilogo',
    templateUrl: './fase-giurisdizionale-comparsa-verbale-riepilogo.component.html',
})
export class FaseGiurisdizionaleComparsaCostituzioneRispostaRiepilogoComponent implements OnInit, OnDestroy {
    public idVerbale: number;
    public subscribers: any = {};
    public allegatoEnable: boolean;
    constructor(
        private router: Router,
        private logger: LoggerService,
        private sharedVerbaleService: SharedVerbaleService,
        private activatedRoute: ActivatedRoute,
    ) { }

    ngOnInit(): void {
        this.logger.init(FaseGiurisdizionaleComparsaCostituzioneRispostaRiepilogoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idVerbale = +params['id'];
            if (isNaN(this.idVerbale)) this.router.navigateByUrl(Routing.FASE_GIURISDIZIONALE_INSERIMENTO_COMPARSA_RICERCA);
            this.isTipoAllegatoAllegabile();

            if (this.activatedRoute.snapshot.paramMap.get('action') == 'caricato')
                this.manageMessageTop("Inserimento lettera comparsa di costituzione e risposta effettuato con successo", 'SUCCESS');
            else if(this.activatedRoute.snapshot.paramMap.get('action') == 'caricati')
            this.manageMessageTop("Inserimento lettera comparsa di costituzione e risposta con allegati effettuato con successo", 'SUCCESS');
        });
    }

    isTipoAllegatoAllegabile() {
        this.subscribers.riconcilia = this.sharedVerbaleService.isTipoAllegatoAllegabile(this.idVerbale, Constants.COMPARSA).subscribe(data => {
            this.allegatoEnable = data;
        })
    }

    goToAggiugiControdeduzioni() {
        this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_INSERIMENTO_COMPARSA_ALLEGATO + this.idVerbale);
    }
    public typeMessageTop: String;
    public showMessageTop: boolean;
    public messageTop: String;

    manageMessageTop(message: string, type: string) {
        this.messageTop = message;
        this.typeMessageTop = type;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        const source = timer(1000, 1000).take(31);
        this.subscribers.timer = source.subscribe(val => {
            if (val == 30)
                this.resetMessageTop();
        });
    }
    ngOnDestroy(): void {        this.logger.destroy(FaseGiurisdizionaleComparsaCostituzioneRispostaRiepilogoComponent.name);
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
    }


}