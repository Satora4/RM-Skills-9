import { TestBed } from '@angular/core/testing';

import { PlayingScheduleService } from './playing-schedule.service';

describe('GameService', () => {
  let service: PlayingScheduleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlayingScheduleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
