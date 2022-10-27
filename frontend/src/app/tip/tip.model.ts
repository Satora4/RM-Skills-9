export interface Tip {
  userId: number;
  gameId: number
  tipTeam1: number;
  tipTeam2: number;
  points: number;
  teamCountry1: string;
  teamCountry2: string;
  goalsTeam1: number;
  goalsTeam2: number;
  gameTime: Date;
}
