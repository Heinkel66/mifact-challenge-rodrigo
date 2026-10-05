package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data // Lombok: genera código de getters, setters, toString, etc automáticamente
@NoArgsConstructor // JPA requiere un constructor vacío
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    private Integer cantidad;

    // Usamos BigDecimal para montos de dinero por precisión, en lugar de Double
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}