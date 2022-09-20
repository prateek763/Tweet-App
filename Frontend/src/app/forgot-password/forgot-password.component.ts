import { NONE_TYPE } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { TweetServiceService } from '../tweet-service.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  constructor(private service:TweetServiceService) { }

  new:string="";
  confirm:string="";
  disp="none";
  disp1="none";
  error!:any;
  ngOnInit(): void {
  }

  /*check(x:any){
    console.warn("sdaa")
    if (this.new==this.confirm) {
      this.disp="block";
    }
  }*/

  resetPassword(data:any,form:NgForm){
    console.warn(data);
    this.service.forgot(data.username,data.password).subscribe(
      (response:any)=>{
        console.warn(response);
        console.warn(response.message);
        this.disp="block";
        form.resetForm();
      },
      (error) => {
        this.disp1="block";
        console.error('error caught in component')
        console.warn(error);
        this.error=error.error.message;
      }
    );
  }
}
