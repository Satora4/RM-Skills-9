import {Component, OnInit} from '@angular/core';
import {GroupService} from "../group/group.service";
import {Group} from "../group/group.model";

@Component({
  selector: 'app-group-phase',
  templateUrl: './group-phase.component.html',
  styleUrls: ['./group-phase.component.css']
})
export class GroupPhaseComponent implements OnInit {
  private groups: Group[] = [];
  groupColumnsToDisplay = ['name', 'groupMembers']
  constructor(private groupService: GroupService) { }

  ngOnInit(): void {
    this.loadGroups();
  }

  private loadGroups(): void {
    this.groupService.getGroups().subscribe((groups) => {
      this.groups = groups;
    });
  }

  getGroups(): Group[] {
    return this.groups;
  }
}
