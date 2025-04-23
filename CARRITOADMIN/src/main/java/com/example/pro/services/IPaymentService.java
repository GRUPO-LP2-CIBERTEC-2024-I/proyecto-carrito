package com.example.pro.services;

import com.mercadopago.resources.Payment;

public interface IPaymentService {

    void generarVentaConMercadoPago(Payment Mpago);
}
