package com.example.pro.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaAndDetalles {
	 private VentaDTO ventaDTO;
	 private List<DetalleDTO> detallesDTO;
}
