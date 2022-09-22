import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {Game} from "../game/game.model";
import {catchError} from "rxjs/operators";
import {handleError} from "../util/http.util";
import {GroupPhaseModel} from "./group-phase.model";

@Injectable({
  providedIn: 'root',
})
export class GroupPhaseService {
  private groupPhaseUrl = 'api/game?phase=group';

  constructor(private http: HttpClient) {

  }

  getGroupPhases(): Observable<GroupPhaseModel[]> {
    return this.http
      .get<GroupPhaseModel[]>(this.groupPhaseUrl)
      .pipe(tap({complete: () => console.log('fetched GroupPhases')}), catchError(handleError<GroupPhaseModel[]>('getGroupPhases', [])));
  }
}
