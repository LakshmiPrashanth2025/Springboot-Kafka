package com.example.groups.consumer;
import com.example.groups.dto.OrderRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {
 
 @KafkaListener(topics="orders", groupId="payment-group")
 public void consume(OrderRequest order){
  System.out.println("payment-group received order="+order.orderId());
 }
 
}
