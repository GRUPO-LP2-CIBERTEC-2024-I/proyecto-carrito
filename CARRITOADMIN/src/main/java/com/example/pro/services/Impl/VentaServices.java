package com.example.pro.services.Impl;
import com.example.pro.DTO.DetalleDTO;
import com.example.pro.DTO.VentaAndDetalles;
import com.example.pro.model.Detalle;
import com.example.pro.model.Venta;
import com.example.pro.Repository.IDetalleRepository;
import com.example.pro.Repository.IVentaRepository;
import com.example.pro.services.IVentaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VentaServices implements IVentaServices {
	@Autowired
    IVentaRepository _ventaRepository;
	IDetalleRepository _detalleRepository;
    
    public VentaServices(IVentaRepository ventaRepository, IDetalleRepository detalleRepository) {
        _ventaRepository = ventaRepository;
        _detalleRepository = detalleRepository;
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
        
        ventaToSave.setCli(entity.getVentaDTO().getCli());
        ventaToSave.setFechaVenta(entity.getVentaDTO().getFechaVenta());
        ventaToSave.setMonto(entity.getVentaDTO().getMonto());
        
         Venta VentaSaved = _ventaRepository.save(ventaToSave);
        
        entity.getDetallesDTO().forEach(DetalleDTO ->{
        	Detalle detToSave = new Detalle();
        	detToSave.setProducto(DetalleDTO.getProducto());
        	detToSave.setCant(DetalleDTO.getCant());
        	detToSave.setVenta(VentaSaved);
        	_detalleRepository.save(detToSave);
        });
        
        return VentaSaved;
    }


}
