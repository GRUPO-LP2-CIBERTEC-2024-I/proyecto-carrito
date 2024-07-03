package com.example.pro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pro.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository <Producto,Integer> {
}
