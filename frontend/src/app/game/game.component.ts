import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {GameService} from './game.service';

import {Tip} from "../tip/tip.model";
import {TipService} from "../tip/tip.service";
import {Game} from "./game.model";
import {MatDialog} from "@angular/material/dialog";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {getTipFromTeamByGameId, isTipAllowedForGame} from "../tip/tip.util";


export interface DialogData {
  tip1: number;
  tip2: number;
  country1: string;
  country2: string;
}

export interface DataObject {
  dataSource: MatTableDataSource<any>;
  phase: string;
}

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
})
export class GameComponent implements OnInit {
  dataObjects: DataObject[] = [];


  columnsToDisplay = ['gameTime', 'gameLocation', 'teamCountry1', 'flag1', 'pointsTeam1', 'colon', 'pointsTeam2', 'flag2', 'teamCountry2', 'tipTeam1', 'tipTeam2', 'button'];
  public tipTeam1: any = {};
  public tipTeam2: any = {};
  public tips: Tip[] = [];
  public readonly dash = '—';

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private gameService: GameService,
              private tipService: TipService,
              public dialog: MatDialog) {
    this.loadTipsByUser(1)
  }

  ngOnInit(): void {
    this.loadGames();
  }

  public openTipWindow(game: Game): void {
    const dialogRef = this.dialog.open(PopUpComponent, {
      width: '250px',
      data: {
        tip1: this.getTipByGameId(game.id).tipTeam1,
        tip2: this.getTipByGameId(game.id).tipTeam2,
        country1: game.teamCountry1,
        country2: game.teamCountry2
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result)
      this.saveTip(this.getTipByGameId(game.id).userId, result.tip1, result.tip2, game)
      window.location.reload();
    });
  }

  public getTipByGameId(gameId: number): Tip {
    for (let i = 0; i < this.tips.length; i++) {
      if (this.tips[i].gameId == gameId) {
        return this.tips[i];
      }
    }
    throw new Error("tip isn't in database")
  }

  public loadTipsByUser(userId: number) {
    this.tipService.getTips(userId).subscribe((tips) => {
      this.tips = tips;
    });
  }

  public saveTip(userId: number, tipTeam1: number, tipTeam2: number, game: Game) {

    let tip: Tip = {
      userId: userId,
      tipTeam1: tipTeam1,
      tipTeam2: tipTeam2,
      points: 0,
      gameId: game.id,
      teamCountry1: game.teamCountry1,
      teamCountry2: game.teamCountry2,
      pointsTeam1: game.pointsTeam1,
      pointsTeam2: game.pointsTeam2,
      gameTime: game.gameTime
    }
    let requestToggle: boolean = false;
    for (let i = 0; i < this.tips.length; i++) {
      if (this.tips[i].gameId == tip.gameId) {
        requestToggle = true;
        break;
      }
    }

    if (requestToggle) {
      this.updateTip(tip, game)
    } else {
      this.addTip(tip, game);
    }
  }

  public getTipFromTeamByGameId(gameId: number, tipTeam: number, tips: Tip[]): string {
    return getTipFromTeamByGameId(gameId, tipTeam, tips);
  }

  public isTipAllowed(game: Game, tipTeam: number): boolean {
    return isTipAllowedForGame(game, this.tips, tipTeam);
  }

  public isDateValid(game: Game): boolean {
    return Date.parse(game.gameTime.toString()) > Date.now();
  }

  private addTip(tip: Tip, game: Game){
    if (Date.parse(game.gameTime.toString()) > Date.now()) {
      this.tipService.addTip(tip).subscribe(tip => {
        location.reload()
      })
    } else {
      alert("Spiel wird oder wurde bereits gespielt.");
    }
  }

  private updateTip(tip: Tip, game: Game): void {
    if (Date.parse(game.gameTime.toString()) > Date.now()) {
      this.tipService.updateTip(tip).subscribe(tip => {
      })
    } else {
      alert("Spiel wird oder wurde bereits gespielt.");
    }
  }

  private loadGames(): void {
    this.gameService.getKoGames().subscribe((koPhaseModels) => {
      let sortedKoPhaseModels = koPhaseModels.sort((a, b) => b.games.length - a.games.length);
      for (let sortedKoPhaseModel of sortedKoPhaseModels) {
        let dataSource = new MatTableDataSource();
        dataSource.data = sortedKoPhaseModel.games;
        console.log(sortedKoPhaseModel)
        let dataObject: DataObject = {
          dataSource: dataSource,
          phase: this.getPhase(sortedKoPhaseModel.phase.toString())
        }
        this.dataObjects.push(dataObject);
      }
    });
  }

  private getPhase(phase: string): string {
    switch (phase) {
      case "FINAL": {
        return "Final";
      }

      case "SEMI_FINAL": {
        return "Halbfinal";
      }

      case "QUARTER_FINAL": {
        return "Viertelfinal";
      }

      case "ROUND_OF_16": {
        return "Achtelfinal";
      }

      default: {
        return "phase"
      }
    }
  }
}


