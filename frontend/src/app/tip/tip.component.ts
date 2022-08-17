import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {TipService} from "./tip.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-tip',
  templateUrl: './tip.component.html',
  styleUrls: ['./tip.component.css']
})
export class TipComponent implements AfterViewInit, OnInit {
  dataSource = new MatTableDataSource();
  columnsToDisplay = ['gameTime', 'teamCountry1', 'tipTeam1', 'pointsTeam1', 'pointsTeam2', 'tipTeam2', 'teamCountry2'];

  @ViewChild(MatSort) sort = new MatSort();

  constructor(private tipService: TipService) {
  }

  ngOnInit(): void {
    this.loadTipsByUser(1);
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  private loadTipsByUser(userId: number): void {
    this.tipService.getTips(userId).subscribe((tips) => {
      this.dataSource.data = tips;
    });
  }
}

