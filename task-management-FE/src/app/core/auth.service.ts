// src/app/core/auth.service.ts
import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private tokenSignal = signal<string | null>(localStorage.getItem('access_token'));

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {}

  login(username: string, password: string): Observable<string> {
    return this.http
      .post(`${environment.apiUrl}/auth/login`, { username, password }, { responseType: 'text' })
      .pipe(
        tap((token) => {
          localStorage.setItem('access_token', token);
          this.tokenSignal.set(token);
        }),
      );
  }

  register(username: string, email: string, password: string): Observable<string> {
    return this.http
      .post(
        `${environment.apiUrl}/auth/register`,
        { username, email, password },
        { responseType: 'text' },
      )
      .pipe(
        tap((token) => {
          localStorage.setItem('access_token', token);
          this.tokenSignal.set(token);
        }),
      );
  }

  logout(): void {
    localStorage.removeItem('access_token');
    this.tokenSignal.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return this.tokenSignal();
  }

  isLoggedIn(): boolean {
    return !!this.tokenSignal();
  }
}
