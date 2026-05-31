# Kafka Example Setup Guide (Spring Boot + Docker Desktop + Apache Kafka)

This guide explains how to:

1. Install Docker Desktop on Windows  
2. Run Kafka inside Docker  
3. Create and test Kafka topics using Kafka CLI commands  
4. Send and receive messages using Kafka Console Producer/Consumer  
5. Run a Spring Boot Kafka application

---

# 1. Install Docker Desktop

## Why Docker?

Docker allows you to run applications in isolated containers without manually installing Kafka and Zookeeper on Windows.

Benefits:

- Easy setup
- No manual Kafka installation
- Easy restart/reset
- Lightweight local development

## Install Docker Desktop for Windows

Download Docker Desktop:

https://docs.docker.com/desktop/setup/install/windows-install/

Install Docker Desktop and restart your machine if required.

After installation:

Open **Docker Desktop** and ensure it is running.

Verify installation from Command Prompt:

```bash
docker --version
```

Example output:

```text
Docker version 28.x.x
```

Verify Docker is running:

```bash
docker ps
```

If Docker is running, you should not see errors.

---

# 2. Install Kafka Image in Docker

We will use the official Apache Kafka image.

Open **Command Prompt** and run:

```bash
docker run -d --name kafka -p 9092:9092 apache/kafka:latest
```

## Command Explanation

### docker run

Starts a new container.

### -d

Runs the container in detached mode (background).

### --name kafka

Gives the container a readable name:

```text
kafka
```

instead of a random container id.

### -p 9092:9092

Maps container Kafka port to local machine.

Meaning:

```text
localhost:9092 → Kafka broker
```

### apache/kafka:latest

Downloads and runs the latest Kafka image.

---

## Verify Kafka Container

Run:

```bash
docker ps
```

Example:

```text
CONTAINER ID   IMAGE                  STATUS
12345abc       apache/kafka:latest    Up 5 minutes
```

You should see:

```text
kafka
```

running.

---

# 3. Verify Kafka Is Running

List Kafka topics:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

## Command Explanation

### docker exec -it kafka

Runs command inside the Kafka container.

### kafka-topics.sh

Kafka CLI tool to manage topics.

### --list

Lists all Kafka topics.

### --bootstrap-server localhost:9092

Connects to Kafka broker.

Initially, you may see no topics.

---

# 4. Create Topic (orders)

If the topic does not exist, create it.

Run:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
--create \
--topic orders \
--bootstrap-server localhost:9092 \
--partitions 3 \
--replication-factor 1
```

## Why 3 partitions?

This helps demonstrate:

- Parallel processing
- Consumer load balancing
- Ordering guarantees per key
- Multiple consumers

Verify topic:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

Expected output:

```text
orders
```

Describe topic:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic orders
```

Example output:

```text
Topic: orders
PartitionCount: 3
ReplicationFactor: 1
```

---

# 5. Run Kafka Consumer

Open a new Command Prompt window.

Run:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic orders \
--from-beginning
```

## Command Explanation

### kafka-console-consumer.sh

Starts Kafka consumer.

### --topic orders

Reads messages from:

```text
orders
```

topic.

### --from-beginning

Reads all messages from beginning.

The terminal will now wait for incoming messages.

Example:

```text
Hello
Sending orders
```

You will see messages appear here after producer sends them.

---

# 6. Run Kafka Producer

Open another Command Prompt window.

Run:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-producer.sh \
--bootstrap-server localhost:9092 \
--topic orders
```

The terminal will now wait for input.

Type messages:

```text
Hello
Sending orders
Order Created
Order Payment Success
```

Press Enter after each message.

---

# 7. Observe Consumer Output

Switch back to consumer terminal.

You should see:

```text
Hello
Sending orders
Order Created
Order Payment Success
```

This proves:

```text
Producer → Kafka Topic → Consumer
```

is working successfully.

---

# 8. Run Spring Boot Application

