import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../../core/services/auth/auth';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CardModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  private fb = inject(FormBuilder);
  private authService = inject(Auth);
  private router = inject(Router);
  private messageService = inject(MessageService);

  loginForm: FormGroup = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: () => {
          // Si el login es exitoso, lo mandamos a la lista de productos
          this.router.navigate(['/productos']);
        },
        error: (err) => {
          // Si falla (credenciales incorrectas), mostramos una alerta PrimeNG
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'Usuario o contraseña incorrectos'
          });
        }
      });
    } else {
      // Si el formulario es inválido, marcamos los campos para que se pongan rojos
      this.loginForm.markAllAsTouched();
    }
  }
}