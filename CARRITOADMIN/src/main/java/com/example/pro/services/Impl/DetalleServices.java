package com.example.pro.services.Impl;

import com.example.pro.Repository.IDetalleRepository;
import com.example.pro.model.Detalle;
import com.example.pro.model.Venta;
import com.example.pro.services.IDetalleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleServices implements IDetalleServices {

    IDetalleRepository _detalleRepository;
    @Autowired
    public DetalleServices(IDetalleRepository detalleRepository) {
        _detalleRepository = detalleRepository;
    }
    @Override
    public List<Detalle> GetAllDetalles() {
        return _detalleRepository.findAll();
    }

    @Override
    public Detalle SaveDetalle(Detalle entity) {
        Detalle DetalleSaved = _detalleRepository.save(entity);
        return DetalleSaved;
    }

    @Override
    public Detalle FindDetalleById(int id) {
        Optional<Detalle> rowInDB = _detalleRepository.findById(id);
        if (rowInDB.isPresent())
            return rowInDB.get();
        else
            return new Detalle();
    }
}

