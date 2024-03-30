import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, tap} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environemnt";
import {AppUserModel} from "../models/appUser-model";

const BASE_URL= environment.BASE_URL + '/api/users';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user = new BehaviorSubject<AppUserModel | null>(null);

  constructor(private http:HttpClient) { }

  authenticate(credentials: { username: string, password: string }): Observable<AppUserModel> {
    const headers = new HttpHeaders(credentials
      ? {authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)}
      : {});
    console.log(credentials);
    console.log(headers);
    return this.http.get<AppUserModel>(BASE_URL + '/login', {headers: headers})
      .pipe(
        tap(userModel => {
          this.user.next(userModel);
        })
    );
  }
}
