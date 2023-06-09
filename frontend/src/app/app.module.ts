import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatNativeDateModule} from '@angular/material/core';
import {MatDividerModule} from '@angular/material/divider';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {GameComponent} from './game/game.component';
import {GroupComponent} from './group/group.component';
import {HeaderComponent} from './header/header.component';
import {RulesComponent} from './rules/rules.component';
import {TeamComponent} from './team/team.component';
import {TipComponent} from './tip/tip.component';
import {UserComponent} from './user/user.component';
import {MatInputModule} from "@angular/material/input";
import {PopUpComponent} from './pop-up/pop-up.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatGridListModule} from "@angular/material/grid-list";
import {GroupPhaseComponent} from "./group-phase/group-phase.component";
import {MatMenuModule} from "@angular/material/menu";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {GameSortGroupComponent} from './game-sort-group/game-sort-group.component';
import {GameSortDateComponent} from './game-sort-date/game-sort-date.component';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";

@NgModule({
  declarations: [AppComponent, GameComponent, GroupComponent, HeaderComponent, RulesComponent, TeamComponent, TipComponent, UserComponent, PopUpComponent, GroupPhaseComponent, GameSortGroupComponent, GameSortDateComponent],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatCardModule,
    MatDividerModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatNativeDateModule,
    MatSortModule,
    MatTableModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatMenuModule,
    MatButtonToggleModule,
    MatSlideToggleModule,
    MatPaginatorModule,
    MatGridListModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {
}
