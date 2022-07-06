import {Component, OnInit} from '@angular/core';


import {GameService} from "../game.service";

import {Game} from "../Game";

@Component({
  selector: 'app-playing-schedule',
  templateUrl: './playing-schedule.component.html',
  styleUrls: ['./playing-schedule.component.css']
})
export class PlayingScheduleComponent implements OnInit {
  panelOpenState = false;
  date = "2022-11-12";

  constructor(private gs: GameService) {
  }

  private gameArray: Game[] = [];

  ngOnInit(): void {
    this.loadGames()
  }

  loadGames(): void {
    this.gs.getGames().subscribe(games => {
      this.gameArray = games;
    })
  }

  getGame(): Game[] {
    return this.gameArray;
  }

}
