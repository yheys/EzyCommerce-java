package ezycommerce.fileio;

import ezycommerce.models.Order;
import ezycommerce.models.CartItem;
import java.io.*;

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
                        " = $" + item.getSubtotal());
            }
            writer.println("-----------------------------");
            writer.println("Total     : $" + order.getTotalPrice());
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
}