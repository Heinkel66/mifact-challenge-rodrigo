import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductoService } from '../../../core/services/producto/producto';
import { Producto } from '../../../core/models/producto.model';
import { Subject, debounceTime, distinctUntilChanged } from 'rxjs';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToolbarModule } from 'primeng/toolbar';
import { ConfirmationService, MessageService, SharedModule } from 'primeng/api';
import { InputTextareaModule } from 'primeng/inputtextarea';

@Component({
  selector: 'app-producto-list',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, TableModule, ButtonModule, DialogModule,
    InputTextModule, InputNumberModule, ToastModule, ConfirmDialogModule, ToolbarModule, SharedModule, InputTextareaModule
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './producto-list.html',
  styleUrl: './producto-list.scss'
})
export class ProductoList implements OnInit {
  private productoService = inject(ProductoService);
  private fb = inject(FormBuilder);
  private messageService = inject(MessageService);
  private confirmationService = inject(ConfirmationService);
  private cdr = inject(ChangeDetectorRef);

  productos: Producto[] = [];
  productoDialog = false;
  productoForm!: FormGroup;
  editando = false;

  // RxJS Subject para la búsqueda en tiempo real
  searchSubject: Subject<string> = new Subject<string>();

  ngOnInit(): void {
    this.initForm();
    this.cargarProductos();

    this.searchSubject.pipe(
      debounceTime(400), // Espera 400ms después de que el usuario deja de escribir
      distinctUntilChanged() // Solo dispara si el texto realmente cambió
    ).subscribe(textoBusqueda => {
      if (textoBusqueda.trim() === '') {
        this.cargarProductos();
      } else {
        this.productoService.buscarPorNombre(textoBusqueda).subscribe(data => this.productos = data);
      }
    });
  }

  initForm(): void {
    this.productoForm = this.fb.group({
      id: [null],
      nombre: ['', Validators.required],
      descripcion: [''],
      cantidad: [0, [Validators.required, Validators.min(0)]],
      precio: [0, [Validators.required, Validators.min(0)]]
    });
  }

  cargarProductos(): void {
    this.productoService.listar().subscribe({
      next: (data) => {
        console.log('Productos:', data);
        this.productos = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error API:', err);
      }
    });
  }

  onSearch(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.searchSubject.next(value);
  }

  abrirNuevo(): void {
    this.editando = false;
    this.productoForm.reset({ cantidad: 0, precio: 0 });
    this.productoDialog = true;
  }

  editarProducto(producto: Producto): void {
    this.editando = true;
    this.productoForm.patchValue(producto);
    this.productoDialog = true;
  }

  ocultarDialog(): void {
    this.productoDialog = false;
  }

  guardarProducto(): void {
    if (this.productoForm.invalid) {
      this.productoForm.markAllAsTouched();
      return;
    }

    const producto: Producto = this.productoForm.value;

    if (this.editando) {
      this.productoService.actualizar(producto.id!, producto).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Éxito', detail: 'Producto actualizado' });
          this.cargarProductos();
          this.ocultarDialog();
        },
        error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo actualizar' })
      });
    } else {
      this.productoService.crear(producto).subscribe({
        next: () => {
          this.messageService.add({ severity: 'success', summary: 'Éxito', detail: 'Producto creado' });
          this.cargarProductos();
          this.ocultarDialog();
        },
        error: () => this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo crear' })
      });
    }
  }

  eliminarProducto(producto: Producto): void {
    this.confirmationService.confirm({
      message: `¿Estás seguro de que quieres eliminar <b>${producto.nombre}</b>?`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, Eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.productoService.eliminar(producto.id!).subscribe({
          next: () => {
            this.messageService.add({ severity: 'success', summary: 'Éxito', detail: 'Producto eliminado' });
            this.cargarProductos();
          }
        });
      }
    });
  }
}