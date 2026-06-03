import { inject, Injectable, Signal, signal } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable, ReplaySubject, of } from 'rxjs';
import { shareReplay, tap, catchError } from 'rxjs/operators';

import { StateStorageService } from 'app/core/auth/state-storage.service';
import { Account } from 'app/core/auth/account.model';
import { ApplicationConfigService } from '../config/application-config.service';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private userIdentity = signal<Account | null>(null);
  private authenticationState = new ReplaySubject<Account | null>(1);
  private accountCache$?: Observable<Account> | null;

  private http = inject(HttpClient);
  private stateStorageService = inject(StateStorageService);
  private router = inject(Router);
  private applicationConfigService = inject(ApplicationConfigService);

  save(account: Account): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/account'), account);
  }

  authenticate(identity: Account | null): void {
    this.userIdentity.set(identity);
    this.authenticationState.next(this.userIdentity());

    // Store the id and login in localStorage when the user logs in
    if (identity) {
      //localStorage.setItem('userId', identity.id.toString());
      localStorage.setItem('userLogin', identity.login);
      const storedLogin = localStorage.getItem('userLogin');

      console.log('User logged in:');
      console.log('Loginddddddd:', storedLogin);
      this.http.get<{ id: string }>('/api/account').subscribe(userData => {
        console.log('API response:', userData); // Log the full response
        if (userData && userData.id) {
          localStorage.setItem('userId', userData.id); // Store the user id
          const storedUserId = localStorage.getItem('userId');
          console.log('User logged in:');
          console.log('UserIdd:', storedUserId);
        } else {
          console.error('User ID is undefined in the response');
        }
      });
    } else {
      localStorage.removeItem('userId');
      localStorage.removeItem('userLogin');
    }

    if (!identity) {
      this.accountCache$ = null;
    }
  }

  trackCurrentAccount(): Signal<Account | null> {
    return this.userIdentity.asReadonly();
  }

  hasAnyAuthority(authorities: string[] | string): boolean {
    const userIdentity = this.userIdentity();
    if (!userIdentity) {
      return false;
    }
    if (!Array.isArray(authorities)) {
      authorities = [authorities];
    }
    return userIdentity.authorities.some((authority: string) => authorities.includes(authority));
  }

  identity(force?: boolean): Observable<Account | null> {
    if (!this.accountCache$ || force) {
      this.accountCache$ = this.fetch().pipe(
        tap((account: Account) => {
          this.authenticate(account);
          this.navigateToStoredUrl();
        }),
        shareReplay(),
      );
    }
    return this.accountCache$.pipe(catchError(() => of(null)));
  }

  isAuthenticated(): boolean {
    return this.userIdentity() !== null;
  }

  getAuthenticationState(): Observable<Account | null> {
    return this.authenticationState.asObservable();
  }

  private fetch(): Observable<Account> {
    return this.http.get<Account>(this.applicationConfigService.getEndpointFor('api/account'));
  }

  private navigateToStoredUrl(): void {
    const previousUrl = this.stateStorageService.getUrl();
    if (previousUrl) {
      this.stateStorageService.clearUrl();
      this.router.navigateByUrl(previousUrl);
    }
  }
}
