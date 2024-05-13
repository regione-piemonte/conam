import { Component, OnInit, OnDestroy, Input, OnChanges } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SoggettoVO } from "../../../commons/vo/verbale/soggetto-vo";
import { ActivatedRoute } from "@angular/router";
import { SharedVerbaleService } from "../../../shared-verbale/service/shared-verbale.service";
import { VerbaleVO } from "../../../commons/vo/verbale/verbale-vo";
import { RiepilogoVerbaleVO } from "../../../commons/vo/verbale/riepilogo-verbale-vo";
import { VerbaleService } from "../../../verbale/services/verbale.service";

@Component({
    selector: 'shared-soggetto-ruolo',
    templateUrl: './shared-soggetto-ruolo.html'
})
export class SharedSoggettoRuolo implements OnInit, OnDestroy {

    public subscribers: any = {};

    @Input("soggetto") soggetto: SoggettoVO;
    @Input("loaded") loaded: boolean;
    @Input("ruolo") ruolo: string;
    @Input("nota") nota: string;
 importoVerbale: number;
 importoResiduo: number;
    public idVerbale: number;
    public isNota: boolean = false;

    constructor(
        private logger: LoggerService,
        private verbaleService:VerbaleService,

        private activatedRoute: ActivatedRoute,

    ) { }

    ngOnInit(): void {
        this.loaded= false;
        
            this.importoResiduo= this.soggetto.importoResiduoVerbale? this.soggetto.importoResiduoVerbale:0;
            this.importoVerbale= this.soggetto.importoVerbale;
        /*
        this.subscribers.route = this.activatedRoute.params.subscribe((params) => {
            this.idVerbale = +params["idVerbale"];
        this.verbaleService.getVerbaleById(this.idVerbale).subscribe((data: VerbaleVO)=>{
            //this.importoResiduo= data.importoResiduo
            //this.importoVerbale= data.importo
            this.importoResiduo= this.soggetto.importoResiduoVerbale
            this.importoVerbale= this.soggetto.importoVerbale
        })
        
        })
*/
      	this.loaded=true;
        if(this.nota!="null")
            this.isNota = true;
    }

    ngOnDestroy(): void {
        this.logger.destroy(SharedSoggettoRuolo.name);
    }

}