import { Routes } from '@angular/router';
import { Login } from './features/auth/login/login';
import { authGuard } from './core/guards/auth-guard';
import { ProductoList } from './features/productos/producto-list/producto-list';

export const routes: Routes = [
    { path: 'login', component: Login },
    {
        path: 'productos',
        component: ProductoList,
        canActivate: [authGuard]
    },
    { path: '', redirectTo: 'productos', pathMatch: 'full' },
    { path: '**', redirectTo: 'login' }
];