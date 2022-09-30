import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {GameService} from './game.service';

import {Tip} from "../tip/tip.model";
import {TipService} from "../tip/tip.service";
import {Game} from "./game.model";
import {MatDialog} from "@angular/material/dialog";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {UserService} from "../user/user.service";


export interface DialogData {
  tip1: number;
  tip2: number;
  country1: string;
  country2: string;
}

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
})
export class GameComponent implements AfterViewInit, OnInit {
  dataSource = new MatTableDataSource();

  columnsToDisplay = ['gameTime', 'gameLocation', 'teamCountry1', 'pointsTeam1', 'colon', 'pointsTeam2', 'teamCountry2', 'tipTeam1', 'tipTeam2', 'button'];
  public tipTeam1: any = {};
  public tipTeam2: any = {};
  public tips: Tip[] = [];
  public userId: number | any;
  public readonly dash = '—';

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private gameService: GameService,
              private tipService: TipService,
              private userService: UserService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadGames();
    this.loadUser();
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
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
      this.saveTip(result.tip1, result.tip2, game)
      window.location.reload();
    });
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

  public getTipByGameId(gameId: number): Tip {
    for (let i = 0; i < this.tips.length; i++) {
      if (this.tips[i].gameId == gameId) {
        return this.tips[i];
      }
    }
    throw new Error("tip isn't in database")
  }

  public getTipTeam2ByGameId(gameId: number): any {
    let tip:any = null;
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

  public saveTip(tipTeam1: number, tipTeam2: number, game: Game) {

    let tip: Tip = {
      userId: this.userId,
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
    let tipExists: boolean = false;
    for (let i = 0; i < this.tips.length; i++) {
      if (this.tips[i].gameId == tip.gameId) {
        tipExists = true;
        break;
      }
    }

    if (tipExists) {
      this.updateTip(tip)
    } else {
      this.addTip(tip);
    }

  }

  private addTip(tip: Tip){
    this.tipService.addTip(tip).subscribe(tip => {
      location.reload();
      console.log(tip);
    })
  }

  private updateTip(tip: Tip): void {
    this.tipService.updateTip(tip).subscribe(tip => {
    })
  }

  loadGames(): void {
    this.gameService.getGames().subscribe((games) => {
      this.dataSource.data = games;
    });
  }

  loadUser(): void {
    this.userService.getUserData().subscribe( (user) => {
      this.userId = user.userId;
      console.log(user);
      console.log(this.userId);
      this.loadTipsByUser(this.userId);
    })
  }
}


