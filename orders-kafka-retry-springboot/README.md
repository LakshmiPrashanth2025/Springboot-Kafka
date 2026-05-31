# Orders Kafka Spring Boot Project

## Start Kafka via Docker

```bash
docker network create kafka-net

docker run -d --name zookeeper --network kafka-net -p 2181:2181 -e ZOOKEEPER_CLIENT_PORT=2181 confluentinc/cp-zookeeper:latest

docker run -d --name kafka --network kafka-net -p 9092:9092 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 confluentinc/cp-kafka:latest
```

## Create Topics

```bash
docker exec kafka kafka-topics --create --topic orders --bootstrap-server localhost:9092
docker exec kafka kafka-topics --create --topic orders-dlq --bootstrap-server localhost:9092
```

## Run

```bash
mvn clean package
mvn spring-boot:run
```

## Test

Success:

```bash
curl -X POST http://localhost:8080/orders -H "Content-Type: application/json" -d '{"orderId":"101","item":"Laptop","quantity":1}'
```

Duplicate (same orderId):

```bash
curl -X POST http://localhost:8080/orders -H "Content-Type: application/json" -d '{"orderId":"101","item":"Laptop","quantity":1}'
```

Retry + DLQ:

```bash
curl -X POST http://localhost:8080/orders -H "Content-Type: application/json" -d '{"orderId":"999","item":"FAIL","quantity":1}'
```

Behavior:
- Retries = initial + 3 retries
- Duplicate orders ignored using in-memory idempotency
- Failures go to orders-dlq
