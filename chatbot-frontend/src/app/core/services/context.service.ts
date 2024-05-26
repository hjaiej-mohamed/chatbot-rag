import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Context } from '../models/Context';

const BASE_URL="http://localhost:8082/api/context"
@Injectable({
  providedIn: 'root'
})
export class ContextService {

  constructor(private http:HttpClient) { }

  addContext(file:File,PDFDescription:string):Observable<Context>{
    const formData = new FormData()
    formData.append('file',file);
    formData.append('description',PDFDescription)
    return this.http.post<Context>(`${BASE_URL}`,formData)
  }
  getAllContexts():Observable<Context[]>{
    return this.http.get<Context[]>(`${BASE_URL}`)
  }
  deleteContextById(id:number){
    return this.http.delete(`${BASE_URL}/${id}`)
  }
}
