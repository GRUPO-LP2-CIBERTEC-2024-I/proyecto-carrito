package com.example.pro.services;

import java.util.List;

import com.example.pro.model.Producto;

public interface IProductoServices {
    List<Producto> GetAllProductos();
    Producto FindProductoByNombre(String nombre);
    Integer updateProducto(Integer id, Producto producto);

}
