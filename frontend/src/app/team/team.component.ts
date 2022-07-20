import {Component, OnInit} from '@angular/core';
import {Team} from "./team.model";
import {TeamService} from "./team.service"

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {

  private teams: Team[] = [];

  constructor(private teamService: TeamService) {
  }

  ngOnInit(): void {
    this.loadTeams()
  }

  loadTeams(): void {
    this.teamService.getTeams().subscribe(teams => {
      this.teams = teams;
    })
  }

  getTeams(): Team[] {
    return this.teams;
  }

}
