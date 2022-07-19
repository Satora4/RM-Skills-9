import {Component, OnInit} from '@angular/core';
import {User} from "./user-list.model";
import {UserListService} from "./user-list.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  private users: User[] = [];

  constructor(private UserService: UserListService) {
  }

  ngOnInit(): void {
    this.loadUser();
  }

  private loadUser(): void {
    this.UserService.getUsers().subscribe(users => {
      this.users = users;
      UserListComponent.getSortedUsers(this.users)
      console.log(users)
    })
  }

  private static getSortedUsers(users: User[]) {
    for (let i = 0; i < users.length; i++) {
      for (let j = 0; j < (users.length - 1) - i; j++) {
        if (users[j].points < users[j + 1].points) {
          UserListComponent.change(users, j, j + 1);
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

  getUsers(): User[] {

    return this.users;
  }
}
