import {Game} from "../game/game.model";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {Tip} from "./tip.model";
import {MatDialog} from "@angular/material/dialog";
import {TipService} from "./tip.service";
import {Injectable} from "@angular/core";
import {TipUtil} from "../util/tip.util";

@Injectable({
  providedIn: 'root'
})
export class TipHelper {

  constructor(public dialog: MatDialog, public tipService: TipService) {
  }

  public openTipWindow(userId: number, game: Game, tips: Tip[], phase: string): void {
    const dialogRef = this.dialog.open(PopUpComponent, {
      width: 'auto',
      data: {
        tip1: TipUtil.getTipByGameId(game.id, userId, tips)?.tipTeam1,
        tip2: TipUtil.getTipByGameId(game.id, userId, tips)?.tipTeam2,
        country1: game.team1.country,
        country2: game.team2.country,
        flag1: game.team1.countryFlag,
        flag2: game.team2.countryFlag,
        phase: phase
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
      goalsTeam1: game.team1.points,
      goalsTeam2: game.team2.points,
      gameTime: game.gameTime
    }
    if (TipUtil.getTipByGameId(game.id, userId, tips)) {
      this.updateTip(tip, tips)
    } else {
      this.addTip(tip, tips);
    }
  }

  public addTip(tip: Tip, tips: Tip[]) {
    this.tipService.addTip(tip).subscribe(tip => {
    })
    tips.push(tip);
  }

  public updateTip(tip: Tip, tips: Tip[]): void {
    this.tipService.updateTip(tip).subscribe(tip => {
    })
    let indexOfOldTip = tips.findIndex((element) =>
      element.userId == tip.userId && element.gameId == tip.gameId);
    tips.splice(indexOfOldTip, 1, tip);
  }
}
