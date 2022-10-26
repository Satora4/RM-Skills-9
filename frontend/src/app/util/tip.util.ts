import {Game} from "../game/game.model";
import {Tip} from "../tip/tip.model";

export class TipUtil {

  public static isTipAlreadySet(game: Game, userId: number, tips: Tip[]): boolean {
    return TipUtil.getTipByGameId(game.id, userId, tips) != null;
  }

  public static isGameNotPlayedYet(game: Game): boolean {
    return Date.parse(game.gameTime.toString()) > Date.now() &&
      game.goalsTeam1 == null && game.goalsTeam2 == null;
  }

  public static isPositiveNumber(tipTeam1: string, tipTeam2: string): boolean {
    return Number(tipTeam1) >= 0 && Number(tipTeam2) >= 0 || tipTeam1 == null && tipTeam2 == null;
  }

  public static insertingTipIsAllowed(game: Game, userId: number, tips: Tip[]): boolean {
    return TipUtil.isGameNotPlayedYet(game) && !TipUtil.isTipAlreadySet(game, userId, tips);
  }

  public static editingTipIsAllowed(game: Game, userId: number, tips: Tip[]): boolean {
    return TipUtil.isGameNotPlayedYet(game) && TipUtil.isTipAlreadySet(game, userId, tips);
  }

  public static isInsertedTipValid(game: Game, userId: number, tips: Tip[], tipTeam1: string, tipTeam2: string): boolean {
    return TipUtil.insertingTipIsAllowed(game, userId, tips) && TipUtil.isPositiveNumber(tipTeam1, tipTeam2);
  }

  public static isEditedTipValid(game: Game, userId: number, tips: Tip[], tipTeam1: string, tipTeam2: string): boolean {
    return TipUtil.editingTipIsAllowed(game, userId, tips) && TipUtil.isPositiveNumber(tipTeam1, tipTeam2);
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
