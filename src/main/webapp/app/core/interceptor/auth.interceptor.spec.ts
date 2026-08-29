import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { StateStorageService } from 'app/core/auth/state-storage.service';
import { AuthInterceptor } from './auth.interceptor';

describe('AuthInterceptor', () => {
  let httpClient: HttpClient;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: HTTP_INTERCEPTORS,
          useClass: AuthInterceptor,
          multi: true,
        },
      ],
    });

    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    TestBed.inject(StateStorageService).clearAuthenticationToken();
    httpMock.verify();
  });

  it('should add bearer token to API requests', () => {
    TestBed.inject(StateStorageService).storeAuthenticationToken('stored-token', false);

    httpClient.get('api/account').subscribe();

    const req = httpMock.expectOne('api/account');
    expect(req.request.headers.get('Authorization')).toBe('Bearer stored-token');
    req.flush({});
  });

  it('should not add bearer token to authenticate requests', () => {
    TestBed.inject(StateStorageService).storeAuthenticationToken('stale-token', false);

    httpClient.post('api/authenticate', { username: 'admin', password: '1952', rememberMe: false }).subscribe();

    const req = httpMock.expectOne('api/authenticate');
    expect(req.request.headers.has('Authorization')).toBe(false);
    req.flush({ id_token: 'new-token' });
  });
});
