export interface Producto {
    id?: number; // Es opcional porque al crear un producto no tenemos ID aún
    nombre: string;
    descripcion: string;
    cantidad: number;
    precio: number;
}