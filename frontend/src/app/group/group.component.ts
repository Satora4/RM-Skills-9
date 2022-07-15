import { Component, OnInit } from '@angular/core';
import {Group} from "./group.model";
import {GroupService} from "./group.service";

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {

  private groups: Group[] = [];

  constructor(private groupService: GroupService) { }

  ngOnInit(): void {
    this.loadGroups();
  }

  private loadGroups(): void{
    this.groupService.getGroups().subscribe(groupsFromService => {
      this.groups = groupsFromService;
    })
  }

  getGroups(): Group[] {
    return this.groups;
  }

}
