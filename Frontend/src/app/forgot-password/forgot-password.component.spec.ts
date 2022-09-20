import { HttpClient } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, NgForm } from '@angular/forms';
import { of } from 'rxjs';
import { TweetServiceService } from '../tweet-service.service';

import { ForgotPasswordComponent } from './forgot-password.component';

describe('ForgotPasswordComponent', () => {
  let component: ForgotPasswordComponent;
  let fixture: ComponentFixture<ForgotPasswordComponent>;
  let service:any;
  let http:HttpClient;

  beforeEach(async () => {
    http= jasmine.createSpyObj('HttpClient', ['get']);
    await TestBed.configureTestingModule({
      declarations: [ ForgotPasswordComponent ],
      imports:[FormsModule],
      providers:[TweetServiceService,
      {
        provide: HttpClient,
        useValue: http
      }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ForgotPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    service=jasmine.createSpyObj( ['forgot']);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call the forgot method', ()=>{
    let data={
      useranme:'Pra122',
      password:'asdf'
    };
    service.forgot.and.returnValue(of(true))
    let forgotComponent=new ForgotPasswordComponent(service);
    let form= jasmine.createSpyObj('NgForm', ['resetForm']);
    forgotComponent.resetPassword(data,form);
    expect(service.forgot).toHaveBeenCalledTimes(1);
  });

  it('should reset the form after the password is changed', ()=>{
    let data={
      useranme:'Pra122',
      password:'asdf'
    };
    service.forgot.and.returnValue(of(true))
    let forgotComponent=new ForgotPasswordComponent(service);
    let form= jasmine.createSpyObj('NgForm', ['resetForm']);
    forgotComponent.resetPassword(data,form);
    expect(form.resetForm).toHaveBeenCalledTimes(1);
  });
});
