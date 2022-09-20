import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastService } from '../toast-service';
import { TweetServiceService } from '../tweet-service.service';

@Component({
  selector: 'app-tweet-card',
  templateUrl: './tweet-card.component.html',
  styleUrls: ['./tweet-card.component.css']
})
export class TweetCardComponent implements OnInit {

  constructor(private service:TweetServiceService, private route:Router, private toastService:ToastService) { }
  @Input() posts:any;
  id!:string;
  username!:string;
  delId!:string;
  delUsername!:string;
  ngOnInit(): void {
    console.warn(this.posts);
  }

  onDelete(id:string,username:string){
    console.warn(id);
    console.warn(username);
    this.delId=id;
    this.delUsername=username;
  }

  delete(){
    this.service.deleteTweet(this.delUsername,this.delId).subscribe(
      (response:any)=>{
        console.warn(response);
        //window.location.reload();
        this.toastService.show('Tweet Deleted Successfully', { classname: 'bg-success text-light', delay: 10000 });
        let currentUrl = this.route.url;
    this.route.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.route.navigate([currentUrl]);
        console.log(currentUrl);

    });
      },
      (error:any) => {
        console.error('error caught in component')
        console.warn(error);
        this.toastService.show('Error occured while deleting tweet', { classname: 'bg-success text-light', delay: 10000 });
      }
    );
  }

  onUpdate(id:string,username:string){
    console.warn(id);
    console.warn(username);
    this.id=id;
    this.username=username;
  }

  update(tweet:string){
    this.service.updateTweet(this.username,this.id,tweet).subscribe(
      (response:any)=>{
        console.warn(response);
        //window.location.reload();
        this.toastService.show('Tweet Updated Successfully', { classname: 'bg-success text-light', delay: 10000 });
        let currentUrl = this.route.url;
    this.route.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.route.navigate([currentUrl]);
        console.log(currentUrl);
    });
      },
      (error) => {
        console.error('error caught in component')
        console.warn(error);
        this.toastService.show('Error occurred while updating update', { classname: 'bg-success text-light', delay: 10000 });
      }
    );
  }
}
