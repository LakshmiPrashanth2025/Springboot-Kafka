# Kafka Consumer Groups - Real World Fan-Out Example

A Spring Boot + Kafka example demonstrating multiple consumer groups consuming the same topic independently.

## What this project demonstrates

- Same Kafka topic
- Multiple consumer groups
- Every consumer group receives every message
- Event-driven microservice communication

## Architecture

```text
REST API
   |
   v
Kafka Producer
   |
   v
orders topic
   |
   ├── payment-group
   │      └── PaymentConsumer
   │
   ├── notification-group
   │      └── NotificationConsumer
   │
   └── analytics-group
          └── AnalyticsConsumer
```

## Kafka Setup

Broker:

```text
localhost:9092
```

Topic:

```text
orders
```

## Run Application

```bash
mvn clean install
mvn spring-boot:run
```

Application URL:

```text
http://localhost:8082
```

## API

### POST /api/orders

Payload:

```json
{
  "orderId": "ORD-2001",
  "customerId": "CUST-101",
  "product": "MacBook",
  "amount": 120000
}
```

Curl:

```bash
curl --location 'http://localhost:8082/api/orders' --header 'Content-Type: application/json' --data '{
    "orderId":"ORD-2001",
    "customerId":"CUST-101",
    "product":"MacBook",
    "amount":120000
}'
```

## Expected Logs

```text
payment-group received order=ORD-2001
notification-group received order=ORD-2001
analytics-group received order=ORD-2001
```

## Kafka Concepts Demonstrated

- Consumer Groups
- Pub/Sub Messaging
- Event Driven Architecture
- Service Decoupling
- Fan-out Processing
