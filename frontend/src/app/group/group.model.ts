import {Team} from '../team/team.model';
import {MatTableDataSource} from "@angular/material/table";

export interface Group {
  id: number;
  name: string;
  groupMembers: Team[];
}

export interface MyObject {
  dataSource: MatTableDataSource<any>;
  name: string;
}

