import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameSortGroupComponent } from './game-sort-group.component';

describe('GameSortGroupComponent', () => {
  let component: GameSortGroupComponent;
  let fixture: ComponentFixture<GameSortGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GameSortGroupComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameSortGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
