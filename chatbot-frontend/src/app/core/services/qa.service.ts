import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { QuestionAnswer } from '../models/QuestionAnswer';


const BASE_URL="http://localhost:8082/api/qa"

@Injectable({
  providedIn: 'root'
})
export class QaService {

  constructor(private http:HttpClient) { }

  getQAsByDiscussionId(discussionId:number):Observable<QuestionAnswer[]>{
    return this.http.get<QuestionAnswer[]>(`${BASE_URL}/discussion/${discussionId}`)
  }
}
