import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { GameComponent } from './game/game.component';
import { GroupComponent } from './group/group.component';
import { RulesComponent } from './rules/rules.component';
import { UserComponent } from './user/user.component';
import {GroupPhaseComponent} from "./group-phase/group-phase.component";

const routes: Routes = [
  { path: 'groupPhase', component: GroupPhaseComponent},
  { path: 'ko', component: GameComponent },
  { path: 'users', component: UserComponent },
  { path: 'groups', component: GroupComponent },
  { path: 'rules', component: RulesComponent },
  { path: '', redirectTo: '/groupPhase', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
