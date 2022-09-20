import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TweetServiceService } from '../tweet-service.service';
import { UserDetails } from '../user-details';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private route:Router,private aroute:ActivatedRoute,private service:TweetServiceService) { }

  username!:string;
  user!: string;
  userDetails={
    first_name:"Prateek",
    last_name:"Jaiswal",
    email:"jaiswalprateekr291@gmail.com",
    username:"Pra12",
    password:"LoveScience@123",
    number:8448635400
  }
  posts:any;
  searchText = '';
  users !: UserDetails[];
  allUsers !: UserDetails[];

  ngOnInit(): void {
    this.username= this.aroute.snapshot.params.user;
    this.service.getUserByUsername(this.username).subscribe(
      (response:any)=>{
        console.warn(response);
        this.userDetails=response;
        console.warn(this.userDetails)
      },
      (error) => {
        console.error('error caught in component')
        console.warn(error);
      }
    );
    this.service.getTweetPerUser(this.username).subscribe(
      (response:any)=>{
        console.warn(response);
        this.posts=response;
        console.warn(this.posts)
      },
      (error) => {
        console.error('error caught in component')
        console.warn(error);
      }
    );
  }
  disp="none"
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

}
