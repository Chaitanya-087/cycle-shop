import { Component } from '@angular/core';
import { AuthService as basicAuth } from '../services/auth.service';
import { Role } from '../models/role';
import { AuthService, AuthState } from '@auth0/auth0-angular';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  roles: typeof Role = Role;
  constructor( public auth: AuthService){}

  
}
