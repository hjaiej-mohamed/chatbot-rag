import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { UsersService } from 'src/app/core/services/users.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  form!:FormGroup
  showPassword: boolean = false

  constructor(private fb:FormBuilder,
              private usersService:UsersService,
              private router:Router,
              private notification:NzNotificationService){}
              ngOnInit(): void {
                this.form=this.fb.group({
                  firstname:[null,[Validators.required]],
                  lastname:[null,[Validators.required]],
                  email:[null,[Validators.required,Validators.email]],
                  password:[null,[Validators.required,Validators.minLength(5)]],
                  cin:[null,[Validators.required]],
                  address:[null,[Validators.required]]
                  // tel:[null,[Validators.required]],
                  // address:[null,[Validators.required]],

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
                console.log(this.form.value)
                this.usersService.registerClient(this.form.value).subscribe((res:any)=>{
                  this.notification.success(
                    'Register successfully',
                    '',
                    {nzDuration:2500}
                  )
                  this.router.navigateByUrl('login')
                }),
                (errors)=>{
                  alert("Register erorrs")
                }
              }
              toggleShowPassword(){
                this.showPassword=!this.showPassword
              }
              
             
}
