import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {catchError} from "rxjs/operators";
import {handleError} from "../util/http.util";

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  private calculateUrl = 'api/calculate';

  constructor(private httpClient: HttpClient) {}

  calculate(): Observable<null> {
    return this.httpClient
      .post<null>(this.calculateUrl, null)
      .pipe(tap({complete: () => console.log('calculated') }), catchError(handleError<null>('calculate')));
  }
}
