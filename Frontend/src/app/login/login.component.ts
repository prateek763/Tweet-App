import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthServiceService } from '../auth-service.service';
import {TweetServiceService} from '../tweet-service.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private service:TweetServiceService, private route:Router, private authService:AuthServiceService) { }

  ngOnInit(): void {
  }

  error!:any|null;
  disp="none";
  disp1="none";
  logdisp="none"

  login(user:any){
    console.log(user);
    this.service.login(user.username,user.password).subscribe(
      (response:any)=>{
        console.warn(response);
        console.warn(response.message);
        this.authService.login();
        this.route.navigateByUrl("/home/"+user.username);
      },
      (error) => {
        this.logdisp="block";
        console.error('error caught in component')
        console.warn(error);
        this.error=error.error.message;
      }
    );
  }

  signUp(user:any, form:NgForm){
    console.log(user);
    this.service.register(user).subscribe(
      (response:any)=>{
        console.warn(response);
        console.warn(response.message);
        this.disp1="block";
      },
      (error) => {
        this.disp="block";
        console.error('error caught in component')
        console.warn(error);
        this.error=error.error.message;
      }
    );
    form.resetForm();
  }
}
