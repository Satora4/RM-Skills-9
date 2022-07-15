import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Tip} from "./tip.model";
import {TipService} from "./tip.service";
import {MatTableDataSource} from "@angular/material/table";
import {tipToSave} from "./tip.model";
import {UserComponent} from "../user/user.component";
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-tip',
  templateUrl: './tip.component.html',
  styleUrls: ['./tip.component.css']
})
export class TipComponent implements AfterViewInit, OnInit {
  dataSource = new MatTableDataSource();
  columnsToDisplay = ['teamCountry1', 'tipTeam1', 'pointsTeam1', 'pointsTeam2', 'tipTeam2', 'teamCountry2', 'gameTime'];

  @ViewChild(MatSort) sort = new MatSort()

  constructor(private tipService: TipService) {
  }

  ngOnInit(): void {
    this.loadTipByUser(1);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  private loadTipByUser(userId:number): void{
    this.tipService.getTips(userId).subscribe((tips) => {
      this.dataSource.data = tips;
    });
  }

  public saveTip(userId: number, tipTeam1: number, tipTeam2: number, gameId: number) {

    let tip: tipToSave = {
      userId: userId,
      tipTeam1: tipTeam1,
      tipTeam2: tipTeam2,
      gameId: gameId
    }
    this.addTip(tip)
  }

  private addTip(tip: tipToSave): void {
    this.tipService.addTip(tip).subscribe(tip => {
      console.log(tip)
    })
  }


}
