import {Tip} from "../tip/tip.model";

export function getTipByGameId(gameId: number, tips: Tip[]): Tip {
  for (let i = 0; i < tips.length; i++) {
    if (tips[i].gameId == gameId) {
      return tips[i];
    }
  }
  throw new Error("tip isn't in database")
}
