package com.example.groups.controller;
import com.example.groups.dto.OrderRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/orders")
public class OrderController {
 
 private final KafkaTemplate<String,Object> template;
 
 public OrderController(KafkaTemplate<String,Object> template){
   this.template=template;
 }
 
 @PostMapping 
 public String publish(@RequestBody OrderRequest req){
  template.send("orders", req.customerId(), req); return "published";
 }
 
}
