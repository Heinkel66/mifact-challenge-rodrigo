package com.example.demo.repository;

import com.example.demo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar productos por nombre en tiempo real
    // Spring Data JPA creará automáticamente la consulta SQL con LIKE e IgnoreCase
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}