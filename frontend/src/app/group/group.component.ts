import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';

import {GroupService} from './group.service';
import {Team} from "../team/team.model";


interface GroupDataObject {
  dataSource: MatTableDataSource<any>;
  groupName: string;
}

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css'],
})
export class GroupComponent implements OnInit {
  groupDataObjects: GroupDataObject[] = [];

  displayedColumns: string[] = ['Rang', 'Name', 'Punkte'];

  constructor(private groupService: GroupService) {
  }

  ngOnInit(): void {
    this.loadGroups();
  }

  private loadGroups(): void {
    this.groupService.getGroups().subscribe((groups) => {
      console.log(groups);

      for (let i = 0; i < groups.length; i++) {
        let dataSource = new MatTableDataSource<any>();
        let name = groups[i].name;
        dataSource.data = this.sortTeams(groups[i].groupMembers);
        let object: GroupDataObject = {
          dataSource: dataSource,
          groupName: name,
        };
        this.groupDataObjects.push(object);
      }
    });
  }

  public sortTeams(teams: Team[]) {
    for (let i = 0; i < teams.length; i++) {
      for (let j = 0; j < teams.length - 1 - i; j++) {
        if (teams[j].points < teams[j + 1].points) {
          this.change(teams, j, j + 1);
        }
      }
    }
    return teams;
  }

  private change(teams: Team[], a: number, b: number) {
    const tmp = teams[a];
    teams[a] = teams[b];
    teams[b] = tmp;
  }
}
