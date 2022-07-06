import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Game} from "./Game"
import { catchError, map, tap } from 'rxjs/operators';
import {Observable, of} from "rxjs";
import {Games} from "./games";
import {User} from "./User";


@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userUrl = 'http://localhost:8080/user'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient
  ) { }

  getUser(): Observable<User[]>{
    return this.http.get<User[]>(this.userUrl)
      .pipe(
        tap(_ => console.log(('fetched Users')),
          catchError(this.handleError<User[]>('getUser',[])))
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
