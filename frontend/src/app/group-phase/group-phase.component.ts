import {Component, OnInit, ViewChild} from '@angular/core';
import {GroupPhaseService} from "./group-phase.service";
import {MatTableDataSource} from "@angular/material/table";
import {Game} from "../game/game.model";
import {Tip} from "../tip/tip.model";
import {MatSort} from "@angular/material/sort";
import {TipService} from "../tip/tip.service";

@Component({
  selector: 'app-group-phase',
  templateUrl: './group-phase.component.html',
  styleUrls: ['./group-phase.component.css']
})
export class GroupPhaseComponent implements OnInit {
  dataSource = new MatTableDataSource();
  groupColumnsToDisplay = ['gameTime', 'gameLocation', 'teamCountry1', 'pointsTeam1', 'pointsTeam2', 'teamCountry2', 'tipTeam1', 'tipTeam2', 'button'];
  public tipTeam1: any = {};
  public tipTeam2: any = {};

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private tipService: TipService,
              private groupPhaseService: GroupPhaseService) { }

  ngOnInit(): void {
    this.loadGames()
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  public saveTip(userId: number, tipTeam1: number, tipTeam2: number, game: Game) {

    let tip: Tip = {
      userId: userId,
      tipTeam1: tipTeam1,
      tipTeam2: tipTeam2,
      gameId: game.id,
      points: 0,
      teamCountry1: game.teamCountry1,
      teamCountry2: game.teamCountry2,
      pointsTeam1: game.pointsTeam1,
      pointsTeam2: game.pointsTeam2,
      gameTime: game.gameTime
    }
    console.log(tip);
    this.addTip(tip);
  }

  private addTip(tip: Tip): void {
    this.tipService.addTip(tip).subscribe(tip => {
      console.log(tip);
    })
  }

  private loadGames(): void {
    this.groupPhaseService.getGroupPhases().subscribe(groupPhases => {
      this.dataSource.data = groupPhases;
    })
  }
}
