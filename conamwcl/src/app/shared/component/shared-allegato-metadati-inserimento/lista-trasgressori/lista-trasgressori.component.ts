import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  TemplateRef,
  Input,
  Output,
  EventEmitter,
} from "@angular/core";
import { Config } from "../../../module/datatable/classes/config";
import { SoggettoPagamentoVO } from "../../../../commons/vo/verbale/soggetto-pagamento-vo";
import { Subscription } from "rxjs";
import { AllegatoSharedService } from "../../../service/allegato-shared.service";

@Component({
  selector: "lista-trasgressori",
  templateUrl: "./lista-trasgressori.component.html",
})
export class ListaTrasgressoriComponent implements OnInit, OnDestroy {
  @ViewChild("formTemplate") formTemplate: TemplateRef<any>;
  @ViewChild("checkedTemplate") checkedTemplate: TemplateRef<any>;

  @Input() listaTrasgressori: any[];
  @Input() disableFields: boolean = false;
  @Input() isEditProvaDelPagamento: boolean = false;
  @Output() getListaTrasgressori = new EventEmitter<SoggettoPagamentoVO[]>();
  showTable: boolean;
  loaded: boolean = false;
  public configTragressori: Config;
  subscriptionLista: Subscription;
  selectedItems: SoggettoPagamentoVO[] = [];
  provaDelPagamentoDetails: boolean = false;
  constructor(private allegatoService: AllegatoSharedService) {}

  ngOnInit(): void {
    this.completeInfo(this.listaTrasgressori);
    this.configTragressori = {
      pagination: {
        enable: false,
      },
      selection: {
        enable: true,
        selectionType: 1,
        // isSelectable: (el: any) => {
        //   console.log("prova", el);
        //   return el;
        // },
      },
      sort: {
        enable: false,
      },
      comparatorField: "id",
      columns: [
        {
          columnName: "codiceFiscale",
          displayName: "Identificativo Soggetto",
        },
        {
          columnName: "importoVerbale",
          displayName: "Importo in misura ridotta",
        },
        {
          columnName: "importoResiduoVerbale",
          displayName: "Importo residuo",
        },
        {
          columnName: "_cognomeNome",
          displayName: "Cognome Nome/Ragione Sociale",
        },
        {
          columnName: "",
          displayName: "importo Pagato",
          cellTemplate: this.formTemplate,
        },
        {
          columnName: "",
          displayName: "Pagamento Parziale",
          cellTemplate: this.checkedTemplate,
        },
      ],
    };

    this.showTable = true;
    this.loaded = true;
    this.provaDelPagamentoDetails = this.isEditProvaDelPagamento;
 /*if(this.provaDelPagamentoDetails && this.disableFields){
  this.disableFields= true
 }*/
    if (this.listaTrasgressori.length === 1) {
    
      //object copy with JSON.parse/JSON.stringify
      // the pointer may cause problems
      let el = this.listaTrasgressori[0];
      if (this.provaDelPagamentoDetails) {
        if (el.importoPagato < el.importoVerbale && el.importoPagato != null) {
          el.pagamentoParziale = true;
        }
      }
      this.selectedItems.push(JSON.parse(JSON.stringify(el)));
    } else {
      /// edit Prova del Pagamento flow
      this.editProvaDelPagamento();
    }
  }

  editProvaDelPagamento() {
    if (this.listaTrasgressori.length > 1) {
      this.listaTrasgressori.forEach((el) => {
        if (el.importoPagato > 0) {
          if (el.importoPagato < el.importoVerbale) {
            el.pagamentoParziale = true;
          }
          this.selectedItems.push(JSON.parse(JSON.stringify(el)));
          console.log(this.selectedItems, this.provaDelPagamentoDetails);
        }
      });
    }
  }
  completeInfo(lst) {
    lst.map(
      (item) =>
        (item._cognomeNome = item.personaFisica
          ? item.cognome + "  " + item.nome
          : item.ragioneSociale)
    );
  }
  checkBoolean(el): boolean {
    if (
      this.selectedItems &&
      this.selectedItems.length > 0 &&
      !this.disableFields
    ) {
      let check = this.selectedItems.find((element) => element.id === el.id);
      if (check) {
        return false;
      }
    }

    return true;
  }
  sendTheNewValue(el) {
    let i;
    let modifiedEl;

   let element=  this.selectedItems.find((selectedItem, index, arr) => {
      if (selectedItem.id === el.id) {
        i = index;
        modifiedEl = selectedItem;
        return true;
      }
    });
    if (modifiedEl) {
      this.selectedItems.splice(i, 1);
      this.selectedItems.push(modifiedEl);
    console.log('emit',this.selectedItems)
      this.getListaTrasgressori.emit(this.selectedItems);
      
    }
  }
  onSelected(el: SoggettoPagamentoVO[]) {
    this.selectedItems = el;
    console.log('emit',this.selectedItems)
    this.getListaTrasgressori.emit(this.selectedItems);
    
  }
  ngOnDestroy(): void {
    console.log('on destroy')
    this.selectedItems=null
    this.getListaTrasgressori.emit(this.selectedItems);
  }
}
