import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthServiceService } from './auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private route:Router, private service:AuthServiceService){}

  canActivate():boolean {
    console.warn(this.service.isLoggedIn());
    if(this.service.isLoggedIn()){
      return true;
    }
    else{
      
      this.route.navigateByUrl("login");
      return false;
    }
  }
}
