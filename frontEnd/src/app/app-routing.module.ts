import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './identity/login-form/login-form.component';
import {  RegistrationFormComponent } from "./identity/registration-form/registration-form.component";
import {  UserProfileComponent } from "./user/user-profile/user-profile.component";
import {  AllToursPageComponent } from "./all-tours/all-tours-page/all-tours-page.component";
import {  TourPageComponent } from "./tour/tour-page/tour-page.component";
import {  UserProfileEditComponent } from "./user/user-profile-edit/user-profile-edit.component";
import {  AddTourComponent } from "./tour/add-tour/add-tour.component";
import {  EditTourComponent } from "./tour/tour-edit/edit-tour.component";
import {ContactsComponent} from "./contacts/contacts.component";

const routes: Routes = [
  { path: 'login', component: LoginFormComponent },
  { path: 'registration', component: RegistrationFormComponent},
  { path: 'profile', component: UserProfileComponent},
  { path: 'home', component: AllToursPageComponent},
  { path: 'tour/:id', component: TourPageComponent},
  { path: 'profile/edit/:id', component: UserProfileEditComponent},
  { path: 'createTour', component: AddTourComponent},
  { path: 'tour/edit/:id', component: EditTourComponent},
  { path: 'contacts', component: ContactsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
