import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Game} from "./playing-schedule.model"
import {catchError, tap} from 'rxjs/operators';
import {Observable} from "rxjs";
import {handleError} from "../util/http.util";

@Injectable({
  providedIn: 'root'
})
export class PlayingScheduleService {

  private gameUrl = 'game'

  constructor(private httpClient: HttpClient) {
  }

  getGames(): Observable<Game[]> {
    return this.httpClient.get<Game[]>(this.gameUrl)
      .pipe(
        tap({complete: () => console.log(('fetched Users'))}),
        catchError(handleError<Game[]>('getGames', []))
      );
  }
}
