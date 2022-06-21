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
  tourName = new FormControl('');
  tourPrice = new FormControl('');
  tourStartDate = new FormControl(new Date());
  tourEndDate = new FormControl(new Date());
  tourDestination = new FormControl('');
  tourDescription = new FormControl('');
  constructor(private router:Router, private formBuilder: FormBuilder) {
    this.addTourForm = this.formBuilder.group({
      'tourName' : ['', [Validators.required]],
      'tourPrice' : ['', [Validators.required]],
      'tourStartDate' : ['', Validators.required],
      'tourEndDate' : ['', Validators.required],
      'tourDestination' : ['', [Validators.required]],
      'tourDescription' : ['', [Validators.required]],
    })
  }

  ngOnInit(): void {
  }

  addTour(){

  }

  getTourNameErrorMessage() {
    return this.tourName.hasError('required') ? 'You must enter a name of the tour!' : '';
  }

  getTourPriceErrorMessage() {
    if(this.tourPrice.value <= 0){
        return "The tour price must be greater than 0!";
    }
    return this.tourPrice.hasError('required') ? 'You must enter a price of the tour!' : '';
  }

  getTourStartDateErrorMessage(){
    if(this.tourStartDate.value > new Date()){
        return "The start date must be after the date of creating the tour!";
    }
    return this.tourStartDate.hasError('required') ? "You must enter a start date of the tour!" : '';
  }

  getTourEndDateErrorMessage(){
    if(this.tourEndDate.value < this.tourStartDate.value){
      return "The start date must be before end date!";
    }
    return this.tourEndDate.hasError('required') ? "You must enter an end date of the tour!" : '';
  }

  getTourDestinationErrorMessage() {
    return this.tourDestination.hasError('required') ? 'You must enter a destination of the tour!' : '';
  }

  getTourDescriptionErrorMessage() {
    return this.tourDescription.hasError('required') ? 'You must enter a description of the tour!' : '';
  }
}
