package com.example.pro.Controllers;

import com.example.pro.model.Venta;
import com.example.pro.services.IVentaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VentaController {
	IVentaServices ventaServices;

	@Autowired
	public VentaController(IVentaServices ventaServices) {
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

	@DeleteMapping("/venta/{id}")
	public ResponseEntity<Integer> deleteVenta(@PathVariable Integer id) {
		Integer deleted = ventaServices.deleteVenta(id);
		if (deleted == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
