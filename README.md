# 🧠 Quiz App (Microservices Architecture)

## 📌 Overview
This repository represents the **Phase 2 evolution** of the [Quiz App (Monolithic)](https://github.com). 

The project focuses on decomposing a single-unit backend into a scalable, distributed system using **Spring Boot Microservices**. This transition demonstrates how to handle real-world challenges like service discovery, inter-service communication, and independent scaling.

---

## 🎯 Objectives
- **Decomposition:** Successfully split the monolithic Quiz App into independent, manageable services.
- **Scalability:** Enable independent scaling of specific business functions.
- **Resilience:** Implement fault tolerance to ensure system stability.
- **Industry Standards:** Apply modern architectural patterns like API Gateway and Service Discovery.

---

## ⚙️ Tech Stack
- **Backend:** Spring Boot (Microservices)
- **Database:** MongoDB (Per-service data isolation)
- **Service Management:** Spring Cloud Netflix Eureka (Service Discovery)
- **Routing:** Spring Cloud Gateway (API Gateway)
- **Communication:** Feign Client (Synchronous REST-based communication)
- **Build Tool:** Maven
- **Libraries:** Lombok

---

## 🧩 Architecture

### 🔹 Decomposed Services
The application is now split into the following core services:

1.  **Question Service:** Manages the question bank (CRUD, categorization).
2.  **Quiz Service:** Handles the logic for creating quizzes and user attempts.
3.  **Service Registry (Eureka):** Acts as a "phonebook" for services to find each other.
4.  **API Gateway:** The single entry point for all client requests.

---

## 📦 Features
- **Independent Deployment:** Each service can be updated and deployed without affecting the whole system.
- **Centralized Routing:** All API calls are routed through a unified Gateway.
- **Dynamic Discovery:** Services automatically register themselves for seamless communication.
- **Load Balancing:** Client-side load balancing via Feign and Spring Cloud.

---

## 🔄 Roadmap & Future Enhancements
- [ ] **Config Server:** Centralized configuration management.
- [ ] **Fault Tolerance:** Integrating **Resilience4j** for circuit breaking.
- [ ] **Distributed Tracing:** Using **Zipkin/Sleuth** to monitor requests.
- [ ] **Containerization:** Dockerizing each microservice.

---

## 🧪 Running the Project

### 1️⃣ Clone the repository
```bash
git clone https://github.com
```
### 2️⃣ Start the Infrastructure Services
#### 1) Navigate to the Service Registry folder and run:
``` bash
   mvn spring-boot:run
```
#### 2) Navigate to the API Gateway folder and run:
``` bash
 mvn spring-boot:run
```
### 3️⃣ Start the Business Services
#### Repeat the run command for:
##### question-service
##### quiz-service
#### userService
### 4️⃣ Verification
Access the Eureka Dashboard at http://localhost:8761 to ensure all services are registered and "UP".
## 👨‍💻 Author
### Nishant Jha
### GitHub: jhanishant658
### LinkedIn: 
``` bash
 https://www.linkedin.com/in/nishant-jha-0b96a629b/
```
### Original Monolithic Version: Quiz-App Monolith


