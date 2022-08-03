import { Game } from '../game/game.model';
import { User } from '../user/user.model';

export interface Tip {
  id: number;
  userId: User[];
  tipTeam1: number;
  tipTeam2: number;
  gameId: Game[];
}
