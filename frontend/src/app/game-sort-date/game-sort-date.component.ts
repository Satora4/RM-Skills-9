import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Tip} from "../tip/tip.model";
import {MatSort} from "@angular/material/sort";
import {GameService} from "../game/game.service";
import {TipService} from "../tip/tip.service";
import {MatDialog} from "@angular/material/dialog";
import {Game} from "../game/game.model";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {getTipFromTeamByGameId, insertingTipIsAllowed, editingTipIsAllowed} from "../tip/tip.util";
import {FormControl, FormGroupDirective, NgForm,} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import {GameTableModel} from "../game/game.table.model";
import {formControlForTip} from "../util/initFormControlForTip.util";
import {errorMessage} from '../util/errorMessage.util';
import {GroupPhaseService} from "../group-phase/group-phase.service";
import {MatSlideToggleChange} from "@angular/material/slide-toggle";
import {GroupPhaseModelForDate} from "../group-phase/group-phase.model";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
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
  allGames: DataObject[] = [];
  allOpenGamesOnly: DataObject[] = [];
  dataObjects: DataObject[] = [];
  columnsToDisplay = ['gameTime', 'gameLocation', 'teamCountry1', 'flag1', 'pointsTeam1', 'colon', 'pointsTeam2', 'flag2', 'teamCountry2', 'tipTeam1', 'tipTeam2', 'button'];
  public tipTeam1: any = {};
  public tipTeam2: any = {};
  public tips: Tip[] = [];
  public readonly dash = '—';
  public readonly errorMessage = errorMessage;
  public formControlsTip1: FormControl[] = [];
  public formControlsTip2: FormControl[] = [];
  matcher = new MyErrorStateMatcher();

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private gameService: GameService,
              private tipService: TipService,
              public dialog: MatDialog,
              private groupPhaseService: GroupPhaseService) {}

  ngOnInit(): void {
    this.loadGames();
    this.loadTipsByUser();
  }

  onChange(gameStateToggle: MatSlideToggleChange) {
    if (gameStateToggle.checked) {
      this.dataObjects = this.allGames;
    } else {
      this.dataObjects = this.allOpenGamesOnly;
    }
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

  public loadTipsByUser() {
    this.tipService.getTips().subscribe((tips) => {
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

  public getTipFromTeamByGameId(gameId: number, tipTeam: number): string {
    return getTipFromTeamByGameId(gameId, tipTeam, this.tips);
  }

  public insertingTipIsAllowed(game: Game, tipTeam: number): boolean {
    return insertingTipIsAllowed(game, this.tips, tipTeam);
  }

  public editingTipIsAllowed(game: Game, tipTeam: number): boolean {
    return editingTipIsAllowed(game, this.tips, tipTeam);
  }

  private addTip(tip: Tip) {
    this.tipService.addTip(tip).subscribe(tip => {
      location.reload();
    });
  }

  private updateTip(tip: Tip): void {
    this.tipService.updateTip(tip).subscribe(tip => {
    });
  }

  public loadGames(): void {
    this.groupPhaseService.getGamesOrderByDate().subscribe((groupPhaseModelsForDate) => {
      for (let groupPhaseModel of groupPhaseModelsForDate) {
        this.allGames.push(this.getDataObject(groupPhaseModel));
        let openGamesOnly: Game[] = [];
        for (let i = 0; i < groupPhaseModel.games.length; i++) {
          if (this.isOpenGame(groupPhaseModel.games[i])) {
            openGamesOnly.push(groupPhaseModel.games[i])
          }
        }
        if (openGamesOnly.length !== 0) {
          let groupPhaseModelForDateOpenGamesOnly: GroupPhaseModelForDate = {
            games: openGamesOnly,
            groupDate: groupPhaseModel.groupDate
          }
          this.allOpenGamesOnly.push(this.getDataObject(groupPhaseModelForDateOpenGamesOnly));
        }
      }
      this.dataObjects = this.allOpenGamesOnly;
    });
  }

  private isOpenGame(game: Game): boolean {
    return game.pointsTeam1 === null && game.pointsTeam2 === null;
  }

  private getDataObject(groupPhaseModel: GroupPhaseModelForDate): DataObject {
    let dataSource = new MatTableDataSource();
    dataSource.data = this.mapGamesToGameTableModel(groupPhaseModel.games);
    return {
      dataSource: dataSource,
      groupDate: groupPhaseModel.groupDate
    };
  }

  private mapGamesToGameTableModel(games: Game[]): GameTableModel[] {
    const gameTableModel: GameTableModel[] = [];
    games.forEach(game => {
      this.formControlsTip1.push(this.initFormControl());
      this.formControlsTip2.push(this.initFormControl());
      gameTableModel.push({
        game: game,
        formControlTip1: this.initFormControl(),
        formControlTip2: this.initFormControl()
      });
    })
    return gameTableModel;
  }

  private initFormControl(): FormControl {
    return formControlForTip();
  }
}
