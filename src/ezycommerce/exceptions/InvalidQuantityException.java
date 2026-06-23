package ezycommerce.exceptions;
public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException(int quantity) {
        super("❌ Invalid quantity: " + quantity);
    }
}
