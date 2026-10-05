package com.example.demo.service.impl;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.entity.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Lombok inyecta el repositorio automáticamente por el constructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductoDTO guardar(ProductoDTO productoDTO) {
        Producto producto = mapToEntity(productoDTO);
        Producto productoGuardado = productoRepository.save(producto);
        return mapToDTO(productoGuardado);
    }

    @Override
    @Transactional
    public ProductoDTO actualizar(Long id, ProductoDTO productoDTO) {
        // Buscamos si existe, si no, lanzamos un RuntimeException (luego haremos un
        // handler global para esto)
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el id: " + id));

        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setCantidad(productoDTO.getCantidad());
        productoExistente.setPrecio(productoDTO.getPrecio());

        Producto productoActualizado = productoRepository.save(productoExistente);
        return mapToDTO(productoActualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el id: " + id));
        productoRepository.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ProductoDTO mapToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setCantidad(producto.getCantidad());
        dto.setPrecio(producto.getPrecio());
        return dto;
    }

    private Producto mapToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCantidad(dto.getCantidad());
        producto.setPrecio(dto.getPrecio());
        return producto;
    }
}