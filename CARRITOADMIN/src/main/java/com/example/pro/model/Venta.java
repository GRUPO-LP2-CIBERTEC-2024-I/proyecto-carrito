package com.example.pro.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
	
	@Id
	private int IdVenta;
	@ManyToOne
	private List<Detalle> detalles;
	private Cliente Cli;
	

}
