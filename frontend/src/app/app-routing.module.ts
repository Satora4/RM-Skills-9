import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PlayingScheduleComponent} from "./playing-schedule/playing-schedule.component";
import {UserListComponent} from "./user-list/user-list.component";
import {GroupComponent} from "./group/group.component";

const routes: Routes = [

  {path: 'playing-schedule', component: PlayingScheduleComponent},
  {path: 'user-list', component: UserListComponent},
  {path: 'groups', component: GroupComponent},
  {path: '', redirectTo: '/playing-schedule', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
