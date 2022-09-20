import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastService } from '../toast-service';
import { TweetServiceService } from '../tweet-service.service';
import { UserDetails } from '../user-details';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {

  constructor(private route:Router, private aroute:ActivatedRoute, private service:TweetServiceService, private toastService:ToastService) { }

  username!:string
  tweets:any;
  searchText = '';
  users !: UserDetails[];
  allUsers !: UserDetails[];

  ngOnInit(): void {
    this.username= this.aroute.snapshot.params.user;
    console.warn(this.username);
    this.service.getAllTweet().subscribe(
      (response:any)=>{
        console.warn(response);
        this.tweets=response;
        console.warn(this.tweets)
      },
      (error) => {
        console.error('error caught in component')
        console.warn(error);
      }
    );
  }
  post={
    tweet:""
  };
  disp="none"
  postTweet(post:string){
    console.warn(post);
    this.post.tweet=post;
    this.service.postTweet(this.username,this.post).subscribe(
      (response:any)=>{
        console.warn(response);
        this.disp="block";
        this.toastService.show('Post Added Successfully', { classname: 'bg-success text-light', delay: 10000 });
      },
      (error) => {
        console.error('error caught in component')
        console.warn(error);
        this.toastService.show('Post not added. Some error occurred', { classname: 'bg-danger text-light', delay: 15000 });
      }
    );
  }

  toast(){
    console.warn("calling")
    this.toastService.show('I am a success toast', { classname: 'bg-success text-light', delay: 10000 });
  }

  onSearch(){
    this.service.getAllUsers().subscribe(
      (response:any)=>{
        console.warn(response);
        this.users=response;
        this.allUsers=response
      },
      (error) => {
        console.error('error caught in component')
        console.warn(error);
      }
    );
  }

  search(user:string){
    console.warn(user);
    this.route.navigateByUrl("/search/"+user+"/"+this.username);
  }

  public searchUsers(key: string): void {
    console.log(key);
    const results: UserDetails[] = [];
    for (const user of this.allUsers) {
      if (user.username.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(user);
      }
    }
    this.users = results;
    /*if (results.length === 0 || !key) {
      this.users= this.allUsers;
    }*/
  }

  select(user:string){
    console.warn(user);
    this.searchText=user;
  }

  ngOnDestroy(): void {
    this.toastService.clear();
  }
}
