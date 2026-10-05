import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { Auth } from '../services/auth/auth';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(Auth);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return true; // déjalo pasar
  } else {
    router.navigate(['/login']); // redirige a que inicie sesión
    return false; // bloquea el paso
  }
};