import {Observable, of} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {handleError} from '../util/http.util';
import {Game} from './game.model';
import {Tip, tipToSave} from "../tip/tip.model";

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private gameUrl = 'game';
  private tipUrl = 'tip';

  constructor(private httpClient: HttpClient) {
  }

  addTip(tip: tipToSave): Observable<tipToSave> {
    return this.httpClient.post<tipToSave>(this.tipUrl, tip).pipe(
      tap((newTip: tipToSave) => console.log(`added tip w/ id=${newTip.gameId}`)),
      catchError(this.handleError<Tip>('addedTip'))
    );
  }

  getGames(): Observable<Game[]> {
    return this.httpClient
      .get<Game[]>(this.gameUrl)
      .pipe(tap({complete: () => console.log('fetched Games')}), catchError(handleError<Game[]>('getGames', [])));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
