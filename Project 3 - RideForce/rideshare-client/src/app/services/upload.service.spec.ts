import { TestBed } from '@angular/core/testing';

import { UploadService } from './upload.service';

describe('UploadService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UploadService = TestBed.get(UploadService);
    expect(service).toBeTruthy();
  });
  /*
  xit('should get an S3 bucket', () => {
    const service: UploadService = TestBed.get(UploadService);
    expect(service.getS3Bucket()).toBeTruthy();
  });
  */
});
