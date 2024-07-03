package com.example.pro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pro.model.Venta;

@Repository
public interface IVentaRepository extends JpaRepository <Venta,Integer>{
}
