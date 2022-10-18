import {Game} from "../game/game.model";
import {Tip} from "../tip/tip.model";

export function getTipByGameId(gameId: number, tips: Tip[]): Tip {
  for (let i = 0; i < tips.length; i++) {
    if (tips[i].gameId == gameId) {
      return tips[i];
    }
  }
  throw new Error("tip isn't in database")
}
export function insertingTipIsAllowed(game: Game, tips: Tip[], tipTeam: number): boolean {
  return (Date.parse(game.gameTime.toString()) > Date.now() &&
    game.pointsTeam1 == null && game.pointsTeam2 == null &&
    getTipFromTeamByGameId(game.id, tipTeam, tips) == null
  );
}

export function editingTipIsAllowed(game: Game, tips: Tip[], tipTeam: number): boolean {
  return (Date.parse(game.gameTime.toString()) > Date.now() &&
    game.pointsTeam1 == null && game.pointsTeam2 == null &&
    getTipFromTeamByGameId(game.id, tipTeam, tips) != null
  );
}

export function getTipFromTeamByGameId(gameId: number, tipTeam: number, tips: Tip[]): any {
  for (let i = 0; i < tips.length; i++) {
    if (tips[i].gameId == gameId) {
      if (tipTeam == 1) {
        return tips[i].tipTeam1.toString();
      } else if (tipTeam == 2) {
        return tips[i].tipTeam2.toString();
      }
    }
  }
  return null;
}

