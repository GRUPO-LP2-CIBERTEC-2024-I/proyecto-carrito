package com.example.pro.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pro.model.Cliente;
import com.example.pro.services.IClienteServices;


@RestController
@RequestMapping("/Cliente")
public class ClienteRestController {
	@Autowired
	IClienteServices clienteServices;
	public ClienteRestController(IClienteServices clienteservices) {
		clienteServices = clienteservices;
	}
	@GetMapping("/list")
	public List<Cliente> getListCli() {
		return clienteServices.GetAllClientes();
	}

	@GetMapping("/{id}")
	public Cliente getCli(@PathVariable int id) {
		return clienteServices.FindClienteById(id);
	}
	@PostMapping("/add")
	public Cliente postAddCli(@RequestBody Cliente entity) {
		return clienteServices.SaveCliente(entity);
	}
	@PutMapping("/{id}")
	public int putMethodName(@PathVariable int id, @RequestBody Cliente entity) {
		return clienteServices.updateCliente(id, entity);
	}
	@PostMapping("/verificar")
	public Cliente verificar(@RequestBody Map<String, String> request) {
		String correo = request.get("correo");
		String pass = request.get("pass");
		return clienteServices.VerificarCliente(correo, pass);
	}

}
