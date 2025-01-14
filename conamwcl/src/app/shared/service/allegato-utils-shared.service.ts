import { ElementRef, Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Config } from "protractor";
import { AllegatoMultipleFields } from "../../commons/request/salva-allegato-request";
import { FileUploader, FileItem } from "ng2-file-upload";
import { timer } from "rxjs/observable/timer";
declare var $: any;

@Injectable()
export class AllegatoUtilsSharedService implements OnDestroy {
    allowedFilesType = ["pdf", "tiff", "jpg", "jpeg", "p7m"];
    constructor(
        private logger: LoggerService) {
        this.logger.createService(AllegatoUtilsSharedService.name);
    }
    public subscribers: any = {};
    b64toBlob(b64Data: string, contentType: string) {
        let sliceSize = 512;

        var byteCharacters = atob(b64Data);
        var byteArrays = [];

        for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
            var slice = byteCharacters.slice(offset, offset + sliceSize);

            var byteNumbers = new Array(slice.length);
            for (var i = 0; i < slice.length; i++) {
                byteNumbers[i] = slice.charCodeAt(i);
            }

            var byteArray = new Uint8Array(byteNumbers);

            byteArrays.push(byteArray);
        }

        var blob = new Blob(byteArrays, { type: contentType });
        return blob;
    }


  
    ngOnDestroy(): void {
        this.logger.destroyService(AllegatoUtilsSharedService.name);
    }
}