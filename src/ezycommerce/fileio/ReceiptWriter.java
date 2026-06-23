package ezycommerce.fileio;

import ezycommerce.models.Order;
import ezycommerce.models.CartItem;
import java.io.*;
import java.util.Scanner;

public class ReceiptWriter {

    private static final String RECEIPTS_FOLDER = "receipts/";

    public ReceiptWriter() {
        new File(RECEIPTS_FOLDER).mkdirs();
    }

    public void writeTextReceipt(Order order) {
        String filename = RECEIPTS_FOLDER + "order_" + order.getOrderId() + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("=============================");
            writer.println("       EzyCommerce Receipt   ");
            writer.println("=============================");
            writer.println("Order ID  : " + order.getOrderId());
            writer.println("Customer  : " + order.getCustomer().getName());
            writer.println("Date      : " + order.getOrderDate());
            writer.println("Status    : " + order.getStatus());
            writer.println("-----------------------------");
            for (CartItem item : order.getItems()) {
                writer.println(item.getProduct().getName() +
                        " x" + item.getQuantity() +
                        " = $" + String.format("%.2f", item.getSubtotal()));
            }
            writer.println("-----------------------------");
            writer.println("Total     : $" + String.format("%.2f", order.getTotalPrice()));
            writer.println("=============================");
            System.out.println("✅ Text receipt saved: " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error saving text receipt: " + e.getMessage());
        }
    }

    public void writeBinaryReceipt(Order order) {
        String filename = RECEIPTS_FOLDER + "order_" + order.getOrderId() + ".dat";
        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(filename))) {
            dos.writeInt(order.getOrderId());
            dos.writeUTF(order.getCustomer().getName());
            dos.writeDouble(order.getTotalPrice());
            dos.writeUTF(order.getOrderDate());
            System.out.println("✅ Binary receipt saved: " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error saving binary receipt: " + e.getMessage());
        }
    }

    public void serializeOrder(Order order) {
        String filename = RECEIPTS_FOLDER + "order_" + order.getOrderId() + ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(order);
            System.out.println("✅ Order serialized: " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error serializing order: " + e.getMessage());
        }
    }

    public Order readSerializedOrder(int orderId) {
        String filename = RECEIPTS_FOLDER + "order_" + orderId + ".ser";
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            Order order = (Order) ois.readObject();
            System.out.println("✅ Order loaded: " + filename);
            return order;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error reading order: " + e.getMessage());
            return null;
        }
    }

    public void readAllOrders() {
        try (Scanner fileScanner = new Scanner(new File("orders.txt"))) {
            System.out.println("\n--- All Orders ---");
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    System.out.println(
                        "Order ID : " + parts[0] +
                        " | Customer : " + parts[1] +
                        " | Total : $" + String.format("%.2f", Double.parseDouble(parts[2])) +
                        " | Date : " + parts[3] +
                        " | Status : " + parts[4]
                    );
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No orders found.");
        }
    }

    public void updateOrderStatus(int orderId, String newStatus) {
        try {
            File file = new File("orders.txt");
            Scanner fileScanner = new Scanner(file);
            StringBuilder updatedContent = new StringBuilder();
            boolean found = false;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5 && Integer.parseInt(parts[0]) == orderId) {
                    parts[4] = newStatus;
                    line = String.join(",", parts);
                    found = true;
                }
                updatedContent.append(line).append("\n");
            }
            fileScanner.close();

            if (found) {
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                writer.print(updatedContent);
                writer.close();
                System.out.println("✅ Order " + orderId + " status updated to: " + newStatus);
            } else {
                System.out.println("❌ Order ID not found: " + orderId);
            }
        } catch (IOException e) {
            System.out.println("❌ Error updating order: " + e.getMessage());
        }
    }

    public void deleteOrder(int orderId) {
        try {
            File file = new File("orders.txt");
            Scanner fileScanner = new Scanner(file);
            StringBuilder updatedContent = new StringBuilder();
            boolean found = false;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5 && Integer.parseInt(parts[0]) == orderId) {
                    found = true;
                    continue;
                }
                updatedContent.append(line).append("\n");
            }
            fileScanner.close();

            if (found) {
                PrintWriter writer = new PrintWriter(new FileWriter(file));
                writer.print(updatedContent);
                writer.close();
                System.out.println("✅ Order " + orderId + " deleted!");
            } else {
                System.out.println("❌ Order ID not found: " + orderId);
            }
        } catch (IOException e) {
            System.out.println("❌ Error deleting order: " + e.getMessage());
        }
    }

    public void showStatistics() {
        try (Scanner fileScanner = new Scanner(new File("orders.txt"))) {
            int totalOrders = 0;
            double totalRevenue = 0;
            String lastCustomer = "";

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    totalOrders++;
                    totalRevenue += Double.parseDouble(parts[2]);
                    lastCustomer = parts[1];
                }
            }

            System.out.println("\n--- EzyCommerce Statistics ---");
            System.out.println("Total Orders   : " + totalOrders);
            System.out.println("Total Revenue  : $" + String.format("%.2f", totalRevenue));
            System.out.println("Last Customer  : " + lastCustomer);
            if (totalOrders > 0) {
                System.out.println("Average Order  : $" + String.format("%.2f", totalRevenue / totalOrders));
            }
        } catch (FileNotFoundException e) {
            System.out.println("ℹ️ No orders found for statistics.");
        }
    }
}