import { HttpClient } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TweetServiceService } from '../tweet-service.service';

import { UserComponent } from './user.component';

describe('UserComponent', () => {
  let component: UserComponent;
  let fixture: ComponentFixture<UserComponent>;
  let route:Router;
  let aroute:ActivatedRoute;
  let service:any;
  let http: HttpClient;

  beforeEach(async () => {
    route= jasmine.createSpyObj('Router', ['navigateByUrl']);
    //aroute= jasmine.createSpyObj('ActivatedRoute', ['snapshot']);
    http= jasmine.createSpyObj('HttpClient', ['get']);
    await TestBed.configureTestingModule({
      declarations: [ UserComponent ],
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
    fixture = TestBed.createComponent(UserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    service.getUserByUsername.and.returnValue({ subscribe: () => {} });
    let profileComponent=new UserComponent(aroute,route,service);
    expect(component).toBeTruthy();
  });
});
