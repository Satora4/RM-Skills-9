import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayingScheduleComponent } from './playing-schedule.component';

describe('PlayingScheduleComponent', () => {
  let component: PlayingScheduleComponent;
  let fixture: ComponentFixture<PlayingScheduleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlayingScheduleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlayingScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
