import { Component, OnInit } from '@angular/core';

import { Tip } from "./tip.model";
import { TipService } from "./tip.service";
import {UserService} from "../user/user.service";

@Component({
  selector: 'app-tip',
  templateUrl: './tip.component.html',
  styleUrls: ['./tip.component.css']
})
export class TipComponent implements OnInit {
  private tips: Tip[] = [];

  constructor(private tipService: TipService, private userService:UserService) {}

  ngOnInit(): void {
    this.loadTipsByUser();
  }

  private loadTipsByUser(): void {
    this.tipService.getTips().subscribe((tips) => {
      this.tips = tips;
    });
  }
}

