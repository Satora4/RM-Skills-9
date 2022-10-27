import {Component, OnInit} from '@angular/core';
import {User} from "../user/user.model";
import {UserService} from "../user/user.service";
import {HeaderService} from "./header.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  public user: User | any;
  public userIsAdmin?: boolean;

  constructor(private userService: UserService, private headerService: HeaderService) {
  }

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void {
    this.userService.getUserData().subscribe((user) => {
      this.user = user;
      console.log(this.user);
      this.isUserAnAdmin();
    })
  }

  calculateTips(): void {
    this.headerService.calculate().subscribe();
  }

  isUserAnAdmin(): void {
    this.userIsAdmin = this.user.administrator;
  }
}
