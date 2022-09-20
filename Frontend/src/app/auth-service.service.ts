import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor() { }

  loggedIn=false;

  login(){
    console.warn("Logged In");
    this.loggedIn=true;
  }
  logout(){
    console.warn("Logged Out");
    this.loggedIn=false;
  }
  isLoggedIn(){
    console.warn("Check Logged In");
    return this.loggedIn;
  }
}
