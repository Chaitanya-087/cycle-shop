import { Injectable, inject } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { EMPTY, Observable } from 'rxjs';
import { AuthService } from './services/auth.service';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const publicRequestUrls = [
      '/api/cycles/list',
      '/api/auth/*',
    ]

    const isPublicRequest = publicRequestUrls.some(url => request.url.match(url));

    if (isPublicRequest) {
      return next.handle(request);
    }

    if (!this.authService.isLoggedIn) {
      return EMPTY;
    }
    const jwtRequest = request.clone({
      setHeaders: {
        Authorization: 'bearer ' + this.authService.token
      }
    });

    return next.handle(jwtRequest);
  }
}
