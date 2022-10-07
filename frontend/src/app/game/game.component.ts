import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {GameService} from './game.service';

import {Tip} from "../tip/tip.model";
import {TipService} from "../tip/tip.service";
import {Game} from "./game.model";
import {MatDialog} from "@angular/material/dialog";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {
  FormControl,
  FormGroupDirective,
  NgForm
} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import {GameTableModel} from "./game.table.model";
import {formControlForTip} from "../util/initFormControlForTip.util";
import {errorMessage} from "../util/errorMessage.util";


export interface DialogData {
  tip1: number;
  tip2: number;
  country1: string;
  country2: string;
}

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
      this.updateTip(tip)
    } else {
      this.addTip(tip);
    }

  }

  private addTip(tip: Tip) {
    this.tipService.addTip(tip).subscribe(tip => {
      location.reload()
    })
  }

  private updateTip(tip: Tip): void {
    this.tipService.updateTip(tip).subscribe(tip => {
    })
  }


  loadGames(): void {
    this.gameService.getKoGames().subscribe((koPhaseModels) => {
      let sortedKoPhaseModels = koPhaseModels.sort((a, b) => b.games.length - a.games.length);
      for (let sortedKoPhaseModel of sortedKoPhaseModels) {
        let dataSource = new MatTableDataSource();
        dataSource.data = this.loadGameTableModel(sortedKoPhaseModel.games);
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


