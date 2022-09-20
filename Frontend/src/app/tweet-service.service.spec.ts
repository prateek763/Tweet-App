import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';

import { TweetServiceService } from './tweet-service.service';

describe('TweetServiceService', () => {
  let service: TweetServiceService;
  let http:HttpClient;
  beforeEach(() => {
    http= jasmine.createSpyObj('HttpClient', ['get']);
    TestBed.configureTestingModule({
      imports:[FormsModule],
      providers:[TweetServiceService,
        {
          provide: HttpClient,
          useValue: http
        }
        ]
    });
    service = TestBed.inject(TweetServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
