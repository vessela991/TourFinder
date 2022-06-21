import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TourCommentsComponent } from './tour-comments.component';

describe('TourCommentsComponent', () => {
  let component: TourCommentsComponent;
  let fixture: ComponentFixture<TourCommentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TourCommentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TourCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
