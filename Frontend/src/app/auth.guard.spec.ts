import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthServiceService } from './auth-service.service';

import { AuthGuard } from './auth.guard';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let service: AuthServiceService;
  let router:Router;

  beforeEach(() => {
    router = jasmine.createSpyObj('Router',['navigateByUrl']);
    TestBed.configureTestingModule({
      providers:[
        AuthServiceService,
      {
        provide: Router,
        useValue : router
      }
    ]
    });
    guard = TestBed.inject(AuthGuard);
    service = TestBed.inject(AuthServiceService);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should return true when user is logged in' , ()=>{
    service.loggedIn=true;
    let result=guard.canActivate();
    expect(result).toBe(true);
  });

  it('should return false when user is not logged in' , ()=>{
    service.loggedIn=false;
    let result=guard.canActivate();
    expect(result).toBe(false);
  });

  it('should navigate to login page when user is not logged in' , ()=>{
    service.loggedIn=false;
    let result=guard.canActivate();
    expect(router.navigateByUrl).toHaveBeenCalledTimes(1);
  });
});
