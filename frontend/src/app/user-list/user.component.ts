import {Component, OnInit} from '@angular/core';
import {User} from "./user.model";
import {UserService} from "./user.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  private users: User[] = [];

  constructor(private UserService: UserService) {
  }

  ngOnInit(): void {
    this.loadUser();
  }

  private loadUser(): void {
    this.UserService.getUsers().subscribe(users => {
      this.users = users;
      UserComponent.getSortedUsers(this.users)
      console.log(users)
    })
  }

  private static getSortedUsers(users: User[]) {
    for (let i = 0; i < users.length; i++) {
      for (let j = 0; j < (users.length - 1) - i; j++) {
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

  getUsers(): User[] {

    return this.users;
  }
}
