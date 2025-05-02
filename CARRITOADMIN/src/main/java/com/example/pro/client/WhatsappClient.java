package com.example.pro.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.core.io.Resource;

import com.example.pro.model.requestMessage;

@FeignClient(name = "whatsapp", url = "https://graph.facebook.com/v22.0/${IDENT_CUENTA_META}")
public interface WhatsappClient {

    @PostMapping("/messages")
    ResponseEntity<Map<String, Object>> sendMesagge(@RequestHeader(name = "Authorization") String auth,
	    @RequestBody requestMessage msg);

    @PostMapping(value = "/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Map<String, Object> uploadMedia(@RequestPart("file") Resource file,
	    @RequestPart("messaging_product") String messagingProduct,
	    @RequestHeader("Authorization") String authorization);

}
