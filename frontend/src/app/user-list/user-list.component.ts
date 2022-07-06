import {Component, OnInit} from '@angular/core';
import {UserListModel} from "./user-list.model";
import {UserListService} from "./user-list.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  private users: UserListModel[] = [];

  constructor(private us: UserListService) {
  }

  ngOnInit(): void {
    this.loadUser();
  }

  private loadUser(): void {
    this.us.getUser().subscribe(users => {
      this.users = users;
    })
  }

  getUsers(): UserListModel[] {
    return this.users;
  }

}
