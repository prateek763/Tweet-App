import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class TweetServiceService {

  constructor(private http:HttpClient) { }


  login(username:string, password:string){
    console.warn(username+" "+password);
    let param = new HttpParams().set("username",username).set("password",password);
    console.warn(param.get("username"));
    return this.http.get<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/login",{params:param});
  }

  register(user:any){
    console.warn(user);
    return this.http.post<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/register",user);
  }

  forgot(username:string, password:string){
    console.warn(username+" "+password);
    let param = new HttpParams().set("password",password);
    console.warn(param.get("password"));
    return this.http.get<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/"+username+"/forget",{params:param});
  }

  getAllTweet(){
    return this.http.get<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/all");
  }

  postTweet(username:string, tweet:any){
    return this.http.post<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/"+username+"/add",tweet);
  }

  getUserByUsername(username:string){
    return this.http.get("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/user/search/"+username);
  }

  getTweetPerUser(username:string){
    return this.http.get<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/"+username);
  }

  updateTweet(username:string,id:string,tweet:string){
    let param = new HttpParams().set("tweet",tweet);
    console.warn(param.get("tweet"));
    return this.http.put<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/"+username+"/update/"+id,tweet);
  }

  deleteTweet(username:string,id:string){
    return this.http.delete<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/"+username+"/delete/"+id);
  }

  likeTweet(username:string,id:string){
    return this.http.put<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/"+username+"/like/"+id,"");
  }

  replyTweet(username:string,id:string,tweet:string,userReplied:string){
    console.warn(id+" "+username);
    let param = new HttpParams().set("tweet",tweet).set("user",userReplied);
    console.warn(param.get("tweet"));
    return this.http.post<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/"+username+"/reply/"+id,"",{params:param});
  }

  getAllUsers(){
    return this.http.get<any>("http://tweetapp-env.eba-tnhutddf.us-west-2.elasticbeanstalk.com/api/v1.0/tweets/users/all");
  }
}
