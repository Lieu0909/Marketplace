import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { HeaderComponent } from './layouts/header/header.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { HomeComponent }  from './home/home.component';
import { LoginComponent }  from './login/login.component';
import { SignUpComponent }  from './signup/signup.component';
import { ProductManagementComponent }  from './admin/product_management/product_management.component';


import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    SignUpComponent,
    ProductManagementComponent
  ],
  imports: [
    BrowserModule,
    RouterModule, // Add RouterModule here
    AppRoutingModule,
    MatMenuModule,       // For mat-menu and matMenuTriggerFor
    MatIconModule,       // For mat-icon
    MatButtonModule,
    BrowserAnimationsModule// For mat-icon-button
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}

