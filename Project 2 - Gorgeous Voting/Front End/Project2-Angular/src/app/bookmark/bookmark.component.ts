import { Component, OnInit, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Component({
  selector: 'app-bookmark',
  templateUrl: './bookmark.component.html',
  styleUrls: ['./bookmark.component.css']
})
export class BookmarkComponent implements OnInit {

  baseUrl = `http://ec2-54-210-42-186.compute-1.amazonaws.com:8080/Pipeline/bookmark/add`;
  bookUrl = '';

  @Input() url: string;
  @Input() name: string;
  constructor(private http: HttpClient) { }

  ngOnInit() {
  }



  bookmarkUrl(){
    let re = / /gi;
    let str = this.name;
    let urlName = str.replace(re, "%20");
    this.bookUrl = `${this.baseUrl}?name=${urlName}&url=${this.url}`
    let body = {
      name: this.name,
      url: this.url
    }
    console.log(this.bookUrl);
    this.http.post<any[]>(this.bookUrl, body);
    
  }
  
}
