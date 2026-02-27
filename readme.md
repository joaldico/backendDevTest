# 🚀 Backend Technical Test: Similar Products Orchestrator

This project implements a high-performance REST API to provide product details for similar items, following a **Hexagonal Architecture** (Ports and Adapters) to ensure maintainability and decoupled logic.

### 🛠️ Tech Stack & Architecture
* **Java 21 & Spring Boot 3.4**: Leveraged **Virtual Threads** (Project Loom) to handle massive concurrency during microservice orchestration without thread exhaustion.
* **Resilience4j**: Implemented **Circuit Breakers** and **Timeouts** to handle external service failures gracefully.
* **Hexagonal Architecture**:
    * **Domain**: Pure business logic and models.
    * **Application**: Use cases orchestrating parallel calls to external mocks.
    * **Infrastructure**: REST controllers (port 5000) and high-performance `RestClient` adapters.

### 📈 Performance & Resilience Results
The system was subjected to a rigorous stress test using **k6**, simulating various failure scenarios (latency, 500 errors, and timeouts).

* **Key Metrics**:
    * **Throughput**: Successfully handled over **6,500 requests** with a peak of **72 req/s**.
    * **Resilience**: The **Circuit Breaker** effectively isolated failures from the "Simulado" mock service, preventing cascading failures across the system.
    * **Concurrency**: Utilized `parallelStream()` backed by Virtual Threads to fetch product details simultaneously.

#### Visual Evidence (from Grafana):
As shown in the dashboard below, the system maintained stability even when external mock latency spiked to over 5 seconds:

![Grafana Metrics](./results/image_d68a41.png)

#### Stress Test Console Output:
Logs showing the system handling heavy load and the Circuit Breaker/Thread management in action:

![k6 Results](./results/image_d62579.jpg)

### 🐳 How to Run
1. **Build**: `mvn clean install`
2. **Infrastructure**: `docker compose up -d`
3. **Stress Test**: `docker compose run --rm k6 run scripts/test.js`