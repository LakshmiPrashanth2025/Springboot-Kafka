# Kafka Order Partitions - Real World Partition Management Example

A Spring Boot + Apache Kafka example demonstrating real-world Kafka partition management using an Order Management System scenario.

## What this project demonstrates

- Kafka partitions for horizontal scaling
- Message ordering for a customer
- Consumer load balancing
- REST API → Kafka Producer → Kafka Consumer flow
- Same customer routed to same partition

## Architecture

```text
Client / Postman
        |
        v
REST API (/api/orders)
        |
        v
Kafka Producer
        |
        v
orders topic (multiple partitions)
        |
        v
Consumer Group: order-processing-group

    Consumer-1
    Consumer-2
    Consumer-3
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

Check topic partitions:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic orders
```

Increase partitions if required:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --alter --bootstrap-server localhost:9092 --topic orders --partitions 6
```

## Run Application

```bash
mvn clean install
mvn spring-boot:run
```

Application URL:

```text
http://localhost:8081
```

## API

### POST /api/orders

Payload:

```json
{
  "orderId": "ORD-1001",
  "customerId": "CUST-101",
  "product": "iPhone",
  "amount": 4500
}
```

Curl:

```bash
curl --location 'http://localhost:8081/api/orders' --header 'Content-Type: application/json' --data '{
    "orderId":"ORD-1001",
    "customerId":"CUST-101",
    "product":"iPhone",
    "amount":4500
}'
```

## Expected Behavior

Messages with same customerId go to same partition.

Example:

```text
Partition=2 customer=CUST-101 order=ORD-1001
Partition=2 customer=CUST-101 order=ORD-1002
Partition=5 customer=CUST-500 order=ORD-1003
```

## Kafka Concepts Demonstrated

- Partitions
- Consumer Groups
- Ordering Guarantees
- Parallel Processing
- Load Balancing
