package com.example.pro.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pro.model.Producto;
import com.example.pro.services.IProductoServices;

@RestController()
@RequestMapping("/Producto")
public class ProductoRestController {
	@Autowired
	IProductoServices _ProductoServices;
	public ProductoRestController(IProductoServices productoServices) {
		_ProductoServices = productoServices;
	}

	@GetMapping("/list")
	public List<Producto> getProducts() {
		return _ProductoServices.GetAllProductos();

	}
}

