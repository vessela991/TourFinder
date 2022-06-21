import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";

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

  constructor(private router:Router, private formBuilder: FormBuilder, private authService:AuthService) {
    this.registrationForm = this.formBuilder.group({
      'username' : ['', [Validators.required, Validators.pattern('^[A-Za-z][A-Za-z0-9_]{5,29}$')]],
      'email' : ['', [Validators.required, Validators.email, Validators.pattern('^(.+)@(.+)$')]],
      'password': ['', Validators.required, Validators.pattern('^(?=.*[0-9])(?=.*[!@.#$%^&*])[a-zA-Z0-9!.@#$%^&*]{6,16}$')],
      'confirmedPassword' : ['', Validators.required],
      'phoneNumber' : ['', [Validators.required, Validators.pattern('^([0-9]{3}\\)[0-9]{3}-[0-9]{4}$')]]
    })
  }

  ngOnInit() : void{
  }

  register(){
    if(!this.registrationForm.valid){
      return;
    }
    this.authService.register(this.registrationForm.value).subscribe(data => {
      console.log(data);
    });
    return this.router.navigate(['/login']);
  }

  navigate(){
    return this.router.navigate(['/login']);
  }

  getUsernameErrorMessage() {
    if(this.username.hasError('required')){
      return 'You must enter an username!';
    }
    return this.username.hasError('pattern' ?
      'The username must start with capital letter and the length must be between 6 and 30 symbols!' : '');
  }

  getPasswordErrorMessage() {
    if(this.password.hasError('required')){
      return 'You must enter a password!';
    }
    return this.password.hasError('pattern' ?
      'Password length must be between 7 and 17 symbols and password must contain one or more uppercase , lowercase , ' +
      'numeric and special characters!' : '');

  }

  getConfirmPasswordErrorMessage() {
    if(this.confirmedPassword.hasError('required')){
      return 'You must confirm the password!';
    }
    return this.password !== this.confirmedPassword ?
      'The password and the confirmed password are not the same!' : '';
    }

  getEmailErrorMessage() {
    if(this.email.hasError('required')){
      return 'You must enter an email!';
    }else if(this.email.hasError('pattern')){
      return 'You must enter a valid email!';
    }
    return this.email.hasError('email' ? 'Not a valid email!' : '');
  }

  getPhoneNumberErrorMessage() {
    if(this.phoneNumber.hasError('required')){
      return 'You must enter a phone number!';
    }
    return this.phoneNumber.hasError('pattern' ? 'Not a valid phone number!' : '');
  }
}
