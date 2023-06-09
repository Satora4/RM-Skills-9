import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {Tip} from "../tip/tip.model";
import {MatSort} from '@angular/material/sort';
import {GameService} from './game.service';
import {TipService} from "../tip/tip.service";
import {MatDialog} from "@angular/material/dialog";
import {Game} from "./game.model";
import {UserService} from "../user/user.service";
import {TipHelper} from "../tip/tip-helper";
import {FormControl, FormGroupDirective, NgForm} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import {GameTableModel} from "./game.table.model";
import {formControlForTip} from "../util/initFormControlForTip.util";
import {errorMessage} from "../util/errorMessage.util";
import {MatSlideToggleChange} from "@angular/material/slide-toggle";
import {KoPhaseModel, Phase} from "./Ko-Phase.model";
import {TipUtil} from "../util/tip.util";
import {showZeroPoints} from "../util/gameTableView.util";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

export interface DataObject {
  dataSource: MatTableDataSource<any>;
  phase: Phase;
}

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
})
export class GameComponent implements OnInit {
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
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadGames();
    this.loadUser();
    this.loadTipsByUser();
  }

  public loadTipsByUser() {
    this.tipService.getTips().subscribe((tips) => {
      this.tips = tips;
    });
  }

  public isDisabled(game: Game): boolean {
    return game.team1.countryFlag == "" && game.team2.countryFlag == "";
  }

  public openTipWindow(game: Game, phase: string): void {
    this.tipHelper.openTipWindow(this.userId, game, this.tips, phase)
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

  public onDisplayGameToggleChange(gameStateToggle: MatSlideToggleChange) {
    if (gameStateToggle.checked) {
      this.dataObjects = this.sortDataObjects(this.allGames);
    } else {
      this.dataObjects = this.sortDataObjects(this.allOpenGamesOnly);
    }
  }

  public isTipAValidNumber(tipTeam1: string, tipTeam2: string): boolean {
    return TipUtil.isValidNumberKoPhase(tipTeam1, tipTeam2);
  }

  public sortDataObjects(dataObjects: DataObject[]): DataObject[] {
    return dataObjects.sort((a, b) => {
      return (a.phase < b.phase) ? 1 : -1;
    });
  }

  public showZeroPoints(game: Game):string{
      return showZeroPoints(game);
  }

  private loadGames(): void {
    this.gameService.getKoGames().subscribe((sortedKoPhaseModels) => {
        for (let sortedKoPhaseModel of sortedKoPhaseModels) {
          this.allGames.push(this.getDataObject(sortedKoPhaseModel));
          let openGamesOnly: Game[] = [];
          this.getOpenGamesOnly(sortedKoPhaseModel, openGamesOnly);
          if (openGamesOnly.length !== 0) {
            let groupPhaseModelForDateOpenGamesOnly = this.getKoPhaseModel(openGamesOnly, sortedKoPhaseModel);
            this.allOpenGamesOnly.push(this.getDataObject(groupPhaseModelForDateOpenGamesOnly));
          }
        }
        this.dataObjects = this.sortDataObjects(this.allOpenGamesOnly);
      }
    );
  }

  private getKoPhaseModel(openGamesOnly: Game[], sortedKoPhaseModel: KoPhaseModel) {
    let groupPhaseModelForDateOpenGamesOnly: KoPhaseModel = {
      games: openGamesOnly,
      phase: sortedKoPhaseModel.phase
    }
    return groupPhaseModelForDateOpenGamesOnly;
  }

  private getOpenGamesOnly(sortedKoPhaseModel: KoPhaseModel, openGamesOnly: Game[]) {
    for (let i = 0; i < sortedKoPhaseModel.games.length; i++) {
      if (GameComponent.isOpenGame(sortedKoPhaseModel.games[i])) {
        openGamesOnly.push(sortedKoPhaseModel.games[i])
      }
    }
  }

  private getDataObject(groupPhaseModel: KoPhaseModel): DataObject {
    let dataSource = new MatTableDataSource();
    dataSource.data = this.mapGamesToGameTableModel(groupPhaseModel.games);
    return {
      dataSource: dataSource,
      phase: Phase[groupPhaseModel.phase] as unknown as Phase
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

  private static isOpenGame(game: Game): boolean {
    return TipUtil.isGameNotPlayedYet(game);
  }

  loadUser(): void {
    this.userService.getUserData().subscribe((user) => {
      this.userId = user.userId;
      console.log('userId: ' + this.userId);
    })
  }

  public getPhase(phase: Phase): string {
    switch (phase) {
      case Phase.FINAL: {
        return "Final";
      }

      case Phase.LITTLE_FINAL: {
        return "Kleines Final";
      }

      case Phase.SEMI_FINAL: {
        return "Halbfinal";
      }

      case Phase.QUARTER_FINAL: {
        return "Viertelfinale";
      }

      case Phase.ROUND_OF_16: {
        return "Achtelfinale";
      }

      default: {
        return "phase"
      }
    }
  }

  private loadGameTableModel(games: Game[]): GameTableModel[] {
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
}
