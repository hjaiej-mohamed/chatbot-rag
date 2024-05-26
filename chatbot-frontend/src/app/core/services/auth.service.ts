import {  HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { UserStorageService } from './user-storage.service';
import { Login } from '../models/Login';

const BASE_URL="http://localhost:8082/auth/";
const AUTH_HEADER="authorization"
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient,
    private userStorageService:UserStorageService) { }

  
  login(loginInformation:Login) {
    return this.http.post(BASE_URL+'authenticate',loginInformation,{observe:'response'})
    .pipe(
      map((res:any)=> {
        console.log(res.body)
        this.userStorageService.saveToken(res.body.accessToken);
        console.log("userid:",UserStorageService.getUserId())
        console.log("role:",UserStorageService.getUserRole())
        return res;
      })
    )
  }

}
