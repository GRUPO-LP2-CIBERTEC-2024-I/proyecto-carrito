package com.example.pro.Controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pro.DTO.PaymentIntentDTO;
import com.example.pro.DTO.VentaAndDetalles;
import com.example.pro.model.Cliente;
import com.example.pro.model.Producto;
import com.example.pro.services.IClienteServices;
import com.example.pro.services.IPaymentService;
import com.example.pro.services.IProductoServices;
import com.example.pro.services.Impl.PaymentService;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Address;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Identification;
import com.mercadopago.resources.datastructures.preference.Item;
import com.mercadopago.resources.datastructures.preference.Payer;
import com.mercadopago.resources.datastructures.preference.PaymentMethods;
import com.mercadopago.resources.datastructures.preference.Phone;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/pago")

public class PaymentController {

    @Autowired
    private IPaymentService iPaymentService;
    
    @Autowired
    private IProductoServices iProductoServices;
    
    @Autowired
    private IClienteServices iClienteServices;

    @PostMapping("/crear-preferencia")
    public ResponseEntity<?> crearPreferencia(@RequestBody VentaAndDetalles venta) {
	try {
	    Preference preference = new Preference();
	    PaymentMethods payMethods = new PaymentMethods();
	    payMethods.setInstallments(1);
	    preference.setPaymentMethods(payMethods );
	    venta.getDetallesDTO().forEach(detalle -> {
		Producto pro = iProductoServices.getById(detalle.getProducto());
		Item item = new Item();
		item.setTitle(detalle.getNomProd())
		.setQuantity(detalle.getCant())
		.setUnitPrice(Float.parseFloat(pro.getPrecioUnidad()+""));
		preference.appendItem(item);
	    });
	    Cliente cli = iClienteServices.FindClienteById(venta.getVentaDTO().getCli()); 
	    Payer payer = new Payer();
	    payer.setDateCreated(LocalDate.now().toString());
	    payer.setName(cli.getNombres());
	    payer.setSurname(cli.getApellidos());
	    payer.setPhone(new Phone().setNumber(cli.getTelefono()));
	    payer.setEmail(cli.getCorreo());
	    payer.setAddress(new Address().setStreetName(cli.getDireccion()));
	    Identification ident = new Identification();
	    ident.setType("DNI");
	    ident.setNumber(cli.getDni());
	    payer.setIdentification(ident);
	    System.err.println("identificador: "+ ident.getNumber());
	    preference.setPayer(payer);

	    BackUrls backUrls = new BackUrls();
	    backUrls.setSuccess("https://tu-front.com/pago-exitoso")
	    .setFailure("https://tu-front.com/pago-fallido")
		    .setPending("https://tu-front.com/pago-pendiente");

	    preference.setBackUrls(backUrls);
	    preference.setAutoReturn(Preference.AutoReturn.approved);

	    Preference savedPreference = preference.save();

	    return ResponseEntity
		    .ok(Map.of("id", savedPreference.getId(), "init_point", savedPreference.getInitPoint()));

	} catch (Exception e) {
	    e.printStackTrace();
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la preferencia");
	}
    }

    @PostMapping
    public ResponseEntity<String> recibirWebhook(@RequestParam("id") Long paymentId,
	    @RequestParam("topic") String topic) {

	if ("payment".equalsIgnoreCase(topic)) {
	    try {
		Payment payment = Payment.findById(paymentId.toString());
		System.out.println(payment.getStatusDetail());
		if ("approved".equalsIgnoreCase(payment.getStatus().name())) {
		    iPaymentService.generarVentaConMercadoPago(payment);
		} else {
		    System.out.println("⚠️ Pago no aprobado: " + payment.getStatus());
		}
	    } catch (Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar webhook");
	    }
	}

	return ResponseEntity.ok("Webhook recibido");
    }

}
