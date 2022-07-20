import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, tap} from 'rxjs/operators';
import {Observable} from "rxjs";
import {Team} from "./team.model";
import {handleError} from "../util/http.util";

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private teamUrl = 'team'

  constructor(private http: HttpClient) {
  }

  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(this.teamUrl)
      .pipe(
        tap({complete: () => console.log('fetched Teams')}),
        catchError(handleError<Team[]>('getTeams', []))
      );
  }
}
