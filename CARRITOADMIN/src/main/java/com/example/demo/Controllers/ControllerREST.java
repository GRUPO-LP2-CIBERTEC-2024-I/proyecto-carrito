package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ch.qos.logback.core.model.Model;


@Controller

public class ControllerREST {
	@GetMapping("/pedidos")
	public String pedido(Model model) {
		return "PedidosProveedor";
	}
}