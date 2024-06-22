package com.example.pro.DTO;

import java.util.List;

import com.example.pro.model.Cliente;
import com.example.pro.model.Detalle;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class VentaDTO  {
	private int IdVenta;
	private List<Detalle> detalles;
	private Cliente Cli;
	private String FechaVenta;
	private double monto;
	
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
