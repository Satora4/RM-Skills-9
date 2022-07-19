import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, tap} from 'rxjs/operators';
import {Observable} from "rxjs";
import {User} from "./user.model";
import {handleError} from "../util/http.util";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'user'

  constructor(private http: HttpClient) {
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl)
      .pipe(
        tap({complete: () => console.log(('fetched Users'))}),
        catchError(handleError<User[]>('getUsers', []))
      );
  }
}
