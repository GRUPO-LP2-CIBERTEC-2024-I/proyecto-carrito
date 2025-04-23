package com.example.pro.DTO;

public class VentaDTO {
    private int Cli;
    private String FechaVenta;
    private double monto;
    private String paymentId;
    private String metodo;

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
