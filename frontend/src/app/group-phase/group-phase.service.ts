import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {Game} from "../game/game.model";
import {catchError} from "rxjs/operators";
import {handleError} from "../util/http.util";
import {GroupPhaseModel, GroupPhaseModelForDate} from "./group-phase.model";
import {Group} from "../group/group.model";

@Injectable({
  providedIn: 'root',
})
export class GroupPhaseService {
  private groupPhaseUrl = 'api/game?phase=group';
  private dateUrl = 'api/game?phase=group_order_date';


  constructor(private http: HttpClient) {

  }

  getGroupPhases(): Observable<GroupPhaseModel[]> {
    return this.http
      .get<GroupPhaseModel[]>(this.groupPhaseUrl)
      .pipe(tap({complete: () => console.log('fetched GroupPhases')}), catchError(handleError<GroupPhaseModel[]>('getGroupPhases', [])));
  }

  getGamesOrderByDate(): Observable<GroupPhaseModelForDate[]> {
    return this.http
      .get<GroupPhaseModelForDate[]>(this.dateUrl)
      .pipe(tap({ complete: () => console.log('fetched Groups') }), catchError(handleError<GroupPhaseModelForDate[]>('getGroups', [])));
  }
}