Now start your Spring Boot Kafka application.

Example:

```bash
mvn clean install
mvn spring-boot:run
```

or

Run the application from IntelliJ/Eclipse.

Application starts on:

```text
http://localhost:8080
```

(or the configured port)

---

# 9. Send Event to Kafka via REST API

Example endpoint:

```http
POST /api/orders
```

Sample payload:

```json
{
  "orderId": "ORD-1001",
  "customerId": "CUST-101",
  "product": "iPhone",
  "amount": 4500
}
```

Using curl:

```bash
curl --location 'http://localhost:8080/api/orders' \
--header 'Content-Type: application/json' \
--data '{
    "orderId":"ORD-1001",
    "customerId":"CUST-101",
    "product":"iPhone",
    "amount":4500
}'
```

What happens internally:

```text
REST API
    ↓
Kafka Producer
    ↓
orders topic
    ↓
Kafka Consumer
```

Your Spring Boot application publishes message to Kafka.

Consumer receives and processes it.

---

# 10. Useful Kafka Commands

## List Topics

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

## Describe Topic

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic orders
```

## Run Producer

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic orders
```

## Run Consumer

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic orders --from-beginning
```

# Kafka Basic Example (Spring Boot + Kafka + Swagger)

## Run Spring Boot Application

Start the application using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

Or run directly from your IDE.

Application runs at:

```text
http://localhost:8080
```

---

## Open Swagger UI

Open:

```text
http://localhost:8080/swagger-ui/index.html#/order-controller/createOrder
```

---

## Test API Endpoint

### GET /order/{event}

This endpoint publishes an order event to Kafka.

### Example Input

Mobile Order placed

Call in browser / Swagger:

```http
GET http://localhost:8080/order/Mobile%20Order%20placed
```

---

## Expected Response

### HTTP Status

```text
200 OK
```

### Response Body

```text
Order event sent: Mobile Order placed
```

---

## Kafka Flow Explanation

When you invoke the Swagger endpoint, the following flow happens:

```text
Swagger UI
   ↓
REST Controller
   ↓
Kafka Producer
   ↓
orders Topic
   ↓
Kafka Consumer
```

### Step-by-step

### 1. Swagger UI

You trigger the API using Swagger.

Example:

```http
GET /order/Mobile Order placed
```

---

### 2. REST Controller

The controller receives the request and extracts the event.

Example:

```java
@GetMapping("/order/{event}")
```

---

### 3. Kafka Producer

The producer publishes the event message to Kafka.

Example message:

```text
Mobile Order placed
```

---

### 4. Kafka Topic

The message is pushed into:

```text
orders
```

Kafka stores the event temporarily.

---

### 5. Kafka Consumer

The consumer listens to the `orders` topic and receives the event.

Expected console output:

```text
Consumed message: Mobile Order placed
```

---

## Consumer Verification Command

Run Kafka consumer in another terminal:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic orders --from-beginning
```

After invoking Swagger, you should see:

```text
Mobile Order placed
```

---

## Verification Checklist

- Spring Boot application running
- Kafka Docker container running
- Swagger endpoint accessible
- API returns **200 OK**
- Consumer terminal receives the event

---

## End-to-End Result

```text
Swagger Request Sent Successfully
        ↓
Kafka Message Published
        ↓
Consumer Receives Event
        ↓
Kafka Flow Verified
```

### Finally to stop Kafka container in  Docker (if you no longer use it):

## View Running Containers

```bash
docker ps
```

## Stop Kafka

```bash
docker stop kafka
```

## Start Kafka Again

```bash
docker start kafka
```

## Remove Kafka Container

```bash
docker rm -f kafka
```

---

# Summary

This project is to help with:

- Docker Desktop installation
- Running Kafka inside Docker
- Kafka Topics
- Kafka Producer
- Kafka Consumer
- Sending messages manually
- Running Spring Boot Kafka applications
- Publishing events to Kafka using REST APIs

