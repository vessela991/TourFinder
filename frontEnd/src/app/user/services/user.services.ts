import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/identity/services/auth.service';
import { UserGet } from 'src/models/UserGet';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private options = {
    headers: new HttpHeaders({
      'Authorization': `Bearer ${this.auth.getToken()}`
    })
  };
  private userPath = environment.apiUrl + 'user';

  constructor(private http: HttpClient, private auth: AuthService) {
  }

  getUser(): Observable<UserGet> {
    return this.http.get<UserGet>(this.userPath, this.options);
  }
}
