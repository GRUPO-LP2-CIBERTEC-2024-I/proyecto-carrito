package com.example.pro.services.Impl;
import com.example.pro.DTO.VentaAndDetalles;
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
    public Integer deleteVenta(Integer id) {
        Optional<Venta> optionalVenta = _ventaRepository.findById(id);
        if (optionalVenta.isPresent()) {
            _ventaRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Venta SaveVentaAndDetalles(VentaAndDetalles entity) {
        Venta ventaToSave = new Venta();
        ventaToSave.setIdVenta(entity.getVentaDTO().getIdVenta());
        ventaToSave.setCli(entity.getVentaDTO().getCli());
        ventaToSave.setDetalles(entity.getVentaDTO().getDetalles());
        ventaToSave.setFechaVenta(entity.getVentaDTO().getFechaVenta());
        ventaToSave.setMonto(entity.getVentaDTO().getMonto());
        Venta ventaSaved = _ventaRepository.save(ventaToSave);


    }


}
