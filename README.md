# 💳 SSLCommerz Java (Spring Boot) Payment Gateway Integration

This project provides a **complete Java-based Spring Boot implementation** of the [SSLCommerz Payment Gateway](https://developer.sslcommerz.com/), built for developers in Bangladesh who want to integrate secure online payment capabilities into their Java applications.

It covers the **entire payment flow**, including:

- Initiating payments
- Redirecting to SSLCommerz-hosted payment UI
- Handling success, failure, and cancellation callbacks
- Validating payments using SSLCommerz's validation API

---

## ✅ Key Features

- ✅ Java & Spring Boot 3+ based clean architecture
- ✅ Uses **RestClient** (modern HTTP client introduced in Spring 6)
- ✅ Decoupled service layer for future extensibility
- ✅ `application.properties` driven config
- ✅ Handles both card and mobile banking flows
- ✅ Currently uses **static test data**, but designed for easy extension to support **dynamic runtime data**

---

## 🔧 APIs Available

| Endpoint                  | Method | Purpose                             |
|---------------------------|--------|-------------------------------------|
| `/api/payment/initiate`   | POST   | Initiates a payment session         |
| `/api/payment/success`    | POST   | Handles SSLCommerz success callback |
| `/api/payment/fail`       | POST   | Handles failed payment              |
| `/api/payment/cancel`     | POST   | Handles canceled transactions       |

---

## 🧪 initiatePayment() — Static Test Data

Currently, the payment is initiated using **static values** like:

```java
data.add("total_amount", "100");
data.add("cus_name", "Test");
data.add("cus_email", "test@example.com");
data.add("product_name", "TestProd");
