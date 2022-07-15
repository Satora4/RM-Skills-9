import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map, tap} from 'rxjs/operators';
import {Observable, of} from "rxjs";
import {Team} from "./team.model";


@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private userUrl = 'http://localhost:8080/team'
  private httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(this.userUrl)
      .pipe(
        tap(_ => console.log(('fetched Teams')),
          catchError(this.handleError<Team[]>('getTeams', [])))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

}
