import {Game} from "../game/game.model";
import {Tip} from "./tip.model";

export function isTipAllowedForGame(game: Game, tips: Tip[], tipTeam: number): boolean {
  return (Date.parse(game.gameTime.toString()) > Date.now() &&
    game.pointsTeam1 == null && game.pointsTeam2 == null &&
      getTipFromTeamByGameId(game.id, tipTeam, tips) == '—'
  );
}

export function isTTipAlreadySet(game: Game, tips: Tip[], tipTeam: number): boolean {
  return (Date.parse(game.gameTime.toString()) > Date.now() &&
    game.pointsTeam1 == null && game.pointsTeam2 == null &&
    getTipFromTeamByGameId(game.id, tipTeam, tips) != '—'
  );
}

export function getTipFromTeamByGameId(gameId: number, tipTeam: number, tips: Tip[]): string {
  for (let i = 0; i < tips.length; i++) {
    if (tips[i].gameId == gameId) {
      if (tipTeam == 1) {
        return tips[i].tipTeam1.toString();
      } else if (tipTeam == 2) {
        return tips[i].tipTeam2.toString();
      }
    }
  }
  return '—';
}
