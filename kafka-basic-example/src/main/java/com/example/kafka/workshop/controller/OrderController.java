package com.example.kafka.workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.workshop.service.OrderProducer;

@RestController
public class OrderController {
	
	@Autowired
	OrderProducer orderProducer;
	
	@GetMapping("/order/{event}")
	public String createOrder(@PathVariable String event) {
		String response = orderProducer.sendOrderEvent(event);
		return response;	
		
	}
	
	

}
