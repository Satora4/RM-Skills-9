import { Component, OnInit } from '@angular/core';
import {User} from "../user/user.model";
import {UserService} from "../user/user.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  public user: User | any;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void {
    this.userService.getUserData().subscribe( (user) => {
      this.user = user;
      console.log(this.user);
    })
  }
}
