import { Component, OnInit,Input } from '@angular/core';
import { Router } from '@angular/router';
import { TweetServiceService } from '../tweet-service.service';

@Component({
  selector: 'app-tweet-card-user',
  templateUrl: './tweet-card-user.component.html',
  styleUrls: ['./tweet-card-user.component.css']
})
export class TweetCardUserComponent implements OnInit {

  constructor(private route:Router, private service:TweetServiceService) { }
  @Input() tweets:any;
  @Input() username!:string;

  ngOnInit(): void {
    console.warn(this.username);
    console.warn(this.tweets);
  }

  user!:string;
  id!:string;

  onReply(username:string,id:string){
    console.warn(id+" "+username)
    this.user=username;
    this.id=id;
    console.warn(this.id+" "+this.user)
  }

  reply(tweet:string){
    console.warn("reply");
    console.warn(this.id+" "+this.user)
    this.service.replyTweet(this.user,this.id,tweet,this.username).subscribe(
      (response:any)=>{
        console.warn(response);
        //window.location.reload();
        let currentUrl = this.route.url;
    this.route.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.route.navigate([currentUrl]);
        console.log(currentUrl);
    });
      },
      (error) => {
        console.error('error caught in component')
        console.warn(error);
      }
    );
  }

  like(username:string,id:string){
    console.warn(username+"  "+id);
    this.service.likeTweet(username,id).subscribe(
      (response:any)=>{
        console.warn(response);
        //window.location.reload();
        let currentUrl = this.route.url;
    this.route.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.route.navigate([currentUrl]);
        console.log(currentUrl);
    });
      },
      (error) => {
        console.error('error caught in component')
        console.warn(error);
      }
    );
  }
}
