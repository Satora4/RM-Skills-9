import {Component, OnInit} from '@angular/core';
import {PlayingScheduleService} from "./playing-schedule.service";
import {Game} from "./playing-schedule.model";

@Component({
  selector: 'app-playing-schedule',
  templateUrl: './playing-schedule.component.html',
  styleUrls: ['./playing-schedule.component.css']
})
export class PlayingScheduleComponent implements OnInit {

  constructor(private scheduleService: PlayingScheduleService) {
  }

  private games: Game[] = [];

  ngOnInit(): void {
    this.loadGames()
  }

  private loadGames(): void {
    this.scheduleService.getGames().subscribe(games => {
      this.games = games;
    })
  }

  getGames(): Game[] {
    return this.games;
  }

}
