import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from "./header/header.component";
import {GameComponent} from "./game/game.component";
import {HttpClientModule} from '@angular/common/http';
import {UserComponent} from './user/user.component';
import {GroupComponent} from './group/group.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {TeamComponent} from './team/team.component';
import {RulesComponent} from './rules/rules.component';
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from "@angular/material/divider";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    GameComponent,
    UserComponent,
    GroupComponent,
    TeamComponent,
    RulesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatDividerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
