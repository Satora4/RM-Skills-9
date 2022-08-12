import {Observable} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';

import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

import {handleError} from '../util/http.util';
import {Group} from './group.model';
import {Team} from "../team/team.model";

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  private groupUrl = 'group';

  constructor(private http: HttpClient) {
  }

  getGroups(): Observable<Group[]> {
    return this.http
      .get<Group[]>(this.groupUrl)
      .pipe(tap({complete: () => console.log('fetched Groups')}), catchError(handleError<Group[]>('getGroups', [])));
  }


  public getSortedTeams(teams: Team[]) {
    for (let i = 0; i < teams.length; i++) {
      for (let j = 0; j < teams.length - 1 - i; j++) {
        if (teams[j].points < teams[j + 1].points) {
          this.change(teams, j, j + 1);
        }
      }
    }
    return teams;
  }

  private change(users: Team[], a: number, b: number) {
    const tmp = users[a];
    users[a] = users[b];
    users[b] = tmp;
  }
}
