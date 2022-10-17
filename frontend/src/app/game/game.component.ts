import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {Tip} from "../tip/tip.model";
import {MatSort} from '@angular/material/sort';
import {GameService} from './game.service';
import {TipService} from "../tip/tip.service";
import {MatDialog} from "@angular/material/dialog";
import {Game} from "./game.model";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {UserService} from "../user/user.service";
import {TipHelper} from "../tip/tip-helper";


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
  public userId: number | any;
  public readonly dash = '—';

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
  }

  public getTipTeam1ByGameId(gameId: number): any {
    let tip: any = null;
    for (let i = 0; i < this.tips.length; i++) {
      if (this.tips[i].gameId == gameId) {
        tip = this.tips[i].tipTeam1;
      }
    }
    return tip;
  }

  public getTipTeam2ByGameId(gameId: number): any {
    let tip: any = null;
    for (let i = 0; i < this.tips.length; i++) {
      if (this.tips[i].gameId == gameId) {
        tip = this.tips[i].tipTeam2;
      }
    }
    return tip;
  }

  public loadTipsByUser(userId: number) {

    this.tipService.getTips(userId).subscribe((tips) => {
      this.tips = tips;
    });

  }

  public openTipWindow(game: Game): void {
    this.tipHelper.openTipWindow(this.userId, game, this.tips)
  }

  public saveTip(tipTeam1: number, tipTeam2: number, game: Game): void {
    this.tipHelper.saveTip(this.userId, tipTeam1, tipTeam2, game, this.tips);
  }

  loadGames(): void {
    this.gameService.getKoGames().subscribe((koPhaseModels) => {
      let sortedKoPhaseModels = koPhaseModels.sort((a, b) => b.games.length - a.games.length);
      for (let sortedKoPhaseModel of sortedKoPhaseModels) {
        let dataSource = new MatTableDataSource();
        dataSource.data = sortedKoPhaseModel.games;
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
      console.log(user);
      console.log('userId: ' + this.userId);
      this.loadTipsByUser(this.userId);
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


