package com.example.pro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
	@ManyToOne
    @JoinColumn(nullable = false)
	private Detalle detalle;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IdProducto; 
    private String Descripcion;
    private double PrecioUnidad; 
    private int Stock;
    private String Imagen;
    private String Estado;
    
	public int getIdProducto() {
		return IdProducto;
	}

	public void setIdProducto(int idProducto) {
		IdProducto = idProducto;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public double getPrecioUnidad() {
		return PrecioUnidad;
	}

	public void setPrecioUnidad(double precioUnidad) {
		PrecioUnidad = precioUnidad;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}

	public String getImagen() {
		return Imagen;
	}

	public void setImagen(String imagen) {
		Imagen = imagen;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}
    
}
