import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CivicInfoComponent } from './civic-info.component';

describe('CivicInfoComponent', () => {
  let component: CivicInfoComponent;
  let fixture: ComponentFixture<CivicInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CivicInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CivicInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
