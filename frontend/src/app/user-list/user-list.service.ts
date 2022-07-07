import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map, tap} from 'rxjs/operators';
import {Observable, of} from "rxjs";
import {User} from "./user-list.model";


@Injectable({
  providedIn: 'root'
})
export class UserListService {

  private userUrl = 'http://localhost:8080/user'
  private httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl)
      .pipe(
        tap(_ => console.log(('fetched Users')),
          catchError(this.handleError<User[]>('getUsers', [])))
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
