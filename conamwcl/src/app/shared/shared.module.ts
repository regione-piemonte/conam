import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SharedSpinnerComponent } from './component/shared-spinner/shared-spinner.component';
import { SharedDialogComponent } from './component/shared-dialog/shared-dialog.component';
import { SharedAlertComponent } from './component/shared-alert/shared-alert.component';
import { SharedSpinnerFormComponent } from './component/shared-spinner-form/shared-spinner-form.component';
import { SharedBreadcrumbsComponent } from './component/shared-breadcrumbs/shared-breadcrumbs.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DataTableModule } from './module/datatable/datatable.module';
import { AllegatoSharedService } from './service/allegato-shared.service';
import { ConfigSharedService } from './service/config-shared.service';
import { DecimalDirective } from './directive/decimal.directive';
import { NumeroRateDirective } from './directive/numero-rate.directive';
import { SharedAllegatoMetadatiInserimentoComponent } from './component/shared-allegato-metadati-inserimento/shared-allegato-metadati-inserimento.component';
import { SharedAllegatoRicercaProtocolloComponent } from './component/shared-allegato-form-ricerca-protocollo/shared-allegato-form-ricerca-protocollo.component';
import { NumberUtilsSharedService } from './service/number-utils-shared.service';
import { AllegatoUtilsSharedService } from './service/allegato-utils-shared.service';
import { FileSelectDirective } from 'ng2-file-upload';
import { SharedAllegatoComponent } from './component/shared-allegato/shared-allegato.component';
import { SharedAllegatoDocumentoProtocollatoComponent } from './component/shared-allegato-documento-protocollato/shared-allegato-documento-protocollato.component';
import { SharedAllegatoDocumentoProtocollatoPagComponent } from './component/shared-allegato-documento-protocollato-pag/shared-allegato-documento-protocollato-pag.component';
import { ListaTrasgressoriComponent } from './component/shared-allegato-metadati-inserimento/lista-trasgressori/lista-trasgressori.component';
import { RicercaStiloComponent } from './component/shared-allegato-metadati-inserimento/ricerca-stilo/ricerca-stilo.component';
import { DocumentoNonProtComponent } from './component/shared-allegato-metadati-inserimento/documento-non-prot/documento-non-prot.component';


@NgModule({
    imports: [CommonModule, RouterModule, DataTableModule.forRoot(), FormsModule, ReactiveFormsModule],
    exports: [
        SharedSpinnerComponent,
        SharedDialogComponent,
        SharedSpinnerFormComponent,
        SharedAlertComponent,
        SharedBreadcrumbsComponent,
        DataTableModule,
        DecimalDirective,
        NumeroRateDirective,
        SharedAllegatoMetadatiInserimentoComponent,
        SharedAllegatoRicercaProtocolloComponent,
        SharedAllegatoComponent,
        SharedAllegatoDocumentoProtocollatoComponent,
        FileSelectDirective,
        SharedAllegatoDocumentoProtocollatoPagComponent,
        ListaTrasgressoriComponent,
        RicercaStiloComponent,
        DocumentoNonProtComponent
    ],
    declarations: [SharedSpinnerComponent,
        SharedDialogComponent,
        SharedSpinnerFormComponent,
        SharedAlertComponent,
        SharedBreadcrumbsComponent,
        DecimalDirective,
        NumeroRateDirective,
        SharedAllegatoMetadatiInserimentoComponent,
        SharedAllegatoRicercaProtocolloComponent,
        FileSelectDirective,
        SharedAllegatoComponent,
        SharedAllegatoDocumentoProtocollatoComponent,
        SharedAllegatoDocumentoProtocollatoPagComponent,
        ListaTrasgressoriComponent,
        RicercaStiloComponent,
        DocumentoNonProtComponent
    ],
    providers: [AllegatoSharedService, ConfigSharedService, AllegatoUtilsSharedService, NumberUtilsSharedService,   DatePipe],
})
export class SharedModule { }