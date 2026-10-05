package com.example.demo.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductoDTO {

    public Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa.")
    private Integer cantidad;

    @NotNull(message = "el precio es obligatorio.")
    @Min(value = 0, message = "el precio no puede ser negativo")
    private BigDecimal precio;
}
