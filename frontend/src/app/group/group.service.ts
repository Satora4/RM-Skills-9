import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, tap} from 'rxjs/operators';
import {Observable} from "rxjs";
import {Group} from "./group.model";
import {handleError} from "../util/http.util";

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private groupUrl = 'group'

  constructor(private http: HttpClient) {
  }

  getGroups(): Observable<Group[]> {
    return this.http.get<Group[]>(this.groupUrl)
      .pipe(
        tap({complete: () => console.log('fetched Groups')}),
        catchError(handleError<Group[]>('getGroups', []))
      );
  }
}
