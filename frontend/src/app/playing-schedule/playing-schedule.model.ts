export interface PlayingScheduleModel {
  id:number | 1;
  gameTime: Date | null;
  gameLocation: string | null;
  pointsTeam1: number | null;
  pointsTeam2: number | null;
  teamCountry1: string | null;
  teamCountry2: string | null;
}
