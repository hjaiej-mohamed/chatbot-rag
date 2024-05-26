import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserStorageService } from '../services/user-storage.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = UserStorageService.getToken();
    if (token) {
        request = request.clone({
            setHeaders: {
                'Authorization': `Bearer ${token}`
            }
        });
        return next.handle(request);
    }
    return next.handle(request);
  }
}
