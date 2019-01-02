import { Injectable } from '@angular/core';

@Injectable()
export class HelperServiceService {
  currentUser: any;
  loggedIn = false;
  constructor() { }

  makeSession(newUser: object){
    this.currentUser = newUser;
  }

  getSession(){
    return this.currentUser;
  }

  endSession(){
    this.currentUser=null;
  }

}
