import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameSortDateComponent } from './game-sort-date.component';

describe('GameSortDateComponent', () => {
  let component: GameSortDateComponent;
  let fixture: ComponentFixture<GameSortDateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GameSortDateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameSortDateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
