# 🧸 Toys E-commerce API

A Spring Boot-based RESTful web service for managing an online toy store.
Supports products, categories, users, reviews, and shopping carts.

---

## 🚀 Technologies

* Java 17+
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Lombok
* MapStruct
* Swagger / OpenAPI
* JUnit / Mockito

---

## 📁 Project Structure

```
src/
├── controllers/         → REST controllers (e.g. ProductController)
├── services/            → Business logic (e.g. ProductService)
├── models/              → Entity classes (e.g. Product, Category)
├── repositories/        → JPA repositories
├── dtos/                → Request and response DTOs
├── mappers/             → MapStruct mappers
├── exceptions/          → Custom exceptions and global handler
└── config/              → Config files (Swagger, Cloudinary, etc.)
```

---

## 🛠️ Setup Instructions

\### 1. Clone the repository

```bash
git clone https://github.com/your-username/toys-ecommerce.git
cd toys-ecommerce
```

\### 2. Configure MySQL

Create a local database (e.g. `toys_db`) and update credentials in:

```properties
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/toys_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

\### 3. Run the application

```bash
./mvnw spring-boot:run
```

---

## 📦 API Endpoints

### 🔹 Products

| Method | Endpoint         | Description       |
| ------ | ---------------- | ----------------- |
| GET    | `/products`      | Get all products  |
| GET    | `/products/{id}` | Get product by ID |
| POST   | `/products`      | Add new product   |
| PUT    | `/products/{id}` | Update product    |
| DELETE | `/products/{id}` | Delete product    |

Supports filters:

```
/products?name=pikachu&categoryId=1&featured=true
```

### 🔹 Users

| Method | Endpoint      | Description    |
| ------ | ------------- | -------------- |
| GET    | `/users`      | Get all users  |
| GET    | `/users/{id}` | Get user by ID |
| POST   | `/users`      | Add user       |
| PUT    | `/users/{id}` | Update user    |
| DELETE | `/users/{id}` | Delete user    |

---

## 📷 Cloudinary Image Upload

Add your credentials to `application.properties`:

```properties
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret
```

---

## 🧪 Tests

* Unit tests using JUnit + Mockito
* Integration tests for main controllers
* Example: `ProductServiceTest`, `UserControllerTest`

Run tests:

```bash
./mvnw test
```

---

## 🧩 Swagger UI

After starting the app, open:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 📬 Contact

Made with ❤️ by FemCoders team 4

