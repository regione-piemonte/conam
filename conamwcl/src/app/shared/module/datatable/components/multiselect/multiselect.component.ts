import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  OnChanges,
  OnDestroy,
  SimpleChanges,
  Inject,
} from "@angular/core";
import { Column } from "../../classes/settings";

@Component({
  selector: "multiselect",
  templateUrl: "./multiselect.component.html",
  styleUrls: ["./multiselect.component.css"],
})
export class MultiselectComponent implements OnInit, OnDestroy {
  @Input() list?: Column[];

  @Output() shareCheckedList = new EventEmitter();
  @Output() shareIndividualCheckedList = new EventEmitter();

  checkedList: Column[];
  currentSelected: {};

  constructor() {
    this.checkedList = [];
  }

  getSelectedValue(status: Boolean, value: Column) {

    if (status) {
      this.checkedList.push(value);
    } else {
      var index = this.checkedList.indexOf(value);
      this.checkedList.splice(index, 1);
    }

    this.currentSelected = { checked: status, name: value };

    //share checked list
    this.shareCheckedlist();

    //share individual selected item
    this.shareIndividualStatus();
  }
  shareCheckedlist() {
    this.shareCheckedList.emit(this.checkedList);
  }
  shareIndividualStatus() {
    this.shareIndividualCheckedList.emit(this.currentSelected);
  }

  ngOnInit() {
    this.checkedList = this.list.filter(item => item.checked);
  }

  ngOnDestroy() {}
}
