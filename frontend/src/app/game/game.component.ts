import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {GameService} from "./game.service";
import {Game} from "./game.model";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {animate, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class GameComponent implements AfterViewInit {

  dataSource = new MatTableDataSource();
  columnsToDisplay = ['gameTime', 'gameLocation', 'teamCountry1', 'pointsTeam1', 'pointsTeam2', 'teamCountry2'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay, 'expand'];
  expandedElement: Game | null | undefined;

  private games: Game[] = [];

  @ViewChild(MatSort) sort = new MatSort();

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  constructor(private gameService: GameService) {
  }

  ngOnInit(): void {
    this.loadGames()
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
