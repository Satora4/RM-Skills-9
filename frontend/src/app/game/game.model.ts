import {Team} from "../team/team.model";
import {Tip} from "../tip/tip.model";

export interface Game {
  id: number;
  gameTime: Date;
  gameLocation: string;
  goalsTeam1: number;
  goalsTeam2: number;
  tip: Tip;
  team1:Team;
  team2:Team;
}
