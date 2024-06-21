package com.example.pro.services;

import com.example.pro.model.Venta;

import java.util.List;

public interface IVentaServices {
    List<Venta> GetAllVentas();
    Venta SaveVenta(Venta entity);
    Integer updateVenta(Integer id, Venta venta);
    Integer deleteVenta(Integer id);
}
