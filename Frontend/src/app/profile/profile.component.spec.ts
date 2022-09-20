import { HttpClient } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { TweetServiceService } from '../tweet-service.service';

import { ProfileComponent } from './profile.component';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let route:Router;
  let aroute:ActivatedRoute;
  let service:any;
  let http: HttpClient;

  beforeEach(async () => {
    route= jasmine.createSpyObj('Router', ['navigateByUrl']);
    //aroute= jasmine.createSpyObj('ActivatedRoute', ['snapshot']);
    http= jasmine.createSpyObj('HttpClient', ['get']);
    await TestBed.configureTestingModule({
      declarations: [ ProfileComponent ],
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
    service=jasmine.createSpyObj( ['getUserByUsername']);
    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    service.getUserByUsername.and.returnValue({ subscribe: () => {} });
    let profileComponent=new ProfileComponent(route,aroute,service);
    expect(profileComponent).toBeTruthy();
  });
});
