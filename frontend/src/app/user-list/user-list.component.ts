import { Component, OnInit } from '@angular/core';
import {UserListModel} from "./user-list.model";
import {UserListService} from "./user-list.service";



@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  constructor(private us: UserListService) { }

  private userArray: UserListModel[] = [];

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void{
    this.us.getUser().subscribe(users => {
      this.userArray = users;
    })
  }

  getUsers(): UserListModel[]{
    return this.userArray;
  }

}
