# EzyCommerce

A console-based Java e-commerce system built with Object-Oriented Programming (OOP), JDBC, file handling, exception handling, inheritance, polymorphism, and MySQL integration.

---

## Project Structure

```text
EzyCommerce/
└── src/
    └── ezycommerce/
        ├── models/
        │   ├── User.java
        │   ├── Customer.java
        │   ├── Admin.java
        │   ├── Product.java
        │   ├── ElectronicsProduct.java
        │   ├── ClothingProduct.java
        │   ├── Order.java
        │   └── CartItem.java
        │
        ├── exceptions/
        │   ├── InvalidQuantityException.java
        │   ├── OutOfStockException.java
        │   ├── PaymentFailedException.java
        │   └── UserNotFoundException.java
        │
        ├── fileio/
        │   └── ReceiptWriter.java
        │
        ├── db/
        │   └── DBConnection.java
        │
        └── main/
            └── Main.java
```

---

## Features

### Customer

* Register and login
* Browse products
* Add products to cart
* Place orders
* Generate receipts automatically
* Logout

### Admin

* View all products from the database
* View all orders
* Manage store operations
* Logout

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yheys/EzyCommerce-java.git
cd EzyCommerce-java
```

### 2. Create the Database

```sql
CREATE DATABASE ezycommerce;

USE ezycommerce;

CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    price DOUBLE,
    stock INT
);

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100),
    total DOUBLE,
    order_date VARCHAR(50)
);

INSERT INTO products (name, price, stock) VALUES
('iPhone 15', 999.99, 10),
('Nike T-Shirt', 29.99, 50),
('Samsung TV', 499.99, 5);
```

### 3. Configure Database Credentials

Open `DBConnection.java` and update the password:

```java
private static final String PASSWORD = "your_password";
```

### 4. Install MySQL JDBC Connector

Download the MySQL JDBC Driver and place the JAR file inside the `lib/` directory.

---

## Application Flow

```text
Application Start
│
├── Customer
│   ├── Register / Login
│   ├── Browse Products
│   ├── Add to Cart
│   ├── Place Order
│   │   └── Receipt Saved to File
│   └── Logout
│
└── Admin (Password: admin123)
    ├── View All Products
    ├── View All Orders
    └── Logout
```

---

## Runtime Files

| File                 | Description                         |
| -------------------- | ----------------------------------- |
| customers.txt        | Stores customer account information |
| orders.txt           | Stores order history                |
| receipts/order_X.txt | Plain text receipt                  |
| receipts/order_X.dat | Binary receipt                      |
| receipts/order_X.ser | Serialized order object             |

---

## Technologies Used

| Technology | Version                 |
| ---------- | ----------------------- |
| Java       | 17                      |
| MySQL      | 8.0                     |
| JDBC       | mysql-connector-j-9.7.0 |
| IDE        | VS Code / Eclipse       |
| JDK        | Eclipse Adoptium        |

---

## Object-Oriented Concepts Implemented

* Encapsulation
* Inheritance
* Polymorphism
* Method Overriding
* Exception Handling
* File Handling
* Serialization
* Database Connectivity (JDBC)

---

## Default Admin Credentials

```text
Username: admin
Password: admin123
```
