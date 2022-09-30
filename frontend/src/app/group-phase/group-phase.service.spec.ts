import { TestBed } from '@angular/core/testing';

import { GroupPhaseService } from './group-phase.service';

describe('GroupPhaseService', () => {
  let service: GroupPhaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GroupPhaseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
