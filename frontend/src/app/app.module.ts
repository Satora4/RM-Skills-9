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
import {MatSortModule} from "@angular/material/sort";
import {MatTableModule} from "@angular/material/table";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatNativeDateModule} from "@angular/material/core";
import {MatIconModule} from "@angular/material/icon";

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
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatNativeDateModule,
    MatSortModule,
    MatTableModule,
    ReactiveFormsModule,
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
