import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserStorageService } from './core/services/user-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'chatbot';
  isClientLoggedIn :boolean = UserStorageService.isClientLogin()
  isAdminLoggedIn: boolean = UserStorageService.isAdminLogin()
  username:string=UserStorageService.getUsername()

  constructor(private router:Router,
              private storageService:UserStorageService)
              {}
    ngOnInit(){
      this.router.events.subscribe(()=>{
        this.isClientLoggedIn= UserStorageService.isClientLogin()
        this.isAdminLoggedIn = UserStorageService.isAdminLogin()
        this.username=UserStorageService.getUsername()
      })
    }

    isPageLogin(){
      const openPages=[
        '/login',
        '/register',
        '/home'
      ]
      return openPages.some( path =>this.router.url.startsWith(path))
    }
    logout(){
      UserStorageService.signOut()
      this.router.navigateByUrl('login')
    }
}
