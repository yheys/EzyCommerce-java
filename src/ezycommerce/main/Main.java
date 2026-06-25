package ezycommerce.main;

import ezycommerce.db.DBConnection;
import ezycommerce.exceptions.*;
import ezycommerce.fileio.ReceiptWriter;
import ezycommerce.models.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static List<Product> products = new ArrayList<>();
    static List<Order> orders = new ArrayList<>();
    static List<Customer> customers = new ArrayList<>();
    static int orderIdCounter = 1;
    static int customerIdCounter = 1;

    public static void main(String[] args) {

        
        loadCustomersFromFile();

        
        DBConnection.testConnection();
        DBConnection.showAllProducts();

        
        products.add(new ElectronicsProduct(1, "iPhone 15", "Latest iPhone", 999.99, 10, "Apple", 12, "IP15"));
        products.add(new ClothingProduct(2, "Nike T-Shirt", "Casual wear", 29.99, 50, "M", "White", "Cotton"));
        products.add(new ElectronicsProduct(3, "Samsung TV", "4K Smart TV", 499.99, 5, "Samsung", 24, "TV4K"));

        
        boolean running = true;
        while (running) {
            System.out.println("\n=====================================");
            System.out.println("       Welcome to EzyCommerce!       ");
            System.out.println("=====================================");
            System.out.println("1. Login as Customer");
            System.out.println("2. Login as Admin");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> customerMenu();
                    case 2 -> adminMenu();
                    case 0 -> {
                        System.out.println("👋 Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("❌ Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }

        DBConnection.closeConnection();
        scanner.close();
    }

    static void saveCustomerToFile(Customer customer) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("customers.txt", true))) {
            writer.println(customer.getName() + "," +
                           customer.getEmail() + "," +
                           customer.getPassword());
            System.out.println("✅ Account saved!");
        } catch (IOException e) {
            System.out.println("❌ Error saving account: " + e.getMessage());
        }
    }

    
    static void loadCustomersFromFile() {
        try (Scanner fileScanner = new Scanner(new File("customers.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Customer c = new Customer(customerIdCounter++,
                                              parts[0], parts[1], parts[2]);
                    customers.add(c);
                }
            }
            System.out.println("✅ Customers loaded!");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No existing customers found.");
        }
    }

    
    static void saveCartToFile(Customer customer) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(customer.getName() + "_cart.txt", false))) {
            for (CartItem item : customer.getCart()) {
                writer.println(item.getProduct().getProductId() + "," + item.getQuantity());
            }
            System.out.println("✅ Cart saved!");
        } catch (IOException e) {
            System.out.println("❌ Error saving cart: " + e.getMessage());
        }
    }

        static void loadCartFromFile(Customer customer) {
        try (Scanner fileScanner = new Scanner(new File(customer.getName() + "_cart.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int productId = Integer.parseInt(parts[0]);
                    int quantity  = Integer.parseInt(parts[1]);

                    for (Product p : products) {
                        if (p.getProductId() == productId) {
                            customer.addToCart(new CartItem(p, quantity));
                            break;
                        }
                    }
                }
            }
            System.out.println("✅ Cart loaded!");
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No saved cart found.");
        }
    }

    
    static void saveOrderToFile(Order order) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("orders.txt", true))) {
            writer.println(order.getOrderId() + "," +
                           order.getCustomer().getName() + "," +
                           order.getTotalPrice() + "," +
                           order.getOrderDate() + "," +
                           order.getStatus());
            System.out.println("✅ Order saved!");
        } catch (IOException e) {
            System.out.println("❌ Error saving order: " + e.getMessage());
        }
    }

    
    static void loadOrdersFromFile() {
        try (Scanner fileScanner = new Scanner(new File("orders.txt"))) {
            System.out.println("\n--- All Orders ---");
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    System.out.println("📦 Order ID : " + parts[0] +
                                       " | Customer : " + parts[1] +
                                       " | Total : $" + parts[2] +
                                       " | Date : " + parts[3] +
                                       " | Status : " + parts[4]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No existing orders found.");
        }
    }

        static void customerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Customer Login ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();

                        Customer found = null;
                        for (Customer c : customers) {
                            if (c.getName().equals(name) &&
                                c.getPassword().equals(password)) {
                                found = c;
                                break;
                            }
                        }

                        if (found == null) {
                            System.out.println("❌ Wrong name or password!");
                        } else {
                            System.out.println("✅ Welcome back, " + found.getName() + "!");
                            loadCartFromFile(found);     
                            customerShopMenu(found);
                        }
                    }
                    case 2 -> {
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();

                        boolean exists = false;
                        for (Customer c : customers) {
                            if (c.getName().equals(name)) {
                                exists = true;
                                break;
                            }
                        }

                        if (exists) {
                            System.out.println("❌ Name already registered!");
                        } else {
                            Customer newCustomer = new Customer(
                                customerIdCounter++, name, email, password);
                            customers.add(newCustomer);
                            saveCustomerToFile(newCustomer);  
                            System.out.println("✅ Account created! Welcome, " + name + "!");
                            customerShopMenu(newCustomer);
                        }
                    }
                    case 0 -> back = true;
                    default -> System.out.println("❌ Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }

    
    static void customerShopMenu(Customer customer) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Browse Products");
            System.out.println("2. View Cart");
            System.out.println("3. Place Order");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> browseProducts(customer);
                    case 2 -> customer.viewCart();
                    case 3 -> placeOrder(customer);
                    case 0 -> {
                        System.out.println("👋 Logged out!");
                        loggedIn = false;
                    }
                    default -> System.out.println("❌ Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }

    
    static void browseProducts(Customer customer) {
        System.out.println("\n--- Available Products ---");
        for (Product p : products) {
            System.out.println(p);
        }

        System.out.print("\nEnter product ID to add to cart (0 to go back): ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (id == 0) return;

            Product selected = null;
            for (Product p : products) {
                if (p.getProductId() == id) {
                    selected = p;
                    break;
                }
            }

            if (selected == null) {
                throw new UserNotFoundException("Product ID: " + id);
            }
            if (!selected.isInStock()) {
                throw new OutOfStockException(selected.getName());
            }

            System.out.print("Enter quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());

            if (qty <= 0) {
                throw new InvalidQuantityException(qty);
            }

            customer.addToCart(new CartItem(selected, qty));
            saveCartToFile(customer);    

        } catch (UserNotFoundException | OutOfStockException | InvalidQuantityException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }

    
    static void placeOrder(Customer customer) {
    try {
        if (customer.getCart().isEmpty()) {
            throw new InvalidQuantityException(0);
        }

        
        for (CartItem item : customer.getCart()) {
            if (item.getQuantity() > item.getProduct().getStockQuantity()) {
                throw new OutOfStockException(
                    item.getProduct().getName() +
                    " (Requested: " + item.getQuantity() +
                    " | Available: " + item.getProduct().getStockQuantity() + ")"
                );
            }
        }

        String date = java.time.LocalDate.now().toString();
        Order order = new Order(orderIdCounter++, customer,
                                customer.getCart(), date);

        orders.add(order);
        customer.addOrder(order);
        order.displayInfo();
        saveOrderToFile(order);

        
        for (CartItem item : order.getItems()) {
            item.getProduct().reduceStock(item.getQuantity());
        }

        ReceiptWriter writer = new ReceiptWriter();
        writer.writeTextReceipt(order);
        writer.writeBinaryReceipt(order);
        writer.serializeOrder(order);

        customer.getCart().clear();
        saveCartToFile(customer);
        System.out.println("✅ Order placed successfully!");

    } catch (OutOfStockException e) {
        System.out.println("❌ " + e.getMessage());
        System.out.println("❌ Order cancelled! Please update your cart.");
    } catch (InvalidQuantityException e) {
        System.out.println("❌ Your cart is empty! Add items first.");
    }
}

    
    static void adminMenu() {
    System.out.print("\nEnter admin password: ");
    String password = scanner.nextLine();

    if (!password.equals("admin123")) {
        System.out.println("❌ Wrong password! Access denied.");
        return;
    }

    Admin admin = new Admin(1, "Admin", "admin@ezy.com", "admin123", "ADM01");
    System.out.println("✅ Welcome, " + admin.getName() + "!");
    admin.displayInfo();

    boolean loggedIn = true;
    while (loggedIn) {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. View All Products");
        System.out.println("2. View All Orders");
        System.out.println("3. Update Order Status");
        System.out.println("4. Delete Order");
        System.out.println("5. Statistics");
        System.out.println("0. Logout");
        System.out.print("Enter choice: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> DBConnection.showAllProducts();
                case 2 -> {
                    ReceiptWriter rw = new ReceiptWriter();
                    rw.readAllOrders();
                }
                case 3 -> {
                    ReceiptWriter rw = new ReceiptWriter();
                    System.out.print("Enter Order ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter new status (Pending/Shipped/Delivered): ");
                    String status = scanner.nextLine();
                    rw.updateOrderStatus(id, status);
                }
                case 4 -> {
                    ReceiptWriter rw = new ReceiptWriter();
                    System.out.print("Enter Order ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    rw.deleteOrder(id);
                }
                case 5 -> {
                    ReceiptWriter rw = new ReceiptWriter();
                    rw.showStatistics();
                }
                case 0 -> {
                    System.out.println("👋 Logged out!");
                    loggedIn = false;
                }
                default -> System.out.println("❌ Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }
}
}