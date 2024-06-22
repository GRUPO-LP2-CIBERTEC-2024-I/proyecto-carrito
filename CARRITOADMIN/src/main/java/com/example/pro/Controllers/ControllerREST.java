package com.example.pro.Controllers;

import com.example.pro.model.Venta;
import com.example.pro.services.IVentaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ch.qos.logback.core.model.Model;

import java.util.List;


@RestController
public class ControllerREST {
	IVentaServices ventaServices;
	@Autowired
	public ControllerREST(IVentaServices ventaServices) {
		this.ventaServices=ventaServices;
	}

	@GetMapping("/ventas")
	public List<Venta> getAll() {
		return ventaServices.GetAllVentas();
	}

	@GetMapping("/venta/{id}")
	public Venta getAll(@PathVariable int id) {
		return ventaServices.FindVentaById(id);
	}

	@PostMapping("/venta")
	public Venta saveVenta(@RequestBody Venta entity) {
		return ventaServices.SaveVenta(entity);
	}

	@PutMapping("/venta/{id}")
	public ResponseEntity<Integer> updateCar(@PathVariable Integer id, @RequestBody Venta venta) {
		Integer updated = ventaServices.updateVenta(id, venta);
		if (updated == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/venta/{id}")
	public ResponseEntity<Integer> deleteVenta(@PathVariable Integer id) {
		Integer deleted = ventaServices.deleteVenta(id);
		if (deleted == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/pedidos")
	public String pedido(Model model) {
		return "PedidosProveedor";
	}
	
	@GetMapping("/productos")
	public String prod(Model model) {
		return "ProductosProveedor";
	}
	
}
