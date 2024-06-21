package com.example.pro.model;

import java.util.List;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;



@Entity
public class Venta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IdVenta;
	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Detalle> detalles;
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente Cli;
	private String FechaVenta;
	private double monto;
	
	public Venta(int idVenta, List<Detalle> detalles, Cliente cli, String fechaVenta, double monto) {
		super();
		IdVenta = idVenta;
		this.detalles = detalles;
		Cli = cli;
		FechaVenta = fechaVenta;
		this.monto = monto;
	}
	public Venta() {
		super();
	}
	public String getFechaVenta() {
		return FechaVenta;
	}
	public void setFechaVenta(String fechaVenta) {
		FechaVenta = fechaVenta;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public int getIdVenta() {
		return IdVenta;
	}
	public void setIdVenta(int idVenta) {
		IdVenta = idVenta;
	}
	public List<Detalle> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<Detalle> detalles) {
		this.detalles = detalles;
	}
	public Cliente getCli() {
		return Cli;
	}
	public void setCli(Cliente cli) {
		Cli = cli;
	}
}
