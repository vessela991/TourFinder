import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-tour',
  templateUrl: './add-tour.component.html',
  styleUrls: ['./add-tour.component.scss']
})
export class AddTourComponent implements OnInit {
  addTourForm: FormGroup;
  username = new FormControl('');
  tourName = new FormControl('');
  tourPrice = new FormControl('');
  tourDestination = new FormControl('');
  tourDescription = new FormControl('');
  constructor(private router:Router, private formBuilder: FormBuilder) {
    this.addTourForm = this.formBuilder.group({
      'username' : ['', [Validators.required]],
      'tourName' : ['', [Validators.required]],
      'tourPrice' : ['', [Validators.required]],
      'tourDestination' : ['', [Validators.required]],
      'tourDescription' : ['', [Validators.required]],
    })
    }

  ngOnInit(): void {
  }

  addTour(){

  }

  getErrorMessage() {
    if (this.username.hasError('required')) {
      return 'You must enter a value';
    }
    return this.username.hasError('email') ? 'Not a valid email' : '';
  }

}
