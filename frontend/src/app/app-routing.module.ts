import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { GameComponent } from './game/game.component';
import { GroupComponent } from './group/group.component';
import { RulesComponent } from './rules/rules.component';
import { TipComponent } from './tip/tip.component';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  { path: 'games', component: GameComponent },
  { path: 'users', component: UserComponent },
  { path: 'groups', component: GroupComponent },
  { path: 'rules', component: RulesComponent },
  { path: 'tips', component: TipComponent },
  { path: '', redirectTo: '/games', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ useHash: true })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
