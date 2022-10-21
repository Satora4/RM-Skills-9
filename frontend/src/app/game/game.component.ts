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
import {getTipByGameId, insertingTipIsAllowed, editingTipIsAllowed, isTipAPositivNumber} from "../util/tip.util";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
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
  columnsToDisplay = ['gameTime', 'teamCountry1', 'flag1', 'pointsTeam1', 'colon', 'pointsTeam2', 'flag2', 'teamCountry2', 'tipTeam1', 'tipTeam2', 'button'];
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

  public openTipWindow(game: Game): void {
    this.tipHelper.openTipWindow(this.userId, game, this.tips)
  }

  public saveTip(tipTeam1: number, tipTeam2: number, game: Game): void {
    this.tipHelper.saveTip(this.userId, tipTeam1, tipTeam2, game, this.tips);
  }

  public getTipByGameId(gameId: number): Tip | null {
    return getTipByGameId(gameId, this.userId, this.tips);
  }

  public insertingTipIsAllowed(game: Game): boolean {
    return insertingTipIsAllowed(game, this.userId, this.tips);
  }

  public editingTipIsAllowed(game: Game): boolean {
    return editingTipIsAllowed(game, this.userId, this.tips);
  }

  public isTipAPositivNumber(tipTeam1: any, tipTeam2: any): boolean {
    return isTipAPositivNumber(tipTeam1, tipTeam2);
  }

  loadGames(): void {
    this.gameService.getKoGames().subscribe((koPhaseModels) => {
      let sortedKoPhaseModels = koPhaseModels.sort((a, b) => b.games.length - a.games.length);
      for (let sortedKoPhaseModel of sortedKoPhaseModels) {
        let dataSource = new MatTableDataSource();
        dataSource.data = this.loadGameTableModel(sortedKoPhaseModel.games);
        let dataObject: DataObject = {
          dataSource: dataSource,
          phase: this.getPhase(sortedKoPhaseModel.phase.toString())
        }
        this.dataObjects.push(dataObject);
      }
    });
  }

  loadUser(): void {
    this.userService.getUserData().subscribe( (user) => {
      this.userId = user.userId;
      console.log('userId: ' + this.userId);
    })
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
        return "Viertelfinale";
      }

      case "ROUND_OF_16": {
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


