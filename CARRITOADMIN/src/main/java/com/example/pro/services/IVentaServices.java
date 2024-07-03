package com.example.pro.services;

import java.util.List;

import com.example.pro.DTO.VentaAndDetalles;
import com.example.pro.model.Venta;

public interface IVentaServices {
    List<Venta> GetAllVentas();
    Venta SaveVenta(Venta entity);
    Venta FindVentaById(int id);
    Integer deleteVenta(Integer id);
    Venta SaveVentaAndDetalles(VentaAndDetalles entity);
}
