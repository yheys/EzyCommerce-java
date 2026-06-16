# EzyCommerce 
Coslo besed java ecommerce system that has
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
        │   └── EzyCommerceExceptions.java
        │
        ├── fileio/                        ← File i/o
        │   └── ReceiptWriter.java
        │
        ├── db/                            ← database
        │   └── DBConnection.java          ← connect, test, simple query
        │
        └── main/
            └── Main.java                  ← main class 
