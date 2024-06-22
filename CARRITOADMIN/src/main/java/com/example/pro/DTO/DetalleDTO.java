package com.example.pro.DTO;

import com.example.pro.model.Producto;
import com.example.pro.model.Venta;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DetalleDTO {
	private int IdDetalle;
	private Venta venta;
	private Producto producto;
	private int cant;
	
	public Venta getVenta() {
		return venta;
	}
	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	public int getIdDetalle() {
		return IdDetalle;
	}
	public void setIdDetalle(int idDetalle) {
		IdDetalle = idDetalle;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getCant() {
		return cant;
	}
	public void setCant(int cant) {
		this.cant = cant;
	}
	
	
}
