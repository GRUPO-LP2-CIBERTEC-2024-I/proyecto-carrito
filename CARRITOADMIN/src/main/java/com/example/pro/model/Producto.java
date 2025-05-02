package com.example.pro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdProducto;
    @Column(unique = true)
    private String descripcion;
    private double PrecioUnidad;
    private int Stock;
    private String categoria;
    private String Imagen;
    private String Estado;

    public Producto(int idProducto, String descripcion, double precioUnidad, int stock, String imagen, String estado) {
	super();
	IdProducto = idProducto;
	this.descripcion = descripcion;
	PrecioUnidad = precioUnidad;
	Stock = stock;
	Imagen = imagen;
	Estado = estado;
    }

    public Producto() {
	super();
    }     
    
    public String getInfoWhatsapp() {
	return String.format(
	            "descripcion='%s'\n PrecioUnidad=%.2f \n" +
	            "Stock=%d \n categoria='%s' \n\n",
	             descripcion, PrecioUnidad, 
	            Stock, categoria);    }

}
