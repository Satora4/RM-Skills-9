import {Game} from "./game.model";
import {FormControl} from "@angular/forms";

export interface GameTableModel {
  game: Game,
  formControlTip1: FormControl,
  formControlTip2: FormControl
}
