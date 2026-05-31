package com.example.orders;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

  private final IdempotencyStore store;

  public OrderConsumer(IdempotencyStore store){
    this.store = store;
  }

  @RetryableTopic(
      attempts = "4",
      dltStrategy = DltStrategy.ALWAYS_RETRY_ON_ERROR,
      dltTopicSuffix = "-dlq"
  )
  @KafkaListener(topics = "orders", groupId = "orders-group")
  public void consume(Order order){

    if(store.alreadyProcessed(order.getOrderId())){
      System.out.println("Duplicate ignored -> " + order.getOrderId());
      return;
    }

    System.out.println("Processing " + order.getOrderId());

    if(order.getItem() != null && order.getItem().equalsIgnoreCase("FAIL")){
      throw new RuntimeException("forcing retry");
    }
  }

  @KafkaListener(topics = "orders-dlq", groupId = "orders-dlq-group")
  public void dlq(Order order){
    System.out.println("Moved to DLQ -> " + order.getOrderId());
  }
}
