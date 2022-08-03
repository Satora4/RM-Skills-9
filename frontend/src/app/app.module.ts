import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GameComponent } from './game/game.component';
import { GroupComponent } from './group/group.component';
import { HeaderComponent } from './header/header.component';
import { RulesComponent } from './rules/rules.component';
import { TeamComponent } from './team/team.component';
import { TipComponent } from './tip/tip.component';
import { UserComponent } from './user/user.component';

@NgModule({
  declarations: [AppComponent, HeaderComponent, GameComponent, UserComponent, GroupComponent, TeamComponent, RulesComponent, TipComponent],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, BrowserAnimationsModule, MatButtonModule, MatCardModule, MatDividerModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
