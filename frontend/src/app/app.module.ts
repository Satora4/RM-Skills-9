import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from "./header/header.component";
import {PlayingScheduleComponent} from "./playing-schedule/playing-schedule.component";
import {HttpClientModule} from '@angular/common/http';
import {UserListComponent} from './user-list/user-list.component';
import {GroupComponent} from './group/group.component';
import {TeamComponent} from './team/team.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PlayingScheduleComponent,
    UserListComponent,
    GroupComponent,
    TeamComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
