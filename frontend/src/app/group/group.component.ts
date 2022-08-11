import { Component, OnInit } from '@angular/core';

import { Group } from './group.model';
import { GroupService } from './group.service';
import {FlatTreeControl} from "@angular/cdk/tree";
import {MatTreeFlatDataSource, MatTreeFlattener} from "@angular/material/tree";


interface FoodNode {
  name: string;
  children?: FoodNode[];
}




/** Flat node with expandable and level information */
interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}
@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css'],
})
export class GroupComponent implements OnInit {
  private groups: Group[] = [];
  private group0:Group = this.groups[0];
  private group1:Group = this.groups[1];
  private group2:Group = this.groups[2];
  private group3:Group = this.groups[3];
  private group4:Group = this.groups[4];
  private group5:Group = this.groups[5];
  private group6:Group = this.groups[6];
  private group7:Group = this.groups[7];




  TREE_DATA: FoodNode[] = [
    {
      name: this.group0.name,
      children: [{name: 'Apple'}, {name: 'Banana'}, {name: 'Fruit loops'}],
    },
    {
      name: this.group1.name,
      children: [{name: 'Apple'}, {name: 'Banana'}, {name: 'Fruit loops'}],
    },
    {
      name: this.group2.name,
      children: [{name: 'Apple'}, {name: 'Banana'}, {name: 'Fruit loops'}],
    },
    {
      name: this.group3.name,
      children: [{name: 'Apple'}, {name: 'Banana'}, {name: 'Fruit loops'}],
    },
    {
      name: this.group4.name,
      children: [{name: 'Apple'}, {name: 'Banana'}, {name: 'Fruit loops'}],
    },
    {
      name: this.group5.name,
      children: [{name: 'Apple'}, {name: 'Banana'}, {name: 'Fruit loops'}],
    },
    {
      name: this.group6.name,
      children: [{name: 'Apple'}, {name: 'Banana'}, {name: 'Fruit loops'}],
    },
    {
      name: this.group7.name,
      children: [{name: 'Apple'}, {name: 'Banana'}, {name: 'Fruit loops'}],
    },

  ];

  constructor(private groupService: GroupService) { this.dataSource.data = this.TREE_DATA;}


  private _transformer = (node: FoodNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
    };
  };

  treeControl = new FlatTreeControl<ExampleFlatNode>(
    node => node.level,
    node => node.expandable,
  );

  treeFlattener = new MatTreeFlattener(
    this._transformer,
    node => node.level,
    node => node.expandable,
    node => node.children,
  );

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;














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
