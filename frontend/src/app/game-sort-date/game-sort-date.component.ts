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
import {TipHelper} from "../tip/tip-helper";
import {UserService} from "../user/user.service";


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
  public userId: number | any;
  public readonly dash = '—';

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

  public getTipTeam1ByGameId(gameId: number): string {
    let tip: string = this.dash;
    for (let i = 0; i < this.tips.length; i++) {
      if (this.tips[i].gameId == gameId) {
        tip = this.tips[i].tipTeam1.toString();
      }
    }
    return tip;
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

  public openTipWindow(game: Game): void {
    this.savingTipps.openTipWindow(this.userId, game, this.tips);
  }

  public saveTip(tipTeam1: number, tipTeam2: number, game: Game): void {
    this.savingTipps.saveTip(this.userId, tipTeam1, tipTeam2, game, this.tips);
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

  loadUser(): void {
    this.userService.getUserData().subscribe( (user) => {
      this.userId = user.userId;
      console.log(user);
      console.log('userId: ' + this.userId);
      this.loadTipsByUser(this.userId);
    })
  }
}
