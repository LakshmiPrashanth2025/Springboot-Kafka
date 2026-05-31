package com.example.groups.consumer;
import com.example.groups.dto.OrderRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsConsumer {
 
 @KafkaListener(topics="orders", groupId="analytics-group")
 public void consume(OrderRequest order){
  System.out.println("analytics-group received order="+order.orderId());
 }
 
}
