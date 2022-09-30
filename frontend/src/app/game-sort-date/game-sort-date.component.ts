import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Tip} from "../tip/tip.model";
import {MatSort} from "@angular/material/sort";
import {GameService} from "../game/game.service";
import {TipService} from "../tip/tip.service";
import {MatDialog} from "@angular/material/dialog";
import {GroupPhaseService} from "../group-phase/group-phase.service";
import {Game} from "../game/game.model";
import {PopUpComponent} from "../pop-up/pop-up.component";


export interface DialogData {
  tip1: number;
  tip2: number;
  country1: string;
  country2: string;
}

export interface DataObject {
  dataSource: MatTableDataSource<any>;
  groupDate: Date;
}


@Component({
  selector: 'app-game-sort-date',
  templateUrl: './game-sort-date.component.html',
  styleUrls: ['./game-sort-date.component.css']
})
export class GameSortDateComponent implements OnInit {
  dataObjects: DataObject[] = [];
  columnsToDisplay = ['gameTime', 'gameLocation', 'teamCountry1', 'flag1', 'pointsTeam1', 'colon', 'pointsTeam2', 'flag2', 'teamCountry2', 'tipTeam1', 'tipTeam2', 'button'];
  public tipTeam1: any = {};
  public tipTeam2: any = {};
  public tips: Tip[] = [];
  public readonly dash = '—';

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private gameService: GameService,
              private tipService: TipService,
              public dialog: MatDialog,
              private groupPhaseService: GroupPhaseService) {
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


  public getTipTeam1ByGameId(gameId: number): string {
    let tip: string = this.dash;
    for (let i = 0; i < this.tips.length; i++) {
      if (this.tips[i].gameId == gameId) {
        tip = this.tips[i].tipTeam1.toString();
      }
    }
    return tip;
  }

  public getTipByGameId(gameId: number): Tip {
    for (let i = 0; i < this.tips.length; i++) {
      if (this.tips[i].gameId == gameId) {
        return this.tips[i];
      }
    }
    throw new Error("tip isn't in database")
  }

  public getTipTeam2ByGameId(gameId: number): string {
    let tip: string = this.dash;
    for (let i = 0; i < this.tips.length; i++) {
      if (this.tips[i].gameId == gameId) {
        tip = this.tips[i].tipTeam2.toString();
      }
    }
    return tip;
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

  public isTipAllowed(game: Game): boolean {
    if (Date.parse(game.gameTime.toString()) > Date.now()) {
      return true;
    } else {
      return false;
    }
  }

  private addTip(tip: Tip, game: Game){
    if (this.isTipAllowed(game)) {
      this.tipService.addTip(tip).subscribe(tip => {
        location.reload()
      })
    } else {
      alert("Spiel wird oder wurde bereits gespielt.");
    }
  }

  private updateTip(tip: Tip, game: Game): void {
    if (this.isTipAllowed(game)) {
      this.tipService.updateTip(tip).subscribe(tip => {
      })
    } else {
      alert("Spiel wird oder wurde bereits gespielt.");
    }
  }


  loadGames(): void {
    this.groupPhaseService.getGamesOrderByDate().subscribe((groupPhaseModelsForDate) => {
      console.log(groupPhaseModelsForDate)
      for (let groupPhaseModel of groupPhaseModelsForDate) {
        let dataSource = new MatTableDataSource();
        dataSource.data = groupPhaseModel.games;
        console.log(groupPhaseModel)
        let dataObject: DataObject = {
          dataSource: dataSource,
          groupDate: groupPhaseModel.groupDate
        }
        this.dataObjects.push(dataObject);
      }
      console.log(this.dataObjects);
    });
  }
}
