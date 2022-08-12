import {Component, OnInit} from '@angular/core';


import {GroupService} from './group.service';

import {Group} from './group.model';
import {MatTableDataSource} from "@angular/material/table";
import {Team} from "../team/team.model";
import {User} from "../user/user.model";


@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css'],
})
export class GroupComponent implements OnInit {
  groupDataSourceGroup1 = new MatTableDataSource<any>([]);
  groupDataSourceGroup2 = new MatTableDataSource<any>([]);
  groupDataSourceGroup3 = new MatTableDataSource<any>([]);
  groupDataSourceGroup4 = new MatTableDataSource<any>([]);
  groupDataSourceGroup5 = new MatTableDataSource<any>([]);
  groupDataSourceGroup6 = new MatTableDataSource<any>([]);
  groupDataSourceGroup7 = new MatTableDataSource<any>([]);
  groupDataSourceGroup8 = new MatTableDataSource<any>([]);

  teamsGroup1: Team[] = [];
  teamsGroup2: Team[] = [];
  teamsGroup3: Team[] = [];
  teamsGroup4: Team[] = [];
  teamsGroup5: Team[] = [];
  teamsGroup6: Team[] = [];
  teamsGroup7: Team[] = [];
  teamsGroup8: Team[] = [];

  nameGroup1: string = '';
  nameGroup2: string = '';
  nameGroup3: string = '';
  nameGroup4: string = '';
  nameGroup5: string = '';
  nameGroup6: string = '';
  nameGroup7: string = '';
  nameGroup8: string = '';


  displayedColumns: string[] = ['Rang', 'Name', 'Punkte'];

  constructor(private groupService: GroupService) {
  }

  ngOnInit(): void {
    this.loadGroups();
  }

  private loadGroups(): void {
    this.groupService.getGroups().subscribe((groups) => {
      console.log(groups)

      for (let j = 0; j < groups[0].groupMembers.length; j++) {
        this.teamsGroup1.push(groups[0].groupMembers[j]);
      }
      for (let j = 0; j < groups[1].groupMembers.length; j++) {
        this.teamsGroup2.push(groups[1].groupMembers[j]);
      }
      for (let j = 0; j < groups[2].groupMembers.length; j++) {
        this.teamsGroup3.push(groups[2].groupMembers[j]);
      }
      for (let j = 0; j < groups[3].groupMembers.length; j++) {
        this.teamsGroup4.push(groups[3].groupMembers[j]);
      }

      for (let j = 0; j < groups[4].groupMembers.length; j++) {
        this.teamsGroup5.push(groups[4].groupMembers[j]);
      }
      for (let j = 0; j < groups[5].groupMembers.length; j++) {
        this.teamsGroup6.push(groups[5].groupMembers[j]);
      }
      for (let j = 0; j < groups[6].groupMembers.length; j++) {
        this.teamsGroup7.push(groups[6].groupMembers[j]);
      }
      for (let j = 0; j < groups[7].groupMembers.length; j++) {
        this.teamsGroup8.push(groups[7].groupMembers[j]);
      }


      this.nameGroup1 = (groups[0].name)
      this.nameGroup2 = (groups[1].name)
      this.nameGroup3 = (groups[2].name)
      this.nameGroup4 = (groups[3].name)
      this.nameGroup5 = (groups[4].name)
      this.nameGroup6 = (groups[5].name)
      this.nameGroup7 = (groups[6].name)
      this.nameGroup8 = (groups[7].name)



      this.groupDataSourceGroup1.data = this.getSortedTeams(this.teamsGroup1);
      this.groupDataSourceGroup2.data = this.getSortedTeams(this.teamsGroup2);
      this.groupDataSourceGroup3.data = this.getSortedTeams(this.teamsGroup3);
      this.groupDataSourceGroup4.data = this.getSortedTeams(this.teamsGroup4);
      this.groupDataSourceGroup5.data = this.getSortedTeams(this.teamsGroup5);
      this.groupDataSourceGroup6.data = this.getSortedTeams(this.teamsGroup6);
      this.groupDataSourceGroup7.data = this.getSortedTeams(this.teamsGroup7);
      this.groupDataSourceGroup8.data = this.getSortedTeams(this.teamsGroup8);

    });
  }

  private getSortedTeams(teams: Team[]) {
    for (let i = 0; i < teams.length; i++) {
      for (let j = 0; j < teams.length - 1 - i; j++) {
        if (teams[j].points < teams[j + 1].points) {
          this.change(teams, j, j + 1);
        }
      }
    }
    return teams;
  }

  private change(users: Team[], a: number, b: number) {
    const tmp = users[a];
    users[a] = users[b];
    users[b] = tmp;
  }
}
