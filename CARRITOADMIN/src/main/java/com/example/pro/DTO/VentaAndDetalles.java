package com.example.pro.DTO;

import java.util.List;
import com.example.pro.model.Detalle;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class VentaAndDetalles {
	 private VentaDTO ventaDTO;
	 private List<DetalleDTO> detallesDTO;
}
