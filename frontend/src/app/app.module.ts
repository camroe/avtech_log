import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import { AppComponent } from './app.component';
import { LogFormComponent } from './log-form/log-form.component';
import {DataService} from "./data.service";

@NgModule({

  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    LogFormComponent
  ],
  providers: [DataService],
  bootstrap: [AppComponent]
})
export class AppModule { }
