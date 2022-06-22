import {Injectable} from "@angular/core";
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../../identity/services/auth.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {Tour} from "../../../models/Tour";
import {TourGet} from "../../../models/TourGet";

@Injectable()
export class TourServices{
  private baseTourUrl = environment.apiUrl + 'tour';

  private options = {
    headers: new HttpHeaders({
      'Authorization' : `Bearer ${this.auth.getToken()}`
    }),
  };

  constructor(private http: HttpClient, private auth: AuthService, private router: Router) {
  }

  upload(data: any): Observable<Tour>{
    return this.http.post<Tour>(this.baseTourUrl, data, this.options);
  }

  edit(id: any, data: any): Observable<Tour>{
    return this.http.put<Tour>(this.baseTourUrl + '/' + id, data, this.options);
  }

  getTour(id: any): Observable<TourGet>{
    return this.http.get<TourGet>(this.baseTourUrl + '/' + id);
  }

  deleteTour(id: any): Observable<Tour>{
    return this.http.delete<Tour>(this.baseTourUrl + '/' + id, this.options);
  }

  getAllTours(): Observable<Array<Tour>>{
    return this.http.get<Array<Tour>>(this.baseTourUrl);
  }

  isLogged(){
    return this.auth.isLogged();
  }

}
