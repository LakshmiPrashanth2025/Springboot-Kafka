package com.example.orders;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

  private final KafkaTemplate<String,Object> kafkaTemplate;

  public OrderProducer(KafkaTemplate<String,Object> kafkaTemplate){
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(Order order){
    kafkaTemplate.send("orders", order.getOrderId(), order);
  }
}
