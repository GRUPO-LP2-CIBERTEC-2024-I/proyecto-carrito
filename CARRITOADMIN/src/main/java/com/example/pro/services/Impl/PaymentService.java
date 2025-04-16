package com.example.pro.services.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.pro.DTO.PaymentIntentDTO;
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.PaymentIntent;

@Service
public class PaymentService {
	
//	@Value("${config.stripe.secretkey}")
//	private String secretKey;
	
//	public PaymentIntent paymentIntentDTO(PaymentIntentDTO intent) throws StripeException {
//		Stripe.apiKey = secretKey;
//		Map<String, Object> body = new HashMap<>();
//		body.put("amount", intent.getAmount());
//		body.put("currency", intent.getCurrency());
//		body.put("description", intent.getDescripcion());
//		ArrayList<?> payment_method_types = new ArrayList<>();
//		body.put("payment_method_types", payment_method_types);
//		
//		return PaymentIntent.create(body);
//	}
//	public PaymentIntent confirm(String id) throws StripeException {
//		Stripe.apiKey = secretKey;
//		PaymentIntent paymentintent = PaymentIntent.retrieve(id);
//		Map<String, Object> body = new HashMap<>();
//		body.put("payment_method", "pm_card_visa");
//		paymentintent.confirm(body);
//		return paymentintent;
//	}
//	public PaymentIntent cancel(String id) throws StripeException {
//		Stripe.apiKey = secretKey;
//		PaymentIntent paymentintent = PaymentIntent.retrieve(id);
//		
//		paymentintent.cancel();
//		return paymentintent;
//	}
	
	
}
