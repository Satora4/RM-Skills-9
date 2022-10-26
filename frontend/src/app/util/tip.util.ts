import {Game} from "../game/game.model";
import {Tip} from "../tip/tip.model";

export class TipUtil {

  public static isTipAlreadySet(game: Game, userId: number, tips: Tip[]): boolean {
    return TipUtil.getTipByGameId(game.id, userId, tips) != null;
  }

  public static isGameNotPlayedYet(game: Game): boolean {
    return Date.parse(game.gameTime.toString()) > Date.now() &&
      game.pointsTeam1 == null && game.pointsTeam2 == null;
  }

  public static insertingTipIsAllowed(game: Game, userId: number, tips: Tip[]): boolean {
    return TipUtil.isGameNotPlayedYet(game) && !TipUtil.isTipAlreadySet(game, userId, tips);
  }

  public static editingTipIsAllowed(game: Game, userId: number, tips: Tip[]): boolean {
    return TipUtil.isGameNotPlayedYet(game) && TipUtil.isTipAlreadySet(game, userId, tips);
  }

  public static isPositiveNumber(tipTeam1: string, tipTeam2: string): boolean {
    return Number(tipTeam1) >= 0 && Number(tipTeam2) >= 0;
  }

  public static getTipByGameId(gameId: number, userId: number, tips: Tip[]): Tip | null {
    for (let i = 0; i < tips.length; i++) {
      if (tips[i].gameId == gameId && tips[i].userId == userId) {
        return tips[i];
      }
    }
    return null;
  }
}
