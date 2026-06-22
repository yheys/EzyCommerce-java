package ezycommerce.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    
    private int orderId;
    private Customer customer;
    private List<CartItem> items;
    private double totalPrice;
    private String status;
    private String orderDate;

    
    public Order() {
        this.items  = new ArrayList<>();
        this.status = "Pending";
    }

    
    public Order(int orderId, Customer customer, List<CartItem> items, String orderDate) {
        this.orderId   = orderId;
        this.customer  = customer;
        this.items     = items;
        this.orderDate = orderDate;
        this.status    = "Pending";
        this.totalPrice = calculateTotal();
    }

    
    public int getOrderId()        { return orderId; }
    public Customer getCustomer()  { return customer; }
    public List<CartItem> getItems() { return items; }
    public double getTotalPrice()  { return totalPrice; }
    public String getStatus()      { return status; }
    public String getOrderDate()   { return orderDate; }

    
    public void setStatus(String status)       { this.status = status; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    
    public double calculateTotal() {
        try {
            double total = 0;
            for (CartItem item : items) {
                total += item.getSubtotal();
            }
            return total;
        } catch (NullPointerException e) {
            System.out.println("❌ Error calculating total!");
            return 0;
        }
    }

    
    public void displayInfo() {
        try {
            if (items.isEmpty()) {
                throw new Exception("❌ Order has no items!");
            }
            System.out.println("\n--- Order Details ---");
            System.out.println("Order ID   : " + orderId);
            System.out.println("Customer   : " + customer.getName());
            System.out.println("Date       : " + orderDate);
            System.out.println("Status     : " + status);
            System.out.println("Items      :");
            for (CartItem item : items) {
                item.displayInfo();
                System.out.println("----------");
            }
            System.out.println("Total      : $" + String.format("%.2f", totalPrice));;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
    @Override
    public String toString() {
        return "Order[id=" + orderId + ", customer=" + customer.getName() +
            ", total=$" + totalPrice + ", status=" + status + "]";
    }
}