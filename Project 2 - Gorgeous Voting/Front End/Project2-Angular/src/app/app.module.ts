import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { CivicInfoComponent } from './civic-info/civic-info.component';
import { ElectionInfoComponent } from './election-info/election-info.component';
import { LoginComponent } from './login/login.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { BookmarkComponent } from './bookmark/bookmark.component';
import { DisplayBookmarksComponent } from './display-bookmarks/display-bookmarks.component';
import { HelperServiceService } from './Services/helper-service.service';


@NgModule({
  declarations: [
    AppComponent,
    CivicInfoComponent,
    ElectionInfoComponent,
    LoginComponent,
    CreateUserComponent,
    BookmarkComponent,
    DisplayBookmarksComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [HelperServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { 
  
}
