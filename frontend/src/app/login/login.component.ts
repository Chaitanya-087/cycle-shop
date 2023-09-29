import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { LoginForm } from '../models/login-form';
import { Location } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private authService: AuthService, private location: Location) { }

  login(loginForm: any): void {
    if (loginForm.invalid) return;
    this.authService.login(loginForm.value as LoginForm).subscribe();
  }

  goBack(): void {
    this.location.back();
  }
}
