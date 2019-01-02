import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { map } from 'rxjs/operators'
//import { ngModule } from '@angular/common'

@Component({
  selector: 'app-civic-info',
  templateUrl: './civic-info.component.html',
  styleUrls: ['./civic-info.component.css']
})
export class CivicInfoComponent implements OnInit {
  title = 'app';
  restItems: any;
  apiKey = 'key=AIzaSyDiJXpugiOVdpurOMtACPLWUxjn6JWSRn8'
  restItemsUrl = '';
  address: string = '';
  urlAddress: string = '';
  outputArray = [];
  displayData = false;

  

  constructor(private http: HttpClient) { }

  ngOnInit() {
    
  }

  reset() {
    this.outputArray = [];
    this.displayData = false;
    this.restItems = null;
  }

  getAddress() {
    
    //convert the address into a url compatable format
    this.reset();
    var re = / /gi;
    var str = this.address;
    this.urlAddress = str.replace(re, "%20");

    //build the url
    this.restItemsUrl = `https://www.googleapis.com/civicinfo/v2/representatives?address=${this.urlAddress}
    &includeOffices=true&levels=country&${this.apiKey}`;
    
    // log results
    console.log(this.address);
    console.log(this.urlAddress);
    console.log(this.restItemsUrl);
    
    //call the service
    this.getRestItems();

    //display the table
    this.displayData = true;
    
  }
  
  // should be moved to a service
  getRestItems(): void {
    this.restItemsServiceGetRestItems().subscribe(restItems => {
      this.restItems = restItems;
      for(let i = 0; i < this.restItems.offices.length; i++){
        this.outputArray[i] = {
          name: this.restItems.offices[i].name,
          person: this.restItems.officials[i].name,
          party: this.restItems.officials[i].party,
          photo: this.restItems.officials[i].photoUrl,
          url: this.restItems.officials[i].urls[0],
        };
      }
      console.log(this.restItems);
      console.log(this.outputArray);
    })
  }

  restItemsServiceGetRestItems() {
    return this.http.get<any[]>(this.restItemsUrl).pipe(map(data => data));
  }

}
