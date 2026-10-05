package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ProductoDTO;

public interface ProductoService {
    List<ProductoDTO> listarTodos();

    ProductoDTO guardar(ProductoDTO productoDTO);

    ProductoDTO actualizar(Long id, ProductoDTO productoDTO);

    void eliminar(Long id);

    List<ProductoDTO> buscarPorNombre(String nombre);
}
