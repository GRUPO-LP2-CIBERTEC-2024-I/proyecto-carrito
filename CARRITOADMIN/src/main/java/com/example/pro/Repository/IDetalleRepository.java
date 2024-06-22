package com.example.pro.Repository;

import com.example.pro.model.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleRepository extends JpaRepository<Detalle,Integer> {
}
