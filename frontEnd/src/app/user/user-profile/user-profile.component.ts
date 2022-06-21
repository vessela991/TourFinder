import { Component, OnInit } from '@angular/core';
import {UserGet} from "../../../models/UserGet";
import {UserService} from "../services/user.services";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
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
