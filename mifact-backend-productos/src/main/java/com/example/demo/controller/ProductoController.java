package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.service.ProductoService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 Aquí van los endpoints (CRUD) que consumirá el front (angular)
 */
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    // Se utiliza @Valid para
    // que se disparen las
    // validaciones establecidas en el dto
    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO productoDTO) {
        return new ResponseEntity<>(productoService.guardar(productoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.actualizar(id, productoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
