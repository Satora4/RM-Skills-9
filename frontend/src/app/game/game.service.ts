import {Observable} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {handleError} from '../util/http.util';
import {Game} from './game.model';

import {Tip} from "../tip/tip.model";

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private gameUrl = 'game';
  private tipUrl = 'tip';

  constructor(private httpClient: HttpClient) {
  }

  addTip(tip: Tip): Observable<Tip> {
    return this.httpClient.post<Tip>(this.tipUrl, tip)
      .pipe(tap((newTip: Tip) => console.log(`added tip w/ id=${newTip.gameId}`)), catchError(handleError<Tip>('addedTip'))
      );
  }

  getGames(): Observable<Game[]> {
    return this.httpClient
      .get<Game[]>(this.gameUrl)
      .pipe(tap({complete: () => console.log('fetched Games')}), catchError(handleError<Game[]>('getGames', [])));
  }
}
