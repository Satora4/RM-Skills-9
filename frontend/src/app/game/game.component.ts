import {Component, OnInit, ViewChild} from '@angular/core';
import {GameService} from "./game.service";
import {Game} from "./game.model";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";


@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  displayedColumns: string[] = ['gameTime', 'gameLocation', 'teamCountry1', 'pointsTeam1', 'pointsTeam2', 'teamCountry2'];
  dataSource = new MatTableDataSource();

  private games: Game[] = [];

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private gameService: GameService) {
  }

  ngOnInit(): void {
    this.loadGames()
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  private loadGames(): void {
    this.gameService.getGames().subscribe(games => {
      this.games = games;
      this.dataSource.data = games;
    })
  }

  getGames(): Game[] {
    return this.games;
  }
}
