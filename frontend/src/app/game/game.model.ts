import {Team} from "../team/team.model";
import {Tip} from "../tip/tip.model";

export interface Game {
  id: number;
  gameTime: Date;
  gameLocation: string;
  pointsTeam1: number;
  pointsTeam2: number;
  tip: Tip;
  team1:Team;
  team2:Team;
}
