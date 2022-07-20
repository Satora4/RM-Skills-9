import {Team} from "../team/team.model";

export interface Group {
  id: number;
  name: string;
  groupMembers: Team[];
}
