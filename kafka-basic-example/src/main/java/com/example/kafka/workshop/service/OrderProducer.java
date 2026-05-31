package com.example.kafka.workshop.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	
	public String sendOrderEvent(String event) {
		// Argument1 = Topic name, argument2 = message sent to topic
		kafkaTemplate.send("orders", event);
		
		return "Order event sent: " + event;
	}

}
