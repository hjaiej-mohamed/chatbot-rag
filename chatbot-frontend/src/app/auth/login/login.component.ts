import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  form!:FormGroup
  isShowPassword: boolean=false;
  constructor(private fb:FormBuilder,
              private authService:AuthService,
              private router:Router,
              private notification:NzNotificationService){}

  ngOnInit(): void {
    this.form=this.fb.group({
      email:[null,[Validators.required,Validators.email]],
      password:[null,[Validators.required,Validators.minLength(5)]]
    })
  }
  get email(){
    return this.form.get('email');
  }
  get password(){
    return this.form.get('password')
  }
  //login submit function
  submit(){
    this.authService.login(this.form.value).subscribe((res:any)=>{
      this.notification.success(
        'Login successfully',
        '',
      {nzDuration:2500})
      this.router.navigateByUrl('chat')
    },
    (errors)=>{
      this.notification.error(
        'Login failed',
        'please try again or register',
      {nzDuration:2500})
    })
  }
  
  toggleFieldTextTypePassword() {
    this.isShowPassword = !this.isShowPassword;
  }
 
}
