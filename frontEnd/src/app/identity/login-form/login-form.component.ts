import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from "@angular/router";

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

  constructor(private router:Router, private formBuilder: FormBuilder) {
    this.logInForm = this.formBuilder.group({
      'email' : ['', [Validators.required, Validators.email]],
      'password': ['', Validators.required]
    })
  }

  ngOnInit() : void{
  }

  navigate(){
    return this.router.navigate(['/login']);
  }

  login(){
    //TODO: localStorage.setItem.....
    return this.router.navigate(['/home']);
  }

  getErrorMessage() {
    if (this.email.hasError('required') || this.password.hasError('required')) {
      return 'You must enter a value';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

}
