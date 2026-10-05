import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AuthRequest, AuthResponse } from '../../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/api/auth';
  private tokenKey = 'mifact_jwt_token';

  login(credentials: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => this.setToken(response.token))
    );
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }
}