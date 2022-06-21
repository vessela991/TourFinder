import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-comment',
  templateUrl: './add-comment.component.html',
  styleUrls: ['./add-comment.component.scss']
})
export class AddCommentComponent implements OnInit {
  addCommentGroup : FormGroup;
  username = new FormControl('');
  photo = new FormControl('');
  comment = new FormControl('');
  constructor(private router:Router, private formBuilder: FormBuilder) {
    this.addCommentGroup = this.formBuilder.group({
      'username' : ['', [Validators.required]],
      'photo' : ['', [Validators.required]],
      'comment': ['', [Validators.required]],
    })
  }

  ngOnInit(): void {
  }

  addComment(){

  }

}
