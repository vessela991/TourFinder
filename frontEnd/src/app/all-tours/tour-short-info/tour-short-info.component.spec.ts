import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TourShortInfoComponent } from './tour-short-info.component';

describe('TourShortInfoComponent', () => {
  let component: TourShortInfoComponent;
  let fixture: ComponentFixture<TourShortInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TourShortInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TourShortInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
