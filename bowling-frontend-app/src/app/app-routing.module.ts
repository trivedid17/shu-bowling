import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import { RegistrationComponent } from './registration/registration.component';
import {ScoringComponent} from './scoring/scoring.component';
import {TutorialComponent} from './tutorial/tutorial.component';

const appRoutes: Routes = [
  { path : 'register', component : RegistrationComponent },
  { path : 'score', component : ScoringComponent },
  { path : 'tutorial', component : TutorialComponent }
];
@NgModule({


  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    RouterModule
  ]

})
export class AppRoutingModule {}
