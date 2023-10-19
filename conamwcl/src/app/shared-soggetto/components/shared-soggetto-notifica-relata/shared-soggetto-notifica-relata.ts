import { Component, OnInit, OnDestroy, Input, OnChanges } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";

@Component({
    selector: 'shared-soggetto-notifica-relata',
    templateUrl: './shared-soggetto-notifica-relata.html'
})
export class SharedSoggettoNotificaRelata implements OnInit, OnDestroy {

    public subscribers: any = {};

    @Input("soggetto") soggetto: SoggettoVO;
    @Input("loaded") loaded: boolean;
    @Input("ruolo") ruolo: string;
    @Input("nota") nota: string;

    public isNota: boolean = false;

    constructor(
        private logger: LoggerService,
    ) { }

    ngOnInit(): void {
        if(this.nota!="null")
            this.isNota = true;
        if(!this.soggetto.relataNotifica.importoSpeseNotifica)
        this.soggetto.relataNotifica.importoSpeseNotifica = 0
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedSoggettoNotificaRelata.name);
    }

}