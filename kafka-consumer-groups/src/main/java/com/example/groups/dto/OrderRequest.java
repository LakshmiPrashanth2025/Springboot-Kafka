package com.example.order.dto;

//For records, Java automatically generates accessor methods for each field with the same name as the field
public record OrderRequest(
  String orderId,
  String customerId,
  String product,
  double amount)
 {
  
  }
