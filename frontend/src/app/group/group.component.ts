import {Component, OnInit} from '@angular/core';

import {GroupService} from './group.service';
import {MatTableDataSource} from "@angular/material/table";
import {GroupDataObject} from './group.model';

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
      console.log(groups)

      for (let i = 0; i < groups.length; i++) {
        let dataSource = new MatTableDataSource<any>()
        let name = groups[i].name;
        dataSource.data = this.groupService.getSortedTeams(groups[i].groupMembers);
        let object: GroupDataObject = {
          dataSource: dataSource,
          name: name,
        }
        this.groupDataObjects.push(object);
      }
    });
  }
}
