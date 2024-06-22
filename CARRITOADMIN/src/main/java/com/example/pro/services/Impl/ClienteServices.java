package com.example.pro.services.Impl;

import com.example.pro.Repository.IClienteRepository;
import com.example.pro.Repository.IDetalleRepository;
import com.example.pro.Repository.IVentaRepository;
import com.example.pro.model.Cliente;
import com.example.pro.model.Venta;
import com.example.pro.services.IClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices implements IClienteServices {
    IClienteRepository _clienteRepository;

    @Autowired
    public ClienteServices(IClienteRepository clienteRepository) {
        _clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> GetAllClientes() {
        return _clienteRepository.findAll();
    }

    @Override
    public Cliente SaveCliente(Cliente entity) {
        Cliente clienteSaved = _clienteRepository.save(entity);
        return clienteSaved;    }

    @Override
    public Integer updateCliente(Integer id, Cliente cliente) {
        Optional<Cliente> existingCliente = _clienteRepository.findById(id);
        if (existingCliente.isPresent()) {
            Cliente ClienteToUpdate = existingCliente.get();
            ClienteToUpdate.setApellidos(cliente.getApellidos());
            ClienteToUpdate.setNombres(cliente.getNombres());
            ClienteToUpdate.setDireccion(cliente.getDireccion());
            ClienteToUpdate.setFechaNacimiento(cliente.getFechaNacimiento());
            ClienteToUpdate.setNombres(cliente.getNombres());
            ClienteToUpdate.setSexo(cliente.getSexo());
            ClienteToUpdate.setCorreo(cliente.getCorreo());
            ClienteToUpdate.setPassword(cliente.getPassword());
            ClienteToUpdate.setEstado(cliente.getEstado());
            _clienteRepository.save(ClienteToUpdate);
            return 1;
        } else {
            return 0;
        }
    }


    @Override
    public Cliente FindClienteById(int id) {
        Optional<Cliente> rowInDB = _clienteRepository.findById(id);
        if (rowInDB.isPresent())
            return rowInDB.get();
        else
            return new Cliente();    }

    @Override
    public Integer deleteCliente(Integer id) {
        Optional<Cliente> optionalCliente = _clienteRepository.findById(id);
        if (optionalCliente.isPresent()) {
            _clienteRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }
}
