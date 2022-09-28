import {Team} from "../team/team.model";


export interface Game {
  id: number;
  gameTime: Date;
  gameLocation: string;
  pointsTeam1: number;
  pointsTeam2: number;
  teamCountry1: string;
  teamCountry2: string;
  tip: string;
}

export interface GameObject {
  id: number;
  gameTime: Date;
  gameLocation: string;
  pointsTeam1: number;
  pointsTeam2: number;
  teamCountry1: Team;
  teamCountry2: Team;
  tip: string;
}
