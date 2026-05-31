package com.example.kafka.workshop.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
	
	@KafkaListener(topics = "orders", groupId = "order-group" )
	public void consume(String message) {
		System.out.println("Received message" + message);
		
		// Send email Notification
		
		// Send Shipping Process
	}

}
