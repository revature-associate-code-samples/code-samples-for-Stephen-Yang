import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayBookmarksComponent } from './display-bookmarks.component';

describe('DisplayBookmarksComponent', () => {
  let component: DisplayBookmarksComponent;
  let fixture: ComponentFixture<DisplayBookmarksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DisplayBookmarksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayBookmarksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
