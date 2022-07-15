import {Observable, of} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {handleError} from '../util/http.util';
import {Tip, tipToSave} from "./tip.model";
import {Group} from "../group/group.model";


@Injectable({
  providedIn: 'root'
})
export class TipService {
  private tipUrl = ' http://localhost:8080/tip?userId=';

  constructor(private http: HttpClient) {
  }


  getTips(userId:number): Observable<Tip[]> {
    return this.http.get<Tip[]>(this.tipUrl + userId).pipe(
        tap({complete: () => console.log('fetched Groups')}), catchError(handleError<Tip[]>('getGroups', [])));
  }

  addTip(tip: tipToSave): Observable<tipToSave> {
    return this.http.post<tipToSave>(this.tipUrl, tip).pipe(
      tap((newTip: tipToSave) => console.log(`added tip w/ id=${newTip.gameId}`)),
      catchError(this.handleError<Tip>('addedTip'))
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
