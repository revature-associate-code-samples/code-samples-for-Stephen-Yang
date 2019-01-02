import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { map } from 'rxjs/operators'

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {
  
  // to move into services
  displayForm = true;
  displayUser = false;
  displayFail = false;

  firstname = '';
  lastname = '';
  username = '';
  password = '';
  email = '';
  address = '';


  result = 0;
  //createUserUrl = `http://ec2-54-210-42-186.compute-1.amazonaws.com:8080/Pipeline/user/create`;
  createUserUrl = `http://localhost:8080/Proj2Vote/user/create` // Stephen's localhost
  user = {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };

  
  constructor(private http: HttpClient) { }

  ngOnInit() {
    
  }

  
  submitUser(){
    this.user = {
      user_id: -1,
      username: this.username,
      pswd: this.password,
      fname: this.firstname,
      lname: this.lastname,
      email: this.email,
      address: {adr_id: 0,
                city: '',
                state: '',
                str_adr: this.address,
                zip: 12345
              },
      perm: 0
    }

    // address showing up as null in Spring
    // workaround:


    console.log(JSON.stringify(this.user));
    
    this.submitUserService().subscribe(result => {
      this.result = result;
      
      console.log(this.result);
      if (this.result != 0) {
        console.log("Account successfully created!");
        // save result id in a service or just leave that to the login
        this.displayForm = false;
        this.displayUser = true;
      } else {
        console.log("failed to create user");
        this.displayFail = true;
      }
    })

    

  }
  submitUserService() {
    return this.http.post<number>(this.createUserUrl, JSON.stringify(this.user), this.httpOptions).pipe(map(data => data))
  }
}
