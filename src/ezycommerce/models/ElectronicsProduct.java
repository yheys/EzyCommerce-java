package ezycommerce.models;

public class ElectronicsProduct extends Product {

    private String brand;
    private int warrantyMonths;   // e.g. 12, 24
    private String modelNumber;

    
    public ElectronicsProduct(int productId, String name, String description,
                               double price, int stockQuantity,
                               String brand, int warrantyMonths, String modelNumber) {
        super(productId, name, description, price, stockQuantity);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
        this.modelNumber = modelNumber;
    }


    public String getBrand()            { return brand; }
    public int getWarrantyMonths()      { return warrantyMonths; }
    public String getModelNumber()      { return modelNumber; }

    public void setBrand(String brand)              { this.brand = brand; }
    public void setWarrantyMonths(int months)       { this.warrantyMonths = months; }
    public void setModelNumber(String modelNumber)  { this.modelNumber = modelNumber; }



    @Override
    public String getProductDetails() {
        return String.format(
            "Electronics | %s %s (Model: %s) | Warranty: %d months | Price: $%.2f",
            brand, getName(), modelNumber, warrantyMonths, getPrice()
        );
    }

    @Override
    public String toString() {
        return super.toString() + " | Brand: " + brand + " | Warranty: " + warrantyMonths + " months";
    }
}
