import { inject, Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

import { StateStorageService } from 'app/core/auth/state-storage.service';
import { ApplicationConfigService } from '../config/application-config.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private stateStorageService = inject(StateStorageService);
  private applicationConfigService = inject(ApplicationConfigService);

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const serverApiUrl = this.applicationConfigService.getEndpointFor('');
    if (!request.url || (request.url.startsWith('http') && !(serverApiUrl && request.url.startsWith(serverApiUrl)))) {
      return next.handle(request);
    }

    if (this.isAuthenticationRequest(request)) {
      return next.handle(request);
    }

    const token: string | null = this.stateStorageService.getAuthenticationToken();
    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
    return next.handle(request);
  }

  private isAuthenticationRequest(request: HttpRequest<any>): boolean {
    const requestPath = this.getPath(request.url);
    const authenticationPath = this.getPath(this.applicationConfigService.getEndpointFor('api/authenticate'));

    return requestPath === authenticationPath;
  }

  private getPath(url: string): string {
    const urlWithoutQuery = url.split('?')[0];

    try {
      return new URL(urlWithoutQuery, window.location.origin).pathname.replace(/^\/+/, '');
    } catch {
      return urlWithoutQuery.replace(/^\/+/, '');
    }
  }
}
