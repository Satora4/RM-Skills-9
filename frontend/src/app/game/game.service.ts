import {Observable} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {handleError} from '../util/http.util';
import {Game} from './game.model';
import {KoPhaseModel} from "./Ko-Phase.model";

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private koGameUrl = 'api/game?phase=ko';
  private allGamesUrl = 'api/game';


  constructor(private httpClient: HttpClient) {
  }


  getKoGames(): Observable<KoPhaseModel[]> {
    return this.httpClient
      .get<KoPhaseModel[]>(this.koGameUrl)
      .pipe(tap({complete: () => console.log('fetched Games')}), catchError(handleError<KoPhaseModel[]>('getGames', [])));
  }

  getGames(): Observable<Game[]> {
    return this.httpClient
      .get<Game[]>(this.allGamesUrl)
      .pipe(tap({complete: () => console.log('fetched Games')}), catchError(handleError<Game[]>('getGames', [])));
  }
}
