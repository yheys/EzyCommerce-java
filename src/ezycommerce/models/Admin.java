package ezycommerce.models;

import java.util.List; 

public class Admin extends User {

    
    private String adminCode;

    
    public Admin() {
        super();
    }

    
    public Admin(int id, String name, String email, String password, String adminCode) {
        super(id, name, email, password);
        this.adminCode = adminCode;
    }

    
    public String getAdminCode()              { return adminCode; }
    public void setAdminCode(String code)     { this.adminCode = code; }

    
    public void addProduct(List<Product> products, Product product) {
        try {
            if (product == null) {
                throw new IllegalArgumentException("❌ Product cannot be null!");
            }
            products.add(product);
            System.out.println("✅ Product added: " + product.getName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    
    public void viewAllOrders(List<Order> orders) {
        try {
            if (orders.isEmpty()) {
                throw new Exception("❌ No orders found!");
            }
            System.out.println("\n--- All Orders ---");
            for (Order order : orders) {
                order.displayInfo();
                System.out.println("----------");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
    @Override
    public void displayInfo() {
        System.out.println("Role  : Admin");
        super.displayInfo();
    }

    
    @Override
    public String toString() {
        return "Admin[" + getName() + " | Code: " + adminCode + "]";
    }
}