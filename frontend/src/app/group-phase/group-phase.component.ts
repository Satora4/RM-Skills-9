import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {MatButtonToggleChange} from "@angular/material/button-toggle";

@Component({
  selector: 'app-game',
  templateUrl: './group-phase.component.html',
  styleUrls: ['./group-phase.component.css'],
})
export class GroupPhaseComponent implements OnInit {

  toggle: boolean = true;

  constructor(public dialog: MatDialog,) {
  }

  toggleView(change: MatButtonToggleChange){
    this.toggle = change.value;
  }

  ngOnInit(): void {
  }
}
