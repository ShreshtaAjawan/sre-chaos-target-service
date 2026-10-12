# Chaos Engineering Target API Sandbox

A decoupled, stand-alone simulation environment designed to mock, isolate, and forcefully trigger severe distributed microservice failures under strict conditions. This utility functions as a controlled chaos sandbox to evaluate the resilience of external monitoring systems, SRE tools, and log scraping engines.

## 🚀 Key Architectural Highlights

* **Contract-First Chaos Endpoints:** Exposes robust Spring WebMVC HTTP interfaces specifically engineered to programmatically simulate production-grade application failure vectors.
* **Deterministic Failure Models:** Includes out-of-the-box support to instantly force execution-blocking errors, including `SQLException` database connection resource deadlocks and background security thread `NullPointerException` events.
* **Telemetry Output Pipeline:** Employs enterprise-grade, highly structured text stream generation via SLF4J and Logback configurations, pumping contextual application telemetry outputs straight into cross-workspace ingestion logs.

---

## 🛠️ Tech Stack

* **Core Runtime:** Java 21
* **Framework:** Spring Boot 3.x (Spring Web)
* **Logging System:** SLF4J with Logback Core Appenders
* **Testing Client:** Postman / cURL Compatibility Suite

---

## 🛤️ Chaos Simulation Endpoints Matrix

You can trigger distinct system failures by issuing requests to the following REST interfaces:

| HTTP Method | API Path                     | Simulated Failure Payload | Telemetry Output Profile |
| :--- |:-----------------------------| :--- | :--- |
| **GET** | `/api/v1/error/null-pointer` | Forces an immediate connection pool timeout | Generates multi-line HikariCP framework traces |
| **GET** | `/api/v1/error/db-timeout`   | Interrupts active security controller execution thread | Pushes explicit core Java runtime exception stack arrays |
| **POST** | `/api/v1/error/network-failure`      | Inject artificial millisecond response delay windows | Simulates downstream network degradation anomalies |

---

## ⏱️ Local Run & Operations Sequence

### 1. Build the Binary
Clone the sandbox directory and execute the local build system wrapper:
```bash
./mvnw clean package
```

### 2. Launch the Service
Start the sandbox on its target container port configuration (Defaults to port `8085` to avoid gateway allocation conflicts):
```bash
java -jar target/chaos-api-sandbox-0.0.1-SNAPSHOT.jar --server.port=8081
```

### 3. Verify Logs Telemetry Routing
Confirm that the runtime framework is successfully linking its SLF4J appenders to your environment target profile path:
```bash
tail -f /var/log/target-apps/app-telemetry.log
```

---

## 🧪 Testing Simulation Scenarios

### Trigger a Database Pool Interruption
Execute a simple `GET` call via terminal or Postman client to stream a raw `SQLException` block into the monitoring pipeline:
```bash
curl -X GET http://localhost:8081/api/v1/error/db-timeout
```
Check the output tracking file to watch the immediate deployment of the multi-line stack array payload into your log file ecosystem.
