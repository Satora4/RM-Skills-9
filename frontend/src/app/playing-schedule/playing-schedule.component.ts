import {Component, OnInit} from '@angular/core';


import {PlayingScheduleService} from "./playing-schedule.service";

import {PlayingScheduleModel} from "./playing-schedule.model";

@Component({
  selector: 'app-playing-schedule',
  templateUrl: './playing-schedule.component.html',
  styleUrls: ['./playing-schedule.component.css']
})
export class PlayingScheduleComponent implements OnInit {

  constructor(private gs: PlayingScheduleService) {
  }

  private gameArray: PlayingScheduleModel[] = [];

  ngOnInit(): void {
    this.loadGames()
  }

  loadGames(): void {
    this.gs.getGames().subscribe(games => {
      this.gameArray = games;
    })
  }

  getGame(): PlayingScheduleModel[] {
    return this.gameArray;
  }

}
