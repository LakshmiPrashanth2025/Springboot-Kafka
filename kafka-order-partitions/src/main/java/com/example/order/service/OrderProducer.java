package com.example.order.service;
import com.example.order.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
public class OrderProducer {
 private final KafkaTemplate<String,Object> kafkaTemplate;
 @Value("${topic.name}") private String topic;
 public OrderProducer(KafkaTemplate<String,Object> kafkaTemplate){this.kafkaTemplate=kafkaTemplate;}
 public void send(OrderRequest order){
   kafkaTemplate.send(topic, order.customerId(), order);
 }
}
