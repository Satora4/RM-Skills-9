import {Team} from "../team/team.model";
import {Tip} from "../tip/tip.model";




export interface Game {
  id: number;
  gameTime: Date;
  gameLocation: string;
  pointsTeam1: number;
  pointsTeam2: number;
  teamCountry1: string;
  teamCountry2: string;
  tip: string;
  flagTeam1: string;
  flagTeam2: string;
}
