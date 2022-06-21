import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.scss']
})
export class RegistrationFormComponent implements OnInit{

  registrationForm : FormGroup;
  username = new FormControl('');
  email = new FormControl('');
  password = new FormControl('');
  confirmedPassword = new FormControl('');
  phoneNumber = new FormControl('');
  hide = true;

  constructor(private router:Router, private formBuilder: FormBuilder) {
    this.registrationForm = this.formBuilder.group({
      'username' : ['', [Validators.required]],
      'email' : ['', [Validators.required, Validators.email]],
      'password': ['', Validators.required],
      'confirmedPassword' : ['', Validators.required],
      'phoneNumber' : ['', [Validators.required]]
    })
  }

  ngOnInit() : void{
  }

  register(){
    return this.router.navigate(['/login']);
  }

  navigate(){
    return this.router.navigate(['/registration']);
  }

  getErrorMessage() {
    if (this.username.hasError('required') || this.email.hasError('required') ||
      this.password.hasError('required') || this.confirmedPassword.hasError('required') ||this.phoneNumber.hasError('required')) {
      return 'You must enter a value';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
}
