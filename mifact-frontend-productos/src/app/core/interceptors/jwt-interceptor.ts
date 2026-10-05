import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Auth } from '../services/auth/auth';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(Auth);
  const token = authService.getToken();

  // Si tenemos un token, clonamos la petición y le añadimos el header Authorization
  if (token) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(clonedRequest);
  }

  // Si no hay token la dejamos pasar tal cual
  return next(req);
};