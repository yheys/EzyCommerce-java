package ezycommerce.models;


import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    
    private List<CartItem> cart;
    private List<Order> orders;

    
    public Customer() {
        super();
        this.cart   = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    
    public Customer(int id, String name, String email, String password) {
        super(id, name, email, password);
        this.cart   = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    
    public List<CartItem> getCart()     { return cart; }
    public List<Order> getOrders()      { return orders; }

    
    public void addToCart(CartItem item) {
        try {
            if (item == null) {
                throw new IllegalArgumentException("❌ Item cannot be null!");
            }
            cart.add(item);
            System.out.println("✅ " + item.getProduct().getName() + " added to cart!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    
    public void removeFromCart(int index) {
        try {
            if (index < 0 || index >= cart.size()) {
                throw new IndexOutOfBoundsException("❌ Invalid item index!");
            }
            System.out.println("✅ " + cart.get(index).getProduct().getName() + " removed from cart!");
            cart.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    
    public void viewCart() {
        try {
            if (cart.isEmpty()) {
                throw new Exception("❌ Your cart is empty!");
            }
            System.out.println("\n--- My Cart ---");
            double total = 0;
            for (CartItem item : cart) {
                item.displayInfo();
                total += item.getSubtotal();
                System.out.println("----------");
            }
            System.out.println("Total : $" + total);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void addOrder(Order order) {
        orders.add(order);
    }

    
    @Override
    public void displayInfo() {
        System.out.println("Role  : Customer");
        super.displayInfo();
    }

    
    @Override
    public String toString() {
        return "Customer[" + getName() + " | Cart Items: " + cart.size() + "]";
    }
}