package com.example.pro.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Detalle {
	private int IdDetalle;
	private Producto producto;
	private int cant;
	
}
