package com.example.pro.services.Impl;
import com.example.pro.model.Venta;
import com.example.pro.Repository.IVentaRepository;
import com.example.pro.services.IVentaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VentaServices implements IVentaServices {
    IVentaRepository _ventaRepository;

    @Autowired
    public VentaServices(IVentaRepository ventaRepository) {
        _ventaRepository = ventaRepository;
    }

    @Override
    public List<Venta> GetAllVentas() {
        return _ventaRepository.findAll();
    }

    @Override
    public Venta SaveVenta(Venta entity) {
        Venta ventaSaved = _ventaRepository.save(entity);
        return ventaSaved;
    }

    @Override
    public Venta FindVentaById(int id) {
        Optional<Venta> rowInDB = _ventaRepository.findById(id);
        if (rowInDB.isPresent())
            return rowInDB.get();
        else
            return new Venta();
    }

    @Override
    public Integer updateVenta(Integer id, Venta venta) {
        Optional<Venta> existingVenta = _ventaRepository.findById(id);
        if (existingVenta.isPresent()) {
            Venta VentaToUpdate = existingVenta.get();
            VentaToUpdate.setDetalles(venta.getDetalles());
            VentaToUpdate.setCli(venta.getCli());
            _ventaRepository.save(VentaToUpdate);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Integer deleteVenta(Integer id) {
        Optional<Venta> optionalVenta = _ventaRepository.findById(id);
        if (optionalVenta.isPresent()) {
            _ventaRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }


}
