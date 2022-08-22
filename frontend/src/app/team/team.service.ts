import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { handleError } from '../util/http.util';
import { Team } from './team.model';

@Injectable({
  providedIn: 'root',
})
export class TeamService {
  private teamUrl = 'api/team';

  constructor(private http: HttpClient) {}

  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(this.teamUrl).pipe(tap({ complete: () => console.log('fetched Teams') }), catchError(handleError<Team[]>('getTeams', [])));
  }
}
