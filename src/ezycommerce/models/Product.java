package ezycommerce.models;

public abstract class Product {

    private int productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;

    public Product() {}

    public Product(int productId, String name, String description, double price, int stockQuantity) {
        this.productId     = productId;
        this.name          = name;
        this.description   = description;
        this.price         = price;
        this.stockQuantity = stockQuantity;
    }

    // ── Getters ───────────────────────────────────────
    public int getProductId()       { return productId; }
    public String getName()         { return name; }
    public String getDescription()  { return description; }
    public double getPrice()        { return price; }
    public int getStockQuantity()   { return stockQuantity; }

    // ── Setters ───────────────────────────────────────
    public void setName(String name)               { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price)             { this.price = price; }
    public void setStockQuantity(int qty)          { this.stockQuantity = qty; }

    // ── Methods ───────────────────────────────────────
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public void reduceStock(int quantity) {
        try {
            if (quantity > stockQuantity) {
                throw new IllegalArgumentException("❌ Not enough stock for: " + name);
            }
            this.stockQuantity -= quantity;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // ── Abstract Method ───────────────────────────────
    public abstract String getProductDetails();

    // ── toString ──────────────────────────────────────
    @Override
    public String toString() {
        return String.format("[%d] %s — $%.2f (%d in stock)", productId, name, price, stockQuantity);
    }
}