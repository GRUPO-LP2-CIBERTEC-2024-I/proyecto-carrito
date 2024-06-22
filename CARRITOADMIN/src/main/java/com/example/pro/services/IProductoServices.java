package com.example.pro.services;

import com.example.pro.model.Producto;

import java.util.List;

public interface IProductoServices {
    List<Producto> GetAllProductos();
    Producto FindProductoByNombre(String nombre);
    Integer updateProducto(Integer id, Producto producto);

}
