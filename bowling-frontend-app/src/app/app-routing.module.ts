import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import { RegistrationComponent } from './registration/registration.component';
import {ScoringComponent} from "./scoring/scoring.component";

const appRoutes: Routes = [
  { path : 'register', component : RegistrationComponent },
  { path : 'score', component : ScoringComponent }
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
