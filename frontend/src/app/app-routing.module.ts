import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GameComponent} from "./game/game.component";
import {UserComponent} from "./user/user.component";
import {GroupComponent} from "./group/group.component";
import {RulesComponent} from "./rules/rules.component";

const routes: Routes = [

  {path: 'game', component: GameComponent},
  {path: 'user-list', component: UserComponent},
  {path: 'groups', component: GroupComponent},
  {path: 'rules', component: RulesComponent},
  {path: '', redirectTo: '/game', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
