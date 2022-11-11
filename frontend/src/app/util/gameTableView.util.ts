import {Game} from "../game/game.model";

export function showZeroPoints(game:Game):string{
  if (game.goalsTeam1 != null && game.goalsTeam2 != null){
    return "0";
  } else {
    return ""
  }
}
