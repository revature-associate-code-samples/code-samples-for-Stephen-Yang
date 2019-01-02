import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { map } from 'rxjs/operators'

@Component({
  selector: 'app-election-info',
  templateUrl: './election-info.component.html',
  styleUrls: ['./election-info.component.css']
})
export class ElectionInfoComponent implements OnInit {
  title = 'app';
  restItems: any;
  apiKey = 'key=AIzaSyDiJXpugiOVdpurOMtACPLWUxjn6JWSRn8'
  restItemsUrl = '';
  address: string = '';
  urlAddress: string = '';
  outputArray = [];
  displayElect = false;
  displayPoll = false;
  generalElections = [];
  pollingLocations = []; // Stephen
  normalizedInput = ''; // Kien

  constructor(private http: HttpClient) { }

  ngOnInit() {
    
  }

  reset() {
    this.outputArray = [];
    this.displayElect = false;
    this.displayPoll = false;
    this.restItems = null;
  }

  getItems(items) {
    
    //convert the address into a url compatable format
    this.reset();
    var re = / /gi;
    var str = this.address;
    this.urlAddress = str.replace(re, "%20");

    // this.restItemsUrl = `https://www.googleapis.com/civicinfo/v2/elections?electionid=2000&address=${this.address}
    // &includeOffices=true&levels=country&${this.apiKey}`;
    //this.restItemsUrl = `https://www.googleapis.com/civicinfo/v2/elections?${this.apiKey}&address=${this.urlAddress}`;
    this.restItemsUrl = `https://www.googleapis.com/civicinfo/v2/voterinfo?${this.apiKey}&address=${this.urlAddress}&electionId=2000`
    // log results
    console.log(this.address);
    console.log(this.urlAddress);
    console.log(this.restItemsUrl);
    
    //call the service
    this.getRestItems(items);
  
    //display the right table
    if (items == "elections") {
      this.displayElect = true;
      this.displayPoll = false;
    }
    if (items == "polling") {
      this.displayElect = false;
      this.displayPoll = true;
    }
    
  }

  // should be moved to a service
  getRestItems(items): void {
    this.restItemsServiceGetRestItems().subscribe(restItems => {
      this.restItems = restItems;

      console.log(this.restItems);
      if (items == "elections") {
        // populate general elections array - Stephen
        for(let i = 0; i < this.restItems.contests.length; i++){
          if(this.restItems.contests[i].type == 'General'){
            this.generalElections[i] = this.restItems.contests[i];
          }

      // Kien
      this.normalizedInput = this.restItems.normalizedInput.city + ' ' + this.restItems.normalizedInput.line1 + ' ' + this.restItems.normalizedInput.state + ' ' + this.restItems.normalizedInput.zip

        }
        console.log(this.restItems.contests[1]);
        console.log(this.generalElections[0].candidates);
      }
      if (items == "polling") { // won't return polling locations if ElectionId is used
        // populate polling locations array
        if (this.restItems.pollingLocations == undefined) {
          console.log("polling locations unavailable or NA");
        } else {
          for(let i = 0; i < this.restItems.pollingLocations.length; i++){
            this.pollingLocations[i] = this.restItems.pollingLocations[i];
          }
        }
        console.log(this.restItems.pollingLocations[1]);
        console.log(this.pollingLocations[0].name);
      }

    })
  }

  getGenerals(): boolean{
    
    return true;
  }

  restItemsServiceGetRestItems() {
    return this.http.get<any[]>(this.restItemsUrl).pipe(map(data => data));
  }
}
