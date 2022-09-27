import { Component, OnInit } from '@angular/core';
import {HeaderService} from "./header.service";
import {User} from "../user/user.model";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  public user: User | any;

  constructor(private headerService: HeaderService) {}

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void {
    this.headerService.getUserData().subscribe( (user) => {
      this.user = user;
      console.log(this.user);
    })
  }
}
