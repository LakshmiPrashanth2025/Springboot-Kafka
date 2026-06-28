# Kafka Setup Using Docker

This guide explains how to install Apache Kafka using Docker and perform basic Kafka operations such as checking cluster status, listing topics, running producers, consumers, and deleting topics.

---

## 1. Install Kafka Using Docker

Run the following command to start Kafka:

```bash
docker run -d --name kafka -p 9092:9092 apache/kafka:latest
```

Verify that the Kafka container is running:

```bash
docker ps
```

---

## 2. Get Kafka Cluster ID

Run the following command to retrieve the Kafka Cluster ID and confirm that Kafka is active:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-cluster.sh cluster-id --bootstrap-server :9092
```

Example output:

```
ClusterId: xxxxxxxxxxxxxxxx
```

---

## 3. List Kafka Topics

To view all available topics:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
--list \
--bootstrap-server localhost:9092
```

---

## 4. Run Kafka Consumer

### Consume Messages from `orders` Topic

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic orders \
--from-beginning
```

---

### Consume Messages from `payment-failed` Topic

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic payment-failed \
--from-beginning
```

The consumer will start listening and display incoming messages.

---

## 5. Run Kafka Producer

Start a producer for the `orders` topic:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-producer.sh \
--bootstrap-server localhost:9092 \
--topic orders
```

After running this command, the terminal will wait for your input.

Example:

```
{"orderId":"101","customerId":"C001","amount":2500}
```

Press **Enter** to publish the message.

The consumer listening on the `orders` topic will receive the message.

---

## 6. Delete Kafka Topic

To delete a topic:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
--bootstrap-server localhost:9092 \
--delete \
--topic order-created
```

Verify deletion:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
--list \
--bootstrap-server localhost:9092
```

---

## 7. Useful Kafka Docker Commands

### Check Kafka Container Logs

```bash
docker logs kafka
```

### Stop Kafka

```bash
docker stop kafka
```

### Start Kafka

```bash
docker start kafka
```

### Remove Kafka Container

```bash
docker rm -f kafka
```

---

## Summary

| Operation | Command |
|-----------|---------|
| Start Kafka | `docker run -d --name kafka -p 9092:9092 apache/kafka:latest` |
| Get Cluster ID | `kafka-cluster.sh cluster-id` |
| List Topics | `kafka-topics.sh --list` |
| Start Consumer | `kafka-console-consumer.sh` |
| Start Producer | `kafka-console-producer.sh` |
| Delete Topic | `kafka-topics.sh --delete` |
