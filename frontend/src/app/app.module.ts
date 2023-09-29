import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { CyclesComponent } from './cycles/cycles.component';
import { FormsModule } from '@angular/forms';
import { RequestInterceptor } from './request.interceptor';
import { NavbarComponent } from './navbar/navbar.component';
import { BaseLayoutComponent } from './base-layout/base-layout.component';
import { SiteLayoutComponent } from './site-layout/site-layout.component';
import { AuthClickDirective } from './auth-click.directive';
import { CartComponent } from './cart/cart.component';
import { OrdersComponent } from './orders/orders.component';
import { UserOrdersComponent } from './user-orders/user-orders.component';
import { HasRoleDirective } from './has-role.directive';
import { AuthHttpInterceptor, AuthModule } from '@auth0/auth0-angular';
import { environment as env } from 'src/environments/environment';
import { LoginButtonComponent } from './login-button/login-button.component';
import { LogoutButtonComponent } from './logout-button/logout-button.component';
@NgModule({
  declarations: [AppComponent, RegisterComponent, LoginComponent, CyclesComponent, NavbarComponent, BaseLayoutComponent, SiteLayoutComponent, AuthClickDirective, CartComponent, OrdersComponent, UserOrdersComponent, HasRoleDirective, LoginButtonComponent, LogoutButtonComponent],
  imports: [BrowserModule, HttpClientModule, AppRoutingModule, FormsModule, AuthModule.forRoot({
    domain:"dev-h2l06ohkq1318vwy.us.auth0.com",
    clientId:"Lmeg8PIffqHVS1JDSkdb4BDOM5YySOI8",
    authorizationParams: {
      redirect_uri: window.location.origin,
      // audience: 'https://dev-h2l06ohkq1318vwy.us.auth0.com/api/v2/'
    },
  })
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthHttpInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }