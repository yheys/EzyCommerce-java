package ezycommerce.exceptions;

public class PaymentFailedException extends RuntimeException {
    public PaymentFailedException() {
        super("❌ Payment failed! Please try again.");
    }
}