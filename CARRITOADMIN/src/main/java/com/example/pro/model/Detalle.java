package com.example.pro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Detalle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IdDetalle;
	@ManyToOne
	@JoinColumn(name = "venta_id", nullable = false)
	private Venta venta;
	@ManyToOne
	@JoinColumn(name = "producto_id", nullable = false)
	private Producto producto;
	private int cant;

	public Detalle() {
		super();
	}
	public Detalle(int idDetalle, Venta venta, Producto producto, int cant) {
		super();
		IdDetalle = idDetalle;
		this.venta = venta;
		this.producto = producto;
		this.cant = cant;
	}
	public int getIdDetalle() {
		return IdDetalle;
	}
	public void setIdDetalle(int idDetalle) {
		IdDetalle = idDetalle;
	}
	public Venta getVenta() {
		return venta;
	}
	public void setVenta(Venta venta) {
		this.venta = venta;
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
