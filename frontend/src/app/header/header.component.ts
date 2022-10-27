import {Component, OnInit} from '@angular/core';
import {User} from "../user/user.model";
import {UserService} from "../user/user.service";
import {AdminService} from "../admin/admin.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  public user: User | any;
  public userIsAdmin: boolean = false;

  constructor(private userService: UserService, private adminService: AdminService) {
  }

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void {
    this.userService.getUserData().subscribe((user) => {
      this.user = user;
      this.userIsAdmin = this.user.administrator;
      console.log(this.user);
    })
  }

  calculateTips(): void {
    this.adminService.calculate().subscribe(() => {
      location.reload();
    });
  }
}
