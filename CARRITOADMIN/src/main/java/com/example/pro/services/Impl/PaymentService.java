package com.example.pro.services.Impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pro.DTO.VentaAndDetalles;
import com.example.pro.DTO.VentaDTO;
import com.example.pro.model.Cliente;
import com.example.pro.services.IClienteServices;
import com.example.pro.services.IPaymentService;
import com.example.pro.services.IVentaServices;
import com.mercadopago.resources.Payment;


@Service
public class PaymentService implements IPaymentService {
    
    private Logger log =  LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private IVentaServices iVentaServices;
    @Autowired
    private IClienteServices iClienteServices;
    @Override
    public void generarVentaConMercadoPago(Payment payment) {
	log.info("extraendo datos de payment de Mercado pago encontrado por id, del pagador: "
    + payment.getPayer().getIdentification().getNumber() );
//        String emailComprador = payment.getPayer().getEmail();
        Long monto = payment.getTransactionAmount().longValue();
        String metodo = payment.getPaymentMethodId();
        Cliente cli = iClienteServices.getByDni(payment.getPayer().getIdentification().getNumber());
        VentaDTO ventadto = new VentaDTO();
        ventadto.setMonto(monto);
        ventadto.setMetodo(metodo);
        ventadto.setFechaVenta(LocalDateTime.now().toString());
        ventadto.setPaymentId(payment.getId().toString());
        ventadto.setCli(cli.getIdCliente());
//        venta.setEmail(emailComprador);
        VentaAndDetalles venta = new VentaAndDetalles();
        venta.setVentaDTO(ventadto);
        log.info("generando la venta...");
        iVentaServices.SaveVentaAndDetalles(venta);
        System.out.println("✅ Venta registrada: " + venta);
	
    }
	

	
}
