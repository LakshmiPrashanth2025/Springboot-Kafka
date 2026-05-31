package com.example.orders;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class IdempotencyStore {
  private final Set<String> processed = ConcurrentHashMap.newKeySet();

  public boolean alreadyProcessed(String id){
    return !processed.add(id);
  }
}
