import { HttpInterceptor, HttpRequest, HttpResponse, HttpHandler, HttpEvent } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

import { AlertService } from 'app/core/util/alert.service';
import { AlertMuteService } from 'app/core/util/alert-mute.service';

@Injectable()
export class NotificationInterceptor implements HttpInterceptor {
  private alertService = inject(AlertService);
  private alertMuteService = inject(AlertMuteService);

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          if (request.headers.has('X-Skip-Alert') || this.alertMuteService.isMuted()) {
            return;
          }

          let alert: string | null = null;
          for (const headerKey of event.headers.keys()) {
            if (headerKey.toLowerCase().endsWith('app-alert')) {
              alert = event.headers.get(headerKey);
            }
          }

          if (alert) {
            this.alertService.addAlert({
              type: 'success',
              message: alert,
            });
          }
        }
      }),
    );
  }
}
