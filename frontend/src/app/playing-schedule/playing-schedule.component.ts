import {Component, OnInit} from '@angular/core';


import {PlayingScheduleService} from "./playing-schedule.service";

import {PlayingScheduleModel} from "./playing-schedule.model";

@Component({
  selector: 'app-playing-schedule',
  templateUrl: './playing-schedule.component.html',
  styleUrls: ['./playing-schedule.component.css']
})
export class PlayingScheduleComponent implements OnInit {

  constructor(private scheduleService: PlayingScheduleService) {
  }

  private games: PlayingScheduleModel[] = [];

  ngOnInit(): void {
    this.loadGames()
  }

  private loadGames(): void {
    this.scheduleService.getGames().subscribe(games => {
      this.games = games;
    })
  }

  getGames(): PlayingScheduleModel[] {
    return this.games;
  }

}
