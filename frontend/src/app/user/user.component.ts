import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';

import {User} from './user.model';
import {UserService} from './user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit, AfterViewInit {
  public user: User | any;
  public users: User[] = [];
  userDataSource = new MatTableDataSource();
  displayedColumns: string[] = ['ranking', 'points', 'firstName', 'lastName'];

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private UserService: UserService) {
  }

  ngAfterViewInit() {
    this.userDataSource.sort = this.sort;
  }

  ngOnInit(): void {
    this.loadUser();
    this.getUser();
  }

  private calculateUserRank(): void {
    for (let user of this.users) {
      if (user.userId == this.user.userId) {
        this.user.ranking = user.ranking;
        break;
      }
    }
  }

  private loadUser(): void {
    this.UserService.getUsers().subscribe((users) => {
      this.users = users;
      this.computeRanks(this.users);
      this.userDataSource.data = this.users;
    });
  }

  private computeRanks(users: User[]) {
    if (users.length === 0) {
      return;
    }
    users[0].ranking = 1;
    users[0].email = "";
    for (let i = 1; i < users.length; i++) {
      if (users[i].points === users[i - 1].points) {
        users[i].ranking = users[i - 1].ranking;
        users[i].email = "";
      } else {
        users[i].ranking = i + 1;
        users[i].email = "";
      }
    }
    return users;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.userDataSource.filter = filterValue.trim().toLowerCase();
  }

  private getUser() {
    this.UserService.getUserData().subscribe((user) => {
      this.user = user;
      this.calculateUserRank();
    })
  }
}
