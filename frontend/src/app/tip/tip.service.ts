import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { handleError } from '../util/http.util';
import { Tip } from './tip.model';

@Injectable({
  providedIn: 'root',
})
export class TipService {
  private tipUrl = 'tip';

  constructor(private httpClient: HttpClient) {}

  getTips(): Observable<Tip[]> {
    return this.httpClient.get<Tip[]>(this.tipUrl).pipe(tap({ complete: () => console.log('fetched Tips') }), catchError(handleError<Tip[]>('getTips', [])));
  }
}
