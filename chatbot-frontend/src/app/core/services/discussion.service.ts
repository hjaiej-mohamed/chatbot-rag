import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Discussion } from '../models/Discussion';

const BASE_URL="http://localhost:8082/api/discussion"

@Injectable({
  providedIn: 'root'
})
export class DiscussionService {

  constructor(private http:HttpClient) { }


  getDiscussionsByuserId(userId:number):Observable<Discussion[]>{
    return this.http.get<Discussion[]>(`${BASE_URL}/${userId}`)
  }
  getDiscussions(){
    return this.http.get<Discussion[]>(`${BASE_URL}`)
  }
  deleteDiscussionById(id:number){
    return this.http.delete(`${BASE_URL}/${id}`)
  }
}
