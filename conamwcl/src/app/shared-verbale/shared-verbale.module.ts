import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SharedVerbaleRicercaComponent } from './component/shared-verbale-ricerca/shared-verbale-ricerca.component';
import { SharedVerbaleRiepilogoComponent } from './component/shared-verbale-riepilogo/shared-verbale-riepilogo.component';
import { FormsModule } from '@angular/forms';
import { SharedVerbaleAllegatoTabellaComponent } from './component/shared-verbale-allegato-tabella/shared-verbale-allegato-tabella.component';
import { SharedModule } from '../shared/shared.module';
import { SharedVerbaleService } from './service/shared-verbale.service';
import { SharedVerbaleDettaglioSoggettiComponent } from './component/shared-verbale-dettaglio-soggetti/shared-verbale-dettaglio-soggetti.component';
import { SharedVerbaleConfigService } from './service/shared-verbale-config.service';
import { SharedVerbaleScrittoDifensivoSoggettoComponent } from './component/shared-verbale-scritto-difensivo-soggetto/shared-verbale-scritto-difensivo-soggetto.component';
import { SharedVerbaleScrittoDifensivoDatiVerbaleComponent } from './component/shared-verbale-scritto-difensivo-dati-verbale/shared-verbale-scritto-difensivo-dati-verbale.component';
import { SharedVerbaleRicercaScrittoDifensivoComponent } from './component/shared-verbale-ricerca-scritto-difensivo/shared-verbale-ricerca-scritto-difensivo.component';
import { ListaOrdinanzeComponent } from './component/shared-verbale-dettaglio-soggetti/lista-ordinanze-modal/lista-ordinanze';
import { NoteDetailComponent } from './component/shared-verbale-riepilogo/notes/note-detail/note-detail.component';
import { NotesListComponent } from './component/shared-verbale-riepilogo/notes/notes-list/notes-list.component';
//import { VerbaleSoggettoRicercaFisicaComponent } from "./component/shared-verbale-riepilogo/verbale-soggetto-ricerca-fisica/verbale-soggetto-ricerca-fisica.component";
//import { VerbaleSoggettoRicercaGiuridicaComponent } from "./component/shared-verbale-riepilogo/verbale-soggetto-ricerca-giuridica/verbale-soggetto-ricerca-giuridica.component";
import { SharedCommonsModule } from '../shared-commons/shared-commons.module';
@NgModule({
    imports: [CommonModule, RouterModule, FormsModule, SharedModule, SharedCommonsModule],
    exports: [
        SharedVerbaleRicercaComponent,
        SharedVerbaleRicercaScrittoDifensivoComponent,
        SharedVerbaleRiepilogoComponent,
        SharedVerbaleAllegatoTabellaComponent,
        SharedVerbaleDettaglioSoggettiComponent,
        SharedVerbaleScrittoDifensivoDatiVerbaleComponent,
        SharedVerbaleScrittoDifensivoSoggettoComponent,
        NoteDetailComponent,
        NotesListComponent,
    ],
    declarations: [
        SharedVerbaleRicercaComponent,
        ListaOrdinanzeComponent,
        SharedVerbaleRicercaScrittoDifensivoComponent,
        SharedVerbaleRiepilogoComponent,
        SharedVerbaleDettaglioSoggettiComponent,
        SharedVerbaleAllegatoTabellaComponent,
        SharedVerbaleScrittoDifensivoDatiVerbaleComponent,
        SharedVerbaleScrittoDifensivoSoggettoComponent,
//        VerbaleSoggettoRicercaGiuridicaComponent,
//        VerbaleSoggettoRicercaFisicaComponent,
        NoteDetailComponent,
        NotesListComponent,
    ],
    providers: [SharedVerbaleService, SharedVerbaleConfigService],
})
export class SharedVerbaleModule { }