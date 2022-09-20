import { HttpClient } from '@angular/common/http';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { TweetServiceService } from '../tweet-service.service';

import { HomeComponent } from './home.component';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let route:Router;
  let aroute:ActivatedRoute;
  let service:any;
  let http: HttpClient;

  beforeEach(async () => {
    route= jasmine.createSpyObj('Router', ['navigateByUrl']);
    //aroute= jasmine.createSpyObj('ActivatedRoute', ['snapshot']);
    http= jasmine.createSpyObj('HttpClient', ['get']);
    await TestBed.configureTestingModule({
      declarations: [ HomeComponent ],
      imports:[FormsModule],
      providers:[TweetServiceService,
        {
          provide: Router,
          useValue: route
        },
        {
          provide: ActivatedRoute,
          useValue :{
            snapshot: {params: {user: 'Pra122'}}
          }
        },
        {
          provide: HttpClient,
          useValue: http
        }
        ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    service=jasmine.createSpyObj( ['getAllTweet']);
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

  });

  it('should create', () => {
    service.getAllTweet.and.returnValue(of(true));
    let homeComponent=new HomeComponent(route,aroute,service);
    homeComponent.ngOnInit();
    expect(homeComponent).toBeTruthy();
  });
});
