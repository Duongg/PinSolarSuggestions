package duongdd.dtos;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private String productName;
    private double productCapacity;

    public ProductDTO() {
    }

    public ProductDTO(String productName, double productCapacity) {
        this.productName = productName;
        this.productCapacity = productCapacity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductCapacity() {
        return productCapacity;
    }

    public void setProductCapacity(double productCapacity) {
        this.productCapacity = productCapacity;
    }
}
