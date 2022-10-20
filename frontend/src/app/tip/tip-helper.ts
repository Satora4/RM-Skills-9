import {Game} from "../game/game.model";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {Tip} from "./tip.model";
import {MatDialog} from "@angular/material/dialog";
import {TipService} from "./tip.service";
import {Injectable} from "@angular/core";
import {getTipByGameId} from "../util/tip.util";

@Injectable({
  providedIn: 'root'
})
export class TipHelper {

  constructor(public dialog: MatDialog, public tipService: TipService) {}

  public openTipWindow(userId: number, game: Game, tips: Tip[]): void {
    const dialogRef = this.dialog.open(PopUpComponent, {
      width: '250px',
      data: {
        tip1: getTipByGameId(game.id, tips)?.tipTeam1,
        tip2: getTipByGameId(game.id, tips)?.tipTeam2,
        country1: game.team1.country,
        country2: game.team2.country
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      this.saveTip(userId, result.tip1, result.tip2, game, tips)
    });
  }

  public saveTip(userId: number, tipTeam1: number, tipTeam2: number, game: Game, tips: Tip[]) {
    let tip: Tip = {
      userId: userId,
      tipTeam1: tipTeam1,
      tipTeam2: tipTeam2,
      points: 0,
      gameId: game.id,
      teamCountry1: game.team1.country,
      teamCountry2: game.team2.country,
      pointsTeam1: game.team1.points,
      pointsTeam2: game.team2.points,
      gameTime: game.gameTime
    }
    if (tips.findIndex((element) => element.userId == tip.userId && element.gameId == tip.gameId) != -1) {
      this.updateTip(tip)
    } else {
      this.addTip(tip);
    }
  }

  public addTip(tip: Tip) {
    this.tipService.addTip(tip).subscribe(tip => {
      location.reload()
    })
  }

  public updateTip(tip: Tip): void {
    this.tipService.updateTip(tip).subscribe(tip => {
      location.reload();
    })
  }
}
