package com.example.pro.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pro.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository <Producto,Integer> {

    Page<Producto> findByDescripcionContainingIgnoreCase(String producto, Pageable pageable); 

    Page<Producto> findByDescripcionContainingIgnoreCaseAndCategoriaContainingIgnoreCase(String producto,
	    String categoria, Pageable pageable);

    Page<Producto> findByCategoriaContainingIgnoreCase(String categoria, Pageable pageable);
}
