import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule} from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import { RegistrationService} from './service/registration.service';
import { NgDatepickerModule } from 'ng2-datepicker';
import { ScoringComponent } from './scoring/scoring.component';
import {ScoringService} from './service/scoring.service';
import { TutorialComponent } from './tutorial/tutorial.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    ScoringComponent,
    TutorialComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgDatepickerModule
  ],
  providers: [RegistrationService,ScoringService],
  bootstrap: [AppComponent]
})
export class AppModule { }
