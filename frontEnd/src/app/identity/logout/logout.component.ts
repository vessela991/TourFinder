import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(private router:Router, private authService: AuthService) {
  }

  logOut(){
    this.authService.logout();
    return this.router.navigate(['/login']);
  }

  ngOnInit(): void {
  }

}
