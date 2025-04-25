package com.example.pro.services.Impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pro.DTO.PagoDTO;
import com.example.pro.DTO.PedidoDTO;
import com.example.pro.DTO.VentaAndDetalles;
import com.example.pro.DTO.VentaDTO;
import com.example.pro.Repository.IPagoRepository;
import com.example.pro.model.Cliente;
import com.example.pro.model.EstadoPedido;
import com.example.pro.model.Pago;
import com.example.pro.model.Pedido;
import com.example.pro.model.Venta;
import com.example.pro.services.IClienteServices;
import com.example.pro.services.IPaymentService;
import com.example.pro.services.IVentaServices;
import com.google.gson.Gson;
import com.mercadopago.resources.Payment;

@Service
public class PaymentService implements IPaymentService {

    private Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private IVentaServices iVentaServices;
    @Autowired
    private IPagoRepository _IPagoRepository;
    
        
    @Autowired
    private Gson gson;
    @Override
    public void generarVentaConMercadoPago(Payment payment) {
	log.info("extraendo datos de payment de Mercado pago encontrado por id, del pagador: "
		+ payment.getPayer().getIdentification().getNumber());
	Long monto = payment.getTransactionAmount().longValue();
	String metodo = payment.getPaymentMethodId();
	PagoDTO pago = new PagoDTO();
	pago.setMetodo(metodo);
	pago.setEstado(payment.getStatus().name());
	pago.setPaymentId(payment.getId().toString());
	String metadata = gson.toJson(payment.getMetadata());
	VentaAndDetalles venta = gson.fromJson(metadata, VentaAndDetalles.class);
	VentaDTO ventadto = venta.getVentaDTO();
	ventadto.setMonto(monto);
	ventadto.setFechaVenta(LocalDateTime.now().toString());
	venta.setVentaDTO(ventadto);
	venta.setPagoDTO(pago);
	log.info("generando la venta con el pago: "+payment.getId());
	iVentaServices.SaveVentaAndDetalles(venta);
	System.out.println("✅ Venta registrada: " + venta);

    }
    @Override
    public void cancelarVenta(Payment payment) {
	Pago pago = _IPagoRepository.findById(payment.getId()).orElseThrow(); 
	Venta venta = iVentaServices.findByPago(pago);
	Pedido pedido = venta.getPedido();
	pago.setEstado(payment.getStatus().name().toLowerCase());
	pedido.setEstado(EstadoPedido.CANCELADO);
	venta.setPago(pago);
	Venta ventaSaved =iVentaServices.SaveVenta(venta);
	log.info("cancelando la venta: "+ ventaSaved.getIdVenta()+ "con el pago: "+ pago.getId());

    }

}
