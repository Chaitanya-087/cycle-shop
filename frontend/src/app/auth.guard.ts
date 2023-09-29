import { Router, UrlTree } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthService } from '@auth0/auth0-angular';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {
  constructor(private auth: AuthService, private router: Router) { }
  canActivate(): boolean | Observable<boolean | UrlTree> {
    this.auth.isAuthenticated$.subscribe(res => {
      if (!res) {
        this.auth.loginWithPopup();
      }
    });
    return this.auth.isAuthenticated$;
  }
}
