package com.example.pro.DTO;

import com.example.pro.model.Cliente;


public class VentaDTO  {
	private int Cli;
	private String FechaVenta;
	private double monto;
	
	public VentaDTO() {
		super();
	}
	public VentaDTO(int cli, String fechaVenta, double monto) {
		super();
		Cli = cli;
		FechaVenta = fechaVenta;
		this.monto = monto;
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
	public int getCli() {
		return Cli;
	}
	public void setCli(int cli) {
		Cli = cli;
	}
	
}
