export interface Tip{
  userId: number;
  gameId: number
  tipTeam1: number;
  tipTeam2: number;
  teamCountry1: string;
  teamCountry2: string;
  pointsTeam1: number;
  pointsTeam2: number;
  gameTime: Date;
}


export interface tipToSave{
  userId: number;
  gameId: number;
  tipTeam1: number;
  tipTeam2: number;
}

