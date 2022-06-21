import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit{
  logInForm : FormGroup;
  email = new FormControl('');
  password = new FormControl('');
  hide = true;

  constructor(private router:Router, private formBuilder: FormBuilder, private authService:AuthService) {
    this.logInForm = this.formBuilder.group({
      'email' : ['', [Validators.required, Validators.email, Validators.pattern(
        '^(.+)@(.+)$',
      )]],
      'password': ['', Validators.required, Validators.pattern(
        '^((?=.*[0-9])(?=.*[!@.#$%^&*])[a-zA-Z0-9!.@#$%^&*]{6,16}$'
      )]
    })
  }

  ngOnInit() : void{
  }

  navigate(){
    return this.router.navigate(['/login']);
  }

  login(){
    if(!this.logInForm.valid){
      return;
    }
    this.authService.login(this.logInForm.value).subscribe(data => {
      localStorage.setItem('token', data.jwt);
    })
    return this.router.navigate(['/home']);
  }

  getErrorEmailMessage() {
    if(this.email.hasError('required')){
      return 'You must enter an email!';
    }else if(this.email.hasError('pattern')){
      return 'You must enter a valid email!';
    }
    return this.email.hasError('email' ? 'Not a valid email' : '');
  }

  getErrorPasswordMessage(){
    if(this.password.hasError('required')){
      return 'You must enter a password!';
    }
    return this.password.hasError('pattern' ?
      'Password length must be between 7 and 17 symbols and password must contain one or more uppercase , lowercase , ' +
      'numeric and special characters!' : '');
  }
}
