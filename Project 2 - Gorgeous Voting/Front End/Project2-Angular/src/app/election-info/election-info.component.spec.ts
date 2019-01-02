import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ElectionInfoComponent } from './election-info.component';

describe('ElectionInfoComponent', () => {
  let component: ElectionInfoComponent;
  let fixture: ComponentFixture<ElectionInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ElectionInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ElectionInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
