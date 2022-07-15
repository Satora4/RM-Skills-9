import {Team} from "../team/team.model";

export interface Group {
  id: number;
  name: string & { length: 1 };
  groupMembers: Team[];
}
