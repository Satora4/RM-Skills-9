import {Game} from "../game/game.model";
import {Tip} from "../tip/tip.model";

export function isTipAlreadySet(game: Game, userId: number, tips: Tip[]): boolean {
  return getTipByGameId(game.id, userId, tips) != null;
}

export function isGameNotPlayedYet(game: Game): boolean {
  return Date.parse(game.gameTime.toString()) > Date.now() &&
  game.goalsTeam1 == null && game.goalsTeam2 == null;
}

export function insertingTipIsAllowed(game: Game, userId: number, tips: Tip[]): boolean {
  return isGameNotPlayedYet(game) && !isTipAlreadySet(game, userId, tips);
}

export function editingTipIsAllowed(game: Game, userId: number, tips: Tip[]): boolean {
  return isGameNotPlayedYet(game) && isTipAlreadySet(game, userId, tips);
}

export function getTipByGameId(gameId: number, userId: number, tips: Tip[]): Tip | null {
  for (let i = 0; i < tips.length; i++) {
    if (tips[i].gameId == gameId && tips[i].userId == userId) {
      return tips[i];
    }
  }
  return null;
}
