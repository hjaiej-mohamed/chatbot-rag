import { Injectable } from '@angular/core';


const TOKEN = 's_token'
const USER  = 's_user'

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {  
  constructor() { }

  public saveToken(token:string):void{
    window.sessionStorage.removeItem(TOKEN)
    window.sessionStorage.setItem(TOKEN,token)
  }
  static getToken(){
    return window.sessionStorage.getItem(TOKEN)
  }
  static getUsername(){
    const token = window.sessionStorage.getItem(TOKEN)
    if(token){
      const userData=this.decodeJwt(token)
      return userData.username;
    }
    return null;
  }
  static getUserId(){
    const token=this.getToken()
    if(token){
      const userData=this.decodeJwt(token)
      return userData.userId;
    }
    return null;
  }
  private static decodeJwt(token:string):any{
    const jwtData=token.split('.')[1]
    const decodeJwtJsonData= window.atob(jwtData)
    return JSON.parse(decodeJwtJsonData);
  } 

  static getUserRole():string{
    const token = this.getToken()
    if(token){
      const userData=this.decodeJwt(token)
      return userData.roles[0].authority.split('_')[1];
    }
    return null;
  }
  static isClientLogin():boolean{
    const role =this.getUserRole()
    return 'CLIENT'===role
  }

  static isAdminLogin() :boolean{
    const role =this.getUserRole()
    return 'ADMIN'===role
  }
  static signOut():void{
    window.localStorage.removeItem(TOKEN)
  }
  
}
