import {Observable} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {handleError} from '../util/http.util';
import {Tip} from "./tip.model";

@Injectable({
  providedIn: 'root'
})
export class TipService {
  private tipUrl = 'tip';

  constructor(private http: HttpClient) {
  }

  getTips(userId: number): Observable<Tip[]> {
    return this.http.get<Tip[]>(this.tipUrl + "?userId=" + userId).pipe(
      tap({complete: () => console.log('fetched Groups')}), catchError(handleError<Tip[]>('getGroups', [])));
  }
}
