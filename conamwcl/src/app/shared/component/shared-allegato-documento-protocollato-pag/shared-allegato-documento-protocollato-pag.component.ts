import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";


@Component({
    selector: 'shared-allegato-documento-protocollato-pag',
    templateUrl: './shared-allegato-documento-protocollato-pag.component.html',
    styleUrls: ['./shared-allegato-documento-protocollato-pag.component.scss']
})
export class SharedAllegatoDocumentoProtocollatoPagComponent implements OnInit, OnDestroy {
    @Input() currentPage: number;
    @Input() numPages: number;
    @Input() numResults: number;
    @Output("pageChange") private _pageChange = new EventEmitter<number>();

    constructor(private logger: LoggerService) { }

    ngOnInit() {
        this.logger.init(SharedAllegatoDocumentoProtocollatoPagComponent.name);
    }
    
    ngOnDestroy(): void {
        this.logger.destroy(SharedAllegatoDocumentoProtocollatoPagComponent.name);
    }

    createRange(page:number, maxPages:number) {
        let items: number[] = [];

        let start: number;
        let end: number;
        let next:number = +page+1;

        if (page > 1) {
            start = page - 1;
        } else {
            start = 1;
        }

        if (next > maxPages) {
            end = maxPages;
        } else {
            end = next;
        }
      
        for (var i = start; i <= end; i++) {
            items.push(i);
        }
        return items;
    }

    openPage(page) {
        this._pageChange.emit(page);
    }
}