import { Component, OnInit } from '@angular/core';
import {User} from "../User";
import {UserService} from "../user.service";
import {users} from "../users";


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  constructor(private us: UserService) { }

  private userArray: User[] = [];

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void{
    this.us.getUser().subscribe(users => {
      this.userArray = users;
    })
  }

  getUsers(): User[]{
    return this.userArray;
  }

}
