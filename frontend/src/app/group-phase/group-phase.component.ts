import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {GameService} from '../game/game.service';

import {Tip} from "../tip/tip.model";
import {TipService} from "../tip/tip.service";
import {Game} from "../game/game.model";
import {MatDialog} from "@angular/material/dialog";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {GroupPhaseService} from "./group-phase.service";
import {MatButtonToggleChange} from "@angular/material/button-toggle";


export interface DialogData {
  tip1: number;
  tip2: number;
  country1: string;
  country2: string;
}



@Component({
  selector: 'app-game',
  templateUrl: './group-phase.component.html',
  styleUrls: ['./group-phase.component.css'],
})
export class GroupPhaseComponent implements OnInit {

  toggle: boolean = true;

  constructor(private gameService: GameService,
              private tipService: TipService,
              public dialog: MatDialog,
              private groupPhaseService: GroupPhaseService) {
  }

  toggleView(change: MatButtonToggleChange){
    this.toggle = change.value;
  }

  ngOnInit(): void {
  }



}
