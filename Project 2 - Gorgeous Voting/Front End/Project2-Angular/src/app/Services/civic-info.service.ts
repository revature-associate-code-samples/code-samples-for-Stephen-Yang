import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class CivicInfoService {

  CIVICURL = "https://www.googleapis.com/civicinfo/v2";
  ELECTIONURL = this.CIVICURL + "/elections";
  VOTERURL = this.CIVICURL + "/voterinfo";
  APIKEY = "";
  DEFAULTADDRESS = "";

  constructor(private http: HttpClient) { }

}
