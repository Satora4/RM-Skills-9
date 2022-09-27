import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { handleError } from '../util/http.util';
import { User } from '../user/user.model';

@Injectable({
  providedIn: 'root',
})
export class HeaderService {
  private userDataUrl = 'api/username';

  constructor(private http: HttpClient) {}

  getUserData(): Observable<User> {
    return this.http.get<User>(this.userDataUrl).pipe(tap({ complete: () => console.log('fetched userdata') }), catchError(handleError<User>('getUserData')));
  }
}
