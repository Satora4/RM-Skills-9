import {Game} from "../game/game.model";
import {GameTableModel} from "../game/game.table.model";

export interface GroupPhaseModel{
  groupName:string;
  games:Game[];
}


export interface GroupPhaseModelForDate{
  groupDate:Date;
  games:Game[];
}
