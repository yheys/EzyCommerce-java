package ezycommerce.models;

public class CartItem {

    
    private Product product;
    private int quantity;

    
    public CartItem() {
    }

    
    public CartItem(Product product, int quantity) {
        this.product  = product;
        this.quantity = quantity;
    }

    
    public Product getProduct()  { return product; }
    public int getQuantity()     { return quantity; }

    
    public void setProduct(Product product)  { this.product = product; }
    public void setQuantity(int quantity)    { this.quantity = quantity; }

    
    public double getSubtotal() {
    try {
        return product.getPrice() * quantity;
    } catch (NullPointerException e) {
        System.out.println("❌ Error: Product is null!");
        return 0;
    }
}

    
    public void displayInfo() {
    try {
        System.out.println("Product  : " + product.getName());
        System.out.println("Quantity : " + quantity);
        System.out.println("Subtotal : $" + getSubtotal());
    } catch (NullPointerException e) {
        System.out.println("Subtotal : $" + String.format("%.2f", getSubtotal()));
    }
}

    
    @Override
public String toString() {
    try {
        return "CartItem[" + product.getName()
                + " x" + quantity
                + " = $" + String.format("%.2f", getSubtotal())
                + "]";
    } catch (NullPointerException e) {
        return "CartItem[No Product x" + quantity + " = $0.00]";
    }
}
}
