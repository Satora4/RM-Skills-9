import {Team} from "../team/team.model";

export interface Game {
  id: number;
  gameTime: Date;
  gameLocation: string;
  pointsTeam1: number;
  pointsTeam2: number;
  tip: string;
  team1:Team;
  team2:Team;
}
