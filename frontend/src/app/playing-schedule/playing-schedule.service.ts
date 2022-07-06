import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {PlayingScheduleModel} from "./playing-schedule.model"
import { catchError, map, tap } from 'rxjs/operators';
import {Observable, of} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class PlayingScheduleService {
  private gameUrl = 'http://localhost:8080/game'

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private httpClient: HttpClient
  ) { }

  getGames(): Observable<PlayingScheduleModel[]>{
    return this.httpClient.get<PlayingScheduleModel[]>(this.gameUrl)
      .pipe(
        tap(_ => console.log(('fetched Games')),
        catchError(this.handleError<PlayingScheduleModel[]>('getGames',[])))
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
