package com.example.pro.services.Impl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pro.DTO.VentaAndDetalles;
import com.example.pro.Repository.IClienteRepository;
import com.example.pro.Repository.IDetalleRepository;
import com.example.pro.Repository.IProductoRepository;
import com.example.pro.Repository.IVentaRepository;
import com.example.pro.model.Detalle;
import com.example.pro.model.Producto;
import com.example.pro.model.Venta;
import com.example.pro.services.IVentaServices;
@Service
public class VentaServices implements IVentaServices {
	IProductoRepository _productoRepository;
    IVentaRepository _ventaRepository;
	IDetalleRepository _detalleRepository;
	IClienteRepository _clienteRepository;
    ProductoServices productoServices;
    @Autowired
    public VentaServices(IProductoRepository productoRepository,IClienteRepository clienteRepository, IVentaRepository ventaRepository, IDetalleRepository detalleRepository, ProductoServices productoServices) {
    	_productoRepository = productoRepository;
    	_clienteRepository = clienteRepository;
        _ventaRepository = ventaRepository;
        _detalleRepository = detalleRepository;
        this.productoServices = productoServices;
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
        if (rowInDB.isPresent()) {
			return rowInDB.get();
		} else {
			return new Venta();
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

    @Override
    public Venta SaveVentaAndDetalles(VentaAndDetalles entity) {

        Venta ventaToSave = new Venta();

        ventaToSave.setCli(_clienteRepository.findById(entity.getVentaDTO().getCli()).get());
        ventaToSave.setFechaVenta(entity.getVentaDTO().getFechaVenta());
        ventaToSave.setMonto(entity.getVentaDTO().getMonto());

         Venta VentaSaved = _ventaRepository.save(ventaToSave);

        entity.getDetallesDTO().forEach(DetalleDTO ->{
        	Detalle detToSave = new Detalle();
        	detToSave.setProducto(_productoRepository.findById(DetalleDTO.getProducto()).get());
        	detToSave.setCant(DetalleDTO.getCant());
        	detToSave.setVenta(VentaSaved);
        	_detalleRepository.save(detToSave);
            if (detToSave.getProducto() != null) {
                Producto producto = detToSave.getProducto();
                producto.setStock(producto.getStock() - detToSave.getCant());
                productoServices.updateProducto(producto.getIdProducto(), producto);
            }
        });

        return VentaSaved;
    }
}
