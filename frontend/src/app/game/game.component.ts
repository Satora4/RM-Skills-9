import {Component, OnInit} from '@angular/core';
import {GameService} from "./game.service";
import {Game} from "./game.model";


@Component({
  selector: 'app-playing-schedule',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  private games: Game[] = [];

  constructor(private gameService: GameService) {
  }

  ngOnInit(): void {
    this.loadGames()
  }

  private loadGames(): void {
    this.gameService.getGames().subscribe(games => {
      this.games = games;
    })
  }

  getGames(): Game[] {
    return this.games;
  }

  displayedColumns: string[] = ['gameTime', 'gameLocation', 'teamCountry1', 'pointsTeam1', 'pointsTeam2', 'teamCountry2'];
}
