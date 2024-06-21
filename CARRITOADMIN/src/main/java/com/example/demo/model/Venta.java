package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Venta {
	
	@Id
	private int IdVenta;
	@ManyToOne
	private List<Detalle> detalles;
	private Cliente Cli;
	

}
