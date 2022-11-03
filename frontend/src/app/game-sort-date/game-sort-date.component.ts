import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Tip} from "../tip/tip.model";
import {MatSort} from "@angular/material/sort";
import {GameService} from "../game/game.service";
import {TipService} from "../tip/tip.service";
import {Game} from "../game/game.model";
import {TipUtil} from "../util/tip.util";
import {FormControl, FormGroupDirective, NgForm,} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import {GameTableModel} from "../game/game.table.model";
import {formControlForTip} from "../util/initFormControlForTip.util";
import {errorMessage} from '../util/errorMessage.util';
import {GroupPhaseService} from "../group-phase/group-phase.service";
import {MatSlideToggleChange} from "@angular/material/slide-toggle";
import {GroupPhaseModelForDate} from "../group-phase/group-phase.model";
import {TipHelper} from "../tip/tip-helper";
import {UserService} from "../user/user.service";

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
  styleUrls: ['./game-sort-date.component.css'],
})
export class GameSortDateComponent implements OnInit {
  allGames: DataObject[] = [];
  allOpenGamesOnly: DataObject[] = [];
  dataObjects: DataObject[] = [];
  columnsToDisplay = ['gameTime', 'teamCountry1', 'goalsTeam1', 'colon', 'goalsTeam2', 'teamCountry2', 'tipTeam1', 'tipTeam2', 'button', 'points'];
  public tipTeam1: any = {};
  public tipTeam2: any = {};
  public tips: Tip[] = [];
  public userId: number | any;
  public readonly dash = '—';
  public readonly errorMessage = errorMessage;
  matcher = new MyErrorStateMatcher();

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private gameService: GameService,
              private tipService: TipService,
              private userService: UserService,
              private tipHelper: TipHelper,
              private groupPhaseService: GroupPhaseService) {
  }

  ngOnInit(): void {
    this.loadGames();
    this.loadUser();
    this.loadTipsByUser();
  }

  onChange(gameStateToggle: MatSlideToggleChange) {
    if (gameStateToggle.checked) {
      this.dataObjects = this.allGames;
    } else {
      this.dataObjects = this.allOpenGamesOnly;
    }
  }

  public loadTipsByUser() {
    this.tipService.getTips().subscribe((tips) => {
      this.tips = tips;
    });
  }

  public openTipWindow(game: Game, phase: string): void {
    this.tipHelper.openTipWindow(this.userId, game, this.tips, phase);
  }

  public saveTip(tipTeam1: number, tipTeam2: number, game: Game): void {
    this.tipHelper.saveTip(this.userId, tipTeam1, tipTeam2, game, this.tips);
  }

  public getTipByGameId(gameId: number): Tip | null {
    return TipUtil.getTipByGameId(gameId, this.userId, this.tips);
  }

  public insertingTipIsAllowed(game: Game): boolean {
    return TipUtil.insertingTipIsAllowed(game, this.userId, this.tips);
  }

  public editingTipIsAllowed(game: Game): boolean {
    return TipUtil.editingTipIsAllowed(game, this.userId, this.tips);
  }

  public isSavingNewTipAllowed(game: Game, tipTeam1: string, tipTeam2: string): boolean {
    return TipUtil.isSavingNewTipAllowed(game, this.userId, this.tips, tipTeam1, tipTeam2);
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
    return game.goalsTeam1 === null && game.goalsTeam2 === null;
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

  private loadUser(): void {
    this.userService.getUserData().subscribe((user) => {
      this.userId = user.userId;
    })
  }

  public isTipAValidNumber(tipTeam1: string, tipTeam2: string): boolean {
    return TipUtil.isPositiveNumber(tipTeam1, tipTeam2);
  }
}
