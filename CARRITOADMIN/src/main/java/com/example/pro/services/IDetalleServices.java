package com.example.pro.services;

import com.example.pro.model.Detalle;


import java.util.List;

public interface IDetalleServices {
    List<Detalle> GetAllDetalles();
    Detalle SaveDetalle(Detalle entity);
    Detalle FindDetalleById(int id);
}
