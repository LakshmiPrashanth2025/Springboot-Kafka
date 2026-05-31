package com.example.order.dto;
public record OrderRequest(String orderId,String customerId,String product,double amount){}
