package com.example.order.controller;
import com.example.order.dto.OrderRequest;
import com.example.order.service.OrderProducer;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/orders")
public class OrderController {
 private final OrderProducer producer;
 public OrderController(OrderProducer producer){this.producer=producer;}
 @PostMapping public String publish(@RequestBody OrderRequest request){
  producer.send(request); return "Order published";
 }
}
