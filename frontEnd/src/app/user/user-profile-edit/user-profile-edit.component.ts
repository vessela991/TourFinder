import { Component, OnInit } from '@angular/core';
import {UserGet} from "../../../models/UserGet";
import {UserService} from "../services/user.services";

@Component({
  selector: 'app-user-profile-edit',
  templateUrl: './user-profile-edit.component.html',
  styleUrls: ['./user-profile-edit.component.scss']
})
export class UserProfileEditComponent implements OnInit {
  user: UserGet | undefined;

  constructor(private userService:UserService) { }

  ngOnInit(): void {
    this.getUser();
  }

  getUser(){
    this.userService.getUser().subscribe(data => {
      this.user = data;
    })
  }
}
