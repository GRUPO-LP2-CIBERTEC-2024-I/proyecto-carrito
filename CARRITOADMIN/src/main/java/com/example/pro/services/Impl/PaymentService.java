package com.example.pro.services.Impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.pro.DTO.PagoDTO;
import com.example.pro.DTO.PedidoDTO;
import com.example.pro.DTO.VentaAndDetalles;
import com.example.pro.DTO.VentaDTO;
import com.example.pro.Repository.IPagoRepository;
import com.example.pro.client.WhatsappClient;
import com.example.pro.model.Cliente;
import com.example.pro.model.EstadoPedido;
import com.example.pro.model.ImageMessage;
import com.example.pro.model.Pago;
import com.example.pro.model.Pedido;
import com.example.pro.model.Venta;
import com.example.pro.model.requestMessage;
import com.example.pro.services.IClienteServices;
import com.example.pro.services.IPaymentService;
import com.example.pro.services.IReporteServices;
import com.example.pro.services.IVentaServices;
import com.example.pro.utils.ResourceUtil;
import com.google.gson.Gson;
import org.springframework.core.io.Resource;
import com.mercadopago.resources.Payment;

import io.grpc.netty.shaded.io.netty.util.internal.ResourcesUtil;
import net.sf.jasperreports.engine.JRException;

@Service
public class PaymentService implements IPaymentService {

    private Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private IVentaServices iVentaServices;
    @Autowired
    private IPagoRepository _IPagoRepository;
    @Autowired
    private IReporteServices _IReporteServices;
    @Autowired
    private WhatsappClient client;

    @Value("${META_TOKEN}")
    private String auth;

    @Autowired
    private Gson gson;

    @Override
    public void generarVentaConMercadoPago(Payment payment) throws IOException, JRException {
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
	log.info("generando la venta con el pago: " + payment.getId());
	Venta ventaSaved = iVentaServices.SaveVentaAndDetalles(venta);
	System.out.println("✅ Venta registrada: " + ventaSaved.getIdVenta());
	byte[] imageBytes = _IReporteServices.generarBoleta(ventaSaved);
	Resource imagenResource = ResourceUtil.byteArrayToResource(imageBytes, "comprobante.png");
	String id = client.uploadMedia(imagenResource, "whatsapp", "Bearer ".concat(auth)).get("id").toString();
	ImageMessage imgMsm = new ImageMessage();
	imgMsm.setId(id);
	requestMessage requestbody = new requestMessage("51".concat(ventaSaved.getCli().getTelefono()), "image",
		imgMsm);
	client.sendMesagge("Bearer ".concat(auth), requestbody);
    }

    @Override
    public void cancelarVenta(Payment payment) {
	Pago pago = _IPagoRepository.findById(payment.getId()).orElseThrow();
	Venta venta = iVentaServices.findByPago(pago);
	Pedido pedido = venta.getPedido();
	pago.setEstado(payment.getStatus().name().toLowerCase());
	pedido.setEstado(EstadoPedido.CANCELADO);
	venta.setPago(pago);
	Venta ventaSaved = iVentaServices.SaveVenta(venta);
	log.info("cancelando la venta: " + ventaSaved.getIdVenta() + "con el pago: " + pago.getId());

    }

}
