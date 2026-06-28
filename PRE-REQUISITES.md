# Pre-Requisites

This document lists the required software and tools needed for running Kafka-based applications.

---

## 1. Docker Desktop

Docker is required to run Kafka using containers.

### Download and Install Docker Desktop

Download Docker Desktop from:

https://docs.docker.com/desktop/setup/install/windows-install/

After installation, verify Docker is running:

```bash
docker --version
```

---

## 2. Java Development Kit (JDK 25)

Java is required for developing and running Spring Boot and Kafka applications.

### Download and Install Java 25

Download JDK 25 from:

https://www.oracle.com/in/java/technologies/downloads/#jdk25-windows

Verify Java installation:

```bash
java -version
```

Expected output:

```
java version "25"
```

---

## 3. IDE Installation

Install any one of the following IDEs for application development.

### Option 1: Eclipse / Spring Tool Suite (STS)

Download Eclipse:

https://www.eclipse.org/downloads/packages/

or

Download Spring Tool Suite (STS):

https://spring.io/tools

---

### Option 2: IntelliJ IDEA

Download IntelliJ IDEA:

https://www.jetbrains.com/idea/download/?section=windows

---

## 4. Postman

Postman is used for testing REST APIs exposed by Kafka-based applications.

### Download and Install Postman

https://www.postman.com/downloads

Verify installation by opening Postman and creating API requests.

---

# Kafka Installation Using Docker

After installing Docker Desktop, install the Kafka Docker image.

## Step 1: Open Command Prompt

Open Command Prompt or Terminal.

## Step 2: Run Kafka Docker Container

Execute the following command:

```bash
docker run -d --name kafka -p 9092:9092 apache/kafka:latest
```

This command will:

- Download the Apache Kafka Docker image
- Create a Kafka container named `kafka`
- Start Kafka on port `9092`

---

## Step 3: Verify Kafka Container

Check if Kafka is running:

```bash
docker ps
```

Expected output:

```
CONTAINER ID   IMAGE                 PORTS
xxxxxxx        apache/kafka:latest   0.0.0.0:9092->9092/tcp
```

---

## Environment Setup Complete

After completing the above steps, the system will have:

| Software | Purpose |
|----------|---------|
| Docker Desktop | Runs Kafka container |
| Java 25 | Develop and run Kafka applications |
| Eclipse / STS / IntelliJ | Application development |
| Postman | API testing |
| Apache Kafka Docker Image | Kafka broker |

The environment is now ready for Kafka application development.
