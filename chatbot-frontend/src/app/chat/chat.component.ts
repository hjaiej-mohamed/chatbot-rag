import { Component } from '@angular/core';
import { ChatService, Message } from '../core/services/chat.service';
import { DiscussionService } from '../core/services/discussion.service';
import { Discussion } from '../core/models/Discussion';
import { UserStorageService } from '../core/services/user-storage.service';
import { QuestionAnswer } from '../core/models/QuestionAnswer';
import { QaService } from '../core/services/qa.service';
import { NzNotificationService } from 'ng-zorro-antd/notification';
@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent {
  messages: Message[] = [];
  value!: string;
  discussionId:number=-1
  selectedDiscssion!:Discussion;
  discussions:Discussion[] = [];
  QAs:QuestionAnswer[]=[]
  chatView:boolean=true;

  constructor(public chatService: ChatService,private discussionService:DiscussionService,private QAService:QaService,    private notificationService:NzNotificationService,) { }

  ngOnInit() {
    this.getDiscussionsByuserId()
      this.chatService.conversation.subscribe((val) => {
      this.messages = this.messages.concat(val);
    });
  }

  getDiscussionsByuserId(){
    this.discussionService.getDiscussionsByuserId(UserStorageService.getUserId()).subscribe((discussions)=>{
      this.discussions=discussions;
    },err=>{
      this.notificationService.error('Error occured while getting discussions','',{nzDuration:2500});
    })
  }

  sendMessage() {
    this.chatService.sendQuestion(this.value,this.discussionId).subscribe((answer)=>{
      this.discussionId=answer.discussionId;
    },err=>{
      this.notificationService.error('Error occured while sending message','',{nzDuration:2500});
    });
    this.value = '';
  }
  startNewDiscussion(){
    this.messages=[]
    this.discussionId=-1
  }
  getQAsByDiscussionId(discussion:Discussion){
    this.QAService.getQAsByDiscussionId(discussion.id).subscribe((QAs:QuestionAnswer[])=>{
      this.discussionId=discussion.id
      this.selectedDiscssion=discussion;
      this.QAs= QAs
      this.chatView=false;
    },err=>{
      this.notificationService.error('Error occured while getting QAs','',{nzDuration:2500});
    })
  }
  returnToChat(){
    this.chatView=true
    this.discussionId=-1
    this.getDiscussionsByuserId()
  }
  initChat(){
    this.chatView=true
    this.discussionId=-1
    this.getDiscussionsByuserId()
    this.messages=[];
  }
  deleteDiscussion(discussionId:number){
    this.discussionService.deleteDiscussionById(discussionId).subscribe((data) =>{
      this.initChat();
      this.notificationService.success('Discussion deleted successfully', '', {nzDuration:2500});
    },err=>{
      this.notificationService.error('Error occured while deleting discussion','',{nzDuration:2500});
    }
    )
  }
}
