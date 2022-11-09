import {Game} from "./game.model";

export enum Phase{
  FINAL = 0,
  LITTLE_FINAL = 1,
  SEMI_FINAL = 2,
  QUARTER_FINAL = 3,
  ROUND_OF_16 = 4,
}

export interface KoPhaseModel{
  games: Game[];
  phase: Phase;
}
