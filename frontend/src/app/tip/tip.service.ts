import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { handleError } from '../util/http.util';
import { Tip } from "./tip.model";

@Injectable({
  providedIn: 'root'
})
export class TipService {
  private tipUrl = 'api/tip?user=currentUser';

  constructor(private httpClient: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  getTips(): Observable<Tip[]> {
    return this.httpClient.get<Tip[]>(this.tipUrl).pipe(
      tap({complete: () => console.log('fetched Tips')}), catchError(handleError<Tip[]>('getTips', [])));
  }

  addTip(tip: Tip): Observable<Tip> {
    return this.httpClient.post<Tip>(this.tipUrl, tip)
      .pipe(tap((newTip: Tip) => console.log(`added tip w/ tip: ${newTip}`)), catchError(handleError<Tip>('addedTip'))
      );
  }

  updateTip(tip: Tip): Observable<Tip> {
    return this.httpClient.put<Tip>(this.tipUrl, tip, this.httpOptions)
      .pipe(tap((newTip: Tip) => console.log(`updated tip w/ tip: ${newTip}`)), catchError(handleError<Tip>('updatedTip'))
      );
  }
}
