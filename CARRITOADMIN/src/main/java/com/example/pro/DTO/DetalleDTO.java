package com.example.pro.DTO;

public class DetalleDTO {
	private int producto;
	private int cant;
	
	public DetalleDTO() {
		super();
	}
	public DetalleDTO(int producto, int cant) {
		super();
		this.producto = producto;
		this.cant = cant;
	}
	public int getProducto() {
		return producto;
	}
	public void setProducto(int producto) {
		this.producto = producto;
	}
	public int getCant() {
		return cant;
	}
	public void setCant(int cant) {
		this.cant = cant;
	}
}
