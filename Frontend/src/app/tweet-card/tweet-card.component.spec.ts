import { HttpClient } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TweetServiceService } from '../tweet-service.service';

import { TweetCardComponent } from './tweet-card.component';

describe('TweetCardComponent', () => {
  let component: TweetCardComponent;
  let fixture: ComponentFixture<TweetCardComponent>;
  let http : HttpClient

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TweetCardComponent ],
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
    fixture = TestBed.createComponent(TweetCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
