import {Component, OnInit, ViewChild} from '@angular/core';
import {Tip} from "../tip/tip.model";
import {MatSort} from "@angular/material/sort";
import {GameService} from "../game/game.service";
import {TipService} from "../tip/tip.service";
import {MatDialog} from "@angular/material/dialog";
import {GroupPhaseService} from "../group-phase/group-phase.service";
import {Game} from "../game/game.model";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {MatTableDataSource} from "@angular/material/table";
import {getTipFromTeamByGameId, isTipAllowedForGame} from "../tip/tip.util";


export interface DataObjekt {
  dataSource: MatTableDataSource<any>;
  group: string;
}

@Component({
  selector: 'app-game-sort-group',
  templateUrl: './game-sort-group.component.html',
  styleUrls: ['./game-sort-group.component.css']
})
export class GameSortGroupComponent implements OnInit {
  dataObjects: DataObjekt[] = [];
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
      this.updateTip(tip)
    } else {
      this.addTip(tip);
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

  private addTip(tip: Tip){
      this.tipService.addTip(tip).subscribe(tip => {
        location.reload()
      });
  }

  private updateTip(tip: Tip): void {
      this.tipService.updateTip(tip).subscribe(tip => {
      });
  }

  private loadGames(): void {
    this.groupPhaseService.getGroupPhases().subscribe((groupsWithGamesObjects) => {
      for (let groupsGame of groupsWithGamesObjects) {
        let dataSource = new MatTableDataSource();
        let games: Game[] = groupsGame.games;

        dataSource.data = games;
        let dataObject: DataObjekt = {
          dataSource: dataSource,
          group: groupsGame.groupName
        }
        this.dataObjects.push(dataObject);
        this.dataObjects.sort(    (firstObject: DataObjekt , secondObject:DataObjekt ) =>
          (firstObject.group > secondObject.group) ? 1 : -1
        );
      }
      console.log(this.dataObjects)

    });
  }
}
