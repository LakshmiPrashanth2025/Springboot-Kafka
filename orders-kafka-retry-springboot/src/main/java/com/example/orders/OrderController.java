package com.example.orders;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderProducer producer;

  public OrderController(OrderProducer producer){
    this.producer = producer;
  }

  @PostMapping
  public String publish(@RequestBody Order order){
    producer.send(order);
    return "Order published: " + order.getOrderId();
  }
}
