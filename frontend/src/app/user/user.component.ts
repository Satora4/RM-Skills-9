import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { User } from './user.model';
import { UserService } from './user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  private users: User[] = [];
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
      this.setRank(this.users);
      this.userDataSource.data = this.users;
      console.log(users);
    });
  }

  private setRank(users: User[]) {
    if (users.length === 0) {
      return;
    }
    users[0].ranking = 1;
    for (let i = 1; i < users.length; i++) {
        if (users[i].points === users[i - 1].points) {
          users[i].ranking = users[i - 1].ranking;
        } else {
          users[i].ranking = i + 1;
        }
    }
    return users;
  }
}
