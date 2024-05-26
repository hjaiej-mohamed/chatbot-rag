import { Component, OnInit } from '@angular/core';
import { ContextService } from '../core/services/context.service';
import { Context } from '../core/models/Context';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UsersService } from '../core/services/users.service';
import { User } from '../core/models/User';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  dtOptions: any= {
    // pageLength: 5,
    paging:false,
    lengthChange: false,
    ordering: false,
    language: {
        url: "//cdn.datatables.net/plug-ins/1.10.21/i18n/French.json",
    },
  }
  isSendingFile:Boolean = false
  isSendingUserInfo:Boolean =false
  file!:File
  contexts!:Context[]
  adminUsers!:User[]
  form!:FormGroup 
  showPassword:Boolean = false
  PDFDescription!:string
  constructor(private contextService:ContextService,
    private notificationService:NzNotificationService,
    private fb:FormBuilder,
    private usersServices:UsersService
  ){}
  ngOnInit(): void {
    this.getContexts();
    this.getAdminUsers()
    this.form=this.fb.group({
      id:[null],
      firstname:[null,[Validators.required]],
      lastname:[null,[Validators.required]],
      email:[null,[Validators.required,Validators.email]],
      password:[null,[Validators.required,Validators.minLength(5)]],
      cin:[null,[Validators.required]],
      address:[null,[Validators.required]],
      tel:[null,[Validators.required]]
    })
  }
  get email(){
    return this.form.get('email');
  }
  get password(){
    return this.form.get('password')
  }
  get firstname(){
    return this.form.get('firstname')
  }
  get lastname(){
    return this.form.get('lastname')
  }
  get address(){
    return this.form.get('address')
  }
  get cin(){
    return this.form.get('cin')
  }
  //login submit function
  submit(){
    this.isSendingUserInfo=true
    this.usersServices.registerAdmin(this.form.value).subscribe((res:any)=>{
      this.getAdminUsers()
      const btn = document.getElementById("closeAddUserModal")
      btn.click();
      this.isSendingUserInfo=false
      this.form.reset()
      this.notificationService.success(
        'Un nouveau utilisateur a été ajouté avec succées',
        '',
        {nzDuration:2500}
      )
    }),
    (errors)=>{
      const btn = document.getElementById("closeAddUserModal")
      btn.click();
      this.isSendingUserInfo=false
      this.form.reset()
      this.isSendingUserInfo=false
      this.notificationService.error("erreur est survenue lors de l'ajout d'un nouveau utilisateur",'',{nzDuration:2500})
    }
  }
  getContexts(){
    this.contextService.getAllContexts().subscribe((contexts:Context[])=>{
      this.contexts = contexts;
    }, err =>{
      this.notificationService.error("Erreur lors de la récupération des contexts",'Error', err);
    })
  }

  getAdminUsers(){
    this.usersServices.getAdminUsers().subscribe((adminUsers:User[])=>{
      this.adminUsers = adminUsers;
      console.log(adminUsers)
    }, err =>{
      this.notificationService.error("Erreur lors de la récupération des utilisateurs",'Error', err);
    })
  }
  onChange(event:any):void{
    this.file = event.target.files[0];
    console.log(this.file);
  }
  addContext(){
    this.isSendingFile=true
    this.contextService.addContext(this.file,this.PDFDescription).subscribe((context:Context)=>{
      this.contexts.push(context);
      const btn = document.getElementById("closeModal")
      btn.click();
      this.notificationService.success('Context added successfully', '', {nzDuration:2500});
      this.isSendingFile=false
    },error =>{
      this.isSendingFile=false
      this.notificationService.error('Error occured while sending file','',{nzDuration:2500});
    }
  );
  }
  delete(id:number){
    this.contextService.deleteContextById(id).subscribe(()=>{
      this.notificationService.success('Context deleted successfully', '', {nzDuration:2500});
      this.getContexts();
    },err=>{
      this.notificationService.error('Error occured while deleting context','',{nzDuration:2500});

    })
  }
  deleteAdmin(id:number){
    this.usersServices.deleteAdminById(id).subscribe(()=>{
      this.notificationService.success('Utilisateur deleted successfully', '', {nzDuration:2500});
      this.getAdminUsers();
    },err=>{
      this.notificationService.error('Error occured while deleting user','',{nzDuration:2500});

    })
  }
  initAdminForUpdate(adminUser:User){
    this.form.patchValue(adminUser)
  }
  updateAdmin(){
    this.isSendingUserInfo=true
    console.log(this.form.value)
    this.usersServices.updateAdmin(this.form.value).subscribe((res:any)=>{
      this.getAdminUsers();
      const btn = document.getElementById("closeEditUserModal")
      btn.click();
      this.isSendingUserInfo=false
      this.form.reset()
      this.notificationService.success(
        'l\'utilidateur a été modifié avec succées',
        '',
        {nzDuration:2500}
      )
    }),
    (errors)=>{
      this.isSendingUserInfo=false
      this.notificationService.error("erreur est survenue lors de la modification de l'utilisateur",'',{nzDuration:2500})
    }
  }
  toggleShowPassword(){
    this.showPassword =!this.showPassword;
  }
  resetForm(){
    this.form.reset();
  }
}
