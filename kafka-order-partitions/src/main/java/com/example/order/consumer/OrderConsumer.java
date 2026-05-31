package com.example.order.consumer;
import com.example.order.dto.OrderRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
@Component
public class OrderConsumer {
 @KafkaListener(topics="${topic.name}", groupId="order-processing-group")
 public void consume(OrderRequest order,
   @Header(KafkaHeaders.RECEIVED_PARTITION) int partition){
   try{Thread.sleep(1500);}catch(Exception e){}
   System.out.println("Partition="+partition+" customer="+order.customerId()+" order="+order.orderId()+" thread="+Thread.currentThread().getName());
 }
}
