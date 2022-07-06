import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, map, tap} from 'rxjs/operators';
import {Observable, of} from "rxjs";
import {UserListModel} from "./user-list.model";


@Injectable({
  providedIn: 'root'
})
export class UserListService {
  private userUrl = 'http://localhost:8080/user'

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(
    private http: HttpClient
  ) {
  }

  getUser(): Observable<UserListModel[]> {
    return this.http.get<UserListModel[]>(this.userUrl)
      .pipe(
        tap(_ => console.log(('fetched Users')),
          catchError(this.handleError<UserListModel[]>('getUser', [])))
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
