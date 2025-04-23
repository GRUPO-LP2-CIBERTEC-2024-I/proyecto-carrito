package com.example.pro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IdVenta;
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cli;
	private String FechaVenta;
	private double monto;
	private String metodo;
	private  String paymentId;


	public Venta(int idVenta, Cliente cli, String fechaVenta, double monto) {
		super();
		IdVenta = idVenta;
		this.cli = cli;
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
	public Cliente getCli() {
		return cli;
	}
	public void setCli(Cliente cli) {
		this.cli = cli;
	}
	public String getPaymentId() {
	    return paymentId;
	}
	public void setPaymentId(String paymentId) {
	    this.paymentId = paymentId;
	}
	public String getMetodo() {
	    return metodo;
	}
	public void setMetodo(String metodo) {
	    this.metodo = metodo;
	}
}
