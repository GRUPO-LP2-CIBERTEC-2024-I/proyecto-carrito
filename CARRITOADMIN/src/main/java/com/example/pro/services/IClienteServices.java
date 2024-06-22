package com.example.pro.services;

import com.example.pro.model.Cliente;

import java.util.List;

public interface IClienteServices {
    List<Cliente> GetAllClientes();
    Cliente SaveCliente(Cliente entity);
    Integer updateCliente(Integer id, Cliente cliente);
    Cliente FindClienteById(int id);
}
