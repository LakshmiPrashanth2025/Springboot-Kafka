# Kafka Order Partitions
Uses existing Kafka on localhost:9092 and topic `orders`.
Run app and POST to `/api/orders`.
Messages are keyed by customerId to preserve ordering per customer.
Scale by increasing listener concurrency or app instances.
