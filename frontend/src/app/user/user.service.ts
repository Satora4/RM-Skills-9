import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { handleError } from '../util/http.util';
import { User } from './user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userUrl = 'api/users';
  private userDataUrl = 'api/user';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl).pipe(tap({ complete: () => console.log('fetched Users') }), catchError(handleError<User[]>('getUsers', [])));
  }

  getUserData(): Observable<User> {
    return this.http.get<User>(this.userDataUrl).pipe(tap({ complete: () => console.log('fetched userdata') }), catchError(handleError<User>('getUserData')));
  }
}
