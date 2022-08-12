import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';

import {GameService} from '../game/game.service';
import {TipService} from './tip.service';

@Component({
  selector: 'app-tip',
  templateUrl: './tip.component.html',
  styleUrls: ['./tip.component.css'],
})
export class TipComponent implements OnInit {
  tipDataSource = new MatTableDataSource();
  gameDataSource = new MatTableDataSource();
  displayedColumns: string[] = ['gameTime', 'gameLocation', 'teamCountry1', 'tipTeam1', 'tipTeam2', 'teamCountry2'];

  constructor(private tipService: TipService, private gameService: GameService) {
  }

  ngOnInit(): void {
    this.loadTips();
    this.loadGames();
  }

  private loadTips(): void {
    this.tipService.getTips().subscribe((tips) => {
      this.tipDataSource.data = tips;
    });
  }

  private loadGames(): void {
    this.gameService.getGames().subscribe((games) => {
      this.gameDataSource.data = games;
    });
  }
}
