import { Injectable } from '@angular/core';
import { Observable, Subject, map } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserStorageService } from './user-storage.service';
import { Answer } from '../models/Answer';

export class Message {
  constructor(public author: string, public content: string) {}
}

// const BASE_URL="http://localhost:8080/auth/chat/"
const BASE_URL="http://localhost:8082/api/chat/send"

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  audioFile = new Audio(
    "https://s3-us-west-2.amazonaws.com/s.cdpn.io/3/success.mp3"
  );
  constructor(private http:HttpClient) { }
  conversation = new Subject<Message[]>();

  sendQuestion(msg: string,discusionId:number):Observable<Answer> {
    const userMessage = new Message("user", msg);
    this.conversation.next([userMessage]);
    // let httpHeader = new HttpHeaders()
    // const bearerToken="Bearer " +UserStorageService.getToken()
    // httpHeader.set('Authorization',bearerToken) 
    return this.http.post(BASE_URL,{
        question:msg,
        discussionId:discusionId,
        userId:UserStorageService.getUserId()
    }).pipe(
      map((res:Answer)=>{
      console.log("botMessage:"+res)
      const botMessage = new Message("bot", res.answer);
      this.conversation.next([botMessage]);
      this.playFile();
      return res;
    }))
  }

  playFile() {
    this.audioFile.play();
  }

}
