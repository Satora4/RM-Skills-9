import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { User } from './user.model';
import { UserService } from './user.service';
import {Tip} from "../tip/tip.model";

@Component({
  selector: 'app-user-list',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  private users: User[] = [];
  private tips: Tip[] = [];
  userDataSource = new MatTableDataSource();
  displayedColumns: string[] = ['ranking', 'firstName', 'lastName', 'points'];
  @ViewChild(MatSort) sort = new MatSort();

  constructor(private UserService: UserService) {
  }

  ngAfterViewInit() {
    this.userDataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.loadUser();
  }

  private loadUser(): void {
    this.UserService.getUsers().subscribe((users) => {
      this.users = users;
      this.userDataSource.data = UserComponent.sortUsers(this.users);
      console.log(users);
    });
  }

  private static sortUsers(users: User[]) {
    for (let i = 0; i < users.length; i++) {
      for (let j = 0; j < users.length - 1 - i; j++) {
        if (users[j].points < users[j + 1].points) {
          UserComponent.change(users, j, j + 1);
        }
      }
    }
    return users;
  }

  private static change(users: User[], a: number, b: number) {
    const tmp = users[a];
    users[a] = users[b];
    users[b] = tmp;
  }
}
