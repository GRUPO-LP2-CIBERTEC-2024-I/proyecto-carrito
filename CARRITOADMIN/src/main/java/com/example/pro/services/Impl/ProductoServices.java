package com.example.pro.services.Impl;
import com.example.pro.Repository.IProductoRepository;

import com.example.pro.model.Producto;
import com.example.pro.model.Venta;
import com.example.pro.services.IProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServices implements IProductoServices {
    IProductoRepository _productoRepository;

    @Autowired
    public ProductoServices(IProductoRepository productoRepository) {
        _productoRepository = productoRepository;
    }

    @Override
    public List<Producto> GetAllProductos() {
        return _productoRepository.findAll();
    }

    @Override
    public Producto FindProductoByNombre(String nombre) {
        return null;
    }

    @Override
    public Integer updateProducto(Integer id, Producto producto) {
        Optional<Producto> existingProducto = _productoRepository.findById(id);
        if (existingProducto.isPresent()) {
            Producto ProductoToUpdate = existingProducto.get();
            ProductoToUpdate.setStock(producto.getStock());
            _productoRepository.save(ProductoToUpdate);
            return 1;
        } else {
            return 0;
        }
    }
}
