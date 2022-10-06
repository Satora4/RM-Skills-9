import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupPhaseComponent } from './group-phase.component';

describe('GroupPhaseComponent', () => {
  let component: GroupPhaseComponent;
  let fixture: ComponentFixture<GroupPhaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupPhaseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GroupPhaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
