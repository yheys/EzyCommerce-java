package ezycommerce.models; 

public class ClothingProduct extends Product {


    private String size;      
    private String color;
    private String material;  

    
    public ClothingProduct(int productId, String name, String description,
                            double price, int stockQuantity,
                            String size, String color, String material) {
        super(productId, name, description, price, stockQuantity);
        this.size = size;
        this.color = color;
        this.material = material;
    }

    

    public String getSize()       { return size; }
    public String getColor()      { return color; }
    public String getMaterial()   { return material; }

    public void setSize(String size)          { this.size = size; }
    public void setColor(String color)        { this.color = color; }
    public void setMaterial(String material)  { this.material = material; }



    @Override
    public String getProductDetails() {
        return String.format(
            "Clothing | %s | Size: %s | Color: %s | Material: %s | Price: $%.2f",
            getName(), size, color, material, getPrice()
        );
    }

    @Override
    public String toString() {
        return super.toString() + " | Size: " + size + " | Color: " + color;
    }
}
