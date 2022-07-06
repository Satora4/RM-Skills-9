import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Game} from "./Game"
import { catchError, map, tap } from 'rxjs/operators';
import {Observable, of} from "rxjs";
import {Games} from "./games";

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private gameUrl = 'http://localhost:8080/game'
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
  ) { }


  getGames(): Observable<Game[]>{
    return this.http.get<Game[]>(this.gameUrl)
      .pipe(
        tap(_ => console.log(('fetched Games')),
        catchError(this.handleError<Game[]>('getGames',[])))
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
