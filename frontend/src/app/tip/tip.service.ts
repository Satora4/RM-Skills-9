import {Observable} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {handleError} from '../util/http.util';
import {Tip} from "./tip.model";

@Injectable({
  providedIn: 'root'
})
export class TipService {
  private tipUrl = 'api/tip';

  constructor(private httpClient: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  getTips(userId: number): Observable<Tip[]> {
    return this.httpClient.get<Tip[]>(this.tipUrl + "?userId=" + userId).pipe(
      tap({complete: () => console.log('fetched Tips')}), catchError(handleError<Tip[]>('getTips', [])));
  }

  addTip(tip: Tip): Observable<Tip> {
    return this.httpClient.post<Tip>(this.tipUrl, tip)
      .pipe(tap((newTip: Tip) => console.log(`added tip w/ id=${newTip.gameId}`)), catchError(handleError<Tip>('addedTip'))
      );
  }

  updateTip(tip: Tip): Observable<Tip> {
    return this.httpClient.put<Tip>(this.tipUrl, tip, this.httpOptions)
      .pipe(tap((newTip: Tip) => console.log(`updated tip w/ id=${newTip.gameId}`)), catchError(handleError<Tip>('updatedTip'))
      );
  }
}
