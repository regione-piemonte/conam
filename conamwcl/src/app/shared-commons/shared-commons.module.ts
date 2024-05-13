import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { SharedCommonsRicercaPianoRateComponent } from "./components/shared-commons-ricerca-piano-rate/shared-commons-ricerca-piano-rate.component";
import { SharedCommonsSoggettoRicercaFisicaComponent } from "./components/shared-commons-soggetto-ricerca-fisica/shared-commons-soggetto-ricerca-fisica.component";
import { SharedCommonsSoggettoRicercaGiuridicaComponent } from "./components/shared-commons-soggetto-ricerca-giuridica/shared-commons-soggetto-ricerca-giuridica.component";
/*
import { SharedPagamentiService } from "./services/shared-pagamenti.service";
import { SharedPagamentiRicercaPianoComponent } from "./components/shared-pagamenti-ricerca-piano/shared-pagamenti-ricerca-piano.component";
import { SharedPagamentiDettaglioPianoComponent } from "./components/shared-pagamenti-dettaglio-piano/shared-pagamenti-dettaglio-piano.component";
import { SharedRateConfigService } from "./services/shared-pagamenti-config.service";
import { SharedPagamentiRicercaRateComponent } from "./components/shared-pagamenti-ricerca-rate/shared-pagamenti-ricerca-rate.component";
*/
@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        SharedModule,
    ],
    exports: [
       /* SharedPagamentiRicercaPianoComponent,
        SharedPagamentiRicercaRateComponent,
        SharedPagamentiDettaglioPianoComponent  */
        SharedCommonsRicercaPianoRateComponent,
        SharedCommonsSoggettoRicercaFisicaComponent,
        SharedCommonsSoggettoRicercaGiuridicaComponent
    ],
    declarations: [
      /*
        SharedPagamentiRicercaPianoComponent,
        SharedPagamentiRicercaRateComponent,
        SharedPagamentiDettaglioPianoComponent
        */
        SharedCommonsRicercaPianoRateComponent,
        SharedCommonsSoggettoRicercaFisicaComponent,
        SharedCommonsSoggettoRicercaGiuridicaComponent
    ],
    providers: [
      /*SharedPagamentiService, SharedRateConfigService*/
    ]
})
export class SharedCommonsModule { }