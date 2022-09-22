import {Observable} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {handleError} from '../util/http.util';
import {Game} from './game.model';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private gameUrl = 'api/game?phase=ko';

  constructor(private httpClient: HttpClient) {
  }


  getGames(): Observable<Game[]> {
    return this.httpClient
      .get<Game[]>(this.gameUrl)
      .pipe(tap({complete: () => console.log('fetched Games')}), catchError(handleError<Game[]>('getGames', [])));
  }
}
