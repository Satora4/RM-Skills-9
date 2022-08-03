import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { handleError } from '../util/http.util';
import { Group } from './group.model';

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  private groupUrl = 'group';

  constructor(private http: HttpClient) {}

  getGroups(): Observable<Group[]> {
    return this.http
      .get<Group[]>(this.groupUrl)
      .pipe(tap({ complete: () => console.log('fetched Groups') }), catchError(handleError<Group[]>('getGroups', [])));
  }
}
