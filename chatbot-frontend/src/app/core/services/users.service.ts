import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/User';


const BASE_URL="http://localhost:8082/api/users"
@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http:HttpClient) { }
  registerClient(registerRequestDTO:any):Observable<any>{
    return this.http.post(BASE_URL+'/register-client',registerRequestDTO)
  }
  registerAdmin(registerRequestDTO:any):Observable<any>{
    return this.http.post(BASE_URL+'/register-admin',registerRequestDTO)
  }

  getAdminUsers():Observable<User[]>{
    return this.http.get<User[]>(BASE_URL)
  }
  deleteAdminById(id:number){
    return this.http.delete(`${BASE_URL}/${id}`)
  }
  updateAdmin(user:User){
    return this.http.put(`${BASE_URL}/${user.id}`,user)
  }
}
