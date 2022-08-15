import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {GameService} from './game.service';

import {Tip} from "../tip/tip.model";
import {TipService} from "../tip/tip.service";
import {Game} from "./game.model";


@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
})
export class GameComponent implements AfterViewInit, OnInit {
  dataSource = new MatTableDataSource();
  columnsToDisplay = ['gameTime', 'gameLocation', 'teamCountry1', 'pointsTeam1', 'pointsTeam2', 'teamCountry2', 'tipTeam1', 'tipTeam2', 'button'];
  public tipTeam1: any = {};
  public tipTeam2: any = {};

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private gameService: GameService, private tipService: TipService) {
  }

  ngOnInit(): void {
    this.loadGames();
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  public saveTip(userId: number, tipTeam1: number, tipTeam2: number, game: Game, gameId: number) {

    let tip: Tip = {
      userId: userId,
      tipTeam1: tipTeam1,
      tipTeam2: tipTeam2,
      gameId: gameId,
      teamCountry1: game.teamCountry1,
      teamCountry2: game.teamCountry2,
      pointsTeam1: game.pointsTeam1,
      pointsTeam2: game.pointsTeam2,
      gameTime: game.gameTime
    }
    console.log(tip)
    this.addTip(tip)
  }

  private addTip(tip: Tip): void {
    this.tipService.addTip(tip).subscribe(tip => {
      console.log(tip)
    })
  }

  private loadGames(): void {
    this.gameService.getGames().subscribe((games) => {
      this.dataSource.data = games;
    });
  }
}
