import { TestBed, inject } from '@angular/core/testing';

import { CivicInfoService } from './civic-info.service';

describe('CivicInfoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CivicInfoService]
    });
  });

  it('should be created', inject([CivicInfoService], (service: CivicInfoService) => {
    expect(service).toBeTruthy();
  }));
});
