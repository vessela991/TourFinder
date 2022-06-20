import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { RegistrationFormComponent } from './identity/registration-form/registration-form.component';
import { LoginFormComponent } from './identity/login-form/login-form.component';
import { AllToursPageComponent } from './all-tours/all-tours-page/all-tours-page.component';
import { TourPageComponent } from './tour/tour-page/tour-page.component';
import { AppRoutingModule } from './app-routing.module';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { UserProfileEditComponent } from './user/user-profile-edit/user-profile-edit.component';
import { AddTourComponent } from './tour/add-tour/add-tour.component';
import { EditTourComponent } from './tour/tour-edit/edit-tour.component';
import { NavigationComponent } from './navigation/navigation.component';
import { LogoutButtonComponent } from './identity/logout/logout-button.component';
import { TourCommentsComponent } from './comments/all-comments/tour-comments.component';
import { AddCommentComponent } from './comments/add-comment/add-comment.component';
import { TourShortInfoComponent } from './all-tours/tour-short-info/tour-short-info.component';
import { ContactsComponent } from './contacts/contacts.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationFormComponent,
    LoginFormComponent,
    AllToursPageComponent,
    TourPageComponent,
    UserProfileComponent,
    UserProfileEditComponent,
    AddTourComponent,
    EditTourComponent,
    NavigationComponent,
    LogoutButtonComponent,
    TourCommentsComponent,
    AddCommentComponent,
    TourShortInfoComponent,
    ContactsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    BrowserAnimationsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
