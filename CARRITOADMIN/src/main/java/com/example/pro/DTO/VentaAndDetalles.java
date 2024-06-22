package com.example.pro.DTO;

import java.util.List;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class VentaAndDetalles {
	
	 private VentaDTO ventaDTO;
	 private List<DetalleDTO> detallesDTO;
	 
	 
	public VentaDTO getVentaDTO() {
		return ventaDTO;
	}
	public void setVentaDTO(VentaDTO ventaDTO) {
		this.ventaDTO = ventaDTO;
	}
	public List<DetalleDTO> getDetallesDTO() {
		return detallesDTO;
	}
	public void setDetallesDTO(List<DetalleDTO> detallesDTO) {
		this.detallesDTO = detallesDTO;
	} 
}
