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

  constructor(private GameService: GameService) {
  }

  ngOnInit(): void {
    this.loadGames()
  }

  private loadGames(): void {
    this.GameService.getGames().subscribe(games => {
      this.games = games;
    })
  }

  getGames(): Game[] {
    return this.games;
  }

}
