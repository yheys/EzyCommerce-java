# EzyCommerce 

A console-based Java e-commerce system
EzyCommerce/
└── src/
    └── ezycommerce/
        │
        ├── models/                       
        │   ├── User.java                  ← base class
        │   ├── Customer.java              ← extends User
        │   ├── Admin.java                 ← extends User
        │   ├── Product.java               ← base class
        │   ├── ElectronicsProduct.java    ← extends Product
        │   ├── ClothingProduct.java       ← extends Product
        │   ├── Order.java                 ← implements Serializable
        │   └── CartItem.java              ← implements Serializable
        │
        ├── exceptions/                    ← exception handling
        │   └── InvalidQuantityException.java
        │   └── OutofStokeException.java
        │   └── PaymentFailedyException.java
        │   └── UserNotFoundException.java
        │
        ├── fileio/                        ← File i/o
        │   └── ReceiptWriter.java
        │
        ├── db/                            ← database
        │   └── DBConnection.java          ← connect, test, simple query
        │
        └── main/
            └── Main.java                  ← main class 


---

## ⚙️ Setup Instructions

### 1️⃣ Clone the repo:
```bash
git clone https://github.com/yheys/EzyCommerce-java.git
cd EzyCommerce-java
```

### 2️⃣ Install MySQL and create the database:
```sql
CREATE DATABASE ezycommerce;
USE ezycommerce;

CREATE TABLE products (
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(100),
    price DOUBLE,
    stock INT
);

CREATE TABLE orders (
    id            INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100),
    total         DOUBLE,
    order_date    VARCHAR(50)
);

INSERT INTO products (name, price, stock) VALUES
('iPhone 15', 999.99, 10),
('Nike T-Shirt', 29.99, 50),
('Samsung TV', 499.99, 5);
```

### 3️⃣ Update your password in `DBConnection.java`:
```java
private static final String PASSWORD = "your_password";
```

### 4️⃣ Download MySQL JDBC connector and place in `lib/`:


App Starts

├── Customer
│   ├── Register / Login
│   ├── Browse Products
│   ├── Add to Cart
│   ├── Place Order → receipt saved to file
│   └── Logout
│
└── Admin (password: admin123)
    ├── View All Products (from DB)
    ├── View All Orders
    └── Logout

---

## 📄 Files Created at Runtime

| File                   | Description             |
|------------------------|-------------------------|
| `customers.txt`        | Saved customer accounts |
| `orders.txt`           | Saved order history     |
| `receipts/order_X.txt` | Text receipt            |
| `receipts/order_X.dat` | Binary receipt          |
| `receipts/order_X.ser` | Serialized order object |

---

## 🛠️ Technologies Used
- Java 17
- MySQL 8.0
- JDBC (mysql-connector-j-9.7.0)
- VS Code + Eclipse Adoptium JDK