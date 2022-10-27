import {Team} from "../team/team.model";

export interface Game {
  id: number;
  gameTime: Date;
  gameLocation: string;
  goalsTeam1: number;
  goalsTeam2: number;
  tip: string;
  team1: Team;
  team2: Team;
}
