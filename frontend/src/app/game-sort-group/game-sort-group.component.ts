import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Tip} from "../tip/tip.model";
import {MatSort} from "@angular/material/sort";
import {GameService} from "../game/game.service";
import {TipService} from "../tip/tip.service";
import {Game} from "../game/game.model";
import {getTipFromTeamByGameId, insertingTipIsAllowed, editingTipIsAllowed} from "../util/tip.util";
import {FormControl, FormGroupDirective, NgForm,} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import {GameTableModel} from "../game/game.table.model";
import {formControlForTip} from "../util/initFormControlForTip.util";
import {errorMessage} from '../util/errorMessage.util';
import {GroupPhaseService} from "../group-phase/group-phase.service";
import {MatSlideToggleChange} from "@angular/material/slide-toggle";
import {GroupPhaseModel} from "../group-phase/group-phase.model";
import {TipHelper} from "../tip/tip-helper";
import {UserService} from "../user/user.service";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

export interface DataObjectForGroup {
  dataSource: MatTableDataSource<any>;
  group: string;
}

@Component({
  selector: 'app-game-sort-group',
  templateUrl: './game-sort-group.component.html',
  styleUrls: ['./game-sort-group.component.css']
})
export class GameSortGroupComponent implements OnInit {
  allGames: DataObjectForGroup[] = [];
  allOpenGamesOnly: DataObjectForGroup[] = [];
  dataObjects: DataObjectForGroup[] = [];
  columnsToDisplay = ['gameTime', 'gameLocation', 'teamCountry1', 'flag1', 'pointsTeam1', 'colon', 'pointsTeam2', 'flag2', 'teamCountry2', 'tipTeam1', 'tipTeam2', 'button'];
  public tipTeam1: any = {};
  public tipTeam2: any = {};
  public tips: Tip[] = [];
  public userId: number | any;
  public readonly dash = '—';
  public readonly errorMessage = errorMessage;
  public formControlsTip1: FormControl[] = [];
  public formControlsTip2: FormControl[] = [];
  matcher = new MyErrorStateMatcher();

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private gameService: GameService,
              private tipService: TipService,
              private userService: UserService,
              private savingTipps: TipHelper,
              private groupPhaseService: GroupPhaseService) {
  }

  ngOnInit(): void {
    this.loadGames();
    this.loadUser();
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

  public openTipWindow(game: Game): void {
    this.savingTipps.openTipWindow(this.userId, game, this.tips)
  }

  public saveTip(tipTeam1: number, tipTeam2: number, game: Game): void {
    this.savingTipps.saveTip(this.userId, tipTeam1, tipTeam2, game, this.tips);
  }

  public getTipFromTeamByGameId(gameId: number, tipTeam: number, tips: Tip[]): string {
    return getTipFromTeamByGameId(gameId, tipTeam, tips);
  }

  public insertingTipIsAllowed(game: Game, tipTeam: number): boolean {
    return insertingTipIsAllowed(game, this.tips, tipTeam);
  }

  public editingTipIsAllowed(game: Game, tipTeam: number): boolean {
    return editingTipIsAllowed(game, this.tips, tipTeam);
  }

  public loadGames(): void {
    this.groupPhaseService.getGroupPhases().subscribe((groupsWithGamesObjects) => {
      for (let groupsGame of groupsWithGamesObjects) {
        this.allGames.push(this.getDataObject(groupsGame));
        let openGamesOnly: Game[] = [];
        for (let i = 0; i < groupsGame.games.length; i++) {
          if (this.isOpenGame(groupsGame.games[i])) {
            openGamesOnly.push(groupsGame.games[i])
          }
        }
        if (openGamesOnly.length !== 0) {
          let groupPhaseModelForGroupOpenGamesOnly: GroupPhaseModel = {
            games: openGamesOnly,
            groupName: groupsGame.groupName
          }
          this.allOpenGamesOnly.push(this.getDataObject(groupPhaseModelForGroupOpenGamesOnly));
        }
        this.allGames.sort((firstObject: DataObjectForGroup, secondObject: DataObjectForGroup) =>
          (firstObject.group > secondObject.group) ? 1 : -1
        );
        this.allOpenGamesOnly.sort((firstObject: DataObjectForGroup, secondObject: DataObjectForGroup) =>
          (firstObject.group > secondObject.group) ? 1 : -1
        );
      }
      this.dataObjects = this.allOpenGamesOnly;
    });
  }

  private isOpenGame(game: Game): boolean {
    return game.pointsTeam1 === null && game.pointsTeam2 === null;
  }

  private getDataObject(groupPhaseModel: GroupPhaseModel): DataObjectForGroup {
    let dataSource = new MatTableDataSource();
    dataSource.data = this.mapGamesToGameTableModel(groupPhaseModel.games);
    return {
      dataSource: dataSource,
      group: groupPhaseModel.groupName
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

  loadUser(): void {
    this.userService.getUserData().subscribe( (user) => {
      this.userId = user.userId;
      console.log(user);
      console.log('userId: ' + this.userId);
      this.loadTipsByUser();
    })
  }
}
