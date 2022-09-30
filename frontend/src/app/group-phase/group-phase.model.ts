import {Game} from "../game/game.model";

export interface GroupPhaseModel{
  groupName:string;
  games:Game[];
}


export interface GroupPhaseModelForDate{
  groupDate:Date;
  games:Game[];
}
