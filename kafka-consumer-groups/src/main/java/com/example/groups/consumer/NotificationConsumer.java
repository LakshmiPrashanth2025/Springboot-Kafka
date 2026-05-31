package com.example.groups.consumer;
import com.example.groups.dto.OrderRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
 
 @KafkaListener(topics="orders", groupId="notification-group")
 public void consume(OrderRequest order){
  System.out.println("notification-group received order="+order.orderId());
 }
 
}
