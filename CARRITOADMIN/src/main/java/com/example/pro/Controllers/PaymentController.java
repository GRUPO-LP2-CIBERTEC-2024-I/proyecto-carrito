package com.example.pro.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pro.DTO.PaymentIntentDTO;
import com.example.pro.services.Impl.PaymentService;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/stripe")

public class PaymentController {
	@Autowired
	private PaymentService paymentService;
//	
//	@PostMapping("/paymentintent")
//	public ResponseEntity<String> paymentintent(@RequestBody PaymentIntentDTO paymentIntentDTO) throws StripeException {
//		PaymentIntent paymentIntent = paymentService.paymentIntentDTO(paymentIntentDTO);
//		String paymenttostring = paymentIntent.toJson();
//		return new ResponseEntity<String>(paymenttostring,HttpStatus.OK);
//	}
//	
//	@PostMapping("/confirm/{id}")
//	public ResponseEntity<String> confirm(@PathVariable String id) throws StripeException {
//		PaymentIntent paymentIntent = paymentService.confirm(id);
//		String paymenttostring = paymentIntent.toJson();
//		return new ResponseEntity<String>(paymenttostring,HttpStatus.OK);
//	}
//	
//	@PostMapping("/cancel/{id}")
//	public ResponseEntity<String> cancel(@PathVariable String id) throws StripeException {
//		PaymentIntent paymentIntent = paymentService.cancel(id);
//		String paymenttostring = paymentIntent.toJson();
//		return new ResponseEntity<String>(paymenttostring,HttpStatus.OK);
//	}
	
}
