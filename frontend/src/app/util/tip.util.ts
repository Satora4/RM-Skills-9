import {Game} from "../game/game.model";
import {Tip} from "../tip/tip.model";

export function isTipAlreadySet(game: Game, tips: Tip[]): boolean {
  return getTipByGameId(game.id, tips) != null;
}

export function isGameNotPlayedYet(game: Game): boolean {
  return Date.parse(game.gameTime.toString()) > Date.now() &&
  game.pointsTeam1 == null && game.pointsTeam2 == null;
}

export function insertingTipIsAllowed(game: Game, tips: Tip[]): boolean {
  return isGameNotPlayedYet(game) && !isTipAlreadySet(game, tips);
}

export function editingTipIsAllowed(game: Game, tips: Tip[]): boolean {
  return isGameNotPlayedYet(game) && isTipAlreadySet(game, tips);
}

export function getTipByGameId(gameId: number, tips: Tip[]): Tip | null {
  for (let i = 0; i < tips.length; i++) {
    if (tips[i].gameId == gameId) {
      return tips[i];
    }
  }
  return null;
}

