import { HttpClient } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { TweetServiceService } from '../tweet-service.service';

import { TweetCardUserComponent } from './tweet-card-user.component';

describe('TweetCardUserComponent', () => {
  let component: TweetCardUserComponent;
  let fixture: ComponentFixture<TweetCardUserComponent>;
  let route: Router;
  let http: HttpClient;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TweetCardUserComponent ],
      providers:[TweetServiceService,
        {
          provide: Router,
          useValue: route
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
    fixture = TestBed.createComponent(TweetCardUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
